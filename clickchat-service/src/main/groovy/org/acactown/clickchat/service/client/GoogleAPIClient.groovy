package org.acactown.clickchat.service.client

import com.google.common.base.Optional
import org.acactown.clickchat.commons.Token
import org.acactown.clickchat.service.model.GoogleUserInfo

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface GoogleAPIClient {

    /**
     * Get User Profile Info from Google+
     *
     * @param token The access token
     * @return The Optional{@link GoogleUserInfo} after consult Google's API
     */
    Optional<GoogleUserInfo> getUserInfo(Token token)

}
