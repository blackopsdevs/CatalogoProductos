package com.comunidad.simplecatproducto.departamento.dao;

import com.comunidad.simplecatproducto.departamento.dao.vo.DepartamentoVoTest;
import java.util.List;

public interface DepartamentoDao {
	int guardar(DepartamentoVoTest departamentoVo) throws Exception;
	int actualizar(DepartamentoVoTest departamentoVo) throws Exception;
	int existe(DepartamentoVoTest departamentoVo) throws Exception;
	DepartamentoVoTest buscar(DepartamentoVoTest departamentoVo) throws Exception;
	List<DepartamentoVoTest> listDepartamentoVo(DepartamentoVoTest departamentoVo) throws Exception;
}