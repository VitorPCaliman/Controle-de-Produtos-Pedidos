package com.controle.mspedidos.service;

import com.controle.mspedidos.model.Pedido;
import com.controle.mspedidos.model.ItemPedido;
import com.controle.mspedidos.model.Produto;
import com.controle.mspedidos.repository.PedidoRepository;
import com.controle.mspedidos.repository.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;
    private ItemPedido itemPedido;
    private Produto produto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        produto = new Produto();
        produto.setId(1L);
        produto.setPreco(new BigDecimal("100.00"));

        itemPedido = new ItemPedido();
        itemPedido.setProduto(produto);
        itemPedido.setQuantidade(2);

        pedido = new Pedido();
        pedido.setId(1L);
        pedido.setNumero("PED001");
        pedido.setData(LocalDate.now());
        pedido.setItens(List.of(itemPedido));
    }

    @Test
    public void testSavePedido_Success() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.of(produto));
        when(pedidoRepository.save(any(Pedido.class))).thenReturn(pedido);

        Pedido savedPedido = pedidoService.criarPedido(pedido);

        assertNotNull(savedPedido);
        assertEquals("PED001", savedPedido.getNumero());
        verify(pedidoRepository, times(1)).save(any(Pedido.class));
    }

    @Test
    public void testSavePedido_ProdutoNotFound() {
        when(produtoRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            pedidoService.criarPedido(pedido);
        });
        assertEquals("Produto não encontrado com ID: 1", exception.getMessage());
    }
}