/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.departamento.model;

import com.comunidad.simplecatproducto.departamento.dao.vo.DepartamentoVoTest;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataListener;

/**
 *
 * @author Santiago
 */
public class DepartamentoComboboxModel implements ComboBoxModel {

    private List<DepartamentoVoTest> listDepartamentoVo;

    private DepartamentoVoTest departamentoVo;

    public DepartamentoComboboxModel() {
        listDepartamentoVo = new ArrayList<DepartamentoVoTest>();
    }

    @Override
    public void setSelectedItem(Object anItem) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getSelectedItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSize() {
        return listDepartamentoVo.size();
    }

    @Override
    public Object getElementAt(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
