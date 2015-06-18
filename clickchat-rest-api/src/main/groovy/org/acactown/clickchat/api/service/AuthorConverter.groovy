package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.domain.User

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface AuthorConverter {

    AuthorResource toResource(User user)

}
