package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {

    private static MongoClient client;

    public static MongoDatabase getDatabase() {
        if (client == null) {
            // Read environment variables
            String uri = System.getenv("MONGO_URI");
            String dbName = System.getenv("DB_NAME");

            if (uri == null || uri.isEmpty() || dbName == null || dbName.isEmpty()) {
                throw new RuntimeException("Environment variables MONGO_URI or DB_NAME not set!");
            }

            client = MongoClients.create(uri);
            return client.getDatabase(dbName);
        }

        String dbName = System.getenv("DB_NAME");
        return client.getDatabase(dbName);
    }

}