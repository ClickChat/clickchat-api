package org.acactown.clickchat.service.config

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@ComponentScan([
    "org.acactown.clickchat.repository.config",
    "org.acactown.clickchat.service"
])
class ServiceConfig {
}
