package org.acactown.clickchat.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableWebSocketMessageBroker
class APIConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Bean
    ResourceBundleMessageSource messageSource() {
        return new ResourceBundleMessageSource(
            basename: "/i18n/messages",
            defaultEncoding: "UTF-8",
            useCodeAsDefaultMessage: true
        )
    }

    @Override
    void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic")
        registry.setApplicationDestinationPrefixes("/app")
    }

    @Override
    void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat").withSockJS()
    }

}
