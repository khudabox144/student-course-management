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
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 min-h-screen">
    <div class="container mx-auto p-6">
        <h1 class="text-3xl font-bold mb-6 text-center text-gray-800">Admin Dashboard</h1>

        <!-- Add Course Form -->
        <div class="bg-white shadow-md rounded-lg p-6 mb-8">
            <h2 class="text-xl font-semibold mb-4 text-gray-700">Add New Course</h2>
            <form action="addCourse" method="post" class="space-y-4">
                <div>
                    <label class="block text-gray-600 font-medium mb-1">Course Name</label>
                    <input type="text" name="courseName" required
                           class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                </div>
                <div>
                    <label class="block text-gray-600 font-medium mb-1">Assign Teacher Username</label>
                    <input type="text" name="teacherUsername" required
                           class="w-full border border-gray-300 rounded px-3 py-2 focus:outline-none focus:ring-2 focus:ring-blue-500"/>
                </div>
                <div>
                    <button type="submit"
                            class="bg-blue-500 text-white font-semibold px-4 py-2 rounded hover:bg-blue-600 transition">
                        Add Course
                    </button>
                </div>
            </form>
        </div>

        <!-- Existing Courses Table -->
        <div class="bg-white shadow-md rounded-lg p-6">
            <h2 class="text-xl font-semibold mb-4 text-gray-700">Existing Courses</h2>
            <div class="overflow-x-auto">
                <table class="min-w-full border border-gray-300">
                    <thead class="bg-gray-200">
                    <tr>
                        <th class="text-left px-4 py-2 border-b">Course Name</th>
                        <th class="text-left px-4 py-2 border-b">Assigned Teacher</th>
                        <th class="text-left px-4 py-2 border-b">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% for(Course c : courses){ %>
                        <tr class="hover:bg-gray-50">
                            <td class="px-4 py-2 border-b"><%= c.getCourseName() %></td>
                            <td class="px-4 py-2 border-b"><%= c.getAssignedTeacher() %></td>
                            <td class="px-4 py-2 border-b">
                                <form action="deleteCourse" method="post" class="inline">
                                    <input type="hidden" name="id" value="<%= c.getId() %>"/>
                                    <button type="submit"
                                            class="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600 transition">
                                        Delete
                                    </button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</body>
</html>
