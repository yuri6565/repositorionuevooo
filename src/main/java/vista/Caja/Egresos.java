/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.Caja;

import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import controlador.Ctrl_CajaEgresos;
import controlador.GeneradorEgresosPDF;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import modelo.Caja;
import vista.TemaManager;

/**
 *
 * @author ADSO
 */
public final class Egresos extends javax.swing.JPanel {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Creates new form CajaContenido
     */
    public Egresos() {
        initComponents();

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{"Codigo", "Fecha Pago", "Monto", "Descripcion", "Categoria", "Detalle", "Editar", "proveedor", "productos", "cantidad"}
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(7)); // Oculta proveedor
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(7)); // Oculta productos
        Tabla1.removeColumn(Tabla1.getColumnModel().getColumn(7)); // Oculta productos

        Tabla1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        Tabla1.setRowSelectionAllowed(true);
        Tabla1.setFocusable(false);
        cargarTablaEgresos();
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
            jPanel2.setBackground(fondo);

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
            btnNuevo.setBackground(encabezado);
            btnNuevo.setBackgroundHover(new Color(118, 142, 240));
        } else {
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;
            Color primario = new Color(72, 92, 188);

            jPanel2.setBackground(fondo);
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
            btnNuevo.setBackground(new Color(46, 49, 82));

        }
        Tabla1.repaint();
        Tabla1.getTableHeader().repaint();
    }

    public void cargarTablaEgresos() {
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        model.setRowCount(0); // Limpiar tabla

        Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
        List<Caja> egresos = ctrl.obtenerEgresos();

        if (egresos.isEmpty()) {
            System.out.println("No se encontraron egresos en la base de datos");
            return;
        }

        try {
            for (Caja caja : egresos) {
                Object endDate = null;
                Object startDate = null;

//
                // Filtrar por rango de fechas si se proporcionan
                if (startDate != null && endDate != null) {
                    try {
                        Date fechaEgreso = dateFormat.parse(caja.getFecha());
                        // Normalizar fechas para comparar solo el día (ignorar horas)
                        Date start = truncateTime((Date) startDate);
                        Date end = truncateTime((Date) endDate);
                        Date fecha = truncateTime(fechaEgreso);
                        // Incluir fechas iguales a startDate y endDate
                        if (fecha.compareTo(start) >= 0 && fecha.compareTo(end) <= 0) {
                            model.addRow(new Object[]{
                                caja.getId_codigo(),
                                caja.getFecha(),
                                caja.getMonto(),
                                caja.getDescripcion(),
                                caja.getCategoria(),
                                "Ver",
                                "editar",
                                caja.getProveedor() != null ? caja.getProveedor() : "",
                                caja.getProductos() != null ? String.join(", ", caja.getProductos()) : "",
                                caja.getCantidad()
                            });
                        }
                    } catch (Exception e) {
                        System.err.println("Error al parsear fecha de egreso: " + caja.getFecha() + " - " + e.getMessage());
                        continue; // Saltar registros con fechas inválidas
                    }
                } else {
                    // Sin filtro, añadir todos los registros
                    model.addRow(new Object[]{
                        caja.getId_codigo(),
                        caja.getFecha(),
                        caja.getMonto(),
                        caja.getDescripcion(),
                        caja.getCategoria(),
                        "Ver",
                        "editar",
                        caja.getProveedor() != null ? caja.getProveedor() : "",
                        caja.getProductos() != null ? String.join(", ", caja.getProductos()) : "",
                        caja.getCantidad()
                    });
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al cargar egresos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Ajustar ancho de columnas
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(20);  // ID
        Tabla1.getColumnModel().getColumn(1).setPreferredWidth(100); // Fecha
        Tabla1.getColumnModel().getColumn(2).setPreferredWidth(80);  // Monto
        Tabla1.getColumnModel().getColumn(3).setPreferredWidth(200); // Descripción
        Tabla1.getColumnModel().getColumn(4).setPreferredWidth(300); // Categoría
        Tabla1.getColumnModel().getColumn(5).setPreferredWidth(40);  // Ver
        Tabla1.getColumnModel().getColumn(6).setPreferredWidth(50);  // Editar

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new RSMaterialComponent.RSTableMetroCustom();
        btnEliminar1 = new RSMaterialComponent.RSButtonShape();
        txtbuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        btnNuevo = new RSMaterialComponent.RSButtonShape();
        btnImprimirReg = new RSMaterialComponent.RSButtonShape();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Id Registro", "Fecha Pago", "Detalle", "Categoria", "Cantidad ingresada"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
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
        Tabla1.setMinimumSize(new java.awt.Dimension(75, 546));
        Tabla1.setPreferredSize(new java.awt.Dimension(375, 482));
        Tabla1.setRowHeight(23);
        Tabla1.setSelectionBackground(new java.awt.Color(109, 160, 221));
        Tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Tabla1MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(Tabla1);
        Tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 1150, 520));

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
        jPanel2.add(btnEliminar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 50, 110, 30));

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
        jPanel2.add(txtbuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 410, 30));

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
        jPanel2.add(btnNuevo, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 50, 110, 30));

        btnImprimirReg.setBackground(new java.awt.Color(46, 49, 82));
        btnImprimirReg.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnImprimirReg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/archivo-pdf.png"))); // NOI18N
        btnImprimirReg.setText("Imprimir");
        btnImprimirReg.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnImprimirReg.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        btnImprimirReg.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnImprimirReg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnImprimirReg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirRegActionPerformed(evt);
            }
        });
        jPanel2.add(btnImprimirReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 40, 110, 40));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1197, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        try {
            int column = Tabla1.columnAtPoint(evt.getPoint());
            int viewRow = Tabla1.rowAtPoint(evt.getPoint());

            if (viewRow < 0 || column < 0) {
                return;
            }

            int modelRow = Tabla1.convertRowIndexToModel(viewRow);
            DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
            int idEgreso = (int) model.getValueAt(modelRow, 0);

            if (column == 5) { // Columna "Ver"
                mostrarDetalleEgreso(model, modelRow, idEgreso);
            } else if (column == 6) { // Columna "Editar"
                abrirEditarEgreso(idEgreso); // Nuevo método para abrir el formulario de edición
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al procesar clic: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_Tabla1MouseClicked

    private void txtbuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtbuscarActionPerformed
        // TODO add your handling code here:
        filtrarTabla();
    }//GEN-LAST:event_txtbuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        formuEgresos1 dialog = new formuEgresos1(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        cargarTablaEgresos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnEliminar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminar1ActionPerformed
        int[] selectedRows = Tabla1.getSelectedRows();

        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(
                    this,
                    "Por favor seleccione al menos una fila para eliminar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "¿Está seguro que desea eliminar los " + selectedRows.length + " registros seleccionados?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        int eliminados = 0;

        for (int i = selectedRows.length - 1; i >= 0; i--) {
            int codigo = (int) Tabla1.getValueAt(selectedRows[i], 0);
            if (ctrl.eliminar(codigo)) {
                model.removeRow(selectedRows[i]);
                eliminados++;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Se eliminaron " + eliminados + " registros correctamente.",
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE
        );
    }//GEN-LAST:event_btnEliminar1ActionPerformed

    private List<Integer> obtenerAñosEgresos() {
        Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
        List<Caja> egresos = ctrl.obtenerEgresos();
        Set<Integer> años = new TreeSet<>(); // Usar TreeSet para ordenar años

        if (egresos != null && !egresos.isEmpty()) {
            for (Caja caja : egresos) {
                if (caja.getFecha() != null && !caja.getFecha().isEmpty()) {
                    try {
                        Date fechaEgreso = dateFormat.parse(caja.getFecha());
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(fechaEgreso);
                        años.add(cal.get(Calendar.YEAR));
                    } catch (Exception e) {
                        System.err.println("Error al parsear fecha para obtener año: " + caja.getFecha() + " - " + e.getMessage());
                    }
                }
            }
        }

        // Si no hay años, añadir el año actual como respaldo
        if (años.isEmpty()) {
            años.add(Calendar.getInstance().get(Calendar.YEAR));
        }
        return new ArrayList<>(años);
    }

    private List<String> obtenerCategoriasEgresos() {
        Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
        List<Caja> egresos = ctrl.obtenerEgresos();
        Set<String> categorias = new TreeSet<>(); // Usar TreeSet para ordenar categorías

        if (egresos != null && !egresos.isEmpty()) {
            for (Caja caja : egresos) {
                if (caja.getCategoria() != null && !caja.getCategoria().isEmpty()) {
                    categorias.add(caja.getCategoria());
                }
            }
        }

        // Añadir opción "Todas" para no filtrar por categoría
        List<String> resultado = new ArrayList<>();
        resultado.add("Todas");
        resultado.addAll(categorias);
        return resultado;
    }

    private Date mostrarSelectorFechaPersonalizado(boolean esDesde, int añoSeleccionado) {
        JDialog fechaDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this),
                "Seleccionar Fecha " + (esDesde ? "Inicio" : "Fin"), true);
        fechaDialog.setSize(250, 250);
        fechaDialog.setLayout(new BorderLayout());
        fechaDialog.setLocationRelativeTo(this);

        JCalendar calendar = new JCalendar();
        calendar.setWeekOfYearVisible(false);

        // Establecer límites según el año seleccionado
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, añoSeleccionado);
        cal.set(Calendar.MONTH, Calendar.JANUARY);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date minDate = truncateTime(cal.getTime());
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);
        Date maxDate = truncateTime(cal.getTime());
        calendar.setMinSelectableDate(minDate);
        calendar.setMaxSelectableDate(maxDate);

        // Variable para almacenar la fecha seleccionada
        final Date[] fechaSeleccionada = {null};

        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(e -> {
            fechaSeleccionada[0] = calendar.getDate();
            fechaDialog.dispose();
        });

        fechaDialog.add(calendar, BorderLayout.CENTER);
        fechaDialog.add(btnAceptar, BorderLayout.SOUTH);
        fechaDialog.setVisible(true);

        return fechaSeleccionada[0];
    }

    private void btnImprimirRegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirRegActionPerformed
