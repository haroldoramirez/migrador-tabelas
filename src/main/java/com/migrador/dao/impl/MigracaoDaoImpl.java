package com.migrador.dao.impl;

import com.migrador.dao.MigracaoDao;
import com.migrador.model.ResultadoMigracao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementação da camada DAO que recebe a conexão repassada pela Service/Strategy
 * e executa a query específica de migração entre o banco de origem e destino.
 */
@Repository
public class MigracaoDaoImpl implements MigracaoDao {

    private static final Logger log = LoggerFactory.getLogger(MigracaoDaoImpl.class);

    @Override
    public ResultadoMigracao executarQueryMigracao(
            Connection conexaoOrigem,
            String codigoTabela,
            String nomeEstrategia,
            String sqlEspecifico,
            Integer limiteRegistros
    ) {
        String queryRenderizada = String.format(sqlEspecifico, limiteRegistros);

        log.info("[DAO] Executando query específica delegada por {}: {}", nomeEstrategia, queryRenderizada);

        try (PreparedStatement stmt = conexaoOrigem.prepareStatement(queryRenderizada)) {
            int registrosMigrados = stmt.executeUpdate();

            String mensagem = String.format(
                    "Migração da tabela %s concluída pela camada DAO via %s (%d registro(s) migrado(s), limite solicitado: %d).",
                    codigoTabela,
                    nomeEstrategia,
                    registrosMigrados,
                    limiteRegistros
            );

            return new ResultadoMigracao(
                    codigoTabela,
                    nomeEstrategia,
                    limiteRegistros,
                    queryRenderizada,
                    mensagem
            );
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao executar query de migração na camada DAO para a tabela " + codigoTabela, e);
        }
    }
}
