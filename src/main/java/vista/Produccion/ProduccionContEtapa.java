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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
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
public final class ProduccionContEtapa extends javax.swing.JPanel {

    private int idProduccion;

    /**
     * Creates new form ProduccionContDetalle
     *
     * @param idProduccion
     */
    public ProduccionContEtapa(int idProduccion) {
        this.idProduccion = idProduccion;
        System.out.println("ID de producción recibido: " + idProduccion);

        initComponents();

        // Configuración básica de la tabla
        Tabla1.setAutoCreateRowSorter(true);
        Tabla1.setRowHeight(25);
        Tabla1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Verificar conexión
        testConexion();

        // Cargar datos
        cargarTablaEtapa();

        aplicarTema();
    }

    private void testConexion() {
        try {
            Connection testCon = Conexion.getConnection();
            System.out.println("Conexión exitosa: " + testCon);
            testCon.close();
        } catch (SQLException e) {
            System.err.println("Error de conexión: " + e.getMessage());
        }
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
            btnNuevo.setBackground(encabezado);
            btnNuevo.setBackgroundHover(new Color(118, 142, 240));
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
            btnNuevo.setBackground(new Color(46, 49, 82));
            btnElimi.setBackground(new Color(46, 49, 82));
        }
        Tabla1.repaint();
        Tabla1.getTableHeader().repaint();
    }

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
    // Renderizador para la columna de estado

    private void mostrarDetalleEtapa(DefaultTableModel model, int modelRow, int idEtapa) {
        try {
            // Obtener datos básicos de la fila seleccionada
            String nombre = model.getValueAt(modelRow, 1).toString();
            String cantidad = String.valueOf(model.getValueAt(modelRow, 2));
            String fechaInicio = model.getValueAt(modelRow, 3).toString();
            String fechaFin = model.getValueAt(modelRow, 4).toString();
            String estado = model.getValueAt(modelRow, 5).toString();
            String asignado = model.getValueAt(modelRow, 6) != null
                    ? model.getValueAt(modelRow, 6).toString() : "No asignado";

            // Consultar cantidades de materiales y herramientas desde la base de datos
            Map<String, String> cantidadesMateriales = obtenerCantidadesMateriales(idEtapa);
            Map<String, String> cantidadesHerramientas = obtenerCantidadesHerramientas(idEtapa);

            // Crear el diálogo
            DetallleEtapa dialog = new DetallleEtapa(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    true,
                    idEtapa,
                    nombre,
                    cantidad,
                    fechaInicio,
                    fechaFin,
                    estado,
                    cantidadesMateriales, // Pasar Map en lugar de String
                    cantidadesHerramientas, // Pasar Map en lugar de String
                    asignado
            );

            // Centrar y mostrar el diálogo
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar detalle: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private Map<String, String> obtenerCantidadesMateriales(int idEtapa) {
        Map<String, String> cantidades = new HashMap<>();
        try (Connection con = Conexion.getConnection()) {
            String sql = "SELECT i.nombre, ut.cantidad_usada "
                    + "FROM utilizado ut "
                    + "JOIN inventario i ON ut.inventario_id_inventario = i.id_inventario "
                    + "WHERE ut.etapa_produccion_idetapa_produccion = ? AND i.tipo = 'material'";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idEtapa);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cantidades.put(rs.getString("nombre"), rs.getString("cantidad_usada"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener materiales: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return cantidades;
    }

    private Map<String, String> obtenerCantidadesHerramientas(int idEtapa) {
        Map<String, String> cantidades = new HashMap<>();
        try (Connection con = Conexion.getConnection()) {
            String sql = "SELECT i.nombre, ut.cantidad_usada "
                    + "FROM utilizado ut "
                    + "JOIN inventario i ON ut.inventario_id_inventario = i.id_inventario "
                    + "WHERE ut.etapa_produccion_idetapa_produccion = ? AND i.tipo = 'herramienta'";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idEtapa);
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        cantidades.put(rs.getString("nombre"), rs.getString("cantidad_usada"));
                    }
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Error al obtener herramientas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return cantidades;
    }

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
                        case "completado":
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
                        case "completado":
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
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnNuevo = new RSMaterialComponent.RSButtonShape();
        txtbuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        btnElimi = new RSMaterialComponent.RSButtonShape();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new RSMaterialComponent.RSTableMetroCustom();

        setBackground(new java.awt.Color(242, 247, 255));
        setMaximumSize(new java.awt.Dimension(1150, 510));
        setMinimumSize(new java.awt.Dimension(1150, 510));
        setPreferredSize(new java.awt.Dimension(1150, 510));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(242, 247, 255));

        btnNuevo.setBackground(new java.awt.Color(46, 49, 82));
        btnNuevo.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (1).png"))); // NOI18N
        btnNuevo.setText("  Nuevo");
        btnNuevo.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnNuevo.setFont(new java.awt.Font("Roboto Bold", 1, 18)); // NOI18N
        btnNuevo.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

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
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Nombre", "Cantidad", "Fecha inicio", "Fecha final", "Estado", "Material", "Herramienta", "Asignado"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
        Tabla1.setPreferredSize(new java.awt.Dimension(600, 310));
        Tabla1.setRowHeight(23);
        Tabla1.setSelectionBackground(new java.awt.Color(109, 160, 221));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla1);
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(436, 436, 436)
                        .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)
                        .addComponent(btnElimi, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addComponent(txtbuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnElimi, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(153, Short.MAX_VALUE))
        );

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 630));
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        filtrarTabla();

    }//GEN-LAST:event_txtbuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        FormuEtapaProduccion dialog = new FormuEtapaProduccion(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                true,
                this.idProduccion
        );
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        cargarTablaEtapa(); // Refrescar la tabla después de cerrar el diálogo
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnElimiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElimiActionPerformed
        int selectedRow = Tabla1.getSelectedRow();
        if (selectedRow == -1) {
            new Error_eliminar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Seleccione una etapa para eliminar",
                    "/warning-triangle-sign-free-vector-removebg-preview.png"
            ).setVisible(true);
            return;
        }

        int modelRow = Tabla1.convertRowIndexToModel(selectedRow);
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        int idEtapa = obtenerIdEtapa(model, modelRow);

        if (idEtapa == -1) {
            return;
        }

        // Usar tu diálogo personalizado
        alertaEliminarEtapa confirmDialog = new alertaEliminarEtapa(
                (Frame) SwingUtilities.getWindowAncestor(this),
                true,
                "Confirmar eliminación",
                "¿Está seguro de eliminar esta etapa? Esto devolverá los materiales al inventario.",
                "/warning-triangle-sign-free-vector-removebg-preview.png"
        );

        confirmDialog.setVisible(true);

        if (!confirmDialog.confirmarEliminar()) {
            return;
        }

        if (eliminarEtapa(idEtapa)) {
            new DatosActualizados(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Éxito",
                    "Etapa eliminada correctamente",
                    "/success-icon.png"
            ).setVisible(true);
            cargarTablaEtapa();
        } else {
            new DatosActualizados(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Error al eliminar la etapa",
                    "/error-icon.png"
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

            int modelRow = Tabla1.convertRowIndexToModel(viewRow);
            DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
            int idEtapa = (int) model.getValueAt(modelRow, 0); // ID está en la columna 0

            // Si hicieron clic en la columna de acciones (7)
            if (column == 7) {
                // Crear menú emergente
                JPopupMenu popup = new JPopupMenu();

                JMenuItem verItem = new JMenuItem("Ver Detalles");
                verItem.addActionListener(e -> mostrarDetalleEtapa(model, modelRow, idEtapa));

                JMenuItem editarItem = new JMenuItem("Editar");
                editarItem.addActionListener(e -> editarEtapa(model, modelRow, idEtapa));

                popup.add(verItem);
                popup.add(editarItem);

                // Mostrar el menú donde hicieron clic
                popup.show(Tabla1, evt.getX(), evt.getY());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar clic: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        }
    }//GEN-LAST:event_Tabla1MouseClicked
    public boolean eliminarEtapa(int idEtapa) {
        Connection con = null;
        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Obtener todos los materiales/herramientas utilizados en esta etapa
            String sqlSelect = "SELECT i.id_inventario, ut.cantidad_usada, i.cantidad as cantidad_actual "
                    + "FROM utilizado ut "
                    + "JOIN inventario i ON ut.inventario_id_inventario = i.id_inventario "
                    + "WHERE ut.etapa_produccion_idetapa_produccion = ?";

            // 2. Actualizar inventario (sumar las cantidades usadas)
            String sqlUpdateInventario = "UPDATE inventario SET cantidad = ? "
                    + "WHERE id_inventario = ?";

            // 3. Eliminar asignaciones de empleados
            String sqlDeleteAsignada = "DELETE FROM asignada WHERE etapa_produccion_idetapa_produccion = ?";

            // 4. Eliminar registros de materiales/herramientas utilizados
            String sqlDeleteUtilizado = "DELETE FROM utilizado WHERE etapa_produccion_idetapa_produccion = ?";

            // 5. Eliminar la etapa de producción
            String sqlDeleteEtapa = "DELETE FROM etapa_produccion WHERE idetapa_produccion = ?";

            // Paso 1: Obtener items utilizados y preparar actualización de inventario
            try (PreparedStatement psSelect = con.prepareStatement(sqlSelect); PreparedStatement psUpdate = con.prepareStatement(sqlUpdateInventario)) {

                psSelect.setInt(1, idEtapa);
                ResultSet rs = psSelect.executeQuery();

                while (rs.next()) {
                    int idInventario = rs.getInt("id_inventario");
                    double cantidadUsada = rs.getDouble("cantidad_usada");
                    String cantidadActualStr = rs.getString("cantidad_actual");

                    // Convertir cantidad actual de String (con coma) a double
                    double cantidadActual = convertirStringADouble(cantidadActualStr);

                    // Calcular nueva cantidad
                    double nuevaCantidad = cantidadActual + cantidadUsada;

                    // Convertir de vuelta a String con formato de coma
                    String nuevaCantidadStr = convertirDoubleAString(nuevaCantidad);

                    // Preparar actualización para cada item
                    psUpdate.setString(1, nuevaCantidadStr);
                    psUpdate.setInt(2, idInventario);
                    psUpdate.addBatch();
                }

                // Ejecutar todas las actualizaciones de inventario
                psUpdate.executeBatch();
            }

            // Paso 2: Eliminar asignaciones de empleados
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteAsignada)) {
                ps.setInt(1, idEtapa);
                ps.executeUpdate();
            }

            // Paso 3: Eliminar registros de materiales utilizados
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteUtilizado)) {
                ps.setInt(1, idEtapa);
                ps.executeUpdate();
            }

            // Paso 4: Eliminar la etapa de producción
            try (PreparedStatement ps = con.prepareStatement(sqlDeleteEtapa)) {
                ps.setInt(1, idEtapa);
                int affectedRows = ps.executeUpdate();

                if (affectedRows > 0) {
                    con.commit(); // Confirmar todas las operaciones
                    return true;
                }
            }
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback(); // Revertir en caso de error
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar la etapa: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true); // Restaurar auto-commit
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

