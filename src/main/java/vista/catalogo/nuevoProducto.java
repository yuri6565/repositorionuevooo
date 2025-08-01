/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.catalogo;

import controlador.Ctrl_productocatalogo;
import java.awt.Color;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.PlainDocument;
import java.awt.event.KeyAdapter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import modelo.Catalogocategoria;
import modelo.Categoria;
import modelo.catalogoproducto;

public class nuevoProducto extends javax.swing.JDialog {

    private String rutaImagenSeleccionada;
    private List<byte[]> imagenesBytes = new ArrayList<>();
    private rojeru_san.rspanel.RSPanelImage[] etiquetasImagenes;
    private rojerusan.RSLabelIcon[] etiquetasIconos;
    private int idCategoria;
    private String nombreCategoria;
    private static final List<String> UNIDADES = Arrays.asList("cm", "m", "mm", "cm²", "cm³", "m²", "m³", "in", "ft");

    public nuevoProducto(java.awt.Frame parent, boolean modal, int idCategoria, String nombreCategoria) {
        super(parent, modal);
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria != null ? nombreCategoria : "Categoría desconocida";
        initComponents();
        this.setLocationRelativeTo(null); // Centrar el diálogo
        jlabelnombre.setVisible(false);
        jlabelalto.setVisible(false);
        jlabelcolor.setVisible(false);
        jlabelma.setVisible(false);

        configurarAutocompletadoUnidades();
        List<rojeru_san.rspanel.RSPanelImage> imagenesList = new ArrayList<>();
        if (rSLabelImage3 != null) {
            imagenesList.add(rSLabelImage3);
        } else {
            System.out.println("rSLabelImage3 es null");
        }
        if (rSLabelImage6 != null) {
            imagenesList.add(rSLabelImage6);
        } else {
            System.out.println("rSLabelImage6 es null");
        }
        if (rSLabelImage8 != null) {
            imagenesList.add(rSLabelImage8);
        } else {
            System.out.println("rSLabelImage8 es null");
        }
        if (rSLabelImage9 != null) {
            imagenesList.add(rSLabelImage9);
        } else {
            System.out.println("rSLabelImage9 es null");
        }
        etiquetasImagenes = imagenesList.toArray(new rojeru_san.rspanel.RSPanelImage[0]);
        List<rojerusan.RSLabelIcon> iconosList = new ArrayList<>();
        if (rSLabelIcon6 != null) {
            iconosList.add(rSLabelIcon6);
        } else {
            System.out.println("rSLabelIcon6 es null");
        }
        if (rSLabelIcon7 != null) {
            iconosList.add(rSLabelIcon7);
        } else {
            System.out.println("rSLabelIcon7 es null");
        }
        if (rSLabelIcon8 != null) {
            iconosList.add(rSLabelIcon8);
        } else {
            System.out.println("rSLabelIcon8 es null");
        }
        if (rSLabelIcon9 != null) {
            iconosList.add(rSLabelIcon9);
        } else {
            System.out.println("rSLabelIcon9 es null");
        }
        etiquetasIconos = iconosList.toArray(new rojerusan.RSLabelIcon[0]);
        agregarListenersAImagenes();
        jLabel14.setText("Agregar Producto a " + this.nombreCategoria);
        System.out.println("etiquetasImagenes inicializado con " + etiquetasImagenes.length + " elementos, etiquetasIconos con " + etiquetasIconos.length + " elementos");

        // Agregar validaciones en tiempo real
        agregarValidaciones();
    }

    public String getRutaImagenSeleccionada() {
        return rutaImagenSeleccionada;
    }

    public String getNombre() {
        return txtNombre.getText().trim();
    }

    public void setNombre(String nombre) {
        txtNombre.setText(nombre);
    }

    public String getAlto() {
        return txtNombre13.getText().trim();
    }

    public void setAlto(String alto) {
        txtNombre13.setText(alto);
    }

    public String getAncho() {
        return txtNombre3.getText().trim();
    }

