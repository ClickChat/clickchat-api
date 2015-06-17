package org.acactown.clickchat.cache.config

import org.acactown.clickchat.commons.Environment
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory
import org.springframework.data.redis.core.StringRedisTemplate
import redis.clients.jedis.JedisPoolConfig

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Configuration
@ComponentScan([
    "org.acactown.clickchat.cache"
])
class CacheConfig {

    private int maximumConnections = 20
    private int minimumIdleConnections = 0
    private int maximumIdleConnections = 5

    private static final String REDIS_HOST = "REDIS_HOST"
    private static final String DEFAULT_REDIS_HOST = "localhost"
    private static final String REDIS_PORT = "REDIS_PORT"
    private static final String DEFAULT_REDIS_PORT = "6379"
    private static final String MD5 = "MD5"

    @Bean
    public MessageDigest md5Digest() throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(MD5)
    }

    @Bean
    public JedisPoolConfig jedisPoolConfig() {
        return new JedisPoolConfig(
            maxTotal: maximumConnections,
            maxIdle: maximumIdleConnections,
            minIdle: minimumIdleConnections,
            blockWhenExhausted: false,
            testOnBorrow: false
        )
    }

    @Bean
    RedisConnectionFactory nodesRedisConnectionFactory(JedisPoolConfig config, Environment environment) {
        return new JedisConnectionFactory(
            usePool: true,
            hostName: environment.getPropertyOr(REDIS_HOST, DEFAULT_REDIS_HOST),
            port: Integer.parseInt(environment.getPropertyOr(REDIS_PORT, DEFAULT_REDIS_PORT)),
            database: 0,
            poolConfig: config
        )
    }

    @Bean
    StringRedisTemplate nodesRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new StringRedisTemplate(
            connectionFactory: connectionFactory,
            enableTransactionSupport: true
        )
    }

}
