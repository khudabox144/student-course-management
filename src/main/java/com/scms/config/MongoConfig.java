package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {

    private static MongoClient client;
    private static final String DB_NAME;

    static {
        // Initialize environment variables at class load time
        String uri = System.getenv("MONGO_URI");
        String tmpDbName = System.getenv("DB_NAME");
        DB_NAME = tmpDbName;

        if (uri != null && !uri.isEmpty() && DB_NAME != null && !DB_NAME.isEmpty()) {
            try {
                client = MongoClients.create(uri);
            } catch (Exception e) {
                System.err.println("Failed to initialize MongoDB client: " + e.getMessage());
            }
        }
    }

    public static MongoDatabase getDatabase() {
        if (client == null) {
            throw new RuntimeException("MongoDB client not initialized. Please set MONGO_URI and DB_NAME environment variables.");
        }
        return client.getDatabase(DB_NAME);
    }

    public static boolean isConnected() {
        return client != null;
    }

}