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
      <div class="row justify-content-center">
         <div class="col-12">
            <form action="/staff/${userId}/editTraining/${trainingId}" method="post">
                <label for="enrollmentDate">Enrollment Date:</label>
                           <input class="col-3" type="date" id="enrollmentDate" name="enrollmentDate" value="${userTraining.enrollmentDate.toInstant().toString().substring(0,10)}"><br><br>
                            <label for="completionDate">Completion Date:</label>
                            <input class="col-3" type="date" id="completionDate" name="completionDate" value="${userTraining.completionDate.toInstant().toString().substring(0,10)}"><br><br>
                            <br>
                             <label for="status">Status:</label>
                             <select class="col-3" id="status" name="status" value="${userTraining.status}" class="form-control">
                                 <option class="mb-5 pt-4" value="Enrolled">Enrolled</option>
                                 <option class="mb-5 pt-4" value="In Progress">In Progress</option>
                                 <option class="mb-5 pt-4" value="Completed">Completed</option>
                             </select>
                            <br>

                <button type="submit" style="margin-left: 1rem" class="mb-5 mt-4 btn btn-primary">Submit</button>
                <button type="button" style="margin-left: 26rem" class="mb-5 mt-4 btn btn-primary" onclick="window.location.href='/';">Cancel</button>
            </form>

       </div>
      </div>
    </div>
</section>

<script>
function checkOther(select) {
    var otherStatus = document.getElementById('otherStatus');
    if (select.value == 'other') {
        otherStatus.style.display = 'block';
    } else {
        otherStatus.style.display = 'none';
    }
}
</script>

<jsp:include page="../include/footer.jsp"/>