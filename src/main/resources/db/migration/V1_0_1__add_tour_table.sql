--
-- Creating table 'tour'
--
CREATE TABLE tour
(
	id bigserial NOT NULL,
	name character varying(255) NOT NULL,
	description character varying(10000) NOT NULL,
	rate int CHECK (rate BETWEEN 0 AND 100) DEFAULT 50,
	CONSTRAINT pk_tour PRIMARY KEY (id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE tour OWNER to horizon_owner;