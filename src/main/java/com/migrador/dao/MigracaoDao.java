package com.migrador.dao;

import com.migrador.model.ResultadoMigracao;

import java.sql.Connection;

/**
 * Camada DAO responsável pela execução das queries específicas de migração
 * delegadas pelas Strategies utilizando a conexão repassada pela camada Service.
 */
public interface MigracaoDao {

    /**
     * Executa a query específica de migração utilizando a conexão de origem recebida.
     *
     * @param conexaoOrigem   conexão ativa com o banco de origem aberta na camada Service
     * @param codigoTabela    identificador da tabela
     * @param nomeEstrategia  nome da Strategy que delegou a execução
     * @param sqlEspecifico   query SQL específica da tabela
     * @param limiteRegistros quantidade limite de registros para migração
     * @return objeto contendo o resultado detalhado da execução no DAO
     */
    ResultadoMigracao executarQueryMigracao(
            Connection conexaoOrigem,
            String codigoTabela,
            String nomeEstrategia,
            String sqlEspecifico,
            Integer limiteRegistros
    );
}
