CREATE SCHEMA IF NOT EXISTS origem;
CREATE SCHEMA IF NOT EXISTS destino;

CREATE TABLE IF NOT EXISTS origem.tb_clientes (
    id BIGINT PRIMARY KEY,
    cpf_cnpj VARCHAR(20),
    razao_social VARCHAR(120),
    data_cadastro TIMESTAMP
);

CREATE TABLE IF NOT EXISTS destino.tb_clientes (
    id BIGINT,
    cpf_cnpj VARCHAR(20),
    razao_social VARCHAR(120),
    data_cadastro TIMESTAMP
);

CREATE TABLE IF NOT EXISTS origem.tb_pedidos (
    id BIGINT PRIMARY KEY,
    cliente_id BIGINT,
    data_pedido TIMESTAMP,
    valor_total DECIMAL(15, 2),
    status VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS destino.tb_pedidos (
    id BIGINT,
    cliente_id BIGINT,
    data_pedido TIMESTAMP,
    valor_total DECIMAL(15, 2),
    status VARCHAR(30)
);

CREATE TABLE IF NOT EXISTS origem.tb_produtos (
    id BIGINT PRIMARY KEY,
    codigo_sku VARCHAR(40),
    descricao VARCHAR(150),
    preco_unitario DECIMAL(15, 2),
    saldo_estoque INT,
    ativo INT
);

CREATE TABLE IF NOT EXISTS destino.tb_produtos (
    id BIGINT,
    codigo_sku VARCHAR(40),
    descricao VARCHAR(150),
    preco_unitario DECIMAL(15, 2),
    saldo_estoque INT
);

CREATE TABLE IF NOT EXISTS origem.tb_usuarios (
    id BIGINT PRIMARY KEY,
    login VARCHAR(60),
    email VARCHAR(120),
    perfil_acesso VARCHAR(40),
    ultimo_login TIMESTAMP
);

CREATE TABLE IF NOT EXISTS destino.tb_usuarios (
    id BIGINT,
    login VARCHAR(60),
    email VARCHAR(120),
    perfil_acesso VARCHAR(40),
    ultimo_login TIMESTAMP
);
