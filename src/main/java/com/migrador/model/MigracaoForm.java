package com.migrador.model;

/**
 * Objeto que recebe os dados enviados pelo formulário POST da página migrador.
 */
public class MigracaoForm {

    private String tabelas;
    private Integer limiteRegistros;

    public MigracaoForm() {
    }

    public MigracaoForm(String tabelas, Integer limiteRegistros) {
        this.tabelas = tabelas;
        this.limiteRegistros = limiteRegistros;
    }

    public String getTabelas() {
        return tabelas;
    }

    public void setTabelas(String tabelas) {
        this.tabelas = tabelas;
    }

    public Integer getLimiteRegistros() {
        return limiteRegistros;
    }

    public void setLimiteRegistros(Integer limiteRegistros) {
        this.limiteRegistros = limiteRegistros;
    }
}
