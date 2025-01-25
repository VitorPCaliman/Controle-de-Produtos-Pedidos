package com.controle.mspedidos.controller;


import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.service.DepartamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
@Tag(name = "Departamento", description = "Endpoints para gerenciamento de departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @Operation(summary = "Listar departamentos por intervalo de códigos",
            description = "Retorna uma lista de departamentos que possuem produtos associados, filtrados por um intervalo de códigos.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de departamentos retornada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class),
                            examples = @ExampleObject(value = "[{\"id\":1,\"codigo\":\"DPT001\",\"descricao\":\"Eletrônicos\"}]"))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos fornecidos",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\":\"O código inicial não pode ser maior que o código final\"}"))),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro inesperado. Por favor, tente novamente.")
    })
    @GetMapping
    public List<Departamento> listarPorIntervaloDeCodigo(
            @RequestParam("codigoInicial") @Parameter(description = "Código inicial para o filtro (inclusive).", example = "DPT001") String codigoInicial,
            @RequestParam("codigoFinal") @Parameter(description = "Código final para o filtro (inclusive).", example = "DPT010") String codigoFinal) {
        return departamentoService.listarPorIntervaloDeCodigo(codigoInicial, codigoFinal);
    }

    @Operation(summary = "Criar um novo departamento",
            description = "Adiciona um novo departamento à base de dados.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento criado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do departamento",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "{\"error\":\"O campo 'codigo' é obrigatório.\"}"))),
            @ApiResponse(responseCode = "500", description = "Ocorreu um erro inesperado. Por favor, tente novamente.")
    })
    @PostMapping
    public Departamento criarDepartamento(
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Objeto que representa o departamento a ser criado.",
                required = true,
                content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Departamento.class),
                    examples = @ExampleObject(value = "{\"codigo\":\"DPT011\",\"descricao\":\"Roupas\"}"))
        ) Departamento departamento) {
            return departamentoService.criarDepartamento(departamento);
    }

    @Operation(summary = "Buscar departamento por ID",
            description = "Retorna os detalhes de um departamento com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento encontrado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"codigo\":\"DPT001\",\"descricao\":\"Eletrônicos\"}"))),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Departamento não encontrado com ID: 1")))
    })
    @GetMapping("/{id}")
    public Departamento buscarDepartamento(@PathVariable @Parameter(description = "ID do departamento a ser buscado", example = "1") Long id) {
        return departamentoService.buscarDepartamentoPorId(id);
    }

    @Operation(summary = "Atualizar um departamento existente",
            description = "Atualiza os dados de um departamento com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Departamento atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Departamento.class),
                            examples = @ExampleObject(value = "{\"id\":1,\"codigo\":\"DPT001\",\"descricao\":\"Eletrônicos Atualizados\"}"))),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Departamento não encontrado com ID: 1")))
    })
    @PutMapping("/{id}")
    public Departamento atualizarDepartamento(
            @PathVariable @Parameter(description = "ID do departamento a ser atualizado", example = "1") Long id,
            @RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Objeto contendo os novos dados do departamento",
                    required = true,
                    content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = Departamento.class),
                                        examples = @ExampleObject(value = "{\"codigo\":\"DPT001\",\"descricao\":\"Eletrônicos Atualizados\"}"))
            ) Departamento departamentoAtualizado) {
        return departamentoService.atualizarDepartamento(id, departamentoAtualizado);
    }

    @Operation(summary = "Deletar um departamento",
            description = "Remove um departamento com base no ID fornecido.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Departamento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Departamento não encontrado com o ID fornecido",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(value = "Departamento não encontrado com ID: 1")))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(
            @PathVariable @Parameter(description = "ID do departamento a ser deletado", example = "1")Long id) {
        departamentoService.deletarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

}
