<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Training Search</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light2 pt-5 pb-5">
    <div class="container">
        <form action="/training/search">

            <div class="row justify-content-center">
                   <div class="col-3 col-sm-3 col-md-2 col-lg-2 text-end">
                       <label for="trainingName" class="form-label bg-light2 m-0 pt-1" style="color: black">Training Name</label>
                    </div>
                    <div class="col-8 col-sm-9 col-md-6 col-lg-4">
                      <input type="text" class="form-control" id="trainingName" name="trainingName" placeholder="Search by training name" value="${trainingName}"/>
                   </div>
             </div>
              <button type="submit" style="margin-left: 49rem" class="mb-5 mt-3 btn btn-primary">Submit</button>
              <button type="button" style="margin-left: 21rem" class="mb-5 mt-3 btn btn-primary" onclick="window.location.href='/';">Cancel</button>

        </form>
    </div>
</section>

    <c:if test="${not empty trainingVar}">
              <section class="bg-light1 pb-5">
                  <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                         <c:forEach items="${trainingVar}" var="training">
                                <div class="col-sm-4">
                                    <div class="card pb-3 mb-4">
                                     <td><img src="${training.imageUrl}" style="width:354px; height: 300px"></td>
                                    <div class="card-body">
                                        <h5 class="card-title"><strong>Training Name</strong>: ${training.trainingName}</h5>
                                        <p class="card-text"><strong>Description</strong>: ${training.description}</p>
                                        <p class="card-text"><strong>Prerequisite</strong>: ${training.prerequisite}</p>
                                        <a href="${training.trainingDetail}" class="btn btn-primary">Show more</a>
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
              </section>
     </c:if>

<jsp:include page="../include/footer.jsp"/>