--
-- Creating 'horizon_user' table ('user' table)
--
CREATE TABLE horizon_user
(
	id bigserial NOT NULL,
	social_id character varying(255),
	email character varying(255) NOT NULL,
	phone_number character varying(13),
	first_name character varying(255) NOT NULL,
	last_name character varying(255) NOT NULL,
	image_url character varying(255),
	social_type character varying(255),
	locale character varying(255) NOT NULL,
	role character varying(255) NOT NULL,
	birth_date TIMESTAMP WITH TIME ZONE,
	CONSTRAINT pk_horizon_user PRIMARY KEY (id),
	CONSTRAINT uk_horizon_user_unique_social_id UNIQUE (social_id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE horizon_user OWNER to horizon_owner;
--
-- Creating table 'user_roles'
--
CREATE TABLE user_role
(
	user_id bigint NOT NULL,
	role character varying(255) NOT NULL,
	CONSTRAINT pk_user_role PRIMARY KEY(user_id, role),
	CONSTRAINT fk_user_role_horizon_user FOREIGN KEY (user_id)
		REFERENCES horizon_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE user_role OWNER to horizon_owner;