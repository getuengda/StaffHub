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
        <form action="/department/search">

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
                         <c:forEach items="${departmentVar}" var="department">
                            <div class="col-sm-4">
                                <div class="card pb-3 mb-4">
                                    <td><img src="${department.imageUrl}" style="width:354px; height: 300px"></td>
                                    <div class="card-body">
                                        <h5 class="card-title"><strong>Department Name</strong>: ${department.departmentName}</h5>
                                        <p class="card-text"><strong>Description</strong>: ${department.description}</p>
                                        <a href="${department.departmentDetail}" class="btn btn-primary">Show more</a>
                                    </div>
                                    <div class="card-footer">
                                        <small class="text-muted">ID: ${department.id}</small>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                        </div>
                     </div>
                  </div>
              </section>
     </c:if>

<jsp:include page="../include/footer.jsp"/>