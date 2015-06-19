package org.acactown.clickchat.cache

import com.google.common.base.Optional
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface AuthUserRepository {

    Optional<User> findAuthUser(Token token)

    Optional<User> insertAuthUser(User user)

    void deleteAuthUser(Token token)

}
