/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Caja;

import java.awt.BorderLayout;
import modelo.CheckableItem;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.accessibility.Accessible;
import javax.swing.AbstractAction;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.ComboPopup;
import modelo.Conexion;
import vista.Produccion.Error_guardar;
import vista.Produccion.FormuEtapaProduccion;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import vista.Inventario0.herramientasNuevo;
import vista.Inventario0.nuevoMateriales;
import vista.Produccion.Datos_guardados;
import vista.proveedornuevo;

/**
 *
 * @author ADSO
 */
public class formuEgresos1 extends javax.swing.JDialog {

    private CheckedComboBox<CheckableItem> cmbMateriales;
    private CheckedComboBox<CheckableItem> cmbHerramientas;

    /**
     * Creates new form formuIngresos
     */
    public formuEgresos1(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        cargarProveedores();
        ohtaniahea();
        setPreferredSize(new java.awt.Dimension(522, 460));

        cmbMateriales = new CheckedComboBox<>(makeProductModel("material"));
        cmbMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPanel1.add(cmbMateriales, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 240, 240, 30));

        cmbHerramientas = new CheckedComboBox<>(makeProductModel("herramienta"));
        cmbHerramientas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPanel1.add(cmbHerramientas, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 310, 240, 30));
        cmbHerramientas.setVisible(false);
        cmbMateriales.setVisible(false);
        getContentPane().add(jPanel1, new java.awt.GridBagConstraints());
        configurarFiltroNumerico();
    }

    private DefaultComboBoxModel<CheckableItem> makeProductModel(String tipo) {
        DefaultComboBoxModel<CheckableItem> model = new DefaultComboBoxModel<>();
        try {
            Connection con = Conexion.getConnection();
            String sql = "SELECT id_inventario, nombre FROM inventario WHERE tipo = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_inventario");
                String nombre = rs.getString("nombre");
                model.addElement(new CheckableItem(id, nombre, false));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
            Error_guardar errorDialog = new Error_guardar(
                    (Frame) this.getParent(),
                    true,
                    "Error",
                    "No se pudieron cargar los " + tipo + ": " + ex.getMessage()
            );
            errorDialog.setLocationRelativeTo(null);
            errorDialog.setVisible(true);
        }
        return model;
    }

    private List<String> obtenerSeleccionados(CheckedComboBox<CheckableItem> combo) {
        List<String> seleccionados = new ArrayList<>();
        for (int i = 0; i < combo.getModel().getSize(); i++) {
            CheckableItem item = combo.getModel().getElementAt(i);
            if (item.isSelected()) {
                seleccionados.add(item.toString());
            }
        }
        return seleccionados;
    }

    class CheckedComboBox<E extends CheckableItem> extends JComboBox<E> {

        protected boolean keepOpen;
        private final JPanel panel = new JPanel(new BorderLayout());

        protected CheckedComboBox(ComboBoxModel<E> model) {
            super(model);
            setBackground(new Color(255, 255, 255)); // Fondo blanco para coincidir con jPanel1
            setForeground(Color.DARK_GRAY); // Texto oscuro
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(200, 40); // Aumentar altura para un look más moderno
        }

        @Override
        public void updateUI() {
            setRenderer(null);
            super.updateUI();

            Accessible a = getAccessibleContext().getAccessibleChild(0);
            if (a instanceof ComboPopup) {
                ((ComboPopup) a).getList().addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        JList<?> list = (JList<?>) e.getComponent();
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            keepOpen = true;
                            updateItem(list.locationToIndex(e.getPoint()));
                        }
                    }
                });
            }

            DefaultListCellRenderer renderer = new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                    Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (index >= 0) {
                        c.setBackground(isSelected ? new Color(0, 120, 215, 50) : new Color(255, 255, 255));
                        c.setForeground(Color.DARK_GRAY);
                    } else {
                        c.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente para el texto seleccionado
                    }
                    return c;
                }
            };
            JCheckBox check = new JCheckBox();
            check.setOpaque(false);
            check.setForeground(new Color(0, 120, 215)); // Color de casilla moderna
            setRenderer((list, value, index, isSelected, cellHasFocus) -> {
                panel.removeAll();
                Component c = renderer.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                if (index < 0) {
                    String txt = getCheckedItemString(list.getModel());
                    JLabel l = (JLabel) c;
                    l.setText(txt.isEmpty() ? " " : txt);
                    l.setForeground(Color.DARK_GRAY);
                    l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                    panel.setOpaque(false); // Hacer el panel transparente
                    panel.setBackground(new Color(0, 0, 0, 0)); // Fondo transparente
                } else {
                    check.setSelected(value.isSelected());
                    panel.add(check, BorderLayout.WEST);
                    panel.setBackground(isSelected ? new Color(0, 120, 215, 50) : new Color(255, 255, 255));
                }
                panel.add(c, BorderLayout.CENTER);
                return panel;
            });
            initActionMap();
        }

        protected void initActionMap() {
            KeyStroke ks = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0);
            getInputMap(JComponent.WHEN_FOCUSED).put(ks, "checkbox-select");
            getActionMap().put("checkbox-select", new AbstractAction() {
                public void actionPerformed(ActionEvent e) {
                    Accessible a = getAccessibleContext().getAccessibleChild(0);
                    if (a instanceof ComboPopup) {
                        updateItem(((ComboPopup) a).getList().getSelectedIndex());
                    }
                }
            });
        }

        protected void updateItem(int index) {
            if (isPopupVisible() && index >= 0) {
                E item = getItemAt(index);
                item.setSelected(!item.isSelected());
                setSelectedIndex(-1);
                setSelectedItem(item);
            }
        }

        @Override
        public void setPopupVisible(boolean v) {
            if (keepOpen) {
                keepOpen = false;
            } else {
                super.setPopupVisible(v);
            }
        }

        protected static <E extends CheckableItem> String getCheckedItemString(ListModel<E> model) {
            return IntStream.range(0, model.getSize())
                    .mapToObj(model::getElementAt)
                    .filter(CheckableItem::isSelected)
                    .map(Objects::toString)
                    .sorted()
                    .collect(Collectors.joining(", "));
        }
    }

    private void ohtaniahea() {
        jLabel8.setVisible(false);
        comboProveedor.setVisible(false);
        btnProveedorN.setVisible(false);
        lblMATERIAL.setVisible(false);
        btnHerramientaN.setVisible(false);
        lblHERRAMIENTA.setVisible(false);
        btnMaterialN.setVisible(false);

        jPanel1.revalidate();
        jPanel1.repaint();
    }

    class ProductoItem {

        private final int id;
        private final String nombre;

        public ProductoItem(int id, String nombre) {
            this.id = id;
            this.nombre = nombre;
        }

        public int getId() {
            return id;
        }

        @Override
        public String toString() {
            return nombre;
        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        txtPago = new com.toedter.calendar.JDateChooser();
        txtCantidadnuevo = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel2 = new javax.swing.JLabel();
        comboCategoria = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel7 = new javax.swing.JLabel();
        comboProveedor = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel8 = new javax.swing.JLabel();
        lblMATERIAL = new javax.swing.JLabel();
        btnProveedorN = new RSMaterialComponent.RSButtonShape();
        btnHerramientaN = new RSMaterialComponent.RSButtonShape();
        lblHERRAMIENTA = new javax.swing.JLabel();
        btnMaterialN = new RSMaterialComponent.RSButtonShape();
        btnGuardar1 = new rojeru_san.RSButtonRiple();
        btnCancelar2 = new rojeru_san.RSButtonRiple();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDetallenuevo = new javax.swing.JTextArea();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(504, 526));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Egreso");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 550, 50));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Detalle de Ingreso:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, 130, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Fecha Pago:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        txtPago.setBackground(new java.awt.Color(255, 255, 255));
        txtPago.setForeground(new java.awt.Color(255, 255, 255));
        txtPago.setDateFormatString("y-MM-d");
        txtPago.setMaxSelectableDate(new java.util.Date(253370786472000L));
        jPanel1.add(txtPago, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 230, 30));

        txtCantidadnuevo.setForeground(new java.awt.Color(46, 49, 82));
        txtCantidadnuevo.setColorMaterial(new java.awt.Color(46, 49, 82));
        txtCantidadnuevo.setPhColor(new java.awt.Color(46, 49, 82));
        txtCantidadnuevo.setPlaceholder("");
        txtCantidadnuevo.setSelectionColor(new java.awt.Color(46, 49, 82));
        jPanel1.add(txtCantidadnuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 230, 30));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Valor monetario:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));

        comboCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione categoria:", "Servicios Publicos", "Compra de Productos e Insumos", "Arriendo", "Nómina", "Gastos Administrativos", "Mercadeo y Publicidad", "Transporte", "Domicilios y Logistica", "mantenimineto y Reparaciones", "Muebles", "Equipos o Maquinaria", "Otros" }));
        comboCategoria.setColorMaterial(new java.awt.Color(0, 0, 0));
        comboCategoria.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        comboCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(comboCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 100, 240, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel7.setText("Categoria");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 70, -1, -1));

        comboProveedor.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione proveedor:" }));
        comboProveedor.setColorMaterial(new java.awt.Color(0, 0, 0));
        comboProveedor.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        comboProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboProveedorActionPerformed(evt);
            }
        });
        jPanel1.add(comboProveedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 240, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel8.setText("Proveedor:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

        lblMATERIAL.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lblMATERIAL.setText("Materiales:");
        jPanel1.add(lblMATERIAL, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 210, -1, -1));

        btnProveedorN.setBackground(new java.awt.Color(46, 49, 82));
        btnProveedorN.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnProveedorN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnProveedorN.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnProveedorN.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnProveedorN.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnProveedorN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnProveedorN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedorNActionPerformed(evt);
            }
        });
        jPanel1.add(btnProveedorN, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 140, 20, 20));

        btnHerramientaN.setBackground(new java.awt.Color(46, 49, 82));
        btnHerramientaN.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnHerramientaN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnHerramientaN.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnHerramientaN.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnHerramientaN.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnHerramientaN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnHerramientaN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHerramientaNActionPerformed(evt);
            }
        });
        jPanel1.add(btnHerramientaN, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 20, 20));

        lblHERRAMIENTA.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblHERRAMIENTA.setText("Herramientas:");
        jPanel1.add(lblHERRAMIENTA, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 280, -1, -1));

        btnMaterialN.setBackground(new java.awt.Color(46, 49, 82));
        btnMaterialN.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnMaterialN.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnMaterialN.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnMaterialN.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnMaterialN.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnMaterialN.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMaterialN.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMaterialNActionPerformed(evt);
            }
        });
        jPanel1.add(btnMaterialN, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, 20, 20));

        btnGuardar1.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnGuardar1.setText("Guardar");
        btnGuardar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnGuardar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 380, 140, -1));

        btnCancelar2.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/salida (1).png"))); // NOI18N
        btnCancelar2.setText("Volver");
        btnCancelar2.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnCancelar2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 380, 140, -1));

        txtDetallenuevo.setColumns(10);
        txtDetallenuevo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDetallenuevo.setLineWrap(true);
        txtDetallenuevo.setRows(1);
        txtDetallenuevo.setTabSize(1);
        txtDetallenuevo.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDetallenuevo);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 230, 50));

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 10, 280));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 551, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void comboCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboCategoriaActionPerformed
        String seleccion = comboCategoria.getSelectedItem().toString();
        boolean esCompraProductos = seleccion.equals("Compra de Productos e Insumos");

        jLabel8.setVisible(esCompraProductos);
        comboProveedor.setVisible(esCompraProductos);
        btnProveedorN.setVisible(esCompraProductos);
        btnMaterialN.setVisible(esCompraProductos);
        // Mostrar solo materiales o herramientas según necesidad
        lblMATERIAL.setVisible(esCompraProductos);
        btnHerramientaN.setVisible(esCompraProductos);
        lblHERRAMIENTA.setVisible(esCompraProductos);

        // Mostrar combobox según tipo de producto
        cmbMateriales.setVisible(esCompraProductos);
        cmbHerramientas.setVisible(esCompraProductos); // Cambiar según lógica de negocio

        jPanel1.revalidate();
        jPanel1.repaint();
    }//GEN-LAST:event_comboCategoriaActionPerformed

    private void btnProveedorNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedorNActionPerformed
        proveedornuevo dialog = new proveedornuevo(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

    }//GEN-LAST:event_btnProveedorNActionPerformed

    private void btnHerramientaNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHerramientaNActionPerformed
        herramientasNuevo dialog = new herramientasNuevo(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnHerramientaNActionPerformed

    private void comboProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboProveedorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboProveedorActionPerformed

    private void btnMaterialNActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMaterialNActionPerformed
        nuevoMateriales dialog = new nuevoMateriales(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnMaterialNActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        Connection con = null;
        try {
            // ===== 1. VALIDACIONES BÁSICAS =====
            if (txtPago.getDate() == null) {
                mostrarError("Debe seleccionar una fecha de pago");
                return;
            }

            if (txtCantidadnuevo.getText().trim().isEmpty()) {
                mostrarError("El monto es requerido");
                return;
            }

            if (txtDetallenuevo.getText().trim().isEmpty()) {
                mostrarError("La descripción es requerida");
                return;
            }

            if (comboCategoria.getSelectedIndex() == 0) {
                mostrarError("Debe seleccionar una categoría");
                return;
            }

            // ===== 2. OBTENER VALORES =====
            java.sql.Date fecha = new java.sql.Date(txtPago.getDate().getTime());
            double monto = Double.parseDouble(txtCantidadnuevo.getText().trim().replace(",", "."));
            String descripcion = txtDetallenuevo.getText();
            String categoria = comboCategoria.getSelectedItem().toString();

            // ===== 3. PREPARAR CONEXIÓN =====
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            // ===== 4. MANEJAR COMPRA DE PRODUCTOS =====
            if (categoria.equals("Compra de Productos e Insumos")) {
                // Validar proveedor
                if (comboProveedor.getSelectedIndex() <= 0) {
                    mostrarError("Debe seleccionar un proveedor");
                    return;
                }

                // Recolectar items seleccionados
                List<CheckableItem> seleccionados = new ArrayList<>();
                for (int i = 0; i < cmbMateriales.getModel().getSize(); i++) {
                    CheckableItem item = cmbMateriales.getModel().getElementAt(i);
                    if (item.isSelected()) {
                        seleccionados.add(item);
                    }
                }

                for (int i = 0; i < cmbHerramientas.getModel().getSize(); i++) {
                    CheckableItem item = cmbHerramientas.getModel().getElementAt(i);
                    if (item.isSelected()) {
                        seleccionados.add(item);
                    }
                }

                if (seleccionados.isEmpty()) {
                    mostrarError("Debe seleccionar al menos un producto");
                    return;
                }

                // Mostrar diálogo para cantidades
                EgresosMH formMH = new EgresosMH((Frame) this.getParent(), true, seleccionados, monto);
                formMH.setLocationRelativeTo(null);
                formMH.setVisible(true);

                if (formMH.isConfirmado()) {
                    // Insertar en caja
                    String sqlCaja = "INSERT INTO caja (fecha, movimiento, monto, descripcion, categoria) "
                            + "VALUES (?, 'egreso', ?, ?, ?)";
                    try (PreparedStatement ps = con.prepareStatement(sqlCaja)) {
                        ps.setDate(1, fecha);
                        ps.setDouble(2, monto);
                        ps.setString(3, descripcion);
                        ps.setString(4, categoria);
                        ps.executeUpdate();
                    }

                    con.commit();
                    this.dispose(); // Cerrar la ventana actual
                }
            } // ===== 5. OTRAS CATEGORÍAS =====
            else {
                // Insertar directamente en caja
                String sqlCaja = "INSERT INTO caja (fecha, movimiento, monto, descripcion, categoria) "
                        + "VALUES (?, 'egreso', ?, ?, ?)";
                try (PreparedStatement ps = con.prepareStatement(sqlCaja)) {
                    ps.setDate(1, fecha);
                    ps.setDouble(2, monto);
                    ps.setString(3, descripcion);
                    ps.setString(4, categoria);
                    ps.executeUpdate();
                }

                con.commit();
                mostrarMensaje("Registro guardado exitosamente");
                this.dispose(); // Cerrar la ventana actual
            }
        } catch (NumberFormatException e) {
            mostrarError("Formato de monto inválido. Use números con punto decimal (ej: 1500.50)");
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
                mostrarError("Error al guardar: " + e.getMessage());
            } catch (SQLException ex) {
                mostrarError("Error al revertir cambios: " + ex.getMessage());
            }
        } catch (Exception e) {
            mostrarError("Error inesperado: " + e.getMessage());
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }//GEN-LAST:event_btnGuardar1ActionPerformed
    private int obtenerIdProveedor(Connection con, String nombreProveedor) throws SQLException {
        String sql = "SELECT id_proveedor FROM proveedor WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreProveedor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_proveedor");
                }
            }
        }
        throw new SQLException("Proveedor no encontrado: " + nombreProveedor);
    }

    private int insertarSuministra(Connection con, int idInventario, int idProveedor) throws SQLException {
        String sql = "INSERT INTO suministra (inventario_id_inventario, proveedor_id_proveedor) VALUES (?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, idInventario);
            ps.setInt(2, idProveedor);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        throw new SQLException("No se pudo insertar en suministra");
    }

    private void actualizarInventario(Connection con, Map<String, String> cantidadesMateriales,
            Map<String, String> cantidadesHerramientas) throws SQLException {
        NumberFormat nf = NumberFormat.getNumberInstance(new Locale("es", "ES"));

        // Materiales
        for (Map.Entry<String, String> entry : cantidadesMateriales.entrySet()) {
            try {
                double cantidad = nf.parse(entry.getValue()).doubleValue();

                if (cantidad <= 0) {
                    throw new SQLException("La cantidad para " + entry.getKey() + " debe ser mayor que cero");
                }

                String sql = "UPDATE inventario SET cantidad = ? WHERE nombre = ? AND tipo = 'material'";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setDouble(1, cantidad);
                    ps.setString(2, entry.getKey());
                    ps.executeUpdate();
                }
            } catch (ParseException e) {
                throw new SQLException("Formato inválido para " + entry.getKey() + ": " + entry.getValue());
            }
        }

        // Herramientas
        for (Map.Entry<String, String> entry : cantidadesHerramientas.entrySet()) {
            try {
                double cantidad = nf.parse(entry.getValue()).doubleValue();

                if (cantidad <= 0) {
                    throw new SQLException("La cantidad para " + entry.getKey() + " debe ser mayor que cero");
                }

                String sql = "UPDATE inventario SET cantidad = ? WHERE nombre = ? AND tipo = 'herramienta'";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setDouble(1, cantidad);
                    ps.setString(2, entry.getKey());
                    ps.executeUpdate();
                }
            } catch (ParseException e) {
                throw new SQLException("Formato inválido para " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    private void mostrarError(String mensaje) {
        new Error_guardar(
                (Frame) this.getParent(),
                true,
                "Error",
                mensaje
        ).setVisible(true);
    }

    private void mostrarMensaje(String mensaje) {
        new Datos_guardados(
                (Frame) SwingUtilities.getWindowAncestor(this),
                true,
                "Éxito",
                mensaje
        ).setVisible(true);
    }


    private void btnCancelar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelar2ActionPerformed

    private void cargarProveedores() {
        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement("SELECT nombre FROM proveedor"); ResultSet rs = ps.executeQuery()) {
            comboProveedor.removeAllItems();
            comboProveedor.addItem("Seleccione un Proveedor:");
            while (rs.next()) {
                comboProveedor.addItem(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar proveedores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    try {
                        formuEgresos1 dialog = new formuEgresos1(new javax.swing.JFrame(), true);
                        dialog.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al abrir el formulario: " + e.getMessage(),
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple btnCancelar2;
    private rojeru_san.RSButtonRiple btnGuardar1;
    private RSMaterialComponent.RSButtonShape btnHerramientaN;
    private RSMaterialComponent.RSButtonShape btnMaterialN;
    private RSMaterialComponent.RSButtonShape btnProveedorN;
    private RSMaterialComponent.RSComboBoxMaterial comboCategoria;
    private RSMaterialComponent.RSComboBoxMaterial comboProveedor;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblHERRAMIENTA;
    private javax.swing.JLabel lblMATERIAL;
    private RSMaterialComponent.RSTextFieldMaterial txtCantidadnuevo;
    private javax.swing.JTextArea txtDetallenuevo;
    private com.toedter.calendar.JDateChooser txtPago;
    // End of variables declaration//GEN-END:variables

    private boolean insertarEgreso(java.sql.Date fecha, String descripcion, Double monto,
            String categoria, String proveedor, List<Integer> productosIds, double cantidad)
            throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            Integer idSuministra = null;
            if (categoria.equals("Compra de Productos e Insumos")
                    && productosIds != null && !productosIds.isEmpty()
                    && proveedor != null && !proveedor.isEmpty()) {
                int idProveedor = obtenerIdProveedor(con, proveedor);
                idSuministra = insertarSuministra(con, productosIds.get(0), idProveedor);
            }

            String sql = "INSERT INTO caja (fecha, movimiento, monto, cantidad, descripcion, suministra_idSuministra, categoria) "
                    + "VALUES (?, 'egreso', ?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setDate(1, fecha);
            ps.setDouble(2, monto);
            ps.setDouble(3, cantidad);
            ps.setString(4, descripcion);
            if (idSuministra != null) {
                ps.setInt(5, idSuministra);
            } else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            ps.setString(6, categoria);

            int resultado = ps.executeUpdate();
            con.commit();
            return resultado > 0;
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    private int obtenerIdInventario(Connection con, String nombreProducto) throws SQLException {
        String sql = "SELECT id_inventario FROM inventario WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreProducto);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_inventario");
                }
            }
        }
        throw new SQLException("Producto no encontrado: " + nombreProducto);
    }

    private void configurarFiltroNumerico() {
        ((AbstractDocument) txtCantidadnuevo.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (esNumeroValido(newText)) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (esNumeroValido(newText)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            private boolean esNumeroValido(String text) {
                // Permite números con un solo punto decimal o coma
                return text.matches("^[0-9]*([.,][0-9]*)?$");
            }
        });

        // Opcional: Agregar tooltip para guiar al usuario
        txtCantidadnuevo.setToolTipText("Solo se permiten números (ej: 10.5 o 10,5)");
    }
}
