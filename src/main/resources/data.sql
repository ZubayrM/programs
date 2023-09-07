

INSERT INTO Usr(id, name, pass) VALUES (100, 'test', '$2a$12$Ak.P17GxP2yyzEVEZ33vmewZhDTq3L8TLgmG6hXmjCaLFGVq2vVoy');

INSERT INTO Role(id, name) VALUES (100, 'role_user');

INSERT INTO Usr_role values (100, 100);

INSERT INTO Detail(id, cipher, name) VALUES (100, '120.8700.001', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (200, '120.8700.002', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (300, '120.8700.003', 'Корпус');
INSERT INTO Detail(id, cipher, name) VALUES (400, '120.8700.004', 'Болт');
INSERT INTO Detail(id, cipher, name) VALUES (500, '120.8700.005', 'Гайка');
INSERT INTO Detail(id, cipher, name) VALUES (600, '120.8700.006', 'Корпус');


INSERT INTO Program(id, detail_id, index, code_haas, type) values (100, 100, '010', 'G0 G28 WO. XO.', 'токарная');
INSERT INTO Program(id, detail_id, index, code_fanuc, type) values (200, 100, '020', 'G28 W0. U0.', 'фрезерная');

INSERT INTO Tool(id, name, cipher) values (100, 'Сверло', 'СТЦ 512 6х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (200, 'Сверло', 'СТЦ 512 8х8х26х8 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (300, 'Сверло', 'СТЦ 512 10х8х26х10 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (400, 'Сверло', 'СТЦ 512 12х8х26х12 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (500, 'Сверло', 'СТЦ 512 14х8х26х14 HF AlTin');


INSERT INTO Tool(id, name, cipher) values (600, 'Фреза', 'ФТЦ 530 1х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (700, 'Фреза', 'ФТЦ 530 2х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (800, 'Фреза', 'ФТЦ 530 3х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (900, 'Фреза', 'ФТЦ 530 4х8х26х6 HF AlTin');
INSERT INTO Tool(id, name, cipher) values (1900, 'Фреза', 'ФТЦ 530 6х8х26х6 HF AlTin');

-- create table tool_program (program_id bigint not null, tool_id bigint not null, primary key (program_id, tool_id));
INSERT INTO tool_program values (100, 100);
INSERT INTO tool_program values (200, 100);
INSERT INTO tool_program values (200, 200);

