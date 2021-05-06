package com.comunidad.simplecatproducto.departamento.service;

import java.util.List;

import com.comunidad.simplecatproducto.departamento.dao.vo.DepartamentoVoTest;

public interface DepartamentoService {

    int guardar(DepartamentoVoTest departamentosVo) throws Exception;

    int existe(DepartamentoVoTest departamentosVo) throws Exception;

    DepartamentoVoTest buscar(DepartamentoVoTest departamentosVo) throws Exception;

    List<DepartamentoVoTest> listDepartamentoVo(DepartamentoVoTest departDepartamentosVo) throws Exception;
}
