/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.model;

import com.comunidad.simplecatproducto.catalogos.categorias.dao.vo.CategoriasVo;
import com.comunidad.simplecatproducto.catalogos.categorias.service.CategoriaService;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Santiago
 */
public class CategoriasTableModel extends AbstractTableModel {

    private String[] columnNames = {
        "ID", "Categoria", "Descripcion"
    };
    private List<CategoriasVo> lista = null;
    private CategoriasVo categoriasVo = null;

    public CategoriasTableModel(CategoriaService categoriaService, int idDep) throws Exception {
        categoriasVo = new CategoriasVo();
        categoriasVo.setIdDepart(idDep);
        lista = categoriaService.lista(categoriasVo);
    }

    public CategoriasTableModel() {
        limpiar();
    }

    public void limpiar() {
        lista = new ArrayList<CategoriasVo>();
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
        CategoriasVo depVo = getCategoriasVo(rowIndex);

        switch (columnIndex) {
            case 0:
                return depVo.getId();
            case 1:
                return depVo.getNombre();
            case 2:
                return depVo.getDescripcion();
            default:
                return null;
        }
    }

    public CategoriasVo getCategoriasVo(int row) {
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
