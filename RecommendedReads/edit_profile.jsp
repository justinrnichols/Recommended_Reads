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
		
		<!-- Font Awesome -->
	    <script defer src="https://use.fontawesome.com/releases/v5.11.1/js/all.js"></script>
		
		<!-- Boot Scripts -->
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
		<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</head>
	<body>
		<%
			response.setHeader("Cache-Control",
					"no-cache, no-store, must-revalidate");
			if (session.getAttribute("email") == null) {
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
			<form action="/RecommendedReads/ProfileEdit1" method="POST" class="needs-validation" novalidate>
				<div class="col-md-6">
					<div class="profile-head">
						<div class="profile-img">
							<i class="fas fa-user fa-6x"></i>
						</div>
						<h2>${name}</h2>
						<ul class="nav nav-tabs" role="tablist">
							<li class="nav-item" style="width:100%;">
								<a class="nav-link active" id="account-tab" data-toggle="tab" href="#account" role="tab" aria-controls="account" aria-selected="true">Account</a>
							</li>
						</ul>
					</div>
				</div>
				<main class="form-signin">
					<div class="col-md-6">
						<div class="tab-content account-tab">
							<div class="tab-pane fade show active" id="account" role="tabpanel" aria-labelledby="account-tab">
								<%
									String error = (String) request.getAttribute("err");
									if (error != null) {
								%>
								<p style="color: red;">
									Account
									<%=error%>
									already exists.
								</p>
								<%
									}
								%>
								<div class="col-md-6">
									<label>First Name</label> 
									<input class="required form-control" type="text" value="<%=session.getAttribute("firstName")%>" name="firstName" maxlength="32" required>
									<div class="invalid-feedback">
										Cannot be left blank.
									</div>
								</div>
								<div class="col-md-6">
									<label>Last Name</label> 
									<input class="required form-control" type="text" value="<%=session.getAttribute("lastName")%>" name="lastName" maxlength="32" required>
									<div class="invalid-feedback">
										Cannot be left blank.
									</div>
								</div>
								<div class="col-md-6">
									<label>Email</label> 
									<input class="required form-control" type="email" value=<%=session.getAttribute("email")%> name="email" maxlength="32" required>
									<div class="invalid-feedback">
										Please provide a valid email.
									</div>
								</div>
								<div class="col-md-6">
									<label>Password</label> 
									<input id="password" class="required form-control" pattern="[a-zA-Z0-9]{8,}" type="password" value=<%=session.getAttribute("password")%> name="password" minlength="8" maxlength="32" required>
									<div class="invalid-feedback">
										Please provide a valid password.<br> Must be at least 8
										characters in length.<br> Must include only alphanumeric
										characters.
									</div>
								</div>
								<div class="col-md-6">
									<label>Confirm Password</label> 
									<input class="required form-control" oninput="passwordsMatch(this)" type="password" value=<%=session.getAttribute("password")%> name="confirmPassword" minlength="8" maxlength="32" required>
									<script type='text/javascript'>
										function passwordsMatch(confirm) {
											if (confirm.value != document
													.getElementById('password').value) {
												confirm
														.setCustomValidity("Passwords do not match.");
											} else {
												confirm.setCustomValidity("");
											}
										}
									</script>
									<div class="invalid-feedback">
										Passwords do not match.
									</div>
								</div>
								<div class="col-md-6">
									<label>Favorite Genre</label> 
									<select class="genre required form-control" name="favoriteGenre" required>
										<%
											String[] genres = { "Fiction", "Nonfiction", "Action & Adventure",
													"Autobiography", "Biography", "Business", "Children\'s",
													"Classic", "Comic & Graphic Novel", "Coming-of-age",
													"Comedy", "Crime & True Crime", "Diary", "Drama",
													"Dystopian", "Educational", "Fantasy", "Health & Fitness",
													"Historical Fiction", "Home & Cooking & Garden", "Horror",
													"Memoir", "Mystery", "Poetry", "Political", "Romance",
													"Religious & Spirituality", "Satire", "Science Fiction",
													"Self Help", "Short Story & Folklore",
													"Sports & Leisure & Travel", "Thriller & Suspense",
													"Western", "Young Adult" };
											for (int i = 0; i < genres.length; i++) {
												if (((String) session.getAttribute("favoriteGenre"))
														.equals(genres[i])) {
										%>
										<option value="<%=genres[i]%>" selected><%=session.getAttribute("favoriteGenre")%></option>
										<%
											break;
												}
											}
										%>
									</select>
									<div class="invalid-feedback">
										Cannot be left blank.
									</div>
								</div>
								<div class="col-md-6">
									<label>Favorite Author</label> 
									<input class="required form-control" type="text" name="favoriteAuthor" value="<%=session.getAttribute("favoriteAuthor")%>" maxlength="32" required>
									<div class="invalid-feedback">
										Cannot be left blank.
									</div>
								</div>
								<div class="col-md-6">
									<label>Books Read Per Year</label> 
									<input class="required form-control" type="number" name="booksPerYear" value=<%=session.getAttribute("booksPerYear")%> min="0" max="9999" required>
									<div class="invalid-feedback">
										Please provide a valid number.<br>
									</div>
								</div>
								<div class="pr-5 btn-group general-btn">
									<button class="w-10 btn btn-md btn-success" type="submit">Save</button>
								</div>
								<div class="pl-5 btn-group general-btn">
									<button class="w-10 btn btn-md btn-secondary" type="button" onclick="window.location.href='profile.jsp';">Cancel</button>
								</div>
							</div>
						</div>
					</div>
				</main>
				<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
				<script src="js/script.js"></script>
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
