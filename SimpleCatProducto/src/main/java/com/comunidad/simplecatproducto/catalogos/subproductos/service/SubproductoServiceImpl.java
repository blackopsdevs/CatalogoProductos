/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.service;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.DetalleSubprodDao;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.SubproductoDao;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.SubproductosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Santiago
 */
public class SubproductoServiceImpl implements SubproductoService {

    @Autowired
    private SubproductoDao subproductoDao;

    @Autowired
    private DetalleSubprodDao detalleSubprodDao;

    @Transactional
    @Override
    public int guardar(SubproductosVo subproductosVo) throws Exception {
        int bandera = 0;
        int sub = 0;
        int det = 0;
        if (subproductosVo != null) {
            sub = subproductoDao.guardar(subproductosVo);
            if (!subproductosVo.getDetalles().isEmpty()) {
                for (DetalleSubprodVo detalleSubprodVo : subproductosVo.getDetalles()) {
                    det = detalleSubprodDao.guardar(detalleSubprodVo);
                }
            }
        }
        if (sub != 0 && det != 0) {
            bandera = 1;
        }
        return bandera;
    }

    @Override
    public int existe(SubproductosVo subproductosVo) throws Exception {
        return subproductoDao.existe(subproductosVo);
    }

    @Override
    public SubproductosVo buscar(SubproductosVo subproductosVo) throws Exception {
        return subproductoDao.buscar(subproductosVo);
    }

    @Override
    public List<SubproductosVo> lista(SubproductosVo subproductosVo) throws Exception {
        return subproductoDao.lista(subproductosVo);
    }

    @Override
    public List<ItemComboboxModel> catalogo(int id, int estatus) throws Exception {
        return subproductoDao.catalogo(id, estatus);
    }

    public void setSubproductoDao(SubproductoDao subproductoDao) {
        this.subproductoDao = subproductoDao;
    }

    public void setDetalleSubprodDao(DetalleSubprodDao detalleSubprodDao) {
        this.detalleSubprodDao = detalleSubprodDao;
    }

}