    public void setAncho(String ancho) {
        txtNombre3.setText(ancho);
    }

    public String getProfundidad() {
        return txtNombre1.getText().trim();
    }

    public void setProfundidad(String profundidad) {
        txtNombre1.setText(profundidad);
    }

    public String getDescripcion() {
        return jTextArea1.getText().trim();
    }

    public void setDescripcion(String descripcion) {
        jTextArea1.setText(descripcion);
    }

    public String getColor() {
        return txtcolor.getText().trim();
    }

    public void setColor(String color) {
        txtcolor.setText(color);
    }

    public String getMaterial() {
        return txtmaterial1.getText().trim();
    }

    public void setMaterial(String material) {
        txtmaterial1.setText(material);
    }

    public void setImagenDesdeBytes(byte[] imagen) {
        if (imagen != null && imagen.length > 0) {
            ImageIcon icono = new ImageIcon(imagen);
            rSLabelImage3.setImagen(new ImageIcon(icono.getImage().getScaledInstance(
                    rSLabelImage3.getWidth(), rSLabelImage3.getHeight(), Image.SCALE_SMOOTH)));
            imagenesBytes.add(imagen);
            rSLabelIcon6.setVisible(false);
        }
    }

    public void setImagenesDesdeBytes(byte[] imagen1, byte[] imagen2, byte[] imagen3, byte[] imagen4) {
        imagenesBytes.clear();
        if (imagen1 != null && imagen1.length > 0) {
            imagenesBytes.add(imagen1);
            ImageIcon icono = new ImageIcon(imagen1);
            rSLabelImage3.setImagen(new ImageIcon(icono.getImage().getScaledInstance(
                    rSLabelImage3.getWidth(), rSLabelImage3.getHeight(), Image.SCALE_SMOOTH)));
            rSLabelIcon6.setVisible(false);
        }
        if (imagen2 != null && imagen2.length > 0) {
            imagenesBytes.add(imagen2);
            ImageIcon icono = new ImageIcon(imagen2);
            rSLabelImage6.setImagen(new ImageIcon(icono.getImage().getScaledInstance(
                    rSLabelImage6.getWidth(), rSLabelImage6.getHeight(), Image.SCALE_SMOOTH)));
            rSLabelIcon7.setVisible(false);
        }
        if (imagen3 != null && imagen3.length > 0) {
            imagenesBytes.add(imagen3);
            ImageIcon icono = new ImageIcon(imagen3);
            rSLabelImage8.setImagen(new ImageIcon(icono.getImage().getScaledInstance(
                    rSLabelImage8.getWidth(), rSLabelImage8.getHeight(), Image.SCALE_SMOOTH)));
            rSLabelIcon8.setVisible(false);
        }
        if (imagen4 != null && imagen4.length > 0) {
            imagenesBytes.add(imagen4);
            ImageIcon icono = new ImageIcon(imagen4);
            rSLabelImage9.setImagen(new ImageIcon(icono.getImage().getScaledInstance(
                    rSLabelImage9.getWidth(), rSLabelImage9.getHeight(), Image.SCALE_SMOOTH)));
            rSLabelIcon9.setVisible(false);
        }
    }

