package org.acactown.clickchat.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Optional
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.*
import org.acactown.clickchat.api.service.AuthorConverter
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import java.util.concurrent.ConcurrentHashMap

import static org.acactown.clickchat.service.util.RestUtil.APPLICATION_JSON
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.GET
import static org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Controller
@Api(value = "Chat", description = "Chat Operations", produces = APPLICATION_JSON)
class ChatController {

    private final ObjectMapper mapper
    private final UserService service
    private final AuthorConverter converter

    //TODO: Persist on REDIS!
    private final Map<String, AuthorResource> authorsCache

    @Autowired
    ChatController(UserService service, AuthorConverter converter, @Qualifier("objectMapper") ObjectMapper mapper) {
        this.service = service
        this.mapper = mapper
        this.converter = converter

        authorsCache = new ConcurrentHashMap<>()
    }

    @MessageMapping("/chat")
    @SendTo("/topic/input")
    public OutputResource sendOutput(InputResource input) {
        String data = input.data
        InputType type = input.type

        OutputResource output = new OutputResource(id: input.id, time: new Date(), type: type)
        switch (type) {
            case InputType.MESSAGE:
                InputMessageResource inputMessage = mapper.readValue(data, InputMessageResource)
                Optional<User> user = service.meFromAuthorization(inputMessage.token)
                if (user.isPresent()) {
                    OutputMessageResource outputMessage = new OutputMessageResource(
                        author: user.get().id,
                        message: inputMessage.message
                    )

                    output.data = mapper.writeValueAsString(outputMessage)
                }

                break
            case InputType.LEAVE:
            case InputType.JOIN:
                EventResource event = mapper.readValue(data, EventResource)
                Optional<User> user = service.meFromAuthorization(event.token)
                if (user.isPresent()) {
                    AuthorResource author = converter.toResource(user.get())

                    output.data = mapper.writeValueAsString(author)
                }

                break
        }

        return output
    }

    @RequestMapping(value = '/join', method = GET, produces = APPLICATION_JSON)
    @ApiOperation(value = "Join user to chat", notes = "Get all users in chat", response = AuthorsResource, produces = APPLICATION_JSON)
    ResponseEntity<AuthorsResource> join(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        Optional<User> user = service.meFromAuthorization(authorization)
        if (user.isPresent()) {
            AuthorResource author = converter.toResource(user.get())
            String authorId = author.id
            List<AuthorResource> authors = new ArrayList<>(authorsCache.size())
            for (AuthorResource cachedAuthor : authorsCache.values()) {
                String cachedAuthorId = cachedAuthor.id
                if (authorId != cachedAuthorId) {
                    authors.add(cachedAuthor)
                }
            }

            authorsCache.put(authorId, author)

            return new ResponseEntity<>(new AuthorsResource(authors: authors), OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

    @RequestMapping(value = '/leave', method = POST, produces = APPLICATION_JSON)
    @ApiOperation(value = "Make the leave process", notes = "Leave specified user", response = String, produces = APPLICATION_JSON)
    ResponseEntity<String> leave(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        Optional<User> user = service.meFromAuthorization(authorization)
        if (user.isPresent()) {
            String authorId = user.get().id

            authorsCache.remove(authorId)

            return new ResponseEntity<>("OK", OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

}
