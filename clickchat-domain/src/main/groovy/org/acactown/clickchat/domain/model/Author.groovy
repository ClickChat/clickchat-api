package org.acactown.clickchat.domain.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed

import javax.validation.constraints.NotNull

/**
 * @author Andrés Amado
 * @since 2015-06-18
 */
class Author {

    @Id
    String id
    @NotNull
    String name
    @Indexed(unique = true)
    String email
    @NotNull
    String thumbnail
    
}
