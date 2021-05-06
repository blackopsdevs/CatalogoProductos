/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.departamentos.vista;

import com.comunidad.simplecatproducto.catalogos.departamentos.dao.vo.DepartamentosVo;
import com.comunidad.simplecatproducto.catalogos.departamentos.model.DepartamentosTableModel;
import com.comunidad.simplecatproducto.common.ImageFilter;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import com.comunidad.simplecatproducto.common.DateUtils;
import com.comunidad.simplecatproducto.common.Utils;
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Santiago
 */
public class DepartamentosInternalFrame extends javax.swing.JInternalFrame {

    private DepartamentosTableModel departamentosTableModel = null;
    private DepartamentoService departamentoService = null;
    private JFileChooser chooser = null;
    public String fileID;
    private FileInputStream fisfoto;
    private int longitudBytes;
    public static ApplicationContext ctx;
    private DepartamentosVo departamentoVo = null;
    int rowIndex = -1;

    /**
     * Creates new form DepartamentosInternalFrame
     */
    public DepartamentosInternalFrame() {
        initComponents();
        initConfig();
        refreshTable();
    }

    public void initConfig() {
        try {
            String[] config = new String[2];
            config[0] = "applicationContext.xml";
            config[1] = "classpath:com/comunidad/simplecatproducto/**/*Dao.xml";
            ctx = new ClassPathXmlApplicationContext(config);
            departamentoService = (DepartamentoService) ctx.getBean("DepartamentoService");
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void refreshTable() {
        try {
            departamentosTableModel = new DepartamentosTableModel(departamentoService);
            tblDepartamentos.setModel(departamentosTableModel);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        lblImagenDepartamento.setIcon(null);
        txtIdDepart.setText(Utils.idCadena());
        txtDescDepart.setText("");
        txtDescDepart.setEditable(true);
        txtNomDepart.setText("");
        txtNomDepart.setEditable(true);
        txtPathImg.setText("");
        btnExaminarImgDep.setEnabled(true);
        btnGuardarDep.setEnabled(true);

        radActivoDep.setSelected(false);
        radInactivoDep.setSelected(false);
    }

    public boolean validarCampos() {
        boolean bandera = true;
        String id = txtIdDepart.getText();
        String nombre = txtNomDepart.getText();
        String descripcion = txtDescDepart.getText();
        String pathImg = txtPathImg.getText();
        Icon icono = lblImagenDepartamento.getIcon();
        boolean activo = radActivoDep.isSelected();
        boolean inactivo = radInactivoDep.isSelected();

        if (id.isEmpty()) {
            lblIdDep.setBackground(Color.red);
            lblIdDep.setOpaque(true);
            bandera = false;
        } else if (nombre.isEmpty()) {
            lblNomDep.setBackground(Color.red);
            lblNomDep.setOpaque(true);
            bandera = false;
        } else if (pathImg.isEmpty()) {
            lblImgDep.setBackground(Color.red);
            lblImgDep.setOpaque(true);
            bandera = false;
        } else if (descripcion.isEmpty()) {
            lblDescDep.setBackground(Color.red);
            lblDescDep.setOpaque(true);
            bandera = false;
        } else if (!activo && !inactivo) {
            lblEstDep.setBackground(Color.red);
            lblEstDep.setOpaque(true);
            bandera = false;
        }

        if (!id.isEmpty()) {
            lblIdDep.setBackground(Color.gray);
            lblIdDep.setOpaque(false);
        }
        if (!nombre.isEmpty()) {
            lblNomDep.setBackground(Color.gray);
            lblNomDep.setOpaque(false);
        }
        if (!pathImg.isEmpty()) {
            lblImgDep.setBackground(Color.gray);
            lblImgDep.setOpaque(false);
        }
        if (!descripcion.isEmpty()) {
            lblDescDep.setBackground(Color.gray);
            lblDescDep.setOpaque(false);
        }
        if (activo || inactivo) {
            lblEstDep.setBackground(Color.gray);
            lblEstDep.setOpaque(false);
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
        lblIdDep = new javax.swing.JLabel();
        txtIdDepart = new javax.swing.JTextField();
        lblNomDep = new javax.swing.JLabel();
        txtNomDepart = new javax.swing.JTextField();
        lblDescDep = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescDepart = new javax.swing.JTextArea();
        lblImgDep = new javax.swing.JLabel();
        btnExaminarImgDep = new javax.swing.JButton();
        lblImagenDepartamento = new javax.swing.JLabel();
        btnGuardarDep = new javax.swing.JButton();
        btnCerrarDep = new javax.swing.JButton();
        txtPathImg = new javax.swing.JTextField();
        radActivoDep = new javax.swing.JRadioButton();
        radInactivoDep = new javax.swing.JRadioButton();
        lblEstDep = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        btnNuevoDep = new javax.swing.JButton();
        btnBorrarDep = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDepartamentos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Mantenimiento del Catalogo Departamentos");
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Departamento");

        lblIdDep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblIdDep.setText("* ID:");

        txtIdDepart.setEditable(false);
        txtIdDepart.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblNomDep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNomDep.setText("* Nombre:");

        txtNomDepart.setEditable(false);
        txtNomDepart.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblDescDep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDescDep.setText("* Descripción:");

        txtDescDepart.setEditable(false);
        txtDescDepart.setColumns(20);
        txtDescDepart.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescDepart.setRows(5);
        jScrollPane1.setViewportView(txtDescDepart);

        lblImgDep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblImgDep.setText("* Imagen:");

        btnExaminarImgDep.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnExaminarImgDep.setText("Examinar...");
        btnExaminarImgDep.setEnabled(false);
        btnExaminarImgDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarImgDepActionPerformed(evt);
            }
        });

        lblImagenDepartamento.setBorder(javax.swing.BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        btnGuardarDep.setText("Guardar");
        btnGuardarDep.setEnabled(false);
        btnGuardarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarDepActionPerformed(evt);
            }
        });

