/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.model;

import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import com.comunidad.simplecatproducto.catalogos.subproductos.service.DetalleSubprodService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class DetalleSubprodTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Detalle", "Descripcion"
    };
    private List<DetalleSubprodVo> lista = null;
    private DetalleSubprodVo detalleSubprodVo = null;

    public DetalleSubprodTableModel() {
        lista = new ArrayList<DetalleSubprodVo>();
    }

    public DetalleSubprodTableModel(DetalleSubprodService detalleSubprodService, int idSubprod) throws Exception {
        detalleSubprodVo = new DetalleSubprodVo();
        detalleSubprodVo.setIdSubprod(idSubprod);
        lista = detalleSubprodService.lista(detalleSubprodVo);
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
    public boolean isCellEditable(int row, int col) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DetalleSubprodVo subVo = getDetalleSubprodVo(rowIndex);

        switch (columnIndex) {
            case 0:
                return subVo.getId();
            case 1:
                return subVo.getNombre();
            case 2:
                return subVo.getDescripcion();
            case 3:
                return subVo.getStatus();
            case 4:
                return subVo.getFechaUltActual();
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object value, int row, int column) {
        DetalleSubprodVo setDetalleSubprodVo = getDetalleSubprodVo(row);

        switch (column) {
            case 0:
                setDetalleSubprodVo.setId((int) value);
                break;
            case 1:
                setDetalleSubprodVo.setNombre((String) value);
                break;
            case 2:
                setDetalleSubprodVo.setDescripcion((String) value);
                break;
            case 3:
                setDetalleSubprodVo.setStatus((int) value);
                break;
            case 4:
                setDetalleSubprodVo.setFechaUltActual((Date) value);
                break;
        }

        fireTableCellUpdated(row, column);
    }

    public void limpiar() {
        lista = new ArrayList<DetalleSubprodVo>();
    }

    public DetalleSubprodVo getDetalleSubprodVo(int row) {
        return lista.get(row);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public List<DetalleSubprodVo> getAllDetalleSubprodVo() {
        return lista;
    }

    public void addDetalleSubprodVo(DetalleSubprodVo detalleSubprodVo) {
        insertDetalleSubprodVo(getRowCount(), detalleSubprodVo);
    }

    public void insertDetalleSubprodVo(int row, DetalleSubprodVo detalleSubprodVo) {
        lista.add(row, detalleSubprodVo);
        fireTableRowsInserted(row, row);
    }

    public void updateDetalleSubprodVo(int row, DetalleSubprodVo detalleSubprodVo) {
        lista.add(row, detalleSubprodVo);
        fireTableRowsUpdated(row, row);
    }

    public void removeDetalleSubprodVo(int row) {
        lista.remove(row);
        fireTableRowsDeleted(row, row);
    }
}
