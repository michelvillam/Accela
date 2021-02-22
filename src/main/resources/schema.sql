
CREATE TABLE IF NOT EXISTS Person(
	id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	firstName nchar(50) NOT NULL,
	lastName nchar(50) NOT NULL
);


CREATE TABLE IF NOT EXISTS Address(
	id int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	street nchar(50) NOT NULL,
	city nchar(30) NOT NULL,
	state nchar(30) NOT NULL,
	postalCode nchar(10) NOT NULL,
	person_id int not null,
	foreign key (person_id) references person(id)
);





