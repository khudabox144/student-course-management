<%@ page import="com.scms.dao.CourseDAO" %>
<%@ page import="com.scms.model.Course" %>
<%@ page import="java.util.List" %>
<%
    CourseDAO courseDAO = new CourseDAO();
    List<Course> courses = courseDAO.getAllCourses();
%>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard - Student Course Management</title>
</head>
<body>
    <h2>Admin Dashboard</h2>

    <h3>Add New Course</h3>
    <form action="addCourse" method="post">
        Course Name: <input type="text" name="courseName" required/>
        Assign Teacher Username: <input type="text" name="teacherUsername" required/>
        <input type="submit" value="Add Course"/>
    </form>

    <h3>Existing Courses</h3>
    <table border="1">
        <tr>
            <th>Course Name</th>
            <th>Assigned Teacher</th>
            <th>Action</th>
        </tr>
        <% for(Course c : courses){ %>
            <tr>
                <td><%= c.getCourseName() %></td>
                <td><%= c.getAssignedTeacher() %></td>
                <td>
                    <form action="deleteCourse" method="post" style="display:inline;">
                        <input type="hidden" name="id" value="<%= c.getId() %>"/>
                        <input type="submit" value="Delete"/>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>
</body>
</html>
