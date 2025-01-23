package com.controle.mspedidos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Schema(description = "Representa um departamento da organização, com informações básicas e relação com produtos.")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Identificador único do departamento", example = "1")
    private Long id;

    @NotBlank(message = "O código do departamento é obrigatório.")
    @Size(max = 10, message = "O código do departamento não pode ter mais de 10 caracteres.")
    @Schema(description = "Código que representa o departamento", example = "DPT001", required = true)
    private String codigo;

    @NotBlank(message = "A descrição do departamento é obrigatória.")
    @Size(max = 100, message = "A descrição do departamento não pode ter mais de 100 caracteres.")
    @Schema(description = "Descrição breve e direta do departamento", example = "Eletrônicos", required = true)
    private String descricao;

    @OneToMany(mappedBy = "departamento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Produto> produtos;


    public Departamento() {}

    public Departamento(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }
}
