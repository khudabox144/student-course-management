package com.scms.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.scms.config.MongoConfig;
import com.scms.model.User;

public class UserDAO {

    private MongoCollection<Document> usersCollection;

    public UserDAO() {
        MongoDatabase db = MongoConfig.getDatabase();
        usersCollection = db.getCollection("users");
    }

    // Create a new user
    public boolean createUser(User user) {
        if (findByUsername(user.getUsername()) != null) {
            return false;
        }

        Document doc = new Document();
        doc.append("username", user.getUsername());
        doc.append("password", user.getPassword());
        doc.append("role", user.getRole());
        doc.append("fullName", user.getFullName());

        usersCollection.insertOne(doc);
        user.setId(doc.getObjectId("_id"));
        return true;
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

    // ⭐ NEW METHOD — FETCH TEACHERS ONLY
    public List<User> getAllTeachers() {
        List<User> teachers = new ArrayList<>();

        for (Document doc : usersCollection.find(eq("role", "teacher"))) {
            User user = new User();
            user.setId(doc.getObjectId("_id"));
            user.setUsername(doc.getString("username"));
            user.setPassword(doc.getString("password"));
            user.setRole(doc.getString("role"));
            user.setFullName(doc.getString("fullName"));
            teachers.add(user);
        }

        return teachers;
    }
}
