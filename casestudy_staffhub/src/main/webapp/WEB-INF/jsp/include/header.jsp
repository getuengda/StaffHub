<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
       <meta charset="utf-8">
       <meta name="viewport" content="width=device-width, initial-scale=1">
       <title>Bootstrap demo</title>
       <link href="/pub/css/common-style.css" rel="stylesheet">
       <link href="/pub/css/departments1.css" rel="stylesheet">
       <link href="/pub/css/login1.css" rel="stylesheet">
       <link href="/pub/css/form.css" rel="stylesheet">
       <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
       <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
             crossorigin="anonymous">
       <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
       <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
       <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
       <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
               crossorigin="anonymous"></script>
       <script src="/pub/js/training.js"></script>
</head>
<body>

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                    <li class="nav-item">
                           <a class="nav-link" href="jsp/homePage/home.jsp">Home</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Staff</a>
                        <div class="dropdown-menu">
                            <a class="dropdown-item" href="/staff/create">Create Staff</a>
                            <a class="dropdown-item" href="/staff/search">Search Staff</a>
                            <a class="dropdown-item" href="/staff/find">Find Staff</a>
                            <a class="dropdown-item" href="/staff/showAll">Show All Staffs</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="#">Separated link</a>
                        </div>
                    </li>

                 <li class="nav-item dropdown">
                          <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Department</a>
                          <div class="dropdown-menu">
                                <a class="dropdown-item" href="/department/create">Create Department</a>
                                <a class="dropdown-item" href="/department/search">Search Department</a>
                                <a class="dropdown-item" href="/department/find">Find Department</a>
                                <a class="dropdown-item" href="/department/showAll">Show Departments</a>
                                <div class="dropdown-divider"></div>
                                <a class="dropdown-item" href="#">Separated link</a>
                         </div>
                 </li>

                  <li class="nav-item dropdown">
                           <a class="nav-link dropdown-toggle" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Training</a>
                           <div class="dropdown-menu">
                                 <a class="dropdown-item" href="/training/create">Create Training</a>
                                 <a class="dropdown-item" href="/training/search">Search Training</a>
                                 <a class="dropdown-item" href="/training/find">Find Training</a>
                                 <a class="dropdown-item" href="/training/showAll">Show All Trainings</a>
                                 <div class="dropdown-divider"></div>
                                 <a class="dropdown-item" href="#">Separated link</a>
                          </div>
                </li>
            </ul>
        </div>
    </div>
</nav>