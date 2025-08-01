/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.InventarioTrap;

import vista.Inventario0.*;
import controlador.Ctrl_CategoriaHerramienta;
import controlador.Ctrl_MarcaHerramienta;
import controlador.Ctrl_UnidadHerramienta;
import java.awt.Image;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import javax.swing.ImageIcon;
import modelo.Categoria;
import modelo.HerramientaDatos;
import modelo.Marca;
import modelo.MaterialDatos;
import modelo.Unidad;
import vista.TemaManager;

/**
 *
 * @author ZenBook
 */
public class herramientaInfo extends javax.swing.JDialog {

    /**
     * Creates new form herramientaInfo
     */
    public herramientaInfo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public void mostrarMaterial(HerramientaDatos material) {
        // Configurar el formateador de números
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        DecimalFormat formatter = new DecimalFormat("#,###", symbols);
        formatter.setGroupingSize(3);

        lblCodigo.setText(String.valueOf(material.getIdInventario())); // Usar idInventario como código
        lblNombre.setText(material.getNombre());
        lblDescripcion.setText(material.getDescripcion());
        lblCantidad.setText(String.valueOf(material.getCantidad()));
        lblPrecio.setText(formatter.format(material.getPrecioUnitario())); // Formateado con puntos
        lblEstado.setText(material.getEstado());

        Ctrl_CategoriaHerramienta ctrlCategoria = new Ctrl_CategoriaHerramienta();
        List<Categoria> categorias = ctrlCategoria.obtenerCategoriasHerramienta();
        String nombreCategoria = "Sin categoría";
        for (Categoria cat : categorias) {
            if (cat.getCodigo() == material.getIdCategoria()) {
                nombreCategoria = cat.getNombre();
                break;
            }
        }
        lblCategoria.setText(nombreCategoria);

        Ctrl_MarcaHerramienta ctrlMarca = new Ctrl_MarcaHerramienta();
        List<Marca> marcas = ctrlMarca.obtenerMarcasHerramienta();
        String nombreMarca = "Sin marca";
        for (Marca m : marcas) {
            if (m.getCodigo() == material.getIdMarca()) {
                nombreMarca = m.getNombre();
                break;
            }
        }
        // Asumimos que txtUnidadMedida debería mostrar la marca, ya que no hay un campo directo para unidad en la interfaz
        lblMarca.setText(nombreMarca); // Corregir esto si debe mostrar la unidad de medida

        Ctrl_UnidadHerramienta ctrlUnidad = new Ctrl_UnidadHerramienta();
        List<Unidad> unidades = ctrlUnidad.obtenerUnidadesHerramienta();
        String nombreUnidad = "Sin unidad de medida";
        for (Unidad um : unidades) {
            if (um.getCodigo() == material.getIdUnidadMedida()) {
                nombreUnidad = um.getNombre();
                break;
            }
        }
        // Si quieres mostrar la unidad de medida, podrías agregar un nuevo campo txtUnidadMedidaReal
        // Por ahora, corregiremos txtUnidadMedida para que muestre la unidad
        lblUnidad.setText(nombreUnidad); // Ajuste para mostrar la unidad de medida

        // Cargar imagen si existe
        if (material.getImagen() != null) {
            ImageIcon imagenIcon = new ImageIcon(material.getImagen());
            // Obtener dimensiones del lblImagen
            int width = lblImagen.getWidth();  // 190
            int height = lblImagen.getHeight(); // 160
            Image img = imagenIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        } else {
            lblImagen.setText("No hay imagen");
            lblImagen.setIcon(null);
        }
    }

