<!DOCTYPE html>
<html>
<head>
    <title>Register - Student Course Management</title>
</head>
<body>
    <h2>Register</h2>
    <form action="registerUser" method="post">
        Full Name: <input type="text" name="fullName" required/><br/><br/>
        Username: <input type="text" name="username" required/><br/><br/>
        Password: <input type="password" name="password" required/><br/><br/>
        Role:
        <select name="role" required>
            <option value="">Select Role</option>
            <option value="student">Student</option>
            <option value="teacher">Teacher</option>
            <option value="admin">Admin</option>
        </select><br/><br/>
        <input type="submit" value="Register"/>
    </form>
    <p>Already have an account? <a href="login">Login here</a></p>
</body>
</html>
