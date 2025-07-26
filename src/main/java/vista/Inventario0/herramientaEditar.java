/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Inventario0;

import controlador.Ctrl_CategoriaHerramienta;
import controlador.Ctrl_MarcaHerramienta;
import controlador.Ctrl_UnidadHerramienta;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AbstractDocument;
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
public class herramientaEditar extends javax.swing.JDialog {

    private byte[] imagenBytes; // Para almacenar la imagen en bytes
    private byte[] previewImageBytes; // Para almacenar la imagen de vista previa
    private ImageIcon previewImageIcon; // Para mantener la imagen de vista previa escalada

    public HerramientaDatos material;
    private boolean guardado = false;
    private List<Categoria> categorias;
    private List<Marca> marcas;
    private List<Unidad> unidades;

    /**
     * Creates new form herramientaEditar
     */
    public herramientaEditar(java.awt.Frame parent, boolean modal, HerramientaDatos material) {
        super(parent, modal);
        initComponents();
        this.material = material;
        cargarDatosComboBoxes(); // Cargar datos en los combo boxes
        cargarMaterial(); // Llamar al método para prellenar los campos

        ((AbstractDocument) txtPrecioUnitario.getDocument()).setDocumentFilter(new NumberFormatFilterInventario());

        btnMarca.setToolTipText("<html><b>Agregar marca</html>");
        btnCategoria.setToolTipText("<html><b>Agregar categoría</html>");
        btnUM.setToolTipText("<html><b>Agregar U.M</html>");

        // Agregar esto en el constructor o método de inicialización de tu clase
        txtCantidad.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                String currentText = txtCantidad.getText();

                // Permitir: dígitos, coma, backspace y delete
                if (!(Character.isDigit(c) || c == ',' || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE)) {
                    e.consume();
                    JOptionPane.showMessageDialog(null,
                            "Solo se permiten números enteros y decimales (use coma para decimales)",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

            }
        });

    }

    // Método para cargar datos en los combo boxes
    private void cargarDatosComboBoxes() {
        Ctrl_CategoriaHerramienta ctrlCategoria = new Ctrl_CategoriaHerramienta();
        categorias = ctrlCategoria.obtenerCategoriasHerramienta(); // Asignar a la variable de clase
        cmbCategoria.removeAllItems();
        cmbCategoria.addItem("Seleccione categoría:");
        for (Categoria cat : categorias) {
            cmbCategoria.addItem(cat.getNombre());
        }

        Ctrl_MarcaHerramienta ctrlMarca = new Ctrl_MarcaHerramienta();
        marcas = ctrlMarca.obtenerMarcasHerramienta(); // Asignar a la variable de clase
        cmbMarca.removeAllItems();
        cmbMarca.addItem("Seleccione marca:");
        for (Marca m : marcas) {
            cmbMarca.addItem(m.getNombre());
        }

        Ctrl_UnidadHerramienta ctrlUnidad = new Ctrl_UnidadHerramienta();
        unidades = ctrlUnidad.obtenerUnidadesHerramienta(); // Asignar a la variable de clase
        cmbUnidad.removeAllItems();
        cmbUnidad.addItem("Seleccione unidad-medida:");
        for (Unidad um : unidades) {
            cmbUnidad.addItem(um.getNombre());
        }
    }

