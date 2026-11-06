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
public class PedidosMigracaoStrategy implements MigracaoTabelaStrategy {

    private static final Logger log = LoggerFactory.getLogger(PedidosMigracaoStrategy.class);
    private static final String CODIGO = "TB_PEDIDOS";
    private static final String QUERY_PEDIDOS =
            "INSERT INTO destino.tb_pedidos (id, cliente_id, data_pedido, valor_total, status) " +
            "SELECT id, cliente_id, data_pedido, valor_total, status FROM origem.tb_pedidos WHERE status = 'FATURADO' ORDER BY data_pedido DESC LIMIT %d";

    private final MigracaoDao migracaoDao;

    public PedidosMigracaoStrategy(MigracaoDao migracaoDao) {
        this.migracaoDao = migracaoDao;
    }

    @Override
    public String getCodigoTabela() {
        return CODIGO;
    }

    @Override
    public TabelaItem getTabelaItem() {
        return new TabelaItem(CODIGO, "TB_PEDIDOS - Histórico de Pedidos", "Cabeçalho e transações de vendas realizadas");
    }

    @Override
    public ResultadoMigracao migrar(Connection conexaoOrigem, Integer limiteRegistros) {
        log.info("[Strategy: PedidosMigracaoStrategy] Repassando conexão de origem e query específica da tabela {} para o DAO.", CODIGO);
        return migracaoDao.executarQueryMigracao(
                conexaoOrigem,
                CODIGO,
                this.getClass().getSimpleName(),
                QUERY_PEDIDOS,
                limiteRegistros
        );
    }
}
