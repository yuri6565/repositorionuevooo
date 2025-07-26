/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Inventario0;

import controlador.Ctrl_CategoriaMaterial;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import modelo.Categoria;
import rojeru_san.efectos.ValoresEnum;
import rojerusan.RSLabelIcon;
import vista.TemaManager;

/**
 *
 * @author ZenBook
 */
public class materialCategoria extends javax.swing.JDialog {

    private int ultimaFilaSeleccionada = -1;
    private boolean[] seleccionados;

    /**
     * Creates new form categorias
     */
    public materialCategoria(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        actualizarTabla();
        tabla1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // Ocultar la columna de código (columna 1)
        TableColumn column = tabla1.getColumnModel().getColumn(2);
        column.setMinWidth(0);
        column.setMaxWidth(0);
        column.setWidth(0);
        column.setPreferredWidth(0);

        // Listener para selección de filas
        tabla1.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarDatosSeleccionados();
            }
        });

        // Listener para checkboxes
        tabla1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int column = tabla1.columnAtPoint(e.getPoint());
                int row = tabla1.rowAtPoint(e.getPoint());

                if (column == 0) { // Click en checkbox
                    tabla1.setRowSelectionInterval(row, row);
                    cargarDatosSeleccionados(); // Actualizar campo de nombre
                }
            }
        });

        tabla1.setShowHorizontalLines(true);
        tabla1.setShowVerticalLines(true);

    }

    private void actualizarTabla() {
        DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Seleccionar", "Nombre", "Código"} // Añadir columna para el código
        ) {
            Class[] types = new Class[]{Boolean.class, String.class, Integer.class}; // Tipo para el código

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return columnIndex == 0; // Solo la columna de checkboxes es editable
            }
        };

        tabla1.setModel(model);
        model.setRowCount(0); // Limpiar tabla

        Ctrl_CategoriaMaterial dao = new Ctrl_CategoriaMaterial();
        List<Categoria> categorias = dao.obtenerCategoriasMaterial();
        if (categorias != null) {
            for (Categoria categoria : categorias) {
                model.addRow(new Object[]{false, categoria.getNombre(), categoria.getCodigo()}); // Incluir código
            }
        }

        // Configurar el renderizador y editor para los checkboxes
        tabla1.getColumnModel().getColumn(0).setCellRenderer(new CheckBoxRenderer());
        tabla1.getColumnModel().getColumn(0).setCellEditor(new CheckBoxEditor(new JCheckBox()));

        // Ocultar la columna de código (índice 2)
        TableColumn codeColumn = tabla1.getColumnModel().getColumn(2);
        codeColumn.setMinWidth(0);
        codeColumn.setMaxWidth(0);
        codeColumn.setWidth(0);
        codeColumn.setPreferredWidth(0);
    }

    private void cargarDatosSeleccionados() {
        List<Integer> filasSeleccionadas = getFilasSeleccionadas();

        if (filasSeleccionadas.size() == 1) {
            // Solo una fila seleccionada - mostrar datos
            int filaSeleccionada = filasSeleccionadas.get(0);
            DefaultTableModel model = (DefaultTableModel) tabla1.getModel();
            txtNombre.setText(model.getValueAt(filaSeleccionada, 1).toString()); // Nombre
            ultimaFilaSeleccionada = filaSeleccionada;
        } else {
            // Múltiples selecciones o ninguna - limpiar campo
            limpiarCampos();
            ultimaFilaSeleccionada = -1;
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
    }

    // Clase para renderizar los checkboxes
    class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {

        public CheckBoxRenderer() {
            setHorizontalAlignment(JLabel.CENTER);
            setOpaque(true);
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));

        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            setSelected(Boolean.TRUE.equals(value));

            // Mantener el fondo blanco del checkbox
            setBackground(Color.WHITE);

            // Cambiar el borde según selección
            if (isSelected) {
                // Borde azul cuando está seleccionado
                setBorder(BorderFactory.createLineBorder(new Color(67, 150, 209)));
                // Color de fondo de la fila (opcional)
                setBackground(table.getSelectionBackground());
            } else {
                // Borde gris normal
                setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));
                setBackground(Color.WHITE);
            }

            return this;

        }
    }

