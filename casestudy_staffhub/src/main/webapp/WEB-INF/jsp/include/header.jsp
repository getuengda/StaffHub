<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bootstrap demo</title>
    <link href="/pub/css/common-style.css" rel="stylesheet">
    <link href="/pub/css/departments.css" rel="stylesheet">
    <link href="/pub/css/login1.css" rel="stylesheet">
    <link href="/pub/css/home.css" rel="stylesheet">
    <link href="/pub/css/form.css" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65"
          crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4"
            crossorigin="anonymous"></script>
    <script src="/pub/js/index2.js"></script>

</head>
<body>

<nav class="navbar navbar-expand-lg bg-beige">
    <div class="container-fluid">
        <a class="navbar-brand mb-3 mt-0" href="#">
            <img class="img-responsive logo" src="/pub/images/GT-logo.jpg" alt="Logo">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="nav nav-tabs ml-auto">
               <li class="nav-item">
                  <a class="nav-link" style="font-size: 16px" href="">Home</a>
              </li>
            <!-- ==================ADMIN===================== -->
             <sec:authorize access="hasAuthority('ADMIN')">
             <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Staff</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="/admin/createUser">Create Staff</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/searchUser">Search Staff</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/showLoggedUser">Show a Staff</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/showProfile">Show User Profile</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/showAllUser">Show All Staffs</a>
                </div>
              </li>
             <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Department</a>
                <div class="dropdown-menu">
                    <a class="dropdown-item" href="/admin/createDepartment">Create Department</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/searchDepartment">Search Department</a>
                    <div class="dropdown-divider"></div>
                    <a class="dropdown-item" href="/admin/showAllDepartment">Show All Departments</a>
                </div>
              </li>
              <li class="nav-item dropdown">
                 <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Training</a>
                 <div class="dropdown-menu">
                     <a class="dropdown-item" href="/admin/createTraining">Create Training</a>
                     <div class="dropdown-divider"></div>
                     <a class="dropdown-item" href="/admin/searchTraining">Search Training</a>
                     <div class="dropdown-divider"></div>
                     <a class="dropdown-item" href="/admin/showAllTraining">Show All Trainings</a>
                 </div>
               </li>
         </sec:authorize>
         <!-- ==================USER===================== -->
         <sec:authorize access="hasAuthority('USER')">
            <sec:authorize access="!hasAuthority('ADMIN')">
              <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Staff</a>
              <div class="dropdown-menu">
                  <a class="dropdown-item" href="/staff/showUser">Show a Staff</a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="/staff/profile">Show your Profile</a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="/staff/showAll">Show All Staffs</a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Department</a>
              <div class="dropdown-menu">
                  <a class="dropdown-item" href="/department/search">Search Department</a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="/department/showAll">Show All Departments</a>
              </div>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" style="color: #fd0dc4; font-size: 16px" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">Training</a>
              <div class="dropdown-menu">
                  <a class="dropdown-item" href="/training/search">Search Training</a>
                  <div class="dropdown-divider"></div>
                  <a class="dropdown-item" href="/training/showAll">Show All Trainings</a>
              </div>
            </li>
             </sec:authorize>
         </sec:authorize>
         <!-- ===================NOT LOGGED IN========================= -->
         <sec:authorize access="!isAuthenticated()">
             <li class="nav-item">
                 <a class="nav-link" href="/auth/register" style="color: #fd0dc4; font-size: 16px">Sign Up</a>
             </li>
             <li class = "nav-item">
                 <a class = "nav-link" href="/auth/login" style="color: #fd0dc4; font-size: 16px">Sign In</a>
             </li>
          </sec:authorize>
          <!-- ============================================== -->
           <sec:authorize access="isAuthenticated()">
               <li class="nav-item">
                   <a class="nav-link" style="color: #fd0dc4; font-size: 16px" href="/auth/logout">Logout</a>
               </li>
               <li class="nav-item">
                   <a class="nav-link" style="color: #fd0dc4;" href=""><sec:authentication property="principal.username" /></a>
               </li>
           </sec:authorize>
          </ul>
        </div>
    </div>
</nav>
