DROP TABLE IF EXISTS user_telephone;
DROP TABLE IF EXISTS user_table;
DROP TABLE IF EXISTS address;
DROP TABLE IF EXISTS telephone;

CREATE TABLE telephone (
  id BIGINT NOT NULL,
   number VARCHAR(255),
   CONSTRAINT pk_telephone PRIMARY KEY (id)
);

CREATE TABLE address (
  id BIGINT NOT NULL,
   landline BIGINT,
   address_line_1 VARCHAR(255) NOT NULL,
   town VARCHAR(255),
   post_code VARCHAR(255) NOT NULL,
   CONSTRAINT pk_address PRIMARY KEY (id)
);

ALTER TABLE address ADD CONSTRAINT FK_ADDRESS_ON_LANDLINE FOREIGN KEY (landline) REFERENCES telephone (id);

CREATE TABLE user_table (
  id BIGINT NOT NULL,
   first_name VARCHAR(255) NOT NULL,
   last_name VARCHAR(255) NOT NULL,
   address_id BIGINT,
   CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_telephone (
    user_id BIGINT NOT NULL,
    telephone_id BIGINT NOT NULL,
    CONSTRAINT pk_user_telephone PRIMARY KEY (user_id, telephone_id)
);

ALTER TABLE user_table ADD CONSTRAINT FK_USER_ON_ADDRESS FOREIGN KEY (address_id) REFERENCES address (id);

ALTER TABLE user_telephone ADD CONSTRAINT FK_USER_PHONE FOREIGN KEY (user_id) REFERENCES user_table (id);

ALTER TABLE user_telephone ADD CONSTRAINT FK_PHONE_USER FOREIGN KEY (telephone_id) REFERENCES telephone (id);
