package org.acactown.clickchat.api.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.api.resource.ChatRoomResource
import org.acactown.clickchat.api.resource.PreviousMessageResource
import org.acactown.clickchat.api.service.AuthorResourceConverter
import org.acactown.clickchat.api.service.ChatRoomResourceConverter
import org.acactown.clickchat.api.service.PreviousMessageResourceConverter
import org.acactown.clickchat.domain.model.Author
import org.acactown.clickchat.domain.model.ChatRoom
import org.acactown.clickchat.domain.model.PreviousMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-18
 */
@Slf4j
@Service
class ChatRoomResourceConverterImpl implements ChatRoomResourceConverter {

    private final AuthorResourceConverter authorConverter
    private final PreviousMessageResourceConverter messageConverter

    @Autowired
    ChatRoomResourceConverterImpl(AuthorResourceConverter authorConverter, PreviousMessageResourceConverter messageConverter) {
        this.authorConverter = authorConverter
        this.messageConverter = messageConverter
    }

    @Override
    ChatRoomResource fromChatRoom(ChatRoom room) {
        List<Author> roomAuthors = room.authors
        List<PreviousMessage> roomMessages = room.messages

        List<AuthorResource> authors = new ArrayList<>(roomAuthors.size())
        for (Author author : roomAuthors) {
            authors.add(authorConverter.fromAuthor(author))
        }

        List<PreviousMessageResource> messages = new ArrayList<>(roomMessages.size())
        for (PreviousMessage message : roomMessages) {
            messages.add(messageConverter.fromPreviousMessage(message))
        }

        return new ChatRoomResource(authors: authors, messages: messages)
    }

}
