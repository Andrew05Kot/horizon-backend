ALTER TABLE tour
	ADD COLUMN event_date timestamp with time zone NOT NULL DEFAULT '2022-06-30 09:00:00+02';

UPDATE tour SET event_date = '2022-07-01 08:00:00+02' WHERE id = 1;
UPDATE tour SET event_date = '2022-07-07 08:00:00+02' WHERE id = 2;
UPDATE tour SET event_date = '2022-07-01 10:00:00+02' WHERE id = 3;
UPDATE tour SET event_date = '2022-07-30 19:00:00+02' WHERE id = 4;
UPDATE tour SET event_date = '2022-08-01 06:00:00+02' WHERE id = 5;