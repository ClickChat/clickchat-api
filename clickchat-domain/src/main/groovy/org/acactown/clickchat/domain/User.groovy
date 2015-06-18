package org.acactown.clickchat.domain

import groovy.transform.ToString
import org.acactown.clickchat.commons.Token
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotNull

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Document(collection = "users")
@ToString(includePackage = false, includeNames = true)
class User extends Author {
    
    @Indexed(unique = true)
    String externalId
    @NotNull
    Timestamp createdAt
    Timestamp updateAt

    @Transient
    Token token

}
