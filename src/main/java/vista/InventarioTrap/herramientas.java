/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vista.InventarioTrap;

import vista.Inventario0.*;
import RSMaterialComponent.RSButtonShape;
import controlador.Ctrl_CategoriaHerramienta;
import controlador.Ctrl_InventarioHerramienta;
import controlador.Ctrl_MarcaHerramienta;
import controlador.Ctrl_UnidadHerramienta;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingConstants;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicScrollBarUI;
import modelo.Categoria;
import modelo.HerramientaDatos;
import modelo.Marca;
import modelo.Unidad;
import rojerusan.RSLabelImage;
import vista.TemaManager;

/**
 *
 * @author ZenBook
 */
public class herramientas extends javax.swing.JPanel {

    private Ctrl_InventarioHerramienta ctrlInventario;
    private JPanel contenedorPrincipal;

    private JPopupMenu popupFiltros;
    private List<JCheckBox> checkCategorias = new ArrayList<>();
    private List<JCheckBox> checkMarcas = new ArrayList<>();
    private List<JCheckBox> checkUnidades = new ArrayList<>();
    private JRadioButton radioStockBajo, radioStockMedio, radioStockAlto, radioStockTodos;

    /**
     * Creates new form herramientas
     */
    public herramientas() {
        initComponents();
        contenedorPrincipal = new JPanel();

        // Inicializar componentes
        inicializarPopupFiltros();

        ctrlInventario = new Ctrl_InventarioHerramienta();

        aplicarTema();
        TemaManager.getInstance().addThemeChangeListener(this::aplicarTema);

                // Panel contenedor para principalPanel (para agregar márgenes)
        contenedorPrincipal = new JPanel(new BorderLayout());
        contenedorPrincipal.setBackground(TemaManager.getInstance().isOscuro() ? new Color(37, 37, 52) : new Color(242, 243, 245));

        principalPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 10, 10, 10); // Márgenes
        gbc.fill = GridBagConstraints.NONE; // No estirar componentes

        principalPanel.setBackground(TemaManager.getInstance().isOscuro()
                ? new Color(37, 37, 52) // Fondo oscuro
                : new Color(242, 243, 245)); // Fondo claro

        // Agregar márgenes al contenedor (aumentado a 40px en todos los lados)
        contenedorPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contenedorPrincipal.add(principalPanel, BorderLayout.CENTER);

// JScrollPane -----------------------------
        // Configurar el JScrollPane con el contenedor (en lugar de principalPanel directamente)
        JScrollPane scrollPane = new JScrollPane(contenedorPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(8, 0));
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(153, 153, 153);
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return new JButton() {
                    @Override
                    public Dimension getPreferredSize() {
                        return new Dimension(0, 0);
                    }
                };
            }

            @Override
            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(thumbColor);
                g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 10, 10);
                g2.dispose();
            }
        });

        // Reemplazar en panelprincipal
        panelprincipal.remove(principalPanel);
        panelprincipal.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 1150, 560));
