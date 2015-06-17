package org.acactown.clickchat.service

import com.google.common.base.Optional
import org.acactown.clickchat.commons.Token
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface UserService {

    Optional<User> me(Token token)

    Optional<User> login(Token token, String ip)

    void logout(Token token)

}
