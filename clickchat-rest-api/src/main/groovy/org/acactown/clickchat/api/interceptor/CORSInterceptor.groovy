package org.acactown.clickchat.api.interceptor

import com.google.common.base.Strings

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.http.HttpMethod
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
class CORSInterceptor extends HandlerInterceptorAdapter {

    private static final String LOCATION = "Location"
    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"
    private static final String ACCESS_CONTROL_ALLOW_METHODS = "Access-Control-Allow-Methods"
    private static final String ACCESS_CONTROL_ALLOW_HEADERS = "Access-Control-Allow-Headers"
    private static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers"
    private static final String ACCESS_CONTROL_REQUEST_METHOD = "Access-Control-Request-Method"
    private static final String ACCESS_CONTROL_REQUEST_HEADERS = "Access-Control-Request-Headers"
    private static final String ACCESS_CONTROL_MAX_AGE = "Access-Control-Max-Age"
    private static final String CACHE_SECONDS = "300"

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String acRequestMethod = request.getHeader(ACCESS_CONTROL_REQUEST_METHOD)
        String acRequestHeaders = request.getHeader(ACCESS_CONTROL_REQUEST_HEADERS)

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*")

        if (HttpMethod.OPTIONS.toString().equals(request.getMethod()) && !Strings.isNullOrEmpty(acRequestMethod)) {
            response.addHeader(ACCESS_CONTROL_ALLOW_METHODS, HttpMethod.valueOf(acRequestMethod.toUpperCase()).toString())
            response.setHeader(ACCESS_CONTROL_ALLOW_HEADERS, acRequestHeaders)
            response.setHeader(ACCESS_CONTROL_MAX_AGE, CACHE_SECONDS)

            return false
        } else {
            response.addHeader(ACCESS_CONTROL_EXPOSE_HEADERS, LOCATION)
        }

        return true
    }

}
