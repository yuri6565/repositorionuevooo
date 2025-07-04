/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import controlador.Ctrl_Cliente;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import modelo.Cliente;
import modelo.Conexion;
import rojeru_san.RSButton;
import rojeru_san.efectos.ValoresEnum;
import rojerusan.RSLabelIcon;
import vista.Usuarios1.CustomCheckboxEditor;
import vista.Usuarios1.CustomCheckboxRenderer;

/**
 *
 * @author ZenBook
 */
public class VistaClientes extends javax.swing.JPanel {

    private int id_cliente;
    private Ctrl_Cliente controlador;
    private int currentPage = 0;
    private final int CLIENTES_POR_PAGINA = 19;
    private List<Cliente> todasLasClientes = new ArrayList<>();
    private boolean[] seleccionados;
    private TableRowSorter<DefaultTableModel> sorter; // Declaración de sorter
    private JPopupMenu popupFiltrosAvanzados; // Popup para filtros avanzados
    private List<JCheckBox> chkEstados = new ArrayList<>(); // Lista para checkboxes de estados
    private List<JCheckBox> chkDepartamentos = new ArrayList<>(); // Lista para checkboxes de departamentos
    private List<JCheckBox> chkProductos = new ArrayList<>(); // Lista para checkboxes de tipos de documento
    private rojeru_san.RSButtonRiple btnAplicarFiltros;

