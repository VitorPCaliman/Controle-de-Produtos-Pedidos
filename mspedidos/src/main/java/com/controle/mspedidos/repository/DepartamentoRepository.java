package com.controle.mspedidos.repository;

import com.controle.mspedidos.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    @Query("SELECT d FROM Departamento d JOIN d.produtos p WHERE d.codigo BETWEEN :codigoInicial AND :codigoFinal ORDER BY d.codigo, p.descricao")
    List<Departamento> consultarDepartamentosComProdutosPorIntervaloDeCodigo(@Param("codigoInicial") String codigoInicial, @Param("codigoFinal") String codigoFinal);
}
