package com.controle.mspedidos.service;

import com.controle.mspedidos.model.Departamento;
import com.controle.mspedidos.repository.DepartamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DepartamentoServiceTest {

    @Mock
    private DepartamentoRepository departamentoRepository;

    @InjectMocks
    private DepartamentoService departamentoService;

    private Departamento departamento;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        departamento = new Departamento();
        departamento.setId(1L);
        departamento.setCodigo("DEP001");
        departamento.setDescricao("Eletrônicos");
    }

    @Test
    public void testSaveDepartamento_Success() {
        when(departamentoRepository.save(any(Departamento.class))).thenReturn(departamento);
        Departamento savedDepartamento = departamentoService.criarDepartamento(departamento);
        assertNotNull(savedDepartamento);
        assertEquals("DEP001", savedDepartamento.getCodigo());
        assertEquals("Eletrônicos", savedDepartamento.getDescricao());
        verify(departamentoRepository, times(1)).save(departamento);
    }

    @Test
    public void testFindById_Success() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.of(departamento));
        Departamento foundDepartamento = departamentoService.buscarDepartamentoPorId(1L);
        assertNotNull(foundDepartamento);
        assertEquals(1L, foundDepartamento.getId());
    }

    @Test
    public void testFindById_NotFound() {
        when(departamentoRepository.findById(1L)).thenReturn(Optional.empty());
        Exception exception = assertThrows(RuntimeException.class, () -> {
            departamentoService.buscarDepartamentoPorId(1L);
        });
        assertEquals("Departamento não encontrado", exception.getMessage());
    }

    @Test
    public void testDeleteDepartamento() {
        departamentoService.deletarDepartamento(1L);
        verify(departamentoRepository, times(1)).deleteById(1L);
    }
}

