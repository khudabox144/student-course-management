package com.scms.dao;

import com.scms.config.MongoConfig;
import com.scms.model.Enrollment;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

/*
 Handles all database operations related to enrollments.
*/
public class EnrollmentDAO {

    private MongoCollection<Document> enrollmentCollection;

    public EnrollmentDAO() {
        MongoDatabase db = MongoConfig.getDatabase();
        enrollmentCollection = db.getCollection("enrollments");
    }

    // Enroll a student in a course
    public void enrollStudent(Enrollment enrollment) {
        Document doc = new Document();
        doc.append("studentUsername", enrollment.getStudentUsername());
        doc.append("courseId", enrollment.getCourseId());
        enrollmentCollection.insertOne(doc);
        enrollment.setId(doc.getObjectId("_id"));
    }

    // Get all courses a student is enrolled in
    public List<Enrollment> getEnrollmentsByStudent(String studentUsername) {
        List<Enrollment> enrollments = new ArrayList<>();
        for (Document doc : enrollmentCollection.find(eq("studentUsername", studentUsername))) {
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
        for (Document doc : enrollmentCollection.find(eq("courseId", courseId))) {
            students.add(doc.getString("studentUsername"));
        }
        return students;
    }
}
