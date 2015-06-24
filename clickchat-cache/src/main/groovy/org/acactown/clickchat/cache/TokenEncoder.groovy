package org.acactown.clickchat.cache

import com.google.common.base.Optional
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface TokenEncoder {

    /**
     * A simple and easy way to generate tokens
     * @param user The user input to generate base token
     * @return A Optional {@link Token} related to a given {@link User}
     */
    Optional<Token> generateToken(User user)

}
