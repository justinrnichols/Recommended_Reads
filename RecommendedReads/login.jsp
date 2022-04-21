<!DOCTYPE html>
<html>
	<head>
	    <meta charset="utf-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1">
	    
	    <title>Login</title>
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
      		<h1 class="h3 mb-3 fw-normal">Login</h1>
      		<%
      			String error = (String) request.getAttribute("err");
      			if (error != null) { 
      				if (error != "") {
      		%>
      					<p style="color: red;">Account <%=error%> does not exist.</p>
      		<%
      				}
      				else {
      		%>
      					<p style="color: red;">Incorrect Password</p>
      		<%	
      				}
      			}
      		%>
      		<form action="/RecommendedReads/Login" method="POST" class="needs-validation" novalidate>
        		<div class="form-group">
	          		<input class="required form-control" type="email" placeholder="Email" name="email" maxlength="32" required>
	          		<div class="invalid-feedback">
	            		Cannot be left blank.
	          		</div>
	        	</div>
	        	<div class="form-group">
	          		<input class="required form-control" pattern="[a-zA-Z0-9]{8,}" type="password" placeholder="Password" name="password" minlength="8" maxlength="32" required>
	          		<div class="invalid-feedback">
	            		Cannot be left blank.
	          		</div>
	        	</div>
	        	<button class="toggle-disabled w-100 btn btn-lg btn-primary" type="submit">Login</button>
	        	<div class=m-4>
	          		No Account?
	          		<div class="btn-group">
	            		<button class="w-10 btn btn-sm btn-secondary" type="button" onclick="window.location.href='signup.jsp';">Sign Up</button>
	          		</div>
	        	</div>
      		</form>
      		<p class="mt-5 mb-3 text-muted">&copy; Recommended Reads</p>
		</main>
    	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    	<script src="js/script.js"></script>
	</body>
</html>