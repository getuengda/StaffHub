<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Training Detail</h1>
            </div>
        </div>
    </div>
</section>

<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <c:if test="${training != null}">
            <section class="bg-light1 pb-5">
                <div class="container">
                    <div class="row justify-content-center">
                        <div class="col-12">
                            <h3 class="text-center pb-3">Detail about Training with an id: ${training.id}</h3>

                            <table class="table table-hover">
                                 <tr class="bg-light2">
                                    <td>Id</td>
                                    <td>Training Name</td>
                                    <td>Date Posted</td>
                                    <td>Description</td>
                                    <td>Prerequisite</td>
                                    <td>Image</td>
                                </tr>
                                 <tr>
                                    <td>${training.id}</td>
                                    <td>${training.trainingName}</td>
                                    <td>${training.datePosted}</td>
                                    <td>${training.description}</td>
                                    <td>${training.prerequisite}</td>
                                    <td><img src="${training.imageUrl}" style="width:30px; height: 30px"></td>
                                </tr>
                            </table>

                            <form action="/admin/editTraining/${training.id}">
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