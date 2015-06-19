package org.acactown.clickchat.api.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.common.base.Optional
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.*
import org.acactown.clickchat.api.service.AuthorResourceConverter
import org.acactown.clickchat.api.service.ChatRoomResourceConverter
import org.acactown.clickchat.domain.Message
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.ChatRoom
import org.acactown.clickchat.service.ChatService
import org.acactown.clickchat.service.MessageService
import org.acactown.clickchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.ResponseEntity
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

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
    private final UserService userService
    private final MessageService messageService
    private final ChatService chatService
    private final AuthorResourceConverter authorConverter
    private final ChatRoomResourceConverter chatConverter

    @Autowired
    ChatController(UserService userService, MessageService messageService, ChatService chatService,
                   AuthorResourceConverter authorConverter, ChatRoomResourceConverter chatConverter,
                   @Qualifier("objectMapper") ObjectMapper mapper) {
        this.userService = userService
        this.messageService = messageService
        this.chatService = chatService
        this.mapper = mapper
        this.authorConverter = authorConverter
        this.chatConverter = chatConverter
    }

    @MessageMapping("/chat")
    @SendTo("/topic/input")
    public OutputResource sendOutput(InputResource input) {
        String data = input.data
        InputType type = input.type
        Date now = new Date()
        Integer id = input.id

        OutputResource output = new OutputResource(id: id, time: now, type: type)
        switch (type) {
            case InputType.MESSAGE:
                InputMessageResource inputMessage = mapper.readValue(data, InputMessageResource)
                Optional<User> authUser = userService.meFromAuthorization(inputMessage.token)
                if (authUser.isPresent()) {
                    User user = authUser.get()
                    String content = inputMessage.message
                    //TODO: Get IP!
                    String ip = "127.0.0.1"

                    Optional<Message> message = messageService.persist(user, content, now, ip)
                    if (message.isPresent()) {
                        chatService.addMessage(message.get(), id)
                    }

                    OutputMessageResource outputMessage = new OutputMessageResource(
                        author: user.id,
                        message: content
                    )

                    output.data = mapper.writeValueAsString(outputMessage)
                }

                break
            case InputType.LEAVE:
            case InputType.JOIN:
                EventResource event = mapper.readValue(data, EventResource)
                Optional<User> user = userService.meFromAuthorization(event.token)
                if (user.isPresent()) {
                    AuthorResource author = authorConverter.fromUser(user.get())

                    output.data = mapper.writeValueAsString(author)
                }

                break
        }

        return output
    }

    @RequestMapping(value = '/join', method = GET, produces = APPLICATION_JSON)
    @ApiOperation(value = "Join user to chat", notes = "Get a ChatRoom info", response = ChatRoomResource, produces = APPLICATION_JSON)
    ResponseEntity<ChatRoomResource> join(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        Optional<User> user = userService.meFromAuthorization(authorization)
        if (user.isPresent()) {
            ChatRoom room = chatService.join(user.get())

            return new ResponseEntity<>(chatConverter.fromChatRoom(room), OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

    @RequestMapping(value = '/leave', method = POST, produces = APPLICATION_JSON)
    @ApiOperation(value = "Make the leave process", notes = "Leave specified user", response = String, produces = APPLICATION_JSON)
    ResponseEntity<String> leave(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        Optional<User> user = userService.meFromAuthorization(authorization)
        if (user.isPresent()) {
            chatService.leave(user.get())

            return new ResponseEntity<>("OK", OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

}
