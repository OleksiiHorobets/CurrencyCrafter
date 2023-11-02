INSERT INTO CURRENCY_SCHEMA.CURRENCY (NAME)
VALUES ('USD'),
       ('GBP'),
       ('CAD'),
       ('UAH'),
       ('INR');

INSERT INTO CURRENCY_SCHEMA.EXCHANGE_RATE ("DATE", RATE, CURRENCY_ID)
VALUES ('02-02-2022', 1, 1),
       ('02-03-2022', 1.2178101, 2),
       ('02-04-2022', 0.72501205, 3),
       ('02-05-2022', 0.02754681, 4),
       ('02-05-2022', 0.012013007, 5)
