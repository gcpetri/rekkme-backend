/*
INSERT INTO CATEGORIES (NAME) VALUES ('Music');
INSERT INTO CATEGORIES (NAME) VALUES ('Movies');
INSERT INTO CATEGORIES (NAME) VALUES ('Products');
INSERT INTO CATEGORIES (NAME) VALUES ('Restaurants');
INSERT INTO CATEGORIES (NAME) VALUES ('Travel');

INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('gcpetri', 'Gregory', 'Petri', 'gcpetri@tamu.edu', LOCALTIMESTAMP, 500, 2, 'https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (1, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('tatiaris', 'Rish', 'Tatia', 'tatiaris@tamu.edu', LOCALTIMESTAMP, 940, 12, 'https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (2, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('dantheman', 'Dan', 'Abreo', 'dan.abreo@tamu.edu', LOCALTIMESTAMP, 200, 10, 'https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-2.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (3, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('gcurious', 'Curious', 'George', 'gcurious@tamu.edu', LOCALTIMESTAMP, 900, 0, 'https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-2.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (4, 'password');
INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES ('theaggie', 'Maddie', 'The Aggie', 'theaggie@tamu.edu', LOCALTIMESTAMP, 100000, 20, 'https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png');
INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (5, 'password');

INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (1, 2);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (1, 3);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (2, 1);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (2, 4);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 4);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 1);
INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (3, 2);

INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (2, 1, 'Check this song out!', 1, 200, 'https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086', LOCALTIMESTAMP, 'Lincoln Park', 'Song Recommendation', 'https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/14/f1/83/14f18320-9f03-880c-1712-f7a408fbff35/source/600x600bb.jpg');
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, TITLE, IMAGE_URL) VALUES (1, 3, 'This show bro...', 2, 100, 'https://www.warnerbros.com/movies/lord-rings-fellowship-ring', LOCALTIMESTAMP, 'LOTR', 'https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_.jpg'); 
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, LOCATION, TITLE, IMAGE_URL) VALUES (1, 4, 'I LOVE THIS RESTAURANT', 4, 800, 'https://torchystacos.com/', LOCALTIMESTAMP, '-38.24031, 89.20920', 'TORCHYS', 'https://res.cloudinary.com/culturemap-com/image/upload/ar_4:3,c_fill,g_faces:center,w_980/v1581463475/photos/307170_original.jpg'); 
INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (2, 1, 'Check this song out too!', 1, 300, 'https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086', LOCALTIMESTAMP, 'Palace', 'My favorite song', 'https://i.scdn.co/image/ab67616d0000b273929dae46c6b93942c7499b7d');

INSERT INTO USER_REK_QUEUES (USER_ID, REK_ID, QUEUE_ORDER) VALUES (2, 1, 0);
INSERT INTO USER_REK_QUEUES (USER_ID, REK_ID, QUEUE_ORDER) VALUES (2, 4, 1);
*/

SELECT COUNT(*) FROM EVENTS;