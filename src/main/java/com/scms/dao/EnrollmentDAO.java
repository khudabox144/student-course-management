package com.scms.dao;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import com.scms.config.MongoConfig;
import com.scms.model.Enrollment;

/*
 Handles all database operations related to enrollments.
*/
public class EnrollmentDAO {

    private MongoCollection<Document> enrollmentCollection;

    public EnrollmentDAO() {
        // Initialize lazily on first use
        enrollmentCollection = null;
    }

    private MongoCollection<Document> getCollection() {
        if (enrollmentCollection == null) {
            MongoDatabase db = MongoConfig.getDatabase();
            enrollmentCollection = db.getCollection("enrollments");
        }
        return enrollmentCollection;
    }

    // Enroll a student in a course
    public void enrollStudent(Enrollment enrollment) {
        Document doc = new Document();
        doc.append("studentUsername", enrollment.getStudentUsername());
        doc.append("courseId", enrollment.getCourseId());
        getCollection().insertOne(doc);
        enrollment.setId(doc.getObjectId("_id"));
    }

    // Get all courses a student is enrolled in
    public List<Enrollment> getEnrollmentsByStudent(String studentUsername) {
        List<Enrollment> enrollments = new ArrayList<>();
        for (Document doc : getCollection().find(eq("studentUsername", studentUsername))) {
            Enrollment e = new Enrollment();
            e.setId(doc.getObjectId("_id"));
            e.setStudentUsername(doc.getString("studentUsername"));
            e.setCourseId(doc.getObjectId("courseId"));
            enrollments.add(e);
        }
        return enrollments;
    }

    // Get all students in a course
    public List<String> getStudentsByCourse(ObjectId courseId) {
        List<String> students = new ArrayList<>();
        for (Document doc : getCollection().find(eq("courseId", courseId))) {
            students.add(doc.getString("studentUsername"));
        }
        return students;
    }
}
