package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import io.github.cdimascio.dotenv.Dotenv;

/*
 This class provides a MongoDatabase instance.
 It uses a singleton MongoClient to reuse the connection.
 Supports both local .env file and online environment variables.
*/
public class MongoConfig {

    private static MongoClient client;  // Singleton MongoClient
    private static Dotenv dotenv;       // To read local .env file

    public static MongoDatabase getDatabase() {
        if (client == null) {
            String uri;
            String dbName;

            // Try to read environment variables first (for deployment)
            uri = System.getenv("MONGO_URI");
            dbName = System.getenv("DB_NAME");

            // If not set, fallback to local .env file
            if (uri == null || uri.isEmpty() || dbName == null || dbName.isEmpty()) {
                dotenv = Dotenv.configure().ignoreIfMissing().load();
                uri = dotenv.get("MONGO_URI", "mongodb://localhost:27017"); // default local
                dbName = dotenv.get("DB_NAME", "student-course-management");
            }

            client = MongoClients.create(uri);
            return client.getDatabase(dbName);
        }

        // Return existing client
        String dbName = System.getenv("DB_NAME");
        if (dbName == null && dotenv != null) {
            dbName = dotenv.get("DB_NAME", "student-course-management");
        }
        if (dbName == null) dbName = "student-course-management";

        return client.getDatabase(dbName);
    }
}

