/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.categorias.vista;

import com.comunidad.simplecatproducto.catalogos.categorias.dao.vo.CategoriasVo;
import com.comunidad.simplecatproducto.catalogos.categorias.model.CategoriasTableModel;
import com.comunidad.simplecatproducto.catalogos.categorias.service.CategoriaService;
import com.comunidad.simplecatproducto.catalogos.departamentos.model.DepartamentosComboboxModel;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.common.ImageFilter;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import com.comunidad.simplecatproducto.common.Utils;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Santiago
 */
public class CategoriasInternalFrame extends javax.swing.JInternalFrame {

    private DepartamentosComboboxModel departamentosComboboxModel = null;
    private CategoriasTableModel categoriasTableModel = null;
    private DepartamentoService departamentoService = null;
    private CategoriaService categoriaService = null;

    private JFileChooser chooser = null;
    public String fileID = null;
    private FileInputStream fisfoto = null;
    private int longitudBytes = 0;
    public static ApplicationContext ctx = null;
    private CategoriasVo categoriasVo = null;
    int rowIndex = 0;

    private ItemComboboxModel itemComboboxModel = null;
    private DefaultComboBoxModel modelo = null;

    /**
     * Creates new form CategoriasInternalFrame
     */
    public CategoriasInternalFrame() {
        initComponents();
        initConfig();
        loadDataDepartamentos();
    }

