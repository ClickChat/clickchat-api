package org.acactown.clickchat.service.impl

import com.google.common.base.Optional
import org.acactown.clickchat.cache.AuthUserRepository
import org.acactown.clickchat.commons.Token
import org.acactown.clickchat.domain.Timestamp
import org.acactown.clickchat.service.aspect.AuthIntercepted

import static com.google.common.base.Strings.*
import groovy.util.logging.Slf4j
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.repository.UserRepository
import org.acactown.clickchat.service.UserService
import org.acactown.clickchat.service.client.GoogleAPIClient
import org.acactown.clickchat.service.model.GoogleUserInfo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
class UserServiceImpl implements UserService {

    private final UserRepository repository
    private final AuthUserRepository authRepository
    private final GoogleAPIClient googleAPIClient

    @Autowired
    UserServiceImpl(UserRepository repository, AuthUserRepository authRepository, GoogleAPIClient googleAPIClient) {
        this.repository = repository
        this.authRepository = authRepository
        this.googleAPIClient = googleAPIClient
    }

    @Override
    Optional<User> me(Token token) {
        return authRepository.findAuthUser(token)
    }

    @Override
    @AuthIntercepted
    Optional<User> login(Token token, String ip) {
        Optional<GoogleUserInfo> userInfo = googleAPIClient.getUserInfo(token)

        if (userInfo.isPresent()) {
            GoogleUserInfo info = userInfo.get()

            String externalId = info.id,
                   name = info.name,
                   email = info.email,
                   thumbnail = info.picture

            Timestamp now = new Timestamp(date: new Date(), ip: ip)

            User previousUser = repository.findByExternalId(externalId)
            if (previousUser) {
                boolean update = false

                String previousName = previousUser.name
                if (!isNullOrEmpty(name) && (isNullOrEmpty(previousName) || (previousName != name))) {
                    update = true
                    previousUser.name = name
                }

                String previousThumbnail = previousUser.thumbnail
                if (!isNullOrEmpty(thumbnail) && (isNullOrEmpty(previousThumbnail) || (previousThumbnail != thumbnail))) {
                    update = true
                    previousUser.thumbnail = thumbnail
                }

                if (update) {
                    previousUser.updateAt = now

                    return Optional.fromNullable(repository.save(previousUser))
                }

                return Optional.of(previousUser)
            } else {
                User user = new User(
                    name: name,
                    externalId: externalId,
                    email: email,
                    thumbnail: thumbnail,
                    createdAt: now,
                    updateAt: now
                )

                return Optional.fromNullable(repository.insert(user))
            }
        }

        return Optional.absent()
    }

    @Override
    void logout(Token token) {
        authRepository.deleteAuthUser(token)
    }

}
