package org.acactown.clickchat.repository

import org.acactown.clickchat.domain.User
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Repository
interface UserRepository extends MongoRepository<User, String> {

    /**
     * Find a {@link User} from given external ID
     * @param externalId The external ID
     * @return a {@link User} related to the given external ID
     */
    User findByExternalId(String externalId)

}
