package org.acactown.clickchat.service.impl

import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Timestamp
import org.acactown.clickchat.service.MessageService
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
@Slf4j
@Service
class MessageServiceImpl implements MessageService {

    @Override
    Optional<Message> persist(User user, String content, Date date, String ip) {
        Message message = new Message(
            user: user,
            createdAt: new Timestamp(date: date, ip: ip),
            content: content
        )

        //TODO: Send Message to async persist queue!

        return Optional.of(message)
    }

}
