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
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import modelo.Caja;
import modelo.Ingresos;
import modelo.PedidoDetalle;
import vista.TemaManager;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;
import rojeru_san.RSButtonRiple;

/**
 *
 * @author ADSO
 */
public final class ingresos extends javax.swing.JPanel {

    private JPanel contenedor; // Referencia al contenedor de Principal
    private Ctrl_CajaIngresos controlador;
    private GeneradorIngresosPDF generadorPDF;
    private Ctrl_Pedido ctrlPedido;
    private final Font fontNormal = new Font("Tahoma", Font.PLAIN, 14);
    private final Font fontBold = new Font("Tahoma", Font.BOLD, 14);
    private final Color textColor = new Color(46, 49, 82);
    private final Color BG_GRAY = new Color(230, 230, 230);
    private final Color FG_GRAY = new Color(138, 138, 138);
    private final Color BG_NORMAL = Color.WHITE;
    private final Color FG_NORMAL = Color.BLACK;

    // Agrega estas variables en la sección de variables de clase
    private final Color HEADER_BG = new Color(46, 49, 82);  // Color de fondo del encabezado
    private final Color HEADER_FG = Color.WHITE;            // Color de texto del encabezado

    private final Color ROW_FG = Color.BLACK;               // Color texto filas
    private final Color ROW_SELECTED_BG = new Color(67, 150, 209); // Fondo selección
    private final Color ROW_SELECTED_FG = Color.WHITE;      // Texto selección
    private final Color ROW_PAID_BG = new Color(230, 230, 230); // Fondo filas pagadas
    private final Color ROW_PAID_FG = new Color(138, 138, 138); // Texto filas pagadas

    private JPopupMenu filtrosMenu;
    private JCheckBoxMenuItem chkPendientes;
    private JCheckBoxMenuItem chkAbonosParciales;
    private JCheckBoxMenuItem chkPagadosCompletos;
    private JCheckBoxMenuItem chkTodos;
    private List<String> clientesDisponibles = new ArrayList<>();
    private List<JCheckBox> chkClientesItems = new ArrayList<>();
    private JRadioButton radioTodos, radioPendientes, radioAbonos, radioPagados;

    final int COL_MONTO_TOTAL = 3;
    final int COL_PAGADO = 4;
    final int COL_DEBIDO = 5;

