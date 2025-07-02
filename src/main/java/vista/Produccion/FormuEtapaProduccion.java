/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Produccion;

import vista.alertas.Error_fecha;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelo.Conexion;

/**
 *
 * @author pc
 */
public class FormuEtapaProduccion extends javax.swing.JDialog {

    private CheckedComboBox<CheckableItem> cmbMateriales;
    private CheckedComboBox<CheckableItem> cmbHerramientas;
    private int idProduccion;

    /**
     * Creates new form EtapaProduccion
     */
    public FormuEtapaProduccion(Frame parent, boolean modal, int idProduccion) {
        super(parent, modal);
        this.idProduccion = idProduccion;
        initComponents();
        setLocationRelativeTo(parent);
        cargarTrabajadores();

        // Cargar materiales (tipo 'material')
        cmbMateriales = new CheckedComboBox<>(makeProductModel("material"));
        cmbMateriales.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPanel1.add(cmbMateriales, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 188, 200, 30));

        // Cargar herramientas (tipo 'herramienta')
        cmbHerramientas = new CheckedComboBox<>(makeProductModel("herramienta"));
        cmbHerramientas.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jPanel1.add(cmbHerramientas, new org.netbeans.lib.awtextra.AbsoluteConstraints(245, 267, 200, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 490, 480));
        configurarFiltroTexto();
        configurarFiltroNumerico();
    }

    private DefaultComboBoxModel<CheckableItem> makeProductModel(String tipo) {
        DefaultComboBoxModel<CheckableItem> model = new DefaultComboBoxModel<>();
        try {
            Connection con = new Conexion().getConnection();
            String sql = "SELECT nombre FROM inventario WHERE tipo = ? ";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, tipo);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                model.addElement(new CheckableItem(rs.getString("nombre"), false));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);

            // Mostrar mensaje de error al usuario
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
// Clases internas para el CheckedComboBox

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

    class CheckableItem {

        private final String text;
        private boolean selected;

        protected CheckableItem(String text, boolean selected) {
            this.text = text;
            this.selected = selected;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public String toString() {
            return text;
        }
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
        txtetapa = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel11 = new javax.swing.JLabel();
        txtFechainicio = new com.toedter.calendar.JDateChooser();
        txtfechafin = new com.toedter.calendar.JDateChooser();
        btnGuardar1 = new rojeru_san.RSButtonRiple();
        btnCancelar1 = new rojeru_san.RSButtonRiple();
        jLabel12 = new javax.swing.JLabel();
        txtcantidad = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel7 = new javax.swing.JLabel();
        BoxAsignado = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel8 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Etapa Produccion");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 50));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Fecha final:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Cantidad:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        txtetapa.setForeground(new java.awt.Color(0, 0, 0));
        txtetapa.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtetapa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtetapa.setPhColor(new java.awt.Color(0, 0, 0));
        txtetapa.setPlaceholder("");
        txtetapa.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtetapa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtetapaActionPerformed(evt);
            }
        });
        jPanel1.add(txtetapa, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 190, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setText("Fecha inicio:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        txtFechainicio.setBackground(new java.awt.Color(255, 255, 255));
        txtFechainicio.setForeground(new java.awt.Color(255, 255, 255));
        txtFechainicio.setDateFormatString("y-MM-d");
        txtFechainicio.setMaxSelectableDate(new java.util.Date(253370786472000L));
        jPanel1.add(txtFechainicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 190, 30));

        txtfechafin.setBackground(new java.awt.Color(255, 255, 255));
        txtfechafin.setForeground(new java.awt.Color(255, 255, 255));
        txtfechafin.setDateFormatString("y-MM-d");
        jPanel1.add(txtfechafin, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 190, 30));

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
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 430, 140, -1));

        btnCancelar1.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/salida (1).png"))); // NOI18N
        btnCancelar1.setText("Volver");
        btnCancelar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnCancelar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 430, 140, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setText("Nombre etapa:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        txtcantidad.setForeground(new java.awt.Color(0, 0, 0));
        txtcantidad.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtcantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcantidad.setPhColor(new java.awt.Color(0, 0, 0));
        txtcantidad.setPlaceholder("");
        txtcantidad.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtcantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcantidadActionPerformed(evt);
            }
        });
        jPanel1.add(txtcantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 190, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Heramientas:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 230, -1, -1));

        BoxAsignado.setForeground(new java.awt.Color(102, 102, 102));
        BoxAsignado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccionar", "pendiente", "proceso", "completado" }));
        BoxAsignado.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        BoxAsignado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoxAsignadoActionPerformed(evt);
            }
        });
        jPanel1.add(BoxAsignado, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, -1, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Asignado:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 70, -1, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setText("Materiales:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 160, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtetapaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtetapaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtetapaActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        try {
            if (!validarCampos()) {
                return;
            }

            List<String> materiales = obtenerSeleccionados(cmbMateriales);
            List<String> herramientas = obtenerSeleccionados(cmbHerramientas);

            FormularioMH dialog = new FormularioMH(
                    (Frame) this.getParent(),
                    true,
                    materiales,
                    herramientas
            );
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);

            if (dialog.isConfirmado()) {
                Connection con = null;
                try {
                    con = Conexion.getConnection();
                    con.setAutoCommit(false); // Iniciar transacción

                    // 1. Guardar etapa principal
                    int idEtapa = guardarEtapaProduccion(con);

                    // 2. Obtener el ID del detalle_pedido asociado a esta producción
                    int idDetallePedido = -1;
                    String sqlGetDetallePedido = "SELECT detalle_pedido_iddetalle_pedido FROM produccion WHERE id_produccion = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlGetDetallePedido)) {
                        ps.setInt(1, idProduccion);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                idDetallePedido = rs.getInt("detalle_pedido_iddetalle_pedido");
                            } else {
                                throw new SQLException("No se encontró el detalle de pedido asociado a esta producción");
                            }
                        }
                    }

                    // 3. Obtener el ID del pedido asociado al detalle_pedido
                    int idPedido = -1;
                    String sqlGetPedido = "SELECT pedido_id_pedido FROM detalle_pedido WHERE iddetalle_pedido = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlGetPedido)) {
                        ps.setInt(1, idDetallePedido);
                        try (ResultSet rs = ps.executeQuery()) {
                            if (rs.next()) {
                                idPedido = rs.getInt("pedido_id_pedido");
                            } else {
                                throw new SQLException("No se encontró el pedido asociado a este detalle de pedido");
                            }
                        }
                    }

                    // 4. Actualizar estado de producción a "proceso"
                    String sqlUpdateProduccion = "UPDATE produccion SET estado = 'proceso' WHERE id_produccion = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlUpdateProduccion)) {
                        ps.setInt(1, idProduccion);
                        ps.executeUpdate();
                    }

                    // 5. Actualizar estado del pedido vinculado a "proceso"
                    String sqlUpdatePedido = "UPDATE pedido SET estado = 'proceso' WHERE id_pedido = ?";
                    try (PreparedStatement ps = con.prepareStatement(sqlUpdatePedido)) {
                        ps.setInt(1, idPedido);
                        ps.executeUpdate();
                    }

                    // 6. Guardar materiales y actualizar inventario
                    guardarMaterialesHerramientas(con, idEtapa, dialog.getCantidadesMateriales(), "material");

                    // 7. Guardar herramientas y actualizar inventario
                    guardarMaterialesHerramientas(con, idEtapa, dialog.getCantidadesHerramientas(), "herramienta");

                    // 8. Guardar asignación de trabajador
                    guardarAsignado(con, idEtapa, BoxAsignado.getSelectedItem().toString());

                    con.commit(); // Confirmar transacción

                    // Mostrar mensaje de éxito con tu clase personalizada
                    new Datos_guardados(
                            (Frame) this.getParent(),
                            true,
                            "Éxito",
                            "Etapa guardada y producción/pedido marcados como 'proceso'"
                    ).setVisible(true);
                    this.dispose();
                } catch (SQLException | ParseException e) {
                    if (con != null) {
                        try {
                            con.rollback();
                        } catch (SQLException ex) {
                            Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    new Error_guardar((Frame) this.getParent(), true, "Error", "Error al guardar: " + e.getMessage()).setVisible(true);
                } finally {
                    if (con != null) {
                        try {
                            con.setAutoCommit(true);
                            con.close();
                        } catch (SQLException ex) {
                            Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            new Error_guardar((Frame) this.getParent(), true, "Error",
                    "La cantidad debe ser un número válido").setVisible(true);
        }

    }//GEN-LAST:event_btnGuardar1ActionPerformed
    private boolean guardarDatosCompletos(Map<String, String> cantidadesMateriales,
            Map<String, String> cantidadesHerramientas) throws ParseException {
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Guardar etapa principal
            int idEtapa = guardarEtapaProduccion(con);

            // 2. Guardar materiales y actualizar inventario
            guardarMaterialesHerramientas(con, idEtapa, cantidadesMateriales, "material");

            // 3. Guardar herramientas y actualizar inventario
            guardarMaterialesHerramientas(con, idEtapa, cantidadesHerramientas, "herramienta");

            // 4. Guardar asignación de trabajador
            guardarAsignado(con, idEtapa, BoxAsignado.getSelectedItem().toString());

            // 5. Actualizar estado del pedido a "en proceso"
            actualizarEstadoPedidoYProduccion(con, this.idProduccion);

            con.commit(); // Confirmar transacción

            // Mostrar mensaje de éxito con tu clase personalizada
            new Datos_guardados(
                    (Frame) this.getParent(),
                    true,
                    "Éxito",
                    "Etapa guardada y producción marcada como 'en proceso'"
            ).setVisible(true);

            return true;

        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
            }
            mostrarError("Error de base de datos: " + e.getMessage());
            return false;
        } finally {
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

// Método mejorado que actualiza tanto el pedido como la producción
    private void actualizarEstadoPedidoYProduccion(Connection con, int idProduccion) throws SQLException {
        // Primero obtenemos el id_pedido asociado a esta producción
        String sqlGetPedido = "SELECT pedido_id_pedido FROM produccion WHERE id_produccion = ?";
        int idPedido = -1;

        try (PreparedStatement ps = con.prepareStatement(sqlGetPedido)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idPedido = rs.getInt("pedido_id_pedido");
                } else {
                    throw new SQLException("No se encontró el pedido asociado a la producción " + idProduccion);
                }
            }
        }

        // Actualizamos el estado del pedido a "en proceso"
        String sqlUpdatePedido = "UPDATE pedido SET estado = 'en proceso' WHERE id_pedido = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlUpdatePedido)) {
            ps.setInt(1, idPedido);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo actualizar el estado del pedido " + idPedido);
            }
        }

        // También actualizamos el estado de la producción a "en proceso" por consistencia
        String sqlUpdateProduccion = "UPDATE produccion SET estado = 'en proceso' WHERE id_produccion = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlUpdateProduccion)) {
            ps.setInt(1, idProduccion);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo actualizar el estado de la producción " + idProduccion);
            }
        }
    }

