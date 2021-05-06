/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.service;

import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;

/**
 *
 * @author Santiago
 */
public interface ProductoService {

    int guardar(ProductosVo productosVo) throws Exception;

    int borrar(ProductosVo productosVo) throws Exception;

    int existe(ProductosVo productosVo) throws Exception;

    ProductosVo buscar(ProductosVo productosVo) throws Exception;

    List<ProductosVo> lista(ProductosVo productosVo) throws Exception;

    List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception;
}
