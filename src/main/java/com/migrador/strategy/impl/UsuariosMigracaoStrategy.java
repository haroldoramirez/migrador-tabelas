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
public class UsuariosMigracaoStrategy implements MigracaoTabelaStrategy {

    private static final Logger log = LoggerFactory.getLogger(UsuariosMigracaoStrategy.class);
    private static final String CODIGO = "TB_USUARIOS";
    private static final String QUERY_USUARIOS =
            "INSERT INTO destino.tb_usuarios (id, login, email, perfil_acesso, ultimo_login) " +
            "SELECT id, login, email, perfil_acesso, ultimo_login FROM origem.tb_usuarios ORDER BY id ASC LIMIT %d";

    private final MigracaoDao migracaoDao;

    public UsuariosMigracaoStrategy(MigracaoDao migracaoDao) {
        this.migracaoDao = migracaoDao;
    }

    @Override
    public String getCodigoTabela() {
        return CODIGO;
    }

    @Override
    public TabelaItem getTabelaItem() {
        return new TabelaItem(CODIGO, "TB_USUARIOS - Usuários do Sistema", "Credenciais, perfis e permissões de acesso");
    }

    @Override
    public ResultadoMigracao migrar(Connection conexaoOrigem, Integer limiteRegistros) {
        log.info("[Strategy: UsuariosMigracaoStrategy] Repassando conexão de origem e query específica da tabela {} para o DAO.", CODIGO);
        return migracaoDao.executarQueryMigracao(
                conexaoOrigem,
                CODIGO,
                this.getClass().getSimpleName(),
                QUERY_USUARIOS,
                limiteRegistros
        );
    }
}
