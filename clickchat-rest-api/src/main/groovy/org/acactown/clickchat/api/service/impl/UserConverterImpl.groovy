package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.UserDetailResource
import org.acactown.clickchat.api.service.UserConverter
import org.acactown.clickchat.domain.User
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class UserConverterImpl implements UserConverter {

    @Override
    UserDetailResource toResource(User user) {
        return new UserDetailResource(
            token: user.token,
            name: user.name,
            email: user.email,
            thumbnail: user.thumbnail
        )
    }
    
}
