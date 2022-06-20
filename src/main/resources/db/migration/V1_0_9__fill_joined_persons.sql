INSERT INTO tour_tourist
	SELECT tour_id, tourist_id
	FROM booking
	WHERE status='ACCEPTED' or status='DONE';