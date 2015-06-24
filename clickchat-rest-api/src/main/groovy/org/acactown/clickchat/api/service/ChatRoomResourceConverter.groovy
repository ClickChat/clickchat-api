package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.ChatRoomResource
import org.acactown.clickchat.domain.model.ChatRoom

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface ChatRoomResourceConverter {

    /**
     * Convert from {@link ChatRoom} to {@link ChatRoomResource}
     * @param room The room
     * @return The {@link ChatRoomResource} from a given {@link ChatRoom}
     */
    ChatRoomResource fromChatRoom(ChatRoom room)

}
