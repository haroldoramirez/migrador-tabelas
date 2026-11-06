package com.migrador.strategy.impl;

import com.migrador.dao.MigracaoDao;
import com.migrador.model.ResultadoMigracao;
import com.migrador.model.TabelaItem;
import com.migrador.strategy.MigracaoTabelaStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Component
public class ProdutosMigracaoStrategy implements MigracaoTabelaStrategy {

    private static final Logger log = LoggerFactory.getLogger(ProdutosMigracaoStrategy.class);
    private static final String CODIGO = "TB_PRODUTOS";
    private static final String QUERY_PRODUTOS =
            "INSERT INTO destino.tb_produtos (id, codigo_sku, descricao, preco_unitario, saldo_estoque) " +
            "SELECT id, codigo_sku, descricao, preco_unitario, saldo_estoque FROM origem.tb_produtos WHERE ativo = 1 ORDER BY id ASC LIMIT %d";

    private final MigracaoDao migracaoDao;

    public ProdutosMigracaoStrategy(MigracaoDao migracaoDao) {
        this.migracaoDao = migracaoDao;
    }

    @Override
    public String getCodigoTabela() {
        return CODIGO;
    }

    @Override
    public TabelaItem getTabelaItem() {
        return new TabelaItem(CODIGO, "TB_PRODUTOS - Catálogo de Produtos", "Itens de estoque, preços e categorias");
    }

    @Override
    public ResultadoMigracao migrar(Connection conexaoOrigem, Integer limiteRegistros) {
        log.info("[Strategy: ProdutosMigracaoStrategy] Repassando conexão de origem e query específica da tabela {} para o DAO.", CODIGO);
        return migracaoDao.executarQueryMigracao(
                conexaoOrigem,
                CODIGO,
                this.getClass().getSimpleName(),
                QUERY_PRODUTOS,
                limiteRegistros
        );
    }
}