// Método para prellenar los campos con los datos del material
    private void cargarMaterial() {
        if (material != null) {
            txtNombre.setText(material.getNombre());
            txtDescripcion.setText(material.getDescripcion());
            txtCantidad.setText(material.getCantidad());
            // Formatear el precio unitario con puntos como separadores de miles

            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            DecimalFormat formatter = new DecimalFormat("#,###", symbols);
            formatter.setGroupingSize(3);
            txtPrecioUnitario.setText(formatter.format(material.getPrecioUnitario()));
            //txtCodigo.setText(String.valueOf(material.getIdInventario())); // Mostrar el ID como código

            // Seleccionar categoría
            for (Categoria cat : categorias) {
                if (cat.getCodigo() == material.getIdCategoria()) {
                    cmbCategoria.setSelectedItem(cat.getNombre());
                    break;
                }
            }

            // Seleccionar marca
            for (Marca m : marcas) {
                if (m.getCodigo() == material.getIdMarca()) {
                    cmbMarca.setSelectedItem(m.getNombre());
                    break;
                }
            }

            // Seleccionar unidad de medida
            for (Unidad um : unidades) {
                if (um.getCodigo() == material.getIdUnidadMedida()) {
                    cmbUnidad.setSelectedItem(um.getNombre());
                    break;
                }
            }

            // Seleccionar estado
            cmbEstado.setSelectedItem(material.getEstado());

            // Cargar imagen si existe
            if (material.getImagen() != null) {
                ImageIcon imagenIcon = new ImageIcon(material.getImagen());
                // Obtener dimensiones del lblImagen
                int width = lblImagen.getWidth();  // 140
                int height = lblImagen.getHeight(); // 140
                Image img = imagenIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(img));
            } else {
                lblImagen.setIcon(null);
            }
        }
    }

    private void cargarImagenVistaPrevia() {
        try {
            // Cargar la imagen de vista previa desde los recursos
            String rutaImagenPrevia = "/subirImagen.png"; // Ajusta la ruta según la ubicación
            java.net.URL imgURL = getClass().getResource(rutaImagenPrevia);

            if (imgURL == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la imagen de vista previa.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Leer los bytes de la imagen de vista previa
            previewImageBytes = Files.readAllBytes(new File(imgURL.toURI()).toPath());

            // Obtener dimensiones del lblImagen
            int width = lblImagen.getWidth();  // 180
            int height = lblImagen.getHeight(); // 150

            // Escalar la imagen de vista previa
            ImageIcon imagen = new ImageIcon(imgURL);
            Image img = imagen.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            previewImageIcon = new ImageIcon(img);
            lblImagen.setIcon(previewImageIcon);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen de vista previa: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

// Método para cargar la imagen que se guardará en la base de datos
    private void cargarImagenPorDefectoParaBaseDatos() {
        try {
            // Cargar la imagen predeterminada para la base de datos
            String rutaImagenDefecto = "/imagenSin.png"; // Ajusta la ruta según la ubicación
            java.net.URL imgURL = getClass().getResource(rutaImagenDefecto);

            if (imgURL == null) {
                JOptionPane.showMessageDialog(this, "No se encontró la imagen por defecto para la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Leer los bytes de la imagen predeterminada
            imagenBytes = Files.readAllBytes(new File(imgURL.toURI()).toPath());

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar la imagen por defecto para la base de datos: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    private void formatNumberField() {
        // Eliminar cualquier carácter que no sea número
        String text = txtPrecioUnitario.getText().replaceAll("[^0-9]", "");

        if (!text.isEmpty()) {
            try {
                long amount = Long.parseLong(text);

                // Formatear el número con puntos como separadores de miles
                String formatted = String.format("%,d", amount).replace(",", ".");

                // Evitar bucles infinitos al comparar antes de actualizar
                if (!formatted.equals(txtPrecioUnitario.getText())) {
                    txtPrecioUnitario.setText(formatted);
                    txtPrecioUnitario.setCaretPosition(formatted.length());
                }
            } catch (NumberFormatException e) {
                // Ignorar errores de formato
            }
        }
    }

    public boolean isGuardado() {
        return guardado;
    }

    public HerramientaDatos getMaterial() {
        return material;
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
        btnCancelar = new rojeru_san.RSButtonRiple();
        btnGuardar = new rojeru_san.RSButtonRiple();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel10 = new javax.swing.JLabel();
        cmbCategoria = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel11 = new javax.swing.JLabel();
        cmbUnidad = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel8 = new javax.swing.JLabel();
        txtCantidad = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel6 = new javax.swing.JLabel();
        txtPrecioUnitario = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel5 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        cmbMarca = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel7 = new javax.swing.JLabel();
        cmbEstado = new RSMaterialComponent.RSComboBoxMaterial();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        btnSubirImagen = new RSMaterialComponent.RSButtonShape();
        btnQuitar = new RSMaterialComponent.RSButtonShape();
        btnUM = new RSMaterialComponent.RSButtonShape();
        btnCategoria = new RSMaterialComponent.RSButtonShape();
        btnMarca = new RSMaterialComponent.RSButtonShape();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setPreferredSize(new java.awt.Dimension(232, 70));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Editar herramienta");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 70));

        btnCancelar.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar.setText("Cancelar");
        btnCancelar.setColorHover(new java.awt.Color(204, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 600, 140, -1));

        btnGuardar.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar.setText("Actualizar");
        btnGuardar.setColorHover(new java.awt.Color(0, 153, 51));
        btnGuardar.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 600, 140, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel3.setText("Nombre:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 90, -1, -1));

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
        jPanel1.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 120, 200, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Categoria:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 90, -1, -1));

        cmbCategoria.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione categoría:", "Categoría 1", "Categoría 2", "Categoría 3" }));
        cmbCategoria.setColorMaterial(new java.awt.Color(0, 0, 0));
        cmbCategoria.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        cmbCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(cmbCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 120, 200, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setText("U.M:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        cmbUnidad.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione unidad-medida:", "RSItem 1", "RSItem 2", "RSItem 3", "RSItem 4" }));
        cmbUnidad.setColorMaterial(new java.awt.Color(0, 0, 0));
        cmbUnidad.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        cmbUnidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbUnidadActionPerformed(evt);
            }
        });
        jPanel1.add(cmbUnidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, -1, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Cantidad:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, -1, -1));

        txtCantidad.setForeground(new java.awt.Color(0, 0, 0));
        txtCantidad.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtCantidad.setPhColor(new java.awt.Color(0, 0, 0));
        txtCantidad.setPlaceholder("Ingrese la cantidad...");
        txtCantidad.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtCantidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCantidadActionPerformed(evt);
            }
        });
        jPanel1.add(txtCantidad, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 200, 30));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("Precio unitario:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        txtPrecioUnitario.setForeground(new java.awt.Color(0, 0, 0));
        txtPrecioUnitario.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtPrecioUnitario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtPrecioUnitario.setPhColor(new java.awt.Color(0, 0, 0));
        txtPrecioUnitario.setPlaceholder("Ingrese el precio unitario...");
        txtPrecioUnitario.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtPrecioUnitario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPrecioUnitarioActionPerformed(evt);
            }
        });
        jPanel1.add(txtPrecioUnitario, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 200, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Descripcion: (opcinal)");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 460, -1, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Marca:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, -1, -1));

        cmbMarca.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione marca:" }));
        cmbMarca.setColorMaterial(new java.awt.Color(0, 0, 0));
        cmbMarca.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        cmbMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(cmbMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, -1, 30));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Estado:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, -1));

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione estado:", "Disponible", "Reparación", "Dañado" }));
        cmbEstado.setColorMaterial(new java.awt.Color(0, 0, 0));
        cmbEstado.setFont(new java.awt.Font("Roboto Bold", 0, 14)); // NOI18N
        cmbEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEstadoActionPerformed(evt);
            }
        });
        jPanel1.add(cmbEstado, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 270, -1, 30));

        txtDescripcion.setColumns(10);
        txtDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(1);
        txtDescripcion.setTabSize(1);
        txtDescripcion.setWrapStyleWord(true);
        jScrollPane1.setViewportView(txtDescripcion);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 190, 60));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setText("Imagen: (opcional)");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 340, -1, -1));

        lblImagen.setBackground(new java.awt.Color(153, 204, 255));
        lblImagen.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel1.add(lblImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 370, 180, 150));

        btnSubirImagen.setBackground(new java.awt.Color(28, 135, 212));
        btnSubirImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/importar.png"))); // NOI18N
        btnSubirImagen.setText("Subir");
        btnSubirImagen.setBackgroundHover(new java.awt.Color(35, 112, 210));
        btnSubirImagen.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnSubirImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSubirImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirImagenActionPerformed(evt);
            }
        });
        jPanel1.add(btnSubirImagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 530, 80, 26));

        btnQuitar.setBackground(new java.awt.Color(163, 38, 0));
        btnQuitar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/boton-menos (1).png"))); // NOI18N
        btnQuitar.setText("Quitar");
        btnQuitar.setBackgroundHover(new java.awt.Color(147, 0, 0));
        btnQuitar.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnQuitar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnQuitar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuitarActionPerformed(evt);
            }
        });
        jPanel1.add(btnQuitar, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 530, 80, 26));

        btnUM.setBackground(new java.awt.Color(46, 49, 82));
        btnUM.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnUM.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnUM.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnUM.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnUM.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnUM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnUM.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUMActionPerformed(evt);
            }
        });
        jPanel1.add(btnUM, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 25, 20));

        btnCategoria.setBackground(new java.awt.Color(46, 49, 82));
        btnCategoria.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnCategoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnCategoria.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnCategoria.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnCategoria.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnCategoria.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriaActionPerformed(evt);
            }
        });
        jPanel1.add(btnCategoria, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 90, 25, 20));

        btnMarca.setBackground(new java.awt.Color(46, 49, 82));
        btnMarca.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnMarca.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnMarca.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnMarca.setFont(new java.awt.Font("Roboto Bold", 1, 15)); // NOI18N
        btnMarca.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnMarca.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnMarca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcaActionPerformed(evt);
            }
        });
        jPanel1.add(btnMarca, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 170, 25, 20));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 570, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed

        dispose(); // Cierra la ventana emergente
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String cantidad = txtCantidad.getText().trim();
        String categoriaNombre = (String) cmbCategoria.getSelectedItem();
        String marcaNombre = (String) cmbMarca.getSelectedItem();
        String unidadNombre = (String) cmbUnidad.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        // Validar campos obligatorios
        if (nombre.isEmpty() || cantidad.isEmpty() || descripcion.isEmpty() || categoriaNombre == null || marcaNombre == null || unidadNombre == null || estado == null) {
            JOptionPane.showMessageDialog(this, "Por favor, complete todos los campos obligatorios.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validar y obtener la cantidad
        int precioUnitario;
        try {
            precioUnitario = Integer.parseInt(txtPrecioUnitario.getText().replace(".", "").trim());
            if (precioUnitario < 0) {
                JOptionPane.showMessageDialog(this, "La cantidad no puede ser negativa.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido en la cantidad.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Mapear nombres a IDs
        int idCategoria = -1;
        for (Categoria cat : categorias) {
            if (cat.getNombre().equals(categoriaNombre)) {
                idCategoria = cat.getCodigo();
                break;
            }
        }

        int idMarca = -1;
        for (Marca m : marcas) {
            if (m.getNombre().equals(marcaNombre)) {
                idMarca = m.getCodigo();
                break;
            }
        }

        int idUnidadMedida = -1;
        for (Unidad um : unidades) {
            if (um.getNombre().equals(unidadNombre)) {
                idUnidadMedida = um.getCodigo();
                break;
            }
        }

        if (idCategoria == -1 || idMarca == -1 || idUnidadMedida == -1) {
            JOptionPane.showMessageDialog(this, "Error al mapear los datos seleccionados.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar los datos del material
        if (material != null) {
            material.setNombre(nombre);
            material.setDescripcion(descripcion);
            material.setCantidad(cantidad);
            material.setPrecioUnitario(precioUnitario);
            material.setEstado(estado);
            material.setIdCategoria(idCategoria);
            material.setIdMarca(idMarca);
            material.setIdUnidadMedida(idUnidadMedida);
            material.setImagen(imagenBytes != null ? imagenBytes : material.getImagen()); // Usar nueva imagen o mantener la original
        } else {
            JOptionPane.showMessageDialog(this, "Error: material no está inicializado.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        guardado = true; // Marcar que se han guardado los cambios
        dispose(); // Cierra el JDialog
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void cmbCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbCategoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbCategoriaActionPerformed

    private void cmbUnidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbUnidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbUnidadActionPerformed

    private void txtCantidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCantidadActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCantidadActionPerformed

    private void txtPrecioUnitarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPrecioUnitarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPrecioUnitarioActionPerformed

    private void cmbMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMarcaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMarcaActionPerformed

    private void cmbEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbEstadoActionPerformed

    private void btnSubirImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirImagenActionPerformed
        try {
            // Configurar el Look and Feel del sistema
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Seleccionar imagen del material");

            // Filtro actualizado que incluye WEBP
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Archivos de imagen (JPG, PNG, JPEG, GIF, BMP, WEBP)",
                    "jpg", "png", "jpeg", "gif", "bmp", "webp");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(true);

            // Configuración adicional
            fileChooser.setFileHidingEnabled(false);
            fileChooser.rescanCurrentDirectory();

            int opcion = fileChooser.showOpenDialog(this);
            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();
                try {
                    // Verificar extensión WEBP
                    if (archivo.getName().toLowerCase().endsWith(".webp")) {
                        // Usar ImageIO para leer WEBP (requiere dependencia adicional)
                        BufferedImage originalImage = ImageIO.read(archivo);
                        if (originalImage == null) {
                            throw new IOException("No se pudo leer el archivo WEBP");
                        }

                        // Convertir a byte array
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        ImageIO.write(originalImage, "png", baos);
                        imagenBytes = baos.toByteArray();

                        // Mostrar previsualización
                        Image scaledImage = originalImage.getScaledInstance(
                                lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
                        previewImageIcon = new ImageIcon(scaledImage);
                    } else {
                        // Procesamiento normal para otros formatos
                        imagenBytes = Files.readAllBytes(archivo.toPath());
                        ImageIcon imagen = new ImageIcon(archivo.getAbsolutePath());
                        Image img = imagen.getImage().getScaledInstance(
                                lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
                        previewImageIcon = new ImageIcon(img);
                    }

                    lblImagen.setIcon(previewImageIcon);
                    lblImagen.setText("");

                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Error al procesar la imagen:\n" + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                    cargarImagenVistaPrevia();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error en el selector de archivos:\n" + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnSubirImagenActionPerformed

    private void btnQuitarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuitarActionPerformed
        // Restaurar la imagen predeterminada y limpiar imagenBytes
        imagenBytes = null;
        cargarImagenPorDefectoParaBaseDatos(); // Reestablecer imagen predeterminada para base de datos
        cargarImagenVistaPrevia(); // Restaurar la vista previa predeterminada
    }//GEN-LAST:event_btnQuitarActionPerformed

    private void btnUMActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUMActionPerformed
        nuevaUMherramienta dialog = new nuevaUMherramienta(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);

        dialog.setCategoriaGuardadaListener(() -> {
            // Recargar unidades y actualizar la lista interna
            Ctrl_UnidadHerramienta ctrl = new Ctrl_UnidadHerramienta();
            unidades = ctrl.obtenerUnidadesHerramienta();

            // Actualizar ComboBox
            cmbUnidad.removeAllItems();
            cmbUnidad.addItem("Seleccione unidad-medida:");
            for (Unidad um : unidades) {
                cmbUnidad.addItem(um.getNombre());
            }

            // Mantener la selección actual si existe
            if (material != null) {
                for (Unidad um : unidades) {
                    if (um.getCodigo() == material.getIdUnidadMedida()) {
                        cmbUnidad.setSelectedItem(um.getNombre());
                        break;
                    }
                }
            }
        });

        dialog.setVisible(true);
    }//GEN-LAST:event_btnUMActionPerformed

    private void btnCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriaActionPerformed
        nuevaCategoriaHerramienta dialog = new nuevaCategoriaHerramienta(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);

        dialog.setCategoriaGuardadaListener(() -> {
            // Recargar categorías y actualizar la lista interna
            Ctrl_CategoriaHerramienta ctrl = new Ctrl_CategoriaHerramienta();
            categorias = ctrl.obtenerCategoriasHerramienta();

            // Actualizar ComboBox
            cmbCategoria.removeAllItems();
            cmbCategoria.addItem("Seleccione categoría:");
            for (Categoria cat : categorias) {
                cmbCategoria.addItem(cat.getNombre());
            }

            // Mantener la selección actual si existe
            if (material != null) {
                for (Categoria cat : categorias) {
                    if (cat.getCodigo() == material.getIdCategoria()) {
                        cmbCategoria.setSelectedItem(cat.getNombre());
                        break;
                    }
                }
            }
        });

        dialog.setVisible(true);
    }//GEN-LAST:event_btnCategoriaActionPerformed

    private void btnMarcaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcaActionPerformed
        nuevaMarcaHerramienta dialog = new nuevaMarcaHerramienta(new javax.swing.JFrame(), true);
        dialog.setLocationRelativeTo(null);

        dialog.setCategoriaGuardadaListener(() -> {
            // Recargar marcas y actualizar la lista interna
            Ctrl_MarcaHerramienta ctrl = new Ctrl_MarcaHerramienta();
            marcas = ctrl.obtenerMarcasHerramienta();

            // Actualizar ComboBox
            cmbMarca.removeAllItems();
            cmbMarca.addItem("Seleccione marca:");
            for (Marca m : marcas) {
                cmbMarca.addItem(m.getNombre());
            }

            // Mantener la selección actual si existe
            if (material != null) {
                for (Marca m : marcas) {
                    if (m.getCodigo() == material.getIdMarca()) {
                        cmbMarca.setSelectedItem(m.getNombre());
                        break;
                    }
                }
            }
        });

        dialog.setVisible(true);
    }//GEN-LAST:event_btnMarcaActionPerformed

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
            java.util.logging.Logger.getLogger(herramientaEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(herramientaEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(herramientaEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(herramientaEditar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                herramientaEditar dialog = new herramientaEditar(new javax.swing.JFrame(), true, null);
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
    private RSMaterialComponent.RSButtonShape btnCategoria;
    private rojeru_san.RSButtonRiple btnGuardar;
    private RSMaterialComponent.RSButtonShape btnMarca;
    private RSMaterialComponent.RSButtonShape btnQuitar;
    private RSMaterialComponent.RSButtonShape btnSubirImagen;
    private RSMaterialComponent.RSButtonShape btnUM;
    private RSMaterialComponent.RSComboBoxMaterial cmbCategoria;
    private RSMaterialComponent.RSComboBoxMaterial cmbEstado;
    private RSMaterialComponent.RSComboBoxMaterial cmbMarca;
    private RSMaterialComponent.RSComboBoxMaterial cmbUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblImagen;
    private RSMaterialComponent.RSTextFieldMaterial txtCantidad;
    private javax.swing.JTextArea txtDescripcion;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    private RSMaterialComponent.RSTextFieldMaterial txtPrecioUnitario;
    // End of variables declaration//GEN-END:variables
}
