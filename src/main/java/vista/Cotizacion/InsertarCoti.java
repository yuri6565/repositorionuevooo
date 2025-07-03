/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Cotizacion;

import java.awt.Frame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author tilin del sena
 */
public class InsertarCoti extends javax.swing.JDialog {

    private boolean confirmado = false;
    private String producto;
    private String unidad;
    private String cantidad;
    private String valorUnitario;

    // Constructor original para nuevo producto
    public InsertarCoti(Frame parent, boolean modal, String unidad1, String valorUnitario1) {
        super(parent, modal);
        initComponents();
        CargarUnidadMed();
        // Opcional: precargar valor unitario si se proporciona
        if (!valorUnitario1.isEmpty()) {
            txtValorUni.setText(valorUnitario1);
        }
    }

    // Constructor para edición
    public InsertarCoti(java.awt.Frame parent, boolean modal, String producto, String unidad, String cantidad, String valorUnitario) {
        super(parent, modal);
        initComponents();
        CargarUnidadMed();
        // Precargar los campos con los datos
        this.producto = producto;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        txtProducto.setText(producto);
        for (int i = 0; i < combox_Unidad.getItemCount(); i++) {
            if (combox_Unidad.getItemAt(i).equals(unidad)) {
                combox_Unidad.setSelectedIndex(i);
                break;
            }
        }
        txtCantidad.setText(cantidad);
        txtValorUni.setText(valorUnitario);
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Object[] getDatosCoti() {
        return new Object[]{
            txtProducto.getText().trim(),
            txtCantidad.getText().trim(),
            txtValorUni.getText().trim(),
            combox_Unidad.getSelectedItem().toString() // Añadir unidad
        };

    }

    private void CargarUnidadMed() {
        combox_Unidad.removeAllItems();
        combox_Unidad.addItem("Seleccione unidad");
        String[] unidades = {"Metro", "Centímetro", "Unidad"};
        for (String unidad : unidades) {
            combox_Unidad.addItem(unidad);
        }
    }

    private void añadirProducto() {
        String producto = txtProducto.getText().trim();
        String unidad = combox_Unidad.getSelectedItem().toString();
        String cantidadTexto = txtCantidad.getText().trim();
        String valorUnitarioTexto = txtValorUni.getText().trim();

        if (producto.isEmpty() || unidad.equals("Seleccione unidad") || cantidadTexto.isEmpty() || valorUnitarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos del producto", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            double valorUnitario = Double.parseDouble(valorUnitarioTexto);
            double subtotal = cantidad * valorUnitario;
            // Los datos se pasarán al padre a través de getDatosCoti()
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad y valor unitario deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtProducto.setText("");
        combox_Unidad.setSelectedIndex(0);
        txtCantidad.setText("");
        txtValorUni.setText("");
        txtProducto.requestFocus();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnGuardar = new rojeru_san.RSButtonRiple();
        btnCancelar = new rojeru_san.RSButtonRiple();
        txtProducto = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel3 = new javax.swing.JLabel();
        combox_Unidad = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtCantidad = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel6 = new javax.swing.JLabel();
        txtValorUni = new RSMaterialComponent.RSTextFieldMaterial();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Detalles  Cotizacion:");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, 210, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 50));

        btnGuardar.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnGuardar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 260, 140, -1));

        btnCancelar.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/salida (1).png"))); // NOI18N
        btnCancelar.setText("Volver");
        btnCancelar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnCancelar.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 140, -1));

        txtProducto.setForeground(new java.awt.Color(0, 0, 0));
        txtProducto.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtProducto.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtProducto.setPhColor(new java.awt.Color(0, 0, 0));
        txtProducto.setPlaceholder("Ingrese nombre pedido...");
        txtProducto.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtProductoActionPerformed(evt);
            }
        });
        jPanel1.add(txtProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 180, 30));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Pedido :");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 90, 30));

        combox_Unidad.setColorMaterial(new java.awt.Color(0, 0, 0));
        combox_Unidad.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        combox_Unidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combox_UnidadActionPerformed(evt);
            }
        });
        jPanel1.add(combox_Unidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 100, 180, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Medida:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 70, 70, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Cantidad:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 90, 30));

        txtCantidad.setForeground(new java.awt.Color(0, 0, 0));
        txtCantidad.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCantidad.setPhColor(new java.awt.Color(0, 0, 0));
        txtCantidad.setPlaceholder("Ingrese cantidad..");
        txtCantidad.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 180, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setText("Valor Unitario:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 150, 120, 30));

        txtValorUni.setForeground(new java.awt.Color(0, 0, 0));
        txtValorUni.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtValorUni.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtValorUni.setPhColor(new java.awt.Color(0, 0, 0));
        txtValorUni.setPlaceholder("Ingrese Valor Unitario..");
        txtValorUni.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtValorUni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtValorUniActionPerformed(evt);
            }
        });
        jPanel1.add(txtValorUni, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 190, 180, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String producto = txtProducto.getText().trim();
        String unidad = combox_Unidad.getSelectedItem().toString();
        String cantidadTexto = txtCantidad.getText().trim();
        String valorUnitarioTexto = txtValorUni.getText().trim();

        if (producto.isEmpty() || unidad.equals("Seleccione unidad") || cantidadTexto.isEmpty() || valorUnitarioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor complete todos los campos del producto", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int cantidad = Integer.parseInt(cantidadTexto);
            double valorUnitario = Double.parseDouble(valorUnitarioTexto);
            if (cantidad <= 0 || valorUnitario < 0) {
                JOptionPane.showMessageDialog(this, "Cantidad debe ser positiva y valor unitario no puede ser negativo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            confirmado = true; // Confirmar solo si es válido
            this.dispose(); // Cerrar después de validar
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Cantidad y valor unitario deben ser numéricos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed


    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void txtProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtProductoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtProductoActionPerformed

    private void combox_UnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combox_UnidadActionPerformed

    }//GEN-LAST:event_combox_UnidadActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtValorUniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtValorUniActionPerformed

    }//GEN-LAST:event_txtValorUniActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(InsertarCoti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(InsertarCoti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(InsertarCoti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(InsertarCoti.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                InsertarCoti dialog = new InsertarCoti(new javax.swing.JFrame(), true, "", ""); // Valores predeterminados
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
    private rojeru_san.RSButtonRiple btnCancelar;
    private rojeru_san.RSButtonRiple btnGuardar;
    private RSMaterialComponent.RSComboBoxMaterial combox_Unidad;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private RSMaterialComponent.RSTextFieldMaterial txtCantidad;
    private RSMaterialComponent.RSTextFieldMaterial txtProducto;
    private RSMaterialComponent.RSTextFieldMaterial txtValorUni;
    // End of variables declaration//GEN-END:variables

}
