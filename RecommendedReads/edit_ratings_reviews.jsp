<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    
	    <title>Profile</title>
	    <link rel="icon" href="images/logo_icon.png">
	    
	    <!-- CSS Stylesheets -->
	    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	    <link rel="stylesheet" href="css/base.css">
	    <link rel="stylesheet" href="css/profile.css">
	   	<link rel="stylesheet" href="css/ratingsreviews.css">
	    
	    <!-- Font Awesome -->
	    <script defer src="https://use.fontawesome.com/releases/v5.11.1/js/all.js"></script>
	
	    <!-- Boot Scripts -->
	    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
		<script src="js/stars.js"></script>
	</head>
	<body>
  	<%
  		response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    	if (session.getAttribute("email")==null) {
      	response.sendRedirect("index.html");
    	}
  	%>
    <section id="top" class="colored-section">
    	<div class="container-fluid">
        	<nav class="navbar navbar-expand-lg navbar-dark">
          		<a href="/RecommendedReads/Profile" class="navbar-brand">
            		Recommended Reads
            		<img src="images/logo_icon.png" height="28" alt="Recommended Reads">
          		</a>
          		<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            		<span class="navbar-toggler-icon"></span>
          		</button>
          		<div class="collapse navbar-collapse" id="navbarSupportedContent">
            		<ul class="navbar-nav ml-auto">
              			<li class="nav-item">
                			<a class="nav-link" href="/RecommendedReads/RatingsReviews">Browse</a>
              			</li>
              			<li class="nav-item">
              				<a class="nav-link" href="/RecommendedReads/Logout">Logout</a>
              			</li>
            		</ul>
          		</div>
        	</nav>
        </div>
    </section>
    <div class="container">
    	<form action="/RecommendedReads/ProfileEdit2" method="POST" class="needs-validation" novalidate>
	        <div class="col-md-6">
	            <div class="profile-head">
	             <div class="profile-img">
	                 <i class="fas fa-user fa-6x"></i>
	             </div>
	                <h2>${firstName} ${lastName}</h2>
	                <ul class="nav nav-tabs" role="tablist">
	                    <li class="nav-item" style="width:100%;">
	                        <a class="nav-link active" id="ratings-reviews-tab" data-toggle="tab" href="#ratings-reviews" role="tab" aria-controls="ratings-reviews" aria-selected="false">Ratings & Reviews</a>
	                    </li>
	                </ul>
	            </div>
	        </div>
			<div class="col-md-6">
	            <div class="tab-content account-tab">
	                <div class="tab-pane show active" id="ratings-reviews" role="tabpanel" aria-labelledby="ratings-reviews-tab">
	                	<div class="reviews container" style="display: block; text-align: center;">
					        <%@ page import ="java.util.ArrayList"%>
		                	<% 
		                	ArrayList<String> titles = (ArrayList<String>) session.getAttribute("profileTitles");
		                	ArrayList<String> authors = (ArrayList<String>) session.getAttribute("profileAuthors");
		                	ArrayList<String> posted = (ArrayList<String>) session.getAttribute("profilePosted");
		                	ArrayList<Object> ratings = (ArrayList<Object>) session.getAttribute("profileRatings");
		                	ArrayList<String> reviews = (ArrayList<String>) session.getAttribute("profileReviews");
		                	%>
				            <div class="row">
				                <div class="col-md-12">
				                    <div class="card">
				                        <%
				                        if(titles.size() == 0) {
				                        %>
				                        	<p class="text mt-2 mb-2" style="text-align: center;">You have not rated or reviewed any books yet.</p>
				                        <%
				                        }
				                        for(int i = 0; i < titles.size() ; i++) {
								       	%>
					                        <div class="review">
					                            <div class="review-row pt-4 pb-4 d-flex flex-row ">
					                                <div class="review-text active w-100">
					                                	<label class="mb-4 p-0">
															<input class="m-0 form-check-input" name="ratings_reviews" type="checkbox" value="<%= titles.get(i) %>###<%= authors.get(i) %>">
														</label>
					                                    <h5 class="mb-1"><%= titles.get(i) %></h5>
					                                    <h6 class="m-0"><%= authors.get(i) %></h6>
					                                    <div class="here">
									                    	<script>
									                       		$(".here").addClass("rating"+<%= i+1 %>);
									                       		$(".here").removeClass("here");
								 								var rating = <%= ratings.get(i) %>;
								 								stars(rating, ".rating"+<%= i+1 %>, 1);
							 								</script>
							 							</div>
					                                    <p class="text mb-2" style="line-height: 2;"><%= reviews.get(i) %></p>
					                                    <p class="date mb-0"><%= posted.get(i) %></p>
					                                </div>
					                            </div>
					                        </div>
					                	<%
									    }
										%>
				                    </div>
				                </div>
				            </div>
						</div>
	                </div>
	              	<div class="pr-5 pt-3 btn-group general-btn">
						<button class="w-10 btn btn-md btn-danger" type="submit">Delete</button>
					</div>
					<div class="pl-5 pt-3 btn-group general-btn"">
						<button class="w-10 btn btn-md btn-secondary" type="button" onclick="window.location.href='profile.jsp';">Cancel</button>
					</div>
	            </div>
    		</div>   
    	</form>    
    </div>
    <footer id="footer" class="white-section">
		<div class="container-fluid copyright">
	       	<a class="social-icons" href="https://www.facebook.com/">
	       		<i class="social-icon fab fa-facebook-f fa-2x"></i>
	       	</a>
	       	<a class="social-icons" href="https://twitter.com/">
	       		<i class="social-icon fab fa-twitter fa-2x"></i>
	       	</a>
	       	<a class="social-icons" href="https://www.instagram.com/">
	       		<i class="social-icon fab fa-instagram fa-2x"></i>
	       	</a>
	       	<a class="social-icons" href="mailto:jnich56@lsu.edu">
	       		<i class="social-icon fas fa-envelope fa-2x"></i>
	       	</a>
	       	<p class="copyright-text">© Copyright 2021 Recommended Reads</p>
		</div>
	</footer>
  </body>
</html>
