<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Show All Staff</h1>
            </div>
        </div>
    </div>
</section>
    <div class="container">
      <form action="/staff/showAll">
      <c:if test="${not empty userVar}">
                  <section class="bg-light1 pb-5">
                        <div class="container">
                              <div class="row justify-content-center">
                                    <div class="col-12">
                                          <h3 class="text-center pb-3">Currently we have ${userVar.size()} Staffs</h3>

                            <table class="table table-hover">
                                <tr>
                                    <td>Id</td>
                                    <td>Image</td>
                                    <td>First Name</td>
                                    <td>Last Name</td>
                                    <td>Email</td>
                                    <td>Job Title</td>
                                    <td>Office ID</td>
                                    <td>Address</td>
                                </tr>
                                <c:forEach items="${userVar}" var="user">
                                    <tr>
                                        <td>${user.id}</td>
                                        <td><img src="${user.imageUrl}" style="width:30px; height: 30px"></td>
                                        <td>${user.firstName}</td>
                                        <td>${user.lastName}</td>
                                        <td>${user.email}</td>
                                        <td>${user.jobTitle}</td>
                                        <td>${user.office_Id}</td>
                                        <td>${user.address}</td>
                                    </tr>
                                </c:forEach>
                            </table>
                            </div>
                         </div>
                      </div>
                  </section>
         </c:if>
        </form>
    </div>




<jsp:include page="../include/footer.jsp"/>