/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.Produccion;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Frame;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Conexion;
import vista.TemaManager;

/**
 *
 * @author pc
 */
public final class Produccion extends javax.swing.JPanel {

    private java.awt.Frame parent;
    private int idProduccion;

    /**
     * Creates new form produccionContenido
     */
    public Produccion(JFrame jFrame, boolean par) {
        initComponents();
        aplicarTema();
        Tabla1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Configura el modelo de tabla correctamente
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"id produccion", "Codigo Pedido", "Nombre", "Cliente", "Fecha inicio", "Fecha Final", "Cantidad", "Estado", "Detalle", "Dimensiones"}
        ) {

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return String.class;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        Tabla1.setModel(model);

        // Oculta las columnas adicionales después de establecer el modelo
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(9)); // Oculta Dimensiones
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(0)); // Oculta Dimensiones

        // Configura el renderizador especial para la columna de estado (sobrescribe el general)
        Tabla1.getColumnModel().getColumn(6).setCellRenderer(new EstadoTableCellRenderer());

        // Configura el renderizador especial para la columna "Ver" (sobrescribe el general)
        Tabla1.getColumnModel().getColumn(7).setCellRenderer(new VerTableCellRenderer());
        // Configura el buscador
        txtbuscar.getDocument().addDocumentListener(new BuscadorDocumentListener());
        // Ajustar el ancho de la columna
        TableColumn cantidadColumn = Tabla1.getColumnModel().getColumn(7);
        cantidadColumn.setPreferredWidth(10);

        TableColumn cantidadColumn2 = Tabla1.getColumnModel().getColumn(0);
        cantidadColumn2.setPreferredWidth(8);
        // Carga los datos
        cargarTablaProduccion();
        TemaManager.getInstance().addThemeChangeListener(() -> {
            aplicarTema(); // Update theme when it changes
        });
    }

    public void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            // Configuración para modo oscuro
            Color fondo = new Color(21, 21, 33);
            Color fondoTabla = new Color(30, 30, 45);
            Color encabezado = new Color(67, 71, 120);
            Color texto = Color.WHITE;

            // Panel principal
            jPanel1.setBackground(fondo);

            // Campo de búsqueda
            txtbuscar.setBackground(new Color(37, 37, 52));
            txtbuscar.setForeground(texto);
            txtbuscar.setColorIcon(texto);
            txtbuscar.setPhColor(Color.LIGHT_GRAY);

            // Configuración COMPLETA de la tabla
            Tabla1.setBackground(fondoTabla);
            Tabla1.setForeground(texto);

            // Configuración de filas
            Tabla1.setColorPrimary(new Color(37, 37, 52));  // Filas impares
            Tabla1.setColorSecondary(new Color(30, 30, 45)); // Filas pares
            Tabla1.setColorPrimaryText(texto);
            Tabla1.setColorSecundaryText(texto);

            // Encabezados
            Tabla1.setBackgoundHead(encabezado);
            Tabla1.setForegroundHead(texto);
            Tabla1.setColorBorderHead(encabezado);

            // Selección y hover
            Tabla1.setSelectionBackground(new Color(67, 71, 120));
            Tabla1.setBackgoundHover(new Color(40, 50, 90));

            // Bordes y grid
            Tabla1.setColorBorderRows(new Color(60, 60, 60));
            Tabla1.setGridColor(new Color(80, 80, 80));
            Tabla1.setShowGrid(true);

            // Fuentes
            Tabla1.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Tabla1.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));

            // Efectos
            Tabla1.setEffectHover(true);

            // Botones
            btnElimi.setBackground(encabezado);
            btnElimi.setBackgroundHover(new Color(118, 142, 240));

        } else {
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;
            Color primario = new Color(72, 92, 188);

            jPanel1.setBackground(fondo);
            txtbuscar.setBackground(fondo);
            txtbuscar.setForeground(texto);
            txtbuscar.setColorIcon(texto);
            txtbuscar.setPhColor(Color.GRAY);

            Tabla1.setBackground(new Color(255, 255, 255));
            Tabla1.setBackgoundHead(new Color(46, 49, 82));
            Tabla1.setForegroundHead(Color.WHITE);
            Tabla1.setBackgoundHover(new Color(67, 150, 209));
            Tabla1.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Tabla1.setColorPrimary(new Color(242, 242, 242));
            Tabla1.setColorPrimaryText(texto);
            Tabla1.setColorSecondary(new Color(255, 255, 255));
            Tabla1.setColorSecundaryText(texto);
            Tabla1.setColorBorderHead(primario);
            Tabla1.setColorBorderRows(new Color(0, 0, 0));
            Tabla1.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setEffectHover(true);
            Tabla1.setSelectionBackground(new Color(67, 150, 209));
            Tabla1.setShowGrid(true);
            Tabla1.setGridColor(Color.WHITE); // o el color que desees
            Tabla1.setBackground(Color.WHITE);
            Tabla1.setColorPrimary(new Color(242, 242, 242)); // Fondo filas impares
            Tabla1.setColorSecondary(Color.WHITE); // Fondo filas pares
            Tabla1.setForeground(Color.BLACK);
            btnElimi.setBackground(new Color(46, 49, 82));
        }
        Tabla1.repaint();
        Tabla1.getTableHeader().repaint();
    }
