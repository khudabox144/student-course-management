package com.scms.dao;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.scms.config.MongoConfig;
import com.scms.model.User;



/*
 This class handles all database operations related to User.
 It hides MongoDB implementation from the rest of the app.
*/
public class UserDAO {

    private MongoCollection<Document> usersCollection;

    public UserDAO() {
        MongoDatabase db = MongoConfig.getDatabase();
        usersCollection = db.getCollection("users"); // "users" collection
    }

    // Create a new user
    public void createUser(User user) {
        Document doc = new Document();
        doc.append("username", user.getUsername());
        doc.append("password", user.getPassword());
        doc.append("role", user.getRole());
        doc.append("fullName", user.getFullName());
        usersCollection.insertOne(doc);
        user.setId(doc.getObjectId("_id")); // set id after insert
    }

    // Find user by username
    public User findByUsername(String username) {
        Document doc = usersCollection.find(eq("username", username)).first();
        if (doc == null) return null;

        User user = new User();
        user.setId(doc.getObjectId("_id"));
        user.setUsername(doc.getString("username"));
        user.setPassword(doc.getString("password"));
        user.setRole(doc.getString("role"));
        user.setFullName(doc.getString("fullName"));
        return user;
    }

}
