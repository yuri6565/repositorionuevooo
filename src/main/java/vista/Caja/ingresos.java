/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.Caja;

import controlador.Ctrl_CajaIngresos;
import controlador.Ctrl_Pedido;
import controlador.GeneradorIngresosPDF;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import modelo.Caja;
import modelo.PedidoDetalle;
import vista.TemaManager;

/**
 *
 * @author ADSO
 */
public final class ingresos extends javax.swing.JPanel {

    private Ctrl_CajaIngresos controlador;
    private GeneradorIngresosPDF generadorPDF;
    private Ctrl_Pedido ctrlPedido;

    /**
     * Creates new form Ingresos
     */
    public ingresos(JPanel panelP1) {
        initComponents();

        // Inicializar el controlador
        controlador = new Ctrl_CajaIngresos();
        generadorPDF = new GeneradorIngresosPDF();
        ctrlPedido = new Ctrl_Pedido();

        // Configurar la columna "Detalle"
        TableColumn detailColumn = Tabla1.getColumnModel().getColumn(6);
        detailColumn.setCellRenderer(new ButtonRenderer());
        detailColumn.setPreferredWidth(35); // Ajustar el ancho de la columna
        Tabla1.setRowHeight(30); // Ajustar la altura de las filas

        // Cargar datos iniciales
        cargarDatosIniciales();
        aplicarTema();
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
            btnEliminar1.setBackground(encabezado);
            btnEliminar1.setBackgroundHover(new Color(118, 142, 240));
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
            btnEliminar1.setBackground(new Color(46, 49, 82));

        }
        Tabla1.repaint();
        Tabla1.getTableHeader().repaint();
    }

    // Método para cargar datos iniciales en la tabla
    public void cargarDatosIniciales() {
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        model.setRowCount(0); // Limpiar la tabla

        List<Ctrl_CajaIngresos.IngresoConDetalles> ingresos = controlador.obtenerIngresos();
        if (ingresos == null || ingresos.isEmpty()) {
            System.out.println("No se encontraron ingresos para cargar en la tabla.");
            return;
        }

        for (Ctrl_CajaIngresos.IngresoConDetalles ingreso : ingresos) {
            model.addRow(new Object[]{
                ingreso.getIngreso().getIdAbono(),
                ingreso.getNombreDetallePedido(),
                ingreso.getNombreCliente(),
                ingreso.getMontoTotalDetalle(),
                ingreso.getIngreso().getPagado(),
                ingreso.getIngreso().getDebido(),
                "Ver", // Columna Detalle
                "Imprimir" // Columna Acciones (puedes implementar botones aquí si lo deseas)
            });
        }
    }

    // Renderizador personalizado para la columna "Ver"
    private class ButtonRenderer extends DefaultTableCellRenderer {

        private final Color textColor = new Color(46, 49, 82);
        private final Font fontNormal = new Font("Tahoma", Font.PLAIN, 14);
        private final Font fontBold = new Font("Tahoma", Font.BOLD, 14);

        @Override
        public Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            c.setForeground(isSelected ? Color.WHITE : textColor);
            c.setBackground(isSelected ? table.getSelectionBackground() : Color.WHITE);
            c.setFont(isSelected ? fontBold : fontNormal);

            setHorizontalAlignment(CENTER);
            setText("Ver");

            // Bordes para que se vea como un botón
            setBorder(BorderFactory.createLineBorder(new Color(219, 219, 219), 1));
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
        txtbuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        btnNuevo = new RSMaterialComponent.RSButtonShape();
        btnEliminar1 = new RSMaterialComponent.RSButtonShape();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new RSMaterialComponent.RSTableMetroCustom();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(1250, 630));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtbuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtbuscar.setColorIcon(new java.awt.Color(0, 0, 0));
        txtbuscar.setColorMaterial(new java.awt.Color(153, 153, 153));
        txtbuscar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
        txtbuscar.setPhColor(new java.awt.Color(102, 102, 102));
        txtbuscar.setPlaceholder("Buscar...");
        txtbuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtbuscarActionPerformed(evt);
            }
        });
        jPanel1.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 410, 30));

        btnNuevo.setBackground(new java.awt.Color(46, 49, 82));
        btnNuevo.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnNuevo.setText(" Nuevo");
        btnNuevo.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnNuevo.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        btnNuevo.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnNuevo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 40, 110, 30));

        btnEliminar1.setBackground(new java.awt.Color(46, 49, 82));
        btnEliminar1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnEliminar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete (1).png"))); // NOI18N
        btnEliminar1.setText(" Eliminar");
        btnEliminar1.setToolTipText("");
        btnEliminar1.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnEliminar1.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        btnEliminar1.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnEliminar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnEliminar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 40, 110, 30));

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Codigo", "Pedido", "Cliente", "Monto total", "Pagado", "Debido", "Detalle", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, true, true, true, true, true, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setBackgoundHead(new java.awt.Color(46, 49, 82));
        Tabla1.setBackgoundHover(new java.awt.Color(67, 150, 209));
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
        Tabla1.setRowHeight(23);
        Tabla1.setSelectionBackground(new java.awt.Color(67, 150, 209));
        Tabla1.setShowGrid(false);
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla1);
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 1210, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1301, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1301, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 673, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
        filtrarTabla();
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        ingresoNuevo dialog = new ingresoNuevo(new javax.swing.JFrame(), true, this);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed

    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked

        int column = Tabla1.columnAtPoint(evt.getPoint());
        int row = Tabla1.rowAtPoint(evt.getPoint());

        if (row >= 0) {
            if (column == 6) { // Columna "Detalle" (Ver)
                int idAbono = (int) Tabla1.getValueAt(row, 0);
                Ctrl_CajaIngresos.IngresoConDetalles ingreso = controlador.obtenerIngresoPorId(idAbono);
                if (ingreso != null) {
                    System.out.println("Ver detalles de la fila " + row + " (ID: " + idAbono + ")");
                    // Aquí puedes agregar la lógica para mostrar detalles
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontraron detalles para este ingreso.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (column == 7) { // Columna "Acciones" (Imprimir)
                // Obtener el idAbono de la fila seleccionada
                Object idAbonoObj = Tabla1.getValueAt(row, 0);
                if (idAbonoObj == null) {
                    JOptionPane.showMessageDialog(this, "ID de abono no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                int idAbono = (int) idAbonoObj;

                // Obtener datos de Caja
                Ctrl_CajaIngresos.IngresoConDetalles ingresoCaja = controlador.obtenerIngresoPorId(idAbono);
                if (ingresoCaja == null) {
                    JOptionPane.showMessageDialog(this, "No se encontraron datos de ingreso para el ID: " + idAbono, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Verificar y asignar valores con manejo de tipos
                Double montoTotalObj = ingresoCaja.getMontoTotalDetalle();
                double montoTotal = (montoTotalObj != null) ? montoTotalObj.doubleValue() : 0.0;

                Double pagadoObj = ingresoCaja.getIngreso() != null ? ingresoCaja.getIngreso().getPagado() : null;
                double pagado = (pagadoObj != null) ? pagadoObj.doubleValue() : 0.0;

                Double debidoObj = ingresoCaja.getIngreso() != null ? ingresoCaja.getIngreso().getDebido() : null;
                double debido = (debidoObj != null) ? debidoObj.doubleValue() : 0.0;

                // Obtener el id_pedido desde Ingreso (ajusta según la estructura real)
                int idPedido = ingresoCaja.getIngreso() != null ? ingresoCaja.getIngreso().getIdPedido() : idAbono; // Ajusta getPedidoIdPedido()
                if (idPedido == 0 && idAbono != idPedido) {
                    JOptionPane.showMessageDialog(this, "No se pudo determinar el ID del pedido para el ID de abono: " + idAbono, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Obtener datos del pedido
                Ctrl_Pedido.MaterialConDetalles material = ctrlPedido.obtenerPedidoPorId(idPedido);
                if (material == null) {
                    JOptionPane.showMessageDialog(this, "No se encontró el pedido para el ID: " + idPedido, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                String nombreCliente = material.getNombreCliente();
                String nombrePedido = material.getPedido().getNombre();
                String estadoPedido = material.getPedido().getEstado();
                Date fechaInicio = material.getPedido().getFecha_inicio();
                Date fechaFin = material.getPedido().getFecha_fin();

                // Obtener detalles del pedido
                List<PedidoDetalle> detalles = ctrlPedido.obtenerDetallesPorPedido(idPedido);
                if (detalles == null || detalles.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron detalles para el pedido ID: " + idPedido, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Crear modelo de tabla con detalles
                DefaultTableModel pdfModel = new DefaultTableModel(
                        new Object[detalles.size() + 1][], // +1 para incluir el resumen
                        new String[]{"Pedido", "Descripción", "Cantidad", "Dimensiones", "Precio Unitario", "Subtotal", "Total", "Monto Total", "Pagado", "Debido"}
                );

                // Llenar con detalles del pedido
                for (int i = 0; i < detalles.size(); i++) {
                    PedidoDetalle detalle = detalles.get(i);
                    pdfModel.setValueAt(nombrePedido, i, 0);
                    pdfModel.setValueAt(detalle.getDescripcion(), i, 1);
                    pdfModel.setValueAt(detalle.getCantidad(), i, 2);
                    pdfModel.setValueAt(detalle.getDimensiones(), i, 3);
                    pdfModel.setValueAt(detalle.getPrecioUnitario(), i, 4);
                    pdfModel.setValueAt(detalle.getSubtotal(), i, 5);
                    pdfModel.setValueAt(detalle.getTotal(), i, 6);
                }

                // Agregar fila de resumen
                int lastRow = detalles.size();
                pdfModel.setValueAt(nombrePedido, lastRow, 0);
                pdfModel.setValueAt("Resumen", lastRow, 1);
                pdfModel.setValueAt("", lastRow, 2);
                pdfModel.setValueAt("", lastRow, 3);
                pdfModel.setValueAt("", lastRow, 4);
                pdfModel.setValueAt("", lastRow, 5);
                pdfModel.setValueAt("", lastRow, 6);
                pdfModel.setValueAt(montoTotal, lastRow, 7);
                pdfModel.setValueAt(pagado, lastRow, 8);
                pdfModel.setValueAt(debido, lastRow, 9);

                // Calcular el total (usando Monto Total como ejemplo)
                String total = String.format("$%.2f", montoTotal);

                // Generar el PDF con datos adicionales
                String archivoSalida = "ingreso_" + idAbono + "_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";
                generadorPDF.generarPDF(
                        nombreCliente,
                        pdfModel,
                        total,
                        archivoSalida,
                        fechaInicio != null ? new SimpleDateFormat("dd/MM/yyyy").format(fechaInicio) : "Sin fecha"
                );
            }
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void filtrarTabla() {
        String textoBusqueda = txtbuscar.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
        Tabla1.setRowSorter(tr);
        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (textoBusqueda.isEmpty()) {
            tr.setRowFilter(null);
            return;
        }
        // Expresión regular para detectar si son solo números (1-2 dígitos)
        if (textoBusqueda.matches("\\d+")) {
            // Buscar en ID (columna 0) y fechas (columnas 1 y 2)
            filters.add(RowFilter.regexFilter(textoBusqueda, 0));// ID (coincidencia exacta)
            filters.add(RowFilter.regexFilter(textoBusqueda, 1));
            filters.add(RowFilter.regexFilter(textoBusqueda, 4));

        } // Si contiene letras (aunque sea parcial)
        else {
            // Buscar en Detalle (columna 2) y Categoría (columna 3)
            String regex = "(?i)" + textoBusqueda; // (?i) = ignore case
            filters.add(RowFilter.regexFilter(regex, 2)); // Detalle
            filters.add(RowFilter.regexFilter(regex, 3)); // Categoría
        }

        // Aplicar todos los filtros combinados con OR
        tr.setRowFilter(RowFilter.orFilter(filters));

    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSTableMetroCustom Tabla1;
    private RSMaterialComponent.RSButtonShape btnEliminar1;
    private RSMaterialComponent.RSButtonShape btnNuevo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtbuscar;
    // End of variables declaration//GEN-END:variables
}
