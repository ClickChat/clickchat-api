package org.acactown.clickchat.service

import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.model.PreviousMessage

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface PreviousMessageConverter {

    PreviousMessage toPreviousMessage(Message message, Integer hash)

}
