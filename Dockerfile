FROM tomcat:7.0.62-jre7

MAINTAINER Andrés Amado "acactown@gmail.com"

# Clean Tomcat installation
RUN rm -rf ${CATALINA_HOME}/temp/* \
  && rm -rf ${CATALINA_HOME}/webapps/* \
  && rm -rf ${CATALINA_HOME}/work/* \
  && rm -rf ${CATALINA_HOME}/logs/*

# Add ClickChat REST-API compiled artifact
ADD clickchat-rest-api/build/libs/clickchat-rest-api-1.0.0.war ${CATALINA_HOME}/webapps/ROOT.war

# Mount logs directory
VOLUME ['/usr/local/tomcat/logs']
