<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Create Department</h1>
            </div>
        </div>
    </div>
</section>


    <div class="container d-flex justify-content-center align-items-center">

        <form method="get" action="/admin/createDepartmentSubmit" style="width: 50%;">
                            <c:if test="${not empty success}">
                                            <div class="row justify-content-center">
                                                <div class="col-6 text-center">
                                                    <div class="alert alert-success" role="alert">
                                                            ${success}
                                                    </div>
                                                </div>
                                            </div>
                            </c:if>
               <div class="mb-3 mt-5">
                       <label for="departmentName" class="form-label">Department Name</label>
                       <input type="text" class="form-control" id="departmentName" name="departmentName" value="${form.departmentName}">
              </div>
               <c:if test="${errors.hasFieldErrors('departmentName')}">
                          <div class="error" style="color:red">
                              <c:forEach items="${errors.getFieldErrors('departmentName')}" var="error">
                                  ${error.defaultMessage}<br>
                              </c:forEach>
                          </div>
              </c:if>
              <div class="mb-3">
                  <label for="yourTextarea" class="form-label">Description</label>
                  <input class="form-control" id="description" name="description" value="${form.description}" style="width: 568px; height: 100px;">
               </div>
                <div class="mb-3">
                    <label for="imageUrl" class="form-label">Image URL</label>
                    <input type="text" class="form-control" id="imageUrl" name="imageUrl" value="${form.imageUrl}">
                </div>
                <div class="mb-3">
                    <label for="departmentDetail" class="form-label">Department Detail</label>
                    <input type="text" class="form-control" id="departmentDetail" name="departmentDetail" value="${form.departmentDetail}">
                </div>

            <button type="submit" class="mb-5 btn btn-primary">Submit</button>
            <button type="button" style="margin-left: 42rem" class="mb-5 btn btn-primary" onclick="window.location.href='/';">Cancel</button>
        </form>
    </div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<jsp:include page="../include/footer.jsp"/>