// Clase del renderizador

    private class EditarTableCellRenderer extends DefaultTableCellRenderer {

        private final Font fontNormal = new Font("Tahoma", Font.PLAIN, 14);
        private final Font fontBold = new Font("Tahoma", Font.BOLD, 14);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            boolean oscuro = TemaManager.getInstance().isOscuro();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Configuración basada en el tema
            if (oscuro) {
                if (isSelected) {
                    c.setBackground(new Color(67, 71, 120)); // Seleccionado
                    c.setForeground(Color.WHITE);
                } else {
                    // Alternar colores para filas pares/impares
                    c.setBackground(row % 2 == 0 ? new Color(37, 37, 52) : new Color(30, 30, 45));
                    c.setForeground(Color.WHITE);
                }
            } else {
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(242, 242, 242) : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
            }
            setHorizontalAlignment(CENTER);
            setText("Editar");
            setBorder(BorderFactory.createLineBorder(oscuro ? new Color(153, 153, 153) : new Color(153, 153, 153), 1));
            setFont(isSelected ? fontBold : fontNormal);

            return c;
        }
    }
// Renderizador para la columna de estado

    private class EstadoTableCellRenderer extends DefaultTableCellRenderer {

        public EstadoTableCellRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            boolean oscuro = TemaManager.getInstance().isOscuro();
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            label.setHorizontalAlignment(CENTER);
            label.setText(value != null ? value.toString() : "");

            if (isSelected) {
                label.setForeground(oscuro ? Color.WHITE : Color.BLACK);
                label.setBackground(oscuro ? new Color(67, 71, 120) : table.getSelectionBackground());
            } else {
                label.setForeground(oscuro ? Color.WHITE : Color.BLACK);

                String estado = value != null ? value.toString() : "";
                if (oscuro) {
                    switch (estado.toLowerCase()) {
                        case "pendiente":
                            label.setBackground(new Color(153, 0, 51)); // Rojo oscuro
                            break;
                        case "proceso":
                            label.setBackground(new Color(251, 139, 36)); // Amarillo oscuro
                            break;
                        case "finalizado":
                            label.setBackground(new Color(31, 123, 21)); // Verde oscuro
                            break;
                        default:
                            label.setBackground(new Color(37, 37, 52));
                            break;
                    }
                } else {
                    switch (estado.toLowerCase()) {
                        case "pendiente":
                            label.setBackground(new Color(255, 204, 204)); // Rojo claro
                            break;
                        case "proceso":
                            label.setBackground(new Color(255, 255, 153)); // Amarillo claro
                            break;
                        case "finalizado":
                            label.setBackground(new Color(204, 255, 204)); // Verde claro
                            break;
                        default:
                            label.setBackground(Color.WHITE);
                            break;
                    }
                }
            }

            label.setBorder(BorderFactory.createLineBorder(oscuro ? new Color(153, 153, 153) : new Color(153, 153, 153), 1));
            return label;
        }
    }

