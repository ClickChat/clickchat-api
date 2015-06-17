package org.acactown.clickchat.service

import com.google.common.base.Optional
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface UserService {

    Optional<User> authUser(String accessToken, String tokenType, String ip)

}
