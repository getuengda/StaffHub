<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:include page="../include/header.jsp"/>

<section>
    <div class="bg-light2 pt-5 pb-5">
        <div class="row">
            <div class="col-12 text-center">
                <h1 class="m-0">Welcome to Staff Management Website</h1>
                <h5 style="color: black; text-align: center; padding-top: 1.5rem;">Home Page</h5>
            </div>
        </div>
    </div>

</section>
<section id="searchForm">
        <h1>Search</h1>
        <form action="staff/search" method="post">
            <label for="searchType">Search for:</label>
            <select id="searchType" name="searchType">
                <option value="trainings">Trainings</option>
                <option value="departments">Departments</option>
            </select>
            <input type="text" id="searchText" name="searchText" placeholder="Enter your search term">
            <input type="submit" value="Search">
        </form>
</section>


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
                <section class="footer">
                    <div class="socials">
                        <p id="footerP">
                          Copyright &copy; 2023 Staff Management Website - All rights reserved.
                        </p>

                        <a href="https://www.facebook.com" class="fa fa-facebook"></a>
                        <a href="https://www.twitter.com" class="fa fa-twitter"></a>
                        <a href="https://www.linkedin.com" class="fa fa-linkedin"></a>
                      </div>
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
            </script>

            <script src="../js/logout.js"></script>


 <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
 <jsp:include page="include/footer.jsp"/>