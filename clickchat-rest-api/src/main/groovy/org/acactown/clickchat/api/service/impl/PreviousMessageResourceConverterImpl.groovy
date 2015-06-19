package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.PreviousMessageResource
import org.acactown.clickchat.api.service.PreviousMessageResourceConverter
import org.acactown.clickchat.domain.model.PreviousMessage
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-18
 */
@Slf4j
@Service
class PreviousMessageResourceConverterImpl implements PreviousMessageResourceConverter {

    @Override
    PreviousMessageResource fromPreviousMessage(PreviousMessage message) {
        return new PreviousMessageResource(
            id: message.id,
            author: message.author,
            message: message.message,
            date: message.date
        )
    }

}
