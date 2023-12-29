<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                        <h3 class="text-center pb-3">Staff Profile id: ${userProfile.id}</h3>

                        <h3 class="text-center pb-3">Staff Personal Information</h3>
                        <table class="table table-hover">
                            <tr class="bg-light2">
                                <td>Id</td>
                                <td>${userProfile.id}</td>
                            </tr>
                            <tr>
                                <td>First Name</td>
                                <td>${userProfile.firstName}</td>
                            </tr>
                            <c:if test="${errors.hasFieldErrors('firstName')}">
                                <tr>
                                    <td colspan="2" style="color:red">
                                        <c:forEach items="${errors.getFieldErrors('firstName')}" var="error">
                                            ${error.defaultMessage}<br>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>Last Name</td>
                                <td>${userProfile.lastName}</td>
                            </tr>
                            <c:if test="${errors.hasFieldErrors('lastName')}">
                                <tr>
                                    <td colspan="2" style="color:red">
                                        <c:forEach items="${errors.getFieldErrors('lastName')}" var="error">
                                            ${error.defaultMessage}<br>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>Email</td>
                                <td>${userProfile.email}</td>
                            </tr>
                            <c:if test="${errors.hasFieldErrors('email')}">
                                <tr>
                                    <td colspan="2" style="color:red">
                                        <c:forEach items="${errors.getFieldErrors('email')}" var="error">
                                            ${error.defaultMessage}<br>
                                        </c:forEach>
                                    </td>
                                </tr>
                            </c:if>
                            <tr>
                                <td>Job Title</td>
                                <td>${empty userProfile.jobTitle ? 'N/A' : userProfile.jobTitle}</td>
                            </tr>
                            <tr>
                                <td>Office ID</td>
                                <td>${empty userProfile.office_Id ? 'N/A' : userProfile.office_Id}</td>
                            </tr>
                            <tr>
                                <td>Address</td>
                                <td>${empty userProfile.address ? 'N/A' : userProfile.address}</td>
                            </tr>
                            <tr>
                                <td>Image</td>
                                <td><img src="${userProfile.imageUrl}" alt="User Image" style="width:30px; height: 30px"></td>
                            </tr>
                        </table>

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
                        <ol>
                            <c:forEach items="${userProfile.userTrainings}" var="userTraining">
                                <li>
                                ${empty userProfile.jobTitle ? 'N/A' : userProfile.jobTitle}
                                    ID: ${empty userTraining.training.id ? 'N/A' : userTraining.training.id} <br/>
                                    Name: ${empty userTraining.training.trainingName ? 'N/A' : userTraining.training.trainingName} <br/>
                                    Enrollment Date: ${empty userTraining.enrollmentDate ? 'N/A' : userTraining.enrollmentDate} <br/>
                                    Completion Date: ${empty userTraining.completionDate ? 'N/A' : userTraining.completionDate} <br/>
                                    Status: ${empty userTraining.status ? 'N/A' : userTraining.status}
                                </li>
                            </c:forEach>
                        </ol>

                        <form action="/staff/edit/${userProfile.id}">
                            <button type="submit" class="btn col-12 btn-primary">Edit</button>
                        </form>
                    </div>
                </div>
            </div>
        </section>
    </c:if>
</div>

<jsp:include page="../include/footer.jsp"/>
