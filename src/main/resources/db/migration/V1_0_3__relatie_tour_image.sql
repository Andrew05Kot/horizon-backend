--
-- Creating relation table 'tour_image'
--
CREATE TABLE tour_image
(
	tour_id bigint,
	image_id bigint,
	CONSTRAINT pk_tour_image PRIMARY KEY (tour_id, image_id),
	CONSTRAINT fk_tour_image_to_image FOREIGN KEY (image_id)
		REFERENCES image (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	CONSTRAINT fk_tour_image_to_tour FOREIGN KEY (tour_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE tour_image OWNER to horizon_owner;