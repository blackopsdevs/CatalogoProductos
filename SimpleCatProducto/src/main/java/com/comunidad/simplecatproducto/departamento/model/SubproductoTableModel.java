/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.departamento.model;

import com.comunidad.simplecatproducto.departamento.dao.vo.SubproductoVoTest;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class SubproductoTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Nombre", "Descripcion", "Estatus", "Fecha"
    };

    private List<SubproductoVoTest> listSubproductoVo;

    private SubproductoVoTest subproductoVo;

    public SubproductoTableModel() {
        listSubproductoVo = new ArrayList<SubproductoVoTest>();
    }

    @Override
    public int getRowCount() {
        return listSubproductoVo.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
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
