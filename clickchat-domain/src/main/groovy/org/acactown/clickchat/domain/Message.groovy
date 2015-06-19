package org.acactown.clickchat.domain

import org.acactown.clickchat.domain.model.Timestamp
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

import javax.validation.constraints.NotNull

import static org.springframework.data.mongodb.core.index.IndexDirection.ASCENDING

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Document(collection = "messages")
class Message {

    @Id
    String id
    @NotNull
    @Indexed(name = "created_idx", direction = ASCENDING, unique = false)
    Timestamp createdAt
    @NotNull
    String content
    @DBRef
    User user

}
