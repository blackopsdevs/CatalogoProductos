/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Santiago
 */
public class LookAndFeelDemo {
    public static void main(String[] args) {
        new LookAndFeelDemo();
    }

    public LookAndFeelDemo() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }

                JFrame frame = new JFrame("Testing");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new TestPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public class TestPane extends JPanel {

        public TestPane() {
            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridwidth = GridBagConstraints.REMAINDER;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.insets = new Insets(2, 2, 2, 2);

            add(new JLabel("I have a bad feeling about this"), gbc);
            add(new JTextField("When this blows up in your face, don't blame me"), gbc);

            UIManager.LookAndFeelInfo[] lafs = UIManager.getInstalledLookAndFeels();
            DefaultComboBoxModel model = new DefaultComboBoxModel(lafs);
            JComboBox cb = new JComboBox(model);
            cb.setRenderer(new LookAndFeelInfoListCellRenderer());
            add(cb, gbc);

            String name = UIManager.getLookAndFeel().getName();
            for (int index = 0; index < model.getSize(); index++) {
                UIManager.LookAndFeelInfo info = (UIManager.LookAndFeelInfo) model.getElementAt(index);
                if (info.getName().equals(name)) {
                    model.setSelectedItem(info);
                    break;
                }
            }

            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    UIManager.LookAndFeelInfo info = (UIManager.LookAndFeelInfo) cb.getSelectedItem();
                    String className = info.getClassName();
                    try {
                        UIManager.setLookAndFeel(className);
                        SwingUtilities.updateComponentTreeUI(SwingUtilities.windowForComponent(TestPane.this));
                    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                        ex.printStackTrace();
                    }
                }
            });
        }

        public class LookAndFeelInfoListCellRenderer extends DefaultListCellRenderer {

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof UIManager.LookAndFeelInfo) {
                    UIManager.LookAndFeelInfo info = (UIManager.LookAndFeelInfo) value;
                    value = info.getName();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }

        }

    }
}
