package com.migrador.service;

import com.migrador.model.ResultadoMigracao;
import com.migrador.model.TabelaItem;
import com.migrador.strategy.MigracaoTabelaStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Serviço responsável por manter a lista de tabelas em memória, obter a conexão com o
 * banco de origem via try-with-resources e repassá-la para a Strategy e para a camada DAO.
 */
@Service
public class MigracaoStrategyService {

    private static final Logger log = LoggerFactory.getLogger(MigracaoStrategyService.class);

    private final Map<String, MigracaoTabelaStrategy> estrategiasPorTabela;
    private final List<TabelaItem> tabelasEmMemoria;
    private final DataSource dataSourceOrigem;

    public MigracaoStrategyService(List<MigracaoTabelaStrategy> strategies, DataSource dataSourceOrigem) {
        this.dataSourceOrigem = dataSourceOrigem;

        Map<String, MigracaoTabelaStrategy> mapaTemp = new HashMap<String, MigracaoTabelaStrategy>();
        List<TabelaItem> listaTemp = new ArrayList<TabelaItem>();

        for (MigracaoTabelaStrategy strategy : strategies) {
            mapaTemp.put(strategy.getCodigoTabela(), strategy);
            listaTemp.add(strategy.getTabelaItem());
        }

        Collections.sort(listaTemp, new Comparator<TabelaItem>() {
            @Override
            public int compare(TabelaItem item1, TabelaItem item2) {
                return item1.getCodigo().compareTo(item2.getCodigo());
            }
        });

        this.estrategiasPorTabela = Collections.unmodifiableMap(mapaTemp);
        this.tabelasEmMemoria = Collections.unmodifiableList(listaTemp);
    }

    /**
     * Retorna a lista de objetos de tabelas armazenada em memória para o seletor da página.
     */
    public List<TabelaItem> listarTabelasEmMemoria() {
        return tabelasEmMemoria;
    }

    /**
     * Obtém uma conexão com o banco de dados de origem.
     */
    protected Connection obterConexao() throws SQLException {
        log.info("[Service] Obtendo conexão com o banco de origem...");
        return dataSourceOrigem.getConnection();
    }

    /**
     * Resolve a Strategy correspondente à tabela selecionada diretamente pelo Map,
     * abre a conexão com o banco de origem em um bloco try-with-resources (Java 7)
     * e repassa a conexão para a Strategy e camada DAO.
     */
    public ResultadoMigracao executarMigracao(String codigoTabela, Integer limiteRegistros) {
        MigracaoTabelaStrategy strategy = estrategiasPorTabela.get(codigoTabela);
        if (strategy == null) {
            throw new IllegalArgumentException(
                    "Nenhuma estratégia de migração registrada para a tabela: " + codigoTabela
            );
        }

        try (Connection conexaoOrigem = obterConexao()) {
            return strategy.migrar(conexaoOrigem, limiteRegistros);
        } catch (SQLException e) {
            throw new IllegalStateException("Erro ao obter ou gerenciar a conexão com o banco de origem", e);
        }
    }
}
