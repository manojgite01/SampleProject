CREATE  TABLE users (
  username VARCHAR(45) NOT NULL ,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (username));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  role varchar(45) NOT NULL,
  PRIMARY KEY (user_role_id),
  UNIQUE KEY uni_username_role (role,username),
  KEY fk_username_idx (username),
  CONSTRAINT fk_username FOREIGN KEY (username) REFERENCES users (username));

INSERT INTO users(username,password,enabled)
VALUES ('mgite','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y', true);
INSERT INTO users(username,password,enabled)
VALUES ('akokate','$2a$10$04TVADrR6/SPLBjsK0N30.Jf5fNjBugSACeGv1S69dZALR7lSov0y', true);

INSERT INTO user_roles (username, role)
VALUES ('mgite', 'ROLE_USER');
INSERT INTO user_roles (username, role)
VALUES ('mgite', 'ROLE_ADMIN');
INSERT INTO user_roles (username, role)
VALUES ('akokate', 'ROLE_USER');

CREATE TABLE user_details (
  user_det_id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(45) NOT NULL,
  fname varchar(100) NOT NULL,
  lname varchar(100) NOT NULL,
  gender char(1) NOT NULL,
  email varchar(45) NOT NULL,
  phone_number_id int(11),
  address varchar(255),  
  city varchar(255),
  country varchar(255),
  post_code int(11),
  PRIMARY KEY (user_det_id),
  KEY fk_uname_idx (username),
  CONSTRAINT fk_uname FOREIGN KEY (username) REFERENCES users (username));