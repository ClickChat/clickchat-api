package org.acactown.clickchat.service

import com.google.common.base.Optional
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface MessageService {

    Optional<Message> persist(User user, String content, Date date, String ip)
    
}
