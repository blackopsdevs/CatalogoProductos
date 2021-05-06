/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.SubproductosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;

/**
 *
 * @author Santiago
 */
public interface SubproductoDao {

    int guardar(SubproductosVo subproductosVo) throws Exception;

    int actualizar(SubproductosVo subproductosVo) throws Exception;

    int existe(SubproductosVo subproductosVo) throws Exception;

    SubproductosVo buscar(SubproductosVo subproductosVo) throws Exception;

    List<SubproductosVo> lista(SubproductosVo subproductosVo) throws Exception;

    List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception;
}
