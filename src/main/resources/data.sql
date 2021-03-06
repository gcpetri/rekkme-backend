DO
'
DECLARE

    test_username USERS.USERNAME%type;

    gcurious_user_id USERS.USER_ID%type;
    gcpetri_user_id USERS.USER_ID%type;
    maggie_user_id USERS.USER_ID%type;
    bobby_user_id USERS.USER_ID%type;
    larry_user_id USERS.USER_ID%type;

    music_category_id CATEGORIES.CATEGORY_ID%type;
    tv_category_id CATEGORIES.CATEGORY_ID%type;
    products_category_id CATEGORIES.CATEGORY_ID%type;
    recipe_category_id CATEGORIES.CATEGORY_ID%type;
    travel_category_id CATEGORIES.CATEGORY_ID%type;
    book_category_id CATEGORIES.CATEGORY_ID%type;
    restaurant_category_id CATEGORIES.CATEGORY_ID%type;

    lotr_rek_id REKS.REK_ID%type;
    torchys_rek_id REKS.REK_ID%type;
    palace_rek_id REKS.REK_ID%type;
    lincoln_park_rek_id REKS.REK_ID%type;

BEGIN

    SELECT USERNAME FROM USERS INTO test_username WHERE USERNAME = ''gcurious'';

    if not found then

        INSERT INTO USERS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL, IS_PUBLIC) VALUES (''7882c194-8989-4629-9af9-7cd0bc92d7cf'', ''gcurious'', ''Curious'', ''George'', ''gcurious@tamu.edu'', LOCALTIMESTAMP, 500, 2, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png'', true) RETURNING USER_ID INTO gcurious_user_id;
        INSERT INTO AUTH (AUTH_ID, USER_ID, PASSWORD) VALUES (uuid_generate_v4(), gcurious_user_id, ''password'');
        INSERT INTO USERS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL, IS_PUBLIC) VALUES (''921060ec-e733-4271-a2a7-24d967bf936d'', ''gcpetri'', ''Gregory'', ''Petri'', ''gcpetri@tamu.edu'', LOCALTIMESTAMP, 100, 0, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png'', false) RETURNING USER_ID INTO gcpetri_user_id;
        INSERT INTO AUTH (AUTH_ID, USER_ID, PASSWORD) VALUES (uuid_generate_v4(), gcpetri_user_id, ''password'');
        INSERT INTO USERS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL, IS_PUBLIC) VALUES (''4f9ed0ce-81e8-437b-bc99-aefa9eebbf21'', ''maggie'', ''Maggie'', ''Aggie'', ''theaggie@tamu.edu'', LOCALTIMESTAMP, 150, 18, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png'', false) RETURNING USER_ID INTO maggie_user_id;
        INSERT INTO AUTH (AUTH_ID, USER_ID, PASSWORD) VALUES (uuid_generate_v4(), maggie_user_id, ''password'');
        INSERT INTO USERS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL, IS_PUBLIC) VALUES (''b13d85fa-fa0e-4f4a-9dc5-cac41593982d'', ''bobby'', ''Bobby'', ''Bones'', ''thebob@tamu.edu'', LOCALTIMESTAMP, 123, 11, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png'', true) RETURNING USER_ID INTO bobby_user_id;
        INSERT INTO AUTH (AUTH_ID, USER_ID, PASSWORD) VALUES (uuid_generate_v4(), bobby_user_id, ''password'');
        INSERT INTO USERS (USER_ID, USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL, IS_PUBLIC) VALUES (''0d4f139e-f8f4-4a65-bd3e-0f912ca9b5ff'', ''larrydog'', ''Larry'', ''Medina'', ''larrydog29@tamu.edu'', LOCALTIMESTAMP, 13, 1, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png'', true) RETURNING USER_ID INTO larry_user_id;
        INSERT INTO AUTH (AUTH_ID, USER_ID, PASSWORD) VALUES (uuid_generate_v4(), bobby_user_id, ''password'');

        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (gcpetri_user_id, gcurious_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (maggie_user_id, gcpetri_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (larry_user_id, gcpetri_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (gcpetri_user_id, larry_user_id);

        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Music'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/MusicBadge.png'') RETURNING CATEGORY_ID INTO music_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''TV'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/TVBadge.png'') RETURNING CATEGORY_ID INTO tv_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Products'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/ProductBadge.png'') RETURNING CATEGORY_ID INTO products_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Restaurants'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/RestaurantBadge.png'') RETURNING CATEGORY_ID INTO restaurant_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Travel'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/TravelBadge.png'') RETURNING CATEGORY_ID INTO travel_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Books'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/BookBadge.png'') RETURNING CATEGORY_ID INTO book_category_id;
        INSERT INTO CATEGORIES (CATEGORY_ID, NAME, IMAGE_URL) VALUES (uuid_generate_v4(), ''Recipe'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/RecipeBadge.png'') RETURNING CATEGORY_ID INTO recipe_category_id;

        INSERT INTO REKS (REK_ID, TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, TITLE, IMAGE_URL) VALUES (''15abd695-f680-4511-bf6f-166a1b800099'', gcurious_user_id, gcpetri_user_id, ''This show bro...'', tv_category_id, 80, ''https://www.warnerbros.com/movies/lord-rings-fellowship-ring'', LOCALTIMESTAMP, ''LOTR'', ''https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_.jpg'') RETURNING REK_ID INTO lotr_rek_id;
        INSERT INTO REKS (REK_ID, TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, LOCATION, TITLE, IMAGE_URL) VALUES (uuid_generate_v4(), gcurious_user_id, gcpetri_user_id, ''I LOVE THIS RESTAURANT'', restaurant_category_id, 80, ''https://torchystacos.com/'', LOCALTIMESTAMP, ''-38.24031, 89.20920'', ''TORCHYS'', ''https://res.cloudinary.com/culturemap-com/image/upload/ar_4:3,c_fill,g_faces:center,w_980/v1581463475/photos/307170_original.jpg'') RETURNING REK_ID INTO torchys_rek_id;
        INSERT INTO REKS (REK_ID, TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (''0172a695-a602-47a0-a978-86a30061d1ad'', gcpetri_user_id, gcurious_user_id, ''Check this song out too!'', music_category_id, 30, ''https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086'', LOCALTIMESTAMP, ''Palace'', ''My favorite song'', ''https://i.scdn.co/image/ab67616d0000b273929dae46c6b93942c7499b7d'') RETURNING REK_ID INTO palace_rek_id;
        INSERT INTO REKS (REK_ID, TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (uuid_generate_v4(), gcpetri_user_id, maggie_user_id, ''Check this song out!'', music_category_id, 100, ''https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086'', LOCALTIMESTAMP, ''Lincoln Park'', ''Song Recommendation'', ''https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/14/f1/83/14f18320-9f03-880c-1712-f7a408fbff35/source/600x600bb.jpg'') RETURNING REK_ID INTO lincoln_park_rek_id;

        INSERT INTO LIKES (USER_ID, REK_ID) VALUES (maggie_user_id, lotr_rek_id);
        INSERT INTO LIKES (USER_ID, REK_ID) VALUES (gcurious_user_id, lotr_rek_id);

        INSERT INTO COMMENTS (COMMENT_ID, REK_ID, USER_ID, MESSAGE, CREATED_ON) VALUES (uuid_generate_v4(), lotr_rek_id, gcpetri_user_id, ''You crazy if you do not like this movie'', LOCALTIMESTAMP);
        INSERT INTO COMMENTS (COMMENT_ID, REK_ID, USER_ID, MESSAGE, CREATED_ON) VALUES (uuid_generate_v4(), lotr_rek_id, gcurious_user_id, ''I will try...'', LOCALTIMESTAMP);

        INSERT INTO REK_RESULTS (REK_RESULT_ID, REK_ID, RESULT, KO, CREATED_ON) VALUES (uuid_generate_v4(), lotr_rek_id, 3, false, LOCALTIMESTAMP);

        INSERT INTO FRIEND_REQUESTS (FROM_USER_ID, TO_USER_ID) VALUES (gcurious_user_id, gcpetri_user_id);
        INSERT INTO FRIEND_REQUESTS (FROM_USER_ID, TO_USER_ID) VALUES (gcpetri_user_id, maggie_user_id);
        INSERT INTO FRIEND_REQUESTS (FROM_USER_ID, TO_USER_ID) VALUES (bobby_user_id, gcpetri_user_id);

        INSERT INTO USER_REK_QUEUES (USER_REK_QUEUE_ID, QUEUE_ORDER, USER_ID, REK_ID) VALUES (uuid_generate_v4(), 10.0, gcpetri_user_id, lincoln_park_rek_id);
        INSERT INTO USER_REK_QUEUES (USER_REK_QUEUE_ID, QUEUE_ORDER, USER_ID, REK_ID) VALUES (uuid_generate_v4(), 5.0, gcpetri_user_id, palace_rek_id);

    end if;

END;
' LANGUAGE PLPGSQL;