    private void formatNumberField() {
        // Eliminar cualquier carácter que no sea número
        String text = lblPrecio.getText().replaceAll("[^0-9]", "");

        if (!text.isEmpty()) {
            try {
                long amount = Long.parseLong(text);

                // Formatear el número con puntos como separadores de miles
                String formatted = String.format("%,d", amount).replace(",", ".");

                // Evitar bucles infinitos al comparar antes de actualizar
                if (!formatted.equals(lblPrecio.getText())) {
                    lblPrecio.setText(formatted);

                }
            } catch (NumberFormatException e) {
                // Ignorar errores de formato
            }
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
        jLabel2 = new javax.swing.JLabel();
        lblCodigo = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblMarca = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblUnidad = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblCantidad = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        lblPrecio = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        btnCancelar1 = new rojeru_san.RSButtonRiple();
        jLabel12 = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lblDescripcion = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(530, 575));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(520, 540));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Información de la herramienta");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 50));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Codigo:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 70, -1, -1));

        lblCodigo.setBackground(new java.awt.Color(255, 255, 255));
        lblCodigo.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblCodigo.setText("jLabel12");
        lblCodigo.setOpaque(true);
        jPanel1.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, 140, 20));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel7.setText("Marca:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 150, -1, -1));

        lblMarca.setBackground(new java.awt.Color(255, 255, 255));
        lblMarca.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblMarca.setText("jLabel12");
        lblMarca.setOpaque(true);
        jPanel1.add(lblMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 150, 120, 20));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel10.setText("Nombre:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        lblNombre.setBackground(new java.awt.Color(255, 255, 255));
        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblNombre.setText("jLabel12");
        lblNombre.setOpaque(true);
        jPanel1.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 110, 130, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setText("Unidad de medida:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        lblUnidad.setBackground(new java.awt.Color(255, 255, 255));
        lblUnidad.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblUnidad.setText("jLabel12");
        lblUnidad.setOpaque(true);
        jPanel1.add(lblUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 150, 80, 20));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setText("Categoria:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 70, -1, -1));

        lblCategoria.setBackground(new java.awt.Color(255, 255, 255));
        lblCategoria.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblCategoria.setText("jLabel12");
        lblCategoria.setOpaque(true);
        jPanel1.add(lblCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 110, 20));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel8.setText("Cantidad:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, -1, -1));

        lblCantidad.setBackground(new java.awt.Color(255, 255, 255));
        lblCantidad.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblCantidad.setText("jLabel12");
        lblCantidad.setOpaque(true);
        jPanel1.add(lblCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 90, 20));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setText("Descripcion:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 190, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel11.setText("Precio unitario:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        lblPrecio.setBackground(new java.awt.Color(255, 255, 255));
        lblPrecio.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblPrecio.setText("jLabel12");
        lblPrecio.setOpaque(true);
        jPanel1.add(lblPrecio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, 90, 20));
        jPanel1.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 470, 20));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setText("Imagen:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, -1, 30));

        lblImagen.setBackground(new java.awt.Color(153, 204, 255));
        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 160, 150));

        btnCancelar1.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar1.setText("Volver");
        btnCancelar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 490, 140, -1));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel12.setText("Estado:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 110, -1, -1));

        lblEstado.setBackground(new java.awt.Color(255, 255, 255));
        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        lblEstado.setText("jLabel12");
        lblEstado.setOpaque(true);
        jPanel1.add(lblEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 110, 130, 20));

        lblDescripcion.setEditable(false);
        lblDescripcion.setColumns(10);
        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescripcion.setLineWrap(true);
        lblDescripcion.setRows(1);
        lblDescripcion.setTabSize(1);
        lblDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(lblDescripcion);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 220, 180, 50));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 520, 540));
        jPanel1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed

        dispose(); // Cierra la ventana emergente
    }//GEN-LAST:event_btnCancelar1ActionPerformed

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
            java.util.logging.Logger.getLogger(herramientaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(herramientaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(herramientaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(herramientaInfo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                herramientaInfo dialog = new herramientaInfo(new javax.swing.JFrame(), true);
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
    private rojeru_san.RSButtonRiple btnCancelar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCantidad;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JTextArea lblDescripcion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblMarca;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPrecio;
    private javax.swing.JLabel lblUnidad;
    // End of variables declaration//GEN-END:variables
}
