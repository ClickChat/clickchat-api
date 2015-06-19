package org.acactown.clickchat.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.model.Author
import org.acactown.clickchat.domain.model.PreviousMessage
import org.acactown.clickchat.service.AuthorConverter
import org.acactown.clickchat.service.PreviousMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
@Slf4j
@Service
class PreviousMessageConverterImpl implements PreviousMessageConverter {

    private final AuthorConverter converter

    @Autowired
    PreviousMessageConverterImpl(AuthorConverter converter) {
        this.converter = converter
    }

    @Override
    PreviousMessage toPreviousMessage(Message message, Integer hash) {
        Author author = converter.toAuthor(message.user)
        Date time = message.createdAt.date
        String previousMessage = message.content

        return new PreviousMessage(id: hash, author: author, date: time, message: previousMessage)
    }

}
