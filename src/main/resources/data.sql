INSERT INTO User (id, name, pass) VALUES (1, 'test', 'testpass');

INSERT INTO Detail(id, cipher, name) VALUES (1, '0001', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (2, '0002', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (3, '0003', 'Корпус');

INSERT INTO Program(id, detail_id, index, code) values (1, 1, '010', 'code');
INSERT INTO Program(id, detail_id, index, code) values (2, 1, '020', 'code2');