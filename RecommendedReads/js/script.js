(function () {
  'use strict'
    var forms = document.querySelectorAll('.needs-validation')
    var nextPage = $("#signUp")
      Array.prototype.slice.call(forms)
        .forEach(function (form) {
          form.addEventListener('submit', function (event) {
            if (!form.checkValidity()) {
              event.preventDefault()
              event.stopPropagation()
            }
            else {
                nextPage.attr("type", "signUp2.html")
            }
            
            form.classList.add('was-validated')
          }, false)
        })
})()

var values = ["Fiction", "Nonfiction", "Action & Adventure", "Autobiography", "Biography", 
              "Business", "Children\'s", "Classic", "Comic & Graphic Novel", "Coming-of-age", 
              "Comedy", "Crime & True Crime", "Diary", "Drama", "Dystopian", "Educational", 
              "Fantasy", "Health & Fitness", "Historical Fiction", "Home & Cooking & Garden", 
              "Horror", "Memoir", "Mystery", "Poetry", "Political", "Romance", 
              "Religious & Spirituality", "Satire", "Science Fiction", "Self Help", 
              "Short Story & Folklore", "Sports & Leisure & Travel", "Thriller & Suspense", 
              "Western", "Young Adult"];

var select = $(".genre");

for (var i = 0; i < values.length; i++) {
	for (var j = 0; j < values.length; j++){
		var option = document.createElement("option");
		option.value = option.innerHTML = values[j];
		select[i].appendChild(option);
	}
}