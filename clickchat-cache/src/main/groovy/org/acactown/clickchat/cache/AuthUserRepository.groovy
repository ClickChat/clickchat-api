package org.acactown.clickchat.cache

import com.google.common.base.Optional
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface AuthUserRepository {

    /**
     * Find and Authenticated user from a given token
     * @param token The token
     * @return A Optional {@link User} from valid token
     */
    Optional<User> findAuthUser(Token token)

    /**
     * Add a {@link User} to cache
     * @param user The user
     * @return The cached {@link User}
     */
    Optional<User> insertAuthUser(User user)

    /**
     * Remover the {@link User} related to the given token
     * @param token The token
     */
    void deleteAuthUser(Token token)

}
