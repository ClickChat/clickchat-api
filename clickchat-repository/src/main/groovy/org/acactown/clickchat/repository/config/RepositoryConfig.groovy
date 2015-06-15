package org.acactown.clickchat.repository.config

import com.mongodb.Mongo
import com.mongodb.MongoURI
import org.acactown.clickchat.commons.Environment
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

/**
 * @author Andrés Amado
 * @since 2015-06-14
 */
@Configuration
@EnableMongoRepositories('org.acactown.clickchat.repository')
class RepositoryConfig extends AbstractMongoConfiguration {

    @Autowired
    private Environment environment

    private static final String DATABASE_NAME = "DATABASE_NAME"
    private static final String DEFAULT_DATABASE_NAME = "clickchat"
    private static final String DATABASE_URI = "DATABASE_URI"

    @Override
    protected String getDatabaseName() {
        return environment.getPropertyOr(DATABASE_NAME, DEFAULT_DATABASE_NAME)
    }

    @Override
    Mongo mongo() throws Exception {
        String uri = environment.getProperty(DATABASE_URI)

        return new Mongo(new MongoURI(uri))
    }

    @Override
    protected String getMappingBasePackage() {
        return "org.acactown.clickchat.domain"
    }
}
