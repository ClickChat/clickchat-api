package org.acactown.clickchat.api.config

import groovy.util.logging.Slf4j
import org.springframework.jmx.support.JmxUtils

import javax.management.*

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Slf4j
class ServletContainerSettingsEnsurance {

    void safelyVerifyServlet() {
        MBeanServer mBeanServer = JmxUtils.locateMBeanServer()
        HTTPConnectorCheck httpConnectorCheck = new HTTPConnectorCheck()
        httpConnectorCheck.perform(mBeanServer)
    }

    private static class HTTPConnectorCheck implements ManagedBeanCheck {

        private static final String HTTP_CONNECTOR_DEFAULT_PORT = "8080"
        private static final String URI_ENCODING_MBEAN_ATTRIBUTE = "URIEncoding"
        private static final String COMPRESSION_MBEAN_ATTRIBUTE = "compression"
        private static final String EXPECTED_URI_ENCODING_VALUE = "UTF-8"
        private static final String EXPECTED_COMPRESSION_VALUE = "force"

        @Override
        void perform(MBeanServer mBeanServer) {
            ObjectName connectorObjectName
            try {
                connectorObjectName = new ObjectName("Catalina:type=Connector,port=${HTTP_CONNECTOR_DEFAULT_PORT}")

                internalPerform(mBeanServer, connectorObjectName)
            } catch (MalformedObjectNameException ignored) {
                // Swallowed on purpose
            }
        }

        private void internalPerform(MBeanServer mBeanServer, ObjectName objectName) {
            try {
                this.ensureMBeanAttributeValue(URI_ENCODING_MBEAN_ATTRIBUTE, EXPECTED_URI_ENCODING_VALUE, mBeanServer, objectName)
            } catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException e) {
                log.warn("Could not verify MBean Attribute [{}] in [{}]", URI_ENCODING_MBEAN_ATTRIBUTE, objectName.toString())
            }
            try {
                this.ensureMBeanAttributeValue(COMPRESSION_MBEAN_ATTRIBUTE, EXPECTED_COMPRESSION_VALUE, mBeanServer, objectName)
            } catch (AttributeNotFoundException | InstanceNotFoundException | MBeanException | ReflectionException e) {
                log.warn("Could not verify MBean Attribute [{}] in [{}]", COMPRESSION_MBEAN_ATTRIBUTE, objectName.toString())
            }
        }

        private void ensureMBeanAttributeValue(String mBeanAttributeName, Object mBeanExpectedValue,
                                               MBeanServer mBeanServer, ObjectName objectName) {
            Object attributeValue = mBeanServer.getAttribute(objectName, mBeanAttributeName)
            String stringValue = safeToString(attributeValue)
            if (!stringValue || !stringValue.equals(mBeanExpectedValue)) {
                log.warn("Value of MBean Attribute [{}] is [{}]", mBeanAttributeName, stringValue)

                changeAttributeValue(mBeanAttributeName, mBeanExpectedValue, mBeanServer, objectName)
            } else {
                log.info("Checked: Value of MBean Attribute [{}] is [{}] in [{}]", mBeanAttributeName, stringValue, objectName.toString())
            }
        }

        private void changeAttributeValue(String mBeanAttributeName, Object mBeanExpectedValue,
                                          MBeanServer mBeanServer, ObjectName objectName) {
            Attribute attribute = new Attribute(mBeanAttributeName, mBeanExpectedValue)
            try {
                mBeanServer.setAttribute(objectName, attribute)

                log.warn("Changed value of of MBean Attribute [{}] to [{}] in [{}]", attribute.getName(), attribute.getValue(), objectName.toString())
            } catch (InstanceNotFoundException | InvalidAttributeValueException | AttributeNotFoundException | ReflectionException | MBeanException e) {
                log.error("Failed to change value of MBean Attribute [{}] to [{}] in [{}]!", attribute.getName(), attribute.getValue(), objectName.toString(), e)
            }
        }

    }

    private static interface ManagedBeanCheck {

        void perform(MBeanServer mBeanServer)

    }

    private static String safeToString(Object object) {
        if (object) {
            if (object.getClass().isAssignableFrom(String)) {
                return (String) object
            } else {
                return object.toString()
            }
        }

        return null
    }

}
