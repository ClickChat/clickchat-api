package org.acactown.clickchat.api.controller

import com.wordnik.swagger.annotations.Api
import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.MessageResource
import org.acactown.clickchat.api.resource.OutputMessageResource
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.stereotype.Controller

import static org.acactown.clickchat.service.util.RestUtil.APPLICATION_JSON

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Controller
@Api(value = "Chat", description = "Chat Operations", produces = APPLICATION_JSON)
class ChatController {

    @MessageMapping("/chat")
    @SendTo("/topic/message")
    public OutputMessageResource sendMessage(MessageResource message) {
        return new OutputMessageResource(id: message.id, message: message.message, time: new Date())
    }

}
