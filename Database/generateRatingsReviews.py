import random

books = ["A Separate Peace", "A Series of Unfortunate Events", "Becoming", "Billy Summers",
"Breaking Dawn", "Catching Fire", "Dolores Claiborne", "Eclipse", "Harry Potter and the Chamber of Secrets",
"Harry Potter and the Deathly Hallows", "Harry Potter and the Goblet of Fire",
"Harry Potter and the Half Blood Prince", "Harry Potter and the Order of the Phoenix",
"Harry Potter and the Prisoner of Azkaban", "Harry Potter and the Sorcerer's Stone", "It",
"Memoirs of a Geisha", "Midnight Sun", "Mockingjay", "Modern Comfort Food A Barefoot Contessa Cookbook",
"New Moon", "The Cuckoo's Calling", "The Hunger Games", "The Shining", "The Things They Carried",
"The Witches Salem, 1962", "Their Eyes Were Watching God", "To Kill a Mockingbird", "Twilight",
"The Fault in Our Stars"]

authors = ["John Knowles", "Lemony Snicket", "Michelle Obama", "Stephen King", "Stephenie Meyer",
"Suzanne Collins", "Stephen King", "Stephenie Meyer", "J. K. Rowling", "J. K. Rowling",
"J. K. Rowling", "J. K. Rowling", "J. K. Rowling", "J. K. Rowling", "J. K. Rowling",
"Stephen King", "Arthur Golden ", "Stephenie Meyer", "Suzanne Collins", "Ina Garten",
"Stephenie Meyer", "Robert Galbraith", "Suzanne Collins", "Stephen King", "Tim O'Brien",
"Stacy Schiff", "Zora Neale Hurston", "Harper Lee", "Stephenie Meyer", "John Green"]

users = ["amberbuetter@gmail.com", "anniexia@gmail.com", "brennanhagan@gmail.com",
"carsonwilson@gmail.com", "chrisnichols@gmail.com", "hannahwagner@gmail.com",
"huntermeatte@gmail.com", "juliethawkes@gmail.com", "lexiedunham@gmail.com",
"libbyharrison@gmail.com", "madisoncancienne@gmail.com", "matthewmccoy@gmail.com",
"mikeanderson@gmail.com ", "nicholsrjustin@gmail.com", "rachelkitchen@gmail.com",
"test@gmail.com"]

ratings = [0, 1, 2, 3, 4, 5]

goodReviews = ["I loved it!", "Enjoyed greatly.", "What a fantastic read.", 
"Great character development.", "I read it so quickly it was that good.",
"Best book I've read in a long time.", "Loved every page.",
"One of my favorite books.", "I would highly recommend.", "Amazingly written!"]

okayReviews = ["It was good.", "Average, but could be better.", "Read over the weekend, it was okay.",
"Writing could be improved.", "Overall, it was fine.", "Not the best book ever, but I recommend it.",
"Worth the read if you enjoy this genre.", "Good for what it was.", "Not the best, not the worst.", 
"I've read better."]

badReviews = ["I disliked it!", "Very hard to follow.", "This book was terrible.",
"I would not recommend.", "Do not read, please.", "Terrible writing.", "I despised every page.",
"Not worth buying or reading.", "Avoid if you can.", "A waste of time reading it.",
"Choose any other book because this book was not enjoyable."]

months = [10, 11]

days = list(range(32))

hours = list(range(24))

minutes = list(range(60))

seconds = list(range(60))

alreadyExists = []
zero_count = 0
one_count = 0

while len(alreadyExists) < 280:
	randBookAuthor = random.randint(0, 29)
	randUser = random.randint(0, 15)
	randRating = random.randint(0, 5)
	randReview = random.randint(0, 9)
	randMonth = random.randint(0, 1)
	if randMonth == 0:
		randDay = random.randint(1, 31)
	else:
		randDay = random.randint(1, 30)
	randHour = random.randint(1, 23)
	randMinute = random.randint(1, 59)
	randSecond = random.randint(1, 59)

	selectedBook = books[randBookAuthor]
	selectedAuthor = authors[randBookAuthor]
	selectedUser = users[randUser]

	selectedRating = ratings[randRating]

	if selectedRating == 0 or selectedRating == 1:
		selectedReview = badReviews[randReview]
	elif selectedRating == 2 or selectedRating == 3:
		selectedReview = okayReviews[randReview]
	else: 
		selectedReview = goodReviews[randReview]

	selectedMonth = months[randMonth]
	selectedDay = days[randDay]
	selectedHour = hours[randHour]
	selectedMinute = minutes[randMinute]
	selectedSecond = seconds[randSecond]

	if selectedRating == 0:
		zero_count += 1
	elif selectedRating == 0:
		one_count += 1
	if selectedRating == 0 and zero_count/280 >= 0.02 or selectedRating == 1 and one_count/280 >= 0.1:
		continue

	if (selectedUser, selectedBook) in alreadyExists:
		continue
	else:
		alreadyExists.append((selectedUser, selectedBook))
		with open("ratingsreviews.txt", "w") as file:
			sql = 'INSERT INTO ratings_reviews (email, title, author, rating, review, posted) VALUES ("' + \
			selectedUser + '", "' + selectedBook + '", "' + selectedAuthor + '", "' + str(selectedRating) + '", "' \
			+ str(selectedReview) + '", "2021-' + str(selectedMonth) + '-' + str(selectedDay) + ' ' + str(selectedHour) + ':' + \
			str(selectedMinute) + ':' + str(selectedSecond) + '");' 
			file.write(sql + '\n') 

