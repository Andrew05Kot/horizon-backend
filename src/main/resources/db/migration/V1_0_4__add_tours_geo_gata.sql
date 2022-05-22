--
-- Creating relation table 'geo_data'
--

CREATE TABLE geo_data
(
	id bigserial NOT NULL,
	latitude numeric NOT NULL,
	longitude numeric NOT NULL,
	altitude numeric NOT NULL,
	address_name character varying(255) NOT NULL,
	CONSTRAINT pk_geo_data PRIMARY KEY (id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE geo_data OWNER to horizon_owner;


ALTER TABLE tour
	ADD COLUMN geo_data_id bigint UNIQUE,
    ADD CONSTRAINT fk_geo_data_id_tour FOREIGN KEY (geo_data_id)
    REFERENCES geo_data (id) MATCH SIMPLE
		ON UPDATE CASCADE
        ON DELETE CASCADE;