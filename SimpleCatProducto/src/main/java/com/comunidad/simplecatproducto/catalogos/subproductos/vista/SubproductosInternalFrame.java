/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.comunidad.simplecatproducto.catalogos.subproductos.vista;

import com.comunidad.simplecatproducto.catalogos.categorias.service.CategoriaService;
import com.comunidad.simplecatproducto.catalogos.departamentos.service.DepartamentoService;
import com.comunidad.simplecatproducto.catalogos.productos.service.ProductoService;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.DetalleSubprodVo;
import com.comunidad.simplecatproducto.catalogos.subproductos.dao.vo.SubproductosVo;
import com.comunidad.simplecatproducto.catalogos.subproductos.model.DetalleSubprodTableModel;
import com.comunidad.simplecatproducto.catalogos.subproductos.model.SubproductosTableModel;
import com.comunidad.simplecatproducto.catalogos.subproductos.service.DetalleSubprodService;
import com.comunidad.simplecatproducto.catalogos.subproductos.service.SubproductoService;
import com.comunidad.simplecatproducto.common.ImageFilter;
import com.comunidad.simplecatproducto.common.ItemComboboxModel;
import com.comunidad.simplecatproducto.common.Utils;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.table.JTableHeader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Santiago
 */
public class SubproductosInternalFrame extends javax.swing.JInternalFrame {

    private SubproductosTableModel subproductosTableModel = null;
    private SubproductoService subproductoService = null;

    private DetalleSubprodTableModel detalleSubprodTableModel = null;
    private DetalleSubprodService detalleSubprodService = null;
    private DetalleSubprodVo detalleSubprodVo = null;
    private List<DetalleSubprodVo> detalles = null;

    private DepartamentoService departamentoService = null;
    private CategoriaService categoriaService = null;
    private ProductoService productoService = null;

    private JFileChooser chooser = null;
    public String fileID = null;
    private FileInputStream fisfoto = null;
    private int longitudBytes = 0;
    public static ApplicationContext ctx = null;
    private SubproductosVo subproductosVo = null;
    int rowIndex = 0;
    private int rowIndexDet = -1;

    private ItemComboboxModel itemComboboxModel = null;
    private DefaultComboBoxModel modeloDepa = null;
    private DefaultComboBoxModel modeloCatego = null;
    private DefaultComboBoxModel modeloProd = null;

