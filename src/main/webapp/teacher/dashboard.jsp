<%@ page import="com.scms.model.User" %>
<%@ page import="com.scms.dao.CourseDAO" %>
<%@ page import="com.scms.dao.EnrollmentDAO" %>
<%@ page import="com.scms.model.Course" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.stream.Collectors" %>
<%
    User user = (User) session.getAttribute("user");
    if(user == null || !"teacher".equals(user.getRole())){
        response.sendRedirect("../login");
        return;
    }

    CourseDAO courseDAO = new CourseDAO();
    EnrollmentDAO enrollmentDAO = new EnrollmentDAO();

    // Get courses assigned to this teacher
    List<Course> myCourses = courseDAO.getAllCourses()
                          .stream()
                          .filter(c -> user.getUsername().equals(c.getAssignedTeacher()))
                          .collect(Collectors.toList());
%>

<!DOCTYPE html>
<html>
<head>
    <title>Teacher Dashboard</title>
</head>
<body>
    <h2>Welcome, <%= user.getFullName() %>!</h2>

    <h3>My Courses and Enrolled Students</h3>
    <% for(Course c : myCourses){ %>
        <h4><%= c.getCourseName() %></h4>
        <ul>
            <%
                List<String> students = enrollmentDAO.getStudentsByCourse(c.getId());
                if(students.isEmpty()){
                    out.println("<li>No students enrolled yet</li>");
                } else {
                    for(String s : students){
                        out.println("<li>" + s + "</li>");
                    }
                }
            %>
        </ul>
    <% } %>
</body>
</html>
