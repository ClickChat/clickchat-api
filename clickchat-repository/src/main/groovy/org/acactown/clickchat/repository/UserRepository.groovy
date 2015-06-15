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
}
