CREATE TABLE cluster (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	owner TEXT,
	metadata TEXT
);

--now let us create a holding place for spare machines or "provision ready" machines
INSERT INTO cluster VALUES (NULL, 'spare', 'The Man.', 'THis is the spare machine nursery');
INSERT INTO cluster VALUES (NULL, 'testing', 'The Tester.', 'A test cluster');
