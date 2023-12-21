<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Create User</h1>
            </div>
        </div>
    </div>
</section>

    <div class="d-flex justify-content-center align-items-center">

        <form method="post" action="/staff/createSubmit" style="width: 50%;">
            <div class="mt-3 col-12">
                <label for="firstName" class="form-label">First Name</label>
                <input type="text" class="form-control" id="firstName" name="firstName" value="${form.firstName}" aria-describedby="firstNameHelp">
                <div id="firstNameHelp" class="form-text">Please let us know your first name</div>
            </div>

            <div class="mt-3 col-12">
                 <label for="lastName" class="form-label">Last Name</label>
                  <input type="text" class="form-control" id="lastName" name="lastName" value="${form.lastName}">
             </div>

             <div class="mt-3 col-12">
                <label for="email" class="form-label">Email</label>
                <input type="email" class="form-control" id="email" name="email" value="${form.email}">
              </div>

              <div class="mt-3 col-12">
                  <label for="password" class="form-label">Password</label>
                  <input type="password" class="form-control" id="password" name="password" value="${form.password}">
               </div>

            <div class="mt-3 col-12">
                  <label for="jobTitle" class="form-label">Job Title</label>
                  <input type="text" class="form-control" id="jobTitle" name="jobTitle" value="${form.jobTitle}">
             </div>

             <div class="mb-3 col-12">
                  <label for="office_Id" class="form-label">Office ID</label>
                  <input type="Number" class="form-control" id="office_Id" name="office_Id" value="${form.office_Id}">
              </div>
             <div class="mb-3 col-12">
                  <label for="address" class="form-label">Address</label>
                 <input type="text" class="form-control" id="address" name="address" value="${form.address}">
              </div>
              <div class="mb-3 col-12">
                 <label for="imageUrl" class="form-label">Image URL</label>
                 <input type="text" class="form-control" id="imageUrl" name="imageUrl" value="${form.imageUrl}">
             </div>

            <button type="submit" class="btn btn-primary mb-3 ml-4">Submit</button>
        </form>
    </div>

<jsp:include page="../include/footer.jsp"/>