// Crear diálogo para seleccionar fechas
       JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));
        String[] periodos = {"Ultima Semana", "Ultimos Mes", "Ultimos 6 Meses", "Ultimo Año", "Personalizado", "Por Categoría"};
        JComboBox<String> comboPeriodo = new JComboBox<>(periodos);
        List<Integer> años = obtenerAñosEgresos();
        JComboBox<Integer> comboAños = new JComboBox<>(años.toArray(new Integer[0]));
        List<String> categorias = obtenerCategoriasEgresos();
        JComboBox<String> comboCategorias = new JComboBox<>(categorias.toArray(new String[0]));
        JButton btnFechaInicio = new JButton("Inicio");
        JButton btnFechaFin = new JButton("Fin");

        panel.add(comboPeriodo);
        panel.add(new JPanel()); // Espacio vacío
        panel.add(comboAños);
        panel.add(comboCategorias);
        panel.add(btnFechaInicio);
        panel.add(btnFechaFin);

        comboAños.setVisible(false);
        comboCategorias.setVisible(false);
        btnFechaInicio.setVisible(false);
        btnFechaFin.setVisible(false);

        final Date[] fechaInicioSeleccionada = {null};
        final Date[] fechaFinSeleccionada = {null};

        btnFechaInicio.addActionListener(e -> {
            int añoSeleccionado = (Integer) comboAños.getSelectedItem();
            fechaInicioSeleccionada[0] = mostrarSelectorFechaPersonalizado(true, añoSeleccionado);
            if (fechaInicioSeleccionada[0] != null) {
                btnFechaInicio.setText(dateFormat.format(fechaInicioSeleccionada[0]));
            }
        });

        btnFechaFin.addActionListener(e -> {
            int añoSeleccionado = (Integer) comboAños.getSelectedItem();
            fechaFinSeleccionada[0] = mostrarSelectorFechaPersonalizado(false, añoSeleccionado);
            if (fechaFinSeleccionada[0] != null) {
                btnFechaFin.setText(dateFormat.format(fechaFinSeleccionada[0]));
            }
        });

        comboPeriodo.addActionListener(e -> {
            String periodo = (String) comboPeriodo.getSelectedItem();
            boolean esPersonalizado = periodo.equals("Personalizado");
            boolean esAño = periodo.equals("Año");
            boolean esCategoria = periodo.equals("Por Categoría");

            comboAños.setVisible(esAño || esPersonalizado || esCategoria);
            comboCategorias.setVisible(esCategoria);
            btnFechaInicio.setVisible(esPersonalizado);
            btnFechaFin.setVisible(esPersonalizado);

            if (!esPersonalizado) {
                btnFechaInicio.setText("Inicio");
                btnFechaFin.setText("Fin");
                fechaInicioSeleccionada[0] = null;
                fechaFinSeleccionada[0] = null;
            }
            if (!esCategoria) {
                comboCategorias.setSelectedItem("Todas");
            }

            panel.revalidate();
            panel.repaint();
        });

        int result = JOptionPane.showConfirmDialog(this, panel, "Reporte", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Date startDate = null;
            Date endDate = new Date();
            endDate = truncateTime(endDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(endDate);
            String categoriaSeleccionada = (String) comboCategorias.getSelectedItem();

            String periodoSeleccionado = (String) comboPeriodo.getSelectedItem();
            switch (periodoSeleccionado) {
                case "Semana":
                    cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    startDate = truncateTime(cal.getTime());
                    categoriaSeleccionada = "Todas";
                    break;
                case "Mes":
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = truncateTime(cal.getTime());
                    categoriaSeleccionada = "Todas";
                    break;
                case "6 Meses":
                    cal.add(Calendar.MONTH, -6);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = truncateTime(cal.getTime());
                    categoriaSeleccionada = "Todas";
                    break;
                case "Año":
                    if (años.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No hay años disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int añoSeleccionado = (Integer) comboAños.getSelectedItem();
                    cal.set(Calendar.YEAR, añoSeleccionado);
                    cal.set(Calendar.MONTH, Calendar.JANUARY);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = truncateTime(cal.getTime());
                    cal.set(Calendar.MONTH, Calendar.DECEMBER);
                    cal.set(Calendar.DAY_OF_MONTH, 31);
                    endDate = truncateTime(cal.getTime());
                    categoriaSeleccionada = "Todas";
                    break;
                case "Personalizado":
                    if (años.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No hay años disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    startDate = fechaInicioSeleccionada[0];
                    endDate = fechaFinSeleccionada[0];
                    if (startDate == null || endDate == null) {
                        JOptionPane.showMessageDialog(this, "Seleccione ambas fechas.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    startDate = truncateTime(startDate);
                    endDate = truncateTime(endDate);
                    if (startDate.after(endDate)) {
                        JOptionPane.showMessageDialog(this, "Fecha inicio debe ser anterior a fecha fin.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int añoPersonalizado = (Integer) comboAños.getSelectedItem();
                    cal.setTime(startDate);
                    if (cal.get(Calendar.YEAR) != añoPersonalizado) {
                        JOptionPane.showMessageDialog(this, "Fecha inicio fuera del año seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    cal.setTime(endDate);
                    if (cal.get(Calendar.YEAR) != añoPersonalizado) {
                        JOptionPane.showMessageDialog(this, "Fecha fin fuera del año seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    categoriaSeleccionada = "Todas";
                    break;
                case "Por Categoría":
                    if (años.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No hay años disponibles.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    int añoCategoria = (Integer) comboAños.getSelectedItem();
                    cal.set(Calendar.YEAR, añoCategoria);
                    cal.set(Calendar.MONTH, Calendar.JANUARY);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    startDate = truncateTime(cal.getTime());
                    cal.set(Calendar.MONTH, Calendar.DECEMBER);
                    cal.set(Calendar.DAY_OF_MONTH, 31);
                    endDate = truncateTime(cal.getTime());
                    break;
            }

            try {
                Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
                List<Caja> egresosFiltrados = new ArrayList<>();
                List<Caja> egresos = ctrl.obtenerEgresos();

                if (egresos == null || egresos.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron egresos.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                for (Caja caja : egresos) {
                    if (caja.getFecha() == null || caja.getFecha().isEmpty()) {
                        continue;
                    }
                    boolean categoriaCoincide = categoriaSeleccionada.equals("Todas") || 
                        (caja.getCategoria() != null && caja.getCategoria().equals(categoriaSeleccionada));
                    if (!categoriaCoincide) {
                        continue;
                    }
                    try {
                        Date fechaEgreso = dateFormat.parse(caja.getFecha());
                        Date fecha = truncateTime(fechaEgreso);
                        if (fecha.compareTo(startDate) >= 0 && fecha.compareTo(endDate) <= 0) {
                            egresosFiltrados.add(caja);
                        }
                    } catch (Exception e) {
                        continue;
                    }
                }

                if (egresosFiltrados.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "No se encontraron egresos para el período/categoría.", "Información", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }

                DefaultTableModel model = new DefaultTableModel(
                        new Object[][]{},
                        new String[]{"Código", "Fecha Pago", "Monto", "Descripción", "Categoría", "Proveedor", "Productos", "Cantidad"}
                );
                double totalMonto = 0.0;
                for (Caja caja : egresosFiltrados) {
                    model.addRow(new Object[]{
                        caja.getFecha(),
                        caja.getMonto(),
                        caja.getDescripcion(),
                        caja.getCategoria(),
                        caja.getProveedor() != null ? caja.getProveedor() : "",
                        caja.getProductos() != null ? String.join(", ", caja.getProductos()) : "",
                        caja.getCantidad()
                    });
                    totalMonto += caja.getMonto();
                }

                GeneradorEgresosPDF generador = new GeneradorEgresosPDF();
                String archivoSalida = "reporte_egresos_" + System.currentTimeMillis() + ".pdf";
                generador.generarPDF(model, dateFormat.format(startDate), dateFormat.format(endDate), String.format("$%,.2f", totalMonto), archivoSalida);

                //cargarTablaEgresos(startDate, endDate, categoriaSeleccionada.equals("Todas") ? null : categoriaSeleccionada);
                // // cargarTablaEgresos(null, null, null); // Descomentar si no quieres filtrar la tabla

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al generar PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }//GEN-LAST:event_btnImprimirRegActionPerformed

    private Date truncateTime(Date date) {
        if (date == null) {
            return null;
        }
        try {
            String formattedDate = dateFormat.format(date);
            return dateFormat.parse(formattedDate);
        } catch (Exception e) {
            return date; // Devolver la fecha original si falla el parseo
        }
    }

    private void abrirEditarEgreso(int idEgreso) {
        try {
            Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
            Caja caja = ctrl.obtenerEgresoPorId(idEgreso); // Obtener datos del egreso

            if (caja != null) {
                // Crear y mostrar el formulario de edición
                EditarEgresos2 dialog = new EditarEgresos2(
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        true,
                        caja.getId_codigo(),
                        caja.getFecha(),
                        caja.getMonto(),
                        caja.getDescripcion(),
                        caja.getCategoria(),
                        caja.getProveedor(),
                        caja.getProductos(),
                        caja.getCantidad()
                );
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);

                // Recargar la tabla después de editar
                cargarTablaEgresos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron datos para este egreso.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir editor: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

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
    private RSMaterialComponent.RSButtonShape btnImprimirReg;
    private RSMaterialComponent.RSButtonShape btnNuevo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtbuscar;
    // End of variables declaration//GEN-END:variables

    private void mostrarDetalleEgreso(DefaultTableModel model, int modelRow, int idEgreso) {
        try {
            Ctrl_CajaEgresos ctrl = new Ctrl_CajaEgresos();
            Caja caja = ctrl.obtenerEgresoPorId(idEgreso);

            if (caja != null) {
                // Preparar los datos con valores por defecto si son nulos
                String fecha = caja.getFecha() != null ? caja.getFecha() : "No especificado";
                String descripcion = caja.getDescripcion() != null ? caja.getDescripcion() : "No especificado";
                String categoria = caja.getCategoria() != null ? caja.getCategoria() : "No especificado";
                String monto = caja.getMonto() == 0.0 ? "No especificado" : String.valueOf(caja.getMonto());
                String proveedor = caja.getProveedor() != null ? caja.getProveedor() : "No especificado";
                String cantidad = caja.getCantidad() != 0 ? String.valueOf(caja.getCantidad()) : "No especificado";
                // Manejar productos (puede ser null o lista vacía)
                String productosStr;
                if (caja.getProductos() == null || caja.getProductos().isEmpty()) {
                    productosStr = "No especificado";
                } else {
                    productosStr = String.join(", ", caja.getProductos());
                }

                DetalleEgreso dialog = new DetalleEgreso(
                        (JFrame) SwingUtilities.getWindowAncestor(this),
                        true,
                        caja.getId_codigo(),
                        fecha,
                        descripcion,
                        categoria,
                        monto,
                        proveedor,
                        productosStr,
                        cantidad
                );
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron detalles para este egreso",
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al mostrar detalle: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
