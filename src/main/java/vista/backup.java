/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista;

import controlador.Ctrl_Backup;
import controlador.Ctrl_Cliente;
import controlador.Ctrl_Proveedor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import modelo.BackupDatos;
import modelo.ProveedorDatos;
import rojeru_san.efectos.ValoresEnum;
import rojerusan.RSLabelIcon;

/**
 *
 * @author ZenBook
 */
public class backup extends javax.swing.JPanel {

    private Ctrl_Backup backupContro;
    private int currentPage = 0;
    private final int BACKUPS_POR_PAGINA = 19;
    private List<BackupDatos> todosLosBackups = new ArrayList<>();
    private boolean[] seleccionados;

    public backup(JFrame jFrame, boolean par) {
        backupContro = new Ctrl_Backup();
        initComponents();
        jPanel1.setPreferredSize(new Dimension(1340, 750));
        jScrollPane3.setPreferredSize(new Dimension(1200, 550));
        rSCheckBox1.addActionListener(e -> seleccionarTodo());
        cargarTablaBackups();
        aplicarTema();

        rSButtonMaterialRippleIcon1.setVisible(false); // Ocultar botón por defecto
        TemaManager.getInstance().addThemeChangeListener(this::aplicarTema);
        SwingUtilities.invokeLater(() -> {
            cargarTablaBackups();
            inicializarPopupFiltrosAvanzados();
        });
    }

