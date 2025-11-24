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
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="min-h-screen bg-gray-100">
    <div class="max-w-4xl mx-auto p-6">
        <div class="bg-white shadow rounded-lg p-6 mb-6">
            <h2 class="text-2xl font-semibold">Welcome, <%= user.getFullName() %>!</h2>
            <p class="text-sm text-gray-600 mt-1">Manage your enrollments and view available courses below.</p>
        </div>

        <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div class="bg-white shadow rounded-lg p-6">
                <h3 class="text-lg font-semibold mb-4">Enroll in a Course</h3>
                <form action="enrollCourse" method="post">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Select Course</label>
                    <select name="courseId" required class="block w-full border rounded-md px-3 py-2 mb-4">
                        <% for(Course c : allCourses){ %>
                            <option value="<%= c.getId() %>"><%= c.getCourseName() %> (Teacher: <%= c.getAssignedTeacher() %>)</option>
                        <% } %>
                    </select>
                    <button type="submit" class="w-full bg-blue-600 text-white py-2 rounded-md hover:bg-blue-700">Enroll</button>
                </form>
            </div>

            <div class="bg-white shadow rounded-lg p-6">
                <h3 class="text-lg font-semibold mb-4">My Enrollments</h3>
                <ul class="list-disc pl-5 text-gray-800">
                    <% for(Enrollment e : myEnrollments){
                            Course c = courseDAO.getAllCourses().stream()
                                    .filter(course -> course.getId().equals(e.getCourseId()))
                                    .findFirst().orElse(null);
                            if(c != null){ %>
                                <li class="mb-2"><%= c.getCourseName() %> <span class="text-sm text-gray-500">(Teacher: <%= c.getAssignedTeacher() %>)</span></li>
                    <%      }
                       } %>
                </ul>
            </div>
        </div>
    </div>
</body>
</html>
