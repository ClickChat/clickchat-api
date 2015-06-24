package org.acactown.clickchat.service

import com.google.common.base.Optional
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface UserService {

    /**
     * Get a {@link User} related to a given {@link Token}
     * @param token The token
     * @return A Optional {@link User} related to a given {@link Token}
     */
    Optional<User> me(Token token)

    /**
     * Get a {@link User} related to a given authorization representation
     * @param authorization The authorization representation
     * @return A Optional {@link User} related to a given authorization representation
     */
    Optional<User> meFromAuthorization(String authorization)

    /**
     * The login process using a given {@link Token}
     * @param token The token
     * @param ip The ip
     * @return A Optional {@link User} related to a given {@link Token}
     */
    Optional<User> login(Token token, String ip)

    /**
     * The logout process using a given {@link Token}
     * @param token The token
     */
    void logout(Token token)

    /**
     * The logout process using a given authorization representation
     * @param authorization The authorization representation
     */
    void logoutFromAuthorization(String authorization)

}
