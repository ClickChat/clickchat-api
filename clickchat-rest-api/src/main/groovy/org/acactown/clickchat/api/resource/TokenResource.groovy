package org.acactown.clickchat.api.resource

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = ["metaClass"])
class TokenResource {

    String accessToken
    String tokenType

}
