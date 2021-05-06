/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.productos.vista;

import com.comunidad.simplecatproducto.catalogos.categorias.service.CategoriaService;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import com.comunidad.simplecatproducto.catalogos.productos.dao.vo.ProductosVo;
import com.comunidad.simplecatproducto.catalogos.productos.model.ProductosTableModel;
import com.comunidad.simplecatproducto.catalogos.productos.service.ProductoService;
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
public class ProductosInternalFrame extends javax.swing.JInternalFrame {

    private ProductosTableModel productosTableModel = null;
    private ProductoService productoService = null;

    private DepartamentoService departamentoService = null;
    private CategoriaService categoriaService = null;

    private JFileChooser chooser = null;
    public String fileID = null;
    private FileInputStream fisfoto = null;
    private int longitudBytes = 0;
    public static ApplicationContext ctx = null;
    private ProductosVo productosVo = null;
    int rowIndex = 0;

    private ItemComboboxModel itemComboboxModel = null;
    private DefaultComboBoxModel modeloDep = null;
    private DefaultComboBoxModel modeloCatego = null;

    /**
     * Creates new form ProductosInternalFrame
     */
    public ProductosInternalFrame() {
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
            productoService = (ProductoService) ctx.getBean("ProductoService");
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void refreshTable(int idCatego) {
        try {
            if (idCatego != 0) {
                productosTableModel = new ProductosTableModel(productoService, idCatego);
            } else {
                productosTableModel = new ProductosTableModel();
            }
            tblProductos.setModel(productosTableModel);
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void loadDataDepartamentos() {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modeloDep = new DefaultComboBoxModel(values);
            List<ItemComboboxModel> listaValores = departamentoService.catalogo();
            if (listaValores != null && listaValores.size() > 0) {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                for (int x = 0; x < listaValores.size(); x++) {
                    itemComboboxModel = listaValores.get(x);
                    values.add(itemComboboxModel);
                }
            } else {
                values.add(new ItemComboboxModel(0, "Selecionar"));
            }
            cbmDepartamentos.setModel(modeloDep);
            cbmDepartamentos.setSelectedIndex(0);

            limpiarComboCategorias();
            refreshTable(0);

//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public final void limpiarComboCategorias() {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            values.add(new ItemComboboxModel(0, "Selecionar"));
            modeloCatego = new DefaultComboBoxModel(values);
            cmbCategorias.setModel(modeloCatego);
            cmbCategorias.setSelectedIndex(0);

//            if (subproductosTableModel != null) {
//                subproductosTableModel.limpiar();
//                tblSubproductos.setModel(subproductosTableModel);
//                tblSubproductos.removeAll();
//            }
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void loadDataCategorias(int id) {
        try {
            System.out.println("loadDataCategorias-> " + id);
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modeloCatego = new DefaultComboBoxModel(values);
            List<ItemComboboxModel> listaValores = categoriaService.catalogo(id, 1);
            System.out.println("size-> " + listaValores.size());
            if (listaValores != null && listaValores.size() > 0) {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                for (int x = 0; x < listaValores.size(); x++) {
                    itemComboboxModel = listaValores.get(x);
                    values.add(itemComboboxModel);
                }
            } else {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                productosTableModel.limpiar();
                tblProductos.setModel(productosTableModel);
                tblProductos.removeAll();
            }
            cmbCategorias.setModel(modeloCatego);
            cmbCategorias.setSelectedIndex(0);

//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        String id = txtIdProd.getText();
        String nombre = txtNombreProd.getText();
        String descripcion = txtDescProd.getText();
        String pathImg = txtPathImgProd.getText();
        Icon icono = lblImagenProd.getIcon();
        int depaSelect = cbmDepartamentos.getSelectedIndex();
        int cateSelect = cmbCategorias.getSelectedIndex();
        boolean activo = radActivoProd.isSelected();
        boolean inactivo = radInactivoProd.isSelected();

        if (depaSelect == 0) {
            lblDepart.setBackground(Color.red);
            lblDepart.setOpaque(true);
            bandera = false;
        } else if (cateSelect == 0) {
            lblCatego.setBackground(Color.red);
            lblCatego.setOpaque(true);
            bandera = false;
        } else if (id.isEmpty()) {
            lblIdProd.setBackground(Color.red);
            lblIdProd.setOpaque(true);
            bandera = false;
        } else if (nombre.isEmpty()) {
            lblNomProd.setBackground(Color.red);
            lblNomProd.setOpaque(true);
            bandera = false;
        } else if (pathImg.isEmpty()) {
            lblImgProd.setBackground(Color.red);
            lblImgProd.setOpaque(true);
            bandera = false;
        } else if (descripcion.isEmpty()) {
            lblDescProd.setBackground(Color.red);
            lblDescProd.setOpaque(true);
            bandera = false;
        } else if (!activo && !inactivo) {
            lblEstProd.setBackground(Color.red);
            lblEstProd.setOpaque(true);
            bandera = false;
        }
        if (depaSelect != 0) {
            lblDepart.setBackground(Color.gray);
            lblDepart.setOpaque(false);
        }
        if (cateSelect != 0) {
            lblCatego.setBackground(Color.gray);
            lblCatego.setOpaque(false);
        }
        if (!id.isEmpty()) {
            lblIdProd.setBackground(Color.gray);
            lblIdProd.setOpaque(false);
        }
        if (!nombre.isEmpty()) {
            lblNomProd.setBackground(Color.gray);
            lblNomProd.setOpaque(false);
        }
        if (!pathImg.isEmpty()) {
            lblImgProd.setBackground(Color.gray);
            lblImgProd.setOpaque(false);
        }
        if (!descripcion.isEmpty()) {
            lblDescProd.setBackground(Color.gray);
            lblDescProd.setOpaque(false);
        }
        if (activo || inactivo) {
            lblEstProd.setBackground(Color.gray);
            lblEstProd.setOpaque(false);
        }
        return bandera;
    }

    private void activarComboDepartamentos() {
        cbmDepartamentos.setEnabled(true);
    }

    private void activarComboCategorias() {
        cmbCategorias.setEnabled(true);
    }

    public void limpiarCampos() {
        lblImagenProd.setIcon(null);
        txtIdProd.setText(Utils.idCadena());
        txtDescProd.setText("");
        txtDescProd.setEditable(true);
        txtNombreProd.setText("");
        txtNombreProd.setEditable(true);
        txtPathImgProd.setText("");
        bntExaminarProd.setEnabled(true);
        //btnBorrarProd.setEnabled(true);
        //btnCerrarProd.setEnabled(true);
        btnGuardarProd.setEnabled(true);
        cbmDepartamentos.setEnabled(true);
        cmbCategorias.setEnabled(true);

        radActivoProd.setSelected(false);
        radInactivoProd.setSelected(false);
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
        cbmDepartamentos = new javax.swing.JComboBox<>();
        lblCatego = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        lblIdProd = new javax.swing.JLabel();
        txtIdProd = new javax.swing.JTextField();
        lblNomProd = new javax.swing.JLabel();
        txtNombreProd = new javax.swing.JTextField();
        lblImgProd = new javax.swing.JLabel();
        txtPathImgProd = new javax.swing.JTextField();
        bntExaminarProd = new javax.swing.JButton();
        lblImagenProd = new javax.swing.JLabel();
        lblDescProd = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescProd = new javax.swing.JTextArea();
        btnCerrarProd = new javax.swing.JButton();
        btnGuardarProd = new javax.swing.JButton();
        lblEstProd = new javax.swing.JLabel();
        radActivoProd = new javax.swing.JRadioButton();
        radInactivoProd = new javax.swing.JRadioButton();
        jPanel2 = new javax.swing.JPanel();
        btnNuevoProd = new javax.swing.JButton();
        btnBorrarProd = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblProductos = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Catalogo productos");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(332, 503));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Productos");

        lblDepart.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDepart.setText("* Departamentos:");

        cbmDepartamentos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cbmDepartamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbmDepartamentos.setEnabled(false);
        cbmDepartamentos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbmDepartamentosItemStateChanged(evt);
            }
        });

        lblCatego.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblCatego.setText("* Categorias:");

        cmbCategorias.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategorias.setEnabled(false);
        cmbCategorias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriasItemStateChanged(evt);
            }
        });

        lblIdProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblIdProd.setText("* ID:");

        txtIdProd.setEditable(false);
        txtIdProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblNomProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblNomProd.setText("* Nombre:");

        txtNombreProd.setEditable(false);
        txtNombreProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        lblImgProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblImgProd.setText("* Imagen:");

        txtPathImgProd.setEditable(false);
        txtPathImgProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        bntExaminarProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bntExaminarProd.setText("Examinar...");
        bntExaminarProd.setEnabled(false);
        bntExaminarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntExaminarProdActionPerformed(evt);
            }
        });

        lblDescProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        lblDescProd.setText("* Descripcion:");

        txtDescProd.setEditable(false);
        txtDescProd.setColumns(20);
        txtDescProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescProd.setRows(5);
        jScrollPane1.setViewportView(txtDescProd);

        btnCerrarProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnCerrarProd.setText("Cerrar");
        btnCerrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarProdActionPerformed(evt);
            }
        });

        btnGuardarProd.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGuardarProd.setText("Guardar");
        btnGuardarProd.setEnabled(false);
        btnGuardarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarProdActionPerformed(evt);
            }
        });

        lblEstProd.setText("* Estatus:");

        radActivoProd.setText("Activo");

        radInactivoProd.setText("Inactivo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(bntExaminarProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDepart)
                            .addComponent(lblCatego)
                            .addComponent(lblIdProd)
                            .addComponent(lblNomProd)
                            .addComponent(lblImgProd))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPathImgProd)
                            .addComponent(txtNombreProd)
                            .addComponent(txtIdProd)
                            .addComponent(cmbCategorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cbmDepartamentos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(lblImagenProd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCerrarProd, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarProd, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(radInactivoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDescProd)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblEstProd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radActivoProd, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                    .addComponent(cbmDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblCatego)
                    .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblIdProd)
                    .addComponent(txtIdProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblNomProd)
                    .addComponent(txtNombreProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblImgProd)
                    .addComponent(txtPathImgProd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(bntExaminarProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagenProd, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDescProd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstProd)
                    .addComponent(radActivoProd)
                    .addComponent(radInactivoProd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCerrarProd)
                    .addComponent(btnGuardarProd))
                .addContainerGap())
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(613, 457));

        btnNuevoProd.setText("Nuevo");
        btnNuevoProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoProdActionPerformed(evt);
            }
        });

        btnBorrarProd.setText("Borrar");
        btnBorrarProd.setEnabled(false);
        btnBorrarProd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBorrarProdActionPerformed(evt);
            }
        });

        tblProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        tblProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductosMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblProductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(btnNuevoProd)
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrarProd)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevoProd)
                    .addComponent(btnBorrarProd))
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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCerrarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarProdActionPerformed
        // TODO add your handling code here:
        try {
            this.setClosed(true);
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnCerrarProdActionPerformed

    private void btnNuevoProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoProdActionPerformed
        // TODO add your handling code here:
        try {
            activarComboDepartamentos();
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnNuevoProdActionPerformed

    private void bntExaminarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntExaminarProdActionPerformed
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
                    lblImagenProd.setIcon(new ImageIcon(icono));

                    File file = chooser.getSelectedFile();
                    fileID = file.getAbsolutePath();
                    txtPathImgProd.setText(fileID);
                }
            } else {
                System.out.println("No file choosen!");
            }

        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_bntExaminarProdActionPerformed

    private void btnGuardarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarProdActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            int est = 0;
            if (validarCampos()) {
                productosVo = new ProductosVo();
                itemComboboxModel = (ItemComboboxModel) modeloCatego.getSelectedItem();
                productosVo.setId(Integer.parseInt(txtIdProd.getText()));
                productosVo.setIdCatego(itemComboboxModel.getId());
                productosVo.setNombre(txtNombreProd.getText());
                productosVo.setDescripcion(txtDescProd.getText());
                productosVo.setFoto(fisfoto);
                productosVo.setPath(txtPathImgProd.getText());
                if (radActivoProd.isSelected()) {
                    est = 1;
                } else if (radInactivoProd.isSelected()) {
                    est = 0;
                }
                productosVo.setStatus(est);
                productosVo.setFechaUltActual(DateUtils.fechaActual());
                productosVo.setFechaExpiracion(DateUtils.fechaActual());
                productosVo.setFechaAlta(DateUtils.fechaActual());
                productosVo.setUserUltActual("TEST");
                productosVo.setBorrado(0);
                resp = productoService.guardar(productosVo);
                if (resp == 1) {
                    JOptionPane.showMessageDialog(null, "El registro de guardo correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Ocurrio un erro al guardar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
                }
                limpiarCampos();
                refreshTable(itemComboboxModel.getId());
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnGuardarProdActionPerformed

    private void cbmDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbmDepartamentosItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                itemComboboxModel = (ItemComboboxModel) modeloDep.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    loadDataCategorias(itemComboboxModel.getId());
                    activarComboCategorias();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cbmDepartamentosItemStateChanged

    private void cmbCategoriasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriasItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                itemComboboxModel = (ItemComboboxModel) modeloCatego.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    refreshTable(itemComboboxModel.getId());
                    limpiarCampos();
                } else {
                    tblProductos.removeAll();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cmbCategoriasItemStateChanged

    private void tblProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductosMouseClicked
        // TODO add your handling code here:
        try {
            limpiarCampos();
            btnBorrarProd.setEnabled(true);
            productosVo = new ProductosVo();
            JTable target = (JTable) evt.getSource();
            rowIndex = target.getSelectedRow();
            if (evt.getClickCount() == 1) {
                productosVo = productosTableModel.getProductosVo(rowIndex);
                txtIdProd.setText(String.valueOf(productosVo.getId()));
                txtNombreProd.setText(productosVo.getNombre());
                txtPathImgProd.setText(productosVo.getPath());
                txtDescProd.setText(productosVo.getDescripcion());
                if (productosVo.getStatus() == 1) {
                    radActivoProd.setSelected(true);
                    radInactivoProd.setSelected(false);
                } else if (productosVo.getStatus() == 0) {
                    radInactivoProd.setSelected(true);
                    radActivoProd.setSelected(false);
                }
                //Image icono = ImageIO.read(new File(departamentoVo.getPath())).getScaledInstance(320, 118, Image.SCALE_DEFAULT);
                //lblImagenDepartamento.setIcon(new ImageIcon(icono));
            }
        } catch (Exception e) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, e);
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_tblProductosMouseClicked

    private void btnBorrarProdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBorrarProdActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            productosVo.setBorrado(1);
            resp = productoService.borrar(productosVo);
            if (resp == 1) {
                JOptionPane.showMessageDialog(null, "El registro de borro correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Ocurrio un erro al borrar el registro, intente otra vez.", "Alerta", JOptionPane.WARNING_MESSAGE);
            }
            refreshTable(itemComboboxModel.getId());
            limpiarCampos();
            btnBorrarProd.setEnabled(false);
        } catch (Exception e) {
            Logger.getLogger(ProductosInternalFrame.class.getName()).log(Level.SEVERE, null, e);
            StringBuilder sb = new StringBuilder(e.toString());
            for (StackTraceElement ste : e.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnBorrarProdActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntExaminarProd;
    private javax.swing.JButton btnBorrarProd;
    private javax.swing.JButton btnCerrarProd;
    private javax.swing.JButton btnGuardarProd;
    private javax.swing.JButton btnNuevoProd;
    private javax.swing.JComboBox<String> cbmDepartamentos;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCatego;
    private javax.swing.JLabel lblDepart;
    private javax.swing.JLabel lblDescProd;
    private javax.swing.JLabel lblEstProd;
    private javax.swing.JLabel lblIdProd;
    private javax.swing.JLabel lblImagenProd;
    private javax.swing.JLabel lblImgProd;
    private javax.swing.JLabel lblNomProd;
    private javax.swing.JRadioButton radActivoProd;
    private javax.swing.JRadioButton radInactivoProd;
    private javax.swing.JTable tblProductos;
    private javax.swing.JTextArea txtDescProd;
    private javax.swing.JTextField txtIdProd;
    private javax.swing.JTextField txtNombreProd;
    private javax.swing.JTextField txtPathImgProd;
    // End of variables declaration//GEN-END:variables
}
