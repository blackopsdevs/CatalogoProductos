/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.model;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.SubproductosVo;
import com.comunidad.simplecatproducto.catalogos.subproductos.service.SubproductoService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class SubproductosTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Subproducto", "Descripcion"
    };
    private List<SubproductosVo> lista = null;
    private SubproductosVo subproductosVo = null;

    public SubproductosTableModel(SubproductoService subproductoService, int idProd) throws Exception {
        subproductosVo = new SubproductosVo();
        subproductosVo.setIdProd(idProd);
        lista = subproductoService.lista(subproductosVo);
    }

    public SubproductosTableModel() {
        lista = new ArrayList<SubproductosVo>();
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SubproductosVo subVo = getSubproductosVo(rowIndex);

        switch (columnIndex) {
            case 0:
                return subVo.getId();
            case 1:
                return subVo.getNombre();
            case 2:
                return subVo.getDescripcion();
            case 3:
                return subVo.getPath();
            case 4:
                return subVo.getStatus();
            case 5:
                return subVo.getFechaUltActual();
            default:
                return null;
        }
    }

    public void limpiar() {
        lista = new ArrayList<SubproductosVo>();
    }

    public SubproductosVo getSubproductosVo(int row) {
        return lista.get(row);
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
