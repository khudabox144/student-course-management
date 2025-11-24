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
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-gray-100">
    <div class="max-w-4xl mx-auto p-6">
        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-2xl font-semibold">Welcome, <%= user.getFullName() %>!</h2>
            <p class="text-sm text-gray-600 mt-1">Overview of your courses and enrolled students.</p>
        </div>

        <div class="space-y-6">
            <h3 class="text-xl font-semibold">My Courses and Enrolled Students</h3>
            <% for(Course c : myCourses){ %>
                <div class="bg-white shadow rounded-lg p-4">
                    <div class="flex items-center justify-between mb-2">
                        <h4 class="text-lg font-medium"><%= c.getCourseName() %></h4>
                    </div>
                    <ul class="list-disc pl-5 text-gray-800">
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
                </div>
            <% } %>
        </div>
    </div>
</body>
</html>
