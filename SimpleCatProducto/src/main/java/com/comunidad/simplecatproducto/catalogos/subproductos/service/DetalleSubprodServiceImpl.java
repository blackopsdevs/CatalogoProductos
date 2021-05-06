/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.service;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.DetalleSubprodDao;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Santiago
 */
public class DetalleSubprodServiceImpl implements DetalleSubprodService {

    @Autowired
    private DetalleSubprodDao detalleSubprodDao;

    @Override
    public List<DetalleSubprodVo> lista(DetalleSubprodVo detalleSubprodVo) throws Exception {
        return detalleSubprodDao.lista(detalleSubprodVo);
    }

    public void setDetalleSubprodDao(DetalleSubprodDao detalleSubprodDao) {
        this.detalleSubprodDao = detalleSubprodDao;
    }

}