    public void initConfig() {
        try {
            String[] config = new String[2];
            config[0] = "applicationContext.xml";
            config[1] = "classpath:com/comunidad/simplecatproducto/**/*Dao.xml";
            ctx = new ClassPathXmlApplicationContext(config);
            departamentoService = (DepartamentoService) ctx.getBean("DepartamentoService");
            categoriaService = (CategoriaService) ctx.getBean("CategoriaService");
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void refreshTable(int idDep) {
        try {
            if (idDep != 0) {
                categoriasTableModel = new CategoriasTableModel(categoriaService, idDep);
            } else {
                categoriasTableModel = new CategoriasTableModel();
            }
            tblCategorias.setModel(categoriasTableModel);
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void limpiarCampos() {
        lblImagenCatego.setIcon(null);
        txtIdCatego.setText(Utils.idCadena());
        txtNombreCatego.setText("");
        txtNombreCatego.setEditable(true);
        txtPathImgProd.setText("");
        txtDescCatego.setText("");
        txtDescCatego.setEditable(true);
        //btnBorrarCatego.setEnabled(true);
        //btnCerrarCatego.setEnabled(true);
        btnExaminarCatego.setEnabled(true);
        btnGuardarCatego.setEnabled(true);

        radActivoCat.setSelected(false);
        radInactivoCat.setSelected(false);
        cmbDepartamentos.setEnabled(true);
        //cmbDepartamentos.setSelectedIndex(0);
    }

    public void loadDataDepartamentos() {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modelo = new DefaultComboBoxModel(values);
            List<ItemComboboxModel> listaValores = departamentoService.catalogo();
            if (listaValores != null && listaValores.size() > 0) {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                for (int x = 0; x < listaValores.size(); x++) {
                    itemComboboxModel = listaValores.get(x);
                    values.add(itemComboboxModel);
                }
            }
            cmbDepartamentos.setModel(modelo);
            cmbDepartamentos.setSelectedIndex(0);
            refreshTable(0);

//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public boolean validarCampos() {
        boolean bandera = true;
        String id = txtIdCatego.getText();
        String nombre = txtNombreCatego.getText();
        String descripcion = txtDescCatego.getText();
        String pathImg = txtPathImgProd.getText();
        Icon icono = lblImagenCatego.getIcon();
        int depaSelect = cmbDepartamentos.getSelectedIndex();
        boolean activo = radActivoCat.isSelected();
        boolean inactivo = radInactivoCat.isSelected();

        if (depaSelect == 0) {
            lblDepart.setBackground(Color.red);
            lblDepart.setOpaque(true);
            bandera = false;
        } else if (id.isEmpty()) {
            lblIdCat.setBackground(Color.red);
            lblIdCat.setOpaque(true);
            bandera = false;
        } else if (nombre.isEmpty()) {
            lblNomCat.setBackground(Color.red);
            lblNomCat.setOpaque(true);
            bandera = false;
        } else if (pathImg.isEmpty()) {
            lblImgCat.setBackground(Color.red);
            lblImgCat.setOpaque(true);
            bandera = false;
        } else if (descripcion.isEmpty()) {
            lblDescCat.setBackground(Color.red);
            lblDescCat.setOpaque(true);
            bandera = false;
        } else if (!activo && !inactivo) {
            lblEstCat.setBackground(Color.red);
            lblEstCat.setOpaque(true);
            bandera = false;
        }

        if (depaSelect != 0) {
            lblDepart.setBackground(Color.gray);
            lblDepart.setOpaque(false);
        }
        if (!id.isEmpty()) {
            lblIdCat.setBackground(Color.gray);
            lblIdCat.setOpaque(false);
        }
        if (!nombre.isEmpty()) {
            lblNomCat.setBackground(Color.gray);
            lblNomCat.setOpaque(false);
        }
        if (!pathImg.isEmpty()) {
            lblImgCat.setBackground(Color.gray);
            lblImgCat.setOpaque(false);
        }
        if (!descripcion.isEmpty()) {
            lblDescCat.setBackground(Color.gray);
            lblDescCat.setOpaque(false);
        }
        if (activo || inactivo) {
            lblEstCat.setBackground(Color.gray);
            lblEstCat.setOpaque(false);
        }
        return bandera;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblDepart = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        lblIdCat = new javax.swing.JLabel();
        txtIdCatego = new javax.swing.JTextField();
        lblNomCat = new javax.swing.JLabel();
        txtNombreCatego = new javax.swing.JTextField();
        lblImgCat = new javax.swing.JLabel();
        txtPathImgProd = new javax.swing.JTextField();
        btnExaminarCatego = new javax.swing.JButton();
        lblImagenCatego = new javax.swing.JLabel();
        lblDescCat = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescCatego = new javax.swing.JTextArea();
        btnCerrarCatego = new javax.swing.JButton();
        btnGuardarCatego = new javax.swing.JButton();
        lblEstCat = new javax.swing.JLabel();
        radActivoCat = new javax.swing.JRadioButton();
        radInactivoCat = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btnNuevoCatego = new javax.swing.JButton();
        btnBorrarCatego = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblCategorias = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Catalogo categorias");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(332, 503));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Categorias");

        lblDepart.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDepart.setText("* Departamento:");

        cmbDepartamentos.setFont(new java.awt.Font("Arial", 0, 14));
        cmbDepartamentos.setEnabled(false);
        cmbDepartamentos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDepartamentosItemStateChanged(evt);
            }
        });

        lblIdCat.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblIdCat.setText("* ID:");

        txtIdCatego.setEditable(false);
        txtIdCatego.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIdCatego.setActionCommand("<Not Set>");

        lblNomCat.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNomCat.setText("* Nombre:");

        txtNombreCatego.setEditable(false);
        txtNombreCatego.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblImgCat.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblImgCat.setText("* Imagen:");

        txtPathImgProd.setEditable(false);
        txtPathImgProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnExaminarCatego.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnExaminarCatego.setText("Examinar...");
        btnExaminarCatego.setEnabled(false);
        btnExaminarCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarCategoActionPerformed(evt);
            }
        });

        lblDescCat.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDescCat.setText("* Descripción:");

        txtDescCatego.setEditable(false);
        txtDescCatego.setColumns(20);
        txtDescCatego.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescCatego.setRows(5);
        jScrollPane1.setViewportView(txtDescCatego);

        btnCerrarCatego.setText("Cerrar");
        btnCerrarCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarCategoActionPerformed(evt);
            }
        });

        btnGuardarCatego.setText("Guardar");
        btnGuardarCatego.setEnabled(false);
        btnGuardarCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarCategoActionPerformed(evt);
            }
        });

        lblEstCat.setText("* Estatus:");

        radActivoCat.setText("Activo");
        radActivoCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radActivoCatMouseClicked(evt);
            }
        });

        radInactivoCat.setText("Inactivo");
        radInactivoCat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radInactivoCatMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenCatego, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExaminarCatego, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDepart)
                            .addComponent(lblIdCat)
                            .addComponent(lblNomCat)
                            .addComponent(lblImgCat))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPathImgProd)
                            .addComponent(txtNombreCatego)
                            .addComponent(txtIdCatego)
                            .addComponent(cmbDepartamentos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCerrarCatego, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarCatego, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblDescCat)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblEstCat)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(radActivoCat, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(radInactivoCat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDepart)
                    .addComponent(cmbDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIdCat)
                    .addComponent(txtIdCatego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNomCat)
                    .addComponent(txtNombreCatego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblImgCat)
                    .addComponent(txtPathImgProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExaminarCatego)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagenCatego, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblDescCat)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstCat)
                    .addComponent(radActivoCat)
                    .addComponent(radInactivoCat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarCatego)
                    .addComponent(btnCerrarCatego))
                .addContainerGap())
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(613, 457));

        btnNuevoCatego.setText("Nuevo");
        btnNuevoCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoCategoActionPerformed(evt);
            }
        });

        btnBorrarCatego.setText("Borrar");
        btnBorrarCatego.setEnabled(false);
        btnBorrarCatego.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarCategoActionPerformed(evt);
            }
        });

        tblCategorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblCategorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCategoriasMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblCategorias);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevoCatego)
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrarCatego)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoCatego)
                    .addComponent(btnBorrarCatego))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 608, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarCategoActionPerformed
        // TODO add your handling code here:
        try {
            this.setClosed(true);
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnCerrarCategoActionPerformed

    private void btnNuevoCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoCategoActionPerformed
        // TODO add your handling code here:
        try {
            limpiarCampos();
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnNuevoCategoActionPerformed

    private void btnExaminarCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarCategoActionPerformed
        // TODO add your handling code here:
        chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        chooser.setDialogTitle("Select an image");
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG, PNG y JPEG", "jpg", "png", "jpeg");
        chooser.addChoosableFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);

        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                if (new ImageFilter().accept(chooser.getSelectedFile())) {

                    fisfoto = new FileInputStream(chooser.getSelectedFile());
                    this.longitudBytes = (int) chooser.getSelectedFile().length();
                    Image icono = ImageIO.read(chooser.getSelectedFile()).getScaledInstance(320, 118, Image.SCALE_DEFAULT);
                    lblImagenCatego.setIcon(new ImageIcon(icono));

                    File file = chooser.getSelectedFile();
                    fileID = file.getAbsolutePath();
                    txtPathImgProd.setText(fileID);
                }
            } else {
                System.out.println("No file choosen!");
            }

        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnExaminarCategoActionPerformed

    private void btnGuardarCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarCategoActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            int est = 0;
            if (validarCampos()) {
                categoriasVo = new CategoriasVo();
                itemComboboxModel = (ItemComboboxModel) modelo.getSelectedItem();
                categoriasVo.setId(Integer.parseInt(txtIdCatego.getText()));
                categoriasVo.setIdDepart(itemComboboxModel.getId());
                categoriasVo.setNombre(txtNombreCatego.getText());
                categoriasVo.setDescripcion(txtDescCatego.getText());
                categoriasVo.setFoto(fisfoto);
                categoriasVo.setPath(txtPathImgProd.getText());
                if (radActivoCat.isSelected()) {
                    est = 1;
                } else if (radInactivoCat.isSelected()) {
                    est = 0;
                }
                categoriasVo.setStatus(est);
                System.out.println("fechaActual-> " + DateUtils.fechaActual());
                categoriasVo.setFechaUltActual(DateUtils.fechaActual());
                categoriasVo.setFechaExpiracion(DateUtils.fechaActual());
                categoriasVo.setFechaAlta(DateUtils.fechaActual());
                categoriasVo.setUserUltActual("TEST");
                categoriasVo.setBorrado(0);
                resp = categoriaService.guardar(categoriasVo);
                if (resp == 1) {
                    JOptionPane.showMessageDialog(null, "El registro de guardo correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un erro al guardar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                limpiarCampos();
                refreshTable(itemComboboxModel.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnGuardarCategoActionPerformed

    private void tblCategoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCategoriasMouseClicked
        // TODO add your handling code here:
        try {
            limpiarCampos();
            btnBorrarCatego.setEnabled(true);
            categoriasVo = new CategoriasVo();
            JTable target = (JTable) evt.getSource();
            rowIndex = target.getSelectedRow();
            if (evt.getClickCount() == 1) {
                categoriasVo = categoriasTableModel.getCategoriasVo(rowIndex);
                txtIdCatego.setText(String.valueOf(categoriasVo.getId()));
                txtNombreCatego.setText(categoriasVo.getNombre());
                txtPathImgProd.setText(categoriasVo.getPath());
                txtDescCatego.setText(categoriasVo.getDescripcion());

                if (categoriasVo.getStatus() == 1) {
                    radActivoCat.setSelected(true);
                    radInactivoCat.setSelected(false);
                } else if (categoriasVo.getStatus() == 0) {
                    radInactivoCat.setSelected(true);
                    radActivoCat.setSelected(false);
                }
                //Image icono = ImageIO.read(new File(categoriasVo.getPath())).getScaledInstance(320, 118, Image.SCALE_DEFAULT);
                //lblImagenCatego.setIcon(new ImageIcon(icono));
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_tblCategoriasMouseClicked

    private void cmbDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDepartamentosItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                itemComboboxModel = (ItemComboboxModel) modelo.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    refreshTable(itemComboboxModel.getId());
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cmbDepartamentosItemStateChanged

    private void radActivoCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radActivoCatMouseClicked
        // TODO add your handling code here:
        if (radActivoCat.isSelected()) {
            radInactivoCat.setSelected(false);
        }
    }//GEN-LAST:event_radActivoCatMouseClicked

    private void radInactivoCatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radInactivoCatMouseClicked
        // TODO add your handling code here:
        if (radInactivoCat.isSelected()) {
            radActivoCat.setSelected(false);
        }
    }//GEN-LAST:event_radInactivoCatMouseClicked

    private void btnBorrarCategoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarCategoActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            categoriasVo.setBorrado(1);
            resp = categoriaService.borrar(categoriasVo);
            if (resp == 1) {
                JOptionPane.showMessageDialog(null, "El registro de borro correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un erro al borrar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            refreshTable(itemComboboxModel.getId());
            limpiarCampos();
            btnBorrarCatego.setEnabled(false);
        } catch (Exception e) {
            Logger.getLogger(CategoriasInternalFrame.class.getName()).log(Level.SEVERE, null, e);
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnBorrarCategoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarCatego;
    private javax.swing.JButton btnCerrarCatego;
    private javax.swing.JButton btnExaminarCatego;
    private javax.swing.JButton btnGuardarCatego;
    private javax.swing.JButton btnNuevoCatego;
    private javax.swing.JComboBox<String> cmbDepartamentos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDepart;
    private javax.swing.JLabel lblDescCat;
    private javax.swing.JLabel lblEstCat;
    private javax.swing.JLabel lblIdCat;
    private javax.swing.JLabel lblImagenCatego;
    private javax.swing.JLabel lblImgCat;
    private javax.swing.JLabel lblNomCat;
    private javax.swing.JRadioButton radActivoCat;
    private javax.swing.JRadioButton radInactivoCat;
    private javax.swing.JTable tblCategorias;
    private javax.swing.JTextArea txtDescCatego;
    private javax.swing.JTextField txtIdCatego;
    private javax.swing.JTextField txtNombreCatego;
    private javax.swing.JTextField txtPathImgProd;
    // End of variables declaration//GEN-END:variables
}
