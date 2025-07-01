<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>DriveSure - Vehicle Insurance</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">DriveSure</a>
            <div class="collapse navbar-collapse justify-content-end">
                <a class="btn btn-light me-2" href="jsp/login.jsp">Login</a>
                <a class="btn btn-outline-light" href="jsp/register.jsp">Register</a>
            </div>
        </div>
    </nav>

    <header class="bg-light text-center p-5">
        <h1>Welcome to DriveSure</h1>
        <p>Your trusted partner in vehicle insurance.</p>
    </header>

    <section class="container text-center my-5">
        <h2>Our Services</h2>
        <div class="row mt-4">
            <div class="col-md-4">
                <div class="card p-3 shadow-sm">
                    <h5>Policy Management</h5>
                    <p>Create, view and track insurance policies with ease.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3 shadow-sm">
                    <h5>Claim Processing</h5>
                    <p>Submit and track your insurance claims securely.</p>
                </div>
            </div>
            <div class="col-md-4">
                <div class="card p-3 shadow-sm">
                    <h5>Real-time Tracking</h5>
                    <p>View policy status and receive timely notifications.</p>
                </div>
            </div>
        </div>
    </section>

    <footer class="bg-primary text-white text-center py-3">
        &copy; 2025 DriveSure. All Rights Reserved.
    </footer>

    <script src="js/script.js"></script>
</body>
</html>
