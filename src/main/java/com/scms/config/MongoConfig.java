package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConfig {

    private static MongoClient client;
    private static String dbName;
    private static boolean initialized = false;

    public static MongoDatabase getDatabase() {
        if (!initialized) {
            initialize();
        }

        if (client == null) {
            throw new RuntimeException("MongoDB client not initialized. Please set MONGO_URI and DB_NAME environment variables.");
        }

        return client.getDatabase(dbName);
    }

    private static synchronized void initialize() {
        if (initialized) {
            return;
        }

        String uri = System.getenv("MONGO_URI");
        dbName = System.getenv("DB_NAME");

        if (uri != null && !uri.isEmpty() && dbName != null && !dbName.isEmpty()) {
            try {
                client = MongoClients.create(uri);
                System.out.println("MongoDB client initialized successfully.");
            } catch (Exception e) {
                System.err.println("Failed to initialize MongoDB client: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.err.println("MONGO_URI: " + uri);
            System.err.println("DB_NAME: " + dbName);
        }

        initialized = true;
    }

    public static boolean isConnected() {
        if (!initialized) {
            initialize();
        }
        return client != null;
    }

}