<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>DriveSure - Register</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/style.css">
    <script>
        function calculateAge() {
            const dob = document.getElementById("dob").value;
            if (dob) {
                const today = new Date();
                const birthDate = new Date(dob);
                let age = today.getFullYear() - birthDate.getFullYear();
                const m = today.getMonth() - birthDate.getMonth();
                if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
                    age--;
                }
                document.getElementById("age").value = age;
            }
        }
    </script>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="../index.jsp">DriveSure</a>
        </div>
    </nav>

    <div class="container mt-5 mb-4">
        <div class="row justify-content-center">
            <div class="col-md-6">
                <div class="card shadow p-4">
                    <h3 class="text-center mb-3">User Registration</h3>
                    <form action="/register" method="post">
                        <div class="mb-3">
                            <label for="name">Full Name</label>
                            <input type="text" class="form-control" name="name" required>
                        </div>
                        <div class="mb-3">
                            <label for="email">Email Address</label>
                            <input type="email" class="form-control" name="email" required>
                        </div>
                        <div class="mb-3">
                            <label for="password">Password</label>
                            <input type="password" class="form-control" name="password" required>
                        </div>
                        <div class="mb-3">
                            <label for="dob">Date of Birth</label>
                            <input type="date" class="form-control" id="dob" name="dob" onchange="calculateAge()" required>
                        </div>
                        <div class="mb-3">
                            <label for="age">Age</label>
                            <input type="number" class="form-control" id="age" name="age" readonly required>
                        </div>
                        <div class="mb-3">
                            <label for="aadhaar">Aadhaar Number</label>
                            <input type="text" class="form-control" name="aadhaar" required>
                        </div>
                        <div class="mb-3">
                            <label for="pan">PAN Number</label>
                            <input type="text" class="form-control" name="pan" required>
                        </div>
                        <div class="d-grid">
                            <button type="submit" class="btn btn-success">Register</button>
                        </div>
                        <div class="mt-3 text-center">
                            <a href="login.jsp">Already have an account? Login</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="../js/script.js"></script>
</body>
</html>