    /**
     * Creates new form Ingresos
     */
    public ingresos(JPanel contenedor) {
        this.contenedor = contenedor;

        // Inicializar el controlador
        controlador = new Ctrl_CajaIngresos();
        generadorPDF = new GeneradorIngresosPDF();
        ctrlPedido = new Ctrl_Pedido();

        initComponents();

        aplicarTema();
        TemaManager.getInstance().addThemeChangeListener(this::aplicarTema);

        jPanel1.setBackground(TemaManager.getInstance().isOscuro() ? new Color(21, 21, 33) : new Color(242, 247, 255));
        txtbuscar.setBackground(TemaManager.getInstance().isOscuro() ? new Color(37, 37, 52) : new Color(242, 247, 255));
        txtbuscar.setForeground(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
        txtbuscar.setColorIcon(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
        txtbuscar.setPhColor(TemaManager.getInstance().isOscuro() ? Color.LIGHT_GRAY : Color.GRAY);

        // Configuración COMPLETA de la tabla
        Tabla1.setBackground(TemaManager.getInstance().isOscuro() ? new Color(30, 30, 45) : new Color(255, 255, 255));
        Tabla1.setForeground(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
        // Configuración de filas
        Tabla1.setColorPrimary(TemaManager.getInstance().isOscuro() ? new Color(37, 37, 52) : new Color(242, 242, 242));  // Filas impares
        Tabla1.setColorSecondary(TemaManager.getInstance().isOscuro() ? new Color(37, 37, 52) : new Color(242, 242, 242)); // Filas pares
        Tabla1.setColorPrimaryText(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
        Tabla1.setColorSecundaryText(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
        // Encabezados
        Tabla1.setBackgoundHead(TemaManager.getInstance().isOscuro() ? new Color(67, 71, 120) : new Color(46, 49, 82));
        Tabla1.setForegroundHead(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.WHITE);
        Tabla1.setColorBorderHead(TemaManager.getInstance().isOscuro() ? new Color(67, 71, 120) : new Color(72, 92, 188));
        // Selección y hover
        Tabla1.setSelectionBackground(TemaManager.getInstance().isOscuro() ? new Color(40, 50, 90) : new Color(67, 150, 209));
        Tabla1.setBackgoundHover(TemaManager.getInstance().isOscuro() ? new Color(40, 50, 90) : new Color(67, 150, 209));
        // Bordes y grid
        Tabla1.setColorBorderRows(TemaManager.getInstance().isOscuro() ? new Color(60, 60, 60) : new Color(0, 0, 0));
        Tabla1.setGridColor(TemaManager.getInstance().isOscuro() ? new Color(80, 80, 80) : Color.WHITE);
        Tabla1.setShowGrid(true);
        // Fuentes
        Tabla1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        Tabla1.setFontHead(new Font("Tahoma", Font.BOLD, 15));
        Tabla1.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
        Tabla1.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));

        filtar.setIcon(
                TemaManager.getInstance().isOscuro()
                ? new ImageIcon(getClass().getResource("/filtrar (2).png"))
                : new ImageIcon(getClass().getResource("/filtrar (1).png"))
        );
        // Efectos
        Tabla1.setEffectHover(true);

        Tabla1.setBorderHead(null);

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID Pedido", "Nombre pedido", "Cliente", "Monto total", "Pagado", "Debido", "Abonar", "Detalle", "Acciones", "ID"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer que ninguna celda sea editable
                return false;
            }
        });

        TableColumn numPedido = Tabla1.getColumnModel().getColumn(0);
        numPedido.setPreferredWidth(50); // Ajustar el ancho de la columna

        TableColumn nombre = Tabla1.getColumnModel().getColumn(1);
        nombre.setPreferredWidth(120); // Ajustar el ancho de la columna

        TableColumn abonar = Tabla1.getColumnModel().getColumn(6);
        abonar.setPreferredWidth(10); // Ajustar el ancho de la columna

        // Configurar la columna "Detalle"
        TableColumn detalles = Tabla1.getColumnModel().getColumn(7);
        detalles.setPreferredWidth(10);
        detalles.setCellRenderer(new ingresos.ButtonRenderer());

        TableColumn imprimir = Tabla1.getColumnModel().getColumn(8);
        imprimir.setPreferredWidth(10); // Ajustar el ancho de la columna

        // En el constructor de la clase:
        Tabla1.getColumnModel().getColumn(9).setMinWidth(0);
        Tabla1.getColumnModel().getColumn(9).setMaxWidth(0);
        Tabla1.getColumnModel().getColumn(9).setWidth(0);

        Tabla1.setShowHorizontalLines(true);
        Tabla1.setShowVerticalLines(true);

        // Configurar RowSorter
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) Tabla1.getModel());
        Tabla1.setRowSorter(sorter);

        // Cargar datos iniciales
        cargarDatosIniciales();
        configurarMenuFiltros();
        filtrarFilasPagadas();
// En el constructor, después de cargar datos iniciales:
        Tabla1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                boolean oscuro = TemaManager.getInstance().isOscuro();

                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                // Convertir fila de la vista a fila del modelo
                int modelRow = table.convertRowIndexToModel(row);
                TableModel m = table.getModel();

                // Verificar si está pagado
                String debidoStr = m.getValueAt(modelRow, 5).toString();
                boolean esPagado = debidoStr.equals("$0.00") || debidoStr.equals("$0,00");

                if (oscuro) {
                    if (isSelected) {
                        c.setBackground(new Color(67, 71, 120));
                        c.setForeground(Color.WHITE);
                        c.setFont(fontBold);
                    } else if (esPagado) {
                        c.setBackground(new Color(85, 85, 85));
                        c.setForeground(Color.WHITE);
                        c.setFont(fontNormal);
                    } else {
                        c.setBackground(new Color(37, 37, 52));
                        c.setForeground(Color.WHITE);
                    }

                } else {

                    if (isSelected) {
                        c.setBackground(ROW_SELECTED_BG);
                        c.setForeground(ROW_SELECTED_FG);
                        c.setFont(fontBold);
                    } else if (esPagado) {
                        c.setBackground(ROW_PAID_BG);
                        c.setForeground(ROW_PAID_FG);
                        c.setFont(fontNormal);
                    } else {
                        c.setBackground(BG_NORMAL);
                        c.setForeground(FG_NORMAL);
                    }

                }

                setHorizontalAlignment(CENTER);
                setBorder(BorderFactory.createLineBorder(oscuro ? new Color(153, 153, 153) : new Color(153, 153, 153), 1));

                return c;
            }
        });

        txtbuscar.getDocument().addDocumentListener(new DocumentListener() {
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
        });

        /// Versión con tamaño de fuente aumentado a 14px
        filtar.setToolTipText("<html><body style='"
                + "background-color: black;"
                + "color: white;"
                + "font-weight: bold;"
                + "font-size: 11px;" // Tamaño aumentado (normal es 10-12px)
                + "margin: 0;"
                + "padding: 5px;" // Espacio interno para mejor visualización
                + "'>Filtrar materiales</body></html>");

