package com.comunidad.simplecatproducto.departamento.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.comunidad.simplecatproducto.departamento.dao.DepartamentoDao;
import com.comunidad.simplecatproducto.departamento.dao.vo.DepartamentoVoTest;

//@Service
public class DepartamentoServiceImpl implements DepartamentoService {

    @Autowired
    private DepartamentoDao departamentoDao;

    @Override
    public int guardar(DepartamentoVoTest departamentoVo) throws Exception {
        return departamentoDao.guardar(departamentoVo);
    }

    @Override
    public int existe(DepartamentoVoTest departamentoVo) throws Exception {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public DepartamentoVoTest buscar(DepartamentoVoTest departamentoVo) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<DepartamentoVoTest> listDepartamentoVo(DepartamentoVoTest departamentoVo) throws Exception {
        return departamentoDao.listDepartamentoVo(departamentoVo);
    }

}
