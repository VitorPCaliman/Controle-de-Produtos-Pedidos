package com.controle.mspedidos.controller;


import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.service.DepartamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping
    public List<Departamento> istarPorIntervaloDeCodigo(
            @RequestParam("codigoInicial") String codigoInicial,
            @RequestParam("codigoFinal") String codigoFinal) {
        return departamentoService.listarPorIntervaloDeCodigo(codigoInicial, codigoFinal);
    }

    @PostMapping
    public Departamento criarDepartamento(@RequestBody Departamento departamento) {
        return departamentoService.criarDepartamento(departamento);
    }

    @GetMapping("/{id}")
    public Departamento buscarDepartamento(@PathVariable Long id) {
        return departamentoService.buscarDepartamentoPorId(id);
    }

    @PutMapping("/{id}")
    public Departamento atualizarDepartamento(@PathVariable Long id, @RequestBody Departamento departamentoAtualizado) {
        return departamentoService.atualizarDepartamento(id, departamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDepartamento(@PathVariable Long id) {
        departamentoService.deletarDepartamento(id);
        return ResponseEntity.noContent().build();
    }

}
