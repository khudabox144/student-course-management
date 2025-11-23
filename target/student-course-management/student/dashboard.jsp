<%@ page import="com.scms.dao.CourseDAO" %>
<%@ page import="com.scms.dao.EnrollmentDAO" %>
<%@ page import="com.scms.model.Course" %>
<%@ page import="com.scms.model.Enrollment" %>
<%@ page import="com.scms.model.User" %>
<%@ page import="java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null || !"student".equals(user.getRole())){
        response.sendRedirect("../login");
        return;
    }

    CourseDAO courseDAO = new CourseDAO();
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    List<Course> allCourses = courseDAO.getAllCourses();
    List<Enrollment> myEnrollments = enrollmentDAO.getEnrollmentsByStudent(user.getUsername());
%>
<!DOCTYPE html>
<html>
<head>
    <title>Student Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= user.getFullName() %>!</h2>

    <h3>Enroll in a Course</h3>
    <form action="enrollCourse" method="post">
        <select name="courseId" required>
            <% for(Course c : allCourses){ %>
                <option value="<%= c.getId() %>"><%= c.getCourseName() %> (Teacher: <%= c.getAssignedTeacher() %>)</option>
            <% } %>
        </select>
        <input type="submit" value="Enroll"/>
    </form>

    <h3>My Enrollments</h3>
    <ul>
        <% for(Enrollment e : myEnrollments){
                Course c = courseDAO.getAllCourses().stream()
                        .filter(course -> course.getId().equals(e.getCourseId()))
                        .findFirst().orElse(null);
                if(c != null){ %>
                    <li><%= c.getCourseName() %> (Teacher: <%= c.getAssignedTeacher() %>)</li>
        <%      }
           } %>
    </ul>
</body>
</html>
