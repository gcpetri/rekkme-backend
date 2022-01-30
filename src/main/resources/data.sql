INSERT INTO CATEGORIES (NAME) VALUES ('Music');
INSERT INTO CATEGORIES (NAME) VALUES ('Movies and TV');
INSERT INTO CATEGORIES (NAME) VALUES ('Products');
INSERT INTO CATEGORIES (NAME) VALUES ('Restaurants');
INSERT INTO CATEGORIES (NAME) VALUES ('Travel');

INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('gcpetri', 'Gregory', 'Petri', 'gcpetri@tamu.edu', LOCALTIMESTAMP, 500, 2, 'https://avatars.githubusercontent.com/u/61479556?v=4');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (1, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('tatiaris', 'Rish', 'Tatia', 'tatiaris@tamu.edu', LOCALTIMESTAMP, 1000, 12, 'https://avatars.githubusercontent.com/u/33273930?v=4');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (2, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('dantheman', 'Dan', 'Abreo', 'dan.abreo@tamu.edu', LOCALTIMESTAMP, 200, 10, 'https://avatars.githubusercontent.com/u/34114190?v=4');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (3, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('gcurious', 'Curious', 'George', 'gcurious@tamu.edu', LOCALTIMESTAMP, 900, 0, 'https://images-na.ssl-images-amazon.com/images/I/81inD4wl+QL.jpg');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (4, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('theaggie', 'Maddie', 'The Aggie', 'theaggie@tamu.edu', LOCALTIMESTAMP, 100000, 20, 'https://www.tamu.edu/assets/images/12thman-stat.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (5, 'password');

INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (1, 2);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (1, 3);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (2, 1);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (2, 4);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 4);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 1);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 2);

INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (2, 1, 'Check this song out!', 1, 200, 'https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086', LOCALTIMESTAMP, 'Lincoln Park', 'Song Recommendation', 'https://picsum.photos/200');
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, TITLE, IMAGE_URL) VALUES (1, 3, 'This show bro...', 2, 100, 'https://www.warnerbros.com/movies/lord-rings-fellowship-ring', LOCALTIMESTAMP, 'LOTR', 'https://picsum.photos/200'); 
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, LOCATION, TITLE, IMAGE_URL) VALUES (1, 4, 'I LOVE THIS RESTAURANT', 4, 800, 'https://torchystacos.com/', LOCALTIMESTAMP, '-38.24031, 89.20920', 'TORCHYS', 'https://picsum.photos/200'); 
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (2, 1, 'Check this song out too!', 1, 300, 'https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086', LOCALTIMESTAMP, 'Palace', 'My favorite song', 'https://picsum.photos/200');

INSERT INTO USER_REK_QUEUES (USER_ID, REK_ID, QUEUE_ORDER) VALUES (2, 1, 0);
INSERT INTO USER_REK_QUEUES (USER_ID, REK_ID, QUEUE_ORDER) VALUES (2, 4, 1);