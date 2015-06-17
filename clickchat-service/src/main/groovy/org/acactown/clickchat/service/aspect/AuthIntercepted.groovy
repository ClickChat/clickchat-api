package org.acactown.clickchat.service.aspect

import java.lang.annotation.Retention
import java.lang.annotation.Target

import static java.lang.annotation.ElementType.METHOD
import static java.lang.annotation.RetentionPolicy.RUNTIME

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Retention(RUNTIME)
@Target(METHOD)
@interface AuthIntercepted {
}
