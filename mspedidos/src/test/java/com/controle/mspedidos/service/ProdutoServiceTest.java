package com.controle.mspedidos.service;

import com.controle.mspedidos.dto.ProdutoDTO;
import com.controle.mspedidos.model.Produto;
import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.repository.ProdutoRepository;
import com.controle.mspedidos.repository.DepartamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    private Produto produto;
    private Departamento departamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        departamento = new Departamento();
        departamento.setId(1L);
        departamento.setCodigo("DEP001");

        produto = new Produto();
        produto.setId(1L);
        produto.setCodigo("PROD001");
        produto.setDescricao("Smartphone");
        produto.setDepartamento(departamento);
        produto.setPreco(new BigDecimal(500));
    }

    @Test
    public void testSaveProduto_Success() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        when(produtoRepository.save(any(Produto.class))).thenReturn(produto);

        Produto savedProduto = produtoService.criarProduto(produto);

        assertNotNull(savedProduto);
        assertEquals("PROD001", savedProduto.getCodigo());

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void testFindById_Success() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));

        ProdutoDTO foundProduto = produtoService.buscarProdutoPorId(1L);

        assertNotNull(foundProduto);
        assertEquals("PROD001", foundProduto.getCodigo());
    }
}
