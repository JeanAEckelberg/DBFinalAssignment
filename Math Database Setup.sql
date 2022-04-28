-- drop tables, functions and views first
drop table if exists userTable cascade;
drop table if exists topic cascade;
drop table if exists question cascade;
drop table if exists answer cascade;
drop table if exists test cascade;
drop table if exists leaderboard, answerToQuestion, questionInTopic, topicInTest, questionInTest;
drop view if exists HighScores;
drop function if exists getUserQuestionsAndAnswers;
drop function if exists getUserTests;
drop function if exists getUserTopics;

-- create tables if needed
create table if not exists userTable (
	userID serial primary key,
	username varchar(25) unique not null,
	password varchar(30) not null,
	permissions int default 0
);

create table if not exists topic (
	topicID serial primary key,
	topicName varchar(50) unique not null,
	topicDescription varchar(500) not null,
	creator int,
	foreign key (creator) references userTable (userID)
);

create table if not exists question (
	questionID serial primary key,
	questionText varchar(200) unique not null,
	creator int,
	foreign key (creator) references userTable (userID)
);

create table if not exists answer (
	answerID serial primary key,
	answerText varchar(200) unique not null,
	creator int,
	foreign key (creator) references userTable(userID)
);

create table if not exists test (
	testID serial primary key,
	numberOfQuestions int not null,
	creator int,
	foreign key (creator) references userTable (userID)
);

create table if not exists leaderboard (
	userID int,
	testID int,
	score real,
	timeElapsed interval, -- intervals are written in 'HH:mm:ss.s' where m is minutes and .s is fractions of a second
	primary key (userID, testID),
	foreign key (userID) references userTable (userID),
	foreign key (testID) references test (testID)
);

create table if not exists answerToQuestion (
	questionID int,
	answerID int,
	correct boolean default false,
	primary key (questionID, answerID),
	foreign key (questionID) references question (questionID),
	foreign key (answerID) references answer (answerID)
);

create table if not exists questionInTopic (
	questionID int,
	topicID int,
	primary key (questionID, topicID),
	foreign key (questionID) references question (questionID),
	foreign key (topicID) references topic (topicID)
);

create table if not exists topicInTest(
	topicID int,
	testID int,
	primary key (topicID, testID),
	foreign key (topicID) references topic (topicID),
	foreign key (testID) references test (testID)
);

create table if not exists questionInTest(
	questionID int,
	testID int,
	primary key (questionID, testID),
	foreign key (questionID) references question (questionID),
	foreign key (testID) references test (testID)
);

-- create views and functions
create view HighScores as
	select userName, testID, score, timeElapsed
	from leaderboard, userTable
	where leaderboard.userID = userTable.userID
	order by score;


create or replace function getUserQuestionsAndAnswers ( userID int)
	returns table (quesstionID int, answerID int)
	as $$
		begin
			return query
 
			select answerToQuestion.questionID, answerToQuestion.answerID 
			from answerToQuestion
			where questionID in (select questionID from question where $1 = creator);
			
		end;
	   $$
	language 'plpgsql';
	
create or replace function getUserTests ( userID int)
	returns table (testID int)
	as $$
		begin
			return query
 
			select test.testID
			from test
			where $1 = creator;
			
		end;
	   $$
	language 'plpgsql';
	
create or replace function getUserTopics ( userID int)
	returns table (topicID int)
	as $$
		begin
			return query
 
			select topic.topicID
			from topic
			where $1 = creator;
			
		end;
	   $$
	language 'plpgsql';


-- insert initial values into tables
insert into userTable (username, password, permissions)
values ('BigMoney', 'salvia', 0),
	   ('Wingmat', 'key123', 0),
	   ('GrayCrypto', 'BTC2Moon', 0),
	   ('Milksoplimit', 'p455word', 2),
	   ('Amogus', 'sus', 1),
	   ('TileJumper', 'slateKitchen', 1),
	   ('Cardog', 'theWrit31ne', 1);
	   
	   
insert into topic (topicName, topicDescription, creator)
values ('Symbolic Logic', 'Symbolic logic is a way to represent 
		logical expressions by using symbols and variables in place of 
		natural language, such as English, in order to remove vagueness.', 4),
		
	   ('Proofs', 'Proof, in logic, an argument that establishes the validity of a proposition.', 4),
	   
	   ('Sets', 'A set is the mathematical model for a collection of different 
		things; a set contains elements or members, which can be mathematical 
		objects of any kind: numbers, symbols, points in space, lines, other 
		geometrical shapes, variables, or even other sets.', 7);
	   
insert into question (questionText, creator)
values ('A ^ B', 4),
	   ('The Empty Set contains no elements', 4),
	   ('A v B', 4),
	   ('P→Q is true if P is false is what kind of proof?', 1),
	   ('Z represents the Natural (or counting) Numbers', 5);
	   
insert into answer (answerText, creator)
values ('True', 4),
	   ('False', 4),
	   ('Conjunction', 4),
	   ('Disjunction', 4),
	   ('Not a logical expression', 4),
	   ('Vacuous proof', 4),
	   ('Trivial proof', 4);
	   
insert into answerToQuestion (questionID, answerID, correct)
values (1, 3, true),
	   (1, 4, false),
	   (1, 5, false),
	   (2, 1, true),
	   (2, 2, false),
	   (3, 3, false),
	   (3, 4, true),
	   (3, 5, false),
	   (4, 6, true),
	   (4, 7, false),
	   (5, 1, false),
	   (5, 2, true);
	   
insert into test (numberOfQuestions, creator)
values (5, 1),
	   (2, 6);
	   
insert into questionInTopic (questionID, topicID)
values (1,1),
	   (2,3),
	   (3,1),
	   (4,2),
	   (5,3);
	   
insert into topicInTest (testID, topicID)
values (1, 1),
	   (1, 2),
	   (1, 3),
	   (2, 1);
	   
insert into questionInTest (testID, questionID)
values (1,1),
	   (1,2),
	   (1,3),
	   (1,4),
	   (1,5),
	   (2,1),
	   (2,3);
	   
insert into leaderboard (userID, testID, score, timeElapsed)
values (4, 1, 100.0, '00:01:02.1'); -- intervals are written in 'HH:mm:ss.s' where m is minutes and .s is fractions of a second

/* Sample Quereys for Demonstration purposes */
--select * from HighScores;

--select * from getUserQuestionsAndAnswers(4);

--select * from getUserTests(6);

--select * from getUserTopics(7);

--select userID from userTable where username = 'Milksoplimit' and password = 'p455word';
