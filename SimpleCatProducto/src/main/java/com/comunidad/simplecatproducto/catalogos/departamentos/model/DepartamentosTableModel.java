/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.departamentos.model;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.DepartamentosVo;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class DepartamentosTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Nombre", "Descripcion"
    };

    private List<DepartamentosVo> listDepartamentoVo = null;
    private DepartamentosVo departamentoVo = null;

    public DepartamentosTableModel(DepartamentoService departamentoService) throws Exception {
        listDepartamentoVo = departamentoService.lista(departamentoVo);
    }

    @Override
    public int getRowCount() {
        return listDepartamentoVo.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DepartamentosVo depVo = getDepartamentoVo(rowIndex);

        switch (columnIndex) {
            case 0: return depVo.getId();
            case 1: return depVo.getNombre();
            case 2: return depVo.getDescripcion();
            default: return null;
        }
    }

    public DepartamentosVo getDepartamentoVo(int row) {
        return listDepartamentoVo.get(row);
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
