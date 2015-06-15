package org.acactown.clickchat.api.resource

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = ["metaClass"])
class ErrorResource {

    Integer code
    String error

}
