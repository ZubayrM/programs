INSERT INTO User (id, name, pass) VALUES (1, 'test', 'testpass');

INSERT INTO Detail(id, cipher, name) VALUES (1, '120.8700.001', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (2, '120.8700.002', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (3, '120.8700.003', 'Корпус');
INSERT INTO Detail(id, cipher, name) VALUES (1, '120.8700.004', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (2, '120.8700.005', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (3, '120.8700.006', 'Корпус');


INSERT INTO Program(id, detail_id, index, code) values (1, 1, '010', 'G0 G28 WO. XO.');
INSERT INTO Program(id, detail_id, index, code) values (2, 1, '020', 'G28 W0. U0.');

INSERT INTO Tool(id, name, cipher) values (1, 'Сверло', 'СТЦ 512 6х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (2, 'Фреза', 'СТЦ 530 6х8х26х6 HF AlTin');

INSERT INTO Tool2Program values (1, 1, 1);
INSERT INTO Tool2Program values (2, 2, 1);
INSERT INTO Tool2Program values (3, 2, 2);

