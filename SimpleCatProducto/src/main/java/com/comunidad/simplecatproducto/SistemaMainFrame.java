/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto;

import com.comunidad.simplecatproducto.catalogos.categorias.vista.CategoriasInternalFrame;
import com.comunidad.simplecatproducto.catalogos.departamentos.vista.DepartamentosInternalFrame;
import com.comunidad.simplecatproducto.catalogos.productos.vista.ProductosInternalFrame;
import com.comunidad.simplecatproducto.catalogos.subproductos.vista.SubproductosInternalFrame;
import org.springframework.stereotype.Component;

/**
 *
 * @author Santiago
 */
@Component
public class SistemaMainFrame extends javax.swing.JFrame {

    /**
     * Creates new form SistemaMainFrame
     */
    public SistemaMainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jdpSistemaMain = new javax.swing.JDesktopPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuDepartamentos = new javax.swing.JMenuItem();
        menuCategoria = new javax.swing.JMenuItem();
        menuProducto = new javax.swing.JMenuItem();
        menuSubproducto = new javax.swing.JMenuItem();
        menuSalirSistema = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de administracion");
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jdpSistemaMain.setAutoscrolls(true);
        jdpSistemaMain.setMinimumSize(new java.awt.Dimension(930, 561));

        jMenu1.setText("File");

        menuDepartamentos.setText("Departamentos");
        menuDepartamentos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuDepartamentosActionPerformed(evt);
            }
        });
        jMenu1.add(menuDepartamentos);

        menuCategoria.setText("Categoria");
        menuCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCategoriaActionPerformed(evt);
            }
        });
        jMenu1.add(menuCategoria);

        menuProducto.setText("Producto");
        menuProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProductoActionPerformed(evt);
            }
        });
        jMenu1.add(menuProducto);

        menuSubproducto.setText("Subproducto");
        menuSubproducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSubproductoActionPerformed(evt);
            }
        });
        jMenu1.add(menuSubproducto);

        menuSalirSistema.setText("Salir");
        menuSalirSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuSalirSistemaActionPerformed(evt);
            }
        });
        jMenu1.add(menuSalirSistema);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpSistemaMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jdpSistemaMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void menuSalirSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSalirSistemaActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_menuSalirSistemaActionPerformed

    private void menuDepartamentosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuDepartamentosActionPerformed
        // TODO add your handling code here:
        if (jdpSistemaMain.getComponentCount() < 1) {
            DepartamentosInternalFrame depInternalFrame = new DepartamentosInternalFrame();
            depInternalFrame.setVisible(true);
            jdpSistemaMain.add(depInternalFrame);
        }
    }//GEN-LAST:event_menuDepartamentosActionPerformed

    private void menuCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCategoriaActionPerformed
        // TODO add your handling code here:
        if (jdpSistemaMain.getComponentCount() < 1) {
            CategoriasInternalFrame categoInternalFrame = new CategoriasInternalFrame();
            categoInternalFrame.setVisible(true);
            jdpSistemaMain.add(categoInternalFrame);
        }
    }//GEN-LAST:event_menuCategoriaActionPerformed

    private void menuProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProductoActionPerformed
        // TODO add your handling code here:
        if (jdpSistemaMain.getComponentCount() < 1) {
            ProductosInternalFrame prodInternalFrame = new ProductosInternalFrame();
            prodInternalFrame.setVisible(true);
            jdpSistemaMain.add(prodInternalFrame);
        }
    }//GEN-LAST:event_menuProductoActionPerformed

    private void menuSubproductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuSubproductoActionPerformed
        // TODO add your handling code here:
        if (jdpSistemaMain.getComponentCount() < 1) {
            SubproductosInternalFrame subprodInternalFrame = new SubproductosInternalFrame();
            subprodInternalFrame.setVisible(true);
            jdpSistemaMain.add(subprodInternalFrame);
        }
    }//GEN-LAST:event_menuSubproductoActionPerformed

    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String args[]) {

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(SistemaMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SistemaMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SistemaMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SistemaMainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SistemaMainFrame().setVisible(true);
            }
        });
    }*/

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JDesktopPane jdpSistemaMain;
    private javax.swing.JMenuItem menuCategoria;
    private javax.swing.JMenuItem menuDepartamentos;
    private javax.swing.JMenuItem menuProducto;
    private javax.swing.JMenuItem menuSalirSistema;
    private javax.swing.JMenuItem menuSubproducto;
    // End of variables declaration//GEN-END:variables
}
