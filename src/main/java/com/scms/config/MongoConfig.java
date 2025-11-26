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

        // Debugging output
        System.out.println("MONGO_URI = " + uri);
        System.out.println("DB_NAME = " + dbName);

        if (uri == null || uri.isEmpty() || dbName == null || dbName.isEmpty()) {
            throw new RuntimeException("Environment variables MONGO_URI or DB_NAME not set!");
        }

        client = MongoClients.create(uri);
        System.out.println("MongoDB client created successfully.");
        return client.getDatabase(dbName);
    }

    String dbName = System.getenv("DB_NAME");
    System.out.println("Using existing MongoDB client with DB_NAME = " + dbName);
    return client.getDatabase(dbName);
}

}