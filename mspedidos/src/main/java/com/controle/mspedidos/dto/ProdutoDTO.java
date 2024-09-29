package com.controle.mspedidos.dto;

import java.math.BigDecimal;

public class ProdutoDTO {

    private String codigo;
    private String descricao;
    private BigDecimal preco;
    private Long departamentoId;
  private String departamentoDescricao;


    public ProdutoDTO() {}

    public ProdutoDTO(String codigo, String descricao, BigDecimal preco, Long departamentoId, String departamentoDescricao) {
        this.codigo = codigo;
        this.descricao = descricao;
        this.preco = preco;
        this.departamentoId = departamentoId;
        this.departamentoDescricao = departamentoDescricao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Long getDepartamentoId() {
        return departamentoId;
    }

    public void setDepartamentoId(Long departamentoId) {
        this.departamentoId = departamentoId;
    }

    public String getDepartamentoDescricao() {
        return departamentoDescricao;
    }

    public void setDepartamentoDescricao(String departamentoDescricao) {
        this.departamentoDescricao = departamentoDescricao;
    }

}
