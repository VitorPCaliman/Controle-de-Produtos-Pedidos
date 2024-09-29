package com.controle.mspedidos.service;

import com.controle.mspedidos.model.ItemPedido;
import com.controle.mspedidos.model.Pedido;
import com.controle.mspedidos.model.Produto;
import com.controle.mspedidos.repository.ItemPedidoRepository;
import com.controle.mspedidos.repository.PedidoRepository;
import com.controle.mspedidos.repository.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoService(PedidoRepository pedidoRepository, ItemPedidoRepository itemPedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.itemPedidoRepository = itemPedidoRepository;
        this.produtoRepository = produtoRepository;
    }

    public Pedido buscarPedidoPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com ID: " + id));
    }

    public List<Pedido> listarPedidosPorPeriodo(LocalDate dataInicial, LocalDate dataFinal) {
        return pedidoRepository.listarPedidosPorPeriodo(dataInicial, dataFinal);
    }

    public Pedido criarPedido(Pedido pedido) {
        validarItensPedido(pedido);
        calcularTotalPedido(pedido);
        return pedidoRepository.save(pedido);
    }

    public void deletarPedido(Long id) {
        buscarPedidoPorId(id); // Verifica se o pedido existe
        pedidoRepository.deleteById(id);
    }

    @Transactional
    public Pedido atualizarPedido(Long id, Pedido pedidoAtualizado) {
        Pedido pedidoExistente = buscarPedidoPorId(id);
        itemPedidoRepository.deleteAllInBatch(pedidoExistente.getItens());
        pedidoExistente.getItens().clear();
        validarItensPedido(pedidoAtualizado);
        calcularTotalPedido(pedidoExistente);
        return pedidoRepository.save(pedidoExistente);
    }

    private void validarItensPedido(Pedido pedido) {
        for (ItemPedido item : pedido.getItens()) {
            Produto produto = produtoRepository.findById(item.getProduto().getId())
                    .orElseThrow(() -> new EntityNotFoundException("Produto não encontrado com ID: " + item.getProduto().getId()));
            item.setPedido(pedido);
            item.setValorVenda(produto.getPreco());
            item.getProduto().setDepartamento(produto.getDepartamento());
        }
    }

    private void calcularTotalPedido(Pedido pedido) {
        BigDecimal total = pedido.getItens().stream()
                .map(ItemPedido::getValorTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        pedido.setTotalPedido(total);
    }
}