// Renderizador para la columna "Ver"
    private class VerTableCellRenderer extends DefaultTableCellRenderer {

        private final Font fontNormal = new Font("Tahoma", Font.PLAIN, 14);
        private final Font fontBold = new Font("Tahoma", Font.BOLD, 14);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            boolean oscuro = TemaManager.getInstance().isOscuro();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
// Configuración basada en el tema
            if (oscuro) {
                if (isSelected) {
                    c.setBackground(new Color(67, 71, 120)); // Seleccionado
                    c.setForeground(Color.WHITE);
                } else {
                    // Alternar colores para filas pares/impares
                    c.setBackground(row % 2 == 0 ? new Color(37, 37, 52) : new Color(30, 30, 45));
                    c.setForeground(Color.WHITE);
                }
            } else {
                if (isSelected) {
                    c.setBackground(table.getSelectionBackground());
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(242, 242, 242) : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }

            }

            setHorizontalAlignment(CENTER);
            setText("Ver");
            setBorder(BorderFactory.createLineBorder(oscuro ? new Color(153, 153, 153) : new Color(153, 153, 153), 1));
            setFont(isSelected ? fontBold : fontNormal);

            return c;
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     *
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtbuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        btnElimi = new RSMaterialComponent.RSButtonShape();
        jScrollPane4 = new javax.swing.JScrollPane();
        Tabla1 = new RSMaterialComponent.RSTableMetroCustom();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1250, 630));

        jPanel1.setBackground(new java.awt.Color(242, 247, 255));

        txtbuscar.setBackground(new java.awt.Color(245, 245, 245));
        txtbuscar.setForeground(new java.awt.Color(29, 30, 91));
        txtbuscar.setColorIcon(new java.awt.Color(29, 30, 111));
        txtbuscar.setColorMaterial(new java.awt.Color(29, 30, 111));
        txtbuscar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
        txtbuscar.setPlaceholder("Buscar");
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });

        btnElimi.setBackground(new java.awt.Color(46, 49, 82));
        btnElimi.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnElimi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete (1).png"))); // NOI18N
        btnElimi.setText(" Eliminar");
        btnElimi.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnElimi.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        btnElimi.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnElimi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElimiActionPerformed(evt);
            }
        });

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Nombre", "Fecha inicio", "Fecha final", "Estado", "Detalle"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setBackgoundHead(new java.awt.Color(46, 49, 82));
        Tabla1.setBackgoundHover(new java.awt.Color(109, 160, 221));
        Tabla1.setBorderHead(null);
        Tabla1.setBorderRows(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        Tabla1.setColorBorderHead(new java.awt.Color(46, 49, 82));
        Tabla1.setColorBorderRows(new java.awt.Color(46, 49, 82));
        Tabla1.setColorPrimaryText(new java.awt.Color(0, 0, 0));
        Tabla1.setColorSecondary(new java.awt.Color(255, 255, 255));
        Tabla1.setColorSecundaryText(new java.awt.Color(0, 0, 0));
        Tabla1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla1.setFontHead(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tabla1.setFontRowHover(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        Tabla1.setFontRowSelect(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        Tabla1.setPreferredSize(new java.awt.Dimension(450, 499));
        Tabla1.setRowHeight(23);
        Tabla1.setSelectionBackground(new java.awt.Color(109, 160, 221));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(Tabla1);
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnElimi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1211, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnElimi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(97, 97, 97))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 646, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        filtrarTabla();
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void btnElimiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimiActionPerformed
        int[] selectedRows = Tabla1.getSelectedRows();

        if (selectedRows.length == 0) {
            new Error_eliminar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Advertencia",
                    "Por favor seleccione al menos una fila para eliminar",
                    "/warning-triangle-sign-free-vector-removebg-preview.png"
            ).setVisible(true);
            return;
        }

        // Usar tu diálogo personalizado en lugar de JOptionPane
        alertaEliminar confirmDialog = new alertaEliminar(
                (Frame) SwingUtilities.getWindowAncestor(this),
                true,
                "Confirmar eliminación",
                "¿Está seguro que desea eliminar los " + selectedRows.length + " registros seleccionados?",
                "/warning-triangle-sign-free-vector-removebg-preview.png"
        );

        confirmDialog.setVisible(true);

        if (!confirmDialog.confirmarEliminar()) {
            return; // Si el usuario cancela, no hacer nada
        }

        // Resto del código de eliminación...
        try (Connection con = new Conexion().getConnection()) {
            String sql = "DELETE FROM produccion WHERE id_produccion = ?";
            DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();

            for (int i = selectedRows.length - 1; i >= 0; i--) {
                int row = selectedRows[i];
                int id = (int) model.getValueAt(row, 0);

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ps.executeUpdate();
                    model.removeRow(row);
                }
            }

            new alertaEliminar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Éxito",
                    "Registros eliminados correctamente",
                    "/success-icon.png" // Cambia por tu icono de éxito
            ).setVisible(true);

        } catch (SQLException e) {
            new alertaEliminar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Error al eliminar: " + e.getMessage(),
                    "/error-icon.png" // Cambia por tu icono de error
            ).setVisible(true);
        }
    }//GEN-LAST:event_btnElimiActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try {
            int column = Tabla1.columnAtPoint(evt.getPoint());
            int viewRow = Tabla1.rowAtPoint(evt.getPoint());

            if (viewRow < 0 || column < 0) {
                return;
            }

            if (column == 7) { // Columna "Ver Detalle"
                int modelRow = Tabla1.convertRowIndexToModel(viewRow);
                DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();

                // Obtener el ID de producción directamente de la columna 0
                int idProduccion = Integer.parseInt(model.getValueAt(modelRow, 0).toString());

                // Llamar al método con solo el ID de producción
                mostrarDetalleProduccion(idProduccion);
            }
        } catch (Exception e) {
            new Error_guardar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Error al procesar clic: " + e.getMessage()
            ).setVisible(true);
            e.printStackTrace();
        }
    }

