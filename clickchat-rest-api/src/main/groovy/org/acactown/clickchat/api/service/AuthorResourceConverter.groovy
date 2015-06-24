package org.acactown.clickchat.api.service

import org.acactown.clickchat.api.resource.AuthorResource
import org.acactown.clickchat.domain.User
import org.acactown.clickchat.domain.model.Author

/**
 * @author Andrés Amado
 * @since 2015-06-17
 */
interface AuthorResourceConverter {

    /**
     * Convert from {@link User} to {@link AuthorResource}
     * @param user The user
     * @return The {@link AuthorResource} from a given {@link User}
     */
    AuthorResource fromUser(User user)

    /**
     * Convert from {@link Author} to {@link AuthorResource}
     * @param author The author
     * @return The {@link AuthorResource} from a given {@link Author}
     */
    AuthorResource fromAuthor(Author author)

}
