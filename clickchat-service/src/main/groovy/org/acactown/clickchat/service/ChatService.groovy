package org.acactown.clickchat.service

import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.ChatRoom

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface ChatService {

    /**
     * Add the given {@link User} to a {@link ChatRoom}
     * @param user The user
     * @return The updated {@link ChatRoom}
     */
    ChatRoom join(User user)

    /**
     * Remove the given {@link User} from the {@link ChatRoom}
     * @param user The user
     */
    void leave(User user)

    /**
     * Add a {@link Message} to the {@link ChatRoom}
     * @param message The message
     * @param hash The message hash
     */
    void addMessage(Message message, Integer hash)

}
