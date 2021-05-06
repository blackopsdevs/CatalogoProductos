/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.departamentos.model;

import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

/**
 *
 * @author Santiago
 */
public class ItemRenderer extends BasicComboBoxRenderer {

    public Component getListCellRendererComponent(JList list, Object value,
            int index, boolean isSelected, boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected,
                cellHasFocus);

        ItemComboboxModel item = (ItemComboboxModel) value;
        setText(item.getValue().toUpperCase());

        return this;
    }
}
