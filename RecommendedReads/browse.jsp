<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    
	    <title>Browse</title>
	    <link rel="icon" href="images/logo_icon.png">
	
	    <!-- CSS Stylesheets -->
	  	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
	    <link rel="stylesheet" href="css/base.css">
	    <link rel="stylesheet" href="css/browse.css">
	    <link rel="stylesheet" href="css/ratingsreviews.css">
	    
	    <!-- Font Awesome -->
		<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.11.1/css/all.css" rel="stylesheet" />
	
	    <!-- Boot Scripts -->
	    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	  	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	  	<script src="js/script.js"></script>
	  	<script src="js/ratingsReviews.js"></script>
	  	<script src="js/stars.js"></script>
	  	<script src="js/selectRating.js"></script>
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
          			<a href="/RecommendedReads/Browse" class="navbar-brand">
            			Recommended Reads
            			<img src="images/logo_icon.png" height="28" alt="Recommended Reads">
          			</a>
          			<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            			<span class="navbar-toggler-icon"></span>
          			</button>
          			<div class="collapse navbar-collapse" id="navbarSupportedContent">
            			<ul class="navbar-nav ml-auto">
              				<li class="nav-item">
              					<a class="nav-link" href="/RecommendedReads/Profile">Profile</a>
              				</li>
              				<li class="nav-item">
              					<a class="nav-link" href="/RecommendedReads/Logout">Logout</a>
              				</li>
            			</ul>
          			</div>
        		</nav>
      		</div>
    	</section>
    	<% 
    		String selectedGenre = (String) session.getAttribute("selectedGenre"); 
    	%>
    	<form id="select-genre" class="d-flex justify-content-center align-items-center" action="/RecommendedReads/RatingsReviews" method="POST">
    		<select class="mt-4 genre col-md-2 form-control" name="genre" style="max-width: 50%" onchange="this.form.submit()">
    			<option value="all" disabled selected><%= selectedGenre %></option>
    			<option value="All Genres">All Genres</option>
    		</select>
		</form>
		<%@ page import ="java.util.ArrayList"%>
       	<% 
       	ArrayList<String> titles = (ArrayList<String>) session.getAttribute("browseTitles");
       	ArrayList<String> authors = (ArrayList<String>) session.getAttribute("browseAuthors");
       	ArrayList<String> genres = (ArrayList<String>) session.getAttribute("browseGenres");
       	ArrayList<String> pages = (ArrayList<String>) session.getAttribute("browsePages");
       	ArrayList<String> years = (ArrayList<String>) session.getAttribute("browseYears");
       	ArrayList<String> avgRatings = (ArrayList<String>) session.getAttribute("browseAvgRatings");
       	ArrayList<String> totalRatings = (ArrayList<String>) session.getAttribute("browseTotalRatings");
       	ArrayList<String> writeError = (ArrayList<String>) session.getAttribute("browseWriteError");
       	String addError = (String) session.getAttribute("browseAddError");
        if(titles.size() == 0) {
        %>
        	<p class="text mt-3 mb-3 p-0" style="color: red; text-align: center;">There are no books added for this genre yet.</p>
        <%
        }
       	for(int i = 0; i < titles.size(); i++) {
       	%>
			<div class="mt-3 mb-3 container">
			    <div class="row">
			        <div class="col-md-12">
			            <div class="pt-3 pb-3 bg-light rounded mt-2">
			                <div class="d-flex flex-row justify-content-center">
			                	<div class="d-flex justify-content-center align-items-center col-md-3">
				                	<img class="book-img" src="images/books/<%= titles.get(i) %>.png" width="140px">
				                </div>
			                	<div class="p-0 d-flex justify-content-center w-100 border-right" >
				                    <div class="d-flex flex-column justify-content-center w-100">
				                        <div>
				                            <h4><%= titles.get(i) %></h4>
				                            <h5><%= authors.get(i) %></h5>
				                        </div>
				                        <div class="here">
					                    	<script>
					                       		$(".here").addClass("bookRating"+<%= i+1 %>);
					                       		$(".here").removeClass("here");
				 								var rating = <%= avgRatings.get(i) %>;
				 								stars(rating, ".bookRating"+<%= i+1 %>, 2);
			 								</script>
			 							</div>
		 							 <% 
		 							 	if(totalRatings.get(i).equals("1")) {
		 							 %>
		 							 		<div class="rating-numbers">
				                        		<p><%= avgRatings.get(i) %> &nbsp &nbsp &nbsp <%= totalRatings.get(i) %> rating</p>
				                        	</div>
		 							 <%
		 							 }
		 							 else {
		 							 %>
					                    <div class="rating-numbers">
				                        	<p><%= avgRatings.get(i) %> &nbsp &nbsp &nbsp <%= totalRatings.get(i) %> ratings</p>
				                        </div>
				                     <%
		 							 }
		 							 %>
				                        <div>
			                        		<button class="mr-1 toggle-disabled btn btn-sm btn-primary" type="button" onclick="showOrHide('reviews' + <%= i+1 %>)">See Reviews</button>
				                       		<button class="ml-1 toggle-disabled btn btn-sm btn-primary" type="button" onclick="showOrHide('write' + <%= i+1 %>)">Write Review</button>
				                    	</div>
				                	</div>
			                    </div>
			                    <div class="d-flex col-md-3" >
			                        <div class="d-flex flex-column justify-content-center w-100">
			                            <div class="d-flex justify-content-center">
			                            	<img class="author-pic rounded-circle" src="images/authors/<%= authors.get(i) %>.png">
			                            </div>
			                            <div class="book-item">
			                            	<p class="mt-3">Genre: <%= genres.get(i) %></p>
		                            		<p class="mt-3">Pages: <%= pages.get(i) %></p>
		                            		<p class="mt-3">Year of Publication: <%= years.get(i) %></p>
		                            	</div>
			                        </div>
			                    </div>
			                </div>
			            </div>
			        </div>
				    <div class="here reviews container">
				        <script>
							$(".here").addClass("reviews"+<%= i+1 %>);
		               		$(".here").removeClass("here");
              			</script>
				        <%
				        ArrayList<ArrayList<String>> names = (ArrayList<ArrayList<String>>) session.getAttribute("browseNames");
				        ArrayList<ArrayList<String>> ratings = (ArrayList<ArrayList<String>>) session.getAttribute("browseRatings");
				       	ArrayList<ArrayList<String>> reviews = (ArrayList<ArrayList<String>>) session.getAttribute("browseReviews");
				       	ArrayList<ArrayList<String>> posted = (ArrayList<ArrayList<String>>) session.getAttribute("browsePosted");
				        %>
			            <div class="row">
			                <div class="col-md-12">
			                    <div class="card">
			                        <div class="header p-3 d-flex flex-column justify-content-center align-items-center">
			                            <h4 class="m-0">Ratings & Reviews</h4>
			                        </div>
			                        <%
			                        if(Integer.parseInt(totalRatings.get(i)) == 0) {
			                        %>
			                        	<p class="text mt-3 mb-3 p-0" style="color: red; text-align: center;">There are no ratings or reviews for this book yet.</p>
			                        <%
			                        }
							       	for(int j = 0; j < Integer.parseInt(totalRatings.get(i)); j++) {
							       	%>
				                        <div class="review">
				                            <div class="review-row pt-4 pb-4 d-flex flex-row ">
				                                <div class="mr-3 ml-1"><span class="profile-pic"><i class="fas fa-user fa-2x"></i></span></div>
				                                <div class="review-text active w-100">
				                                    <h5 class="m-0"><%= names.get(i).get(j) %></h5>
				                                    <div class="here">
								                    	<script>
								                       		$(".here").addClass("book"+<%= i+1 %>+"userRating"+<%= j+1 %>);
								                       		$(".here").removeClass("here");
							 								var rating = <%= ratings.get(i).get(j) %>;
							 								stars(rating, ".book"+<%= i+1 %>+"userRating"+<%= j+1 %>, 1);
						 								</script>
						 							</div>
				                                    <p class="text mt-2 mb-4 p-0"><%= reviews.get(i).get(j) %></p>
				                                    <p class="date m-0 p-0"><%= posted.get(i).get(j) %></p>
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
					<div class="here write container">
				        <script>
							$(".here").addClass("write"+<%= i+1 %>);
		               		$(".here").removeClass("here");
              			</script>
			            <div class="row">
			                <div class="col-md-12">
			                    <div class="card">
			                        <div class="p-3 header d-flex flex-column justify-content-center align-items-center">
			                            <h4 class="m-0">Write Review</h4>
			                        </div>
			                        <%
			        	      		if(writeError.get(i) == "err") { 
				        	      	%>
			                        	<p class="mt-3 mb-3 p-0 text" style="color: red; text-align: center;">Sorry, you can only rate and review a book once.</p>
				        	      	<%
				        	      	}
			                        else {
			                        %>
					                    <form action="/RecommendedReads/WriteReview" method="POST">
					                    	<select class="here" name="rating">
					                    		<option value="0">0</option>
												<option value="1">1</option>
												<option value="2">2</option>
												<option value="3">3</option>
												<option value="4">4</option>
												<option value="5">5</option>
												<option value="6">6</option>
											</select>
											<div style="clear:both"></div>
											<script>
					                       		$(".here").addClass("selectRating"+<%= i+1 %>);
					                       		$(".here").removeClass("here");
					                       		$(".selectRating"+<%= i+1 %>).fontstar({},function(value,self){});
			 								</script>
			                        		<input class="title form-control" type="hidden" name="title" value="<%= titles.get(i) %>">
			                        		<input class="author form-control" type="hidden" name="author" value="<%= authors.get(i) %>">
				                        	<div class="mt-4 mb-4 ml-5 mr-5 d-flex flex-row">
				                        		<input class="required form-control mr-3" type="text" name="review" placeholder="Add review" required>
								          		<div class="invalid-feedback">
								            		Cannot be left blank.
								          		</div>
					                        	<button class="btn btn-primary" type="submit">Post</button>
					                        </div>
					                    </form>
					            	<%
			                        }
			                        %>
			                    </div>
			                </div>
			            </div>
					</div>	
		    	</div>
			</div>
		<%
	    }
	    %>
	    <button class="m-3 btn btn-primary" type="button" onclick="showOrHide('addBook')">Add Book</button>
	    <%
	    if(addError == "err") {
	    	session.setAttribute("browseAddError", "");
	    %>
        	<p class="mb-3 add p-0 text" style="color: red; text-align: center;">Sorry, that book already exists.</p>
	    <%
	    }
	    else if(addError =="no") {
	    	session.setAttribute("browseAddError", "");
	    %>
	    	<p class="mt-2 mb-4 add p-0 text" style="color: green; text-align: center;">Book successfully added.</p>
	    <%
	    }
	    %>
	    <script>
	   		setTimeout(function(){
  				if ($('.add').length > 0) {
    				$('.add').remove();
  				}
			}, 10000)
	    </script>
	    <div class="addBook container">
            <div class="row justify-content-center ">
                <div class="col-md-8">
                    <div class="card align-items-center ">
                        <div class="p-3 header d-flex flex-column justify-content-center align-items-center">
                            <h4 class="m-0">Add Book</h4>
                        </div>
                        <form action="/RecommendedReads/AddBook" method="POST" class="needs-validation" novalidate>
		                    <div class="form-group">
				          		<input class="required form-control" type="text" placeholder="Title" name="title" maxlength="64" required>
				          		<div class="invalid-feedback">
				            		Cannot be left blank.
				          		</div>
				        	</div>
				        	<div class="form-group">
				          		<input class="required form-control" type="text" placeholder="Author" name="author" maxlength="32" required>
				          		<div class="invalid-feedback">
				            		Cannot be left blank.
				          		</div>
				        	</div>
				        	<div class="form-group">
		          			<select class="genre required form-control" name="genre" required><option value="" disabled selected>Genre</option></select>
							  	<div class="invalid-feedback">
					            	Cannot be left blank.
					          	</div>
				        	</div>
				        	<div class="form-group">
	          					<input class="required form-control" type="number" name="pages" placeholder="Number of Pages" min="0" max="20000" required>
				          		<div class="invalid-feedback">
				            		Cannot be left blank.
				          		</div>
				        	</div>
				        	<div class="form-group">
	          					<input class="required form-control" type="number" name="year" placeholder="Year of Publication" required>
				          		<div class="invalid-feedback">
				            		Cannot be left blank.
				          		</div>
				        	</div>
				        	<button class="mb-4 toggle-disabled btn btn-md btn-primary" type="submit">Add</button>
		                </form>
                    </div>
                </div>
            </div>
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
    	<script src="js/script.js"></script>
  	</body>
</html>
