package com.comunidad.simplecatproducto.catalogos.departamentos.service;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.DepartamentoDao;
import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.DepartamentosVo;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoDao departamentoDao;

    @Transactional
    @Override
    public int guardar(DepartamentosVo departamentoVo) throws Exception {
        int bandera = 0;
        if (departamentoDao.existe(departamentoVo) == 0) {
            bandera = departamentoDao.guardar(departamentoVo);
        } else {
            bandera = departamentoDao.actualizar(departamentoVo);
        }
        return bandera;
    }

    @Override
    public int borrar(DepartamentosVo departamentoVo) throws Exception {
        return departamentoDao.borrar(departamentoVo);
    }

    @Override
    public int existe(DepartamentosVo departamentoVo) throws Exception {
        return departamentoDao.existe(departamentoVo);
    }

    @Override
    public DepartamentosVo buscar(DepartamentosVo departamentoVo) throws Exception {
        return departamentoDao.buscar(departamentoVo);
    }

    @Override
    public List<DepartamentosVo> lista(DepartamentosVo departamentoVo) throws Exception {
        return departamentoDao.lista(departamentoVo);
    }

    public void setDepartamentoDao(DepartamentoDao departamentoDao) {
        this.departamentoDao = departamentoDao;
    }

    public DepartamentoDao getDepartamentoDao() {
        return departamentoDao;
    }

    @Override
    public List<ItemComboboxModel> catalogo() throws Exception {
        return departamentoDao.catalogo();
    }

}
