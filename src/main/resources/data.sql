

INSERT INTO Usr(id, name, pass) VALUES (1, 'test', '$2a$12$Ak.P17GxP2yyzEVEZ33vmewZhDTq3L8TLgmG6hXmjCaLFGVq2vVoy');

INSERT INTO Role(id, name) VALUES (1, 'role_user');

INSERT INTO Usr_role values (1, 1);

INSERT INTO Detail(id, cipher, name) VALUES (1, '120.8700.001', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (2, '120.8700.002', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (3, '120.8700.003', 'Корпус');
INSERT INTO Detail(id, cipher, name) VALUES (4, '120.8700.004', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (5, '120.8700.005', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (6, '120.8700.006', 'Корпус');


INSERT INTO Program(id, detail_id, index, code, type) values (1, 1, '010', 'G0 G28 WO. XO.', 'токарная');
INSERT INTO Program(id, detail_id, index, code, type) values (2, 1, '020', 'G28 W0. U0.', 'фрезерная');

INSERT INTO Tool(id, name, cipher) values (1, 'Сверло', 'СТЦ 512 6х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (2, 'Сверло', 'СТЦ 512 8х8х26х8 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (3, 'Сверло', 'СТЦ 512 10х8х26х10 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (4, 'Сверло', 'СТЦ 512 12х8х26х12 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (5, 'Сверло', 'СТЦ 512 14х8х26х14 HF AlTin');


INSERT INTO Tool(id, name, cipher) values (6, 'Фреза', 'ФТЦ 530 1х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (7, 'Фреза', 'ФТЦ 530 2х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (8, 'Фреза', 'ФТЦ 530 3х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (9, 'Фреза', 'ФТЦ 530 4х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (19, 'Фреза', 'ФТЦ 530 6х8х26х6 HF AlTin');

-- create table tool_program (program_id bigint not null, tool_id bigint not null, primary key (program_id, tool_id));
INSERT INTO tool_program values (1, 1);
INSERT INTO tool_program values (2, 1);
INSERT INTO tool_program values (2, 2);

