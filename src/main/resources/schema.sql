CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SCHEMA IF NOT EXISTS bulletin;

CREATE TABLE IF NOT EXISTS USERS
(
    USER_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    USERNAME VARCHAR(255) NOT NULL UNIQUE,
    FIRST_NAME VARCHAR(255) NOT NULL,
    LAST_NAME VARCHAR(255) NOT NULL,
    EMAIL VARCHAR(255) NOT NULL UNIQUE,
    LAST_LOGIN TIMESTAMP NOT NULL,
    REK_POINTS INT NOT NULL DEFAULT 0,
    KOS INT NOT NULL DEFAULT 0,
    IMAGE_URL VARCHAR(512),
    IS_PUBLIC BOOLEAN NOT NULL DEFAULT true,
    CONSTRAINT POSITIVE_KOS
      CHECK (KOS >= 0)
);

CREATE TABLE IF NOT EXISTS AUTH
(
  AUTH_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
  USER_ID uuid NOT NULL,
  CONSTRAINT FK_AUTH_USER
    FOREIGN KEY(USER_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE CASCADE,
  PASSWORD VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS USER_FRIENDS
(
  USER_ID uuid NOT NULL,
  CONSTRAINT FK_USER_FRIENDS_USER
    FOREIGN KEY(USER_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE SET NULL,
  FRIEND_ID uuid NOT NULL,
  CONSTRAINT FK_USER_FRIENDS_FRIENDS
    FOREIGN KEY(FRIEND_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS CATEGORIES
(
  CATEGORY_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
  NAME VARCHAR(255) NOT NULL,
  IMAGE_URL VARCHAR(512)
);

CREATE TABLE IF NOT EXISTS TAGS
(
  TAG_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
  NAME VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS REKS
(
    REK_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    TO_USER_ID uuid NOT NULL,
    CONSTRAINT FK_REK_TO_USER
      FOREIGN KEY(TO_USER_ID)
      REFERENCES USERS(USER_ID)
      ON DELETE SET NULL,
    FROM_USER_ID uuid NOT NULL,
    CONSTRAINT FK_REK_FROM_USER
      FOREIGN KEY(FROM_USER_ID)
      REFERENCES USERS(USER_ID)
      ON DELETE SET NULL,
    DESCRIPTION TEXT,
    CATEGORY_ID uuid NOT NULL,
    CONSTRAINT FK_REK_CATEGORY
      FOREIGN KEY(CATEGORY_ID)
      REFERENCES CATEGORIES(CATEGORY_ID)
      ON DELETE SET NULL,
    WAGER INT NOT NULL DEFAULT 0,
    URL VARCHAR(512),
    CREATED_ON TIMESTAMP NOT NULL,
    TITLE VARCHAR(255),
    IMAGE_URL VARCHAR(512),
    ARTIST VARCHAR(255),
    LOCATION VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS COMMENTS
(
    COMMENT_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    REK_ID uuid NOT NULL,
    CONSTRAINT FK_COMMENT_REK
      FOREIGN KEY(REK_ID)
      REFERENCES REKS(REK_ID)
      ON DELETE CASCADE,
    USER_ID uuid NOT NULL,
    CONSTRAINT FK_COMMENT_USER
      FOREIGN KEY(USER_ID)
      REFERENCES USERS(USER_ID)
      ON DELETE CASCADE,
    MESSAGE TEXT NOT NULL,
    CREATED_ON TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS REK_RESULTS
(
    REK_RESULT_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    REK_ID uuid NOT NULL,
    CONSTRAINT FK_REK_RESULTS_REK
      FOREIGN KEY(REK_ID)
      REFERENCES REKS(REK_ID)
      ON DELETE SET NULL,
    RESULT INT NOT NULL DEFAULT 0,
    KO BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_ON TIMESTAMP NOT NULL
);

CREATE TABLE IF NOT EXISTS USER_REK_QUEUES
(
    USER_REK_QUEUE_ID uuid PRIMARY KEY DEFAULT uuid_generate_v4 (),
    USER_ID uuid NOT NULL,
    CONSTRAINT FK_USER_REK_QUEUE_USER
      FOREIGN KEY(USER_ID)
      REFERENCES USERS(USER_ID)
      ON DELETE CASCADE,
    REK_ID uuid NOT NULL,
    CONSTRAINT FK_USER_REK_QUEUE_REK
      FOREIGN KEY(REK_ID)
      REFERENCES REKS(REK_ID)
      ON DELETE CASCADE,
    QUEUE_ORDER FLOAT8 NOT NULL
);

CREATE TABLE IF NOT EXISTS REK_TAG_LINKS
(
  TAG_ID uuid NOT NULL,
  CONSTRAINT FK_REK_TAG_LINKS_TAG
    FOREIGN KEY(TAG_ID)
    REFERENCES TAGS(TAG_ID)
    ON DELETE CASCADE,
  REK_ID uuid NOT NULL,
  CONSTRAINT FK_REK_TAG_LINKS_REK
    FOREIGN KEY(REK_ID)
    REFERENCES REKS(REK_ID)
    ON DELETE CASCADE,
  PRIMARY KEY(TAG_ID, REK_ID)
);

CREATE TABLE IF NOT EXISTS FRIEND_REQUESTS
(
  FROM_USER_ID uuid NOT NULL,
  CONSTRAINT FK_FROM_USER_FRIEND_REQUEST
    FOREIGN KEY(FROM_USER_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE CASCADE,
  TO_USER_ID uuid NOT NULL,
  CONSTRAINT FK_TO_USER_FRIEND_REQUEST
    FOREIGN KEY(TO_USER_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE CASCADE,
  PRIMARY KEY(FROM_USER_ID, TO_USER_ID)
);

CREATE TABLE IF NOT EXISTS LIKES
(
  USER_ID uuid NOT NULL,
  CONSTRAINT FK_LIKES_USER
    FOREIGN KEY(USER_ID)
    REFERENCES USERS(USER_ID)
    ON DELETE CASCADE,
  REK_ID uuid NOT NULL,
  CONSTRAINT FK_LIKES_REK
    FOREIGN KEY(REK_ID)
    REFERENCES REKS(REK_ID)
    ON DELETE CASCADE,
  PRIMARY KEY(USER_ID, REK_ID)
);