// Método para convertir String con coma a double
    private double convertirStringADouble(String cantidadStr) throws SQLException {
        if (cantidadStr == null || cantidadStr.isEmpty()) {
            return 0.0;
        }
        try {
            // Reemplazar coma por punto para parsear a double
            return Double.parseDouble(cantidadStr.replace(",", "."));
        } catch (NumberFormatException e) {
            throw new SQLException("Formato de cantidad inválido en inventario: " + cantidadStr);
        }
    }

// Método para convertir double a String con coma
    private String convertirDoubleAString(double cantidad) {
        // Formatear el double con coma decimal
        return String.format(Locale.GERMAN, "%.2f", cantidad)
                .replace(".", ","); // Asegurar formato con coma
    }

    private void editarEtapa(DefaultTableModel model, int modelRow, int idEtapa) {
        try {
            // Obtener datos de la fila
            String nombre = model.getValueAt(modelRow, 1).toString();
            String cantidad = String.valueOf(model.getValueAt(modelRow, 2));
            String fechaInicio = model.getValueAt(modelRow, 3).toString();
            String fechaFin = model.getValueAt(modelRow, 4).toString();
            String estado = model.getValueAt(modelRow, 5).toString();
            String asignado = model.getValueAt(modelRow, 6) != null
                    ? model.getValueAt(modelRow, 6).toString() : "No asignado";
            String materiales = model.getColumnCount() > 9 && model.getValueAt(modelRow, 9) != null
                    ? model.getValueAt(modelRow, 9).toString() : "No especificado";
            String herramientas = model.getColumnCount() > 10 && model.getValueAt(modelRow, 10) != null
                    ? model.getValueAt(modelRow, 10).toString() : "No especificado";

            // Mostrar diálogo de edición
            EditEtapaProduccion dialog = new EditEtapaProduccion(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    true,
                    idEtapa
            );

            // Pasar los datos al diálogo
            dialog.setDatos(
                    idEtapa,
                    nombre,
                    cantidad,
                    fechaInicio,
                    fechaFin,
                    estado,
                    materiales,
                    herramientas,
                    asignado
            );

            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            // Recargar la tabla si se hicieron cambios
            if (dialog.datosModificados()) {
                cargarTablaEtapa();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al preparar edición: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
// Método auxiliar para obtener el ID de producción con validación

    private int obtenerIdEtapa(DefaultTableModel model, int modelRow) {
        try {
            Object idObj = model.getValueAt(modelRow, 0);
            int id = Integer.parseInt(idObj.toString());

            if (id <= 0) {
                JOptionPane.showMessageDialog(this,
                        "ID de producción no válido",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return -1;
            }
            return id;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Formato de ID inválido",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return -1;
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSTableMetroCustom Tabla1;
    private RSMaterialComponent.RSButtonShape btnElimi;
    private RSMaterialComponent.RSButtonShape btnNuevo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtbuscar;
    // End of variables declaration//GEN-END:variables

    public void cargarTablaEtapa() {
        // 1. Crear modelo de tabla con columnas visibles
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Cantidad", "Fecha inicio", "Fecha fin", "Estado", "Asignado", "Acciones"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer todas las celdas no editables
            }

            @Override
            public Class<?> getColumnClass(int column) {
                // Especificar tipos de datos para cada columna
                switch (column) {
                    case 0:
                        return Integer.class; // ID
                    case 2:
                        return Integer.class; // Cantidad
                    default:
                        return String.class;
                }
            }
        };

        // 2. Asignar el modelo a la tabla
        Tabla1.setModel(model);

        // 3. Configurar ancho de columnas
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(50);  // ID
        Tabla1.getColumnModel().getColumn(6).setPreferredWidth(100); // Asignado
        Tabla1.getColumnModel().getColumn(7).setPreferredWidth(120); // Acciones

        // 4. Conexión y consulta a la base de datos
        try (Connection con = Conexion.getConnection()) {
            String sql = "SELECT ep.idetapa_produccion, ep.nombre_etapa, ep.cantidad, "
                    + "ep.fecha_inicio, ep.fecha_fin, ep.estado, "
                    + "u.nombre AS asignado "
                    + "FROM etapa_produccion ep "
                    + "LEFT JOIN asignada a ON ep.idetapa_produccion = a.etapa_produccion_idetapa_produccion "
                    + "LEFT JOIN usuario u ON a.usuario_id_usuario = u.id_usuario "
                    + "WHERE ep.produccion_id_produccion = ? "
                    + "ORDER BY ep.fecha_inicio ASC";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, this.idProduccion);
                ResultSet rs = ps.executeQuery();

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                // 5. Poblar la tabla con los resultados
                while (rs.next()) {
                    model.addRow(new Object[]{
                        rs.getInt("idetapa_produccion"),
                        rs.getString("nombre_etapa"),
                        rs.getInt("cantidad"),
                        sdf.format(rs.getDate("fecha_inicio")),
                        rs.getDate("fecha_fin") != null ? sdf.format(rs.getDate("fecha_fin")) : "En proceso",
                        rs.getString("estado"),
                        rs.getString("asignado") != null ? rs.getString("asignado") : "No asignado",
                        "Ver/Editar" // Columna combinada para acciones
                    });
                }

                System.out.println("Registros cargados: " + model.getRowCount());
            }
        } catch (SQLException e) {
            System.err.println("Error en cargarTablaEtapa: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                    "Error al cargar datos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        // 6. Configurar renderizadores DESPUÉS de cargar los datos
        Tabla1.getColumnModel().getColumn(5).setCellRenderer(new EstadoTableCellRenderer());
        Tabla1.getColumnModel().getColumn(7).setCellRenderer(new AccionesTableCellRenderer());

        // 7. Forzar actualización visual
        Tabla1.revalidate();
        Tabla1.repaint();
    }

    private void filtrarTabla() {
        String textoBusqueda = txtbuscar.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
        Tabla1.setRowSorter(tr);

        if (textoBusqueda.isEmpty()) {
            tr.setRowFilter(null);
            return;
        }

        // Expresión regular para detectar si son solo números (1-2 dígitos)
        if (textoBusqueda.matches("\\d{1}")) {
            // Buscar en ID (columna 0) y fechas (columnas 1 y 2)
            List<RowFilter<Object, Object>> filters = new ArrayList<>();
            filters.add(RowFilter.regexFilter("^" + textoBusqueda, 0));// ID (coincidencia exacta)
            tr.setRowFilter(RowFilter.orFilter(filters));

        } else if (textoBusqueda.matches("\\d{2}")) {
            List<RowFilter<Object, Object>> filters = new ArrayList<>();
            filters.add(RowFilter.regexFilter(textoBusqueda, 1)); // fecha_inicio
            filters.add(RowFilter.regexFilter(textoBusqueda, 2)); // fecha_fin
            tr.setRowFilter(RowFilter.orFilter(filters));
        } // Si contiene letras (aunque sea parcial)
        else if (textoBusqueda.matches(".*[a-zA-ZáéíóúÁÉÍÓÚ].*")) {
            // Buscar solo en estado (columna 3)
            tr.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda, 3));
        } // Para otros casos (números más largos, combinaciones, etc.)
        else {
            // Buscar en todos los campos
            tr.setRowFilter(RowFilter.regexFilter("(?i)" + textoBusqueda));
        }
    }

    private class AccionesTableCellRenderer extends DefaultTableCellRenderer {

        private final Font fontNormal = new Font("Tahoma", Font.PLAIN, 12);
        private final Font fontBold = new Font("Tahoma", Font.BOLD, 12);

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            boolean oscuro = TemaManager.getInstance().isOscuro();
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Configuración basada en el tema
            if (oscuro) {
                if (isSelected) {
                    c.setBackground(new Color(67, 71, 120));
                    c.setForeground(Color.WHITE);
                } else {
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
            setText("Ver/Editar");
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(oscuro ? new Color(100, 100, 100) : new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(0, 5, 0, 5)
            ));
            setFont(isSelected ? fontBold : fontNormal);

            return c;
        }
    }

}
