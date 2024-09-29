package com.controle.mspedidos.service;

import com.controle.mspedidos.dto.ProdutoDTO;
import com.controle.mspedidos.exception.DepartamentoNotFoundException;
import com.controle.mspedidos.exception.ProdutoNotFoundException;
import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.model.Produto;
import com.controle.mspedidos.repository.DepartamentoRepository;
import com.controle.mspedidos.repository.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final DepartamentoRepository departamentoRepository;

    public ProdutoService(ProdutoRepository produtoRepository, DepartamentoRepository departamentoRepository) {
        this.produtoRepository = produtoRepository;
        this.departamentoRepository = departamentoRepository;
    }

    public Produto criarProduto(Produto produto) {
        Departamento departamento = buscarDepartamentoPorId(produto.getDepartamento().getId());
        produto.setDepartamento(departamento);
        validarProduto(produto);
        return produtoRepository.save(produto);
    }

    public ProdutoDTO buscarProdutoPorId(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));
        return converterProdutoDto(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    public Produto atualizarProduto(Long id, Produto produtoAtualizado) {
        Produto produtoExistente = produtoRepository.findById(id)
                .orElseThrow(() -> new ProdutoNotFoundException("Produto não encontrado"));

        produtoExistente.setCodigo(produtoAtualizado.getCodigo());
        produtoExistente.setDescricao(produtoAtualizado.getDescricao());
        produtoExistente.setPreco(produtoAtualizado.getPreco());
        Departamento departamento = buscarDepartamentoPorId(produtoAtualizado.getDepartamento().getId());
        produtoExistente.setDepartamento(departamento);

        validarProduto(produtoExistente);

        return produtoRepository.save(produtoExistente);
    }

    public void deletarProduto(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ProdutoNotFoundException("Produto não encontrado para exclusão");
        }
        produtoRepository.deleteById(id);
    }

    private Departamento buscarDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new DepartamentoNotFoundException("Departamento não encontrado"));
    }

    private ProdutoDTO converterProdutoDto(Produto produto) {
        ProdutoDTO dto = new ProdutoDTO();
        dto.setCodigo(produto.getCodigo());
        dto.setDescricao(produto.getDescricao());
        dto.setPreco(produto.getPreco());
        dto.setDepartamentoDescricao(produto.getDepartamento().getDescricao());
        dto.setDepartamentoId(produto.getDepartamento().getId());
        return dto;
    }

    private void validarProduto(Produto produto) {
        if (produto.getCodigo() == null || produto.getDescricao() == null || produto.getPreco() == null) {
            throw new IllegalArgumentException("Produto deve ter código, descrição e preço");
        }
    }
}
