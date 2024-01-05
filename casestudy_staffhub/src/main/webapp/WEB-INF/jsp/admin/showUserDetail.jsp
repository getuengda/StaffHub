<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Staff Detail</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <c:if test="${user != null}">
            <section class="bg-light1 pb-5">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <h3 class="text-center pb-3">Details about staff with id: ${user.id}</h3>

                            <table class="table table-hover">
                                <tr class="bg-light2">
                                    <td>Id</td>
                                    <td>First Name</td>
                                    <td>Last Name</td>
                                    <td>Email</td>
                                    <td>Job Title</td>
                                    <td>Office ID</td>
                                    <td>Address</td>
                                    <td>Image</td>
                                    <td>Add Training</td>
                                </tr>
                                <tr>
                                    <td>${user.id}</td>
                                    <td>${user.firstName}</td>
                                    <td>${user.lastName}</td>
                                    <td>${user.email}</td>
                                    <td>${user.jobTitle}</td>
                                    <td>${user.office_Id}</td>
                                    <td>${user.address}</td>
                                    <td><img src="${user.imageUrl}" alt="User Image" style="width:30px; height: 30px"></td>
                                    <td>
                                        <a href="/admin/addTraining?userId=${user.id}">Add Training</a>
                                    </td>
                                </tr>
                            </table>

                            <form action="/admin/edit/${user.id}">
                            <button type="submit" class="btn col-12 btn-primary">Edit</button>
                            </form>
                        </div>
                    </div>
                </div>
            </section>
        </c:if>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>