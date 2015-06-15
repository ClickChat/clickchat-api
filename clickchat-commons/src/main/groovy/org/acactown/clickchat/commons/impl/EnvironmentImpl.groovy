package org.acactown.clickchat.commons.impl

import com.google.common.base.Optional
import com.google.common.collect.Iterables
import groovy.util.logging.Slf4j
import org.acactown.clickchat.commons.Environment
import org.springframework.stereotype.Service

import static com.google.common.base.Optional.*
import static com.google.common.base.Strings.isNullOrEmpty

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Slf4j
@Service
class EnvironmentImpl implements Environment {

    @Override
    boolean hasKey(final String key) {
        log.debug("Checking is Environment has the [{}] property...", key)
        Optional<String> property = getOptionalProperty(key)

        return property.isPresent()
    }

    @Override
    boolean supports(final String feature) {
        log.debug("Checking is Environment supports the [{}] feature...", feature)
        Optional<String> property = getOptionalProperty(feature)

        return (property.isPresent() && safeSupports(property.get()))
    }

    @Override
    boolean supportsOr(final String feature, final boolean defaultValue) {
        log.debug("Checking is Environment supports the [{}] feature...", feature)
        Optional<String> property = getOptionalProperty(feature)
        if (property.isPresent()) {
            return safeSupports(property.get())
        }

        log.debug("Environment not supports the [{}] feature, returning default value [{}]", feature, defaultValue)
        return defaultValue
    }

    @Override
    Optional<String> getOptionalProperty(final String key) {
        log.debug("Getting [{}] property from Environment...", key)
        try {
            String value = System.getenv(key)
            if (!isNullOrEmpty(value)) {
                return of(value)
            }
        } catch (Exception ex) {
            log.error("Error getting Environment property for key [{}]!", key, ex)
        }

        return absent()
    }

    @Override
    String getProperty(final String key) {
        return getPropertyOr(key, null)
    }

    @Override
    String getPropertyOr(final String key, final String defaultValue) {
        Optional<String> property = getOptionalProperty(key)
        if (property.isPresent()) {
            return property.get()
        }

        log.debug("Environment has no the [{}] property, returning default value [{}]", key, defaultValue)
        return defaultValue
    }

    @Override
    Iterable<String> getKeys() {
        log.debug("Getting Environment keys...")
        try {
            Optional<Map<String, String>> properties = fromNullable(System.getenv())
            if (properties.isPresent()) {
                Set<String> keys = properties.get().keySet()
                if (!keys.isEmpty()) {
                    return Iterables.unmodifiableIterable(keys)
                }
            }
        } catch (Exception ex) {
            log.error("Error getting ENV properties!", ex)
        }

        log.debug("Environment has no properties...")
        return Collections.emptyList()
    }

    private static boolean safeSupports(final String value) {
        return Boolean.parseBoolean(value.trim().toLowerCase())
    }

}
