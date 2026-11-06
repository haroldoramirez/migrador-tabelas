package com.migrador.strategy;

import com.migrador.model.ResultadoMigracao;
import com.migrador.model.TabelaItem;

import java.sql.Connection;

/**
 * Interface do padrão Strategy para migração de uma tabela específica
 * do banco de origem para o banco de destino.
 */
public interface MigracaoTabelaStrategy {

    /**
     * Identificador único da tabela que esta Strategy atende (ex: "TB_CLIENTES").
     */
    String getCodigoTabela();

    /**
     * Objeto descritivo da tabela para exibição no seletor em memória.
     */
    TabelaItem getTabelaItem();

    /**
     * Recebe a conexão do banco de origem vinda da camada Service e delega a execução
     * da query específica para a camada DAO.
     */
    ResultadoMigracao migrar(Connection conexaoOrigem, Integer limiteRegistros);
}
