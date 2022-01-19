INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User2', 'user2@yandex.ru', '{noop}password'),
       ('User3', 'user3@yandex.ru', '{noop}password'),
       ('User4', 'user4@yandex.ru', '{noop}password');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 3),
       ('USER', 4),
       ('USER', 5);

INSERT INTO RESTAURANT (NAME, ADDRESS)
VALUES ('Restaurant_1', 'First str., 5'),
       ('Restaurant_2', 'Second str., 4'),
       ('Restaurant_3', 'Third str., 3'),
       ('Restaurant_4', 'Fourth str., 2'),
       ('Restaurant_5', 'Fifth str., 1');

INSERT INTO LUNCH_ITEM (LOCAL_DATE, DESCRIPTION, PRICE, RESTAURANT_ID)
VALUES (now(), 'Soup_1', 11, 1),
       (now(), 'Soup_2', 12, 2),
       (now(), 'Soup_3', 13, 3),
       (now(), 'Soup_4', 14, 4),
       (now(), 'Soup_5', 15, 5),
       (now(), 'Dish_1', 21, 1),
       (now(), 'Dish_2', 22, 2),
       (now(), 'Dish_3', 23, 3),
       (now(), 'Dish_4', 24, 4),
       (now(), 'Dish_5', 25, 5);

INSERT INTO VOTE (LOCAL_DATE, USER_ID, RESTAURANT_ID)
VALUES (now(), 3, 2),
       (now(), 4, 3),
       (now(), 5, 3);