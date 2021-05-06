/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.departamentos.model;

import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author Santiago
 */
public class DepartamentosComboboxModel extends AbstractListModel implements ComboBoxModel {

    private ItemComboboxModel itemComboboxModel = null;
//    private List<ItemComboboxModel> listItemComboboxModel = null;
    private Vector<ItemComboboxModel> listItemComboboxModel = null;
    private List<ItemComboboxModel> listItemCombobox = null;
    String selected = null;
    int index = 0;

    public DepartamentosComboboxModel(DepartamentoService departamentoService) throws Exception {
        System.out.println("com.comunidad.simplecatproducto.catalogos.departamentos.model.DepartamentosComboboxModel.<init>()");
        listItemCombobox = departamentoService.catalogo();
        itemComboboxModel = new ItemComboboxModel();
        if (listItemCombobox != null && listItemCombobox.size() > 0) {
            listItemComboboxModel = new Vector<>();
            listItemComboboxModel.add(new ItemComboboxModel(0, "Selecionar"));
            for (int x = 0; x < listItemCombobox.size(); x++) {
                itemComboboxModel = listItemCombobox.get(x);
                listItemComboboxModel.add(new ItemComboboxModel(itemComboboxModel.getId(), itemComboboxModel.getValue()));
            }
        } else {
            listItemComboboxModel = new Vector<>();
            listItemComboboxModel.add(new ItemComboboxModel(0, "Selecionar"));
        }
    }

    @Override
    public int getSize() {
        return listItemComboboxModel.size();
    }

    @Override
    public Object getElementAt(int index) {
        itemComboboxModel = listItemComboboxModel.get(index);
        return itemComboboxModel.getValue();
    }

    public ItemComboboxModel getElementItemComboboxModel() {
        return itemComboboxModel;
    }

    public ItemComboboxModel getElementItemComboboxModel(int key) {
        ItemComboboxModel objItemComboboxModel = null;
        for (int x = 0; x < listItemComboboxModel.size(); x++) {
            objItemComboboxModel = listItemComboboxModel.get(x);
            if (objItemComboboxModel.getId() == key) {
                break;
            }
        }
        return objItemComboboxModel;
    }

    public void setSelectedIndex(int anIndex) {
        index = 0;
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selected = (String) anItem;
    }

    @Override
    public Object getSelectedItem() {
        return itemComboboxModel.getValue();
    }

}
