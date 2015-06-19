package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.api.service.AuthorResourceConverter
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-18
 */
@Slf4j
@Service
class AuthorResourceConverterImpl implements AuthorResourceConverter {

    @Override
    AuthorResource fromUser(User user) {
        return new AuthorResource(
            id: user.id,
            name: user.name,
            email: user.email,
            thumbnail: user.thumbnail
        )
    }

    @Override
    AuthorResource fromAuthor(Author author) {
        return new AuthorResource(
            id: author.id,
            name: author.name,
            email: author.email,
            thumbnail: author.thumbnail
        )
    }

}
