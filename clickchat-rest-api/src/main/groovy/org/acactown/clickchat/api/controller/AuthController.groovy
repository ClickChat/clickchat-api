package org.acactown.clickchat.api.controller

import com.google.common.base.Optional
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.service.UserDetailResourceConverter
import org.acactown.clickchat.domain.model.Token
import org.acactown.clickchat.api.resource.UserDetailResource
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping

import static org.acactown.clickchat.service.util.RestUtil.APPLICATION_JSON
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.POST
import static org.springframework.web.bind.annotation.RequestMethod.GET

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Controller
@Api(value = "Auth", description = "Auth Operations", produces = APPLICATION_JSON)
class AuthController {

    private final UserService userService
    private final UserDetailResourceConverter converter

    @Autowired
    AuthController(UserService userService, UserDetailResourceConverter converter) {
        this.userService = userService
        this.converter = converter
    }

    @RequestMapping(value = '/auth', method = POST, produces = APPLICATION_JSON)
    @ApiOperation(value = "Make the authentication process", notes = "Make the auth process and returns the UserDetail", response = UserDetailResource, produces = APPLICATION_JSON)
    ResponseEntity<UserDetailResource> auth(
        @ApiParam(value = "The social auth token", required = true) @RequestBody(required = true) Token token
    ) {
        //TODO: Get IP from request!
        String ip = "127.0.0.1"

        Optional<User> user = userService.login(token, ip)
        if (user.isPresent()) {
            UserDetailResource userDetail = converter.fromUser(user.get())

            return new ResponseEntity<>(userDetail, OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

    @RequestMapping(value = '/me', method = GET, produces = APPLICATION_JSON)
    @ApiOperation(value = "Get the User Detail", notes = "Get the UserDetail based on a given token", response = UserDetailResource, produces = APPLICATION_JSON)
    ResponseEntity<UserDetailResource> getUserDetail(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        Optional<User> user = userService.meFromAuthorization(authorization)
        if (user.isPresent()) {
            UserDetailResource userDetail = converter.fromUser(user.get())

            return new ResponseEntity<>(userDetail, OK)
        }

        throw new IllegalStateException("Unauthorized!")
    }

    @RequestMapping(value = '/logout', method = POST, produces = APPLICATION_JSON)
    @ApiOperation(value = "Make the logout process", notes = "Log out the specified user.", response = String, produces = APPLICATION_JSON)
    ResponseEntity<String> logout(
        @ApiParam(value = "The auth header", required = true) @RequestHeader(value = "Authorization", required = true) String authorization
    ) {
        userService.logoutFromAuthorization(authorization)

        return new ResponseEntity<>("OK", OK)
    }

}
