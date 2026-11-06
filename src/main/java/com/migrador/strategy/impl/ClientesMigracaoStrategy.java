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
public class ClientesMigracaoStrategy implements MigracaoTabelaStrategy {

    private static final Logger log = LoggerFactory.getLogger(ClientesMigracaoStrategy.class);
    private static final String CODIGO = "TB_CLIENTES";
    private static final String QUERY_CLIENTES =
            "INSERT INTO destino.tb_clientes (id, cpf_cnpj, razao_social, data_cadastro) " +
            "SELECT id, cpf_cnpj, razao_social, data_cadastro FROM origem.tb_clientes ORDER BY id ASC LIMIT %d";

    private final MigracaoDao migracaoDao;

    public ClientesMigracaoStrategy(MigracaoDao migracaoDao) {
        this.migracaoDao = migracaoDao;
    }

    @Override
    public String getCodigoTabela() {
        return CODIGO;
    }

    @Override
    public TabelaItem getTabelaItem() {
        return new TabelaItem(CODIGO, "TB_CLIENTES - Cadastro de Clientes", "Dados cadastrais de pessoas físicas e jurídicas");
    }

    @Override
    public ResultadoMigracao migrar(Connection conexaoOrigem, Integer limiteRegistros) {
        log.info("[Strategy: ClientesMigracaoStrategy] Repassando conexão de origem e query específica da tabela {} para o DAO.", CODIGO);
        return migracaoDao.executarQueryMigracao(
                conexaoOrigem,
                CODIGO,
                this.getClass().getSimpleName(),
                QUERY_CLIENTES,
                limiteRegistros
        );
    }
}
