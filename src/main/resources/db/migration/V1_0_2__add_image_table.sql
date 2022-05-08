--
-- Creating table 'tour'
--
CREATE TABLE image
(
	id bigserial NOT NULL,
	image_name character varying(255) NOT NULL,
	mime_type character varying(255) NOT NULL,
	original_name character varying(255) NOT NULL,
	CONSTRAINT pk_image PRIMARY KEY (id)
)
TABLESPACE pg_default;
ALTER TABLE image OWNER to horizon_owner;
