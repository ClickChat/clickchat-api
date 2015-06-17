package org.acactown.clickchat.service.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@JsonIgnoreProperties(ignoreUnknown = true, value = ["metaClass"])
class GoogleUserInfo {
    
    String id
    String name
    String email
    String picture
    String locale 
    
}
