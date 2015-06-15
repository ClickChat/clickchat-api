package org.acactown.clickchat.api.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor

import java.util.concurrent.Executor

import static com.fasterxml.jackson.annotation.JsonInclude.Include

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableAsync
class APIConfig implements AsyncConfigurer {
    
    @Bean
    ResourceBundleMessageSource messageSource() {
        return new ResourceBundleMessageSource(
            basename: "/i18n/messages",
            defaultEncoding: "UTF-8",
            useCodeAsDefaultMessage: true
        )
    }

    @Override
    Executor getAsyncExecutor() {
        Executor executor = new ThreadPoolTaskExecutor(
            corePoolSize: 5,
            maxPoolSize: 25,
            queueCapacity: 500,
            threadNamePrefix: "api-executor-"
        )
        executor.initialize()

        return executor
    }

    @Override
    AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new SimpleAsyncUncaughtExceptionHandler();
    }

    @Bean(name = "objectMapper")
    ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper(serializationInclusion: Include.NON_NULL)
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        return mapper
    }

    @Bean(name = "messageConverter")
    MappingJackson2HttpMessageConverter messageConverter(@Qualifier("objectMapper") ObjectMapper mapper) {
        return new MappingJackson2HttpMessageConverter(
            objectMapper: mapper
        )
    }

}