    /**
     * Creates new form SubproductosInternalFrame
     */
    public SubproductosInternalFrame() {
        initComponents();
        initConfig();
        //refreshTable();
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
            subproductoService = (SubproductoService) ctx.getBean("SubproductoService");
            detalleSubprodService = (DetalleSubprodService) ctx.getBean("DetalleSubprodService");
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void cargarTablaDetalle(int idSubprod) {
        try {
            if (idSubprod != 0) {
                detalleSubprodTableModel = new DetalleSubprodTableModel(detalleSubprodService, idSubprod);
            } else {
                detalleSubprodTableModel = new DetalleSubprodTableModel();
            }
            tblDetalleSubprod.setModel(detalleSubprodTableModel);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void refreshTable(int idProd) {
        try {
            if (idProd != 0) {
                subproductosTableModel = new SubproductosTableModel(subproductoService, idProd);
            } else {
                subproductosTableModel = new SubproductosTableModel();
            }
            tblSubproductos.setModel(subproductosTableModel);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public void loadDataProductos(int id) {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modeloProd = new DefaultComboBoxModel(values);
            List<ItemComboboxModel> listaValores = productoService.catalogo(id, 1);
            if (listaValores != null && listaValores.size() > 0) {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                for (int x = 0; x < listaValores.size(); x++) {
                    itemComboboxModel = listaValores.get(x);
                    values.add(itemComboboxModel);
                }
            } else {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                if (subproductosTableModel != null) {
                    subproductosTableModel.limpiar();
                    tblSubproductos.setModel(subproductosTableModel);
                    tblSubproductos.removeAll();
                }
            }
            cmbProductos.setModel(modeloProd);
            cmbProductos.setSelectedIndex(0);

//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modeloCatego = new DefaultComboBoxModel(values);
            List<ItemComboboxModel> listaValores = categoriaService.catalogo(id, 1);
            if (listaValores != null && listaValores.size() > 0) {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                for (int x = 0; x < listaValores.size(); x++) {
                    itemComboboxModel = listaValores.get(x);
                    values.add(itemComboboxModel);
                }
            } else {
                values.add(new ItemComboboxModel(0, "Selecionar"));
                //limpiarComboProductos();
            }
            cmbCategorias.setModel(modeloCatego);
            cmbCategorias.setSelectedIndex(0);

//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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

            if (subproductosTableModel != null) {
                subproductosTableModel.limpiar();
                tblSubproductos.setModel(subproductosTableModel);
                tblSubproductos.removeAll();
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public final void limpiarComboProductos() {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            values.add(new ItemComboboxModel(0, "Selecionar"));
            modeloProd = new DefaultComboBoxModel(values);
            cmbProductos.setModel(modeloProd);
            cmbProductos.setSelectedIndex(0);
            if (subproductosTableModel != null) {
                subproductosTableModel.limpiar();
                tblSubproductos.setModel(subproductosTableModel);
                tblSubproductos.removeAll();
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }

    public final void loadDataDepartamentos() {
        try {
            Vector<ItemComboboxModel> values = new Vector<ItemComboboxModel>();
            modeloDepa = new DefaultComboBoxModel(values);
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
            cmbDepartamentos.setModel(modeloDepa);
            cmbDepartamentos.setSelectedIndex(0);
            limpiarComboCategorias();
            limpiarComboProductos();
            cargarTablaDetalle(0);
            refreshTable(0);
//            departamentosComboboxModel = new DepartamentosComboboxModel(departamentoService);
//            cmbDepartamentos.setModel(departamentosComboboxModel);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
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
        String nombre = txtNombreSubprod.getText();
        String descripcion = txtDescSubprod.getText();
        String pathImg = txtPathImgSubprod.getText();
        String detNom = txtDetalleNombre.getText();
        String detDesc = txtDetalleDesc.getText();
        Icon icono = lblImagSubprod.getIcon();
        int depaSelect = cmbDepartamentos.getSelectedIndex();
        int cateSelect = cmbCategorias.getSelectedIndex();
        int prodSelect = cmbProductos.getSelectedIndex();

        if (depaSelect == 0) {
            cmbDepartamentos.setBackground(Color.RED);
            bandera = false;
        } else if (cateSelect == 0) {
            cmbCategorias.setBackground(Color.RED);
            bandera = false;
        } else if (prodSelect == 0) {
            cmbProductos.setBackground(Color.RED);
            bandera = false;
        } else if (nombre.isEmpty()) {
            txtNombreSubprod.setBackground(Color.RED);
            bandera = false;
        } else if (descripcion.isEmpty()) {
            txtDescSubprod.setBackground(Color.RED);
            bandera = false;
        } else if (pathImg.isEmpty()) {
            txtPathImgSubprod.setBackground(Color.RED);
            bandera = false;
        } else if (icono == null) {
            lblImagSubprod.setBackground(Color.RED);
            bandera = false;
        } else if (detalleSubprodTableModel.getRowCount() == 0) {
            JTableHeader header = tblDetalleSubprod.getTableHeader();
            header.setBackground(Color.RED);
            bandera = false;
        }

        /* else if (detDesc.isEmpty()) {
            txtDetalleDesc.setBackground(Color.RED);
            bandera = false;
        }*/
        if (depaSelect != 0) {
            cmbDepartamentos.setBackground(Color.WHITE);
        }
        if (cateSelect != 0) {
            cmbCategorias.setBackground(Color.WHITE);
        }
        if (prodSelect != 0) {
            cmbProductos.setBackground(Color.WHITE);
        }
        if (!nombre.isEmpty()) {
            txtNombreSubprod.setBackground(Color.WHITE);
        }
        if (!descripcion.isEmpty()) {
            txtDescSubprod.setBackground(Color.WHITE);
        }
        if (!pathImg.isEmpty()) {
            txtPathImgSubprod.setBackground(Color.WHITE);
        }
        if (detalleSubprodTableModel.getRowCount() != 0) {
            JTableHeader header = tblDetalleSubprod.getTableHeader();
            header.setBackground(Color.WHITE);
        }
        return bandera;
    }

    private void activarComboDepartamentos() {
        cmbDepartamentos.setEnabled(true);
    }

    private void activarComboCategorias() {
        cmbCategorias.setEnabled(true);
    }

    private void activarComboProductos() {
        cmbProductos.setEnabled(true);
    }

    private void activarCampos() {
        txtIdSubprod.setText(String.valueOf(Utils.generarId()));
        txtNombreSubprod.setEditable(true);
        txtDescSubprod.setEditable(true);
        txtDetalleNombre.setEditable(true);
        txtDetalleDesc.setEditable(true);
        btnGuardarSubprod.setEnabled(true);
        btnAgregarDetalle.setEnabled(true);
        btnExaminarSubprod.setEnabled(true);
    }

    private void inactivarCampos() {
        txtIdSubprod.setText(String.valueOf(Utils.generarId()));
        txtNombreSubprod.setEditable(false);
        txtDescSubprod.setEditable(false);
        txtDetalleNombre.setEditable(false);
        txtDetalleDesc.setEditable(false);
        btnGuardarSubprod.setEnabled(false);
        btnAgregarDetalle.setEnabled(false);
        btnExaminarSubprod.setEnabled(false);
    }

    private void limpiarCamposDetalle() {
        txtDetalleNombre.setText("");
        txtDetalleDesc.setText("");
    }

    public void limpiarCampos() {
        lblImagSubprod.setIcon(null);
        txtDescSubprod.setText("");

        txtIdSubprod.setText(String.valueOf(Utils.generarId()));
        txtNombreSubprod.setText("");

        txtDetalleNombre.setText("");
        txtDetalleDesc.setText("");

        txtPathImgSubprod.setText("");

//        cmbCategorias.setEnabled(true);
//        cmbDepartamentos.setEnabled(true);
//        cmbProductos.setEnabled(true);
//        txtNombreSubprod.setBorder(UIManager.getBorder("TextField.border"));
//        txtDescSubprod.setBorder(UIManager.getBorder("TextField.border"));
//        txtPathImgSubprod.setBorder(UIManager.getBorder("TextField.border"));
//        lblImagSubprod.setBorder(UIManager.getBorder("TextField.border"));
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
        jLabel2 = new javax.swing.JLabel();
        cmbDepartamentos = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        cmbCategorias = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cmbProductos = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtIdSubprod = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtNombreSubprod = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPathImgSubprod = new javax.swing.JTextField();
        btnExaminarSubprod = new javax.swing.JButton();
        lblImagSubprod = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescSubprod = new javax.swing.JTextArea();
        bntCerrarSubprod = new javax.swing.JButton();
        btnGuardarSubprod = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        bntNuevoSubprod = new javax.swing.JButton();
        btnBorrarSubprod = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSubproductos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        btnAgregarDetalle = new javax.swing.JButton();
        txtDetalleNombre = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDetalleDesc = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblDetalleSubprod = new javax.swing.JTable();
        btnQuitarDetalle = new javax.swing.JButton();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Catalogo subproductos");
        setAutoscrolls(true);
        setPreferredSize(new java.awt.Dimension(1200, 600));

        jPanel1.setPreferredSize(new java.awt.Dimension(332, 503));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Subproductos");

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setText("Departamentos:");

        cmbDepartamentos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbDepartamentos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbDepartamentos.setEnabled(false);
        cmbDepartamentos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDepartamentosItemStateChanged(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setText("Categorias:");

        cmbCategorias.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbCategorias.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbCategorias.setEnabled(false);
        cmbCategorias.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCategoriasItemStateChanged(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Productos:");

        cmbProductos.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cmbProductos.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbProductos.setEnabled(false);
        cmbProductos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbProductosItemStateChanged(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel5.setText("ID:");

        txtIdSubprod.setEditable(false);
        txtIdSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtIdSubprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSubprodActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel6.setText("Nombre:");

        txtNombreSubprod.setEditable(false);
        txtNombreSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel7.setText("Imagen:");

        txtPathImgSubprod.setEditable(false);
        txtPathImgSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        btnExaminarSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnExaminarSubprod.setText("Examinar...");
        btnExaminarSubprod.setEnabled(false);
        btnExaminarSubprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExaminarSubprodActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel9.setText("Descripcion:");

        txtDescSubprod.setEditable(false);
        txtDescSubprod.setColumns(20);
        txtDescSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtDescSubprod.setRows(5);
        jScrollPane1.setViewportView(txtDescSubprod);

        bntCerrarSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bntCerrarSubprod.setText("Cerrar");
        bntCerrarSubprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntCerrarSubprodActionPerformed(evt);
            }
        });

        btnGuardarSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnGuardarSubprod.setText("Guardar");
        btnGuardarSubprod.setEnabled(false);
        btnGuardarSubprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarSubprodActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblImagSubprod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExaminarSubprod, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jLabel2)
                                                .addGap(18, 18, 18))
                                            .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addGap(47, 47, 47)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(jLabel4)
                                            .addGap(51, 51, 51)))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(100, 100, 100)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel6)
                                    .addGap(64, 64, 64)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(68, 68, 68)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPathImgSubprod)
                            .addComponent(txtNombreSubprod)
                            .addComponent(txtIdSubprod)
                            .addComponent(cmbProductos, 0, 202, Short.MAX_VALUE)
                            .addComponent(cmbCategorias, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cmbDepartamentos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(bntCerrarSubprod, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardarSubprod, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbDepartamentos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(cmbCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(cmbProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addComponent(txtIdSubprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtNombreSubprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtPathImgSubprod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnExaminarSubprod)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImagSubprod, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntCerrarSubprod)
                    .addComponent(btnGuardarSubprod))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setPreferredSize(new java.awt.Dimension(613, 457));

        bntNuevoSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        bntNuevoSubprod.setText("Nuevo");
        bntNuevoSubprod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bntNuevoSubprodActionPerformed(evt);
            }
        });

        btnBorrarSubprod.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        btnBorrarSubprod.setText("Borrar");
        btnBorrarSubprod.setEnabled(false);

        tblSubproductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tblSubproductos);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(bntNuevoSubprod)
                        .addGap(18, 18, 18)
                        .addComponent(btnBorrarSubprod)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bntNuevoSubprod)
                    .addComponent(btnBorrarSubprod))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2)
                .addContainerGap())
        );

        jLabel8.setText("Detalle");

        btnAgregarDetalle.setText("Agregar");
        btnAgregarDetalle.setEnabled(false);
        btnAgregarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarDetalleActionPerformed(evt);
            }
        });

        txtDetalleNombre.setEditable(false);

        txtDetalleDesc.setEditable(false);
        txtDetalleDesc.setColumns(20);
        txtDetalleDesc.setRows(5);
        jScrollPane3.setViewportView(txtDetalleDesc);

        tblDetalleSubprod.setModel(new javax.swing.table.DefaultTableModel(
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
        tblDetalleSubprod.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblDetalleSubprodMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tblDetalleSubprod);

        btnQuitarDetalle.setText("Quitar");
        btnQuitarDetalle.setEnabled(false);
        btnQuitarDetalle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarDetalleActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnQuitarDetalle, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAgregarDetalle))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtDetalleNombre)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 441, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtDetalleNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarDetalle)
                    .addComponent(btnQuitarDetalle))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 607, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bntCerrarSubprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntCerrarSubprodActionPerformed
        // TODO add your handling code here:
        try {
            this.setClosed(true);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_bntCerrarSubprodActionPerformed

    private void bntNuevoSubprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bntNuevoSubprodActionPerformed
        // TODO add your handling code here:
        try {
            cargarTablaDetalle(0);
            activarComboDepartamentos();
            //limpiarCampos();
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_bntNuevoSubprodActionPerformed

    private void btnExaminarSubprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExaminarSubprodActionPerformed
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
                    lblImagSubprod.setIcon(new ImageIcon(icono));

                    File file = chooser.getSelectedFile();
                    fileID = file.getAbsolutePath();
                    txtPathImgSubprod.setText(fileID);
                }
            } else {
                System.out.println("No file choosen!");
            }

        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnExaminarSubprodActionPerformed

    private void btnGuardarSubprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarSubprodActionPerformed
        // TODO add your handling code here:
        try {
            int resp = 0;
            Date fechaActual = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
            if (validarCampos()) {
                subproductosVo = new SubproductosVo();
                detalleSubprodVo = new DetalleSubprodVo();
                detalles = detalleSubprodTableModel.getAllDetalleSubprodVo();
                itemComboboxModel = (ItemComboboxModel) modeloProd.getSelectedItem();
                subproductosVo.setId(Integer.parseInt(txtIdSubprod.getText()));
                subproductosVo.setIdProd(itemComboboxModel.getId());
                subproductosVo.setNombre(txtNombreSubprod.getText());
                subproductosVo.setDescripcion(txtDescSubprod.getText());
                subproductosVo.setFoto(fisfoto);
                subproductosVo.setStatus(1);
                subproductosVo.setFechaUltActual(fechaActual);
                subproductosVo.setFechaExpiracion(fechaActual);
                subproductosVo.setUserUltActual("TEST");
                subproductosVo.setDetalles(detalles);
                resp = subproductoService.guardar(subproductosVo);
                System.out.println("resp -> " + resp);

                limpiarCampos();
                refreshTable(itemComboboxModel.getId());
                cargarTablaDetalle(0);
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnGuardarSubprodActionPerformed

    private void cmbDepartamentosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDepartamentosItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                inactivarCampos();
                itemComboboxModel = (ItemComboboxModel) modeloDepa.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    loadDataCategorias(itemComboboxModel.getId());
                    activarComboCategorias();
                }
                if (modeloProd != null && modeloProd.getSize() != 0) {
                    limpiarComboProductos();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cmbDepartamentosItemStateChanged

    private void cmbCategoriasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCategoriasItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                inactivarCampos();
                itemComboboxModel = (ItemComboboxModel) modeloCatego.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    loadDataProductos(itemComboboxModel.getId());
                    activarComboProductos();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cmbCategoriasItemStateChanged

    private void cmbProductosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbProductosItemStateChanged
        // TODO add your handling code here:
        try {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                itemComboboxModel = (ItemComboboxModel) modeloProd.getSelectedItem();
                if (itemComboboxModel.getId() != 0) {
                    refreshTable(itemComboboxModel.getId());
                    cargarTablaDetalle(0);
                    activarCampos();
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_cmbProductosItemStateChanged

    private void btnAgregarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarDetalleActionPerformed
        // TODO add your handling code here:
        try {
            String detalle = txtDetalleNombre.getText().trim();
            String descrip = txtDetalleDesc.getText().trim();
            if (!detalle.isEmpty() && !descrip.isEmpty()) {
                detalleSubprodVo = new DetalleSubprodVo();
                //detalleSubprodTableModel = new DetalleSubprodTableModel();
                Date fechaActual = new Date();
                detalleSubprodVo.setIdSubprod(Integer.parseInt(txtIdSubprod.getText()));
                detalleSubprodVo.setNombre(detalle);
                detalleSubprodVo.setDescripcion(descrip);
                detalleSubprodVo.setStatus(1);
                detalleSubprodVo.setFechaUltActual(fechaActual);
                detalleSubprodVo.setFechaExpiracion(fechaActual);
                detalleSubprodVo.setUserUltActual("TEST");
                detalleSubprodTableModel.addDetalleSubprodVo(detalleSubprodVo);
                limpiarCamposDetalle();
                rowIndexDet = -1;
                btnQuitarDetalle.setEnabled(false);
            }
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnAgregarDetalleActionPerformed

    private void btnQuitarDetalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarDetalleActionPerformed
        // TODO add your handling code here:
        try {
            detalleSubprodTableModel.removeDetalleSubprodVo(rowIndexDet);
            btnQuitarDetalle.setEnabled(false);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_btnQuitarDetalleActionPerformed

    private void tblDetalleSubprodMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblDetalleSubprodMouseClicked
        // TODO add your handling code here:
        try {
            JTable target = (JTable) evt.getSource();
            rowIndexDet = target.getSelectedRow();
            btnQuitarDetalle.setEnabled(true);
        } catch (Exception ex) {
            Logger.getLogger(SubproductosInternalFrame.class.getName()).log(Level.SEVERE, null, ex);
            StringBuilder sb = new StringBuilder(ex.toString());
            for (StackTraceElement ste : ex.getStackTrace()) {
                sb.append("\n\tat ");
                sb.append(ste);
            }
            String trace = sb.toString();
            JOptionPane.showMessageDialog(null, "Ocurrio un error en el sistema: " + trace);
        }
    }//GEN-LAST:event_tblDetalleSubprodMouseClicked

    private void txtIdSubprodActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSubprodActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdSubprodActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bntCerrarSubprod;
    private javax.swing.JButton bntNuevoSubprod;
    private javax.swing.JButton btnAgregarDetalle;
    private javax.swing.JButton btnBorrarSubprod;
    private javax.swing.JButton btnExaminarSubprod;
    private javax.swing.JButton btnGuardarSubprod;
    private javax.swing.JButton btnQuitarDetalle;
    private javax.swing.JComboBox<String> cmbCategorias;
    private javax.swing.JComboBox<String> cmbDepartamentos;
    private javax.swing.JComboBox<String> cmbProductos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblImagSubprod;
    private javax.swing.JTable tblDetalleSubprod;
    private javax.swing.JTable tblSubproductos;
    private javax.swing.JTextArea txtDescSubprod;
    private javax.swing.JTextArea txtDetalleDesc;
    private javax.swing.JTextField txtDetalleNombre;
    private javax.swing.JTextField txtIdSubprod;
    private javax.swing.JTextField txtNombreSubprod;
    private javax.swing.JTextField txtPathImgSubprod;
    // End of variables declaration//GEN-END:variables
}