// Método auxiliar para obtener el ID de producción con validación
    private int obtenerIdProduccion(DefaultTableModel model, int modelRow) {
        try {
            Object idObj = model.getValueAt(modelRow, 0);
            int id = Integer.parseInt(idObj.toString());

            if (id <= 0) {
                new Error_guardar(
                        (Frame) SwingUtilities.getWindowAncestor(this),
                        true,
                        "Error",
                        "ID de producción no válido"
                ).setVisible(true);
                return -1;
            }
            return id;

        } catch (NumberFormatException e) {
            new Error_guardar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Formato de ID inválido"
            ).setVisible(true);
            return -1;
        }
    }

    private int obtenerIdProduccionDesdeBD(int idPedido) throws SQLException {
        String sql = "SELECT p.id_produccion "
                + "FROM produccion p "
                + "JOIN detalle_pedido dp ON p.detalle_pedido_iddetalle_pedido = dp.iddetalle_pedido "
                + "JOIN pedido ped ON dp.pedido_id_pedido = ped.id_pedido "
                + "WHERE ped.id_pedido = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPedido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getInt("id_produccion") : -1;
            }
        }
    }

    private int obtenerIdDetallePedido(int idProduccion) throws SQLException {
        String sql = "SELECT detalle_pedido_iddetalle_pedido FROM produccion WHERE id_produccion = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("detalle_pedido_iddetalle_pedido");
                } else {
                    throw new SQLException("No se encontró detalle de pedido para la producción: " + idProduccion);
                }
            }
        }
    }