        btnCerrarDep.setText("Cerrar");
        btnCerrarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarDepActionPerformed(evt);
            }
        });

        txtPathImg.setEditable(false);
        txtPathImg.setMaximumSize(new java.awt.Dimension(80, 26));
        txtPathImg.setScrollOffset(50);

        radActivoDep.setText("Activo");
        radActivoDep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radActivoDepMouseClicked(evt);
            }
        });

        radInactivoDep.setText("Inactivo");
        radInactivoDep.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                radInactivoDepMouseClicked(evt);
            }
        });

        lblEstDep.setText("* Estatus:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagenDepartamento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(btnExaminarImgDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCerrarDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarDep, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNomDep)
                            .addComponent(lblImgDep))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPathImg, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNomDepart)))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(lblIdDep)
                            .addGap(55, 55, 55)
                            .addComponent(txtIdDepart))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblDescDep)
                                    .addGap(80, 80, 80))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblEstDep)
                                    .addGap(18, 18, 18)
                                    .addComponent(radActivoDep, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGap(18, 18, 18)))
                            .addComponent(radInactivoDep, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 0, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdDep)
                    .addComponent(txtIdDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNomDepart, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNomDep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblImgDep)
                    .addComponent(txtPathImg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExaminarImgDep)
                .addGap(8, 8, 8)
                .addComponent(lblImagenDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescDep)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstDep)
                    .addComponent(radActivoDep)
                    .addComponent(radInactivoDep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarDep)
                    .addComponent(btnCerrarDep))
                .addContainerGap())
        );

        btnNuevoDep.setText("Nuevo");
        btnNuevoDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoDepActionPerformed(evt);
            }
        });

        btnBorrarDep.setText("Borrar");
        btnBorrarDep.setEnabled(false);
        btnBorrarDep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarDepActionPerformed(evt);
            }
        });

        tblDepartamentos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDepartamentos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDepartamentosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblDepartamentos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevoDep)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBorrarDep)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoDep)
                    .addComponent(btnBorrarDep))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnExaminarImgDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarImgDepActionPerformed
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
                    lblImagenDepartamento.setIcon(new ImageIcon(icono));

                    File file = chooser.getSelectedFile();
                    fileID = file.getAbsolutePath();
                    txtPathImg.setText(fileID);
                }
            } else {
                System.out.println("No file choosen!");
            }

        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnExaminarImgDepActionPerformed

    private void btnCerrarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarDepActionPerformed
        // TODO add your handling code here:
        try {
            this.setClosed(true);
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnCerrarDepActionPerformed

    private void btnNuevoDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoDepActionPerformed
        // TODO add your handling code here:
        try {
            limpiarCampos();
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnNuevoDepActionPerformed

    private void btnGuardarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarDepActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            int est = 0;
            if (validarCampos()) {
                departamentoVo = new DepartamentosVo();
                departamentoVo.setId(Integer.parseInt(txtIdDepart.getText()));
                departamentoVo.setNombre(txtNomDepart.getText());
                departamentoVo.setDescripcion(txtDescDepart.getText());
                departamentoVo.setFoto(fisfoto);
                departamentoVo.setPath(txtPathImg.getText());
                if (radActivoDep.isSelected()) {
                    est = 1;
                } else if (radInactivoDep.isSelected()) {
                    est = 0;
                }
                departamentoVo.setStatus(est);
                System.err.println("fechaActual -> " + DateUtils.fechaActual());
                departamentoVo.setFechaUltActual(DateUtils.fechaActual());
                departamentoVo.setFechaExpiracion(DateUtils.fechaActual());
                departamentoVo.setFechaAlta(DateUtils.fechaActual());
                departamentoVo.setUserUltActual("TEST");
                departamentoVo.setBorrado(0);
                resp = departamentoService.guardar(departamentoVo);
                if (resp == 1) {
                    JOptionPane.showMessageDialog(null, "El registro de guardo correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un erro al guardar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                refreshTable();
                limpiarCampos();
            }
            System.out.println("lbl -> " + lblImagenDepartamento.getWidth() + lblImagenDepartamento.getHeight());
        } catch (Exception ex) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnGuardarDepActionPerformed

    private void tblDepartamentosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDepartamentosMouseClicked
        // TODO add your handling code here:
        try {
            limpiarCampos();
            btnBorrarDep.setEnabled(true);
            departamentoVo = new DepartamentosVo();
            JTable target = (JTable) evt.getSource();
            rowIndex = target.getSelectedRow();
            if (evt.getClickCount() == 1) {
                departamentoVo = departamentosTableModel.getDepartamentoVo(rowIndex);
                txtIdDepart.setText(String.valueOf(departamentoVo.getId()));
                txtNomDepart.setText(departamentoVo.getNombre());
                txtPathImg.setText(departamentoVo.getPath());
                txtDescDepart.setText(departamentoVo.getDescripcion());
                if (departamentoVo.getStatus() == 1) {
                    radActivoDep.setSelected(true);
                    radInactivoDep.setSelected(false);
                } else if (departamentoVo.getStatus() == 0) {
                    radInactivoDep.setSelected(true);
                    radActivoDep.setSelected(false);
                }
                //Image icono = ImageIO.read(new File(departamentoVo.getPath())).getScaledInstance(320, 118, Image.SCALE_DEFAULT);
                //lblImagenDepartamento.setIcon(new ImageIcon(icono));
            }
        } catch (Exception e) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, e);
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_tblDepartamentosMouseClicked

    private void radActivoDepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radActivoDepMouseClicked
        // TODO add your handling code here        
        if (radActivoDep.isSelected()) {
            radInactivoDep.setSelected(false);
        }
    }//GEN-LAST:event_radActivoDepMouseClicked

    private void radInactivoDepMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_radInactivoDepMouseClicked
        // TODO add your handling code here:
        if (radInactivoDep.isSelected()) {
            radActivoDep.setSelected(false);
        }
    }//GEN-LAST:event_radInactivoDepMouseClicked

    private void btnBorrarDepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarDepActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            departamentoVo.setBorrado(1);
            resp = departamentoService.borrar(departamentoVo);
            if (resp == 1) {
                JOptionPane.showMessageDialog(null, "El registro de borro correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un erro al borrar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            refreshTable();
            limpiarCampos();
            btnBorrarDep.setEnabled(false);
        } catch (Exception e) {
            Logger.getLogger(DepartamentosInternalFrame.class.getName()).log(Level.SEVERE, null, e);
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }

    }//GEN-LAST:event_btnBorrarDepActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBorrarDep;
    private javax.swing.JButton btnCerrarDep;
    private javax.swing.JButton btnExaminarImgDep;
    private javax.swing.JButton btnGuardarDep;
    private javax.swing.JButton btnNuevoDep;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDescDep;
    private javax.swing.JLabel lblEstDep;
    private javax.swing.JLabel lblIdDep;
    private javax.swing.JLabel lblImagenDepartamento;
    private javax.swing.JLabel lblImgDep;
    private javax.swing.JLabel lblNomDep;
    private javax.swing.JRadioButton radActivoDep;
    private javax.swing.JRadioButton radInactivoDep;
    private javax.swing.JTable tblDepartamentos;
    private javax.swing.JTextArea txtDescDepart;
    private javax.swing.JTextField txtIdDepart;
    private javax.swing.JTextField txtNomDepart;
    private javax.swing.JTextField txtPathImg;
    // End of variables declaration//GEN-END:variables
}
