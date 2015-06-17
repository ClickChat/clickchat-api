package org.acactown.clickchat.service.config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.acactown.clickchat.service.client.ApiClientResponseHandler
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.http.client.ClientHttpRequestFactory
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate

import java.util.concurrent.Executor

import static com.fasterxml.jackson.annotation.JsonInclude.Include

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableAsync
@EnableAspectJAutoProxy
@ComponentScan([
    "org.acactown.clickchat.commons.config",
    "org.acactown.clickchat.cache.config",
    "org.acactown.clickchat.repository.config",
    "org.acactown.clickchat.service"
])
class ServiceConfig implements AsyncConfigurer {

    private int corePoolSize = 5
    private int maxPoolSize = 25
    private int queueCapacity = 500
    private int connectTimeout = 10000
    private int readTimeout = 30000

    @Override
    Executor getAsyncExecutor() {
        Executor executor = new ThreadPoolTaskExecutor(
            corePoolSize: corePoolSize,
            maxPoolSize: maxPoolSize,
            queueCapacity: queueCapacity,
            threadNamePrefix: "service-executor-"
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

    @Bean(name = "googleRequestFactory")
    ClientHttpRequestFactory googleRequestFactory() {
        return new SimpleClientHttpRequestFactory(
            connectTimeout: connectTimeout,
            readTimeout: readTimeout
        )
    }

    @Bean(name = "googleRestTemplate")
    public RestTemplate googleRestTemplate(
        @Qualifier("googleRequestFactory") ClientHttpRequestFactory requestFactory,
        @Qualifier("messageConverter") MappingJackson2HttpMessageConverter converter) {

        RestTemplate restTemplate = new RestTemplate(
            requestFactory: requestFactory,
            errorHandler: new ApiClientResponseHandler()
        )
        restTemplate.messageConverters.add(0, converter)

        return restTemplate
    }

}
