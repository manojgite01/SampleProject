CREATE DATABASE smartapp_dev;

USE smartapp_dev;

CREATE TABLE Employee (
  id int(11) unsigned NOT NULL AUTO_INCREMENT,
  name varchar(20) DEFAULT NULL,
  role varchar(20) DEFAULT NULL,
  created_on datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
 
INSERT INTO Employee (id, name, role, created_on)
VALUES(1, 'Manoj', 'CEO', now());
INSERT INTO Employee (id, name, role, created_on)
VALUES(2, 'Sumit', 'CEO', now());