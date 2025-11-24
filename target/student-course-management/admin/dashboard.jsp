<%@ page import="com.scms.dao.CourseDAO" %>
<%@ page import="com.scms.dao.UserDAO" %>
<%@ page import="com.scms.model.Course" %>
<%@ page import="com.scms.model.User" %>
<%@ page import="java.util.List" %>

<%
    CourseDAO courseDAO = new CourseDAO();
    UserDAO userDAO = new UserDAO();
    List<Course> courses = courseDAO.getAllCourses();
    List<User> teachers = userDAO.getAllTeachers();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-10">

<h1 class="text-3xl font-bold mb-6">Admin Dashboard</h1>

<!-- Add Course Section -->
<div class="bg-white p-8 rounded-xl shadow-md mb-8 max-w-2xl">

    <h2 class="text-xl font-semibold mb-4">Add New Course</h2>

    <form action="addCourse" method="post" class="space-y-5">

        <div>
            <label class="block font-semibold">Course Name</label>
            <input type="text" name="courseName"
                   class="w-full p-2 border rounded-lg" required>
        </div>

        <div>
            <label class="block font-semibold">Select Teacher</label>
            <select name="teacherUsername"
                    class="w-full p-2 border rounded-lg" required>
                <option value="">-- Select Teacher --</option>
                <% for(User t : teachers){ %>
                    <option value="<%= t.getUsername() %>">
                        <%= t.getFullName() %> (<%= t.getUsername() %>)
                    </option>
                <% } %>
            </select>
        </div>

        <button
            type="submit"
            class="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded-lg">
            Add Course
        </button>

    </form>
</div>

<!-- Show Existing Courses -->
<div class="bg-white p-6 rounded-xl shadow-md max-w-3xl">
    <h2 class="text-xl font-semibold mb-4">Existing Courses</h2>

    <table class="w-full border">
        <tr class="bg-gray-200">
            <th class="p-2">Course Name</th>
            <th class="p-2">Teacher</th>
            <th class="p-2">Action</th>
        </tr>

        <% for(Course c : courses){ %>
        <tr class="border-b">
            <td class="p-2"><%= c.getCourseName() %></td>
            <td class="p-2"><%= c.getAssignedTeacher() %></td>
            <td class="p-2">

                <form action="deleteCourse" method="post">
                    <input type="hidden" name="id" value="<%= c.getId() %>">
                    <button class="bg-red-600 text-white px-4 py-1 rounded">
                        Delete
                    </button>
                </form>

            </td>
        </tr>
        <% } %>

    </table>

</div>

</body>
</html>
