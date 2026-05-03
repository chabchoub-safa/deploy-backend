package com.example.PFE.config;


import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

@Configuration
public class MongoForceConfig {

    @Bean
    @Primary
    public MongoClient mongoClient(@Value("${spring.data.mongodb.uri}") String uri) {
        return MongoClients.create(new ConnectionString(uri));
    }

    @Bean
    @Primary
    public MongoDatabaseFactory mongoDatabaseFactory(
            MongoClient mongoClient,
            @Value("${spring.data.mongodb.database}") String dbName
    ) {
        return new SimpleMongoClientDatabaseFactory(mongoClient, dbName);
    }

    @Bean
    @Primary
    public MongoTemplate mongoTemplate(MongoDatabaseFactory factory) {
        return new MongoTemplate(factory);
    }
}