// JScrollPane ----------------------------- 

        // Cargar materiales existentes al iniciar
        cargarMateriales();

        // Agregar MouseListener al panelprincipal para cerrar el popup al hacer clic fuera
        panelprincipal.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (popupFiltros.isShowing()) {
                    popupFiltros.setVisible(false);
                }
            }
        });

        // Agregar DocumentListener para búsqueda en tiempo real
        txtBuscar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarMateriales();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarMateriales();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarMateriales();
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

    // Método para cargar los materiales desde la base de datos
    private void cargarMateriales() {
        principalPanel.removeAll();
        // Cambiar a FlowLayout alineado a la izquierda
        principalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        List<Ctrl_InventarioHerramienta.MaterialConDetalles> materiales = ctrlInventario.obtenerMateriales();
        for (Ctrl_InventarioHerramienta.MaterialConDetalles materialConDetalles : materiales) {
            agregarMaterial(
                    materialConDetalles.getMaterial(),
                    materialConDetalles.getNombreCategoria(),
                    materialConDetalles.getNombreMarca(),
                    materialConDetalles.getNombreUnidadMedida()
            );
        }
        principalPanel.revalidate();
        principalPanel.repaint();
        if (principalPanel.getParent() instanceof JViewport) {
            ((JViewport) principalPanel.getParent()).setViewPosition(new Point(0, 0));
        }
    }

    // Método para agregar una nueva tarjeta
    public void agregarMaterial(HerramientaDatos material, String nombreCategoria, String nombreMarca, String nombreUnidadMedida) {

        JPanel tarjeta = new JPanel(new BorderLayout());
        tarjeta.setPreferredSize(new Dimension(210, 300)); // Ancho: 200, Alto: 300
        tarjeta.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        // JPanel para la imagen
        JPanel panelImagen = new JPanel(new GridBagLayout());
        panelImagen.setPreferredSize(new Dimension(210, 197));
        panelImagen.setBackground(Color.WHITE);

        JLabel lblImagen = new JLabel();
        if (material.getImagen() != null) {
            ImageIcon icon = new ImageIcon(material.getImagen());
            // Redimensionar la imagen al tamaño del panelImagen (210x197)
            Image img = icon.getImage().getScaledInstance(210, 197, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
        } else {
            lblImagen.setText("No imagen");
        }
        panelImagen.add(lblImagen);
        tarjeta.add(panelImagen, BorderLayout.NORTH);

        // JPanel para la información
        JPanel panelInfo = new JPanel(new GridLayout(3, 1));
        panelInfo.setBackground(new Color(46, 49, 82)); // Color de fondo azul oscuro
        Font fuenteInfo = new Font("Segoe UI", Font.PLAIN, 15); // Fuente Arial, negrita, tamaño 16

        JLabel lblNombre = new JLabel(material.getNombre());
        lblNombre.setForeground(Color.WHITE);
        lblNombre.setFont(fuenteInfo); // Establece la fuente
        lblNombre.setHorizontalAlignment(SwingConstants.CENTER);
        panelInfo.add(lblNombre);

        JLabel lblCategoria = new JLabel("Categoría: " + nombreCategoria);
        lblCategoria.setForeground(Color.WHITE);
        lblCategoria.setFont(fuenteInfo); // Establece la fuente
        lblCategoria.setHorizontalAlignment(SwingConstants.CENTER);
        panelInfo.add(lblCategoria);

        JLabel lblEstado = new JLabel("Estado: " + material.getEstado());
        lblEstado.setFont(fuenteInfo); // Establece la fuente
        lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
        // Aplicar color según el estado
        switch (material.getEstado().toLowerCase()) {
            case "disponible":
                lblEstado.setForeground(new Color(82, 196, 88)); // Verde
                break;
            case "reparación":
                lblEstado.setForeground(new Color(255, 165, 0)); // Naranja
                break;
            case "dañado":
                lblEstado.setForeground(Color.RED); // Rojo
                break;
            default:
                lblEstado.setForeground(Color.WHITE); // Blanco por defecto
        }
        panelInfo.add(lblEstado);

        tarjeta.add(panelInfo, BorderLayout.CENTER);

        // Almacenar el objeto MaterialDatos y los nombres en la tarjeta
        tarjeta.putClientProperty("material", material);
        tarjeta.putClientProperty("nombreCategoria", nombreCategoria);
        tarjeta.putClientProperty("nombreMarca", nombreMarca);
        tarjeta.putClientProperty("nombreUnidadMedida", nombreUnidadMedida);

        // JPanel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.setBackground(new Color(46, 49, 82));

        RSButtonShape verBtn = new RSButtonShape(); // Reemplaza "ver.png" con la ruta de tu imagen
        verBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view (1).png")));
        verBtn.setPreferredSize(new Dimension(33, 25)); // Ancho: 30, Alto: 30
        verBtn.setBackground(new Color(216, 246, 221)); // Establece el fondo en rojo
        verBtn.setBackgroundHover(new java.awt.Color(188, 225, 193));
        verBtn.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        verBtn.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        verBtn.setToolTipText("<html><b>Ver detalles de la herramienta</html>");
        //accion del boton de ver
        verBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                herramientaInfo dialog = new herramientaInfo(new javax.swing.JFrame(), true);
                dialog.mostrarMaterial(material); // Muestra la información del material
                dialog.setLocationRelativeTo(null); // Centra el JDialog
                dialog.setVisible(true); // Muestra el JDialog
            }
        });


        panelBotones.add(verBtn);
        
        tarjeta.add(panelBotones, BorderLayout.SOUTH);

        principalPanel.add(tarjeta);
        principalPanel.revalidate();
        principalPanel.repaint();
        contenedorPrincipal.revalidate(); // Revalidar el contenedor principal también
        contenedorPrincipal.repaint();   // Repintar el contenedor principal también

        // Calcular la altura dinámicamente según 5 tarjetas por fila
        int totalTarjetas = principalPanel.getComponentCount();
        int filas = (int) Math.ceil((double) totalTarjetas / 5); // Número de filas
        int tarjetaHeight = 310; // Altura de la tarjeta + margen
        int totalHeight = filas * tarjetaHeight;
        // Ajustar el ancho y alto considerando el margen (1150 - 80px de márgenes laterales, 560 - 80px de márgenes verticales)
        principalPanel.setPreferredSize(new Dimension(1150 - 80, Math.max(totalHeight, 560 - 80))); // Mínimo ajustado
    }

