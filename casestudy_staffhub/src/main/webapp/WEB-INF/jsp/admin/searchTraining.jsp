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
        <form action="/admin/searchTraining">

            <div class="row justify-content-center">
                   <div class="col-3 col-sm-3 col-md-2 col-lg-2 text-end">
                       <label for="trainingName" class="form-label bg-light2 m-0 pt-1" style="color: black">Training Name</label>
                    </div>
                    <div class="col-8 col-sm-9 col-md-6 col-lg-4">
                      <input type="text" class="form-control" id="trainingName" name="trainingName" placeholder="Search by training name" value="${trainingName}"/>
                   </div>
             </div>
             <div class="row justify-content-center pt-4">
                   <div class="col-12 text-center">
                      <button type="submit" class="btn btn-primary">Submit</button>
                  </div>
             </div>

        </form>
    </div>
</section>

    <c:if test="${not empty trainingVar}">
              <section class="bg-light1 pb-5">
                    <div class="container">
                          <div class="row justify-content-center">
                                <div class="col-12">
                                      <h1 class="text-center pb-3">Training(s) Found: ${trainingVar.size()}</h1>

                        <table class="table table-hover">
                            <tr>
                                <td>Id</td>
                                <td>Image</td>
                                <td>Training Name</td>
                                <td>Date Posted</td>
                                <td>Description</td>
                                <td>Prerequisite</td>
                                <td>Edit</td>
                                <td>Detail</td>
                                <td>Upload</td>
                            </tr>
                            <c:forEach items="${trainingVar}" var="training">
                                <tr>
                                    <td>${training.id}</td>
                                    <td><img src="${training.imageUrl}" style="width:60px; height: 60px"></td>
                                    <td>${training.trainingName}</td>
                                    <td>${training.datePosted}</td>
                                    <td>${training.description}</td>
                                    <td>${training.prerequisite}</td>
                                    <td>
                                        <a href="/admin/editTraining/${training.id}">Edit</a>
                                    </td>
                                    <td>
                                        <a href="/admin/detailTraining?id=${training.id}">Detail</a>
                                    </td>
                                    <td>
                                        <a href="/admin/fileuploadTraining?id=${training.id}">Upload</a>
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