    public VistaClientes(JFrame jFrame, boolean par) {
        controlador = new Ctrl_Cliente();
        initComponents();
        jPanel1.setPreferredSize(new java.awt.Dimension(1340, 750));
        jScrollPane3.setPreferredSize(new java.awt.Dimension(1200, 550));
        rSCheckBox1.addActionListener(e -> seleccionarTodo());
        cargartablaclientes();
        aplicarTema();
        rSButtonMaterialRippleIcon1.setVisible(false); // Ocultar botón por defecto
        TemaManager.getInstance().addThemeChangeListener(this::aplicarTema);

        // Agregar DocumentListener para búsqueda dinámica
        txtBuscar.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                buscarDinamico();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                buscarDinamico();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                buscarDinamico();
            }
        });
    }

    public void cargartablaclientes() {
        tablaclientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 0 || columna == 9; // "Selec" y "Acciones"
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        model.addColumn("Selec");
        model.addColumn("Código");
        model.addColumn("Doc. Tipo");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Teléfono");
        model.addColumn("Departamento/Municipio");
        model.addColumn("Dirección");
        model.addColumn("Estado");
        model.addColumn("Acciones");

        List<Cliente> clientes = controlador.obtenerClientes();
        todasLasClientes = new ArrayList<>(clientes);
        System.out.println("Número de clientes cargados: " + todasLasClientes.size());
        seleccionados = new boolean[todasLasClientes.size()];

        for (Cliente cliente : todasLasClientes) {
            String ubicacion = (cliente.getDepartamento() != null ? cliente.getDepartamento() : "Sin departamento") + "/"
                    + (cliente.getMunicipio() != null ? cliente.getMunicipio() : "Sin municipio");
            model.addRow(new Object[]{
                false,
                String.valueOf(cliente.getId_cliente()),
                cliente.getIdentificacion() != null ? cliente.getIdentificacion() : "Sin identificación",
                cliente.getNombre() != null ? cliente.getNombre() : "Sin nombre",
                cliente.getApellido() != null ? cliente.getApellido() : "Sin apellido",
                cliente.getTelefono() != null ? cliente.getTelefono() : "Sin teléfono",
                ubicacion,
                cliente.getDireccion() != null ? cliente.getDireccion() : "Sin dirección",
                cliente.isActivo() ? "Activo" : "Inactivo",
                ""
            });
        }

        tablaclientes.setModel(model);
        mostrarPagina(currentPage);

        // Configurar renderers y editores
        tablaclientes.getColumnModel().getColumn(0).setCellRenderer(new CustomCheckboxRenderer());
        tablaclientes.getColumnModel().getColumn(0).setCellEditor(new CustomCheckboxEditor());
        tablaclientes.getColumnModel().getColumn(8).setCellRenderer(new StateCellRenderer());
        tablaclientes.getColumnModel().getColumn(9).setCellRenderer(new ButtonPanelRenderer());
        tablaclientes.getColumnModel().getColumn(9).setCellEditor(new ButtonPanelEditor(new JCheckBox()));

        // Ajustar anchos
        tablaclientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaclientes.getColumnModel().getColumn(1).setPreferredWidth(70);
        tablaclientes.getColumnModel().getColumn(2).setPreferredWidth(70);
        tablaclientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaclientes.getColumnModel().getColumn(4).setPreferredWidth(100);
        tablaclientes.getColumnModel().getColumn(5).setPreferredWidth(80);
        tablaclientes.getColumnModel().getColumn(6).setPreferredWidth(190);
        tablaclientes.getColumnModel().getColumn(7).setPreferredWidth(100);
        tablaclientes.getColumnModel().getColumn(8).setPreferredWidth(80);
        tablaclientes.getColumnModel().getColumn(9).setPreferredWidth(80);
        tablaclientes.setRowHeight(24);

        // Listener para detectar cambios en la selección
        tablaclientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                actualizarEstadoBotonAccion();
            }
        });
        tablaclientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = tablaclientes.rowAtPoint(e.getPoint());
                if (fila_point > -1) {
                    try {
                        id_cliente = Integer.parseInt(tablaclientes.getValueAt(fila_point, 1).toString());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error al parsear id_cliente: " + ex.getMessage());
                    }
                }
                actualizarEstadoBotonAccion();
            }
        });
    }

    private void actualizarEstadoBotonAccion() {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        List<Integer> seleccionadosIds = new ArrayList<>();
        List<String> estados = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
                int id = Integer.parseInt(model.getValueAt(i, 1).toString());
                String estado = model.getValueAt(i, 8).toString().toLowerCase();
                seleccionadosIds.add(id);
                estados.add(estado);
            }
        }

        if (seleccionadosIds.isEmpty()) {
            rSButtonMaterialRippleIcon1.setVisible(false);
        } else {
            boolean todosInactivos = estados.stream().allMatch("inactivo"::equals);
            boolean todosActivos = estados.stream().allMatch("activo"::equals);

            if (todosInactivos) {
                rSButtonMaterialRippleIcon1.setVisible(true);
                rSButtonMaterialRippleIcon1.setToolTipText("Activar clientes seleccionados");
                rSButtonMaterialRippleIcon1.setIcons(ValoresEnum.ICONS.CHECK_CIRCLE);
                rSButtonMaterialRippleIcon1.setForeground(new Color(0, 102, 204));
            } else if (todosActivos) {
                rSButtonMaterialRippleIcon1.setVisible(true);
                rSButtonMaterialRippleIcon1.setToolTipText("Desactivar clientes seleccionados");
                rSButtonMaterialRippleIcon1.setIcons(ValoresEnum.ICONS.CANCEL);
                rSButtonMaterialRippleIcon1.setForeground(Color.GRAY);
            } else {
                rSButtonMaterialRippleIcon1.setVisible(false);
            }
        }
    }

    private void mostrarPagina(int pagina) {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        model.setRowCount(0);

        int inicio = pagina * CLIENTES_POR_PAGINA;
        int fin = Math.min(inicio + CLIENTES_POR_PAGINA, todasLasClientes.size());

        if (inicio >= todasLasClientes.size()) {
            currentPage = 0;
            inicio = 0;
            fin = Math.min(CLIENTES_POR_PAGINA, todasLasClientes.size());
        }

        for (int i = inicio; i < fin; i++) {
            Cliente cliente = todasLasClientes.get(i);
            String ubicacion = (cliente.getDepartamento() != null ? cliente.getDepartamento() : "") + "/"
                    + (cliente.getMunicipio() != null ? cliente.getMunicipio() : "");
            model.addRow(new Object[]{
                seleccionados[i],
                String.valueOf(cliente.getId_cliente()),
                cliente.getIdentificacion() != null ? cliente.getIdentificacion() : "",
                cliente.getNombre() != null ? cliente.getNombre() : "",
                cliente.getApellido() != null ? cliente.getApellido() : "",
                cliente.getTelefono() != null ? cliente.getTelefono() : "",
                ubicacion,
                cliente.getDireccion() != null ? cliente.getDireccion() : "",
                cliente.isActivo() ? "Activo" : "Inactivo",
                ""
            });
        }

        int totalPaginas = (int) Math.ceil((double) todasLasClientes.size() / CLIENTES_POR_PAGINA);
        paginacion.setText("Página " + (currentPage + 1) + " de " + totalPaginas);
        Añadir5.setEnabled(currentPage > 0);
        Añadir4.setEnabled(currentPage < totalPaginas - 1);
    }

    private void seleccionarTodo() {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        boolean seleccionado = rSCheckBox1.isSelected();
        int inicio = currentPage * CLIENTES_POR_PAGINA;
        int fin = Math.min(inicio + CLIENTES_POR_PAGINA, todasLasClientes.size());

        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(seleccionado, i, 0);
            seleccionados[inicio + i] = seleccionado;
        }
        actualizarEstadoBotonAccion();
    }

    private void buscarDinamico() {
        String textoBusqueda = txtBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            todasLasClientes = new ArrayList<>(controlador.obtenerClientes());
            seleccionados = new boolean[todasLasClientes.size()];
            currentPage = 0;
            mostrarPagina(currentPage);
            return;
        }

        List<Cliente> resultados = controlador.buscarClientePorTodos(textoBusqueda);
        if (!resultados.isEmpty()) {
            todasLasClientes = new ArrayList<>(resultados);
            seleccionados = new boolean[todasLasClientes.size()];
            currentPage = 0;
            mostrarPagina(currentPage);
        } else {
            JOptionPane.showMessageDialog(this, "No se encontraron clientes con el texto: " + textoBusqueda, "No encontrado", JOptionPane.WARNING_MESSAGE);
            todasLasClientes = new ArrayList<>();
            seleccionados = new boolean[todasLasClientes.size()];
            mostrarPagina(currentPage);
        }
    }

    class CustomCheckboxRenderer extends JPanel implements TableCellRenderer {

        private final JCheckBox checkBox;

        public CustomCheckboxRenderer() {
            setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
            setOpaque(true);
            checkBox = new JCheckBox();
            checkBox.setOpaque(true);
            add(checkBox);
            updateTheme();
            TemaManager.getInstance().addThemeChangeListener(this::updateTheme);
        }

        private void updateTheme() {
            boolean oscuro = TemaManager.getInstance().isOscuro();
            Color fondo = oscuro ? new Color(21, 21, 33) : Color.WHITE;
            setBackground(fondo);
            checkBox.setBackground(fondo);
            checkBox.setForeground(oscuro ? Color.WHITE : Color.BLACK);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            checkBox.setSelected(Boolean.TRUE.equals(value));
            if (isSelected) {
                setBackground(new Color(240, 240, 240));
                checkBox.setBackground(new Color(240, 240, 240));
            } else {
                updateTheme();
            }
            return this;
        }
    }

    class CustomCheckboxEditor extends DefaultCellEditor {

        private final JCheckBox checkBox;

        public CustomCheckboxEditor() {
            super(new JCheckBox());
            checkBox = (JCheckBox) getComponent();
            checkBox.setOpaque(true);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            boolean oscuro = TemaManager.getInstance().isOscuro();
            checkBox.setBackground(oscuro ? new Color(21, 21, 33) : Color.WHITE);
            checkBox.setSelected(Boolean.TRUE.equals(value));
            return checkBox;
        }

        @Override
        public Object getCellEditorValue() {
            return checkBox.isSelected();
        }
    }

    class StateCellRenderer extends JPanel implements TableCellRenderer {

        private final RSLabelIcon stateIcon;

        public StateCellRenderer() {
            setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 0));
            setOpaque(true);
            stateIcon = new RSLabelIcon();
            stateIcon.setPreferredSize(new Dimension(20, 20));
            add(stateIcon);
            updateTheme();
            TemaManager.getInstance().addThemeChangeListener(this::updateTheme);
        }

        private void updateTheme() {
            boolean oscuro = TemaManager.getInstance().isOscuro();
            setBackground(oscuro ? new Color(21, 21, 33) : Color.WHITE);
            stateIcon.setBackground(getBackground());
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String estado = value != null ? value.toString().toLowerCase() : "";
            if (estado.equals("activo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CHECK_CIRCLE);
                stateIcon.setForeground(new Color(0, 102, 204));
            } else if (estado.equals("inactivo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CANCEL);
                stateIcon.setForeground(Color.GRAY);
            } else {
                stateIcon.setIcons(ValoresEnum.ICONS.HELP);
                stateIcon.setForeground(Color.BLACK);
            }
            if (isSelected) {
                setBackground(table.getSelectionBackground());
            } else {
                updateTheme();
            }
            stateIcon.setBackground(getBackground());
            return this;
        }
    }

    class ButtonPanelRenderer extends JPanel implements TableCellRenderer {

        private RSLabelIcon editIcon;
        private RSLabelIcon stateIcon;

        public ButtonPanelRenderer() {
            setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));
            setOpaque(true);
            editIcon = new RSLabelIcon();
            editIcon.setIcons(ValoresEnum.ICONS.EDIT);
            editIcon.setToolTipText("Editar");
            editIcon.setPreferredSize(new Dimension(20, 20));
            stateIcon = new RSLabelIcon();
            stateIcon.setPreferredSize(new Dimension(20, 20));
            add(editIcon);
            add(stateIcon);
            updateTheme();
            TemaManager.getInstance().addThemeChangeListener(this::updateTheme);
        }

        private void updateTheme() {
            boolean oscuro = TemaManager.getInstance().isOscuro();
            Color fondo = oscuro ? new Color(21, 21, 33) : Color.WHITE;
            setBackground(fondo);
            editIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
            stateIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
            editIcon.setForeground(new Color(29, 30, 81));
            stateIcon.setForeground(new Color(29, 30, 81));
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            String estado = table.getValueAt(row, 8) != null ? table.getValueAt(row, 8).toString().toLowerCase() : "";
            if (estado.equals("activo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CANCEL);
                stateIcon.setForeground(Color.GRAY);
                stateIcon.setToolTipText("Desactivar");
            } else if (estado.equals("inactivo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CHECK_CIRCLE);
                stateIcon.setForeground(new Color(0, 102, 204));
                stateIcon.setToolTipText("Activar");
            } else {
                stateIcon.setIcons(ValoresEnum.ICONS.HELP);
                stateIcon.setForeground(Color.BLACK);
                stateIcon.setToolTipText("Estado Desconocido");
            }
            if (isSelected) {
                setBackground(new Color(240, 240, 240));
            } else {
                updateTheme();
            }
            return this;
        }
    }

    class ButtonPanelEditor extends DefaultCellEditor {

        private JPanel panel;
        private RSLabelIcon editIcon;
        private RSLabelIcon stateIcon;
        private String label;
        private boolean isPushed;
        private int selectedRow;

        public ButtonPanelEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel();
            panel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));
            panel.setOpaque(true);
            editIcon = new RSLabelIcon();
            editIcon.setIcons(ValoresEnum.ICONS.EDIT);
            editIcon.setToolTipText("Editar");
            editIcon.setPreferredSize(new Dimension(20, 20));
            stateIcon = new RSLabelIcon();
            stateIcon.setPreferredSize(new Dimension(20, 20));
            stateIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            panel.add(editIcon);
            panel.add(stateIcon);

            editIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    isPushed = true;
                    fireEditingStopped();
                    try {
                        String codigoStr = tablaclientes.getValueAt(selectedRow, 1).toString();
                        if (codigoStr == null || codigoStr.trim().isEmpty()) {
                            JOptionPane.showMessageDialog(VistaClientes.this, "Código de cliente no válido.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        int codigo = Integer.parseInt(codigoStr);
                        Cliente cliente = controlador.obtenerClientePorId(codigo);
                        if (cliente != null) {
                            EditarCliente2 dialog = new EditarCliente2((JFrame) SwingUtilities.getWindowAncestor(VistaClientes.this), true, cliente);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
                            if (dialog.isGuardado()) {
                                cargartablaclientes();
                            }
                        } else {
                            JOptionPane.showMessageDialog(VistaClientes.this, "No se encontró el cliente con código: " + codigo, "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(VistaClientes.this, "El código del cliente no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(VistaClientes.this, "Error al editar el cliente: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                        ex.printStackTrace();
                    }
                }
            });

            stateIcon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    isPushed = true;
                    fireEditingStopped();
                    try {
                        int codigo = Integer.parseInt(tablaclientes.getValueAt(selectedRow, 1).toString());
                        String estadoActual = tablaclientes.getValueAt(selectedRow, 8).toString().toLowerCase();
                        boolean activar = estadoActual.equals("inactivo");

                        if (activar) {
                            if (controlador.activar(codigo)) {
                                JOptionPane.showMessageDialog(VistaClientes.this, "Cliente activado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
                                model.setValueAt("Activo", selectedRow, 8);
                            } else {
                                JOptionPane.showMessageDialog(VistaClientes.this, "No se encontró el cliente o no se pudo activar.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            if (controlador.tienePedidos(codigo)) {
                                int opcion = JOptionPane.showConfirmDialog(VistaClientes.this,
                                        "Este cliente tiene pedidos asociados. ¿Desea marcarlo como inactivo?",
                                        "Cliente con Pedidos", JOptionPane.YES_NO_OPTION);
                                if (opcion == JOptionPane.YES_OPTION) {
                                    if (controlador.desactivar(codigo)) {
                                        JOptionPane.showMessageDialog(VistaClientes.this, "Cliente desactivado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
                                        model.setValueAt("Inactivo", selectedRow, 8);
                                    } else {
                                        JOptionPane.showMessageDialog(VistaClientes.this, "No se encontró el cliente o no se pudo desactivar.", "Error", JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                            } else {
                                if (controlador.desactivar(codigo)) {
                                    JOptionPane.showMessageDialog(VistaClientes.this, "Cliente desactivado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                                    DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
                                    model.setValueAt("Inactivo", selectedRow, 8);
                                } else {
                                    JOptionPane.showMessageDialog(VistaClientes.this, "No se encontró el cliente o no se pudo desactivar.", "Error", JOptionPane.ERROR_MESSAGE);
                                }
                            }
                        }
                        tablaclientes.repaint();
                        actualizarEstadoBotonAccion();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(VistaClientes.this, "El código del cliente no es un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(VistaClientes.this, "Error al cambiar el estado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });

            updateTheme();
            TemaManager.getInstance().addThemeChangeListener(this::updateTheme);
        }

        private void updateTheme() {
            boolean oscuro = TemaManager.getInstance().isOscuro();
            Color fondo = oscuro ? new Color(21, 21, 33) : Color.WHITE;
            panel.setBackground(fondo);
            editIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
            stateIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
            editIcon.setForeground(new Color(29, 30, 81));
            stateIcon.setForeground(new Color(29, 30, 81));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            isPushed = true;
            selectedRow = row;

            String estado = (row >= 0 && row < table.getRowCount() && table.getValueAt(row, 8) != null)
                    ? table.getValueAt(row, 8).toString().toLowerCase()
                    : "";
            if (estado.equals("activo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CANCEL);
                stateIcon.setForeground(Color.GRAY);
                stateIcon.setToolTipText("Desactivar");
            } else if (estado.equals("inactivo")) {
                stateIcon.setIcons(ValoresEnum.ICONS.CHECK_CIRCLE);
                stateIcon.setForeground(new Color(0, 102, 204));
                stateIcon.setToolTipText("Activar");
            } else {
                stateIcon.setIcons(ValoresEnum.ICONS.HELP);
                stateIcon.setForeground(Color.BLACK);
                stateIcon.setToolTipText("Estado Desconocido");
            }

            if (row == table.getSelectedRow()) {
                panel.setBackground(new Color(240, 240, 240));
            } else {
                updateTheme();
            }
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            Color fondo = new Color(21, 21, 33);
            Color primario = new Color(40, 60, 150);
            Color texto = Color.WHITE;

            jPanel1.setBackground(fondo);
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.LIGHT_GRAY);
            paginacion.setBackground(texto);
            rSCheckBox1.setForeground(texto);
            rSCheckBox1.setColorCheck(texto);
            rSCheckBox1.setColorUnCheck(texto);
            rSButtonMaterialRippleIcon1.setBackground(fondo);

            tablaclientes.setBackground(fondo);
            tablaclientes.setBackgoundHead(new Color(67, 71, 120));
            tablaclientes.setForegroundHead(texto);
            tablaclientes.setBackgoundHover(new Color(40, 50, 90));
            tablaclientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
            tablaclientes.setColorPrimary(new Color(37, 37, 52));
            tablaclientes.setColorPrimaryText(texto);
            tablaclientes.setColorSecondary(new Color(30, 30, 45));
            tablaclientes.setColorSecundaryText(texto);
            tablaclientes.setColorBorderHead(primario);
            tablaclientes.setColorBorderRows(fondo.darker());
            tablaclientes.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setEffectHover(true);
            tablaclientes.setShowGrid(true);
            tablaclientes.setGridColor(Color.WHITE);

            btnNuevo1.setBackground(new Color(67, 71, 120));
            btnNuevo1.setBackgroundHover(new Color(118, 142, 240));
        } else {
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;
            Color primario = new Color(72, 92, 188);
            paginacion.setBackground(texto);
            rSCheckBox1.setForeground(new Color(21, 21, 33));
            rSCheckBox1.setColorCheck(new Color(21, 21, 33));
            rSCheckBox1.setColorUnCheck(new Color(21, 21, 33));
            rSButtonMaterialRippleIcon1.setBackground(fondo);

            jPanel1.setBackground(fondo);
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.GRAY);

            tablaclientes.setBackground(Color.WHITE);
            tablaclientes.setBackgoundHead(new Color(46, 49, 82));
            tablaclientes.setForegroundHead(Color.WHITE);
            tablaclientes.setBackgoundHover(new Color(67, 150, 209));
            tablaclientes.setFont(new Font("Tahoma", Font.PLAIN, 15));
            tablaclientes.setColorPrimary(new Color(242, 242, 242));
            tablaclientes.setColorPrimaryText(texto);
            tablaclientes.setColorSecondary(Color.WHITE);
            tablaclientes.setColorSecundaryText(texto);
            tablaclientes.setColorBorderHead(primario);
            tablaclientes.setColorBorderRows(Color.BLACK);
            tablaclientes.setFontHead(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setFontRowHover(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setFontRowSelect(new Font("Tahoma", Font.BOLD, 15));
            tablaclientes.setEffectHover(true);
            tablaclientes.setSelectionBackground(new Color(67, 150, 209));
            tablaclientes.setShowGrid(true);
            tablaclientes.setGridColor(Color.BLACK);

            btnNuevo1.setBackground(new Color(46, 49, 82));
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
        btnNuevo1 = new RSMaterialComponent.RSButtonShape();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaclientes = new RSMaterialComponent.RSTableMetroCustom();
        txtBuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        rSCheckBox1 = new rojerusan.RSCheckBox();
        Añadir5 = new rojeru_san.RSButtonRiple();
        Añadir4 = new rojeru_san.RSButtonRiple();
        rSButtonMaterialRippleIcon1 = new RSMaterialComponent.RSButtonMaterialRippleIcon();
        paginacion = new javax.swing.JLabel();
        btnNotificacion1 = new rojerusan.RSLabelIcon();

        setPreferredSize(new java.awt.Dimension(1290, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(18, 18, 18));
        jPanel1.setPreferredSize(new java.awt.Dimension(960, 570));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevo1.setBackground(new java.awt.Color(67, 94, 190));
        btnNuevo1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnNuevo1.setText(" Nuevo");
        btnNuevo1.setBackgroundHover(new java.awt.Color(118, 142, 240));
        btnNuevo1.setFocusable(false);
        btnNuevo1.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        btnNuevo1.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnNuevo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1140, 60, 110, 30));

        tablaclientes.setForeground(new java.awt.Color(153, 0, 204));
        tablaclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Seleccionar", "Codigo", "Doc.Tipo", "Nombre", "Apellido", "Telefono", "Departamento/municipio", "Direccion", "Estado", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, true, true, true, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaclientes.setToolTipText("");
        tablaclientes.setBackgoundHead(new java.awt.Color(44, 44, 44));
        tablaclientes.setBackgoundHover(new java.awt.Color(51, 255, 51));
        tablaclientes.setBorderHead(null);
        tablaclientes.setBorderRows(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tablaclientes.setColorBorderHead(new java.awt.Color(102, 102, 255));
        tablaclientes.setColorBorderRows(new java.awt.Color(255, 102, 102));
        tablaclientes.setColorPrimary(new java.awt.Color(37, 37, 52));
        tablaclientes.setColorPrimaryText(new java.awt.Color(0, 0, 0));
        tablaclientes.setColorSecondary(new java.awt.Color(0, 204, 153));
        tablaclientes.setColorSecundaryText(new java.awt.Color(30, 30, 45));
        tablaclientes.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tablaclientes.setFontHead(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setFontRowHover(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setFontRowSelect(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setForegroundHead(new java.awt.Color(224, 224, 224));
        tablaclientes.setGridColor(new java.awt.Color(102, 255, 102));
        tablaclientes.setPreferredSize(new java.awt.Dimension(500, 500));
        tablaclientes.setSelectionBackground(new java.awt.Color(67, 150, 209));
        jScrollPane3.setViewportView(tablaclientes);
        tablaclientes.getColumnModel().getColumn(0).setPreferredWidth(10);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 130, 1180, 520));

        txtBuscar.setBackground(new java.awt.Color(242, 247, 255));
        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorIcon(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorMaterial(new java.awt.Color(153, 153, 153));
        txtBuscar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
        txtBuscar.setPhColor(new java.awt.Color(102, 102, 102));
        txtBuscar.setPlaceholder("Buscar...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 430, 40));

        rSCheckBox1.setForeground(new java.awt.Color(102, 102, 255));
        rSCheckBox1.setText("Seleccionar Todo");
        jPanel1.add(rSCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 680, 190, 20));

        Añadir5.setBackground(new java.awt.Color(46, 49, 82));
        Añadir5.setText("Anterior");
        Añadir5.setColorHover(new java.awt.Color(0, 153, 51));
        Añadir5.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        Añadir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Añadir5ActionPerformed(evt);
            }
        });
        jPanel1.add(Añadir5, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 680, 90, 40));

        Añadir4.setBackground(new java.awt.Color(46, 49, 82));
        Añadir4.setText("Siguiente");
        Añadir4.setColorHover(new java.awt.Color(0, 153, 51));
        Añadir4.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        Añadir4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Añadir4ActionPerformed(evt);
            }
        });
        jPanel1.add(Añadir4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 680, 98, 40));

        rSButtonMaterialRippleIcon1.setBackground(new java.awt.Color(102, 102, 102));
        rSButtonMaterialRippleIcon1.setForeground(new java.awt.Color(253, 126, 20));
        rSButtonMaterialRippleIcon1.setBackgroundHover(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon1.setForegroundHover(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundIcon(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundIconHover(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundText(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.DELETE);
        rSButtonMaterialRippleIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonMaterialRippleIcon1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSButtonMaterialRippleIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 680, 40, 40));

        paginacion.setBackground(new java.awt.Color(0, 0, 0));
        paginacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        paginacion.setForeground(new java.awt.Color(51, 51, 51));
        paginacion.setText("Escritorio");
        paginacion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                paginacionMouseClicked(evt);
            }
        });
        jPanel1.add(paginacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 680, -1, -1));

        btnNotificacion1.setBackground(new java.awt.Color(255, 255, 255));
        btnNotificacion1.setForeground(new java.awt.Color(255, 255, 255));
        btnNotificacion1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.TUNE);
        btnNotificacion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNotificacion1MouseClicked(evt);
            }
        });
        jPanel1.add(btnNotificacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 750));
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        crear_cliente dialog = new crear_cliente(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        if (dialog.isGuardado()) {
            cargartablaclientes(); // Use the correct method with all columns
        }
    }//GEN-LAST:event_btnNuevo1ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        String textoBusqueda = txtBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            todasLasClientes = new ArrayList<>(controlador.obtenerClientes());
            currentPage = 0;
            seleccionados = new boolean[todasLasClientes.size()];
            mostrarPagina(currentPage);
            return;
        }

        try {
            int codigo = Integer.parseInt(textoBusqueda);
            Cliente cliente = controlador.buscarClientePorCodigo(codigo);
            if (cliente != null) {
                todasLasClientes = new ArrayList<>();
                todasLasClientes.add(cliente);
                seleccionados = new boolean[todasLasClientes.size()];
                currentPage = 0;
                mostrarPagina(currentPage);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró cliente con código: " + codigo, "No encontrado", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException e) {
            List<Cliente> resultados = controlador.buscarClientePorNombre(textoBusqueda);
            if (!resultados.isEmpty()) {
                todasLasClientes = new ArrayList<>(resultados);
                seleccionados = new boolean[todasLasClientes.size()];
                currentPage = 0;
                mostrarPagina(currentPage);
            } else {
                JOptionPane.showMessageDialog(this, "No se encontraron clientes con el nombre: " + textoBusqueda, "No encontrado", JOptionPane.WARNING_MESSAGE);
                todasLasClientes = new ArrayList<>();
                seleccionados = new boolean[todasLasClientes.size()];
                mostrarPagina(currentPage);
            }
        }

    }//GEN-LAST:event_txtBuscarActionPerformed

    private void Añadir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir5ActionPerformed
        if (currentPage > 0) {
            currentPage--;
            mostrarPagina(currentPage);
        }
    }//GEN-LAST:event_Añadir5ActionPerformed

    private void Añadir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir4ActionPerformed
        int totalPaginas = (int) Math.ceil((double) todasLasClientes.size() / CLIENTES_POR_PAGINA);
        if (currentPage < totalPaginas - 1) {
            currentPage++;
            mostrarPagina(currentPage);
        }
    }//GEN-LAST:event_Añadir4ActionPerformed

    private void rSButtonMaterialRippleIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonMaterialRippleIcon1ActionPerformed
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        List<Integer> seleccionadosIds = new ArrayList<>();
        List<String> estados = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
                int id = Integer.parseInt(model.getValueAt(i, 1).toString());
                String estado = model.getValueAt(i, 8).toString().toLowerCase();
                seleccionadosIds.add(id);
                estados.add(estado);
            }
        }

        boolean todosInactivos = estados.stream().allMatch("inactivo"::equals);
        boolean todosActivos = estados.stream().allMatch("activo"::equals);

        if (todosInactivos) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea activar " + seleccionadosIds.size() + " cliente(s)?",
                    "Confirmar activación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean todosExitosos = true;
                for (Integer id : seleccionadosIds) {
                    if (!controlador.activar(id)) {
                        todosExitosos = false;
                        JOptionPane.showMessageDialog(this, "No se encontró el cliente con ID: " + id + " o no se pudo activar.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (todosExitosos) {
                    JOptionPane.showMessageDialog(this, "Cliente(s) activado(s) exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                cargartablaclientes();
            }
        } else if (todosActivos) {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "¿Está seguro de que desea desactivar " + seleccionadosIds.size() + " cliente(s)?",
                    "Confirmar desactivación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean todosExitosos = true;
                for (Integer id : seleccionadosIds) {
                    if (controlador.tienePedidos(id)) {
                        int opcion = JOptionPane.showConfirmDialog(this,
                                "El cliente con ID " + id + " tiene pedidos asociados. ¿Desea marcarlo como inactivo?",
                                "Cliente con Pedidos", JOptionPane.YES_NO_OPTION);
                        if (opcion == JOptionPane.YES_OPTION) {
                            if (!controlador.desactivar(id)) {
                                todosExitosos = false;
                                JOptionPane.showMessageDialog(this, "No se encontró el cliente con ID: " + id + " o no se pudo desactivar.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        if (!controlador.desactivar(id)) {
                            todosExitosos = false;
                            JOptionPane.showMessageDialog(this, "No se encontró el cliente con ID: " + id + " o no se pudo desactivar.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (todosExitosos) {
                    JOptionPane.showMessageDialog(this, "Cliente(s) desactivado(s) exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                cargartablaclientes();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione solo clientes con el mismo estado (todos activos o todos inactivos).", "Error", JOptionPane.ERROR_MESSAGE);
        }

        rSCheckBox1.setSelected(false);
        rSButtonMaterialRippleIcon1.setVisible(false);

    }//GEN-LAST:event_rSButtonMaterialRippleIcon1ActionPerformed

    private void paginacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paginacionMouseClicked
        // TODO add your handling code here: 777777
    }//GEN-LAST:event_paginacionMouseClicked

    private void btnNotificacion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotificacion1MouseClicked
        // TODO add your handling code here:
        System.out.println("Clic en btnNotificacion1");
        inicializarPopupFiltrosAvanzados();
        popupFiltrosAvanzados.show(btnNotificacion1, evt.getX(), evt.getY());

    }//GEN-LAST:event_btnNotificacion1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple Añadir4;
    private rojeru_san.RSButtonRiple Añadir5;
    private rojerusan.RSLabelIcon btnNotificacion1;
    private RSMaterialComponent.RSButtonShape btnNuevo1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel paginacion;
    private RSMaterialComponent.RSButtonMaterialRippleIcon rSButtonMaterialRippleIcon1;
    private rojerusan.RSCheckBox rSCheckBox1;
    private RSMaterialComponent.RSTableMetroCustom tablaclientes;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtBuscar;
    // End of variables declaration//GEN-END:variables

    public void cargartablacliente() {
        tablaclientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column == 9; // Solo "Seleccionar" y "Acciones" son editables
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                return columnIndex == 0 ? Boolean.class : String.class; // Checkbox en la primera columna
            }
        };

        // Definir columnas
        model.addColumn("Seleccionar"); // Checkbox
        model.addColumn("Código");
        model.addColumn("Doc. Tipo");
        model.addColumn("Nombre");
        model.addColumn("Apellido");
        model.addColumn("Teléfono");
        model.addColumn("Departamento/Municipio");
        model.addColumn("Dirección");
        model.addColumn("Estado");
        model.addColumn("Acciones");

        // Obtener clientes y llenar la tabla
        List<Cliente> clientes = controlador.obtenerClientes();
        for (Cliente cliente : clientes) {
            Object[] fila = new Object[10];
            fila[0] = false; // Checkbox no seleccionado
            fila[1] = String.valueOf(cliente.getId_cliente()); // Código
            fila[2] = cliente.getIdentificacion() != null ? cliente.getIdentificacion() : "Sin identificación";
            fila[3] = cliente.getNombre() != null ? cliente.getNombre() : "Sin nombre";
            fila[4] = cliente.getApellido() != null ? cliente.getApellido() : "Sin apellido";
            fila[5] = cliente.getTelefono() != null ? cliente.getTelefono() : "Sin teléfono";
            String departamento = cliente.getDepartamento() != null ? cliente.getDepartamento() : "";
            String municipio = cliente.getMunicipio() != null ? cliente.getMunicipio() : "";
            fila[6] = (!departamento.isEmpty() || !municipio.isEmpty()) ? departamento + " / " + municipio : "Sin ubicación";
            fila[7] = cliente.getDireccion() != null ? cliente.getDireccion() : "Sin dirección";
            fila[8] = cliente.isActivo() ? "Activo" : "Inactivo"; // Estado
            fila[9] = ""; // Acciones
            model.addRow(fila);
        }

        // Asignar el modelo a la tabla
        tablaclientes.setModel(model);

        // Configurar renderers y editores
        tablaclientes.getColumnModel().getColumn(9).setCellRenderer(new ButtonPanelRenderer());
        tablaclientes.getColumnModel().getColumn(9).setCellEditor(new ButtonPanelEditor(new JCheckBox()));

        // Ajustar anchos de columnas
        tablaclientes.getColumnModel().getColumn(0).setPreferredWidth(60);  // Seleccionar
        tablaclientes.getColumnModel().getColumn(1).setPreferredWidth(70);  // Código
        tablaclientes.getColumnModel().getColumn(2).setPreferredWidth(70);  // Tipo de identificación
        tablaclientes.getColumnModel().getColumn(3).setPreferredWidth(100); // Nombre
        tablaclientes.getColumnModel().getColumn(4).setPreferredWidth(100); // Apellido
        tablaclientes.getColumnModel().getColumn(5).setPreferredWidth(80);  // Teléfono
        tablaclientes.getColumnModel().getColumn(6).setPreferredWidth(150); // Departamento/Municipio
        tablaclientes.getColumnModel().getColumn(7).setPreferredWidth(160); // Dirección
        tablaclientes.getColumnModel().getColumn(8).setPreferredWidth(70);  // Estado
        tablaclientes.getColumnModel().getColumn(9).setPreferredWidth(80);  // Acciones
        tablaclientes.setRowHeight(24);

        // Listener para capturar la selección de una fila
        tablaclientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int fila_point = tablaclientes.rowAtPoint(e.getPoint());
                if (fila_point > -1) {
                    id_cliente = (int) tablaclientes.getValueAt(fila_point, 1);
                }
            }
        });
    }

    private void obtenerValoresUnicos(List<String> estados, List<String> departamentos, List<String> tiposDocumento) {
        estados.clear();
        departamentos.clear();
        tiposDocumento.clear();
        Set<String> estadosSet = new HashSet<>();
        Set<String> departamentosSet = new HashSet<>();
        Set<String> tiposDocumentoSet = new HashSet<>();

        try {
            for (Cliente cliente : todasLasClientes) {
                // Estado
                estadosSet.add(cliente.isActivo() ? "Activo" : "Inactivo"); // Eliminar la verificación != null
                // Departamento
                if (cliente.getDepartamento() != null && !cliente.getDepartamento().isEmpty()) {
                    departamentosSet.add(cliente.getDepartamento());
                }
                // Tipo de Documento
                if (cliente.getIdentificacion() != null && !cliente.getIdentificacion().isEmpty()) {
                    tiposDocumentoSet.add(cliente.getIdentificacion());
                }
            }
        } catch (Exception e) {
            System.out.println("Error en obtenerValoresUnicos: " + e.getMessage());
            e.printStackTrace();
        }

        estados.addAll(estadosSet.stream().sorted().toList());
        departamentos.addAll(departamentosSet.stream().sorted().toList());
        tiposDocumento.addAll(tiposDocumentoSet.stream().sorted().toList());

        System.out.println("Valores únicos - Estados: " + estados);
        System.out.println("Valores únicos - Departamentos: " + departamentos);
        System.out.println("Valores únicos - Tipos de Documento: " + tiposDocumento);
    }

    private void inicializarPopupFiltrosAvanzados() {
        popupFiltrosAvanzados = new JPopupMenu();
        chkEstados.clear();
        chkDepartamentos.clear();
        chkProductos.clear(); // Note: We're repurposing this list for tiposDocumento

        List<String> estados = new ArrayList<>();
        List<String> departamentos = new ArrayList<>();
        List<String> tiposDocumento = new ArrayList<>();
        obtenerValoresUnicos(estados, departamentos, tiposDocumento);

        System.out.println("Iniciando popup. Estados: " + estados + ", Departamentos: " + departamentos + ", Tipos de Documento: " + tiposDocumento);

        if (estados.isEmpty() && departamentos.isEmpty() && tiposDocumento.isEmpty()) {
            popupFiltrosAvanzados.add(new JLabel("No hay filtros disponibles"));
        } else {
            // Estados
            if (!estados.isEmpty()) {
                popupFiltrosAvanzados.add(new JLabel("Estados:"));
                for (String estado : estados) {
                    JCheckBox chkEstado = new JCheckBox(estado);
                    chkEstados.add(chkEstado);
                    popupFiltrosAvanzados.add(chkEstado);
                }
                popupFiltrosAvanzados.addSeparator();
            }

            // Departamentos
            if (!departamentos.isEmpty()) {
                popupFiltrosAvanzados.add(new JLabel("Departamentos:"));
                for (String depto : departamentos) {
                    JCheckBox chkDepto = new JCheckBox(depto);
                    chkDepartamentos.add(chkDepto);
                    popupFiltrosAvanzados.add(chkDepto);
                }
                popupFiltrosAvanzados.addSeparator();
            }

            // Tipos de Documento
            if (!tiposDocumento.isEmpty()) {
                popupFiltrosAvanzados.add(new JLabel("Tipos de Documento:"));
                for (String tipo : tiposDocumento) {
                    JCheckBox chkTipo = new JCheckBox(tipo);
                    chkProductos.add(chkTipo); // Reusing chkProductos for tiposDocumento
                    popupFiltrosAvanzados.add(chkTipo);
                }
            }
        }

        btnAplicarFiltros = new rojeru_san.RSButtonRiple();
        btnAplicarFiltros.setText("Aplicar");
        btnAplicarFiltros.setBackground(new Color(46, 49, 82));
        btnAplicarFiltros.setColorHover(new Color(0, 153, 51));
        popupFiltrosAvanzados.addSeparator();
        popupFiltrosAvanzados.add(btnAplicarFiltros);

        // Apply theme to popup
        boolean oscuro = TemaManager.getInstance().isOscuro();
        Color fondo = oscuro ? new Color(21, 21, 33) : Color.WHITE;
        Color texto = oscuro ? Color.WHITE : Color.BLACK;
        popupFiltrosAvanzados.setBackground(fondo);
        for (Component comp : popupFiltrosAvanzados.getComponents()) {
            comp.setBackground(fondo);
            comp.setForeground(texto);
        }

        btnAplicarFiltros.addActionListener(e -> {
            aplicarFiltrosAvanzados();
            popupFiltrosAvanzados.setVisible(false);
        });
    }

    private void aplicarFiltrosAvanzados() {
        List<String> filtrosEstado = new ArrayList<>();
        for (JCheckBox chk : chkEstados) {
            if (chk.isSelected()) {
                filtrosEstado.add(chk.getText());
            }
        }

        List<String> filtrosDepartamento = new ArrayList<>();
        for (JCheckBox chk : chkDepartamentos) {
            if (chk.isSelected()) {
                filtrosDepartamento.add(chk.getText());
            }
        }

        List<String> filtrosTipoDocumento = new ArrayList<>();
        for (JCheckBox chk : chkProductos) { // Reusing chkProductos for tiposDocumento
            if (chk.isSelected()) {
                filtrosTipoDocumento.add(chk.getText());
            }
        }

        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        sorter = new TableRowSorter<>(model);
        tablaclientes.setRowSorter(sorter);

        List<RowFilter<Object, Object>> filtros = new ArrayList<>();

        // Filter by Estado (column 8)
        if (!filtrosEstado.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)^(" + String.join("|", filtrosEstado) + ")$", 8));
        }

        // Filter by Departamento (column 6, which contains Departamento/Municipio)
        if (!filtrosDepartamento.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)^(" + String.join("|", filtrosDepartamento) + ").*", 6));
        }

        // Filter by Tipo de Documento (column 2)
        if (!filtrosTipoDocumento.isEmpty()) {
            filtros.add(RowFilter.regexFilter("(?i)^(" + String.join("|", filtrosTipoDocumento) + ")$", 2));
        }

        if (!filtros.isEmpty()) {
            sorter.setRowFilter(RowFilter.andFilter(filtros));
        } else {
            sorter.setRowFilter(null);
        }
    }
}
