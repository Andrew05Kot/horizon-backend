CREATE TABLE api_user
(
	id bigserial NOT NULL,
	social_id character varying(255),
	email character varying(255) NOT NULL,
	phone_number character varying(13),
	first_name character varying(255) NOT NULL,
	last_name character varying(255) NOT NULL,
	birthday timestamp with time zone,
	about_me character varying(10000),
	image_url character varying(255),
	social_type character varying(255),
	locale character varying(2) NOT NULL,
	role character varying(255) NOT NULL,
	birth_date TIMESTAMP WITH TIME ZONE,
	CONSTRAINT pk_api_user PRIMARY KEY (id),
	CONSTRAINT uk_api_user_unique_social_id UNIQUE (social_id)
)
WITH (OIDS = FALSE) TABLESPACE pg_default;
ALTER TABLE api_user OWNER to horizon_owner;


CREATE TABLE tour
(
	id bigserial NOT NULL,
	name character varying(255) NOT NULL,
	description character varying(10000) NOT NULL,
	rate int CHECK (rate BETWEEN 0 AND 100) DEFAULT 50,
	owner_id bigint NOT NULL,
	CONSTRAINT pk_tour PRIMARY KEY (id),
	CONSTRAINT fk_tour_api_user FOREIGN KEY (owner_id)
		REFERENCES api_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE tour OWNER to horizon_owner;


CREATE TABLE image
(
	id bigserial NOT NULL,
	image_name character varying(255) NOT NULL,
	mime_type character varying(255) NOT NULL,
	original_name character varying(255) NOT NULL,
	CONSTRAINT pk_image PRIMARY KEY (id)
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE image OWNER to horizon_owner;


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
WITH (OIDS = FALSE) TABLESPACE pg_default;
ALTER TABLE tour_image OWNER to horizon_owner;


CREATE TABLE geo_data
(
	id bigserial NOT NULL,
	latitude numeric NOT NULL,
	longitude numeric NOT NULL,
	altitude numeric NOT NULL,
	address_name character varying(255) NOT NULL,
	CONSTRAINT pk_geo_data PRIMARY KEY (id)
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE geo_data OWNER to horizon_owner;


ALTER TABLE tour
	ADD COLUMN geo_data_id bigint UNIQUE,
    ADD CONSTRAINT fk_geo_data_id_tour FOREIGN KEY (geo_data_id)
    REFERENCES geo_data (id) MATCH SIMPLE
		ON UPDATE CASCADE
        ON DELETE CASCADE;

CREATE TABLE booking
(
	id bigserial NOT NULL,
	description character varying(5000) NOT NULL,
	status character varying(255) NOT NULL,
	rate int CHECK (rate BETWEEN 0 AND 100) DEFAULT 0,
	tourist_id bigint NOT NULL,
	CONSTRAINT pk_booking PRIMARY KEY (id),
	CONSTRAINT fk_booking_api_user FOREIGN KEY (tourist_id)
		REFERENCES api_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE booking OWNER to horizon_owner;


CREATE TABLE tour_booking
(
	tour_id bigint,
	booking_id bigint,
	CONSTRAINT pk_tour_booking PRIMARY KEY (tour_id, booking_id),
	CONSTRAINT fk_tour_booking_tour FOREIGN KEY (tour_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	CONSTRAINT fk_booking_tour_booking FOREIGN KEY (booking_id)
		REFERENCES booking (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE)	TABLESPACE pg_default;
ALTER TABLE tour_booking OWNER to horizon_owner;


CREATE TABLE email_letter
(
	id bigserial NOT NULL,
	recipient_id bigint NOT NULL,
	status character varying(255) NOT NULL,
	CONSTRAINT fk_email_letter_api_user FOREIGN KEY (recipient_id)
		REFERENCES api_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE NO ACTION
)


INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, image_url, social_type, locale, role, birthday, about_me)
VALUES (1, '102226743103588881193', 'kotygaandrey05@gmail.com', '+380974112881', 'Андрій', 'Котюга',
        'https://lh3.googleusercontent.com/a-/AOh14GgvN0FFnpaLTo2LAsOIpe2csQ0ktPyP7s-G2TPx9A=s96-c',
        'google', 'UK', 'ROLE_USER', '2001-05-02',
        'Привіт, радий бачити в моєму профілі:) Я Андрій, родом з маленького села на Півночі Волині.' ||
        'Але живу я в дуже гарнім місті - Чернівцях.' ||
        'Можу провести самий прикольний тур по місту Чернівці, але й також зробити незабутні подорожі по Поліссі.' ||
        'Обожнюю гори, раду піду в гори за будь-якої нагоди');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (2, '2353453535345435', 'stefan@gmail.com', '+380996112881', 'Степанченко', 'Степан',
        'google', 'UK', 'ROLE_USER', '2000-01-11',
        'Привіт, я супер недосвідчений турист і хочу поїхати будь куди');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (3, '32656547567634', 'kate@gmail.com', '+380932112881', 'Катеринко', 'Катя',
        'facebook', 'UK', 'ROLE_USER', '1999-09-17',
        'Привіт, Я Катя. Я встигла побувати в багатьох ванільних місцях нашої країни. ' ||
        'Але мені цікаво побачити те про що не побачити в інтернеті і де не побували мільйон туристів.');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (4, '335566547567634', 'ivanna@gmail.com', '+380932112881', 'Іванова', 'Іванка',
        'facebook', 'UK', 'ROLE_USER', '2002-05-07',
        'Ку! Я з вижниці! Моє місто хоч і маленьке, але на його околицях є що подивитися.' ||
        'А якщо хто має автомобіль зоб поїхати то можу і показати в області самі файні місця.' ||
        'А чи знали що в Чернівецькі області є водоспади?!)');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (5, '335647567634', 'pupkin@gmail.com', '+380662112882', 'Попов', 'Василь',
        'facebook', 'UK', 'ROLE_USER', '1998-01-01',
        'ЙО! Я програміст Вася. 24*7 працюю за компудахтером. Бажаю змінити смак кави воду з джерела тому спішу в подорож.');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (6, '111566547567634', 'pupkin@gmail.com', '+380662112882', 'Попов', 'Василь',
        'facebook', 'UK', 'ROLE_USER', '1998-01-01',
        'ЙО! Я програміст Вася. 24*7 працюю за компудахтером. Бажаю змінити смак кави воду з джерела тому спішу в подорож.');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (7, '345346755567634', 'pupkin@gmail.com', '+390112112369', 'Tomac', 'Tom',
        'facebook', 'UK', 'ROLE_USER', '1992-02-02',
        'Hi, I love making new friends from different countries. ' ||
        'And I also love Ukraine very much. I will gladly show Ukraine through the eyes of foreigners.');

INSERT INTO api_user(id, social_id, email, phone_number, first_name, last_name, social_type, locale, role, birthday, about_me)
VALUES (8, '2654744556547', 'pupkin@gmail.com', '+390112112369', 'Гуцул', 'Іван',
        'google', 'UK', 'ROLE_USER', '1975-03-02',
        'Я Гуцул Іван, та знаюсь на екстримі!');

INSERT INTO geo_data(id, latitude, longitude, altitude, address_name)
VALUES (1, 51.871705, 25.640747, 0, 'с. Сваловичі, Волинська область');

INSERT INTO tour(id, name, description, rate, owner_id, geo_data_id)
VALUES (1, 'Сваловичі',
        'Сваловичі називають Селом Вдів. Повязано це з тим що там живуть лиш жінки,' ||
        'Не жінки, а бабусі, якщо бути точним) Так ось. Село закинуте, тому там живе лиш 18 відв(' ||
        'Але це село насичене автентичністю та дикою природою.' ||
        'Можна побачити справжні деревяні будинки поліщуків, побачити як 80-річні бабусі плавають самостійно човнем по Припяті.' ||
        'Кілька років там знімали Чеський фільм і деякі локації ще збереглись))' ||
        'А щоб роздивитись село та річку з висока лісники збудуваали 11-метрову вишку для обзору, адже територія належить національному парку.',
        99, 1, 1);

INSERT INTO geo_data(id, latitude, longitude, altitude, address_name)
VALUES (2, 51.773297, 25.576218, 0, 'с. Подкормілля, Волинська область');

INSERT INTO tour(id, name, description, rate, owner_id, geo_data_id)
VALUES (2, 'Каяк клуб на Стоході',
        'Ми створили клуб любителів плавати на байдарках.' ||
        'Нащі запливи це щось неймовірне. Цце атмосфера життя в палатках, нічного багаття,' ||
        'шуму хвиль, та безкінечних русел річки що створюють лабіринт',
        99, 1, 2);

INSERT INTO geo_data(id, latitude, longitude, altitude, address_name)
VALUES (3, 48.838890, 23.452871, 0, 'смт. Славське, Льівська область');

INSERT INTO tour(id, name, description, rate, owner_id, geo_data_id)
VALUES (3, 'Квадро тур Карпатами',
        'Ми вирушимо о 8 ранку зі Славського по гірських хребтах багатьох гір до водоспаду Шипіт.' ||
        'Дорогою нас чекає вершина гори Захар Беркут, перетин річки та проїдемо перетин Львівської,' ||
        'Івано-Франківської та Закарпатської областей. Буде круто! Я вам обіцяю!',
        90, 8, 3);

INSERT INTO geo_data(id, latitude, longitude, altitude, address_name)
VALUES (4, 50.239081, 19.063106, 0, 'Katowice, Poland');

INSERT INTO tour(id, name, description, rate, owner_id, geo_data_id)
VALUES (4, 'Katowice: Private Polish Beer Tasting Tour',
        'We prepared with high precision and special for you a unique program of Beer Tasting tour. ' ||
        'During this tour you will try selected kind of beers, try delicious local appetizers and learn about ' ||
        'Polish history, customs and traditions. You will be led by licensed guide who has many-years of experience ' ||
        'and they are the best in all country! All of it will make this tour very unforgettable.',
        75, 7, 4);

INSERT INTO geo_data(id, latitude, longitude, altitude, address_name)
VALUES (5, 48.838890, 23.452871, 0, 'смт. Верховина, Івано-Франківська область');

INSERT INTO tour(id, name, description, rate, owner_id, geo_data_id)
VALUES (5, 'Верховина',
        'Спокійна прогулянка Верховиною',
        95, 8, 5);


INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (1, '1_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (2, '2_sval.png', 'image/png', '1_sval.png');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (3, '3_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (4, '4_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (5, '5_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (6, '6_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (7, '7_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (8, '8_sval.jpg', 'image/jpeg', '1_sval.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (9, '9_sval.jpg', 'image/jpeg', '1_sval.jpg');

INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (10, 'kayak_1.jpg', 'image/jpeg', 'kayak_1.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (11, 'kayak_2.jpg', 'image/jpeg', 'kayak_2.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (12, 'kayak_3.jpg', 'image/jpeg', 'kayak_3.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (13, 'kayak_4.jpg', 'image/jpeg', 'kayak_4.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (14, 'kayak_5.jpg', 'image/jpeg', 'kayak_5.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (15, 'kayak_6.jpg', 'image/jpeg', 'kayak_6.jpg');

INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (16, 'kvadro_1.jpg', 'image/jpeg', 'kvadro_1.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (17, 'kvadro_2.jpg', 'image/jpeg', 'kvadro_2.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (18, 'kvadro_3.jpg', 'image/jpeg', 'kvadro_3.jpg');

INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (19, 'verh_1.jpg', 'image/jpeg', 'verh_1.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (20, 'verh_2.jpg', 'image/jpeg', 'verh_2.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (21, 'verh_3.jpg', 'image/jpeg', 'verh_3.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (22, 'verh_4.jpg', 'image/jpeg', 'verh_4.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (23, 'verh_5.jpg', 'image/jpeg', 'verh_5.jpg');
INSERT INTO image(id, image_name, mime_type, original_name)
VALUES (24, 'verh_6.jpg', 'image/jpeg', 'verh_6.jpg');

INSERT INTO tour_image(tour_id, image_id) VALUES (1, 1);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 2);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 3);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 4);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 5);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 6);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 7);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 8);
INSERT INTO tour_image(tour_id, image_id) VALUES (1, 9);

INSERT INTO tour_image(tour_id, image_id) VALUES (2, 10);
INSERT INTO tour_image(tour_id, image_id) VALUES (2, 11);
INSERT INTO tour_image(tour_id, image_id) VALUES (2, 12);
INSERT INTO tour_image(tour_id, image_id) VALUES (2, 13);
INSERT INTO tour_image(tour_id, image_id) VALUES (2, 14);
INSERT INTO tour_image(tour_id, image_id) VALUES (2, 15);

INSERT INTO tour_image(tour_id, image_id) VALUES (3, 16);
INSERT INTO tour_image(tour_id, image_id) VALUES (3, 17);
INSERT INTO tour_image(tour_id, image_id) VALUES (3, 18);

INSERT INTO tour_image(tour_id, image_id) VALUES (5, 19);
INSERT INTO tour_image(tour_id, image_id) VALUES (5, 20);
INSERT INTO tour_image(tour_id, image_id) VALUES (5, 21);
INSERT INTO tour_image(tour_id, image_id) VALUES (5, 22);
INSERT INTO tour_image(tour_id, image_id) VALUES (5, 23);
INSERT INTO tour_image(tour_id, image_id) VALUES (5, 24);


INSERT INTO booking(id, description, status, rate, tourist_id)
VALUES (1, '', 'DONE', 90, 5);

INSERT INTO booking(id, description, status, rate, tourist_id)
VALUES (2, 'Thanks!', 'DONE', 100, 7);