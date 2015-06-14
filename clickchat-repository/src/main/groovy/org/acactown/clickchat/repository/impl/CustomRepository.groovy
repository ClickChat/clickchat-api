package org.acactown.clickchat.repository.impl

import org.springframework.data.mongodb.repository.MongoRepository

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
interface CustomRepository<T> extends MongoRepository<T, String> {
}