// Método para mostrar errores usando tu clase personalizada
    private void mostrarError(String mensaje) {
        new Error_guardar(
                (Frame) this.getParent(),
                true,
                "Error",
                mensaje
        ).setVisible(true);
    }

// Método nuevo para actualizar el estado del pedido
    private void actualizarEstadoPedido(Connection con, int idProduccion) throws SQLException {
        // Primero obtenemos el id_pedido asociado a esta producción
        String sqlGetPedido = "SELECT pedido_id_pedido FROM produccion WHERE id_produccion = ?";
        int idPedido = -1;

        try (PreparedStatement ps = con.prepareStatement(sqlGetPedido)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    idPedido = rs.getInt("pedido_id_pedido");
                } else {
                    throw new SQLException("No se encontró el pedido asociado a la producción " + idProduccion);
                }
            }
        }

        // Actualizamos el estado del pedido a "en proceso"
        String sqlUpdate = "UPDATE pedido SET estado = 'proceso' WHERE id_pedido = ?";
        try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
            ps.setInt(1, idPedido);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("No se pudo actualizar el estado del pedido " + idPedido);
            }
        }
    }

    private int guardarEtapaProduccion(Connection con) throws SQLException {
        // Verifica que el ID de producción exista
        if (!existeProduccion(con, this.idProduccion)) {
            throw new SQLException("El ID de producción " + this.idProduccion + " no existe");
        }

        String sql = "INSERT INTO etapa_produccion (nombre_etapa, fecha_inicio, fecha_fin, produccion_id_produccion, cantidad) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, txtetapa.getText().trim());
            ps.setDate(2, new Date(txtFechainicio.getDate().getTime()));
            ps.setDate(3, txtfechafin.getDate() != null ? new Date(txtfechafin.getDate().getTime()) : null);
            ps.setInt(4, idProduccion); // Asegúrate que es 1 o 2
            ps.setInt(5, Integer.parseInt(txtcantidad.getText().trim()));
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                throw new SQLException("No se generó ID para la etapa");
            }
        }
    }

