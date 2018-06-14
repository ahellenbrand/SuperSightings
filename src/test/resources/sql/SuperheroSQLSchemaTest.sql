DROP DATABASE IF EXISTS SuperheroSightings;
CREATE DATABASE SuperheroSightings;

USE SuperheroSightings;

CREATE TABLE `Super` (
	SuperId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	FirstName VARCHAR(50) NULL,
	LastName VARCHAR(50) NULL,
	Alignment TINYINT(2) NOT NULL,
	Description VARCHAR(150) NULL,
	Image VARCHAR(500) NULL
);

CREATE TABLE Ability (
	AbilityId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	Description VARCHAR(150) NULL
);

CREATE TABLE AbilitySuper (
	SuperId INT NOT NULL,
	AbilityId INT NOT NULL,
    PRIMARY KEY (SuperId, AbilityId),
	CONSTRAINT fk_AbilitySuper_SuperId
		FOREIGN KEY (SuperId)
		REFERENCES Super(SuperId),
	CONSTRAINT fk_AbilitySuper_AbilityId
		FOREIGN KEY (AbilityId)
		REFERENCES Ability(AbilityId)
);

CREATE TABLE Location (
	LocationId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NULL,
    Latitude DECIMAL(10,8) NOT NULL,
    Longitude DECIMAL(11,8) NOT NULL
);

CREATE TABLE Organization (
	OrganizationId INT PRIMARY KEY AUTO_INCREMENT,
	`Name` VARCHAR(50) NOT NULL,
	Description VARCHAR(500) NULL,
	LocationId INT NULL,
	CONSTRAINT fk_Organization_Location
		FOREIGN KEY (LocationId)
		REFERENCES Location(LocationId)
);

CREATE TABLE OrganizationSuper (
	SuperId INT NOT NULL,
	OrganizationId INT NOT NULL,
    PRIMARY KEY (SuperId, OrganizationId),
	CONSTRAINT fk_OrganizationSuper_Super
		FOREIGN KEY (SuperId)
		REFERENCES Super(SuperId),
	CONSTRAINT fk_OrganizationSuper_Organization
		FOREIGN KEY (OrganizationId)
		REFERENCES Organization(OrganizationId)
);

CREATE TABLE Sighting (
	SightingId INT PRIMARY KEY AUTO_INCREMENT,
	Description VARCHAR(500) NULL,
	`Date` DATE NOT NULL,
	`Time` TIME NULL,
	LocationId INT NOT NULL,
	CONSTRAINT fk_Sighting_Location
		FOREIGN KEY (LocationId)
		REFERENCES Location(LocationId)
);

CREATE TABLE SightingSuper (
	SuperId INT NOT NULL,
	SightingId INT NOT NULL,
    PRIMARY KEY (SuperId, SightingId),
	CONSTRAINT fk_SightingSuper_Super
		FOREIGN KEY (SuperId)
		REFERENCES `Super`(SuperId),
	CONSTRAINT fk_SightingSuper_Sighting
		FOREIGN KEY (SightingId)
		REFERENCES Sighting(SightingId)
);

INSERT INTO Location (`Name`, Latitude, Longitude) VALUES
	('X-Mansion', 41.26999892, -73.551664),
    ('Avengers Facility', 42.652580, -73.756233),
    ('Times Square', 40.758896, -73.985130),
    ('NY Ice Cream Shop', 40.790885, -73.9747115),
    ('Attica Correctional Facility', 42.843163, -78.269665),
    ('Streetside', 43.009305, -76.139715);

INSERT INTO Sighting (Description, `Date`, `Time`, LocationId) VALUES
	('They went into the trees and disappeared.', '2017-10-29', '14:57', 1),
    ('They appeared to be returning from a stroll.', '2017-10-14', '4:58', 1),
    ('I saw a lightning strike and then a man flew away with his hammer.', '2017-10-07', '0:39', 2),
    ('I watched the Guardians of the Galaxy spaceship land here.', '2017-09-08', '19:22', 2),
    ('Looked like he was fighting a weird alien monster.', '2017-09-16', '18:19', 3),
    ('They were eating some ice cream for breakfast.', '2017-10-14', '7:01', 4),
    ('They were both holding the phones to their ears in the visitors\' room but it didn\'t look like they were speaking.', '2018-01-12', '17:00', 5),
    ('He was enjoying some ice cream.', '2017-09-20', '8:00', 4),
    ('He was reading a book and drinking some coffee with his ice cream.', '2018-04-05', '8:00', 4),
    ('Looked like he was enjoying a nice walk.', '2018-03-14', '13:00', 3),
    ('He was dancing in a flash mob demonstration.', '2018-04-12', '12:00', 3);
    
