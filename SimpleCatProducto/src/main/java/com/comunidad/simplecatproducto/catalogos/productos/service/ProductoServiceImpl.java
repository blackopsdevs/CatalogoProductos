/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.service;

import com.comunidad.simplecatproducto.catalogos.productos.dao.ProductoDao;
import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santiago
 */
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoDao productoDao;

    @Transactional
    @Override
    public int guardar(ProductosVo productosVo) throws Exception {
        int bandera = 0;
        if (productoDao.existe(productosVo) == 0) {
            bandera = productoDao.guardar(productosVo);
        } else {
            bandera = productoDao.actualizar(productosVo);
        }
        return bandera;
    }

    @Override
    public int existe(ProductosVo productosVo) throws Exception {
        return productoDao.existe(productosVo);
    }

    @Override
    public ProductosVo buscar(ProductosVo productosVo) throws Exception {
        return productoDao.buscar(productosVo);
    }

    @Override
    public List<ProductosVo> lista(ProductosVo productosVo) throws Exception {
        return productoDao.lista(productosVo);
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        return productoDao.catalogo(id, estatus);
    }

    public void setProductoDao(ProductoDao productoDao) {
        this.productoDao = productoDao;
    }

    @Override
    public int borrar(ProductosVo productosVo) throws Exception {
        return productoDao.borrar(productosVo);
    }

}