// Método para mostrar el detalle de producción
    private void mostrarDetalleProduccion(int idProduccion) {
        try {
            if (idProduccion <= 0) {
                throw new IllegalArgumentException("ID de producción inválido: " + idProduccion);
            }

            String sql = "SELECT p.id_produccion, dp.descripcion, "
                    + "CONCAT(c.nombre, ' ', c.apellido) AS cliente, "
                    + "p.fecha_inicio, p.fecha_fin, p.estado, "
                    + "dp.cantidad, dp.dimension "
                    + "FROM produccion p "
                    + "JOIN detalle_pedido dp ON p.detalle_pedido_iddetalle_pedido = dp.iddetalle_pedido "
                    + "JOIN pedido ped ON dp.pedido_id_pedido = ped.id_pedido "
                    + "LEFT JOIN cliente c ON ped.cliente_codigo = c.codigo "
                    + "WHERE p.id_produccion = ?";

            try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idProduccion);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                        String nombre = rs.getString("descripcion");
                        String cliente = rs.getString("cliente");
                        String fechaInicio = sdf.format(rs.getDate("fecha_inicio"));
                        String fechaFin = rs.getDate("fecha_fin") != null
                                ? sdf.format(rs.getDate("fecha_fin")) : "En proceso";
                        String estado = rs.getString("estado");
                        String cantidad = String.valueOf(rs.getInt("cantidad"));
                        String dimensiones = rs.getString("dimension");

                        DetalleProduProducto detallePanel = new DetalleProduProducto(
                                idProduccion, nombre, fechaInicio, fechaFin,
                                estado, cantidad, dimensiones, cliente
                        );

                        removeAll();
                        setLayout(new BorderLayout());
                        add(detallePanel, BorderLayout.CENTER);
                        revalidate();
                        repaint();
                    } else {
                        throw new SQLException("No se encontraron datos para la producción ID: " + idProduccion);
                    }
                }
            }
        } catch (Exception e) {
            new Error_guardar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Error al mostrar detalle: " + e.getMessage()
            ).setVisible(true);
            e.printStackTrace();
        }
    }

    private String obtenerDimensionesDeBD(int idProduccion) {
        String sql = "SELECT dp.dimension FROM produccion p "
                + "JOIN detalle_pedido dp ON p.detalle_pedido_iddetalle_pedido = dp.iddetalle_pedido "
                + "WHERE p.id_produccion = ?";

        try (Connection con = new Conexion().getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProduccion);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getString("dimension") : "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

// Métodos auxiliares para obtener valores de celdas con valores por defecto
    private String obtenerValorCelda(DefaultTableModel model, int row, int col) {
        return obtenerValorCelda(model, row, col, "");
    }

    private String obtenerValorCelda(DefaultTableModel model, int row, int col, String valorPorDefecto) {
        Object value = model.getValueAt(row, col);
        return (value != null) ? value.toString() : valorPorDefecto;
    }

    private int obtenerValorCeldaEntero(DefaultTableModel model, int row, int col) {
        try {
            Object value = model.getValueAt(row, col);
            return (value != null) ? Integer.parseInt(value.toString()) : 0;
        } catch (NumberFormatException e) {
            return 0;
        }
    }//GEN-LAST:event_Tabla1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSTableMetroCustom Tabla1;
    private RSMaterialComponent.RSButtonShape btnElimi;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane4;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtbuscar;
    // End of variables declaration//GEN-END:variables

    private void filtrarTabla() {
        String textoBusqueda = txtbuscar.getText().trim().toLowerCase();
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
        Tabla1.setRowSorter(tr);

        if (textoBusqueda.isEmpty()) {
            tr.setRowFilter(null);
            return;
        }

        // Filtro para buscar en todas las columnas
        tr.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));

    }

    public void cargarTablaProduccion() {
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        model.setRowCount(0);

        try (Connection con = new Conexion().getConnection()) {
            String sql = "SELECT p.id_produccion, dp.descripcion, "
                    + // Cambiado a id_produccion como primer campo
                    "CONCAT(c.nombre, ' ', c.apellido) AS cliente, "
                    + "p.fecha_inicio, p.fecha_fin, p.estado, "
                    + "dp.cantidad, dp.dimension, ped.num_pedido "
                    + // Añadido id_pedido al final
                    "FROM produccion p "
                    + "JOIN detalle_pedido dp ON p.detalle_pedido_iddetalle_pedido = dp.iddetalle_pedido "
                    + "JOIN pedido ped ON dp.pedido_id_pedido = ped.id_pedido "
                    + "LEFT JOIN cliente c ON ped.cliente_codigo = c.codigo "
                    + "ORDER BY p.estado ASC";

            try (PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("id_produccion"), // Mostrar id_produccion en la columna 0
                        rs.getString("num_pedido"), // Mostrar id_produccion en la columna 0
                        rs.getString("descripcion"),
                        rs.getString("cliente") != null ? rs.getString("cliente") : "Sin cliente",
                        sdf.format(rs.getDate("fecha_inicio")),
                        rs.getDate("fecha_fin") != null ? sdf.format(rs.getDate("fecha_fin")) : "En proceso",
                        rs.getInt("cantidad"),
                        rs.getString("estado"),
                        "ver",
                        rs.getString("dimension")
                    });
                }
            }
        } catch (SQLException e) {
            new Error_guardar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Error al cargar datos: " + e.getMessage()
            ).setVisible(true);
            e.printStackTrace();
        }
    }

    private class BuscadorDocumentListener implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            filtrarTabla();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            filtrarTabla();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            filtrarTabla();
        }

        private void filtrarTabla() {
            String texto = txtbuscar.getText().trim().toLowerCase();
            DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
            TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
            Tabla1.setRowSorter(tr);

            if (texto.isEmpty()) {
                tr.setRowFilter(null);
            } else {
                tr.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
            }
        }
    }

}
