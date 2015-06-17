package org.acactown.clickchat.service.util

import org.springframework.http.HttpStatus

import static org.springframework.http.HttpStatus.*
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
class RestUtil {

    public static final String APPLICATION_JSON = 'application/json;charset=UTF-8'

    private static final List<HttpStatus> ACCEPTED_STATUS = Arrays.asList(
        OK,
        ACCEPTED,
        PRECONDITION_FAILED,
        EXPECTATION_FAILED,
        NOT_FOUND,
        BAD_REQUEST
    )

    static boolean isError(HttpStatus status) {
        if (ACCEPTED_STATUS.contains(status)) {
            return false
        }

        Series series = status.series()

        return (CLIENT_ERROR.equals(series) || SERVER_ERROR.equals(series))
    }
    
}