// Método auxiliar para verificar existencia
    private boolean existeProduccion(Connection con, int idProduccion) throws SQLException {
        String sql = "SELECT id_produccion FROM produccion WHERE id_produccion = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next(); // Retorna true si existe
            }
        }
    }

    private void guardarMaterialesHerramientas(Connection con, int idEtapa,
            Map<String, String> cantidades, String tipo)
            throws SQLException, ParseException {
        String sqlInsert = "INSERT INTO utilizado (etapa_produccion_idetapa_produccion, inventario_id_inventario, cantidad_usada) VALUES (?, ?, ?)";
        String sqlUpdate = "UPDATE inventario SET cantidad = ? WHERE id_inventario = ?";
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);

        for (Map.Entry<String, String> entry : cantidades.entrySet()) {
            String nombre = entry.getKey();
            String valor = entry.getValue();

            // Saltar si no hay cantidad
            if (valor == null || valor.trim().isEmpty() || valor.trim().equals("0") || valor.trim().equals("0,0") || valor.trim().equals("0,00")) {
                continue;
            }

            int idInventario = obtenerIdInventario(con, nombre, tipo);
            double cantidad;

            try {
                // Normalizar valor (reemplazar puntos por nada, coma por punto)
                String normalizedValor = valor.replace(".", "").replace(",", ".");
                cantidad = Double.parseDouble(normalizedValor);
                if (cantidad < 0) {
                    throw new NumberFormatException("La cantidad no puede ser negativa");
                }
            } catch (NumberFormatException e) {
                throw new SQLException("Valor inválido para cantidad: " + valor + " en " + nombre, e);
            }

            // Obtener cantidad actual del inventario
            String sqlSelect = "SELECT cantidad FROM inventario WHERE id_inventario = ?";
            String cantidadActualStr;
            try (PreparedStatement ps = con.prepareStatement(sqlSelect)) {
                ps.setInt(1, idInventario);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        cantidadActualStr = rs.getString("cantidad").trim();
                    } else {
                        throw new SQLException("No se encontró inventario para id: " + idInventario);
                    }
                }
            }

            // Parsear cantidad actual
            double cantidadActual = parseCantidad(cantidadActualStr);
            double nuevaCantidad = cantidadActual - cantidad;

            // Validar que no se consuma más de lo disponible
            if (nuevaCantidad < 0) {
                throw new SQLException("Cantidad insuficiente para " + nombre + ": disponible=" + cantidadActual + ", solicitada=" + cantidad);
            }

            // Formatear nueva cantidad como '12,50'
            String nuevaCantidadStr = numberFormat.format(nuevaCantidad);

            // Insertar en tabla utilizado
            try (PreparedStatement ps = con.prepareStatement(sqlInsert)) {
                ps.setInt(1, idEtapa);
                ps.setInt(2, idInventario);
                ps.setDouble(3, cantidad);
                ps.executeUpdate();
            }

            // Actualizar inventario
            try (PreparedStatement ps = con.prepareStatement(sqlUpdate)) {
                ps.setString(1, nuevaCantidadStr);
                ps.setInt(2, idInventario);
                ps.executeUpdate();
            }
        }
    }

