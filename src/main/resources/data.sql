DO
'
DECLARE 
    first_user_id USERS.USER_ID%type;

BEGIN

    INSERT INTO USERS (USERNAME, FIRST_NAME, LAST_NAME, EMAIL, LAST_LOGIN, REK_POINTS, KOS, IMAGE_URL) VALUES (''gcurious'', ''Curious'', ''George'', ''gcurious@tamu.edu'', LOCALTIMESTAMP, 500, 2, ''https://raw.githubusercontent.com/tatiaris/rekkme/e3bd5b661546a556cd5b842a6d53c39deccf921c/public/avatars/girl-1.png'') RETURNING USER_ID INTO first_user_id;
    INSERT INTO AUTH (USER_ID, PASSWORD) VALUES (first_user_id, ''password'');

END;
' LANGUAGE PLPGSQL;