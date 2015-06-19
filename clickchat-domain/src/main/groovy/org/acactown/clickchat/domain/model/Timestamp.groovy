package org.acactown.clickchat.domain.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import groovy.transform.TupleConstructor

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@TupleConstructor
@EqualsAndHashCode
@ToString(includePackage = false, includeNames = true)
class Timestamp {

    Date date
    String ip

}
