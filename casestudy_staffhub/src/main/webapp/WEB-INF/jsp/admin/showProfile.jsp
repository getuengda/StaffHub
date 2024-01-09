<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Staff Profile</h1>
            </div>
        </div>
    </div>
</section>
<div class="container">
    <c:if test="${userProfile != null}">
        <section class="bg-light1 pb-5">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-12">
                        <h3 class="text-center pb-3">Staff Personal Information</h3>
                        <form:form modelAttribute="userProfile" method="post" action="/admin/editShowProfile/${userProfile.id}">
                            <table class="table table-hover">
                                <tr class="bg-light2">
                                    <td>Id</td>
                                    <td>${userProfile.id}</td>
                                </tr>
                                <tr>
                                   <td>First Name</td>
                                   <td><form:input path="firstName" value="${userProfile.firstName}" /></td>
                               </tr>
                               <tr>
                                   <td>Last Name</td>
                                   <td><form:input path="lastName" value="${userProfile.lastName}" /></td>
                               </tr>
                               <tr>
                                   <td>Email</td>
                                   <td><form:input path="email" value="${userProfile.email}" /></td>
                               </tr>
                               <tr>
                                   <td>Address</td>
                                   <td><form:input path="address" value="${userProfile.address}" /></td>
                               </tr>
                               <tr>
                                   <td>Image</td>
                                   <td><form:input path="imageUrl" value="${userProfile.imageUrl}" /></td>
                               </tr>
                               <tr>
                                   <td>Job Title</td>
                                   <td><form:input path="jobTitle" value="${userProfile.jobTitle}" /></td>
                               </tr>
                               <tr>
                               <tr>
                                   <td colspan="2">
                                       <a class="btn btn-primary" href="/admin/editUser/${user.id}">Edit</a>
                                   </td>
                               </tr>
                           </table>
                       </form:form>

                        <h3 class="text-center pb-3">Staff Department Information</h3>
                        <table class="table table-hover">
                            <tr class="bg-light2">
                                <td>Department Name</td>
                                <td>${userProfile.departmentName}</td>
                            </tr>
                            <tr>
                                <td>Description</td>
                                <td>${userProfile.description}</td>
                            </tr>
                        </table>

                        <h3 class="text-center pb-3">Staff Training Information</h3>
                        <form:form modelAttribute="userProfile" method="post" action="/admin/editProfile/${userProfile.id}">
                        <table class="table table-hover" style="margin-left: auto; padding-right: 5px">
                            <tr class="bg-light2">
                                <td>ID</td>
                                <td>Training Name</td>
                                <td>Enrollment Date</td>
                                <td>Completion Date</td>
                                <td>Status</td>
                                <td>Edit</td>
                                <td>
                                    <a type="button" class="btn btn-primary" href="/admin/addTraining?userId=${user.id}">Add Training</a>
                                </td>
                            </tr>
                             <c:forEach items="${userProfile.userTrainings}" var="userTraining" varStatus="status">
                            <tr>
                                <td>${empty userTraining.training.id ? 'N/A' : userTraining.training.id}</td>
                                <td class="col-4">${empty userTraining.training.trainingName ? 'N/A' : userTraining.training.trainingName}</td>
                                <td>${empty userTraining.enrollmentDate ? 'N/A' : userTraining.enrollmentDate}</td>
                                <td>
                                   <input path="userTrainings[${status.index}].completionDate" id="completionDate_${status.index}" value="${userTraining.completionDate}"/>
                                </td>
                                <td>
                                    <form:input path="userTrainings[${status.index}].status" id="status_${status.index}" value="${userTraining.status}" />
                                </td>
                                <td>
                                    <a style="color: #fd0dc4;" href="/admin/${userProfile.id}/editTraining/${userTraining.training.id}">Edit</a>
                                </td>
                                <td>
                                    <a style="color: #fd0dc4;" href="/admin/showUserDetail?id=${userProfile.id}">Detail</a>
                                </td>
                            </tr>
                            </c:forEach>
                            <td>
                             <button type="button" class="btn btn-primary" onclick="window.location.href='/';">Cancel</button>
                            </td>
                        </table>
                    </form:form>
                   </div>
                </div>
            </div>
        </section>
    </c:if>
</div>

<jsp:include page="../include/footer.jsp"/>
