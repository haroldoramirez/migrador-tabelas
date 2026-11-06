DELETE FROM origem.tb_clientes;
INSERT INTO origem.tb_clientes (id, cpf_cnpj, razao_social, data_cadastro) VALUES (1, '12345678000199', 'Empresa Alpha Ltda', CURRENT_TIMESTAMP);
INSERT INTO origem.tb_clientes (id, cpf_cnpj, razao_social, data_cadastro) VALUES (2, '98765432000188', 'Comércio Beta S.A.', CURRENT_TIMESTAMP);
INSERT INTO origem.tb_clientes (id, cpf_cnpj, razao_social, data_cadastro) VALUES (3, '11122233344', 'João da Silva', CURRENT_TIMESTAMP);

DELETE FROM origem.tb_pedidos;
INSERT INTO origem.tb_pedidos (id, cliente_id, data_pedido, valor_total, status) VALUES (101, 1, CURRENT_TIMESTAMP, 1500.00, 'FATURADO');
INSERT INTO origem.tb_pedidos (id, cliente_id, data_pedido, valor_total, status) VALUES (102, 2, CURRENT_TIMESTAMP, 3200.50, 'FATURADO');
INSERT INTO origem.tb_pedidos (id, cliente_id, data_pedido, valor_total, status) VALUES (103, 3, CURRENT_TIMESTAMP, 450.00, 'PENDENTE');

DELETE FROM origem.tb_produtos;
INSERT INTO origem.tb_produtos (id, codigo_sku, descricao, preco_unitario, saldo_estoque, ativo) VALUES (10, 'SKU-001', 'Notebook Corporativo', 4500.00, 25, 1);
INSERT INTO origem.tb_produtos (id, codigo_sku, descricao, preco_unitario, saldo_estoque, ativo) VALUES (11, 'SKU-002', 'Monitor 27 Polegadas', 1200.00, 40, 1);

DELETE FROM origem.tb_usuarios;
INSERT INTO origem.tb_usuarios (id, login, email, perfil_acesso, ultimo_login) VALUES (1, 'admin', 'admin@empresa.com', 'ADMIN', CURRENT_TIMESTAMP);
INSERT INTO origem.tb_usuarios (id, login, email, perfil_acesso, ultimo_login) VALUES (2, 'operador', 'operador@empresa.com', 'OPERADOR', CURRENT_TIMESTAMP);
