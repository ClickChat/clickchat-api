package org.acactown.clickchat.service

import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface AuthorConverter {

    Author toAuthor(User user)

}
