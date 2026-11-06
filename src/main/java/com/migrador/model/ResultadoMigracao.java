package com.migrador.model;

import java.util.Date;

/**
 * Representa o resultado do processamento da migração executado pela Strategy correspondente.
 */
public class ResultadoMigracao {

    private final String codigoTabela;
    private final String nomeEstrategia;
    private final Integer limiteRegistros;
    private final String queryExecutada;
    private final String mensagem;
    private final Date dataHoraExecucao;

    public ResultadoMigracao(
            String codigoTabela,
            String nomeEstrategia,
            Integer limiteRegistros,
            String queryExecutada,
            String mensagem
    ) {
        this.codigoTabela = codigoTabela;
        this.nomeEstrategia = nomeEstrategia;
        this.limiteRegistros = limiteRegistros;
        this.queryExecutada = queryExecutada;
        this.mensagem = mensagem;
        this.dataHoraExecucao = new Date();
    }

    public String getCodigoTabela() {
        return codigoTabela;
    }

    public String getNomeEstrategia() {
        return nomeEstrategia;
    }

    public Integer getLimiteRegistros() {
        return limiteRegistros;
    }

    public String getQueryExecutada() {
        return queryExecutada;
    }

    public String getMensagem() {
        return mensagem;
    }

    public Date getDataHoraExecucao() {
        return dataHoraExecucao;
    }
}
