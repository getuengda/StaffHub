<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Department Search</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <form action="/admin/searchDepartment" method="POST">

            <div class="row justify-content-center">
                   <div class="col-3 col-sm-3 col-md-2 col-lg-2 text-end">
                       <label for="departmentName" class="form-label m-0 pt-1" style="color: black">Department Name</label>
                    </div>
                    <div class="col-8 col-sm-9 col-md-6 col-lg-4">
                      <input type="text" class="form-control" id="departmentName" name="departmentName" placeholder="Search by department name" value="${departmentName}"/>
                   </div>
             </div>
              <button type="submit" style="margin-left: 49rem" class="mb-5 mt-3 btn btn-primary">Submit</button>
              <button type="button" style="margin-left: 21rem" class="mb-5 mt-3 btn btn-primary" onclick="window.location.href='/';">Cancel</button>

        </form>
    </div>
</section>

    <c:if test="${not empty departmentVar}">
              <section class="bg-light2 pb-5">
                    <div class="container">
                          <div class="row justify-content-center">
                                <div class="col-12">
                                      <h1 class="text-center pb-3">Department(s) Found: ${departmentVar.size()}</h1>

                        <table class="table table-hover">
                            <tr>
                                <td>Id</td>
                                <td>Department Name</td>
                                <td>Description</td>
                                <td>Image</td>
                                <td>Edit</td>
                                <td>Detail</td>
                                <td>Upload</td>
                            </tr>
                            <c:forEach items="${departmentVar}" var="department">
                                <tr>
                                    <td>${department.id}</td>
                                    <td>${department.departmentName}</td>
                                    <td>${department.description}</td>
                                    <td>${department.departmentDetail}</td>
                                    <td><img src="${department.imageUrl}" style="width:60px; height: 60px; margin-right: 20px"></td>
                                    <td>
                                        <a href="/admin/editDepartment/${department.id}">Edit</a>
                                    </td>
                                     <td>
                                        <a href="/admin/detailDepartment?id=${department.id}">Detail</a>
                                    </td>
                                    <td>
                                        <a href="/admin/fileuploadDepartment?id=${department.id}">Upload</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                        </div>
                     </div>
                  </div>
              </section>
     </c:if>

<jsp:include page="../include/footer.jsp"/>