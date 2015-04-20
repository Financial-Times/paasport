CREATE TABLE cluster (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	owner TEXT,
	metadata TEXT,
);

CREATE TABLE machines (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT,
	cpu INTEGER,
	memory INTEGER,
	disk INTEGER,
	region TEXT,
	state TEXT,		--should be an int really, but thats for later
	metadata TEXT,
	cluster_id INTEGER,
	-- Enforce people assigning machines to a cluster
	FOREIGN KEY (cluster_id) REFERENCES cluster(id)
);

CREATE TABLE manage (
	spare_machines INTEGER
);


--now let us create a holding place for spare machines or "provision ready" machines
INSERT INTO cluster VALUES (NULL, 'spare', 'The Man.', 'THis is the spare machine nursery');
