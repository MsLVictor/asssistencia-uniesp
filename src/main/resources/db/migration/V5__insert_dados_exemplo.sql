INSERT INTO clientes (nome, cpf, email, ativo) VALUES ('Maria Silva', '11111111111', 'maria@email.com', TRUE);
INSERT INTO clientes (nome, cpf, email, ativo) VALUES ('Joao Souza', '22222222222', 'joao@email.com', FALSE);
INSERT INTO clientes (nome, cpf, email, ativo) VALUES ('Ana Lima', '33333333333', 'ana@email.com', TRUE);

INSERT INTO equipamentos (tipo, marca, modelo, numero_serie, cliente_id)
VALUES ('Impressora', 'HP', 'DeskJet 2774', 'HP-001', (SELECT id FROM clientes WHERE cpf = '11111111111'));
INSERT INTO equipamentos (tipo, marca, modelo, numero_serie, cliente_id)
VALUES ('Notebook', 'Dell', 'Inspiron 15', 'DL-002', (SELECT id FROM clientes WHERE cpf = '11111111111'));
INSERT INTO equipamentos (tipo, marca, modelo, numero_serie, cliente_id)
VALUES ('Celular', 'Samsung', 'Galaxy A54', 'SM-003', (SELECT id FROM clientes WHERE cpf = '22222222222'));
INSERT INTO equipamentos (tipo, marca, modelo, numero_serie, cliente_id)
VALUES ('Monitor', 'LG', '24MK430H', 'LG-004', (SELECT id FROM clientes WHERE cpf = '33333333333'));