// Reutilizar parseCantidad de FormularioMH
    private double parseCantidad(String cantidadStr) {
        try {
            String normalized = cantidadStr.replace(".", "").replace(",", ".");
            double cantidad = Double.parseDouble(normalized);
            if (cantidad < 0) {
                System.err.println("Cantidad negativa detectada: " + cantidadStr + " -> " + cantidad);
                return 0.0;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            System.err.println("Error al parsear cantidad: '" + cantidadStr + "' - " + e.getMessage());
            return 0.0;
        }
    }

    private int obtenerIdInventario(Connection con, String nombre, String tipo) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (!"material".equals(tipo) && !"herramienta".equals(tipo)) {
            throw new IllegalArgumentException("Tipo inválido: " + tipo);
        }

        String sql = "SELECT id_inventario FROM inventario WHERE nombre = ? AND tipo = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, tipo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_inventario");
                } else {
                    throw new SQLException("No se encontró el ítem en inventario: " + nombre + " (tipo: " + tipo + ")");
                }
            }
        }
    }

    private void guardarAsignado(Connection con, int idEtapa, String nombreUsuario) throws SQLException {
        if (nombreUsuario.equals("Seleccionar")) {
            return; // No guardar si no se seleccionó usuario
        }
        String sql = "INSERT INTO asignada (etapa_produccion_idetapa_produccion, usuario_id_usuario) VALUES (?, ?)";
        int idUsuario = obtenerIdUsuario(con, nombreUsuario);

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idEtapa);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    private int obtenerIdUsuario(Connection con, String nombreUsuario) throws SQLException {
        String sql = "SELECT id_usuario FROM usuario WHERE nombre = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_usuario");
            } else {
                throw new SQLException("Usuario no encontrado: " + nombreUsuario);
            }
        }
    }