    private void agregarListenersAImagenes() {
        if (etiquetasImagenes != null && etiquetasImagenes.length > 0) {
            for (int i = 0; i < etiquetasImagenes.length; i++) {
                if (etiquetasImagenes[i] != null) {
                    final int indice = i;
                    etiquetasImagenes[i].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            ThumbnailFileChooser fileChooser = new ThumbnailFileChooser();
                            fileChooser.setMultiSelectionEnabled(true);
                            fileChooser.setCurrentDirectory(new File("C:\\Users\\yuriiii\\Escritorio\\yuriiii"));

                            int resultado = fileChooser.showOpenDialog(nuevoProducto.this);
                            if (resultado == JFileChooser.APPROVE_OPTION) {
                                File[] archivosSeleccionados = fileChooser.getSelectedFiles();
                                for (int j = 0; j < archivosSeleccionados.length && (indice + j) < etiquetasImagenes.length; j++) {
                                    File archivoSeleccionado = archivosSeleccionados[j];
                                    if (archivoSeleccionado.length() > 5 * 1024 * 1024) {
                                        JOptionPane.showMessageDialog(nuevoProducto.this,
                                                "La imagen " + archivoSeleccionado.getName() + " excede el tamaño máximo de 5 MB.",
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                        continue;
                                    }
                                    try {
                                        byte[] imagenBytes = Files.readAllBytes(archivoSeleccionado.toPath());
                                        imagenesBytes.add(imagenBytes);
                                        ImageIcon icono = new ImageIcon(imagenBytes);
                                        MediaTracker mt = new MediaTracker(etiquetasImagenes[indice + j]);
                                        mt.addImage(icono.getImage(), 0);
                                        mt.waitForID(0);
                                        Image scaledImage = icono.getImage().getScaledInstance(
                                                etiquetasImagenes[indice + j].getWidth(),
                                                etiquetasImagenes[indice + j].getHeight(),
                                                Image.SCALE_SMOOTH
                                        );
                                        etiquetasImagenes[indice + j].setImagen(new ImageIcon(scaledImage));
                                        if (indice + j < etiquetasIconos.length && etiquetasIconos[indice + j] != null) {
                                            etiquetasIconos[indice + j].setVisible(false);
                                        }
                                        System.out.println("Imagen cargada en índice " + (indice + j) + " con tamaño " + scaledImage.getWidth(null) + "x" + scaledImage.getHeight(null));
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(nuevoProducto.this,
                                                "Error al cargar la imagen: " + ex.getMessage(),
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    } catch (InterruptedException ex) {
                                        JOptionPane.showMessageDialog(nuevoProducto.this,
                                                "Error al procesar la imagen: " + ex.getMessage(),
                                                "Error",
                                                JOptionPane.ERROR_MESSAGE);
                                    }
                                }
                                if (archivosSeleccionados.length > etiquetasImagenes.length - indice) {
                                    JOptionPane.showMessageDialog(nuevoProducto.this,
                                            "Solo se pueden mostrar " + (etiquetasImagenes.length - indice) + " imágenes en la interfaz.",
                                            "Información",
                                            JOptionPane.INFORMATION_MESSAGE);
                                }
                            }
                        }
                    });
                } else {
                    System.out.println("Error: etiquetasImagenes[" + i + "] es null");
                }
            }
        } else {
            System.out.println("Error: etiquetasImagenes no está inicializado o está vacío.");
        }
    }

    private void configurarAutocompletadoUnidades() {
        configurarCampoConAutocompletado(txtNombre13);
        configurarCampoConAutocompletado(txtNombre3);
        configurarCampoConAutocompletado(txtNombre1);
    }

    private void configurarCampoConAutocompletado(RSMaterialComponent.RSTextFieldMaterial campo) {
        JPopupMenu popupMenu = new JPopupMenu();
        campo.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                String text = campo.getText().trim();
                if (e.getKeyChar() == ' ') {
                    String[] partes = text.split("\\s+");
                    if (partes.length == 1 && isNumeric(partes[0])) {
                        popupMenu.removeAll();
                        for (String unidad : UNIDADES) {
                            JMenuItem item = new JMenuItem(unidad);
                            item.addActionListener(evt -> {
                                campo.setText(partes[0] + " " + unidad);
                                popupMenu.setVisible(false);
                            });
                            popupMenu.add(item);
                        }
                        popupMenu.show(campo, campo.getWidth() / 2, campo.getHeight());
                    }
                } else if (text.contains(" ")) {
                    String[] partes = text.split("\\s+");
                    if (partes.length == 2 && isNumeric(partes[0])) {
                        String letra = String.valueOf(e.getKeyChar()).toLowerCase();
                        popupMenu.removeAll();
                        for (String unidad : UNIDADES) {
                            if (unidad.toLowerCase().startsWith(letra)) {
                                JMenuItem item = new JMenuItem(unidad);
                                item.addActionListener(evt -> {
                                    campo.setText(partes[0] + " " + unidad);
                                    popupMenu.setVisible(false);
                                });
                                popupMenu.add(item);
                            }
                        }
                        if (popupMenu.getComponentCount() > 0) {
                            popupMenu.show(campo, campo.getWidth() / 2, campo.getHeight());
                        } else {
                            popupMenu.setVisible(false);
                        }
                    }
                }
            }
        });
    }

    private boolean isNumeric(String str) {
        return str.matches("[0-9]*\\.?[0-9]*");
    }

    private boolean esMedidaValida(String medida) {
        if (medida.trim().isEmpty()) {
            return true;
        }
        String[] partes = medida.trim().split("\\s+");
        if (partes.length < 1 || partes.length > 2) {
            return false;
        }
        try {
            Double.parseDouble(partes[0]);
            if (partes.length == 2) {
                String unidad = partes[1];
                return UNIDADES.contains(unidad);
            }
            return true; // Permitir solo número sin unidad
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private void agregarValidaciones() {
        // Validación para txtNombre
        txtNombre.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtNombre.getText().trim().isEmpty()) {
                    jlabelnombre.setText("Este campo es obligatorio");
                    jlabelnombre.setForeground(Color.RED);
                    jlabelnombre.setVisible(true);
                } else {
                    jlabelnombre.setVisible(false);
                    jlabelnombre.setText("TIPO");
                }
            }
        });
        txtNombre.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!txtNombre.getText().trim().isEmpty()) {
                    jlabelnombre.setVisible(false);
                    jlabelnombre.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtNombre13.requestFocusInWindow();
                }
            }
        });

        // Validación para txtNombre13 (Alto)
        txtNombre13.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = txtNombre13.getText().trim();
                if (!texto.isEmpty() && !esMedidaValida(texto)) {
                    jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
                    jlabelalto.setForeground(Color.RED);
                    jlabelalto.setVisible(true);
                } else {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }
        });
        txtNombre13.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (esMedidaValida(txtNombre13.getText().trim())) {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtNombre3.requestFocusInWindow();
                }
            }
        });

        // Validación para txtNombre3 (Ancho)
        txtNombre3.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = txtNombre3.getText().trim();
                if (!texto.isEmpty() && !esMedidaValida(texto)) {
                    jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
                    jlabelalto.setForeground(Color.RED);
                    jlabelalto.setVisible(true);
                } else {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }
        });
        txtNombre3.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (esMedidaValida(txtNombre3.getText().trim())) {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtNombre1.requestFocusInWindow();
                }
            }
        });

        // Validación para txtNombre1 (Profundidad)
        txtNombre1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = txtNombre1.getText().trim();
                if (!texto.isEmpty() && !esMedidaValida(texto)) {
                    jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
                    jlabelalto.setForeground(Color.RED);
                    jlabelalto.setVisible(true);
                } else {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }
        });
        txtNombre1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (esMedidaValida(txtNombre1.getText().trim())) {
                    jlabelalto.setVisible(false);
                    jlabelalto.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    jTextArea1.requestFocusInWindow();
                }
            }
        });

        // Validación para txtcolor
        txtcolor.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtcolor.getText().trim().isEmpty()) {
                    jlabelcolor.setText("Este campo es obligatorio");
                    jlabelcolor.setForeground(Color.RED);
                    jlabelcolor.setVisible(true);
                } else {
                    jlabelcolor.setVisible(false);
                    jlabelcolor.setText("TIPO");
                }
            }
        });
        txtcolor.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!txtcolor.getText().trim().isEmpty()) {
                    jlabelcolor.setVisible(false);
                    jlabelcolor.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtmaterial1.requestFocusInWindow();
                }
            }
        });

        // Validación para txtmaterial1
        txtmaterial1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (txtmaterial1.getText().trim().isEmpty()) {
                    jlabelma.setText("Este campo es obligatorio");
                    jlabelma.setForeground(Color.RED);
                    jlabelma.setVisible(true);
                } else {
                    jlabelma.setVisible(false);
                    jlabelma.setText("TIPO");
                }
            }
        });
        txtmaterial1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!txtmaterial1.getText().trim().isEmpty()) {
                    jlabelma.setVisible(false);
                    jlabelma.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGuardar.doClick();
                }
            }
        });

        // Validación para jTextArea1 (Descripción, opcional)
        jTextArea1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Descripción es opcional, no se valida
            }
        });
        jTextArea1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtcolor.requestFocusInWindow();
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtNombre4 = new RSMaterialComponent.RSTextFieldMaterial();
        panelP = new javax.swing.JPanel();
        panelP2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        btnGuardar2 = new rojeru_san.RSButtonRiple();
        txtNombre = new RSMaterialComponent.RSTextFieldMaterial();
        txtNombre1 = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel18 = new javax.swing.JLabel();
        txtcolor = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtNombre13 = new RSMaterialComponent.RSTextFieldMaterial();
        txtNombre3 = new RSMaterialComponent.RSTextFieldMaterial();
        rSLabelImage3 = new rojeru_san.rspanel.RSPanelImage();
        rSLabelIcon6 = new rojerusan.RSLabelIcon();
        rSLabelImage6 = new rojeru_san.rspanel.RSPanelImage();
        rSLabelIcon7 = new rojerusan.RSLabelIcon();
        jLabel15 = new javax.swing.JLabel();
        btnCancelar1 = new rojeru_san.RSButtonRiple();
        btnGuardar = new rojeru_san.RSButtonRiple();
        jLabel8 = new javax.swing.JLabel();
        txtmaterial1 = new RSMaterialComponent.RSTextFieldMaterial();
        rSLabelImage4 = new javax.swing.JLabel();
        rSLabelImage5 = new javax.swing.JLabel();
        rSLabelImage7 = new javax.swing.JLabel();
        rSLabelImage8 = new rojeru_san.rspanel.RSPanelImage();
        rSLabelIcon8 = new rojerusan.RSLabelIcon();
        rSLabelImage9 = new rojeru_san.rspanel.RSPanelImage();
        rSLabelIcon9 = new rojerusan.RSLabelIcon();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel21 = new javax.swing.JLabel();
        jlabelnombre = new javax.swing.JLabel();
        jlabelalto = new javax.swing.JLabel();
        jlabelma = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jlabelcolor = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();

        txtNombre4.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre4.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre4.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre4.setPlaceholder("Alto");
        txtNombre4.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre4ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        panelP.setBackground(new java.awt.Color(255, 255, 255));
        panelP.setPreferredSize(new java.awt.Dimension(500, 500));
        panelP.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelP2.setBackground(new java.awt.Color(255, 255, 255));
        panelP2.setPreferredSize(new java.awt.Dimension(500, 500));
        panelP2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(46, 49, 82));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Agregar Producto");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        btnGuardar2.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        btnGuardar2.setColorHover(new java.awt.Color(204, 0, 0));
        btnGuardar2.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnGuardar2.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar2ActionPerformed(evt);
            }
        });
        jPanel4.add(btnGuardar2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 0, 40, 30));

        panelP2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 70));

        txtNombre.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre.setPlaceholder("Ingrese el nombre...");
        txtNombre.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre2ActionPerformed(evt);
            }
        });
        panelP2.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 250, 30));

        txtNombre1.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre1.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre1.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre1.setPlaceholder("Profundidad");
        txtNombre1.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre6ActionPerformed(evt);
            }
        });
        panelP2.add(txtNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 220, 90, 30));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel18.setText("Imagen:");
        panelP2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 80, -1, -1));

        txtcolor.setForeground(new java.awt.Color(0, 0, 0));
        txtcolor.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtcolor.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcolor.setPhColor(new java.awt.Color(0, 0, 0));
        txtcolor.setPlaceholder("Ingrese el nombre...");
        txtcolor.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtcolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcolor1ActionPerformed(evt);
            }
        });
        panelP2.add(txtcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 450, 250, 30));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel19.setText("Descripción:");
        panelP2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel20.setText("Color:");
        panelP2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 420, -1, -1));

        txtNombre13.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre13.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre13.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre13.setPlaceholder("Alto");
        txtNombre13.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre8ActionPerformed(evt);
            }
        });
        panelP2.add(txtNombre13, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 60, 30));

        txtNombre3.setForeground(new java.awt.Color(0, 0, 0));
        txtNombre3.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtNombre3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtNombre3.setPhColor(new java.awt.Color(0, 0, 0));
        txtNombre3.setPlaceholder("Ancho");
        txtNombre3.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtNombre3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombre9ActionPerformed(evt);
            }
        });
        panelP2.add(txtNombre3, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 220, 60, 30));

        rSLabelImage3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rSLabelImage3.setImagen(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ajustesblanco.png"))); // NOI18N
        rSLabelImage3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSLabelIcon6.setForeground(new java.awt.Color(204, 204, 204));
        rSLabelIcon6.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.ADD_A_PHOTO);
        rSLabelImage3.add(rSLabelIcon6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, -1));

        panelP2.add(rSLabelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 100, 100));

        rSLabelImage6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rSLabelImage6.setImagen(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ajustesblanco.png"))); // NOI18N
        rSLabelImage6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSLabelIcon7.setForeground(new java.awt.Color(204, 204, 204));
        rSLabelIcon7.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.ADD_A_PHOTO);
        rSLabelImage6.add(rSLabelIcon7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, -1));

        panelP2.add(rSLabelImage6, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 100, 100));

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel15.setText("Delante");
        panelP2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 210, -1, -1));

        btnCancelar1.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar1.setText("Cancelar");
        btnCancelar1.setColorHover(new java.awt.Color(204, 0, 0));
        btnCancelar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });
        panelP2.add(btnCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 530, 140, -1));

        btnGuardar.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar.setText("Guardar");
        btnGuardar.setColorHover(new java.awt.Color(0, 153, 51));
        btnGuardar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        panelP2.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 530, 140, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Material:");
        panelP2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 420, 70, 20));

        txtmaterial1.setForeground(new java.awt.Color(0, 0, 0));
        txtmaterial1.setColorMaterial(new java.awt.Color(0, 0, 0));
        txtmaterial1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtmaterial1.setPhColor(new java.awt.Color(0, 0, 0));
        txtmaterial1.setPlaceholder("Ingrese el nombre...");
        txtmaterial1.setSelectionColor(new java.awt.Color(0, 0, 0));
        txtmaterial1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtmaterial1ActionPerformed(evt);
            }
        });
        panelP2.add(txtmaterial1, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 450, 230, 30));

        rSLabelImage4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rSLabelImage4.setText("Perspectiva");
        panelP2.add(rSLabelImage4, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 330, 70, 20));

        rSLabelImage5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rSLabelImage5.setText("De lado");
        panelP2.add(rSLabelImage5, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 210, 50, -1));

        rSLabelImage7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rSLabelImage7.setText("Izquierda");
        panelP2.add(rSLabelImage7, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 60, 20));

        rSLabelImage8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rSLabelImage8.setImagen(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ajustesblanco.png"))); // NOI18N
        rSLabelImage8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSLabelIcon8.setForeground(new java.awt.Color(204, 204, 204));
        rSLabelIcon8.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.ADD_A_PHOTO);
        rSLabelImage8.add(rSLabelIcon8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 40, -1));

        panelP2.add(rSLabelImage8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 230, 100, 100));

        rSLabelImage9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rSLabelImage9.setImagen(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ajustesblanco.png"))); // NOI18N
        rSLabelImage9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSLabelIcon9.setForeground(new java.awt.Color(204, 204, 204));
        rSLabelIcon9.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.ADD_A_PHOTO);
        rSLabelImage9.add(rSLabelIcon9, new org.netbeans.lib.awtextra.AbsoluteConstraints(32, 30, 40, -1));

        panelP2.add(rSLabelImage9, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 230, 100, 100));

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        panelP2.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 310, 250, 90));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel21.setText("Medidas:");
        panelP2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, -1));

        jlabelnombre.setForeground(new java.awt.Color(255, 0, 0));
        jlabelnombre.setText("jLabel1");
        panelP2.add(jlabelnombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 240, 20));

        jlabelalto.setForeground(new java.awt.Color(255, 0, 0));
        jlabelalto.setText("jLabel1");
        panelP2.add(jlabelalto, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 250, 190, 20));

        jlabelma.setForeground(new java.awt.Color(255, 0, 0));
        jlabelma.setText("jLabel1");
        panelP2.add(jlabelma, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 486, 240, 20));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel17.setText("Nombre:");
        panelP2.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));

        jlabelcolor.setForeground(new java.awt.Color(255, 0, 0));
        jlabelcolor.setText("jLabel1");
        panelP2.add(jlabelcolor, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 486, 240, 20));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setText("*");
        panelP2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 120, 20, -1));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 51, 51));
        jLabel23.setText("*");
        panelP2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 430, 10, 10));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 51, 51));
        jLabel24.setText("*");
        panelP2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 10, 10));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 51, 51));
        jLabel25.setText("*");
        panelP2.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 190, 10, 10));

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 51, 51));
        jLabel26.setText("*");
        panelP2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 430, 10, 10));

        panelP.add(panelP2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 600));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelP, javax.swing.GroupLayout.PREFERRED_SIZE, 610, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelP, javax.swing.GroupLayout.PREFERRED_SIZE, 605, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNombre4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre4ActionPerformed

    private void txtmaterial1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtmaterial1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtmaterial1ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        agregarOcultadorDeErrores(txtNombre, jlabelnombre);

        boolean hayErrores = false;
        String nombreProducto = txtNombre.getText().trim();

        // Validar Nombre
        if (nombreProducto.isEmpty()) {
            jlabelnombre.setText("Este campo es obligatorio");
            jlabelnombre.setForeground(Color.RED);
            jlabelnombre.setVisible(true);
            hayErrores = true;
        }

        // Validar Color
        if (txtcolor.getText().trim().isEmpty()) {
            jlabelcolor.setText("Campo obligatorio");
            jlabelcolor.setForeground(Color.RED);
            jlabelcolor.setVisible(true);
            hayErrores = true;
        }

        // Validar Material
        if (txtmaterial1.getText().trim().isEmpty()) {
            jlabelma.setText("Campo obligatorio");
            jlabelma.setForeground(Color.RED);
            jlabelma.setVisible(true);
            hayErrores = true;
        }

        // Validar medidas
        if (!txtNombre13.getText().trim().isEmpty() && !esMedidaValida(txtNombre13.getText().trim())) {
            jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
            jlabelalto.setVisible(true);
            hayErrores = true;
        }

        if (!txtNombre3.getText().trim().isEmpty() && !esMedidaValida(txtNombre3.getText().trim())) {
            jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
            jlabelalto.setVisible(true);
            hayErrores = true;
        }

        if (!txtNombre1.getText().trim().isEmpty() && !esMedidaValida(txtNombre1.getText().trim())) {
            jlabelalto.setText("Formato inválido. Ej: '10.5 cm'");
            jlabelalto.setVisible(true);
            hayErrores = true;
        }

        // Validar mínimo dos imágenes
        if (imagenesBytes.size() < 2) {
            JOptionPane.showMessageDialog(this, "Se requieren al menos dos imágenes para guardar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            hayErrores = true;
        }

        // Si hay errores, no continúes
        if (hayErrores) {
            return;
        }

        // Verificar si el producto ya existe
        Ctrl_productocatalogo ctrlProducto = new Ctrl_productocatalogo();
        if (ctrlProducto.existeProducto(nombreProducto, idCategoria)) {
            JOptionPane.showMessageDialog(this,
                    "Ya existe un producto con este nombre en la categoría seleccionada.",
                    "Producto duplicado",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Si todo está bien, crear el producto y guardar
        String alto = txtNombre13.getText().trim();
        String ancho = txtNombre3.getText().trim();
        String profundidad = txtNombre1.getText().trim();

        catalogoproducto producto = new catalogoproducto();
        producto.setNombre(txtNombre.getText().trim());
        producto.setAlto(alto);
        producto.setAncho(ancho);
        producto.setProfundidad(profundidad);
        producto.setMaterial(txtmaterial1.getText().trim());
        producto.setColor(txtcolor.getText().trim());
        producto.setDescripcion(jTextArea1.getText().trim());
        producto.setImagen(imagenesBytes.size() > 0 ? imagenesBytes.get(0) : null);
        producto.setImagen2(imagenesBytes.size() > 1 ? imagenesBytes.get(1) : null);
        producto.setImagen3(imagenesBytes.size() > 2 ? imagenesBytes.get(2) : null);
        producto.setImagen4(imagenesBytes.size() > 3 ? imagenesBytes.get(3) : null);
        producto.setIdCategoria(idCategoria);

        if (ctrlProducto.guardar(producto)) {
            JOptionPane.showMessageDialog(this, "Producto guardado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            if (getParent() instanceof Productos) {
                ((Productos) getParent()).cargarProductosDesdeBD();
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Error al guardar el producto. Verifique los campos y las imágenes.", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al guardar producto con idCategoria: " + idCategoria);

        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCancelar1ActionPerformed

    private void txtNombre9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre9ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre9ActionPerformed

    private void txtNombre8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre8ActionPerformed

    private void txtcolor1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcolor1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtcolor1ActionPerformed

    private void txtNombre6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre6ActionPerformed

    private void txtNombre2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombre2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombre2ActionPerformed

    private void btnGuardar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnGuardar2ActionPerformed

    /**
     * @param args the command line arguments
     */
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(nuevoProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            int idCategoria = 65; // Valor de prueba
            String nombreCategoria = "Categoría de Prueba";
            nuevoProducto dialog = new nuevoProducto(new javax.swing.JFrame(), true, idCategoria, nombreCategoria);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple btnCancelar1;
    private rojeru_san.RSButtonRiple btnGuardar;
    private rojeru_san.RSButtonRiple btnGuardar2;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel jlabelalto;
    private javax.swing.JLabel jlabelcolor;
    private javax.swing.JLabel jlabelma;
    private javax.swing.JLabel jlabelnombre;
    private javax.swing.JPanel panelP;
    private javax.swing.JPanel panelP2;
    private rojerusan.RSLabelIcon rSLabelIcon6;
    private rojerusan.RSLabelIcon rSLabelIcon7;
    private rojerusan.RSLabelIcon rSLabelIcon8;
    private rojerusan.RSLabelIcon rSLabelIcon9;
    private rojeru_san.rspanel.RSPanelImage rSLabelImage3;
    private javax.swing.JLabel rSLabelImage4;
    private javax.swing.JLabel rSLabelImage5;
    private rojeru_san.rspanel.RSPanelImage rSLabelImage6;
    private javax.swing.JLabel rSLabelImage7;
    private rojeru_san.rspanel.RSPanelImage rSLabelImage8;
    private rojeru_san.rspanel.RSPanelImage rSLabelImage9;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre1;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre13;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre3;
    private RSMaterialComponent.RSTextFieldMaterial txtNombre4;
    private RSMaterialComponent.RSTextFieldMaterial txtcolor;
    private RSMaterialComponent.RSTextFieldMaterial txtmaterial1;
    // End of variables declaration//GEN-END:variables

    private void agregarOcultadorDeErrores(JTextField campo, JLabel errorLabel) {
        campo.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) {
                errorLabel.setVisible(false);
            }

            public void removeUpdate(DocumentEvent e) {
                errorLabel.setVisible(false);
            }

            public void changedUpdate(DocumentEvent e) {
                errorLabel.setVisible(false);
            }
        });
    }

}
