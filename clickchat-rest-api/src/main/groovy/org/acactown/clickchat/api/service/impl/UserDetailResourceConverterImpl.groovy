package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.UserDetailResource
import org.acactown.clickchat.api.service.UserDetailResourceConverter
import org.acactown.clickchat.domain.User
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class UserDetailResourceConverterImpl implements UserDetailResourceConverter {

    @Override
    UserDetailResource fromUser(User user) {
        return new UserDetailResource(
            token: user.token,
            id: user.id,
            name: user.name,
            email: user.email,
            thumbnail: user.thumbnail
        )
    }

}
