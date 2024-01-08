<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Create User</h1>
            </div>
        </div>
    </div>
</section>

     <div class="d-flex justify-content-center align-items-center">

             <!-- the action attribute on the form tag is the URL that the form will submit to when then user clicks the submit button -->
            <form method="post" action="/admin/createSubmitUser" style="width: 50%;">

                                <c:if test="${not empty success}">
                                    <div class="row justify-content-center">
                                        <div class="col-6 text-center">
                                            <div class="alert alert-success" role="alert">
                                                    ${success}
                                            </div>
                                        </div>
                                    </div>
                                 </c:if>

            <input type="hidden" name="id" value="${form.id}">
              <div class="bg-light1 pt-5 pb-5 class="col-6 px-5 text-center"">
                  <div class="row">
                           <div class="col-12 px-5">
                              <label for="userType" class="form-label">User Type</label>
                              <select class="form-control" id="userType" name="userType">
                                  <option value="USER">User</option>
                                  <option value="ADMIN">Admin</option>
                              </select>
                          </div>
                          <div class="col-12 px-5">
                              <label for="departmentId" class="col-form-label">Select Department from the List</label>
                              <select class="form-control" id="departmentId" name="departmentId">
                                  <option value="" <c:if test="${empty form.departmentId}">selected</c:if>>Please select a department</option>
                                  <c:forEach var="department" items="${departments}">
                                      <option value="${department.id}" <c:if test="${department.id == form.departmentId}">selected</c:if>>${department.departmentName}</option>
                                  </c:forEach>
                              </select>
                          </div>
                     </div>
               </div>
                <div class="mt-3 col-12">
                    <label for="firstName" class="form-label">First Name</label>
                    <input type="text" class="form-control" id="firstName" name="firstName" value="${form.firstName}" aria-describedby="firstNameHelp">
                    <div id="firstNameHelp" class="form-text">Please let us know your first name</div>
                </div>
                <c:if test="${errors.hasFieldErrors('firstName')}">
                            <div class="error" style="color:red">
                                <c:forEach items="${errors.getFieldErrors('firstName')}" var="error">
                                    ${error.defaultMessage}<br>
                                </c:forEach>
                            </div>
                </c:if>
                <div class="mt-3 col-12">
                     <label for="lastName" class="form-label">Last Name</label>
                      <input type="text" class="form-control" id="lastName" name="lastName" value="${form.lastName}">
                 </div>
                  <c:if test="${errors.hasFieldErrors('lastName')}">
                          <div class="error" style="color:red">
                              <c:forEach items="${errors.getFieldErrors('lastName')}" var="error">
                                  ${error.defaultMessage}<br>
                              </c:forEach>
                          </div>
                 </c:if>
                 <div class="mt-3 col-12">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${form.email}">
                  </div>
                   <c:if test="${errors.hasFieldErrors('email')}">
                           <div class="error" style="color:red">
                               <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                   ${error.defaultMessage}<br>
                               </c:forEach>
                           </div>
                   </c:if>
                  <div class="mt-3 col-12">
                      <label for="password" class="form-label">Password</label>
                      <input type="password" class="form-control" id="password" name="password" value="${form.password}">
                   </div>
                    <c:if test="${errors.hasFieldErrors('password')}">
                                <div class="error" style="color:red">
                                    <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                                        ${error.defaultMessage}<br>
                                    </c:forEach>
                                </div>
                   </c:if>
                <div class="mt-3 col-12">
                      <label for="jobTitle" class="form-label">Job Title</label>
                      <input type="text" class="form-control" id="jobTitle" name="jobTitle" value="${form.jobTitle}">
                 </div>
                  <c:if test="${errors.hasFieldErrors('jobTitle')}">
                              <div class="error" style="color:red">
                                  <c:forEach items="${errors.getFieldErrors('jobTitle')}" var="error">
                                      ${error.defaultMessage}<br>
                                  </c:forEach>
                              </div>
                  </c:if>
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
                 <c:if test="${errors.hasFieldErrors('imageUrl')}">
                           <div class="error" style="color:red">
                               <c:forEach items="${errors.getFieldErrors('imageUrl')}" var="error">
                                   ${error.defaultMessage}<br>
                               </c:forEach>
                           </div>
                </c:if>
                <button type="submit" class="btn btn-primary mb-3 ml-4">Submit</button>
                 <button type="button" style="margin-left: 85rem" class="mb-5 btn btn-primary" onclick="window.location.href='/';">Cancel</button>
            </form>
        </div>

<jsp:include page="../include/footer.jsp"/>