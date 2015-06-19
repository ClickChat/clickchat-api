package org.acactown.clickchat.cache.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.acactown.clickchat.cache.AuthUserRepository
import org.acactown.clickchat.cache.TokenEncoder
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.domain.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.data.redis.core.ValueOperations
import org.springframework.stereotype.Service

import static com.google.common.base.Strings.isNullOrEmpty

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class AuthUserRepositoryImpl implements AuthUserRepository {

    private final TokenEncoder encoder
    private final ValueOperations<String, String> cache
    private final ObjectMapper mapper

    @Autowired
    AuthUserRepositoryImpl(TokenEncoder encoder, StringRedisTemplate redis,
                           @Qualifier("objectMapper") ObjectMapper mapper) {
        this.encoder = encoder
        this.mapper = mapper
        this.cache = redis.opsForValue()
    }

    @Override
    Optional<User> findAuthUser(Token token) {
        Optional<String> authUser = Optional.fromNullable(cache.get(token.accessToken))
        if (authUser.isPresent()) {
            String userJson = authUser.get()

            if (!isNullOrEmpty(userJson)) {
                User user = mapper.readValue(userJson, User)

                return Optional.fromNullable(user)
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
            cache.set(token.accessToken, mapper.writeValueAsString(user))

            return Optional.of(user)
        }

        return Optional.absent()
    }

    @Override
    void deleteAuthUser(Token token) {
        cache.set(token.accessToken, "")
    }

}
