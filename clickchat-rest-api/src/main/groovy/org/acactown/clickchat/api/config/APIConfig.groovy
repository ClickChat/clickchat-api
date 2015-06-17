package org.acactown.clickchat.api.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.context.support.ResourceBundleMessageSource

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableAspectJAutoProxy
class APIConfig {

    @Bean
    ResourceBundleMessageSource messageSource() {
        return new ResourceBundleMessageSource(
            basename: "/i18n/messages",
            defaultEncoding: "UTF-8",
            useCodeAsDefaultMessage: true
        )
    }

}
