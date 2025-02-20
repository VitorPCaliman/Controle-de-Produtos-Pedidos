package com.controle.mspedidos.controller;

import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.model.Pedido;
import com.controle.mspedidos.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Operation(summary = "Buscar pedido por ID",
            description = "Retorna os detalhes de um pedido com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Pedido não encontrado com ID: 1")))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
       Pedido pedidoExistente = pedidoService.buscarPedidoPorId(id);
        return ResponseEntity.ok(pedidoExistente);
    }

    @Operation(summary = "Listar pedidos por um determinado intervalo de datas",
            description = "Retorna uma lista de pedidos e seus detalhes, filtrados por um intervalo de códigos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de Pedidos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\":\"O código inicial não pode ser maior que o código final\"}"))),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro inesperado. Por favor, tente novamente.")
    })
    @GetMapping("/periodo")
    public List<Pedido> buscarPorPeriodo(
            @RequestParam("dataInicial") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicial,
            @RequestParam("dataFinal") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFinal) {
        return pedidoService.listarPedidosPorPeriodo(dataInicial, dataFinal);
    }

    @Operation(summary = "Criar um novo Pedido",
            description = "Adiciona um novo pedido à base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Pedido.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do Pedido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\":\"\"Produto não encontrado com ID: X \"\"}"))),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro inesperado. Por favor, tente novamente.")
    })
    @PostMapping
    public ResponseEntity<Pedido> criarPedido(@RequestBody Pedido pedido) {
        Pedido pedidoSalvo = pedidoService.criarPedido(pedido);
        return ResponseEntity.ok(pedidoSalvo);
    }

    @Operation(summary = "Deletar um pedido",
            description = "Remove um pedido com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Pedido não encontrado com ID: 1")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        pedidoService.deletarPedido(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualizar um pedido existente",
            description = "Atualiza os dados de um pedido com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Pedido não encontrado com ID: 1")))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> atualizarPedido(@PathVariable Long id, @RequestBody Pedido pedidoAtualizado) {
        Pedido pedido = pedidoService.atualizarPedido(id, pedidoAtualizado);
        return ResponseEntity.ok(pedido);
    }
}
