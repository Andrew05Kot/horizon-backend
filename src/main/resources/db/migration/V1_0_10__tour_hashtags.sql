CREATE TABLE IF NOT EXISTS hashtag
(
	tour_id bigint,
	hashtag character varying(255) NOT NULL,
	hashtag_key character varying(255) NOT NULL,
	CONSTRAINT pk_hashtag_key PRIMARY KEY (hashtag_key),
)
WITH (OIDS = FALSE) TABLESPACE pg_default;
ALTER TABLE tour_label OWNER to horizon_owner;