-- Script para criar o banco de dados da frota
-- Execute este script no seu MySQL

-- Criar o banco de dados
CREATE DATABASE IF NOT EXISTS frotaDB;

-- Usar o banco
USE frotaDB;

-- Tabela para CARROS
CREATE TABLE IF NOT EXISTS carros (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Campos herdados de Veiculo
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    
    -- Campos específicos de Carro
    numero_portas INT NOT NULL,
    tipo_carroceria VARCHAR(50) NOT NULL,
    capacidade_tanque DECIMAL(8,2) NOT NULL,
    
    -- Campos da interface Rastreavel
    localizacao_atual VARCHAR(255),
    
    -- Campos de auditoria
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices
    INDEX idx_placa_carro (placa)
);

-- Tabela para CAMINHÕES
CREATE TABLE IF NOT EXISTS caminhoes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Campos herdados de Veiculo
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    
    -- Campos específicos de Caminhao
    numero_eixos INT NOT NULL,
    capacidade_carga DECIMAL(10,2) NOT NULL, -- em kg
    comprimento DECIMAL(8,2) NOT NULL, -- em metros
    
    -- Campos da interface Rastreavel
    localizacao_atual VARCHAR(255),
    
    -- Campos da interface Manutencivel
    manutencao_pendente BOOLEAN DEFAULT FALSE,
    data_ultima_manutencao DATE,
    
    -- Campo do enum StatusVeiculo
    status VARCHAR(20) NOT NULL DEFAULT 'DISPONIVEL',
    
    -- Campos de auditoria
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices
    INDEX idx_placa_caminhao (placa),
    INDEX idx_status_caminhao (status)
);

-- Tabela para VANS
CREATE TABLE IF NOT EXISTS vans (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    -- Campos herdados de Veiculo
    placa VARCHAR(10) NOT NULL UNIQUE,
    modelo VARCHAR(100) NOT NULL,
    marca VARCHAR(50) NOT NULL,
    ano INT NOT NULL,
    
    -- Campos específicos de Van
    capacidade_passageiros INT NOT NULL,
    possui_acessibilidade BOOLEAN DEFAULT FALSE,
    
    -- Campos da interface Rastreavel
    localizacao_atual VARCHAR(255),
    
    -- Campos de auditoria
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_atualizacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    -- Índices
    INDEX idx_placa_van (placa)
);

-- Inserir dados de exemplo

-- Dados para tabela CARROS
INSERT INTO carros (placa, modelo, marca, ano, numero_portas, tipo_carroceria, capacidade_tanque, localizacao_atual) VALUES
('ABC-1234', 'Civic', 'Honda', 2020, 4, 'Sedan', 50.0, 'Garagem Central'),
('DEF-5678', 'Corolla', 'Toyota', 2019, 4, 'Sedan', 60.0, 'Rua das Flores, 123'),
('MNO-7890', 'Gol', 'Volkswagen', 2021, 2, 'Hatch', 55.0, 'Pátio Principal');

-- Dados para tabela VANS
INSERT INTO vans (placa, modelo, marca, ano, capacidade_passageiros, possui_acessibilidade, localizacao_atual) VALUES
('GHI-9012', 'Sprinter', 'Mercedes', 2021, 15, TRUE, 'Terminal Rodoviário'),
('PQR-3456', 'Master', 'Renault', 2020, 12, FALSE, 'Oficina Martinez');

-- Dados para tabela CAMINHÕES
INSERT INTO caminhoes (placa, modelo, marca, ano, numero_eixos, capacidade_carga, comprimento, 
                      manutencao_pendente, localizacao_atual, status) VALUES
('JKL-3456', 'Actros', 'Mercedes', 2022, 3, 25000.00, 12.5, TRUE, 'Oficina Central', 'EM_MANUTENCAO'),
('STU-7891', 'FH', 'Volvo', 2023, 4, 40000.00, 15.0, FALSE, 'Depósito Norte', 'DISPONIVEL');

-- Verificar se foi criado corretamente
SELECT 'Banco criado com sucesso!' as resultado;
SELECT 'Carros cadastrados:' as tipo, COUNT(*) as total FROM carros
UNION ALL
SELECT 'Vans cadastradas:' as tipo, COUNT(*) as total FROM vans
UNION ALL
SELECT 'Caminhões cadastrados:' as tipo, COUNT(*) as total FROM caminhoes;
