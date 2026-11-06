package com.migrador.model;

/**
 * Representa um objeto de tabela mantido em memória para renderização no seletor da view.
 */
public class TabelaItem {

    private final String codigo;
    private final String nome;
    private final String descricao;

    public TabelaItem(String codigo, String nome, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.descricao = descricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
}