// Clase para editar los checkboxes
    class CheckBoxEditor extends DefaultCellEditor {

        public CheckBoxEditor(JCheckBox checkBox) {
            super(checkBox);
            checkBox.setHorizontalAlignment(JLabel.CENTER);
            checkBox.setOpaque(true);
            checkBox.setBackground(Color.WHITE);
            checkBox.setBorder(BorderFactory.createLineBorder(new Color(153, 153, 153)));
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            JCheckBox checkBox = (JCheckBox) super.getTableCellEditorComponent(table, value, isSelected, row, column);
            checkBox.setSelected(Boolean.TRUE.equals(value));
            checkBox.setBackground(Color.WHITE);
            return checkBox;
        }
    }

    private List<Integer> getFilasSeleccionadas() {
        List<Integer> filasSeleccionadas = new ArrayList<>();
        DefaultTableModel model = (DefaultTableModel) tabla1.getModel();

        for (int i = 0; i < model.getRowCount(); i++) {
            if ((Boolean) model.getValueAt(i, 0)) { // Columna 0 = checkboxes
                filasSeleccionadas.add(i);
            }
        }

        return filasSeleccionadas;
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
        jLabel2 = new javax.swing.JLabel();
        lblSalir = new rojerusan.RSLabelImage();
        jPanel3 = new javax.swing.JPanel();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel4 = new javax.swing.JLabel();
        btnEliminar = new RSMaterialComponent.RSButtonShape();
        btnAñadir = new RSMaterialComponent.RSButtonShape();
        btnActualizar = new RSMaterialComponent.RSButtonShape();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabla1 = new RSMaterialComponent.RSTableMetroCustom();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setPreferredSize(new java.awt.Dimension(95, 20));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("SansSerif", 1, 17)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Categorías");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, -1, -1));

        lblSalir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        lblSalir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSalirMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSalirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSalirMouseExited(evt);
            }
        });
        jPanel2.add(lblSalir, new org.netbeans.lib.awtextra.AbsoluteConstraints(688, 3, 20, 20));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 710, 26));

        jPanel3.setBackground(new java.awt.Color(245, 246, 250));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre.setPlaceholder("Ingrese el nombre...");
        txtNombre.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });
        jPanel3.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 50, 200, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Nombre:");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 20, -1, -1));

        btnEliminar.setBackground(new java.awt.Color(46, 49, 82));
        btnEliminar.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/delete (1).png"))); // NOI18N
        btnEliminar.setText(" Eliminar");
        btnEliminar.setBackgroundHover(new java.awt.Color(204, 0, 0));
        btnEliminar.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel3.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 90, 110, 30));

        btnAñadir.setBackground(new java.awt.Color(46, 49, 82));
        btnAñadir.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnAñadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnAñadir.setText("  Añadir");
        btnAñadir.setBackgroundHover(new java.awt.Color(0, 153, 0));
        btnAñadir.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnAñadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAñadirActionPerformed(evt);
            }
        });
        jPanel3.add(btnAñadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 10, 110, 30));

        btnActualizar.setBackground(new java.awt.Color(46, 49, 82));
        btnActualizar.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pencil (1)_1.png"))); // NOI18N
        btnActualizar.setText(" Actualizar");
        btnActualizar.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnActualizar.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel3.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 50, 110, 30));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 690, 130));

        tabla1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null}
            },
            new String [] {
                "Seleccionar", "Nombre"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabla1.setBackgoundHead(new java.awt.Color(46, 49, 82));
        tabla1.setBackgoundHover(new java.awt.Color(67, 150, 209));
        tabla1.setBorderHead(null);
        tabla1.setBorderRows(null);
        tabla1.setColorBorderHead(new java.awt.Color(46, 49, 82));
        tabla1.setColorBorderRows(new java.awt.Color(46, 49, 82));
        tabla1.setColorPrimaryText(new java.awt.Color(0, 0, 0));
        tabla1.setColorSecondary(new java.awt.Color(255, 255, 255));
        tabla1.setColorSecundaryText(new java.awt.Color(0, 0, 0));
        tabla1.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tabla1.setFontHead(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tabla1.setFontRowHover(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        tabla1.setFontRowSelect(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        tabla1.setSelectionBackground(new java.awt.Color(67, 150, 209));
        tabla1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabla1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabla1);
        tabla1.getColumnModel().getColumn(0).setPreferredWidth(10);

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 560, 290));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        List<Integer> filasSeleccionadas = getFilasSeleccionadas();

        if (filasSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Selecciona al menos una categoría primero",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String mensajeConfirmacion = filasSeleccionadas.size() == 1
                ? "¿Eliminar esta categoría de material?"
                : "¿Eliminar estas " + filasSeleccionadas.size() + " categorías de material?";

        if (JOptionPane.showConfirmDialog(this, mensajeConfirmacion, "Confirmar",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tabla1.getModel();
        Ctrl_CategoriaMaterial dao = new Ctrl_CategoriaMaterial();
        int eliminadas = 0;
        StringBuilder enUso = new StringBuilder();
        StringBuilder errores = new StringBuilder();

        for (int i = filasSeleccionadas.size() - 1; i >= 0; i--) {
            int fila = filasSeleccionadas.get(i);
            int codigo = (int) model.getValueAt(fila, 2);
            String nombre = model.getValueAt(fila, 1).toString();

            int resultado = dao.eliminarConVerificacion(codigo);

            switch (resultado) {
                case 1: // Éxito
                    model.removeRow(fila);
                    eliminadas++;
                    break;
                case -1: // En uso
                    enUso.append("- ").append(nombre).append("\n");
                    break;
                default: // Error
                    errores.append("- ").append(nombre).append("\n");
                    break;
            }
        }

        // Mostrar resultados
        StringBuilder mensajeFinal = new StringBuilder();

        if (eliminadas > 0) {
            mensajeFinal.append(eliminadas == 1
                    ? "1 categoría eliminada correctamente\n\n"
                    : eliminadas + " categorías eliminadas correctamente\n\n");
        }

        if (enUso.length() > 0) {
            mensajeFinal.append("No se pudieron eliminar (están en uso en el inventario):\n")
                    .append(enUso.toString()).append("\n");
        }

        if (errores.length() > 0) {
            mensajeFinal.append("Errores al eliminar:\n")
                    .append(errores.toString());
        }

        if (mensajeFinal.length() > 0) {
            JOptionPane.showMessageDialog(this,
                    mensajeFinal.toString(),
                    "Resultado",
                    eliminadas > 0 ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
        }

        limpiarCampos();

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnAñadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAñadirActionPerformed
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Ingrese un nombre para la categoría",
                    "Campo requerido",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Validar que no exista (aunque el controlador también lo valida)
        Ctrl_CategoriaMaterial dao = new Ctrl_CategoriaMaterial();
        if (dao.existeCategoria(nombre)) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe una categoría con este nombre",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Categoria categoria = new Categoria(nombre);
        if (dao.insertar(categoria)) {
            actualizarTabla();
            JOptionPane.showMessageDialog(this,
                    "Categoría añadida correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            txtNombre.setText("");
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al añadir categoría",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAñadirActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        List<Integer> filasSeleccionadas = getFilasSeleccionadas();

        if (filasSeleccionadas.size() != 1) {
            JOptionPane.showMessageDialog(this,
                    "Debes seleccionar exactamente una categoría para actualizar",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nuevoNombre = txtNombre.getText().trim();

        if (nuevoNombre.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "El nombre no puede estar vacío",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) tabla1.getModel();
        int filaSeleccionada = filasSeleccionadas.get(0);
        int codigo = (int) model.getValueAt(filaSeleccionada, 2);
        String nombreActual = model.getValueAt(filaSeleccionada, 1).toString();

        // Solo validar si el nombre cambió
        if (!nuevoNombre.equalsIgnoreCase(nombreActual)) {
            Ctrl_CategoriaMaterial dao = new Ctrl_CategoriaMaterial();
            if (dao.existeCategoria(nuevoNombre)) {
                JOptionPane.showMessageDialog(this,
                        "Ya existe una categoría con este nombre",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Categoria categoria = new Categoria(codigo, nuevoNombre);
        Ctrl_CategoriaMaterial dao = new Ctrl_CategoriaMaterial();

        if (dao.actualizar(categoria)) {
            actualizarTabla();
            JOptionPane.showMessageDialog(this,
                    "¡Categoría actualizada!",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al actualizar",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnActualizarActionPerformed

    private void tabla1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabla1MouseClicked

    }//GEN-LAST:event_tabla1MouseClicked

    private void lblSalirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseClicked
        this.dispose();
    }//GEN-LAST:event_lblSalirMouseClicked

    private void lblSalirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mano al pasar
    }//GEN-LAST:event_lblSalirMouseEntered

    private void lblSalirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSalirMouseExited
        setCursor(Cursor.getDefaultCursor()); // Cursor normal al salir
    }//GEN-LAST:event_lblSalirMouseExited

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(materialCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(materialCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(materialCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(materialCategoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                materialCategoria dialog = new materialCategoria(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private RSMaterialComponent.RSButtonShape btnActualizar;
    private RSMaterialComponent.RSButtonShape btnAñadir;
    private RSMaterialComponent.RSButtonShape btnEliminar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private rojerusan.RSLabelImage lblSalir;
    private RSMaterialComponent.RSTableMetroCustom tabla1;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    // End of variables declaration//GEN-END:variables
}
