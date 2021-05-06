/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.dao;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import java.util.List;

/**
 *
 * @author Santiago
 */
public interface DetalleSubprodDao {

    int guardar(DetalleSubprodVo detalleSubprodVo) throws Exception;

    int actualizar(DetalleSubprodVo detalleSubprodVo) throws Exception;

    int existe(DetalleSubprodVo detalleSubprodVo) throws Exception;

    DetalleSubprodVo buscar(DetalleSubprodVo detalleSubprodVo) throws Exception;

    List<DetalleSubprodVo> lista(DetalleSubprodVo detalleSubprodVo) throws Exception;

    List<DetalleSubprodVo> catalogo(int id, int estatus) throws Exception;
}
