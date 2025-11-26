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

        // Trim whitespace that might come from Railway UI
        if (uri != null) {
            uri = uri.trim();
        }
        if (dbName != null) {
            dbName = dbName.trim();
        }

        System.out.println("=== MongoDB Initialization ===");
        System.out.println("MONGO_URI from env: [" + uri + "]");
        System.out.println("DB_NAME from env: [" + dbName + "]");
        System.out.println("MONGO_URI null? " + (uri == null));
        System.out.println("MONGO_URI empty? " + (uri != null && uri.isEmpty()));
        System.out.println("DB_NAME null? " + (dbName == null));
        System.out.println("DB_NAME empty? " + (dbName != null && dbName.isEmpty()));

        if (uri != null && !uri.isEmpty() && dbName != null && !dbName.isEmpty()) {
            try {
                client = MongoClients.create(uri);
                System.out.println("✓ MongoDB client initialized successfully!");
            } catch (Exception e) {
                System.err.println("✗ Failed to initialize MongoDB client: " + e.getMessage());
                System.err.println("Exception: " + e.getClass().getSimpleName());
            }
        } else {
            System.err.println("✗ Environment variables not properly set!");
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