package org.acactown.clickchat.repository

import org.acactown.clickchat.domain.User
import org.acactown.clickchat.repository.impl.CustomRepository
import org.springframework.stereotype.Repository

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Repository
interface UserRepository extends CustomRepository<User> {
}
