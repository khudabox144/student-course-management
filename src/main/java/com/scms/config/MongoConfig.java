package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

/*
 This class is responsible for providing a MongoDatabase instance.
 We create a single MongoClient and reuse it, instead of creating a new connection every time.
*/
public class MongoConfig {

    private static MongoClient client;  // Singleton MongoClient

    public static MongoDatabase getDatabase() {
        if (client == null) {
            // Connect to MongoDB running locally on default port
            client = MongoClients.create("mongodb://localhost:27017");
        }
        // Use (or create if not exists) database "student_course_db"
        return client.getDatabase("student_course_db");
    }
}
