package org.acactown.clickchat.api.interceptor

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
class ResponseHeadersInterceptor extends HandlerInterceptorAdapter {

    private static final String ACCESS_CONTROL_ALLOW_ORIGIN = "Access-Control-Allow-Origin"

    @Override
    void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                    ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView)

        response.setHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*")
    }
}
