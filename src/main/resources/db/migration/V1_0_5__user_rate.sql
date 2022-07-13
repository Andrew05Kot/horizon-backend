ALTER TABLE api_user
	ADD COLUMN rate int CHECK (rate BETWEEN 0 AND 100) DEFAULT 50;

UPDATE api_user SET rate = 98 WHERE id = 1;
UPDATE api_user SET rate = 70 WHERE id = 7;
UPDATE api_user SET rate = 97 WHERE id = 8;
UPDATE api_user SET rate = 96 WHERE id = 9;