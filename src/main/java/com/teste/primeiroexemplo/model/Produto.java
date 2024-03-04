package com.teste.primeiroexemplo.model;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;



//ao usar entity, vira uma entidade no banco de dados
@Entity
public class Produto {
    //usar os mesmos nomes o insomnia e no model
    @Id //transforma essa coluna em chave primária
    @GeneratedValue(strategy = GenerationType.AUTO)//gerando os ids automaticamente
    private Integer id;

    private String nome;

    private Integer quantidade;

    private Double valor;

    private String observacao;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public Integer getQuantidade() {
        return quantidade;
    }


    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }


    public Double getValor() {
        return valor;
    }


    public void setValor(Double valor) {
        this.valor = valor;
    }


    public String getObservacao() {
        return observacao;
    }


    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}