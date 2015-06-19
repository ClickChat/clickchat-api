package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.UserDetailResource
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface UserDetailResourceConverter {

    UserDetailResource fromUser(User user)

}
