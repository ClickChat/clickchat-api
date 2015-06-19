package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.ChatRoomResource
import org.acactown.clickchat.domain.model.ChatRoom

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface ChatRoomResourceConverter {

    ChatRoomResource fromChatRoom(ChatRoom room)

}
