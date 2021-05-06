/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.service;

import com.comunidad.simplecatproducto.catalogos.categorias.dao.CategoriaDao;
import com.comunidad.simplecatproducto.catalogos.categorias.dao.vo.CategoriasVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santiago
 */
public class CategoriaServiceImpl implements CategoriaService {

    @Autowired
    private CategoriaDao categoriaDao;

    @Transactional
    @Override
    public int guardar(CategoriasVo categoriasVo) throws Exception {
        int bandera = 0;
        if (categoriaDao.existe(categoriasVo) == 0) {
            bandera = categoriaDao.guardar(categoriasVo);
        } else {
            bandera = categoriaDao.actualizar(categoriasVo);
        }
        return bandera;
    }

    @Override
    public int existe(CategoriasVo categoriasVo) throws Exception {
        return categoriaDao.existe(categoriasVo);
    }

    @Override
    public CategoriasVo buscar(CategoriasVo categoriasVo) throws Exception {
        return categoriaDao.buscar(categoriasVo);
    }

    @Override
    public List<CategoriasVo> lista(CategoriasVo categoriasVo) throws Exception {
        return categoriaDao.lista(categoriasVo);
    }

    public void setCategoriaDao(CategoriaDao categoriaDao) {
        this.categoriaDao = categoriaDao;
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        return categoriaDao.catalogo(id, estatus);
    }

    @Override
    public int borrar(CategoriasVo categoriasVo) throws Exception {
        return categoriaDao.borrar(categoriasVo);
    }

}
