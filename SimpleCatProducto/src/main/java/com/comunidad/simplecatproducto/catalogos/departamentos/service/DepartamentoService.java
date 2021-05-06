package com.comunidad.simplecatproducto.catalogos.departamentos.service;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.DepartamentosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;

public interface DepartamentoService {

    int guardar(DepartamentosVo departamentoVo) throws Exception;

    int borrar(DepartamentosVo departamentoVo) throws Exception;

    int existe(DepartamentosVo departamentoVo) throws Exception;

    DepartamentosVo buscar(DepartamentosVo departamentoVo) throws Exception;

    List<DepartamentosVo> lista(DepartamentosVo departamentoVo) throws Exception;

    List<ItemComboboxModel> catalogo() throws Exception;
}
