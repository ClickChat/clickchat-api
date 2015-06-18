package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.api.service.AuthorConverter
import org.acactown.clickchat.domain.User
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-18
 */
@Slf4j
@Service
class AuthorConverterImpl implements AuthorConverter {

    @Override
    AuthorResource toResource(User user) {
        return new AuthorResource(
            id: user.id,
            name: user.name,
            email: user.email,
            thumbnail: user.thumbnail
        )
    }

}
