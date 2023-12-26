<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">User Registration</h1>
            </div>
        </div>
    </div>
</section>

<section class="pt-5 pb-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-5">
                <!-- the action attribute on the form tag is the URL that the form will submit to when then user clicks the submit button -->
                <form method="get" action="/auth/registerSubmit" style="width:auto; height: 780px; border: 10px solid black">
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
                        <input type="text" class="form-control" id="email" name="email" value="${form.email}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('email')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                    <div class="mt-3 col-12">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password" value="${form.password}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('password')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('password')}" var="error">
                                ${error.defaultMessage}<br>
                            </c:forEach>
                        </div>
                    </c:if>
                    <div class="mt-3 col-12">
                        <label for="confirmPassword" class="form-label">Confirm Password</label>
                        <input type="text" class="form-control" id="confirmPassword" name="confirmPassword" value="${form.confirmPassword}">
                    </div>
                    <c:if test="${errors.hasFieldErrors('confirmPassword')}">
                        <div style="color:red">
                            <c:forEach items="${errors.getFieldErrors('confirmPassword')}" var="error">
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


                    <button type="submit" class="btn btn-primary mt-5 ml-4 col-3">Submit</button>
                </form>
            </div>
        </div>
    </div>
</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<jsp:include page="../include/footer.jsp"/>

