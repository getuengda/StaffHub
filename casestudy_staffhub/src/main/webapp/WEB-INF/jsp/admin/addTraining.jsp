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
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger w-100 mb-0 text-center">
                ${errorMessage}
            </div>
        </c:if>
        <input type="hidden" name="userId" value="${userId}">
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
             <select  id="status" name="status" class="form-control">
                 <option value="Enrolled">Enrolled</option>
                 <option value="In Progress">In Progress</option>
                 <option value="Completed">Completed</option>
             </select>
            <br>
            <input type="submit" class="btn btn-primary" value="Add Training">
        </form>
    </div>
</section>

<jsp:include page="../include/footer.jsp"/>