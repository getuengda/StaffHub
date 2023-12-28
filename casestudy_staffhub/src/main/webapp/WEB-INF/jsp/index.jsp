<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="include/header.jsp"/>

<section>
    <div class="bg-light2 pt-5 pb-5">
       <div class="row">
           <div class="col-12 text-center">
               <form id="searchForm" action="">

                   <div class="row justify-content-center">
                       <section id="searchFormSection">

                           <label for="searchType">Search for:</label>
                           <select id="searchType" name="searchType" onchange="changeAction(this)">
                               <option value="/training/search">Trainings</option>
                               <option value="/department/search">Departments</option>
                           </select>
                           <div class="col-12 ml-2 mr-2" id="inputField"></div>
                           <div class="row justify-content-center pt-4">
                               <div class="col-12 text-center">
                                   <button type="submit" class="btn btn-primary">Submit</button>
                               </div>
                           </div>

                       </section>
                   </div>

               </form>
           </div>
       </div>
    </div>

</section>

<c:if test="${not empty departmentVar}">
              <section class="bg-light1 pb-5">
                    <div class="container">
                          <div class="row justify-content-center">
                                <div class="col-12">
                                      <h1 class="text-center pb-3">Department(s) Found ${departmentVar.size()}</h1>

                        <table class="table table-hover">
                            <tr>
                                <td>Id</td>
                                <td>Department Name</td>
                                <td>Description</td>
                                <td>Image</td>
                            </tr>
                            <c:forEach items="${departmentVar}" var="department">
                                <tr>
                                    <td>${department.id}</td>
                                    <td>${department.departmentName}</td>
                                    <td>${department.description}</td>
                                    <td><img src="${department.imageUrl}" style="width:30px; height: 30px"></td>

                                </tr>
                            </c:forEach>
                        </table>
                        </div>
                     </div>
                  </div>
              </section>
     </c:if>

  <c:if test="${not empty trainingVar}">
              <section class="bg-light1 pb-5">
                    <div class="container">
                          <div class="row justify-content-center">
                                <div class="col-12">
                                      <h1 class="text-center pb-3">Training(s) Found ${trainingVar.size()}</h1>

                        <table class="table table-hover">
                            <tr>
                                <td>Id</td>
                                <td>Training Name</td>
                                <td>Date Posted</td>
                                <td>Description</td>
                                <td>Prerequisite</td>
                                <td>Image</td>
                            </tr>
                            <c:forEach items="${trainingVar}" var="training">
                                <tr>
                                    <td>${training.id}</td>
                                    <td>${training.trainingName}</td>
                                    <td>${training.datePosted}</td>
                                    <td>${training.description}</td>
                                    <td>${training.prerequisite}</td>
                                    <td><img src="${training.imageUrl}" style="width:30px; height: 30px"></td>

                                </tr>
                            </c:forEach>
                        </table>
                        </div>
                     </div>
                  </div>
              </section>
     </c:if>

        <div class="container" id="homeViewer">

                <section class="main" id="homeContainer">
                            <video autoplay muted loop id="myVideo">
                                <source src="/pub/vedio/pexels-rodnae-productions-7647633 (1080p).mp4" type="video/mp4">
                            </video>
               </section>

                <section class="mission">
                    <section class="values" id="values">
                      <h1>Who we are</h1>
                      <p>Our firm is designed to operate as one single global partnership united by a strong set of values.</p>
                      <br>
                      </section>
                    <section id="technology" >
                        <div id="myCarousel" class="carousel slide getu" data-ride="carousel">
                        <ol class="carousel-indicators">
                          <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                          <li data-target="#myCarousel" data-slide-to="1"></li>
                          <li data-target="#myCarousel" data-slide-to="2"></li>
                        </ol>
                        <div class="carousel-inner">
                          <div class="item active">
                            <img src="/pub/images/alena-darmel.jpg" alt="Engineering" style="width:100%;">
                            <div class="carousel-caption">
                              <h3>Engineering</h3>
                              <p>Engineering is always so much Innovative!</p>
                            </div>
                          </div>

                          <div class="item">
                            <img src="/pub/images/artem.jpg" alt="AI" style="width:100%;">
                            <div class="carousel-caption">
                              <h3>AI</h3>
                              <p>AI is Creating New World!</p>
                            </div>
                          </div>

                          <div class="item">
                            <img src="/pub/images/image1.jpg" alt="Research" style="width:100%;">
                            <div class="carousel-caption">
                              <h3>Research</h3>
                              <p>We Research Out of the Box & Boundary of Hemisphere!</p>
                            </div>
                          </div>
                          <div class="item">
                            <img src="/pub/images/image2.jpg" alt="Training" style="width:100%;">
                            <div class="carousel-caption">
                              <h3>Training</h3>
                              <p>Every Day Learning and Exploring new Idea!</p>
                            </div>
                          </div>
                        </div>
                        <a class="left carousel-control" href="#myCarousel" data-slide="prev">
                          <span class="glyphicon glyphicon-chevron-left"></span>
                          <span class="sr-only">Previous</span>
                        </a>
                        <a class="right carousel-control" href="#myCarousel" data-slide="next">
                          <span class="glyphicon glyphicon-chevron-right"></span>
                          <span class="sr-only">Next</span>
                        </a>
                      </div>
                    </section>

                    <section class="diversity" id="diversity">
                      <h1>Our Mission</h1>
                      <p>
                        Our clients are always pushing forward. Testing. Challenging.
                    </p>
                    <br>
                    <br>
                    </section>
                </section>

            </div>
            <script>
                document.querySelectorAll('.item').forEach(item => {
                item.addEventListener('mouseover', () => {
                    item.style.transform = 'scale(1.05)';
                });
                item.addEventListener('mouseout', () => {
                    item.style.transform = 'scale(1)';
                    });
                });

              document.querySelectorAll('.values').forEach(values => {
              values.addEventListener('mouseover', () => {
                  values.style.transform = 'scale(1.05)';
              });

              values.addEventListener('mouseout', () => {
                values.style.transform = 'scale(1)';
                  });
              });

              document.querySelectorAll('.diversity').forEach(diversity => {
                diversity.addEventListener('mouseover', () => {
                  diversity.style.transform = 'scale(1.05)';
              });

              diversity.addEventListener('mouseout', () => {
                diversity.style.transform = 'scale(1)';
                  });
              })


             function changeAction(element) {
                     document.getElementById('searchForm').action = element.value;
                     var inputField = document.getElementById('inputField');
                     inputField.innerHTML = '';
                     var input = document.createElement('input');
                     input.type = 'text';
                     input.className = 'form-control';
                     if (element.value === '/training/search') {
                         input.id = 'trainingName';
                         input.name = 'trainingName';
                         input.placeholder = 'Search by training name';
                     } else {
                         input.id = 'departmentName';
                         input.name = 'departmentName';
                         input.placeholder = 'Search by department name';
                     }
                     inputField.appendChild(input);
                 }
                 window.onload = function() {
                     changeAction(document.getElementById('searchType'));
                 };

            </script>

            <script src="../js/logout.js"></script>


 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
<jsp:include page="include/footer.jsp"/>