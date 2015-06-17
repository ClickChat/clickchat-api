package org.acactown.clickchat.cache.impl

import com.google.common.base.Optional
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.acactown.clickchat.cache.AuthUserRepository
import org.acactown.clickchat.cache.TokenEncoder
import org.acactown.clickchat.commons.Token
import org.acactown.clickchat.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service

import static com.google.common.base.Strings.isNullOrEmpty
import static groovy.json.JsonOutput.toJson

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class AuthUserRepositoryImpl implements AuthUserRepository {

    private final TokenEncoder encoder
    private final ValueOperations<String, String> cache
    private final JsonSlurper slurper

    @Autowired
    AuthUserRepositoryImpl(TokenEncoder encoder, StringRedisTemplate redis) {
        this.encoder = encoder
        this.cache = redis.opsForValue()

        slurper = new JsonSlurper()
    }

    @Override
    Optional<User> findAuthUser(Token token) {
        Optional<String> authUser = Optional.fromNullable(cache.get(token.accessToken))
        if (authUser.isPresent()) {
            String user = authUser.get()

            if (!isNullOrEmpty(user)) {
                return Optional.fromNullable(slurper.parseText(user) as User)
            }
        }

        return Optional.absent()
    }

    @Override
    Optional<User> insertAuthUser(User user) {
        Optional<Token> authToken = encoder.generateToken(user)
        if (authToken.isPresent()) {
            Token token = authToken.get()
            user.token = token
            cache.set(token.accessToken, toJson(user))

            return Optional.of(user)
        }

        return Optional.absent()
    }

    @Override
    void deleteAuthUser(Token token) {
        cache.set(token.accessToken, null)
    }

}
