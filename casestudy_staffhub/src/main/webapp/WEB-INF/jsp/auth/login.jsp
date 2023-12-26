<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Login</h1>
            </div>
        </div>
    </div>
</section>
<c:if test="${param['error'] eq ''}">
    <section class="pt-5">
        <div class="container">
            <div class="row justify-content-center">
                <div class="col-6">
                    <div class="alert alert-danger w-100 mb-0 text-center">Invalid Username or Password</div>
                </div>
            </div>
        </div>
    </section>
</c:if>
<section class="pt-5 pb-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-4">
                <!-- the action attribute on the form tag is the URL that the form will submit to when then user clicks the submit button -->
                <form method="post" action="/auth/loginSubmit" style="width:auto; height: 300px; border: 10px solid black">

                    <div class="mt-5 ml-3">
                        <label for="username" class="form-label">Username</label>
                        <input type="text" class="form-control" style="m-3" id="username" name="username">
                    </div>

                    <div class="mt-3 ml-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="text" class="form-control" id="password" name="password">
                    </div>

                    <button type="submit" class="btn btn-primary mt-4 ml-3">Submit</button>
                    <div class="mt-5 ml-3"><p>Forget your <span style="color: darkcyan">Username</span> or <span style="color: darkcyan">Password?</span></p></div>
                </form>

            </div>
        </div>
    </div>

</section>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<jsp:include page="../include/footer.jsp"/>