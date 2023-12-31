<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Show All Trainings</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <form action="/training/showAll">
      <c:if test="${not empty trainingVar}">
                  <section class="bg-light1 pb-5">
                        <div class="container">
                              <div class="row justify-content-center">
                                    <div class="col-12">
                                          <h3 class="text-center pb-3">We have the following ${trainingVar.size()} training area currently</h3>
                            <div class="row">
                                <c:forEach items="${trainingVar}" var="training">
                                    <div class="col-sm-4">
                                        <div class="card pb-3 mb-4">
                                         <td><img src="${training.imageUrl}" style="width:354px; height: 300px"></td>
                                        <div class="card-body">
                                            <h5 class="card-title"><strong>Training Name</strong>: ${training.trainingName}</h5>
                                            <p class="card-text"><strong>Description</strong>: ${training.description}</p>
                                            <p class="card-text"><strong>Prerequisite</strong>: ${training.prerequisite}</p>
                                            <a href="${training.trainingDetail}" class="btn btn-primary">Go somewhere</a>
                                        </div>
                                        <div class="card-footer">
                                            <small class="text-muted">ID: ${training.id}</small>
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