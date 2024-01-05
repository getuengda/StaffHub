<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Add Training</h1>
            </div>
        </div>
    </div>
</section>
<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <form action="/admin/${userId}/addTraining" method="post">
            <label for="trainingName">Training Name:</label>
            <select name="trainingId" class="form-control" required>
                <c:forEach var="training" items="${trainingList}">
                    <option value="${training.id}">${training.trainingName}</option>
                </c:forEach>
            </select>
            <br>
            <label for="enrollmentDate">Enrollment Date:</label>
            <input type="datetime-local" name="enrollmentDate" class="form-control" required>
            <br>
             <label for="status">Status:</label>
             <input type="text" class="form-control" id="status" name="status" value="${form.status}">
            <br>
            <input type="submit" class="btn btn-primary" value="Add Training">
        </form>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>