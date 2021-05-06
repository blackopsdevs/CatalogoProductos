/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.model;

import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
import com.comunidad.simplecatproducto.catalogos.productos.service.ProductoService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class ProductosTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Producto", "Descripcion"
    };
    private List<ProductosVo> lista = null;
    private ProductosVo productosVo = null;

    public ProductosTableModel(ProductoService productoService, int idCatego) throws Exception {
        productosVo = new ProductosVo();
        productosVo.setIdCatego(idCatego);
        lista = productoService.lista(productosVo);
    }

    public ProductosTableModel() {
        limpiar();
    }

    public void limpiar() {
        lista = new ArrayList<ProductosVo>();
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
        ProductosVo prodVo = getProductosVo(rowIndex);

        switch (columnIndex) {
            case 0:
                return prodVo.getId();
            case 1:
                return prodVo.getNombre();
            case 2:
                return prodVo.getDescripcion();
            case 3:
                return prodVo.getPath();
            case 4:
                return prodVo.getStatus();
            case 5:
                return prodVo.getFechaUltActual();
            default:
                return null;
        }
    }

    public ProductosVo getProductosVo(int row) {
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
