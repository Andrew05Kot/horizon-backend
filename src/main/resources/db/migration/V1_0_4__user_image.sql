ALTER TABLE api_user
	ADD COLUMN image_id bigint,
    ADD CONSTRAINT fr_api_user_to_image FOREIGN KEY (image_id)
        REFERENCES image (id)
            ON UPDATE NO ACTION
            ON DELETE SET NULL;