// Quitar el borde del tooltip
        ToolTipManager.sharedInstance().setInitialDelay(500);
        UIManager.put("ToolTip.border", BorderFactory.createEmptyBorder());

    }

    // Método para cargar datos iniciales en la tabla
    public void cargarDatosIniciales() {
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();

        model.setRowCount(0); // Limpiar la tabla

        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        DecimalFormat df = new DecimalFormat("$#,##0.00", symbols);

        List<Ctrl_CajaIngresos.IngresoConDetalles> ingresos = controlador.obtenerIngresos();
        if (ingresos == null || ingresos.isEmpty()) {
            System.out.println("No se encontraron ingresos para cargar en la tabla.");
            return;
        }

        for (Ctrl_CajaIngresos.IngresoConDetalles ingreso : ingresos) {
            model.addRow(new Object[]{
                ingreso.getNumPedido(),
                ingreso.getNombrePedido(), // Ajustado a numPedido
                ingreso.getNombreCliente(),
                df.format(ingreso.getMontoTotal()),
                df.format(ingreso.getPagado()),
                df.format(ingreso.getDebido()),
                "Abonar",
                "Ver",
                "Imprimir",
                ingreso.getIdPedido()
            });
        }
    }

    private class ButtonRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {

            boolean oscuro = TemaManager.getInstance().isOscuro();

            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            int modelRow = table.convertRowIndexToModel(row);
            TableModel m = table.getModel();
            String debidoStr = m.getValueAt(modelRow, 5).toString();
            boolean esPagado = debidoStr.equals("$0.00") || debidoStr.equals("$0,00");

            if (oscuro) {
                if (isSelected) {
                    c.setBackground(new Color(67, 71, 120));
                    c.setForeground(Color.WHITE);
                    c.setFont(fontBold);
                } else if (esPagado) {
                    c.setBackground(new Color(85, 85, 85));
                    c.setForeground(Color.WHITE);
                    c.setFont(fontNormal);
                } else {
                    c.setBackground(new Color(37, 37, 52));
                    c.setForeground(Color.WHITE);
                }

            } else {

                if (isSelected) {
                    c.setBackground(ROW_SELECTED_BG);
                    c.setForeground(ROW_SELECTED_FG);
                    c.setFont(fontBold);
                } else if (esPagado) {
                    c.setBackground(ROW_PAID_BG);
                    c.setForeground(ROW_PAID_FG);
                    c.setFont(fontNormal);
                } else {
                    c.setBackground(BG_NORMAL);
                    c.setForeground(FG_NORMAL);
                }

            }

            setHorizontalAlignment(CENTER);
            setText("Ver");
            setBorder(BorderFactory.createLineBorder(oscuro ? new Color(153, 153, 153) : new Color(153, 153, 153), 1));
            return c;
        }
    }

    private void mostrarDetallesPedido(String id) {
        ingDetalles detalles = new ingDetalles(id, contenedor);
        detalles.setSize(1290, 730);
        detalles.setLocation(0, 0);
        contenedor.removeAll();
        contenedor.add(detalles);
        contenedor.revalidate();
        contenedor.repaint();
    }

    // Método auxiliar para formatear moneda
    private String formatCurrency(double amount) {
        DecimalFormat df = new DecimalFormat("$#,##0.00");
        return df.format(amount);
    }

    public void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            // Configuración para modo oscuro
            Color fondo = new Color(21, 21, 33);
            Color fondoTabla = new Color(30, 30, 45);
            Color encabezado = new Color(67, 71, 120);
            Color texto = Color.WHITE;

            jPanel1.setBackground(fondo);
            txtbuscar.setBackground(new Color(37, 37, 52));
            txtbuscar.setForeground(texto);
            txtbuscar.setColorIcon(texto);
            txtbuscar.setPhColor(Color.LIGHT_GRAY);

            // Configuración de la tabla
            Tabla1.setBackground(fondoTabla);
            Tabla1.setForeground(texto);
            Tabla1.setColorPrimary(new Color(37, 37, 52));  // Filas impares
            Tabla1.setColorSecondary(new Color(37, 37, 52)); // Filas pares
            Tabla1.setBackgoundHead(encabezado);
            Tabla1.setForegroundHead(texto);
            Tabla1.setColorBorderHead(encabezado);
            Tabla1.setSelectionBackground(new Color(40, 50, 90));
            Tabla1.setBackgoundHover(new Color(40, 50, 90));
            Tabla1.setColorBorderRows(new Color(60, 60, 60));
            Tabla1.setGridColor(new Color(80, 80, 80));

            // Fuentes
            Tabla1.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Tabla1.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));

            filtar.setIcon(new ImageIcon(getClass().getResource("/filtrar (2).png")));

            // Efectos
            Tabla1.setEffectHover(true);

        } else {
            // Configuración para modo claro
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;

            jPanel1.setBackground(fondo);
            txtbuscar.setBackground(fondo);
            txtbuscar.setForeground(texto);
            txtbuscar.setColorIcon(texto);
            txtbuscar.setPhColor(Color.GRAY);

            // Configuración de la tabla
            Tabla1.setBackground(Color.WHITE);
            Tabla1.setForeground(texto);
            Tabla1.setBackgoundHead(HEADER_BG);
            Tabla1.setForegroundHead(HEADER_FG);
            Tabla1.setColorBorderHead(HEADER_BG);
            Tabla1.setSelectionBackground(ROW_SELECTED_BG);
            Tabla1.setBackgoundHover(ROW_SELECTED_BG);

            // Fuentes
            Tabla1.setFont(new Font("Tahoma", Font.PLAIN, 15));
            Tabla1.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            Tabla1.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));

            filtar.setIcon(new ImageIcon(getClass().getResource("/filtrar (1).png")));

            // Efectos
            Tabla1.setEffectHover(true);

        }
    }

    private void configurarMenuFiltros() {
        chkClientesItems.clear();
        filtrosMenu = new JPopupMenu();
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Reducir márgenes a 5px

        Font fuenteTexto = new Font("Segoe UI", Font.PLAIN, 12);
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 13);
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 14);

        // 1. Sección de estado de pago
        JPanel estadoPanel = new JPanel();
        estadoPanel.setLayout(new BoxLayout(estadoPanel, BoxLayout.Y_AXIS)); // Columna vertical
        estadoPanel.setBorder(BorderFactory.createTitledBorder("Estado de pago"));
        ((TitledBorder) estadoPanel.getBorder()).setTitleFont(fuenteTitulo);
        estadoPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        ButtonGroup estadoGroup = new ButtonGroup();
        radioTodos = new JRadioButton("Todos", true);
        radioPendientes = new JRadioButton("Pendientes (sin pagos)");
        radioAbonos = new JRadioButton("Con abonos parciales");
        radioPagados = new JRadioButton("Pagados completamente");

        radioTodos.setFont(fuenteTexto);
        radioPendientes.setFont(fuenteTexto);
        radioAbonos.setFont(fuenteTexto);
        radioPagados.setFont(fuenteTexto);

        estadoGroup.add(radioTodos);
        estadoGroup.add(radioPendientes);
        estadoGroup.add(radioAbonos);
        estadoGroup.add(radioPagados);

        // Agregar todos los radio buttons en una columna con alineación a la izquierda
        estadoPanel.add(radioTodos);
        estadoPanel.add(Box.createVerticalStrut(2)); // Espaciado mínimo entre opciones
        estadoPanel.add(radioPendientes);
        estadoPanel.add(Box.createVerticalStrut(2));
        estadoPanel.add(radioAbonos);
        estadoPanel.add(Box.createVerticalStrut(2));
        estadoPanel.add(radioPagados);

        panelFiltros.add(estadoPanel);
        panelFiltros.add(Box.createVerticalStrut(15));

        // 2. Sección de clientes
        obtenerClientesDisponibles();
        JPanel clientesPanel = new JPanel(new GridLayout(0, 1));
        clientesPanel.setBorder(BorderFactory.createTitledBorder("Clientes"));
        ((TitledBorder) clientesPanel.getBorder()).setTitleFont(fuenteTitulo);
        clientesPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda

        for (String cliente : clientesDisponibles) {
            JCheckBox chkCliente = new JCheckBox(cliente, true);
            chkCliente.setFont(fuenteTexto);
            chkClientesItems.add(chkCliente);
            clientesPanel.add(chkCliente);
        }

        if (clientesDisponibles.size() > 4) {
            JScrollPane scroll = new JScrollPane(clientesPanel);
            scroll.setPreferredSize(new Dimension(180, 120));
            panelFiltros.add(scroll);
        } else {
            panelFiltros.add(clientesPanel);
        }

        // 3. Botón de aplicar
        RSButtonRiple btnAplicar = new RSButtonRiple();
        btnAplicar.setText("Aplicar");
        btnAplicar.setBackground(new Color(180, 180, 180));
        btnAplicar.setColorHover(new Color(150, 150, 150));
        btnAplicar.setPreferredSize(new Dimension(100, 25));
        btnAplicar.setFont(fuenteBoton);
        btnAplicar.setAlignmentX(Component.LEFT_ALIGNMENT); // Alinear a la izquierda
        btnAplicar.addActionListener(e -> {
            aplicarFiltrosAvanzados();
            filtrosMenu.setVisible(false);
        });

        panelFiltros.add(btnAplicar);
        filtrosMenu.add(panelFiltros);

        filtar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                filtrosMenu.show(filtar, 0, filtar.getHeight());
            }
        });
    }

    // 3. Método para obtener clientes únicos
    private void obtenerClientesDisponibles() {
        clientesDisponibles.clear();
        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();

        Set<String> clientesUnicos = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String cliente = model.getValueAt(i, 2).toString(); // Columna 2 = Cliente
            clientesUnicos.add(cliente);
        }

        clientesDisponibles.addAll(clientesUnicos);
        Collections.sort(clientesDisponibles);
    }

    private void aplicarFiltrosAvanzados() {
        // Guardar selecciones de clientes
        clientesDisponibles.clear();
        for (JCheckBox chk : chkClientesItems) {
            if (chk.isSelected()) {
                clientesDisponibles.add(chk.getText());
            }
        }

        DefaultTableModel model = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) Tabla1.getRowSorter();

        Map<String, Integer> columnIndices = new HashMap<>();
        for (int i = 0; i < model.getColumnCount(); i++) {
            columnIndices.put(model.getColumnName(i), i);
        }

        List<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // Filtro por estado de pago con radio buttons
        if (radioPendientes.isSelected()) {
            // Pendientes: Pagado = 0 y Debido > 0
            filtros.add(RowFilter.andFilter(Arrays.asList(
                    RowFilter.regexFilter("^\\$0,00", columnIndices.get("Pagado"))
            )));
        } else if (radioAbonos.isSelected()) {
            // Con abonos: Pagado > 0 y Debido > 0
            filtros.add(RowFilter.andFilter(Arrays.asList(
                    RowFilter.notFilter(RowFilter.regexFilter("^\\$0,00", columnIndices.get("Pagado")))
            )));
        } else if (radioPagados.isSelected()) {
            // Pagados completamente: Debido = 0 (o Pagado = MontoTotal)
            filtros.add(RowFilter.regexFilter("^\\$0,00", columnIndices.get("Debido")));

        }

        // Filtro por clientes seleccionados
        List<String> clientesSeleccionados = chkClientesItems.stream()
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());

        if (!clientesSeleccionados.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)^(" + String.join("|", clientesSeleccionados) + ")$",
                    columnIndices.get("Cliente")));
        }

        // Combinar todos los filtros
        if (!filtros.isEmpty()) {
            RowFilter<Object, Object> combinedFilter = RowFilter.andFilter(filtros);
            sorter.setRowFilter(combinedFilter);
        } else {
            sorter.setRowFilter(null);
        }
    }

    /**
     * ✅ Método auxiliar para convertir valores como "$1,200.50" a double
     */
    private double parseCurrency(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value.replace("$", "").replace(",", "").trim());
        } catch (NumberFormatException e) {
            return 0.0;
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
        jScrollPane3 = new javax.swing.JScrollPane();
        Tabla1 = new RSMaterialComponent.RSTableMetroCustom();
        filtar = new rojerusan.RSLabelImage();

        jPanel1.setBackground(new java.awt.Color(242, 247, 255));
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

        Tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID pedido", "Nombre pedido", "Cliente", "Monto total", "Pagado", "Debido", "Abonar", "Detalle", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Double.class, java.lang.Double.class, java.lang.Double.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Tabla1.setToolTipText("");
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
        Tabla1.setPreferredSize(new java.awt.Dimension(675, 463));
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

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 1190, 500));

        filtar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/filtrar (1).png"))); // NOI18N
        filtar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                filtarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                filtarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                filtarMouseExited(evt);
            }
        });
        jPanel1.add(filtar, new org.netbeans.lib.awtextra.AbsoluteConstraints(482, 35, 34, 34));

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

    private void Tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Tabla1MouseClicked
        // 1. Obtener posición del clic en la vista
        int viewRow = Tabla1.rowAtPoint(evt.getPoint());
        int viewColumn = Tabla1.columnAtPoint(evt.getPoint());

        if (viewRow >= 0 && viewColumn >= 0) {
            // 2. Convertir índices de vista a modelo (para manejar filtros/ordenamiento)
            int modelRow = Tabla1.convertRowIndexToModel(viewRow);
            int modelColumn = Tabla1.convertColumnIndexToModel(viewColumn);

            // 3. Obtener datos clave del modelo
            int idPedido = (int) Tabla1.getModel().getValueAt(modelRow, 9); // Columna oculta
            String numPedido = Tabla1.getModel().getValueAt(modelRow, 0).toString();

            // 4. Obtener el objeto ingreso completo
            Ctrl_CajaIngresos.IngresoConDetalles ingreso = controlador.obtenerIngresos().stream()
                    .filter(i -> i.getIdPedido() == idPedido)
                    .findFirst()
                    .orElse(null);

            if (ingreso == null) {
                JOptionPane.showMessageDialog(this, "No se encontró el pedido seleccionado", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 5. Manejar acciones según la columna clickeada
            switch (modelColumn) {
                case 6: // Columna "Abonar"
                    manejarAbono(ingreso, numPedido);
                    break;

                case 7: // Columna "Detalle"
                    mostrarDetallesPedido(String.valueOf(idPedido));
                    break;

                case 8: // Columna "Acciones"
                    manejarImpresion(ingreso, idPedido);
                    break;
            }
        }
    }

// Métodos auxiliares separados para mejor organización
    private void manejarAbono(Ctrl_CajaIngresos.IngresoConDetalles ingreso, String numPedido) {
        try {
            if (ingreso.getDebido() <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Este pedido ya ha sido pagado completamente.\n"
                        + "Total pagado: " + formatCurrency(ingreso.getPagado()) + "\n"
                        + "Monto total: " + formatCurrency(ingreso.getMontoTotal()),
                        "Pago Completo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
            iAbonoNuevo dialog = new iAbonoNuevo(
                    parentFrame,
                    true,
                    ingreso.getIdPedido(),
                    numPedido,
                    controlador
            );
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);

            if (dialog.isGuardado()) {
                cargarDatosIniciales();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al abrir el diálogo de abono: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void manejarImpresion(Ctrl_CajaIngresos.IngresoConDetalles ingreso, int idPedido) {
        try {
            Ctrl_Pedido.MaterialConDetalles material = ctrlPedido.obtenerPedidoPorId(idPedido);
            if (material == null) {
                JOptionPane.showMessageDialog(this,
                        "No se encontró el pedido para el ID: " + idPedido,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            List<PedidoDetalle> detalles = ctrlPedido.obtenerDetallesPorPedido(idPedido);
            if (detalles == null || detalles.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No se encontraron detalles para el pedido",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Configuración de formato
            DecimalFormat df = new DecimalFormat("$#,##0.00", new DecimalFormatSymbols(Locale.US));
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            // Generar PDF
            String archivoSalida = "ingreso_" + material.getPedido().getNum_pedido() + "_"
                    + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".pdf";

            generadorPDF.generarPDF(
                    ingreso.getNombreCliente(),
                    ingreso.getCodigoCliente(),
                    ingreso.getTelefonoCliente(),
                    ingreso.getDireccionCliente(),
                    ingreso.getDepartamentoCliente(),
                    ingreso.getMunicipioCliente(),
                    crearModeloParaPDF(detalles, ingreso, df, sdf),
                    df.format(ingreso.getMontoTotal()),
                    archivoSalida,
                    material.getPedido().getFecha_inicio() != null
                    ? sdf.format(material.getPedido().getFecha_inicio()) : "Sin fecha",
                    material.getPedido().getNum_pedido(),
                    df.format(ingreso.getPagado()),
                    df.format(ingreso.getDebido())
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error al generar PDF: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private DefaultTableModel crearModeloParaPDF(List<PedidoDetalle> detalles,
            Ctrl_CajaIngresos.IngresoConDetalles ingreso,
            DecimalFormat df,
            SimpleDateFormat sdf) {
        DefaultTableModel model = new DefaultTableModel(
                new Object[detalles.size() + (ingreso.getAbonos().isEmpty() ? 1 : 2)][6],
                new String[]{"Descripción", "Cantidad", "Dimensiones", "Precio Unitario", "Subtotal", "Total"}
        );

        // Llenar detalles
        for (int i = 0; i < detalles.size(); i++) {
            PedidoDetalle detalle = detalles.get(i);
            model.setValueAt(detalle.getDescripcion(), i, 0);
            model.setValueAt(detalle.getCantidad(), i, 1);
            model.setValueAt(detalle.getDimensiones(), i, 2);
            model.setValueAt(df.format(detalle.getPrecioUnitario()), i, 3);
            model.setValueAt(df.format(detalle.getSubtotal()), i, 4);
            model.setValueAt(df.format(detalle.getTotal()), i, 5);
        }

        // Agregar abonos si existen
        if (!ingreso.getAbonos().isEmpty()) {
            int row = detalles.size();
            model.setValueAt("ABONOS REGISTRADOS", row, 0);

            StringBuilder abonosInfo = new StringBuilder();
            for (Ingresos abono : ingreso.getAbonos()) {
                abonosInfo.append("Abono #").append(abono.getNumAbono())
                        .append(" - ").append(sdf.format(abono.getFechaPago()))
                        .append(": ").append(df.format(abono.getMonto()))
                        .append(" (").append(abono.getMetodoPago()).append(")\n");
            }
            model.setValueAt(abonosInfo.toString(), row, 1);
        }

        return model;

    }//GEN-LAST:event_Tabla1MouseClicked
    private void filtrarFilasPagadas() {
        TableRowSorter<DefaultTableModel> sorter = (TableRowSorter<DefaultTableModel>) Tabla1.getRowSorter();

        // Filtro para excluir filas donde la columna "Debido" sea "$0.00"
        RowFilter<DefaultTableModel, Object> filter = RowFilter.regexFilter("^(?!\\$0\\.00|\\$0,00).*", 5); // Columna 5 = "Debido"

        sorter.setRowFilter(filter);
    }
    private void filtarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseClicked

    }//GEN-LAST:event_filtarMouseClicked

    private void filtarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mano al pasar
    }//GEN-LAST:event_filtarMouseEntered

    private void filtarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseExited
        setCursor(Cursor.getDefaultCursor()); // Cursor normal al salir
    }//GEN-LAST:event_filtarMouseExited

    private void filtrarTabla() {
        String textoBusqueda = txtbuscar.getText().trim().toLowerCase(); // Convertir a minúsculas
        DefaultTableModel modelo = (DefaultTableModel) Tabla1.getModel();
        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<>(modelo);
        Tabla1.setRowSorter(tr);

        if (textoBusqueda.isEmpty()) {
            tr.setRowFilter(null);
            return;
        }

        // Crear un filtro que ignore mayúsculas/minúsculas
        RowFilter<DefaultTableModel, Object> filter = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                // Buscar en todas las columnas visibles (excepto las últimas 3 que son botones)
                for (int i = 0; i < entry.getValueCount() - 3; i++) {
                    String value = entry.getStringValue(i).toLowerCase();
                    if (value.contains(textoBusqueda)) {
                        return true;
                    }
                }
                return false;
            }
        };

        tr.setRowFilter(filter);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSTableMetroCustom Tabla1;
    private rojerusan.RSLabelImage filtar;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtbuscar;
    // End of variables declaration//GEN-END:variables
}