//metodo para actualizar tarjeta
    public void actualizarTarjeta(JPanel tarjeta, HerramientaDatos material, String nombreCategoria, String nombreMarca, String nombreUnidadMedida) {
        // Obtener el panel de información (panelInfo) de la tarjeta
        JPanel panelInfo = (JPanel) tarjeta.getComponent(1); // El segundo componente es panelInfo

        // Actualizar las etiquetas dentro de panelInfo
        JLabel lblNombre = (JLabel) panelInfo.getComponent(0);
        lblNombre.setText(material.getNombre());

        JLabel lblCategoria = (JLabel) panelInfo.getComponent(1);
        lblCategoria.setText("Categoría: " + nombreCategoria);

        JLabel lblEstado = (JLabel) panelInfo.getComponent(2);
        lblEstado.setText("Estado: " + material.getEstado());
        // Aplicar color según el estado
        if (material.getEstado() != null) {
            String estado = material.getEstado().toLowerCase();
            if (estado.contains("disponible")) {
                lblEstado.setForeground(new Color(50, 200, 50));
            } else if (estado.contains("reparación") || estado.contains("reparacion")) {
                lblEstado.setForeground(new Color(255, 140, 0));
            } else if (estado.contains("dañado") || estado.contains("danado")) {
                lblEstado.setForeground(new Color(255, 50, 50));
            } else {
                lblEstado.setForeground(Color.WHITE);
            }
        } else {
            lblEstado.setForeground(Color.WHITE);
        }

        // Actualizar la imagen si es necesario
        JPanel panelImagen = (JPanel) tarjeta.getComponent(0); // El primer componente es panelImagen
        JLabel lblImagen = (JLabel) panelImagen.getComponent(0);
        if (material.getImagen() != null) {
            ImageIcon icon = new ImageIcon(material.getImagen());
            // Redimensionar la imagen al tamaño del panelImagen (210x197)
            Image img = icon.getImage().getScaledInstance(210, 197, Image.SCALE_SMOOTH);
            lblImagen.setIcon(new ImageIcon(img));
            lblImagen.setText("");
        } else {
            lblImagen.setIcon(null);
            lblImagen.setText("No imagen");
        }

        // Actualizar los nombres almacenados en la tarjeta
        tarjeta.putClientProperty("material", material);
        tarjeta.putClientProperty("nombreCategoria", nombreCategoria);
        tarjeta.putClientProperty("nombreMarca", nombreMarca);
        tarjeta.putClientProperty("nombreUnidadMedida", nombreUnidadMedida);

        tarjeta.revalidate();
        tarjeta.repaint();
    }

    // Método auxiliar para obtener los nombres de categoría, marca y unidad de medida
    private String[] obtenerDetalles(int idCategoria, int idMarca, int idUnidadMedida) {
        String nombreCategoria = "Sin categoría";
        String nombreMarca = "Sin marca";
        String nombreUnidadMedida = "Sin unidad de medida";

        // Obtener nombre de la categoría
        Ctrl_CategoriaHerramienta ctrlCategoria = new Ctrl_CategoriaHerramienta();
        List<Categoria> categorias = ctrlCategoria.obtenerCategoriasHerramienta();
        for (Categoria cat : categorias) {
            if (cat.getCodigo() == idCategoria) {
                nombreCategoria = cat.getNombre();
                break;
            }
        }

        // Obtener nombre de la marca
        Ctrl_MarcaHerramienta ctrlMarca = new Ctrl_MarcaHerramienta();
        List<Marca> marcas = ctrlMarca.obtenerMarcasHerramienta();
        for (Marca m : marcas) {
            if (m.getCodigo() == idMarca) {
                nombreMarca = m.getNombre();
                break;
            }
        }

        // Obtener nombre de la unidad de medida
        Ctrl_UnidadHerramienta ctrlUnidad = new Ctrl_UnidadHerramienta();
        List<Unidad> unidades = ctrlUnidad.obtenerUnidadesHerramienta();
        for (Unidad um : unidades) {
            if (um.getCodigo() == idUnidadMedida) {
                nombreUnidadMedida = um.getNombre();
                break;
            }
        }

        return new String[]{nombreCategoria, nombreMarca, nombreUnidadMedida};
    }

    public void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            Color fondo = new Color(21, 21, 33);
            Color primario = new Color(40, 60, 150);
            Color texto = Color.WHITE;

            panelprincipal.setBackground(fondo);
            principalPanel.setBackground(new Color(37, 37, 52));
            contenedorPrincipal.setBackground(new Color(37, 37, 52));
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.LIGHT_GRAY);


            filtar.setIcon(new ImageIcon(getClass().getResource("/filtrar (2).png")));

        } else {
            Color fondo = new Color(242, 247, 255);
            Color texto = Color.BLACK;
            Color primario = new Color(72, 92, 188);

            panelprincipal.setBackground(fondo);
            principalPanel.setBackground(new Color(242, 243, 245));
            contenedorPrincipal.setBackground(new Color(242, 243, 245));
            txtBuscar.setBackground(fondo);
            txtBuscar.setForeground(texto);
            txtBuscar.setColorIcon(texto);
            txtBuscar.setPhColor(Color.GRAY);


            filtar.setIcon(new ImageIcon(getClass().getResource("/filtrar (1).png")));

        }
    }

    private void inicializarPopupFiltros() {
// Limpiar las listas de checkboxes
        checkCategorias.clear();
        checkMarcas.clear();
        checkUnidades.clear();

        // Crear un nuevo JPopupMenu
        popupFiltros = new JPopupMenu();
        JPanel panelFiltros = new JPanel();
        panelFiltros.setLayout(new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));
        panelFiltros.setBorder(BorderFactory.createEmptyBorder(7, 7, 5, 7));

        // Definir fuentes
        Font fuenteTexto = new Font("Segoe UI", Font.PLAIN, 12); // Fuente para checkboxes y radio buttons
        Font fuenteTitulo = new Font("Segoe UI", Font.BOLD, 13); // Fuente para títulos
        Font fuenteBoton = new Font("Segoe UI", Font.BOLD, 14); // Fuente para el botón Aplicar

        // Agregar panel para el botón de cerrar (X)
        JPanel panelCerrar = new JPanel(new BorderLayout());
        panelCerrar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

        RSLabelImage lblCerrar = new RSLabelImage();
        URL imageUrl = getClass().getResource("/x (8).png");
        ImageIcon icono = (imageUrl != null) ? new ImageIcon(imageUrl) : new ImageIcon("default_bell.png");
        lblCerrar.setIcon(icono);
        lblCerrar.setPreferredSize(new Dimension(16, 16));

        lblCerrar.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
        lblCerrar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        lblCerrar.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                popupFiltros.setVisible(false);
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblCerrar.setForeground(new Color(255, 50, 50)); // Rojo al pasar el ratón
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblCerrar.setForeground(TemaManager.getInstance().isOscuro() ? Color.WHITE : Color.BLACK);
            }
        });
        panelCerrar.add(lblCerrar, BorderLayout.EAST);

        // 1. Panel Stock (sin cambios)
        JPanel panelStock = new JPanel(new GridLayout(0, 2, 10, 5)); // 2 columnas
        TitledBorder borde = BorderFactory.createTitledBorder("Estado");
        borde.setTitleFont(fuenteTitulo); // Cambia aquí la fuente
        panelStock.setBorder(borde);
        panelStock.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        ButtonGroup grupoStock = new ButtonGroup();
        radioStockTodos = new JRadioButton("Todos", true);
        radioStockBajo = new JRadioButton("Disponible");
        radioStockMedio = new JRadioButton("Reparación");
        radioStockAlto = new JRadioButton("Dañado");

        // Aplicar fuente a los radio buttons
        radioStockTodos.setFont(fuenteTexto);
        radioStockBajo.setFont(fuenteTexto);
        radioStockMedio.setFont(fuenteTexto);
        radioStockAlto.setFont(fuenteTexto);

        grupoStock.add(radioStockTodos);
        grupoStock.add(radioStockBajo);
        grupoStock.add(radioStockMedio);
        grupoStock.add(radioStockAlto);

        panelStock.add(radioStockTodos);
        panelStock.add(radioStockBajo);
        panelStock.add(radioStockMedio);
        panelStock.add(radioStockAlto);

        // 2. Panel de Categorías
        Ctrl_CategoriaHerramienta ctrlCat = new Ctrl_CategoriaHerramienta();
        List<Categoria> categorias = ctrlCat.obtenerCategoriasHerramienta();
        JPanel panelCategorias = crearPanelFiltroDobleColumna("Categorías", checkCategorias, categorias, fuenteTexto, fuenteTitulo);

        // 3. Panel de Marcas
        Ctrl_MarcaHerramienta ctrlMarca = new Ctrl_MarcaHerramienta();
        List<Marca> marcas = ctrlMarca.obtenerMarcasHerramienta();
        JPanel panelMarcas = crearPanelFiltroDobleColumna("Marcas", checkMarcas, marcas, fuenteTexto, fuenteTitulo);

        // 4. Panel de Unidades
        Ctrl_UnidadHerramienta ctrlUnidad = new Ctrl_UnidadHerramienta();
        List<Unidad> unidades = ctrlUnidad.obtenerUnidadesHerramienta();
        JPanel panelUnidades = crearPanelFiltroDobleColumna("Unidades de Medida", checkUnidades, unidades, fuenteTexto, fuenteTitulo);

        // Panel para los botones Aplicar y Limpiar
        JPanel panelBotones = new JPanel(new BorderLayout()); // Cambiamos a BorderLayout
        panelBotones.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Panel central que contendrá ambos botones
        JPanel panelCentral = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // 20px de espacio entre botones
        panelCentral.setOpaque(false);

        // Botón Limpiar
        RSButtonShape btnLimpiar = new RSButtonShape();
        btnLimpiar.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnLimpiar.setText("Limpiar");
        btnLimpiar.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnLimpiar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnLimpiar.setFont(fuenteBoton);
        btnLimpiar.setPreferredSize(new Dimension(100, 25)); // Tamaño fijo
        btnLimpiar.setBackground(new java.awt.Color(46, 49, 82));
        btnLimpiar.setBackgroundHover(new java.awt.Color(67, 150, 209));

        btnLimpiar.addActionListener(e -> {
            // Desmarcar todos los checkboxes
            checkCategorias.forEach(check -> check.setSelected(false));
            checkMarcas.forEach(check -> check.setSelected(false));
            checkUnidades.forEach(check -> check.setSelected(false));
            // Seleccionar el radio button "Todos"
            radioStockTodos.setSelected(true);
            // Opcional: Actualizar la vista de materiales para mostrar todos
            cargarMateriales();
            popupFiltros.setVisible(false);
        });

        // Botón Aplicar
        RSButtonShape btnAplicar = new RSButtonShape();
        btnAplicar.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnAplicar.setText("Aplicar");
        btnAplicar.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnAplicar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnAplicar.setFont(fuenteBoton);
        btnAplicar.setPreferredSize(new Dimension(100, 25)); // Tamaño fijo
        btnAplicar.setBackground(new java.awt.Color(46, 49, 82));
        btnAplicar.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnAplicar.addActionListener(e -> aplicarFiltros());

        // Agregar botones al panel central
        panelCentral.add(btnLimpiar);
        panelCentral.add(btnAplicar);

        // Agregar panel central al panel principal de botones
        panelBotones.add(panelCentral, BorderLayout.CENTER);

        // Agregar todos los paneles al popup
        panelFiltros.add(panelCerrar); // Agregar el panel con el botón de cerrar
        panelFiltros.add(panelStock);
        panelFiltros.add(panelCategorias);
        panelFiltros.add(panelMarcas);
        panelFiltros.add(panelUnidades);
        panelFiltros.add(panelBotones);

        // Configurar tamaño preferido del popup
        panelFiltros.setPreferredSize(new Dimension(330, 550));
        popupFiltros.add(panelFiltros);
    }

    private void aplicarFiltros() {
        principalPanel.removeAll();

        // Obtener selecciones
        List<String> catsSeleccionadas = checkCategorias.stream()
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());

        List<String> marcasSeleccionadas = checkMarcas.stream()
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());

        List<String> unidadesSeleccionadas = checkUnidades.stream()
                .filter(JCheckBox::isSelected)
                .map(JCheckBox::getText)
                .collect(Collectors.toList());

        // Obtener todos los materiales
        List<Ctrl_InventarioHerramienta.MaterialConDetalles> materiales = ctrlInventario.obtenerMateriales();

        for (Ctrl_InventarioHerramienta.MaterialConDetalles material : materiales) {
            // Filtrar por categoría/marca/unidad
            boolean cumpleFiltros
                    = (catsSeleccionadas.isEmpty() || catsSeleccionadas.contains(material.getNombreCategoria()))
                    && (marcasSeleccionadas.isEmpty() || marcasSeleccionadas.contains(material.getNombreMarca()))
                    && (unidadesSeleccionadas.isEmpty() || unidadesSeleccionadas.contains(material.getNombreUnidadMedida()));

            // Filtrar por estado (Disponible/Reparación/Dañado)
            if (cumpleFiltros && !radioStockTodos.isSelected()) {
                String estado = material.getMaterial().getEstado();

                if (radioStockBajo.isSelected() && !"Disponible".equalsIgnoreCase(estado)) {
                    cumpleFiltros = false;
                } else if (radioStockMedio.isSelected() && !"Reparación".equalsIgnoreCase(estado)) {
                    cumpleFiltros = false;
                } else if (radioStockAlto.isSelected() && !"Dañado".equalsIgnoreCase(estado)) {
                    cumpleFiltros = false;
                }
            }

            if (cumpleFiltros) {
                agregarMaterial(
                        material.getMaterial(),
                        material.getNombreCategoria(),
                        material.getNombreMarca(),
                        material.getNombreUnidadMedida()
                );
            }
        }

        principalPanel.revalidate();
        principalPanel.repaint();
        popupFiltros.setVisible(false);

        if (principalPanel.getComponentCount() == 0) {
            JLabel lblNoResultados = new JLabel("No se encontraron materiales que coincidan con la busqueda");
            lblNoResultados.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            principalPanel.add(lblNoResultados);
        }
    }

    private JPanel crearPanelFiltroDobleColumna(String titulo, List<JCheckBox> checkboxes, List<? extends Object> items, Font fuenteTexto, Font fuenteTitulo) {
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.setBorder(BorderFactory.createTitledBorder(null, titulo, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, fuenteTitulo));

        // Panel para contener las dos columnas
        JPanel panelColumnas = new JPanel(new GridLayout(0, 2, 10, 2)); // 2 columnas, espacio entre ellas

        // Límite de 5 filas por columna (10 items máximo sin scroll)
        int mitad = (int) Math.ceil(items.size() / 2.0);

        // Primera columna
        JPanel columna1 = new JPanel(new GridLayout(0, 1, 0, 0));
        columna1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5)); // Margen derecho
        for (int i = 0; i < mitad; i++) {
            JCheckBox check = new JCheckBox(getNombreItem(items.get(i)));
            check.setFont(fuenteTexto); // Aplicar fuente

            check.setMargin(new Insets(1, 1, 1, 1)); // ← esta línea
            checkboxes.add(check);
            columna1.add(check);
        }

        // Segunda columna
        JPanel columna2 = new JPanel(new GridLayout(0, 1, 0, 0));
        for (int i = mitad; i < items.size(); i++) {
            JCheckBox check = new JCheckBox(getNombreItem(items.get(i)));
            check.setFont(fuenteTexto); // Aplicar fuente

            check.setMargin(new Insets(1, 1, 1, 1)); // ← esta línea
            checkboxes.add(check);
            columna2.add(check);
        }

        panelColumnas.add(columna1);
        panelColumnas.add(columna2);

        // ScrollPane
        JScrollPane scrollPane = new JScrollPane(panelColumnas);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);

        // Configurar altura máxima (5 filas = ~150px)
        int filas = (int) Math.ceil(items.size() / 2.0);

        int altura = Math.min(150, filas * 25); // Máx 150px, ~25px por fila
        panelColumnas.setPreferredSize(new Dimension(250, altura));

        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        return panelPrincipal;
    }

    private String getNombreItem(Object item) {
        if (item instanceof Categoria) {
            return ((Categoria) item).getNombre();
        } else if (item instanceof Marca) {
            return ((Marca) item).getNombre();
        } else if (item instanceof Unidad) {
            return ((Unidad) item).getNombre();
        }
        return item.toString();
    }

    private void buscarMateriales() {
        String textoBusqueda = txtBuscar.getText().trim().toLowerCase();

        if (textoBusqueda.isEmpty()) {
            cargarMateriales(); // Si está vacío, mostrar todos
            return;
        }

        principalPanel.removeAll();
        principalPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        List<Ctrl_InventarioHerramienta.MaterialConDetalles> materiales = ctrlInventario.obtenerMateriales();

        // Normalizar el texto de búsqueda para ignorar tildes
        textoBusqueda = textoBusqueda != null ? Normalizer.normalize(textoBusqueda, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "") : "";

        for (Ctrl_InventarioHerramienta.MaterialConDetalles materialConDetalles : materiales) {
            HerramientaDatos material = materialConDetalles.getMaterial();
            String nombreCategoria = materialConDetalles.getNombreCategoria();
            String nombreMarca = materialConDetalles.getNombreMarca();
            String nombreUnidadMedida = materialConDetalles.getNombreUnidadMedida();
            String estado = material.getEstado();

            // Normalizar los campos para ignorar tildes
            String nombreNormalizado = material.getNombre() != null ? Normalizer.normalize(material.getNombre(), Normalizer.Form.NFKD)
                    .replaceAll("\\p{M}", "").toLowerCase() : "";
            String categoriaNormalizada = nombreCategoria != null ? Normalizer.normalize(nombreCategoria, Normalizer.Form.NFKD)
                    .replaceAll("\\p{M}", "").toLowerCase() : "";
            String marcaNormalizada = nombreMarca != null ? Normalizer.normalize(nombreMarca, Normalizer.Form.NFKD)
                    .replaceAll("\\p{M}", "").toLowerCase() : "";
            String unidadNormalizada = nombreUnidadMedida != null ? Normalizer.normalize(nombreUnidadMedida, Normalizer.Form.NFKD)
                    .replaceAll("\\p{M}", "").toLowerCase() : "";
                   String estadoNormalizado = estado != null ? Normalizer.normalize(estado, Normalizer.Form.NFKD)
                .replaceAll("\\p{M}", "").toLowerCase() : "";

            // Normalizar valores numéricos como cadenas
            String cantidadStr = material.getCantidad() != null ? material.getCantidad().toLowerCase() : "";
            String precioUnitarioStr = String.valueOf(material.getPrecioUnitario()).toLowerCase();

            // Buscar en varios campos
            if (nombreNormalizado.contains(textoBusqueda)
                    || categoriaNormalizada.contains(textoBusqueda)
                    || marcaNormalizada.contains(textoBusqueda)
                    || nombreUnidadMedida.toLowerCase().contains(textoBusqueda)
                                    || estadoNormalizado.contains(textoBusqueda)
                    || String.valueOf(material.getIdInventario()).contains(textoBusqueda)
                    || cantidadStr.contains(textoBusqueda)
                    || precioUnitarioStr.contains(textoBusqueda)) {

                agregarMaterial(material, nombreCategoria, nombreMarca, nombreUnidadMedida);
            }
        }

        principalPanel.revalidate();
        principalPanel.repaint();

        if (principalPanel.getComponentCount() == 0) {
            JLabel lblNoResultados = new JLabel("No se encontraron materiales que coincidan con: " + textoBusqueda);
            lblNoResultados.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            principalPanel.add(lblNoResultados);
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

        panelprincipal = new javax.swing.JPanel();
        principalPanel = new javax.swing.JPanel();
        txtBuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        filtar = new rojerusan.RSLabelImage();

        setPreferredSize(new java.awt.Dimension(1240, 580));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelprincipal.setBackground(new java.awt.Color(241, 245, 253));
        panelprincipal.setPreferredSize(new java.awt.Dimension(1240, 580));
        panelprincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        principalPanel.setBackground(new java.awt.Color(30, 30, 45));
        principalPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panelprincipal.add(principalPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 1210, 570));

        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorIcon(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorMaterial(new java.awt.Color(153, 153, 153));
        txtBuscar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
        txtBuscar.setPhColor(new java.awt.Color(102, 102, 102));
        txtBuscar.setPlaceholder("Buscar...");
        txtBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                txtBuscarMouseReleased(evt);
            }
        });
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        panelprincipal.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, 390, 40));

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
        panelprincipal.add(filtar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 80, 35, 35));

        add(panelprincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1290, 730));
    }// </editor-fold>//GEN-END:initComponents

    private void txtBuscarMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarMouseReleased
        buscarMateriales();
    }//GEN-LAST:event_txtBuscarMouseReleased

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        buscarMateriales();
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void filtarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseClicked
        popupFiltros.show(filtar, 0, filtar.getHeight());
    }//GEN-LAST:event_filtarMouseClicked

    private void filtarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mano al pasar
    }//GEN-LAST:event_filtarMouseEntered

    private void filtarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filtarMouseExited
        setCursor(Cursor.getDefaultCursor()); // Cursor normal al salir
    }//GEN-LAST:event_filtarMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSLabelImage filtar;
    private javax.swing.JPanel panelprincipal;
    private javax.swing.JPanel principalPanel;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtBuscar;
    // End of variables declaration//GEN-END:variables
}
