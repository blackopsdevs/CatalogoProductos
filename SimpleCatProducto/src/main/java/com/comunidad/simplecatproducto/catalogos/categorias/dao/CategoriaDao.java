/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.dao;

import com.comunidad.simplecatproducto.catalogos.categorias.dao.vo.CategoriasVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;

/**
 *
 * @author Santiago
 */
public interface CategoriaDao {

    int guardar(CategoriasVo categoriasVo) throws Exception;

    int actualizar(CategoriasVo categoriasVo) throws Exception;

    int borrar(CategoriasVo categoriasVo) throws Exception;

    int existe(CategoriasVo categoriasVo) throws Exception;

    CategoriasVo buscar(CategoriasVo categoriasVo) throws Exception;

    List<CategoriasVo> lista(CategoriasVo categoriasVo) throws Exception;

    List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception;
}