//Métodos auxiliares (actualizados)

    private boolean validarCampos() {
        if (txtetapa.getText().trim().isEmpty()
                || txtFechainicio.getDate() == null
                || txtfechafin.getDate() == null
                || txtcantidad.getText().trim().isEmpty()) {
            espacio_alerta errorDialog = new espacio_alerta(
                    (Frame) this.getParent(),
                    true,
                    "Error",
                    "Todos los campos son obligatorios"
            );
            errorDialog.setLocationRelativeTo(null);
            errorDialog.setVisible(true);
            return false;
        }

        if (txtfechafin.getDate().before(txtFechainicio.getDate())) {
            Error_fecha errorFecha = new Error_fecha(
                    (Frame) this.getParent(),
                    true,
                    "Error",
                    "La fecha final no puede ser anterior a la inicial"
            );
            errorFecha.setLocationRelativeTo(null);
            errorFecha.setVisible(true);
            return false;
        }

        return true;
    }
    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void txtcantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcantidadActionPerformed

    private void BoxAsignadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoxAsignadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_BoxAsignadoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            FormuEtapaProduccion dialog = new FormuEtapaProduccion(new javax.swing.JFrame(), true, 1); // ID de prueba
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSComboBoxMaterial BoxAsignado;
    private rojeru_san.RSButtonRiple btnCancelar1;
    private rojeru_san.RSButtonRiple btnGuardar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private com.toedter.calendar.JDateChooser txtFechainicio;
    private RSMaterialComponent.RSTextFieldMaterial txtcantidad;
    private RSMaterialComponent.RSTextFieldMaterial txtetapa;
    private com.toedter.calendar.JDateChooser txtfechafin;
    // End of variables declaration//GEN-END:variables

    private void cargarTrabajadores() {
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
        model.addElement("Seleccionar");

        try {
            Connection con = new Conexion().getConnection();
            String sql = "SELECT nombre FROM usuario WHERE rol = 'trabajador'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                model.addElement(rs.getString("nombre"));
            }
            con.close();
        } catch (SQLException ex) {
            Logger.getLogger(FormuEtapaProduccion.class.getName()).log(Level.SEVERE, null, ex);
        }

        BoxAsignado.setModel(model);
    }

    private void configurarFiltroTexto() {
        ((AbstractDocument) txtetapa.getDocument()).setDocumentFilter(new LetterFilter());

        // También puedes agregar un tooltip para informar al usuario
        txtetapa.setToolTipText("Este campo solo acepta letras y espacios");
    }

    public class LetterFilter extends DocumentFilter {

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null) {
                return;
            }

            if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) {
                return;
            }

            if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }

    private void configurarFiltroNumerico() {
        ((AbstractDocument) txtcantidad.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr)
                    throws BadLocationException {
                String newText = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if (esNumeroValido(newText)) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
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
        txtcantidad.setToolTipText("Solo se permiten números (ej: 10.5 o 10,5)");
    }
}
