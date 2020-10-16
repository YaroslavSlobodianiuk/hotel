SET NAMES 'utf8';

DROP SCHEMA IF EXISTS final_project_db;

CREATE SCHEMA final_project_db DEFAULT CHARACTER SET 'utf8';

USE final_project_db;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS apartments;
DROP TABLE IF EXISTS user_orders;
DROP TABLE IF EXISTS processed_orders;

DROP TABLE IF EXISTS categories;
DROP TABLE IF EXISTS room_capacity;
DROP TABLE IF EXISTS statuses;
DROP TABLE IF EXISTS order_statuses;
DROP TABLE IF EXISTS roles;

CREATE TABLE room_capacity (
  id int(11) NOT NULL AUTO_INCREMENT,
  capacity int(11) NOT NULL,
  PRIMARY KEY (id)
);


CREATE TABLE statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  status_name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE categories (
  id int(11) NOT NULL AUTO_INCREMENT,
  category_name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE order_statuses (
  id int(11) NOT NULL AUTO_INCREMENT,
  status_name varchar(50) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE roles (
  id int(11) NOT NULL AUTO_INCREMENT,
  role_name varchar(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE users (
  id int(11) NOT NULL AUTO_INCREMENT,
  login varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  first_name varchar(255) NOT NULL,
  last_name varchar(255) NOT NULL,
  role_id int(11) NOT NULL DEFAULT 2,
  locale_name varchar(50) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (role_id) REFERENCES roles(id)
)
ENGINE = INNODB,
AUTO_INCREMENT = 1,
AVG_ROW_LENGTH = 5461,
CHARACTER SET utf8,
COLLATE utf8_general_ci;

CREATE TABLE apartments (
  id int(11) NOT NULL AUTO_INCREMENT,
  title varchar(50) NOT NULL,
  room_capacity_id int(11) NOT NULL,
  category_id int(11) NOT NULL,
  price int(11) NOT NULL,
  status_id int(11) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (room_capacity_id) REFERENCES room_capacity(id),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (status_id) REFERENCES statuses(id)
);

CREATE TABLE user_orders (
  id int(11) NOT NULL AUTO_INCREMENT,
  user_id int(11) NOT NULL,
  apartment_id int(11) NOT NULL,
  room_capacity_id int(11) NOT NULL,
  category_id int(11) NOT NULL,
  arrival date NOT NULL,
  departure date NOT NULL,
  order_status_id int(11) DEFAULT 1,
  user_comment varchar(255) DEFAULT '',
  transaction_start timestamp DEFAULT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (apartment_id) REFERENCES apartments(id),
  FOREIGN KEY (room_capacity_id) REFERENCES room_capacity(id),
  FOREIGN KEY (category_id) REFERENCES categories(id),
  FOREIGN KEY (order_status_id) REFERENCES order_statuses(id)
);

INSERT INTO roles VALUES(1, 'admin');
INSERT INTO roles VALUES(2, 'client');

INSERT INTO users VALUES(DEFAULT, 'admin', 'admin', 'Ivan', 'Ivanov', 1, 'en');
INSERT INTO users VALUES(DEFAULT, 'client', 'client', 'Petr', 'Petrov', 2, 'ru');

INSERT INTO categories VALUES(1, 'econom');
INSERT INTO categories VALUES(2, 'standard');
INSERT INTO categories VALUES(3, 'luxe');
INSERT INTO categories VALUES(4, 'deluxe');

INSERT INTO statuses VALUES(1, 'free');
INSERT INTO statuses VALUES(2, 'reserved');
INSERT INTO statuses VALUES(3, 'booked');
INSERT INTO statuses VALUES(4, 'inaccessible');

INSERT INTO order_statuses VALUES(1, 'new');
INSERT INTO order_statuses VALUES(2, 'waiting for approve');
INSERT INTO order_statuses VALUES(3, 'approved');
INSERT INTO order_statuses VALUES(4, 'waiting for payment');
INSERT INTO order_statuses VALUES(5, 'paid');
INSERT INTO order_statuses VALUES(6, 'declined');
INSERT INTO order_statuses VALUES(7, 'expired');
INSERT INTO order_statuses VALUES(8, 'canceled');

INSERT INTO room_capacity VALUES(1, 1);
INSERT INTO room_capacity VALUES(2, 2);
INSERT INTO room_capacity VALUES(3, 3);
INSERT INTO room_capacity VALUES(4, 4);

INSERT INTO apartments VALUES(DEFAULT, 'apartment1', 4, 4, 1000, 1);
INSERT INTO apartments VALUES(DEFAULT, 'apartment2', 3, 3, 800, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment3', 2, 2, 500, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment4', 2, 3, 700, 1);
INSERT INTO apartments VALUES(DEFAULT, 'apartment5', 1, 1, 300, 2);
INSERT INTO apartments VALUES(DEFAULT, 'apartment6', 1, 1, 300, 2);

SELECT * FROM categories;
SELECT * FROM room_capacity;
SELECT * FROM statuses;
SELECT * FROM apartments;
SELECT * FROM user_orders;
SELECT * FROM order_statuses;
SELECT * FROM roles;
SELECT * FROM users;