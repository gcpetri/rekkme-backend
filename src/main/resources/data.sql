/*
DO
'
DECLARE

    test_username USERS.USERNAME%type;

    gcurious_user_id USERS.USER_ID%type;
    gcpetri_user_id USERS.USER_ID%type;
    maggie_user_id USERS.USER_ID%type;

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

        INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES (''gcurious'', ''Curious'', ''George'', ''gcurious@tamu.edu'', LOCALTIMESTAMP, 500, 2, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png'') RETURNING USER_ID INTO gcurious_user_id;
        INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (gcurious_user_id, ''password'');
        INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES (''gcpetri'', ''Gregory'', ''Petri'', ''gcpetri@tamu.edu'', LOCALTIMESTAMP, 100, 0, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/guy-1.png'') RETURNING USER_ID INTO gcpetri_user_id;
        INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (gcpetri_user_id, ''password'');
        INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES (''maggie'', ''Maggie'', ''Aggie'', ''theaggie@tamu.edu'', LOCALTIMESTAMP, 150, 18, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png'') RETURNING USER_ID INTO maggie_user_id;
        INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (maggie_user_id, ''password'');

        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (gcurious_user_id, gcpetri_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (gcpetri_user_id, gcurious_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (maggie_user_id, gcpetri_user_id);
        INSERT INTO USER_FRIENDS (USER_ID, FRIEND_ID) VALUES (gcpetri_user_id, maggie_user_id);

        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Music'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/MusicBadge.png'') RETURNING CATEGORY_ID INTO music_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''TV'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/TVBadge.png'') RETURNING CATEGORY_ID INTO tv_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Products'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/ProductBadge.png'') RETURNING CATEGORY_ID INTO products_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Restaurants'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/RestaurantBadge.png'') RETURNING CATEGORY_ID INTO restaurant_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Travel'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/TravelBadge.png'') RETURNING CATEGORY_ID INTO travel_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Books'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/BookBadge.png'') RETURNING CATEGORY_ID INTO book_category_id;
        INSERT INTO CATEGORIES (NAME, IMAGE_URL) VALUES (''Recipe'', ''https://github.com/tatiaris/rekkme/raw/master/public/badges/RecipeBadge.png'') RETURNING CATEGORY_ID INTO recipe_category_id;

        INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, TITLE, IMAGE_URL) VALUES (gcurious_user_id, gcpetri_user_id, ''This show bro...'', tv_category_id, 80, ''https://www.warnerbros.com/movies/lord-rings-fellowship-ring'', LOCALTIMESTAMP, ''LOTR'', ''https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_.jpg'') RETURNING REK_ID INTO lotr_rek_id;
        INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, LOCATION, TITLE, IMAGE_URL) VALUES (gcurious_user_id, gcpetri_user_id, ''I LOVE THIS RESTAURANT'', restaurant_category_id, 80, ''https://torchystacos.com/'', LOCALTIMESTAMP, ''-38.24031, 89.20920'', ''TORCHYS'', ''https://res.cloudinary.com/culturemap-com/image/upload/ar_4:3,c_fill,g_faces:center,w_980/v1581463475/photos/307170_original.jpg'') RETURNING REK_ID INTO torchys_rek_id;
        INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (gcpetri_user_id, gcurious_user_id, ''Check this song out too!'', music_category_id, 30, ''https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086'', LOCALTIMESTAMP, ''Palace'', ''My favorite song'', ''https://i.scdn.co/image/ab67616d0000b273929dae46c6b93942c7499b7d'') RETURNING REK_ID INTO palace_rek_id;
        INSERT INTO REKS (TO_USER_ID, FROM_USER_ID, DESCRIPTION, CATEGORY_ID, WAGER, URL, CREATED_ON, ARTIST, TITLE, IMAGE_URL) VALUES (gcpetri_user_id, maggie_user_id, ''Check this song out!'', music_category_id, 100, ''https://open.spotify.com/track/2DkZisoN9h1dLa8Sn5sx0n?si=203a3183a3554086'', LOCALTIMESTAMP, ''Lincoln Park'', ''Song Recommendation'', ''https://is4-ssl.mzstatic.com/image/thumb/Music124/v4/14/f1/83/14f18320-9f03-880c-1712-f7a408fbff35/source/600x600bb.jpg'') RETURNING REK_ID INTO lincoln_park_rek_id;

        INSERT INTO LIKES (USER_ID, REK_ID) VALUES (maggie_user_id, lotr_rek_id);
        INSERT INTO LIKES (USER_ID, REK_ID) VALUES (gcurious_user_id, lotr_rek_id);

        INSERT INTO COMMENTS (REK_ID, USER_ID, MESSAGE, CREATED_ON) VALUES (lotr_rek_id, gcpetri_user_id, ''You crazy if you do not like this movie'', LOCALTIMESTAMP);
        INSERT INTO COMMENTS (REK_ID, USER_ID, MESSAGE, CREATED_ON) VALUES (lotr_rek_id, gcurious_user_id, ''I will try...'', LOCALTIMESTAMP);

        INSERT INTO REK_RESULTS (REK_ID, RESULT, KO, CREATED_ON) VALUES (lotr_rek_id, 3, false, LOCALTIMESTAMP);

    end if;

END;
' LANGUAGE PLPGSQL;
*/
SELECT COUNT(*) FROM USERS;