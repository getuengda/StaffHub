<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-beige pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Create Training</h1>
            </div>
        </div>
    </div>
</section>

<div class="d-flex justify-content-center align-items-center">
        <form method="post" action="/training/createSubmit">
        <input type="hidden" name="id" value="${form.id}">
               <div class="mb-3 mt-5">
                       <label for="trainingName" class="form-label">Training Name</label>
                       <input type="text" class="form-control" id="trainingName" name="trainingName" value="${form.trainingName}">
              </div>
              <div class="mb-3">
                  <label for="yourTextarea" class="form-label">Date Posted</label>
                  <input type="Date" class="form-control" id="datePosted" name="datePosted" value="${form.datePosted}">
               </div>
                <div class="mb-3">
                    <label for="description" class="form-label">Description</label>
                    <textarea class="form-control" id="description" name="description" value="${form.description}" rows="4" cols="50"></textarea>
                </div>
                <div class="mb-3">
                    <label for="prerequisite" class="form-label">Prerequisite</label>
                    <textarea class="form-control" id="prerequisite" name="prerequisite" value="${form.prerequisite}" rows="2" cols="50"></textarea>
                </div>
                <div class="mb-3">
                     <label for="imageUrl" class="form-label">Image URL</label>
                     <input type="text" class="form-control" id="imageUrl" name="imageUrl" value="${form.imageUrl}">
                 </div>

            <button type="submit" class="mb-5 btn btn-primary">Submit</button>
        </form>
    </div>


<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<jsp:include page="../include/footer.jsp"/>