package org.acactown.clickchat.service

import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.model.PreviousMessage

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface PreviousMessageConverter {

    /**
     * Convert from {@link Message} to {@link PreviousMessage}
     * @param message The message
     * @param hash The hash
     * @return The {@link PreviousMessage} from a given {@link Message}
     */
    PreviousMessage toPreviousMessage(Message message, Integer hash)

}
