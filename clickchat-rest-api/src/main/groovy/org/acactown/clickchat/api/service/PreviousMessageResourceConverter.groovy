package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.PreviousMessageResource
import org.acactown.clickchat.domain.model.PreviousMessage

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface PreviousMessageResourceConverter {

    /**
     * Convert from {@link PreviousMessage} to {@link PreviousMessageResource}
     * @param message The message
     * @return The {@link PreviousMessageResource} from a given {@link PreviousMessage}
     */
    PreviousMessageResource fromPreviousMessage(PreviousMessage message)

}