    public void cargarTablaBackups() {
        tablaclientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return columna == 0 || columna == 4; // "Seleccionar" y "Acciones"
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 0) return Boolean.class;
                return String.class;
            }
        };

        model.addColumn("Seleccionar");
        model.addColumn("Base de datos");
        model.addColumn("Última copia de seguridad");
        model.addColumn("Tamaño");
        model.addColumn("Acciones");

        todosLosBackups = backupContro.obtenerBackups();
        System.out.println("Número de backups cargados: " + todosLosBackups.size());

        seleccionados = new boolean[todosLosBackups.size()];
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (BackupDatos backup : todosLosBackups) {
            model.addRow(new Object[]{
                false,
                backup.getDatabaseName(),
                sdf.format(backup.getCreationDate()),
                backup.getFormattedSize(),
                "Acciones"
            });
        }

        tablaclientes.setModel(model);
        mostrarPagina(currentPage);

        // Configurar renderers y editores
        tablaclientes.getColumnModel().getColumn(0).setCellRenderer(new CustomCheckboxRenderer());
        tablaclientes.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(new JCheckBox()));
        tablaclientes.getColumnModel().getColumn(4).setCellRenderer(new ButtonPanelRenderer());
        tablaclientes.getColumnModel().getColumn(4).setCellEditor(new ButtonPanelEditor(new JCheckBox()));

        // Ajustar anchos
        tablaclientes.getColumnModel().getColumn(0).setPreferredWidth(50);
        tablaclientes.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablaclientes.getColumnModel().getColumn(2).setPreferredWidth(200);
        tablaclientes.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablaclientes.getColumnModel().getColumn(4).setPreferredWidth(150);
        tablaclientes.setRowHeight(29);

        // Listener para selección
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
                    actualizarEstadoBotonAccion();
                }
            }
        });
    }

    private void actualizarEstadoBotonAccion() {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        List<Integer> seleccionadosRows = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
                seleccionadosRows.add(i);
            }
        }

        if (seleccionadosRows.isEmpty()) {
            rSButtonMaterialRippleIcon1.setVisible(false);
        } else {
            rSButtonMaterialRippleIcon1.setVisible(true);
            rSButtonMaterialRippleIcon1.setToolTipText("Eliminar backups seleccionados");
            rSButtonMaterialRippleIcon1.setIcons(ValoresEnum.ICONS.DELETE);
            rSButtonMaterialRippleIcon1.setForeground(Color.RED);
        }
    }

    private void mostrarPagina(int pagina) {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        model.setRowCount(0);

        int inicio = pagina * BACKUPS_POR_PAGINA;
        int fin = Math.min(inicio + BACKUPS_POR_PAGINA, todosLosBackups.size());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        if (inicio >= todosLosBackups.size()) {
            currentPage = 0;
            inicio = 0;
            fin = Math.min(BACKUPS_POR_PAGINA, todosLosBackups.size());
        }

        for (int i = inicio; i < fin; i++) {
            BackupDatos backup = todosLosBackups.get(i);
            model.addRow(new Object[]{
                seleccionados[i],
                backup.getDatabaseName(),
                sdf.format(backup.getCreationDate()),
                backup.getFormattedSize(),
                "Acciones"
            });
        }

        int totalPaginas = (int) Math.ceil((double) todosLosBackups.size() / BACKUPS_POR_PAGINA);
        paginacion.setText("Página " + (currentPage + 1) + " de " + totalPaginas);
        Añadir5.setEnabled(currentPage > 0);
        Añadir4.setEnabled(currentPage < totalPaginas - 1);
    }

    private void seleccionarTodo() {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        boolean seleccionado = rSCheckBox1.isSelected();
        int inicio = currentPage * BACKUPS_POR_PAGINA;
        int fin = Math.min(inicio + BACKUPS_POR_PAGINA, todosLosBackups.size());

        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(seleccionado, i, 0);
            seleccionados[inicio + i] = seleccionado;
        }
        actualizarEstadoBotonAccion();
    }

   class ButtonPanelRenderer extends JPanel implements TableCellRenderer {
    private RSLabelIcon downloadIcon;
    private RSLabelIcon restoreIcon;
    private RSLabelIcon deleteIcon;

    public ButtonPanelRenderer() {
        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));
        setOpaque(true);

        // Icono de Descargar
        downloadIcon = new RSLabelIcon();
        downloadIcon.setIcons(ValoresEnum.ICONS.FILE_DOWNLOAD);
        downloadIcon.setToolTipText("Descargar");
        downloadIcon.setPreferredSize(new Dimension(20, 20));

        // Icono de Restaurar
        restoreIcon = new RSLabelIcon();
        restoreIcon.setIcons(ValoresEnum.ICONS.RESTORE);
        restoreIcon.setToolTipText("Restaurar");
        restoreIcon.setPreferredSize(new Dimension(20, 20));

        // Icono de Eliminar
        deleteIcon = new RSLabelIcon();
        deleteIcon.setIcons(ValoresEnum.ICONS.DELETE);
        deleteIcon.setToolTipText("Eliminar");
        deleteIcon.setPreferredSize(new Dimension(20, 20));

        // Agregar los iconos al panel
        add(downloadIcon);
        add(restoreIcon);
        add(deleteIcon);

        updateTheme();
        TemaManager.getInstance().addThemeChangeListener(this::updateTheme);
    }

    private void updateTheme() {
        boolean oscuro = TemaManager.getInstance().isOscuro();
        Color fondo = oscuro ? new Color(21, 21, 33) : Color.WHITE;
        setBackground(fondo);

        downloadIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
        restoreIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
        deleteIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));

        downloadIcon.setForeground(new Color(0, 102, 204));
        restoreIcon.setForeground(new Color(0, 102, 204));
        deleteIcon.setForeground(Color.RED);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Mostrar los iconos siempre, independientemente del estado
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            downloadIcon.setBackground(table.getSelectionBackground());
            restoreIcon.setBackground(table.getSelectionBackground());
            deleteIcon.setBackground(table.getSelectionBackground());
        } else {
            updateTheme();
        }
        return this;
    }
}

class ButtonPanelEditor extends AbstractCellEditor implements TableCellEditor {
    private final JPanel panel;
    private final RSLabelIcon downloadIcon;
    private final RSLabelIcon restoreIcon;
    private final RSLabelIcon deleteIcon;
    private String label;
    private boolean isPushed;
    private int selectedRow;

