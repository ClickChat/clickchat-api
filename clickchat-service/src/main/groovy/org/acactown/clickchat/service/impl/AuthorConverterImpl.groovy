package org.acactown.clickchat.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author
import org.acactown.clickchat.service.AuthorConverter
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
@Slf4j
@Service
class AuthorConverterImpl implements AuthorConverter {

    @Override
    Author toAuthor(User user) {
        return new Author(
            id: user.id,
            name: user.name,
            email: user.email,
            thumbnail: user.thumbnail
        )
    }

}
