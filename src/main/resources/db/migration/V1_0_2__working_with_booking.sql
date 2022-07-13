DROP TABLE IF EXISTS tour_booking;
DROP TABLE IF EXISTS booking;

CREATE TABLE booking
(
	id bigserial NOT NULL,
	status character varying(255) NOT NULL,
	tourist_id bigint NOT NULL,
	tour_id bigint NOT NULL,
	CONSTRAINT pk_booking PRIMARY KEY (id),
	CONSTRAINT fk_booking_api_user FOREIGN KEY (tourist_id)
		REFERENCES api_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE NO ACTION,
	CONSTRAINT fk_booking_tour FOREIGN KEY (tour_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE booking OWNER to horizon_owner;

INSERT INTO booking(status, tourist_id, tour_id)
VALUES ('PENDING', 2, 1);

INSERT INTO booking(status, tourist_id, tour_id)
VALUES ('ACCEPTED', 3, 2);

