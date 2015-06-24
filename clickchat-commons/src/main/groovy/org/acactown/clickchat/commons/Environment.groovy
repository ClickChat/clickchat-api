package org.acactown.clickchat.commons

import com.google.common.base.Optional

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
interface Environment {

    /**
     * Checks if a key is present
     *
     * @return <tt>true</tt> only if the environment has a property
     */
    boolean hasKey(String key)

    /**
     * Checks if a key is equals to <tt>true</tt> if it's not present will return <tt>false</tt>
     *
     * @param feature The feature name
     * @return <tt>true</tt> only if the environment has a property and the value is <tt>true</tt>
     */
    boolean supports(String feature)

    /**
     * Checks if a key is equals to <tt>true</tt>
     * if it's not present will return the <tt>defaultValue</tt>
     *
     * @param feature The feature name
     * @param defaultValue The  default value
     * @return <tt>true</tt> only if the environment has a property and the value is <tt>true</tt>,
     * otherwise: returns the <tt>defaultValue</tt>
     * @see Environment#supports(String)
     */
    boolean supportsOr(String feature, boolean defaultValue)

    /**
     * Get a Optional property value according to Environment
     *
     * @param key The property name
     * @return The Optional property value according to Environment
     */
    Optional<String> getOptionalProperty(String key)

    /**
     * Get the property value according to Environment,
     * use only if you are 100% sure that the environment has a key,
     * otherwise use {@link Environment#getOptionalProperty(String)}
     *
     * @param key The property name
     * @return The property value according to Environment, otherwise returns <tt>null<tt>
     */
    String getProperty(String key)

    /**
     * Get the property value according to Environment,
     * if it's not present will return the <tt>defaultValue</tt>
     *
     * @param key The property name
     * @param defaultValue The default value
     * @return the property value according to Environment
     * @see Environment#getProperty(String)
     */
    String getPropertyOr(String key, String defaultValue)

    /**
     * @return an {@link Iterable} with all Environment keys
     */
    Iterable<String> getKeys()

}
