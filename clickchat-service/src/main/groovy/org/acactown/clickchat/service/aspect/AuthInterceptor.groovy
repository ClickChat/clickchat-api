package org.acactown.clickchat.service.aspect

import com.google.common.base.Optional
import groovy.util.logging.Slf4j
import org.acactown.clickchat.cache.AuthUserRepository
import org.acactown.clickchat.domain.User
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Service
@Aspect
class AuthInterceptor {

    private final AuthUserRepository authRepository

    @Autowired
    AuthInterceptor(AuthUserRepository authRepository) {
        this.authRepository = authRepository
    }

    @Around("execution(* org.acactown.clickchat.service.UserService.*(..))")
    private Object afterReturning(ProceedingJoinPoint joinPoint) {
        Object result = joinPoint.proceed()
        if (result) {
            Optional<User> user = (result as Optional<User>)
            if (user.isPresent()) {
                result = authRepository.insertAuthUser(user.get())
            }
        }

        return result
    }

}
