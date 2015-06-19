package org.acactown.clickchat.service

import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.ChatRoom

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface ChatService {

    ChatRoom join(User user)

    void leave(User user)
    
    void addMessage(Message message, Integer hash)

}
