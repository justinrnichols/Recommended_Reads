<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	
	    <title>Sign Up</title>
	    <link rel="icon" href="images/logo_icon.png">
	
	   <!-- CSS Stylesheets -->
	  	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	    <link href="css/login_signup.css" rel="stylesheet">
	    
	    <!-- Font Awesome -->
	    <script defer src="https://use.fontawesome.com/releases/v5.11.1/js/all.js"></script>
	    
	    <!-- Boot Scripts -->
	    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
	    <script defer src="https://use.fontawesome.com/releases/v5.11.1/js/all.js"></script>
  	</head>
  	<body class="text-center">
    	<main class="form-signin">
      		<img class="m-0" class="p-0" src="images/logo.png" alt="logo" width="200" height="200">
      		<h1 class="h3 mb-3 fw-normal">Sign Up</h1>
			<%
	      		String error = (String) request.getAttribute("err");
	      		if (error != null) { 
	      	%>
	      			<p style="color: red;">Account <%=error%> already exists.</p>
	      	<%
	      		}
	      	%>
      		<form action="/RecommendedReads/SignUp" method="POST" class="needs-validation" novalidate>
        		<div class="form-group">
          			<input class="required form-control" type="text" placeholder="First Name" name="firstName" maxlength="32" required autofocus>
          			<div class="invalid-feedback">
            			Cannot be left blank.
          			</div>
        		</div>
        		<div class="form-group">
          			<input class="required form-control" type="text" placeholder="Last Name" name="lastName" maxlength="32" required>
          			<div class="invalid-feedback">
            			Cannot be left blank.
          			</div>
        		</div>
        		<div class="form-group">
          			<input class="required form-control" type="email" placeholder="Email" name="email" maxlength="32" required>
          			<div class="invalid-feedback">
            			Please provide a valid email.
          			</div>
        		</div>
        		<div class="form-group">
          			<input id="password" class="required form-control" pattern="[a-zA-Z0-9]{8,}" type="password" placeholder="New Password" name="password" minlength="8" maxlength="32" required>
          			<div class="invalid-feedback">
            			Please provide a valid password.<br>
            			Must be at least 8 characters in length.<br>
            			Must include only alphanumeric characters.
          			</div>
        		</div>
       			<div class="form-group">
          			<input class="required form-control" oninput="passwordsMatch(this)" type="password" placeholder="Confirm Password" name="confirmPassword" minlength="8" maxlength="32" required>
          			<script type='text/javascript'>
		    			function passwordsMatch(confirm) {
		        			if (confirm.value != document.getElementById('password').value) {
		        				confirm.setCustomValidity("Passwords do not match.");
		        			}
							else {
		        				confirm.setCustomValidity("");
		        			}
		    			}
		  			</script>
			  		<div class="invalid-feedback">
	            		Passwords do not match.
	          		</div>
        		</div>
	        	<p class="p mb-3 fw-normal">Answer a few questions to let us know about your reading habits.</p>
	        	<div class="form-group">
		          	<select class="genre required form-control" name="favoriteGenre" required><option value="" disabled selected>Favorite Genre</option></select>
				  	<div class="invalid-feedback">
		            	Cannot be left blank.
		          	</div>
	        	</div>
	        	<div class="form-group">
	          		<input class="required form-control" type="text" name="favoriteAuthor" placeholder="Favorite Author" minlength="1" maxlength="32" required>
	          		<div class="invalid-feedback">
	            		Cannot be left blank.
	          		</div>
	        	</div>
	        	<div class="form-group">
	          		<input class="required form-control" type="number" name="booksPerYear" placeholder="Books Read Per Year" min="0" max="9999" required>
	          		<div class="invalid-feedback">
	            		Please provide a valid number.<br>
	          		</div>
	        	</div>
        		<button class="toggle-disabled w-100 btn btn-lg btn-primary" type="submit">Sign Up</button>
        		<div class=m-4>
	          		Have an Account?
	          		<div class="btn-group">
	            		<button class="w-10 btn btn-sm btn-secondary" type="button" onclick="window.location.href='login.jsp';">Login</button>
	          		</div>
	        	</div>
      		</form>
			<p class="mt-5 mb-3 text-muted">&copy; Recommended Reads</p>
		</main>
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    	<script src="js/script.js"></script>
	</body>
</html>