package org.acactown.clickchat.service

import com.google.common.base.Optional
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface MessageService {

    /**
     * Persis a {@link Message} from create attributes
     * @param user The user
     * @param content The content
     * @param date The date
     * @param ip The ip
     * @return A Optional persisted {@link Message} from a given attributes
     */
    Optional<Message> persist(User user, String content, Date date, String ip)

}
