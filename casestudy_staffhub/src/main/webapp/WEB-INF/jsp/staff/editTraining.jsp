<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Edit Training</h1>
            </div>
        </div>
    </div>
</section>
<section class="bg-light1 pt-5 pb-5">
    <div class="container">
        <form action="/staff/${userId}/editTraining/${trainingId}" method="post">
            <label for="enrollmentDate">Enrollment Date:</label>
           <input class="col-3" type="date" id="enrollmentDate" name="enrollmentDate" value="${userTraining.enrollmentDate.toInstant().toString().substring(0,10)}"><br><br>
            <label for="completionDate">Completion Date:</label>
            <input class="col-3" type="date" id="completionDate" name="completionDate" value="${userTraining.completionDate.toInstant().toString().substring(0,10)}"><br><br>
            <label class="mr-5" for="status">Status:</label>
            <input class="col-3 ml-5" type="text" id="status" name="status" value="${userTraining.status}"><br><br>
            <input type="submit" value="Submit">
        </form>

    </div>
</section>

<jsp:include page="../include/footer.jsp"/>