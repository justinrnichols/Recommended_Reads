function showDiv(element) {
	element.style.display = "block";
}

function closeDiv(element) {
	element.style.display = "none";
}

function showOrHide(current) {
	var element = document.getElementsByClassName(current)[0];
	if (element.style.display === "block")
		closeDiv(element);
	else
		showDiv(element);
}