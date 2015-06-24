package org.acactown.clickchat.service

import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author

/**
 * @author Andrés Amado
 * @since 2015-06-19
 */
interface AuthorConverter {

    /**
     * Convert from {@link User} to {@link Author}
     * @param user The user
     * @return The {@link Author} from a given {@link User}
     */
    Author toAuthor(User user)

}
