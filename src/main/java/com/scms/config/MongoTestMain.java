package com.scms.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoTestMain {

    public static void main(String[] args) {
        MongoClient client = null;
        try {
            // Connect to MongoDB
            client = MongoClients.create("mongodb://localhost:27017");
            MongoDatabase database = client.getDatabase("student_course_db");

            // Try to list collections to verify connection
            String firstCollection = database.listCollectionNames().first();
            if (firstCollection == null) {
                System.out.println("Connected to MongoDB successfully. No collections yet.");
            } else {
                System.out.println("Connected to MongoDB successfully. First collection: " + firstCollection);
            }

        } catch (Exception e) {
            System.out.println("MongoDB connection failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (client != null) {
                client.close(); // close connection
            }
        }
    }
}