INSERT INTO `Super` (`Name`, FirstName, LastName, Alignment, Description, Image) VALUES
	('Professor X', 'Charles', 'Xavier', 1, 'Bald, enjoys teaching mutants, and uses a wheelchair.', 'http://i.annihil.us/u/prod/marvel/i/mg/3/e0/528d3378de525.jpg'),
    ('Groot', NULL, NULL, 1, 'Friends with Rocket Raccoon.  Only known phrase "I am Groot".', 'http://i.annihil.us/u/prod/marvel/i/mg/3/10/526033c8b474a.jpg'),
    ('Thor', 'Thor', NULL, 1, 'Has a hammer and likes to point out that nobody else can pick it up.', 'http://i.annihil.us/u/prod/marvel/i/mg/d/d0/5269657a74350.jpg'),
    ('Wolverine', 'James', 'Howlett', 1, 'Has a grumpy disposition.  His bones are reinforced with adamantium and has blades coming out of his hands.', 'http://i.annihil.us/u/prod/marvel/i/mg/2/60/537bcaef0f6cf.jpg'),
    ('Star-Lord', 'Peter', 'Quill', 1, 'Enjoys listening to old earth music.', 'http://i.annihil.us/u/prod/marvel/i/mg/9/a0/537bc55e8b1f5.jpg'),
    ('Magneto', 'Max', 'Eisenhardt', 2, 'Had a rough childhood and now likes to take it out on everyone else.', 'http://i.annihil.us/u/prod/marvel/i/mg/3/b0/5261a7e53f827.jpg'),
    ('Deadpool', NULL, NULL, 0, 'Junk hero to see if I can delete.', 'http://i.annihil.us/u/prod/marvel/i/mg/9/90/5261a86cacb99.jpg');

INSERT INTO SightingSuper (SuperId, SightingId) VALUES
	(1,1),
    (4,1),
    (1,2),
    (4,2),
    (3,3),
    (2,4),
    (5,4),
    (3,5),
    (2,6),
    (5,6),
    (1,7),
    (6,7),
    (2,8),
    (2,9),
    (3,10),
    (3,11);
    
INSERT INTO Ability (`Name`, Description) VALUES
	('Telepathy','Ability to induce mental illusions in others, temporary, mental or physical paralysis, and loss of memories.'),
	('Mental Bolts', 'Ability to produce bolts via the mind.  Bolts can stun or render a person unconscious.'),
	('Mutant Sense', 'Can sense other superhuman mutants within a small radius.'),
    ('Body Regeneration', 'Allows a super being to re-grow limbs or even their entire form.'),
    ('Plant Telepathy', 'Can mentally communicate with plant life and mentally control plants.'),
    ('Storm Control', 'Allows a super being or their weapon to summon and control the powers of the storm, causing rain, wind, thunder, and lightning.'),
    ('Anti-Force', 'Allows a super being or their weapon to absorb energies into itself and then release them.'), 
    ('Healing Factor', 'Ability to completely heal damaged or destroyed areas of a super being\'s body.'),
    ('Bone Claws', 'Can release six one-foot bone claws from their hands at will.'),
    ('Acute Senses', 'Enhanced ability to sense with the five senses at a radius larger than a normal human\'s.'),
    ('Super Suit', 'The costume the super being wears grants them additional abilities.'),
    ('Super Vehicle', 'The vehicle the super being commutes in grants them additional abilities.'),
    ('Magnetism Manipulation', 'Allows super being to generate and control magnetic force fields.'),
    ('Flight', 'Ability to fly through the air.  Sometimes under certain conditions or with certain equipment.');

INSERT INTO AbilitySuper (SuperId, AbilityId) VALUES
	(1,1),
	(1,2),
	(1,3),
	(2,4),
	(2,5),
	(3,6),
	(3,7),
	(3,14),
	(4,8),
	(4,9),
	(4,10),
	(5,8),
	(5,11),
	(5,12),
	(6,11),
	(6,13),
	(6,14);

INSERT INTO Organization (`Name`, Description, LocationId) VALUES
	('X-Men', 'The X-Men fight for peace and equality between normal humans and mutants.', 1),
	('Guardians of the Galaxy', 'A team of interstellar heroes that tries to proactively protect the galaxy.', NULL),
	('Avengers', 'A team of earth-based super heroes that attempts to protect Earth from evil super beings.', 2),
	('Brotherhood of Evil Mutants','This organization consists of mutant super beings that believe mutants are superior to humans.  Their motivations range from world domination to terrorist activities towards anti-mutant public figures.', NULL);

INSERT OrganizationSuper (SuperId, OrganizationId) VALUES
	(1,1),
    (4,1),
    (2,2),
    (5,2),
    (3,3),
    (6,4);