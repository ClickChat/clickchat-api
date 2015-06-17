package org.acactown.clickchat.api.controller

import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.ErrorResource
import org.springframework.beans.TypeMismatchException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

import static org.springframework.http.HttpStatus.*

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Slf4j
@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler([IllegalArgumentException, TypeMismatchException])
    ResponseEntity<ErrorResource> badRequest(Exception e) {
        log.error("Bad request!", e)

        return new ResponseEntity<>(new ErrorResource(error: e.getMessage(), code: 400), BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException)
    ResponseEntity<ErrorResource> unauthorized(Exception e) {
        log.error("Unauthorized error!", e)

        return new ResponseEntity<>(new ErrorResource(error: e.getMessage(), code: 401), UNAUTHORIZED)
    }

    @ExceptionHandler(ClassNotFoundException)
    ResponseEntity<ErrorResource> notFound(Exception e) {
        log.error("Not found!", e)

        return new ResponseEntity<>(new ErrorResource(error: e.getMessage(), code: 404), NOT_FOUND)
    }

    @ExceptionHandler(Exception)
    ResponseEntity<ErrorResource> exception(Exception e) {
        log.error("Unhandled error!", e)

        return new ResponseEntity<>(new ErrorResource(error: e.getMessage(), code: 500), INTERNAL_SERVER_ERROR)
    }

}
