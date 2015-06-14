package org.acactown.clickchat.domain

import groovy.transform.ToString
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotNull

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Document(collection = "users")
@ToString(includePackage = false, includeNames = true)
class User {

    @Id
    String id
    @NotNull
    String name
    @NotNull
    String nickname
    @NotNull
    String email
    @NotNull
    Timestamp createdAt
    Timestamp updateAt

}
