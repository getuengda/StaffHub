<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Show All Departments</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <form action="/department/showAll">
      <c:if test="${not empty departmentVar}">
                  <section class="bg-light1 pb-5">
                        <div class="container">
                              <div class="row justify-content-center">
                                    <div class="col-12">
                                          <h3 class="text-center pb-3">We have the following ${departmentVar.size()} departments currently</h3>

                            <div class="row">
                                <c:forEach items="${departmentVar}" var="department">
                                    <div class="col-sm-4">
                                        <div class="card pb-3 mb-4">
                                            <td><img src="${department.imageUrl}" style="width:354px; height: 300px"></td>
                                            <div class="card-body">
                                                <h5 class="card-title"><strong>Department Name</strong>: ${department.departmentName}</h5>
                                                <p class="card-text"><strong>Description</strong>: ${department.description}</p>
                                                <a href="#" class="btn btn-primary">Go somewhere</a>
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
                      </div>
                  </section>
         </c:if>
        </form>
    </div>
</section>
<jsp:include page="../include/footer.jsp"/>