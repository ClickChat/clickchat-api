package org.acactown.clickchat.api.controller

import com.google.common.base.Optional
import com.wordnik.swagger.annotations.Api
import com.wordnik.swagger.annotations.ApiOperation
import com.wordnik.swagger.annotations.ApiParam
import groovy.util.logging.Slf4j
import org.acactown.clickchat.api.resource.TokenResource
import org.acactown.clickchat.api.resource.UserDetailResource
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping

import static org.acactown.clickchat.service.util.RestUtil.APPLICATION_JSON
import static org.springframework.http.HttpStatus.OK
import static org.springframework.web.bind.annotation.RequestMethod.POST

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
@Slf4j
@Controller
@Api(value = "Auth", description = "Auth Operations", produces = APPLICATION_JSON)
class AuthController {

    private final UserService userService

    @Autowired
    AuthController(UserService userService) {
        this.userService = userService
    }

    @RequestMapping(value = '/auth', method = POST, produces = APPLICATION_JSON)
    @ApiOperation(value = "Check if a Block exists from the given Id", notes = "Returns a Exists entity. Ever returns a 200 HTTP status code", response = UserDetailResource, produces = APPLICATION_JSON)
    ResponseEntity<UserDetailResource> getUserDetail(
        @ApiParam(value = "Listing alert notification request fields", required = true) @RequestBody(required = true) TokenResource token
    ) {
        //TODO: Get IP from request!
        String ip = "127.0.0.1"

        Optional<User> authUser = userService.authUser(token.accessToken, token.tokenType, ip)
        if (authUser.isPresent()) {
            User user = authUser.get()
            UserDetailResource userDetail = new UserDetailResource(
                token: user.token,
                name: user.name,
                email: user.email,
                thumbnail: user.thumbnail
            )

            return new ResponseEntity<>(userDetail, OK)
        }

        throw new IllegalAccessError("Unauthorized!")
    }

}