    public ButtonPanelEditor(JCheckBox checkBox) {
        super();
        panel = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 2, 0));
        panel.setOpaque(true);

        // Icono de Descargar
        downloadIcon = new RSLabelIcon();
        downloadIcon.setIcons(ValoresEnum.ICONS.FILE_DOWNLOAD);
        downloadIcon.setToolTipText("Descargar");
        downloadIcon.setPreferredSize(new Dimension(20, 20));
        downloadIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icono de Restaurar
        restoreIcon = new RSLabelIcon();
        restoreIcon.setIcons(ValoresEnum.ICONS.RESTORE);
        restoreIcon.setToolTipText("Restaurar");
        restoreIcon.setPreferredSize(new Dimension(20, 20));
        restoreIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Icono de Eliminar
        deleteIcon = new RSLabelIcon();
        deleteIcon.setIcons(ValoresEnum.ICONS.DELETE);
        deleteIcon.setToolTipText("Eliminar");
        deleteIcon.setPreferredSize(new Dimension(20, 20));
        deleteIcon.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Agregar los iconos al panel
        panel.add(downloadIcon);
        panel.add(restoreIcon);
        panel.add(deleteIcon);

        // Acción para el icono de Descargar
        downloadIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPushed = true;
                fireEditingStopped();
                try {
                    String filename = todosLosBackups.get(currentPage * BACKUPS_POR_PAGINA + selectedRow).getFilename();
                    boolean success = backupContro.exportarBackup(filename);
                    if (success) {
                        JOptionPane.showMessageDialog(backup.this, 
                            "Respaldo descargado exitosamente: " + filename, 
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(backup.this, 
                            "Error al descargar el respaldo: " + filename, 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(backup.this, 
                        "Error inesperado al descargar el respaldo: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        // Acción para el icono de Restaurar
   restoreIcon.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        isPushed = true;
        fireEditingStopped();

        System.out.println("Botón Restaurar clicado");
        System.out.println("Botón Restaurar clicado");

        int selectedRow = tablaclientes.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(backup.this, "Por favor, seleccione un backup para restaurar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int modelRow = tablaclientes.convertRowIndexToModel(selectedRow);
        int backupIndex = currentPage * BACKUPS_POR_PAGINA + modelRow;
        if (backupIndex >= todosLosBackups.size()) {
            JOptionPane.showMessageDialog(backup.this, "Error: Backup no válido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String filename = todosLosBackups.get(backupIndex).getFilename();
        System.out.println("Intentando restaurar: " + filename);

        int confirm = JOptionPane.showConfirmDialog(backup.this,
            "¿Está seguro de que desea restaurar el respaldo '" + filename + "'? Esto sobrescribirá la base de datos actual y reiniciará la aplicación.",
            "Confirmar restauración", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean success = backupContro.restaurarBackup(filename);
            if (success) {
                JOptionPane.showMessageDialog(backup.this,
                    "Respaldo restaurado exitosamente: " + filename + ". La aplicación se reiniciará.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                reiniciarAplicacion();
            } else {
                JOptionPane.showMessageDialog(backup.this,
                    "Error al restaurar el respaldo. Revisa la consola.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
});

        // Acción para el icono de Eliminar
        deleteIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                isPushed = true;
                fireEditingStopped();
                try {
                    String filename = todosLosBackups.get(currentPage * BACKUPS_POR_PAGINA + selectedRow).getFilename();
                    int confirm = JOptionPane.showConfirmDialog(backup.this,
                            "¿Está seguro de que desea eliminar el respaldo '" + filename + "'?",
                            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        if (backupContro.eliminarBackup(filename)) {
                            JOptionPane.showMessageDialog(backup.this, 
                                "Respaldo eliminado exitosamente.", 
                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                            cargarTablaBackups();
                        } else {
                            JOptionPane.showMessageDialog(backup.this, 
                                "Error al eliminar el respaldo.", 
                                "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(backup.this, 
                        "Error: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
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

        downloadIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
        restoreIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));
        deleteIcon.setBackground(oscuro ? new Color(67, 71, 120) : new Color(46, 49, 82));

        downloadIcon.setForeground(new Color(0, 102, 204));
        restoreIcon.setForeground(new Color(0, 102, 204));
        deleteIcon.setForeground(Color.RED);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        label = (value == null) ? "" : value.toString();
        isPushed = true;
        selectedRow = row;
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
}

    private void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            Color fondo = new Color(21, 21, 33);
            Color primario = new Color(40, 60, 150);
            Color texto = Color.WHITE;
            rSButtonMaterialRippleIcon1.setBackground(fondo);
            rSButtonMaterialRippleIcon1.setForegroundIcon(fondo);
            rSButtonMaterialRippleIcon1.setForegroundHover(fondo);
            rSButtonMaterialRippleIcon1.setForegroundIconHover(fondo);
            rSButtonMaterialRippleIcon1.setForegroundText(fondo);

            jPanel1.setBackground(fondo);
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.LIGHT_GRAY);
            paginacion.setBackground(texto);
            rSCheckBox1.setColorCheck(new Color(21, 21, 33));
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
            tablaclientes.setEffectHover(false);
            tablaclientes.setShowGrid(true);
            tablaclientes.setGridColor(Color.WHITE);

            btnNotificacion1.setBackground(new Color(67, 71, 120));
          
        } else {
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;
            Color primario = new Color(72, 92, 188);
            paginacion.setBackground(texto);
            rSButtonMaterialRippleIcon1.setBackground(fondo);

            jPanel1.setBackground(fondo);
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.GRAY);

            tablaclientes.setBackground(Color.WHITE);
            tablaclientes.setBackgoundHead(new Color(46, 49, 82));
            tablaclientes.setForegroundHead(Color.WHITE);
            tablaclientes.setBackgoundHover(new Color(240, 240, 240));
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
            tablaclientes.setEffectHover(false);
            tablaclientes.setSelectionBackground(new Color(67, 150, 209));
            tablaclientes.setShowGrid(true);
            tablaclientes.setGridColor(Color.BLACK);

            btnNotificacion1.setBackground(new Color(46, 49, 82));
          
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
        Añadir4 = new rojeru_san.RSButtonRiple();
        rSButtonMaterialRippleIcon1 = new RSMaterialComponent.RSButtonMaterialRippleIcon();
        Añadir5 = new rojeru_san.RSButtonRiple();
        rSCheckBox1 = new rojerusan.RSCheckBox();
        paginacion = new javax.swing.JLabel();
        btnNotificacion1 = new rojerusan.RSLabelIcon();
        reinicioo = new rojerusan.RSLabelIcon();

        setPreferredSize(new java.awt.Dimension(1290, 730));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(241, 245, 253));
        jPanel1.setPreferredSize(new java.awt.Dimension(960, 570));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNuevo1.setBackground(new java.awt.Color(46, 49, 82));
        btnNuevo1.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnNuevo1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnNuevo1.setText("Crear copia");
        btnNuevo1.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnNuevo1.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        btnNuevo1.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnNuevo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnNuevo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevo1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnNuevo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 130, 30));

        tablaclientes.setForeground(new java.awt.Color(255, 255, 255));
        tablaclientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Seleccionar", "Base de datos", "Ultima copia de seguridad", "Tamaño", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tablaclientes.setToolTipText("");
        tablaclientes.setBackgoundHead(new java.awt.Color(255, 255, 51));
        tablaclientes.setBackgoundHover(new java.awt.Color(51, 255, 51));
        tablaclientes.setBorderHead(null);
        tablaclientes.setBorderRows(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        tablaclientes.setColorBorderHead(new java.awt.Color(102, 102, 255));
        tablaclientes.setColorBorderRows(new java.awt.Color(255, 102, 102));
        tablaclientes.setColorPrimary(new java.awt.Color(153, 255, 153));
        tablaclientes.setColorPrimaryText(new java.awt.Color(0, 0, 0));
        tablaclientes.setColorSecondary(new java.awt.Color(0, 204, 153));
        tablaclientes.setColorSecundaryText(new java.awt.Color(30, 30, 45));
        tablaclientes.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tablaclientes.setFontHead(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setFontRowHover(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setFontRowSelect(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tablaclientes.setGridColor(new java.awt.Color(102, 255, 102));
        tablaclientes.setPreferredSize(new java.awt.Dimension(500, 500));
        tablaclientes.setSelectionBackground(new java.awt.Color(67, 150, 209));
        jScrollPane3.setViewportView(tablaclientes);
        tablaclientes.getColumnModel().getColumn(0).setPreferredWidth(10);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 120, 1200, 530));

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
        rSButtonMaterialRippleIcon1.setForeground(new java.awt.Color(51, 255, 0));
        rSButtonMaterialRippleIcon1.setBackgroundHover(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon1.setForegroundHover(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundIcon(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundIconHover(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setForegroundText(new java.awt.Color(255, 51, 51));
        rSButtonMaterialRippleIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.BLOCK);
        rSButtonMaterialRippleIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonMaterialRippleIcon1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSButtonMaterialRippleIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 680, 40, 40));

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

        rSCheckBox1.setForeground(new java.awt.Color(102, 102, 255));
        rSCheckBox1.setText("Seleccionar Todo");
        rSCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSCheckBox1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSCheckBox1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 680, 190, 20));

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
        btnNotificacion1.setForeground(new java.awt.Color(51, 0, 51));
        btnNotificacion1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.IMPORT_EXPORT);
        btnNotificacion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNotificacion1MouseClicked(evt);
            }
        });
        jPanel1.add(btnNotificacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 60, -1, -1));

        reinicioo.setBackground(new java.awt.Color(255, 255, 255));
        reinicioo.setForeground(new java.awt.Color(51, 0, 51));
        reinicioo.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.RESTORE_PAGE);
        reinicioo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                reiniciooMouseClicked(evt);
            }
        });
        jPanel1.add(reinicioo, new org.netbeans.lib.awtextra.AbsoluteConstraints(1220, 60, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1340, 730));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
       String textoBusqueda = txtBuscar.getText().trim();
        if (textoBusqueda.isEmpty()) {
            cargarTablaBackups();
        } else {
            cargarTablaBackupsFiltrado(textoBusqueda);
        }

    }//GEN-LAST:event_txtBuscarActionPerformed

    private void Añadir4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir4ActionPerformed

    }//GEN-LAST:event_Añadir4ActionPerformed

    private void rSButtonMaterialRippleIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonMaterialRippleIcon1ActionPerformed
     DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        List<String> seleccionadosFiles = new ArrayList<>();

        for (int i = 0; i < model.getRowCount(); i++) {
            if (Boolean.TRUE.equals(model.getValueAt(i, 0))) {
                int index = currentPage * BACKUPS_POR_PAGINA + i;
                seleccionadosFiles.add(todosLosBackups.get(index).getFilename());
            }
        }

        if (!seleccionadosFiles.isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de que desea eliminar " + seleccionadosFiles.size() + " respaldo(s)?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean todosExitosos = true;
                for (String filename : seleccionadosFiles) {
                    if (!backupContro.eliminarBackup(filename)) {
                        todosExitosos = false;
                        JOptionPane.showMessageDialog(this, "Error al eliminar el respaldo: " + filename, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                if (todosExitosos) {
                    JOptionPane.showMessageDialog(this, "Respaldo(s) eliminado(s) exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                cargarTablaBackups();
                rSCheckBox1.setSelected(false);
                rSButtonMaterialRippleIcon1.setVisible(false);
            }
        
        }
    }//GEN-LAST:event_rSButtonMaterialRippleIcon1ActionPerformed

    private void Añadir5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir5ActionPerformed
        if (currentPage > 0) {
            currentPage--;
            mostrarPagina(currentPage);
        }
    }//GEN-LAST:event_Añadir5ActionPerformed

    private void paginacionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_paginacionMouseClicked
        // TODO add your handling code here: 777777
    }//GEN-LAST:event_paginacionMouseClicked

    private void rSCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSCheckBox1ActionPerformed
        seleccionarTodo();
    }//GEN-LAST:event_rSCheckBox1ActionPerformed

    private void btnNotificacion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotificacion1MouseClicked
        JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccionar archivo de respaldo SQL");
    fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos SQL", "sql"));

    int result = fileChooser.showOpenDialog(this);
    if (result == JFileChooser.APPROVE_OPTION) {
        File selectedFile = fileChooser.getSelectedFile();

        int confirm = JOptionPane.showConfirmDialog(this,
            "¿Desea restaurar la base de datos desde el archivo seleccionado?\nEsto sobrescribirá los datos actuales.",
            "Confirmar restauración",
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            boolean restaurado = backupContro.restaurarBackupDesdeArchivo(selectedFile);
            if (restaurado) {
                JOptionPane.showMessageDialog(this, "Base de datos restaurada exitosamente.");
                reiniciarAplicacion(); // Solo si ya tienes este método
            } else {
                JOptionPane.showMessageDialog(this, "Error al restaurar el backup.");
            }
        }
    }
    }//GEN-LAST:event_btnNotificacion1MouseClicked

    private void reiniciooMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reiniciooMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_reiniciooMouseClicked

    private void btnNuevo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevo1ActionPerformed
        WarningBackup dialog = new WarningBackup((JFrame) SwingUtilities.getWindowAncestor(this), true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);

        if (dialog.isConfirmed()) {
            String backupName = dialog.getBackupName();
            try {
                if (backupContro.crearBackup(backupName)) {
                    JOptionPane.showMessageDialog(this, "Copia de seguridad creada exitosamente: " + backupName, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTablaBackups();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al crear la copia de seguridad.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear la copia de seguridad: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnNuevo1ActionPerformed
private void reiniciarAplicacion() {
    SwingUtilities.invokeLater(() -> {
        try {
            // Cerrar la ventana actual
            JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (currentFrame != null) {
                currentFrame.dispose();
            }
            // Abrir la ventana de Login1121
            Login1121 login = new Login1121();
            login.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al reiniciar la aplicación: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error al reiniciar la aplicación: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    });
}

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
    private rojerusan.RSLabelIcon reinicioo;
    private RSMaterialComponent.RSTableMetroCustom tablaclientes;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtBuscar;
    // End of variables declaration//GEN-END:variables
 private void cargarTablaBackupsFiltrado(String textoBusqueda) {
        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        model.setRowCount(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        List<BackupDatos> backupsFiltrados = todosLosBackups.stream()
            .filter(b -> b.getFilename().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                         b.getDatabaseName().toLowerCase().contains(textoBusqueda.toLowerCase()) ||
                         sdf.format(b.getCreationDate()).contains(textoBusqueda))
            .toList();

        int inicio = currentPage * BACKUPS_POR_PAGINA;
        int fin = Math.min(inicio + BACKUPS_POR_PAGINA, backupsFiltrados.size());

        for (int i = inicio; i < fin; i++) {
            BackupDatos backup = backupsFiltrados.get(i);
            model.addRow(new Object[]{
                false,
                backup.getDatabaseName(),
                sdf.format(backup.getCreationDate()),
                backup.getFormattedSize(),
                "Acciones"
            });
        }

        int totalPaginas = (int) Math.ceil((double) backupsFiltrados.size() / BACKUPS_POR_PAGINA);
        paginacion.setText("Página " + (currentPage + 1) + " de " + totalPaginas);
        Añadir5.setEnabled(currentPage > 0);
        Añadir4.setEnabled(currentPage < totalPaginas - 1);
    }

    private void inicializarPopupFiltrosAvanzados() {
        popupFiltrosAvanzados = new JPopupMenu();
        List<String> databases = new ArrayList<>();
        for (BackupDatos backup : todosLosBackups) {
            if (!databases.contains(backup.getDatabaseName())) {
                databases.add(backup.getDatabaseName());
            }
        }

        chkDatabases = new ArrayList<>();
        for (String db : databases) {
            JCheckBox chkDb = new JCheckBox("Base de datos: " + db);
            chkDatabases.add(chkDb);
            popupFiltrosAvanzados.add(chkDb);
        }

        btnAplicarFiltros = new rojeru_san.RSButtonRiple();
        btnAplicarFiltros.setText("Aplicar");
        btnAplicarFiltros.setBackground(new Color(46, 49, 82));
        btnAplicarFiltros.setColorHover(new Color(0, 153, 51));
        popupFiltrosAvanzados.add(btnAplicarFiltros);

        btnAplicarFiltros.addActionListener(e -> {
            aplicarFiltrosAvanzados();
            popupFiltrosAvanzados.setVisible(false);
        });

        popupFiltrosAvanzados.setBackground(Color.WHITE);
        for (Component comp : popupFiltrosAvanzados.getComponents()) {
            comp.setBackground(Color.WHITE);
            comp.setForeground(Color.BLACK);
        }

        // Mostrar popup al hacer clic derecho en la tabla
        tablaclientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    popupFiltrosAvanzados.show(tablaclientes, e.getX(), e.getY());
                }
            }
        });
    }

    private List<JCheckBox> chkDatabases = new ArrayList<>();
    private JPopupMenu popupFiltrosAvanzados;
    private rojeru_san.RSButtonRiple btnAplicarFiltros;
    private TableRowSorter<DefaultTableModel> sorter;

    private void aplicarFiltrosAvanzados() {
        List<String> filtrosDatabase = new ArrayList<>();
        for (JCheckBox chk : chkDatabases) {
            if (chk.isSelected()) {
                filtrosDatabase.add(chk.getText().replace("Base de datos: ", ""));
            }
        }

        DefaultTableModel model = (DefaultTableModel) tablaclientes.getModel();
        sorter = new TableRowSorter<>(model);
        tablaclientes.setRowSorter(sorter);

        if (!filtrosDatabase.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)^(" + String.join("|", filtrosDatabase) + ")$", 1));
        } else {
            sorter.setRowFilter(null);
        }

        mostrarPagina(currentPage);
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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            checkBox.setSelected(Boolean.TRUE.equals(value));
            if (isSelected) {
                setBackground(table.getSelectionBackground());
                checkBox.setBackground(table.getSelectionBackground());
            } else {
                updateTheme();
            }
            return this;
        }
    }
}
