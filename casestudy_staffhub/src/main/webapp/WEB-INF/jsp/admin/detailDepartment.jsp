<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Department Detail</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <c:if test="${department != null}">
            <section class="bg-light1 pb-5">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <h3 class="text-center pb-3">Details about Department with an id: ${department.id}</h3>

                            <table class="table table-hover">
                                <tr class="bg-light2">
                                    <td>Id</td>
                                    <td>Department Name</td>
                                    <td>Description</td>
                                    <td>Image</td>
                                </tr>
                                <tr>
                                    <td>${department.id}</td>
                                    <td>${department.departmentName}</td>
                                    <td>${department.description}</td>
                                    <td><img src="${department.imageUrl}" style="width:30px; height: 30px"></td>
                                    <td>
                                </tr>
                            </table>

                            <form action="/admin/editDepartment/${department.id}">
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