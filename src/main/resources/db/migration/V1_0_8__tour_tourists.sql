CREATE TABLE IF NOT EXISTS tour_tourist
(
	tour_id bigint,
	tourist_id bigint,
	CONSTRAINT pk_tour_tourist PRIMARY KEY (tour_id, tourist_id),
	CONSTRAINT fk_tour_tourist_to_tourist FOREIGN KEY (tourist_id)
		REFERENCES api_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	CONSTRAINT fk_tour_tourist_to_tour FOREIGN KEY (tour_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE) TABLESPACE pg_default;
ALTER TABLE tour_tourist OWNER to horizon_owner;

ALTER TABLE tour
	ADD COLUMN free_places_count int DEFAULT 1;