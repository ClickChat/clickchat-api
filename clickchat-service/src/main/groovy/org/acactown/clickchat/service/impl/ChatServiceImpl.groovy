package org.acactown.clickchat.service.impl

import groovy.util.logging.Slf4j
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author
import org.acactown.clickchat.domain.model.ChatRoom
import org.acactown.clickchat.domain.model.PreviousMessage
import org.acactown.clickchat.service.AuthorConverter
import org.acactown.clickchat.service.ChatService
import org.acactown.clickchat.service.PreviousMessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
@Slf4j
@Service
class ChatServiceImpl implements ChatService {

    private final AuthorConverter authorConverter
    private final PreviousMessageConverter messageConverter

    //TODO: Persist on REDIS!
    private final Map<String, Author> cachedAuthors
    private final Queue<PreviousMessage> cachedMessages

    private static final int MAX_MESSAGES = 10

    @Autowired
    ChatServiceImpl(AuthorConverter authorConverter, PreviousMessageConverter messageConverter) {
        this.authorConverter = authorConverter
        this.messageConverter = messageConverter

        this.cachedAuthors = new ConcurrentHashMap<>()
        this.cachedMessages = new ConcurrentLinkedQueue<>()
    }

    @Override
    ChatRoom join(User user) {
        Author author = authorConverter.toAuthor(user)

        String authorId = author.id
        List<Author> authors = new ArrayList<>(cachedAuthors.size())
        for (Author cachedAuthor : cachedAuthors.values()) {
            String cachedAuthorId = cachedAuthor.id
            if (authorId != cachedAuthorId) {
                authors.add(cachedAuthor)
            }
        }

        cachedAuthors.put(authorId, author)

        return new ChatRoom(authors: authors, messages: new ArrayList(cachedMessages))
    }

    @Override
    @Async
    void leave(User user) {
        String authorId = user.id

        cachedAuthors.remove(authorId)
    }

    @Override
    @Async
    void addMessage(Message previousMessage, Integer hash) {
        int size = (cachedAuthors.size() - MAX_MESSAGES)
        for (int i = 0; i < size; i++) {
            cachedMessages.remove()
        }

        PreviousMessage message = messageConverter.toPreviousMessage(previousMessage, hash)

        cachedMessages.add(message)
    }

}
