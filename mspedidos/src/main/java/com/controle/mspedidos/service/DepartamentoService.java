package com.controle.mspedidos.service;

import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.repository.DepartamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<Departamento> listarPorIntervaloDeCodigo(String codigoInicial, String codigoFinal) {
        return departamentoRepository.consultarDepartamentosComProdutosPorIntervaloDeCodigo(codigoInicial, codigoFinal);
    }

    public Departamento criarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public Departamento buscarDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Departamento não encontrado com ID: " + id));
    }

    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Departamento atualizarDepartamento(Long id, Departamento departamentoAtualizado) {
        Departamento departamentoExistente = buscarDepartamentoPorId(id);

        departamentoExistente.setCodigo(departamentoAtualizado.getCodigo());
        departamentoExistente.setDescricao(departamentoAtualizado.getDescricao());

        return departamentoRepository.save(departamentoExistente);
    }

    public void deletarDepartamento(Long id) {
        buscarDepartamentoPorId(id);
        departamentoRepository.deleteById(id);
    }
}
