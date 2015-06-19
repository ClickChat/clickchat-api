package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface AuthorResourceConverter {

    AuthorResource fromUser(User user)

    AuthorResource fromAuthor(Author author)

}
