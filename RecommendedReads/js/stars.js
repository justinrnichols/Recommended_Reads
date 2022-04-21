function stars(rating, className, size) {
	if (size === 2) {
		var emptyStar = "<i class=\"far fa-star fa-2x\"></i>";
		var fullStar = "<i class=\"fas fa-star fa-2x\"></i>";
		var halfStar = "<i class=\"fas fa-star-half-alt fa-2x\"></i>";
	}
	else {
		var emptyStar = "<i class=\"far fa-star\"></i>";
		var fullStar = "<i class=\"fas fa-star\"></i>";
		var halfStar = "<i class=\"fas fa-star-half-alt\"></i>";
	}
	  
	var wholeNumber = Math.floor(rating);
	var fractionNumber = rating - Math.floor(rating);
	
	for (var i = 0; i < wholeNumber; i++) {
		$(className).append(fullStar);
	}
	if (fractionNumber >= 0.25 && fractionNumber < 1) {
		$(className).append(halfStar);
		for(var i = 0; i < 5-wholeNumber-1; i++) {
			$(className).append(emptyStar);
		}
	}
	else {
		for (var i = 0; i < 5-wholeNumber; i++) {
			$(className).append(emptyStar);
		}
	}
}