--
-- Creating 'horizon_user' table ('user' table)
--
CREATE TABLE horizon_user
(
	id bigserial NOT NULL,
	social_id character varying(255),
	email character varying(255) NOT NULL,
	phone_number character varying(13)  NOT NULL,
	first_name character varying(255)  NOT NULL,
	last_name character varying(255)  NOT NULL,
	password character varying(255) NOT NULL,
	CONSTRAINT pk_horizon_user PRIMARY KEY (id),
	CONSTRAINT uk_horizon_user_unique_social_id UNIQUE (social_id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE horizon_user OWNER to horizon_owner;

--
-- Creating images table
--

CREATE TABLE image
(
	id bigserial NOT NULL,
	original_name character varying(255)  NOT NULL,
	type character varying(255)  NOT NULL,
	CONSTRAINT pk_image PRIMARY KEY (id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE image OWNER to horizon_owner;

--
-- Connecting 'image' table to 'horizon-user'
--
ALTER TABLE horizon_user
	ADD column image_id bigint NOT NULL,
	ADD CONSTRAINT fk_image_horizon_user FOREIGN KEY (image_id)
        REFERENCES image (id) MATCH SIMPLE
            ON UPDATE NO ACTION
			ON DELETE SET NULL;

--
-- Creating 'locale' table
--
CREATE TABLE locale
(
	id bigserial NOT NULL,
	locale character varying(2)  NOT NULL DEFAULT 'UK',
	CONSTRAINT pk_locale PRIMARY KEY (id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE locale OWNER to horizon_owner;

--
-- Connecting 'locale' table to 'horizon-user'
--
ALTER TABLE horizon_user
	ADD column locale_id bigint NOT NULL,
	ADD CONSTRAINT fk_locale_horizon_user FOREIGN KEY (locale_id)
        REFERENCES locale (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE SET NULL;

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


--
-- Creating table 'tour'
--
CREATE TABLE tour
(
	id bigserial NOT NULL,
	name character varying(255) NOT NULL,
	description character varying(10000) NOT NULL,
	rate int CHECK(rate BETWEEN 0 AND 100) DEFAULT 50,
	guide_id bigint NOT NULL,
	CONSTRAINT pk_tour PRIMARY KEY(id),
	CONSTRAINT fk_tour_horizon_user_guide FOREIGN KEY (guide_id)
		REFERENCES horizon_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE SET NULL
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE tour OWNER to horizon_owner;

--
-- Creating table 'response'
--
CREATE TABLE response
(
	id bigserial NOT NULL,
	comment character varying(5000),
	rate int CHECK(rate BETWEEN 0 AND 100) DEFAULT 100,
	create_time timestamp with time zone NOT NULL,
	CONSTRAINT pk_response PRIMARY KEY(id)
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE response OWNER to horizon_owner;

--
-- Creating intermediate between 'tour' and 'response'
--
CREATE TABLE tour_response
(
	tour_id bigint NOT NULL,
	response_id bigint NOT NULL,
	CONSTRAINT pk_tour_response PRIMARY KEY (tour_id, response_id),
	CONSTRAINT fk_tour_response_to_response FOREIGN KEY (response_id)
		REFERENCES response (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	Constraint fk_tour_response_to_tour FOREIGN KEY (response_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE tour_response OWNER to horizon_owner;


--
-- Creating table 'booking'
--
CREATE TABLE booking
(
	id bigserial NOT NULL,
	tour_id bigint NOT NULL,
	tourist_id bigint NOT NULL,
	guide_id bigint NOT NULL,
	CONSTRAINT pk_booking PRIMARY KEY(id),
	CONSTRAINT fk_tour_booking FOREIGN KEY (tour_id)
		REFERENCES tour (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	CONSTRAINT fk_tourist_booking  FOREIGN KEY (tourist_id)
		REFERENCES horizon_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE,
	CONSTRAINT fk_guide_booking FOREIGN KEY (guide_id)
		REFERENCES horizon_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE CASCADE
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE booking OWNER to horizon_owner;

--
-- Creating 'profile' table
--

CREATE TABLE profile
(
	id bigserial NOT NULL,
	address character varying(255) NOT NULL,
	about_me character varying(5000) NOT NULL,
	birthday timestamp with time zone,
	horizon_user_id bigint NOT NULL,
	CONSTRAINT pk_profile PRIMARY KEY (id),
	CONSTRAINT fk_horizon_user_profile FOREIGN KEY (horizon_user_id)
		REFERENCES horizon_user (id) MATCH SIMPLE
		ON UPDATE NO ACTION
		ON DELETE SET NULL
)
WITH (OIDS = FALSE)
TABLESPACE pg_default;
ALTER TABLE profile OWNER to horizon_owner;


INSERT INTO public.locale(locale) VALUES ('UK');
INSERT INTO public.locale(locale) VALUES ('EN');

INSERT INTO public.image(original_name, type) VALUES ('wd3rf23fava1', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('qwf24p4jtj2', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('23f2aefqwef', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('QWFAQEWFWEFG', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('wd3rf23fava1', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('WEFWEFE', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('WEFW4GVWEFVF', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('WERWFWEEGV', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('WETTWEGEWGWEGWEEG', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('wegwegewge', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('sfsdfdheryewg', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('errerrerhregr', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('wer3r3hrwerhr', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('334yerewwewtewt', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('qwefqweg4', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('34645y', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('3r2t43y334', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('wregethjty', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('346tryutyu45y', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('56ujtresa', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('sdfheye', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('regerreg', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('erhejryrhss', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('dfdfgf', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('eyu5i56j', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('wrehjt5eg', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('dfdfgf', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('DFBDFBSDF', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('rtewgfqda', 'image/png');
INSERT INTO public.image(original_name, type) VALUES ('8678868686', 'image/jpg');
INSERT INTO public.image(original_name, type) VALUES ('K56U5GFRH', 'image/jpg');

INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('33453l', 'kotygaandrey05@gmail.com', '+380974112881', 'Andrew', 'Kot', 'password', 1, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('we35344', 'vasya@gmail.com', '+380904912881', 'Vasya', 'Pupkin', 'password', 2, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('453755', 'petro@gmail.com', '+380500102981', 'Petro', 'Petrov', 'password', 3, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('45645', 'ivan@gmail.com', '+380674112881', 'Ivan', 'Ivanov', 'password', 4, 1);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('danya@gmail.com', '+380974142881', 'Danya', 'Danylov', 'password', 5, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('486486', 'vova@gmail.com', '+380994102880', 'Vova', 'Vovkin', 'password', 6, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('4574557', 'ira@gmail.com', '+380994112981', 'Ira', 'kulpaka', 'password', 7, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('58678678', 'kate@gmail.com', '+380964112889', 'Kata', 'Ostapchuck', 'password', 8, 2);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('lilly@gmail.com', '+38097411289', 'Lilly', 'Miloneva', 'password', 9, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('45456546', 'tomash@gmail.com', '+380676112681', 'Tomash', 'Kostushko', 'password', 10, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('34674865', 'bodya@gmail.com', '+380674116881', 'Bodya', 'Nash', 'password', 11, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('3534454h4', 'viktor@gmail.com', '+380674112681', 'Viktor', 'Budko', 'password', 12, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('34t343t43t', 'alina@gmail.com', '+380674162681', 'Alina', 'Kotush', 'password', 13, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('dff34334', 'dasha@gmail.com', '+3806762881', 'Daria', 'Kozachuck', 'password', 14, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('343g334f3', 'nastya@gmail.com', '+380674116886', 'Anastasia', 'Popova', 'password', 15, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('678743', 'lera@gmail.com', '+380504112881', 'Valeria', 'Tulicka', 'password', 16, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('136653543', 'diana@gmail.com', '+380504112081', 'Diana', 'Martinchuk', 'password', 17, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('398899', 'igor@gmail.com', '+38050411201', 'Igor', 'Krisko', 'password', 18, 1);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('nazar@gmail.com', '+380504012631', 'Nazar', 'Kyrin', 'password', 19, 1);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('valya@gmail.com', '+380504012881', 'Valya', 'Koval', 'password', 20, 1);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('56565433', 'jeck@gmail.com', '+10974112881', 'Jeck', 'Sparl', 'password', 21, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('2354643', 'mickle@gmail.com', '+10974112881', 'Michael', 'Jackson', 'password', 22, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('243567', 'mett@gmail.com', '+10974112881', 'Mett', 'Parkinson', 'password', 23, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('32266945', 'ron@gmail.com', '+10974112881', 'Ron', 'Weasley', 'password', 24, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('9978888', 'djon@gmail.com', '+10974112881', 'Djoni', 'Dep', 'password', 25, 2);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('heron@gmail.com', '+10974112881', 'Heron', 'Heronovich', 'password', 26, 1);
INSERT INTO public.horizon_user(email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('frodo@gmail.com', '+10974112881', 'Frodo', 'Torbin', 'password', 27, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('23twegrh', 'legolas@gmail.com', '+10974112881', 'Legolas', 'Trainul', 'password', 28, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('345645', 'bilbo@gmail.com', '+10974112881', 'Bilbo', 'Baggins', 'password', 29, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('h6766445', 'mikkie@gmail.com', '+10974112881', 'Dolly', 'Buster', 'password', 30, 2);
INSERT INTO public.horizon_user(social_id, email, phone_number, first_name, last_name, password, image_id, locale_id)
	VALUES ('5ag5rg8', 'aragorn@gmail.com', '+10974112881', 'Aragorn', 'Elessar', 'password', 31, 2);

INSERT INTO public.user_role(user_id, role) VALUES (1, 'ADMIN');
INSERT INTO public.user_role(user_id, role) VALUES (1, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (1, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (2, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (3, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (4, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (4, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (5, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (6, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (7, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (8, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (9, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (10, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (11, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (12, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (13, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (13, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (14, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (15, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (16, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (17, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (18, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (19, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (19, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (20, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (21, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (22, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (23, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (24, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (25, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (26, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (27, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (28, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (28, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (29, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (29, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (30, 'TOURIST');
INSERT INTO public.user_role(user_id, role) VALUES (30, 'GUIDE');
INSERT INTO public.user_role(user_id, role) VALUES (31, 'GUIDE');

INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Chernivtsi, Pylypa Orlika',
        'I can do tours of Chernivtsi.' ||
        'But the best thing is my walks in the remote abandoned villages of Volyn.' ||
        'And also I search new interesting tours and persons :)',
        '2001-05-28', 1);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Львів, Степанат Бандери',
        'Привіт, Я Катя. Я встигла побувати в багатьох ванільних місцях нашої країни. ' ||
        'Але мені цікаво побачити те про що не побачити в інтернеті і де не побували мільйон туристів.',
        '199-07-07', 9);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Warsaw',
        'Hi, I love making new friends from different countries. ' ||
        'And I also love Ukraine very much. I will gladly show Ukraine through the eyes of foreigners.',
        '2001-10-11', 10);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Kyiv',
        'I am Alina and I am a climber. I have favorite locations for those who have never done' ||
        ' mountaineering but want to feel it and see the best landscapes.',
        '2000-09-10', 13);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Ostivsk village, Rivnenkiy region',
        'Hi there. I live in a remote village. I love architecture. ' ||
        'I have a new hobby to photograph open porches in old houses. ' ||
        'That is  why I like to travel around cities with old architecture and the cutest cafes.',
        '2003-01-02', 19);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Вижниця',
        'Моє місто хоч і маленьке, але на його околицях є що подивитися.' ||
        'А якщо хто має автомобіль зоб поїхати то можу і показати в області самі файні місця.' ||
        'А чи знали що в Чернівецькі області є водоспади?!)',
        '1979-01-01', 20);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('I live in see:(',
        'Hi there. I am the ruler of the Caribbean. I love adventures.
		I only know English and Spanish.
		Always ready to meet new adventures and discoveries.',
        '2002-05-22', 21);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Odessa',
        'The sea, the beach and the sun are my element!' ||
        'I know where to find a wild beach where there are few people..',
        '1998-07-14', 24);
INSERT INTO public.profile(address, about_me, birthday, horizon_user_id)
VALUES ('Bag End, The Shire',
        'I will take you to the Mediterranean.' ||
        'I can show a troll cave, elven forests and wild fields with rocks filled with orcs',
        '1960-01-01', 29);

INSERT INTO public.tour(name, description, guide_id)
VALUES ('Сваловичі',
        'Сваловичі називають Селом Вдів. Повязано це з тим що там живуть лиш жінки,' ||
		'Не жінки, а бабусі, якщо бути точним) Так ось. Село закинуте, тому там живе лиш 18 відв(' ||
        'Але це село насичене автентичністю та дикою природою.' ||
        'Можна побачити справжні деревяні будинки поліщуків, побачити як 80-річні бабусі плавають самостійно човнем по Припяті.' ||
        'Кілька років там знімали Чеський фільм і деякі локації ще збереглись))' ||
        'А щоб роздивитись село та річку з висока лісники збудуваали 11-метрову вишку для обзору, адже територія належить національному парку.', 1);

INSERT INTO public.tour(name, description, guide_id)
VALUES ('Каяк клуб на Стоході',
        'Сваловичі називають Селом Вдів. Повязано це з тим що там живуть лиш жінки,' ||
        'Не жінки, а бабусі, якщо бути точним) Так ось. Село закинуте, тому там живе лиш 18 відв(' ||
        'Але це село насичене автентичністю та дикою природою.' ||
        'Можна побачити справжні деревяні будинки поліщуків, побачити як 80-річні бабусі плавають самостійно човнем по Припяті.' ||
        'Кілька років там знімали Чеський фільм і деякі локації ще збереглись))' ||
        'А щоб роздивитись село та річку з висока лісники збудуваали 11-метрову вишку для обзору, адже територія належить національному парку.', 1);

INSERT INTO public.tour(name, description, guide_id)
VALUES ('Katowice: Private Polish Beer Tasting Tour',
        'We prepared with high precision and special for you a unique program of Beer Tasting tour. ' ||
        'During this tour you will try selected kind of beers, try delicious local appetizers and learn about ' ||
        'Polish history, customs and traditions. You will be led by licensed guide who has many-years of experience ' ||
        'and they are the best in all country! All of it will make this tour very unforgettable.', 10);

INSERT INTO public.tour(name, description, guide_id)
VALUES ('Climbing in Carpathian Mountains',
        'The Carpathian Mountains constitute one of Europes main mountain systems, whose characteristic arc spans some' ||
        ' 1,500 km across, separating the Carpathian Basin from the vast lowlands of Eastern Europe. ' ||
        'At both ends, it is cut off from the neighboring mountains – the Alps in the west, and the Stara ' ||
        'Planina/Balkan Mountains in the south – by the Danube River, although in geologic terms the Carpathians ' ||
        'extend a bit beyond the Danube (for further discussion please see the SW chapter).', 13);

