DELETE FROM user_roles;
DELETE FROM VOTE;
DELETE FROM LUNCH_ITEM;
DELETE FROM RESTAURANT;
DELETE FROM users;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('User2', 'user2@yandex.ru', 'password'),
       ('User3', 'user3@yandex.ru', 'password'),
       ('User4', 'user4@yandex.ru', 'password');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 0),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('ADMIN', 1);

INSERT INTO RESTAURANT (NAME, ADDRESS)
VALUES ('Restaurant_0', 'First str., 5'),
       ('Restaurant_1', 'Second str., 4'),
       ('Restaurant_2', 'Third str., 3'),
       ('Restaurant_3', 'Fourth str., 2'),
       ('Restaurant_4', 'Fifth str., 1');

INSERT INTO LUNCH_ITEM (DATE_TIME, NAME, PRICE, RESTAURANT_ID)
VALUES ('2020-01-30 08:00:00', 'Soup_0', 10, 0),
       ('2020-01-30 08:00:00', 'Soup_1', 11, 1),
       ('2020-01-30 08:00:00', 'Soup_2', 12, 2),
       ('2020-01-30 08:00:00', 'Soup_3', 13, 3),
       ('2020-01-30 08:00:00', 'Soup_4', 14, 4),
       ('2020-01-30 08:00:00', 'Dish_0', 20, 0),
       ('2020-01-30 08:00:00', 'Dish_1', 21, 1),
       ('2020-01-30 08:00:00', 'Dish_2', 22, 2),
       ('2020-01-30 08:00:00', 'Dish_3', 23, 3),
       ('2020-01-30 08:00:00', 'Dish_4', 24, 4);

INSERT INTO VOTE (DATE_TIME, USER_ID, RESTAURANT_ID)
VALUES ('2020-01-30 10:00:00', 1, 1),
       ('2020-01-30 10:00:00', 2, 1),
       ('2020-01-30 10:00:00', 3, 2),
       ('2020-01-30 10:00:00', 4, 4),
       ('2020-01-30 10:00:00', 0, 3);
