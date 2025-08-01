/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.Ctrl_InventarioMaterial;
import controlador.Ctrl_Perfil;
import controlador.Ctrl_productocatalogo;
import java.awt.BasicStroke;
import vista.Caja.Caja;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicScrollBarUI;
import modelo.MaterialDatos;
import modelo.UsuarioModelo;
import rojeru_san.RSButton;
import rojerusan.RSLabelImage;

import vista.Cotizacion.cotizacion;

import vista.InventarioTrap.herramientas;
import vista.InventarioTrap.materiales;
import vista.ProduccionTrap.Produccion;
import vista.catalogo.catalogo22;

/**
 *
 * @author Personal
 */
public class PrincipalTrap extends javax.swing.JFrame {

    private JScrollPane scrollPane;
    private int idCategoria; // Guardar el ID de la categoría
    private String nombreCategoria; // Guardar el nombre de la categoría
    private Ctrl_productocatalogo controladorProducto; // Controlador para productos
    private JPanel parentPanel;
    private JPanel submenuInventario;
    private boolean submenuVisible = false;
// Botón "Materiales" del submenú
    // Botón "Herramientas" del submenú
    // Para controlar si el submenú está visible
    private final int idUsuario;
    private final Ctrl_Perfil controlador;

    // Nuevas variables para el submenú de Ventas
    private JPanel submenuVentas;
    private boolean submenuVentasVisible = false; // Para controlar si el submenú de Ventas está visible

    private JPanel submenuAjustes;
    private boolean submenuAjustesVisible = false;

    private JPanel centerPanel;
    private JPopupMenu notificacionPopupMenu; // Declarar como variable de clase
    private boolean popupVisible = false; // Estado del popup
    private Timer updateTimer;
    private rojerusan.RSLabelImage circulo;
    private JLabel textoCirculo;
    private perfil1 perfilPanel;
    private rojeru_san.RSButton item1;
    private rojeru_san.RSButton item2;
    private rojeru_san.RSButton item4;
    private rojeru_san.RSButton item3;
    private rojeru_san.RSButton ajusteButton;
    private boolean menuExpanded = true; // Inicialmente expandido
    private final int MENU_COLLAPSED_WIDTH = 1;
    private final int MENU_EXPANDED_WIDTH = 250;

    public PrincipalTrap(int idUsuario) {
        centerPanel = new JPanel(); // Inicialización faltante

        this.idUsuario = idUsuario;
        this.controlador = new Ctrl_Perfil();

        this.item1 = new rojeru_san.RSButton(); // ✔️ usa la variable de instancia
        this.item2 = new rojeru_san.RSButton(); // ✔️
        this.item3 = new rojeru_san.RSButton();
        this.item4 = new rojeru_san.RSButton();
        this.ajusteButton = new rojeru_san.RSButton();

        boolean oscuro = TemaManager.getInstance().isOscuro();

        initComponents();

        //circulo rojo para notificaciones
        circulo = new RSLabelImage();
        URL imageUrl = getClass().getResource("/circulo.png");
        ImageIcon icono = (imageUrl != null) ? new ImageIcon(imageUrl) : new ImageIcon("default_bell.png");
        circulo.setIcon(icono);
        circulo.setPreferredSize(new Dimension(23, 22));
        circulo.setBounds(1182, 11, 23, 22); // Mismo tamaño o ligeramente mayor

        textoCirculo = new JLabel();
        textoCirculo.setForeground(Color.WHITE); // Texto blanco para contraste
        textoCirculo.setFont(new Font("Arial", Font.BOLD, 12)); // Fuente pequeña y negrita
        textoCirculo.setHorizontalAlignment(JLabel.CENTER); // Centrar texto
        textoCirculo.setVerticalAlignment(JLabel.CENTER); // Centrar texto
        textoCirculo.setPreferredSize(new Dimension(20, 20));
        textoCirculo.setBounds(1182, 12, 20, 20); // Mismo tamaño o ligeramente mayor

        jPanel2.add(circulo);  // Primero agrega el círculo
        jPanel2.add(textoCirculo);

        // Importante: colocar el JLabel DESPUÉS del botón para que quede al frente
        jPanel2.setComponentZOrder(textoCirculo, 0); // 0 = al frente
        jPanel2.setComponentZOrder(circulo, 1); // círculo detrás
        jPanel2.setComponentZOrder(btnNotificacion1, 2); // botón más atrás

//submenu inventario------------------
        // Inicializar el submenú
        submenuInventario = new JPanel();
        submenuInventario.setBackground(new Color(255, 255, 255)); // Mismo color que el menú
        submenuInventario.setLayout(new GridLayout(2, 1, 0, 0)); // 3 filas, 1 columna, espacio entre ítems
        submenuInventario.setPreferredSize(new Dimension(250, 80)); // Reducir altura total para reflejar menos espacio

        // Añadir ítems al submenú (como botones RSButton)
        //boton para materiales
        item1 = new rojeru_san.RSButton();
        item1.setBackground(new Color(0, 0, 0)); // Fondo igual que el menú
        item1.setForeground(Color.WHITE);
        item1.setFont(new Font("Tahoma", Font.BOLD, 15));
        item1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1)); // Margen izquierdo para alinear con el texto del menú
        item1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/tratar-con-cuidado.png")));
        item1.setIconTextGap(10);
        item1.setText("Materiales");
        item1.setColorHover(new Color(150, 150, 150)); // Mismo color de hover que los botones del menú
        item1.setColorTextHover(Color.WHITE);
        aplicarTema();

        //boton para herramientas
        item2 = new rojeru_san.RSButton();
        item2.setBackground(new Color(0, 0, 0)); // Fondo igual que el menú
        item2.setForeground(Color.WHITE);
        item2.setFont(new Font("Tahoma", Font.BOLD, 15));
        item2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1)); // Margen izquierdo para alinear con el texto del menú
        item2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/llave-inglesa.png")));
        item2.setIconTextGap(10);
        item2.setText("Herramientas");
        item2.setColorHover(new Color(150, 150, 150)); // Mismo color de hover que los botones del menú
        item2.setColorTextHover(Color.WHITE);

        // Configurar el ActionListener para "Materiales"
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deseleccionar();
                dos.setSelected(true); // Mantener "Inventario" resaltado

                // Cargar la vista de Materiales
                materiales m = new materiales();
                m.setSize(1290, 730);
                m.setLocation(0, 0);
                contenedor.removeAll();
                contenedor.add(m);
                contenedor.revalidate();
                contenedor.repaint();

                animacion();
            }
        });

// Configurar el ActionListener para "Herramientas"
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                deseleccionar();
                dos.setSelected(true);

                // Cargar la vista de Herramientas
                herramientas h = new herramientas();
                h.setSize(1290, 730);
                h.setLocation(0, 0);
                contenedor.removeAll();
                contenedor.add(h);
                contenedor.revalidate();
                contenedor.repaint();

                animacion();
            }
        });

        submenuInventario.add(item1);
        submenuInventario.add(item2);
//submenu inventario------------------

//submenu ventas------------------
// Inicializar el submenú de Ventas
        submenuVentas = new JPanel();
        submenuVentas.setBackground(new Color(255, 255, 255)); // Mismo color que el menú
        submenuVentas.setLayout(new java.awt.GridLayout(2, 1, 0, 0)); // 2 filas, 1 columna
        submenuVentas.setPreferredSize(new Dimension(250, 80)); // Alto de 100 (50 por cada botón)

        // Botón "Pedidos"
        item3 = new rojeru_san.RSButton();
        item3.setBackground(new Color(0, 0, 0));
        item3.setForeground(Color.WHITE);
        item3.setFont(new Font("Tahoma", Font.BOLD, 15));
        item3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1));
        item3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/bolsa-de-la-compra.png"))); // Puedes cambiar el icono
        item3.setIconTextGap(10);
        item3.setText("Pedidos");
        item3.setColorHover(new Color(150, 150, 150));
        item3.setColorTextHover(Color.WHITE);

        // Botón "Cotización"
        item4 = new rojeru_san.RSButton();
        item4.setBackground(new Color(0, 0, 0));
        item4.setForeground(Color.WHITE);
        item4.setFont(new Font("Tahoma", Font.BOLD, 15));
        item4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1));
        item4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/solicitud-de-cotizacion.png"))); // Puedes cambiar el icono
        item4.setIconTextGap(10);
        item4.setText("Cotización");
        item4.setColorHover(new Color(150, 150, 150));
        item4.setColorTextHover(Color.WHITE);

        // ActionListener para "Pedidos"
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deseleccionar otros botones del menú principal
                deseleccionar();

                // Cargar la vista de Pedidos
                vista.Ventas.pedido pedidos = new vista.Ventas.pedido(contenedor);
                pedidos.setSize(1290, 730);
                pedidos.setLocation(0, 0);
                contenedor.removeAll();
                contenedor.add(pedidos);
                contenedor.revalidate();
                contenedor.repaint();
                animacion();
            }

        });

        // ActionListener para "Cotización"
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Deseleccionar otros botones del menú principal
                deseleccionar();

                // Cargar la vista de Cotización (temporalmente un mensaje)
                cotizacion co = new cotizacion(contenedor);
                co.setSize(1290, 730); // Ajustar tamaño dinámicamente
                co.setLocation(0, 0);

                contenedor.removeAll();
                contenedor.add(co);
                contenedor.revalidate();
                contenedor.repaint();
                animacion();
            }
        });

        submenuVentas.add(item3);
        submenuVentas.add(item4);
//submenu ventas------------------

//submenu ajustes------------------
// Inicializar el submenú de Ajustes
        submenuAjustes = new JPanel();
        submenuAjustes.setBackground(new Color(255, 255, 255)); // Fondo igual que el menú
        submenuAjustes.setLayout(new GridLayout(1, 1, 0, 0)); // 1 fila, 1 columna
        submenuAjustes.setPreferredSize(new Dimension(250, 50)); // Alto suficiente para un botón

// Botón "Configuración" dentro del submenú de Ajustes
        ajusteButton = new rojeru_san.RSButton();
        ajusteButton.setBackground(new Color(0, 0, 0));
        ajusteButton.setForeground(Color.WHITE);
        ajusteButton.setFont(new Font("Tahoma", Font.BOLD, 15));
        ajusteButton.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 40, 1, 1));
        ajusteButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ajusteButton.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ajusteButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/copia-de-respaldo.png")));
        ajusteButton.setIconTextGap(10);
        ajusteButton.setText("Copia de seguridad");
        ajusteButton.setColorHover(new Color(150, 150, 150));
        ajusteButton.setColorTextHover(Color.WHITE);
        aplicarTema();

// Acción del botón "Configuración"
        ajusteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deseleccionar();

                // Cargar la vista de Materiales
                backup cliente = new backup(new javax.swing.JFrame(), true);
                cliente.setSize(1290, 730);
                cliente.setLocation(0, 0);

                contenedor.removeAll();
                contenedor.add(cliente);
                contenedor.revalidate();
                contenedor.repaint();

                // Ejemplo: Cargar un panel de configuración aquí
                animacion();
            }
        });

        submenuAjustes.add(ajusteButton);
//submenu ajustes------------------

        // Oculta el panel lateral derecho (jPanel5)
        jPanel5.setVisible(false);

// Configura el panel de contenido
        contenedor.setBounds(menuExpanded ? MENU_EXPANDED_WIDTH : MENU_COLLAPSED_WIDTH,
                65, 1800,
                getHeight());

        setIconImage(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB_PRE));

        aplicarTema();

        cargarUsuarioLogueado();
        cargarrol();
        configurarPopupMenuNotificacion();

        jPanel1.setLayout(new BorderLayout());
        jPanel4.setVisible(true);
        jPanel5.setVisible(false);
        jPanel3.setVisible(false);
// Cambia el layout del panel del menú a null (después de initComponents())
        jPanel3.setLayout(null);

        // Establece tamaño inicial
        jPanel3.setPreferredSize(new Dimension(MENU_EXPANDED_WIDTH, jPanel3.getHeight()));

        // Seleccionar el botón "uno" por defecto y cargar el panel Escritorio1
        this.uno.setSelected(true);
        Escritorio1 es = new Escritorio1();
        es.setSize(1290, 730); // Ajustar tamaño dinámicamente
        es.setLocation(0, 0);

        contenedor.removeAll();
        contenedor.add(es, BorderLayout.CENTER);
        contenedor.revalidate();
        contenedor.repaint();

    }

    private void cambiarEstiloBotonRS(RSButton boton, Color fondo, Color texto) {
        boton.setBackground(fondo);
        boton.setColorText(texto);

        // Hover personalizado según modo oscuro/claro
        if (TemaManager.getInstance().isOscuro()) {
            boton.setColorHover(new Color(70, 70, 90)); // Hover más oscuro en modo oscuro
        } else {
            boton.setColorHover(new Color(118, 142, 240)); // Hover azul en modo claro
        }

        boton.setColorTextHover(texto);
    }

    private void aplicarTema() {
        Color fondoPrincipal, fondoPanel, fondoBoton, textoBoton, textoLabel, contenedorc;
        String rutaLogo;

        // Use TemaManager to get the current theme
        boolean oscuro = TemaManager.getInstance().isOscuro();

        if (oscuro) {
            fondoPrincipal = new Color(21, 21, 33);
            fondoPanel = new Color(21, 21, 33);
            fondoBoton = new Color(30, 30, 45);
            textoBoton = Color.WHITE;
            textoLabel = Color.WHITE;
            contenedorc = new Color(21, 21, 33);
            jPanel1.setBackground(new Color(21, 21, 33));
            jPanel2.setBackground(new Color(30, 30, 45));
            jPanel3.setBackground(new Color(30, 30, 45));
            jPanel4.setBackground(new Color(30, 30, 45));
            contenedor.setBackground(new Color(21, 21, 33));
            jPanel5.setBackground(new Color(30, 30, 45));

            // Ajustar foreground de los botones del submenú
            item1.setForeground(textoBoton);
            item2.setForeground(textoBoton);
            item3.setForeground(textoBoton);
            item4.setForeground(textoBoton);
            ajusteButton.setForeground(textoBoton);

            lblUsuarioLogueado.setForeground(Color.WHITE);
            rolusuario.setForeground(Color.WHITE);
            lblTituloPrincipal.setForeground(Color.WHITE);
            cambiarEstiloBotonRS(item1, new Color(30, 30, 45), Color.WHITE);

            uno.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa.png")));
            dos.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-negra.png")));
            cuatro.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity_1.png")));
            nueve.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogar.png")));
            nueve1.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogar.png")));

            item1.setIcon(new ImageIcon(getClass().getResource("/imagenes/tratar-con-cuidado.png")));
            item2.setIcon(new ImageIcon(getClass().getResource("/imagenes/llave-inglesa.png")));
            item3.setIcon(new ImageIcon(getClass().getResource("/imagenes/bolsa-de-la-compra.png")));
            item4.setIcon(new ImageIcon(getClass().getResource("/imagenes/solicitud-de-cotizacion.png")));
            ajusteButton.setIcon(new ImageIcon(getClass().getResource("/imagenes/copia-de-respaldo (1).png")));

            rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/WhatsApp Image 2025-03-20 at 3.58.18 PM (1).png")));
            rSLabelImage3.setIcon(new ImageIcon(getClass().getResource("/imagenes/luna (6).png")));
            rSLabelImage1.setIcon(new ImageIcon(getClass().getResource("/imagenes/feliz-sol-negro.png")));
            lblBotonAyuda.setIcon(new ImageIcon(getClass().getResource("/imagenes/boton-web-de-ayuda (1).png")));

            uno1.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa.png")));
            dos1.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-negra.png")));
            cuatro1.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity_1.png")));
            btnNotificacion1.setForeground(new Color(255, 255, 255));
            rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/logo blancoooo.png")));

        } else {
            fondoPrincipal = new Color(255, 255, 255);
            fondoPanel = new Color(242, 247, 255);
            fondoBoton = new Color(255, 255, 255);
            textoBoton = Color.BLACK;
            textoLabel = Color.BLACK;
            contenedorc = new Color(242, 247, 255);

            jPanel1.setBackground(new Color(242, 247, 255));
            jPanel2.setBackground(new Color(255, 255, 255));
            jPanel3.setBackground(new Color(255, 255, 255));
            jPanel4.setBackground(new Color(255, 255, 255));

            // Ajustar foreground de los botones del submenú
            item1.setForeground(textoBoton);
            item2.setForeground(textoBoton);
            item3.setForeground(textoBoton);
            item4.setForeground(textoBoton);
            ajusteButton.setForeground(textoBoton);

            contenedor.setBackground(new Color(242, 247, 255));
            jPanel5.setBackground(new Color(242, 247, 255));
            lblUsuarioLogueado.setForeground(textoLabel);
            rolusuario.setForeground(textoLabel);
            lblTituloPrincipal.setForeground(textoLabel);
            btnNotificacion1.setForeground(new Color(0, 0, 0));
            uno.setIcon(new ImageIcon(getClass().getResource("/imagenes/home.png")));
            dos.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-blanca.png")));
            cuatro.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity.png")));
            nueve.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogarnegro.png")));
                        nueve1.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogarnegro.png")));

            rSLabelImage1.setIcon(new ImageIcon(getClass().getResource("/imagenes/feliz-sol (6).png")));
            rSLabelImage3.setIcon(new ImageIcon(getClass().getResource("/imagenes/luna (7).png")));
            lblBotonAyuda.setIcon(new ImageIcon(getClass().getResource("/imagenes/boton-web-de-ayuda.png")));

            item1.setIcon(new ImageIcon(getClass().getResource("/imagenes/tratar-con-cuidadoNegro.png")));
            item2.setIcon(new ImageIcon(getClass().getResource("/imagenes/llave-inglesaNegro.png")));
            item3.setIcon(new ImageIcon(getClass().getResource("/imagenes/bolsa-de-la-compraNegro.png")));
            item4.setIcon(new ImageIcon(getClass().getResource("/imagenes/solicitud-de-cotizacionNegro.png")));
            ajusteButton.setIcon(new ImageIcon(getClass().getResource("/imagenes/copia-de-respaldo.png")));

            uno1.setIcon(new ImageIcon(getClass().getResource("/imagenes/home.png")));
            dos1.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-blanca.png")));
            cuatro1.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity.png")));

            rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/logo_azul_sin_letras.png")));

        }

        // Botones
        cambiarEstiloBotonRS(uno, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(dos, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(cuatro, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(nueve, fondoBoton, textoBoton);

        cambiarEstiloBotonRS(item1, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(item2, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(item3, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(item4, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(ajusteButton, fondoBoton, textoBoton);

        cambiarEstiloBotonRS(uno1, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(dos1, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(cuatro1, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(nueve1, fondoBoton, textoBoton);

        // Labels
    }

    private void toggleMenu() {
        int targetWidth = menuExpanded ? MENU_COLLAPSED_WIDTH : MENU_EXPANDED_WIDTH;

        Timer timer = new Timer(10, new ActionListener() {
            int currentWidth = jPanel3.getWidth();

            @Override
            public void actionPerformed(ActionEvent e) {
                if (menuExpanded) {
                    currentWidth -= 10;
                    if (currentWidth <= MENU_COLLAPSED_WIDTH) {
                        currentWidth = MENU_COLLAPSED_WIDTH;
                        ((Timer) e.getSource()).stop();
                        updateMenuIcons(false);
                    }
                } else {
                    currentWidth += 10;
                    if (currentWidth >= MENU_EXPANDED_WIDTH) {
                        currentWidth = MENU_EXPANDED_WIDTH;
                        ((Timer) e.getSource()).stop();
                        updateMenuIcons(true);
                    }
                }

                jPanel3.setPreferredSize(new Dimension(currentWidth, jPanel3.getHeight()));
                jPanel3.revalidate();
                contenedor.revalidate();
            }
        });

        timer.start();
        menuExpanded = !menuExpanded;
    }

    private void updateMenuIcons(boolean expanded) {
        // Actualiza el texto de los botones según el estado
        uno.setText(expanded ? "Escritorio" : "");
        dos.setText(expanded ? " Inventario           ▼" : "");
        cuatro.setText(expanded ? "Producción" : "");
        nueve.setText(expanded ? "Catálogo" : "");

        // Oculta submenús si se contrae el menú
        if (!expanded) {
            if (submenuVisible) {
                jPanel3.remove(submenuInventario);
                submenuVisible = false;
            }
            if (submenuVentasVisible) {
                jPanel3.remove(submenuVentas);
                submenuVentasVisible = false;
            }
        }
    }

    private void cargarImagenAvatar() {
        UsuarioModelo usuario = controlador.obtenerUsuario(idUsuario);
        if (usuario.getImagen() != null && usuario.getImagen().length > 0) {
            ImageIcon icon = new ImageIcon(usuario.getImagen());
            Image img = icon.getImage().getScaledInstance(rSLabelCircleImage1.getWidth(),
                    rSLabelCircleImage1.getHeight(),
                    Image.SCALE_SMOOTH);
            rSLabelCircleImage1.setIcon(new ImageIcon(img));
        } else {
            // Imagen por defecto
        }
    }

    public void actualizarAvatar() {
        cargarImagenAvatar(); // Reutiliza el método para actualizar
    }

    private void configurarPopupMenu() {
        JPopupMenu userPopupMenu = new JPopupMenu();
        userPopupMenu.setOpaque(false);
        userPopupMenu.setBackground(new Color(255, 255, 255));
        userPopupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Crear los ítems del menú  
        JMenuItem profileItem = new JMenuItem("Perfil");

        JMenuItem logoutItem = new JMenuItem("Salir");

        // Personalizar los ítems
        Font menuFont = new Font("Arial", Font.PLAIN, 14);
        Dimension itemSize = new Dimension(150, 30);

        profileItem.setFont(menuFont);
        profileItem.setForeground(new Color(100, 100, 100));
        profileItem.setBackground(new Color(255, 255, 255));
        profileItem.setOpaque(false);
        profileItem.setPreferredSize(itemSize);
        profileItem.setBorderPainted(false);

        logoutItem.setFont(menuFont);
        logoutItem.setForeground(new Color(100, 100, 100));
        logoutItem.setBackground(new Color(255, 255, 255));
        logoutItem.setOpaque(false);
        logoutItem.setPreferredSize(itemSize);
        logoutItem.setBorderPainted(false);

        // Agregar ítems al menú  
        userPopupMenu.add(profileItem);
        userPopupMenu.add(logoutItem);

        // Mostrar el JPopupMenu al hacer clic en rSLabelCircleImage1  
        rSLabelCircleImage1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cargarImagenAvatar(); // Actualizar imagen antes de mostrar el menú
                userPopupMenu.show(rSLabelCircleImage1, 0, rSLabelCircleImage1.getHeight());
            }
        });

        // Acciones de los ítems del menú  
        profileItem.addActionListener(e -> {
            // Crear una nueva instancia de perfil1 cada vez
            perfil1 perfilPanel = new perfil1(idUsuario);
            perfilPanel.setSize(1290, 730);
            perfilPanel.setLocation(0, 0);
            contenedor.removeAll();
            contenedor.add(perfilPanel);
            contenedor.revalidate();
            contenedor.repaint();
            lblTituloPrincipal.setText("Perfil");
        });

    }

    private void animacion() {
        int posicion = jPanel3.getX();
        //System.out.println("Posición actual: " + posicion); // Debug
        if (posicion >= -1) {  // Cambiar a >= para mayor seguridad
            Animacion.Animacion.mover_izquierda(0, -258, 2, 2, jPanel3);
            Animacion.Animacion.mover_izquierda(258, +111, 2, 2, contenedor);

            jPanel4.setVisible(true);
            jPanel5.setVisible(false);
            jPanel3.setVisible(false);
        }
    }

    private void animation_salir() {
        int posicion = jPanel3.getX();
        //System.out.println("Posición actual: " + posicion); // Debug
        if (posicion >= -1) {
            Animacion.Animacion.mover_derecha(-258, 0, 2, 2, jPanel3);
            Animacion.Animacion.mover_derecha(-2, +258, 2, 2, contenedor);

            jPanel3.setVisible(true);
            jPanel4.setVisible(false);
            jPanel5.setVisible(true);
            jPanel5.setOpaque(false);
        } else {
            Animacion.Animacion.mover_derecha(-258, 0, 2, 2, jPanel3);
            Animacion.Animacion.mover_derecha(-2, +258, 2, 2, contenedor);

            jPanel3.setVisible(true);
            jPanel4.setVisible(false);
            jPanel5.setVisible(true);
            jPanel5.setOpaque(false);
        }

    }

    private void ocultarSubmenus() {
        // Cerrar submenú de Inventario si está visible
        if (submenuVisible) {
            jPanel3.removeAll();
            jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            // Restaurar posiciones originales de los botones con un alto de 50 píxeles
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 63, 158, 130));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 50));
            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));

            dos.setText(" Inventario           ▼");
            submenuVisible = false;

        }

        // Cerrar submenú de Ventas si está visible
        if (submenuVentasVisible) {
            jPanel3.removeAll();
            jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            // Restaurar posiciones originales de los botones con un alto de 50 píxeles
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 63, 158, 130));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 50));
            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));
            submenuVentasVisible = false;

        }
        if (submenuAjustesVisible) {
            jPanel3.removeAll();
            jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 63, 158, 130));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 50));
            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));
            submenuAjustesVisible = false;
        }

        jPanel3.revalidate();
        jPanel3.repaint();
    }

    private void configurarPopupMenuNotificacion() {
        JPopupMenu notificacionPopupMenu = new JPopupMenu();
        notificacionPopupMenu.setOpaque(false);
        notificacionPopupMenu.setBackground(new Color(255, 255, 255));
        notificacionPopupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Panel personalizado para el encabezado
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 11, 5, 11));

        // Título "Notificaciones"
        JLabel titleLabel = new JLabel("Notificaciones");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(0, 0, 0));
        headerPanel.add(titleLabel, BorderLayout.WEST);

        RSLabelImage settings = new RSLabelImage();
        URL imageUrl = getClass().getResource("/ajuste (3).png");
        ImageIcon icono = (imageUrl != null) ? new ImageIcon(imageUrl) : new ImageIcon("default_bell.png");
        settings.setIcon(icono);
        settings.setPreferredSize(new Dimension(25, 25));

        // Agregar MouseListener para abrir JDialog al hacer clic
// Configurar el cursor de mano al pasar el mouse
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                settings.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Cambia a cursor de mano
            }

            @Override
            public void mouseExited(MouseEvent e) {
                settings.setCursor(Cursor.getDefaultCursor()); // Vuelve al cursor normal
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                notificacionPopupMenu.setVisible(false);
                stock dialog = new stock(new javax.swing.JFrame(), true);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                // Actualizar el panel después de cerrar el diálogo
                actualizarCenterPanel();
            }
        });

        headerPanel.add(settings, BorderLayout.EAST);

        // Añadir el panel de encabezado al JPopupMenu
        notificacionPopupMenu.add(headerPanel);

        // Añadir separador entre header y center
        JSeparator separator = new JSeparator();
        separator.setForeground(new Color(200, 200, 200)); // Color gris claro
        notificacionPopupMenu.add(separator);

// Panel para materiales con stock bajo
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

// Obtener materiales con stock bajo
        Ctrl_InventarioMaterial ctrl = new Ctrl_InventarioMaterial();
        List<Ctrl_InventarioMaterial.MaterialConDetalles> materialesBajos = ctrl.obtenerMaterialesConStockBajo();

        if (materialesBajos.isEmpty()) {
            JLabel lblSinNotificaciones = new JLabel("No hay materiales con stock bajo");
            lblSinNotificaciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblSinNotificaciones.setForeground(new Color(100, 100, 100));
            lblSinNotificaciones.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(lblSinNotificaciones);
        } else {
            // Crear tarjeta para cada material con stock bajo
            for (Ctrl_InventarioMaterial.MaterialConDetalles detalle : materialesBajos) {
                centerPanel.add(crearTarjetaMaterial(detalle));
                centerPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre tarjetas
            }
        }

        // Hacer el panel desplazable
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setPreferredSize(new Dimension(350, 400));
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        notificacionPopupMenu.add(scrollPane);

        // Mostrar el JPopupMenu al hacer clic en btnNotificacion
        btnNotificacion1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnNotificacion1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mano al pasar
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnNotificacion1.setCursor(Cursor.getDefaultCursor()); // Cursor normal al salir
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (popupVisible) {
                    notificacionPopupMenu.setVisible(false);
                    popupVisible = false;
                } else {
                    // Actualizar contenido antes de mostrar
                    actualizarCenterPanel();

                    notificacionPopupMenu.show(btnNotificacion1, -50, btnNotificacion1.getHeight() + 15);
                    popupVisible = true;
                }
            }
        });
        // Cargar datos iniciales
        actualizarCenterPanel();

        // Configurar temporizador para actualización automática (cada 1 segundos)
        updateTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCenterPanel();
            }
        });
        updateTimer.start();
    }

// Método para obtener materiales con stock bajo
    private List<Ctrl_InventarioMaterial.MaterialConDetalles> obtenerMaterialesConStockBajo() {
        Ctrl_InventarioMaterial ctrl = new Ctrl_InventarioMaterial();
        return ctrl.obtenerMaterialesConStockBajo();
    }

    // Método para actualizar el contenido del centerPanel
    private void actualizarCenterPanel() {
        // Limpiar el panel actual
        centerPanel.removeAll();

        // Obtener materiales con stock bajo
        Ctrl_InventarioMaterial ctrl = new Ctrl_InventarioMaterial();
        List<Ctrl_InventarioMaterial.MaterialConDetalles> materialesBajos = ctrl.obtenerMaterialesConStockBajo();

        // Actualizar el contador de notificaciones
        int totalNotificaciones = materialesBajos.size();
        String textoContador = totalNotificaciones > 99 ? "99+" : String.valueOf(totalNotificaciones);
        textoCirculo.setText(textoContador);

        // Mostrar u ocultar el círculo según haya notificaciones
        circulo.setVisible(totalNotificaciones > 0);
        textoCirculo.setVisible(totalNotificaciones > 0);

        if (materialesBajos.isEmpty()) {
            JLabel lblSinNotificaciones = new JLabel("No hay materiales con stock bajo");
            lblSinNotificaciones.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            lblSinNotificaciones.setForeground(new Color(100, 100, 100));
            lblSinNotificaciones.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(lblSinNotificaciones);
        } else {
            // Crear tarjeta para cada material con stock bajo
            for (Ctrl_InventarioMaterial.MaterialConDetalles detalle : materialesBajos) {
                centerPanel.add(crearTarjetaMaterial(detalle));
                centerPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espacio entre tarjetas
            }
        }

        // Refrescar el panel
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    private JPanel crearTarjetaMaterial(Ctrl_InventarioMaterial.MaterialConDetalles detalle) {
        JPanel tarjeta = new JPanel(new BorderLayout(10, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Fondo
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                // Borde gris
                g2.setStroke(new BasicStroke(1f));
                g2.setColor(new Color(200, 200, 200)); // Color gris claro
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
                g2.dispose();
            }
        };

        tarjeta.setOpaque(false);
        tarjeta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tarjeta.setBackground(Color.WHITE);
        tarjeta.setMaximumSize(new Dimension(330, 80));

        MaterialDatos material = detalle.getMaterial();

        // Panel superior (nombre y categoría)
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setOpaque(false);

        JLabel lblNombre = new JLabel(material.getNombre());
        lblNombre.setFont(new Font("Segoe UI", Font.BOLD, 14));
        panelSuperior.add(lblNombre, BorderLayout.WEST);

        JLabel lblCategoria = new JLabel(detalle.getNombreCategoria());
        lblCategoria.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblCategoria.setForeground(new Color(100, 100, 100));
        panelSuperior.add(lblCategoria, BorderLayout.EAST);

        tarjeta.add(panelSuperior, BorderLayout.NORTH);

        // Panel inferior (cantidad y stock mínimo)
        JPanel panelInferior = new JPanel(new BorderLayout());
        panelInferior.setOpaque(false);

        // Procesar cantidad y stock mínimo para mostrar valores originales o convertidos
        String cantidadOriginal = material.getCantidad() != null ? material.getCantidad() : "0";
        String stockMinimoOriginal = material.getStockMinimo() != null ? material.getStockMinimo() : "0";

        // Limpiar y convertir valores para comparación
        String cantidadStr = cantidadOriginal.replace(",", ".").replaceAll("[^0-9.]", "");
        String stockMinimoStr = stockMinimoOriginal.replace(",", ".").replaceAll("[^0-9.]", "");

        double cantidad = cantidadStr.isEmpty() ? 0.0 : Double.parseDouble(cantidadStr);
        double stockMinimo = stockMinimoStr.isEmpty() ? 0.0 : Double.parseDouble(stockMinimoStr);

        Color colorStock = (cantidad <= stockMinimo)
                ? new Color(255, 50, 50) // Rojo para stock crítico
                : new Color(255, 165, 0); // Naranja para stock bajo

// Mostrar valores originales con unidad
        String unidadMedida = detalle.getNombreUnidadMedida() != null ? detalle.getNombreUnidadMedida() : "";
        JLabel lblStock = new JLabel(String.format("Stock: %s/%s %s",
                cantidadOriginal, stockMinimoOriginal, unidadMedida));
        lblStock.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblStock.setForeground(colorStock);
        panelInferior.add(lblStock, BorderLayout.WEST);

        tarjeta.add(panelInferior, BorderLayout.SOUTH);

        return tarjeta;
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
        jPanel5 = new javax.swing.JPanel();
        contenedor = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        rSLabelImage1 = new rojerusan.RSLabelImage();
        rSSwitch1 = new rojerusan.RSSwitch();
        rSLabelCircleImage1 = new rojerusan.RSLabelCircleImage();
        rolusuario = new javax.swing.JLabel();
        lblUsuarioLogueado = new javax.swing.JLabel();
        lblTituloPrincipal = new javax.swing.JLabel();
        btnNotificacion1 = new rojerusan.RSLabelIcon();
        rSLabelImage3 = new rojerusan.RSLabelImage();
        lblBotonAyuda = new rojerusan.RSLabelImage();
        jPanel4 = new javax.swing.JPanel();
        uno1 = new rojeru_san.RSButton();
        dos1 = new rojeru_san.RSButton();
        cuatro1 = new rojeru_san.RSButton();
        nueve1 = new rojeru_san.RSButton();
        jPanel3 = new javax.swing.JPanel();
        dos = new rojeru_san.RSButton();
        cuatro = new rojeru_san.RSButton();
        nueve = new rojeru_san.RSButton();
        uno = new rojeru_san.RSButton();
        rSPanelImage3 = new rojerusan.RSPanelImage();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setMinimumSize(new java.awt.Dimension(1290, 730));
        jPanel5.setPreferredSize(new java.awt.Dimension(1290, 730));
        jPanel5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jPanel5MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jPanel5MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 68, 1506, 838));

        contenedor.setBackground(new java.awt.Color(255, 255, 255));
        contenedor.setMinimumSize(new java.awt.Dimension(1290, 730));
        contenedor.setPreferredSize(new java.awt.Dimension(1290, 730));

        javax.swing.GroupLayout contenedorLayout = new javax.swing.GroupLayout(contenedor);
        contenedor.setLayout(contenedorLayout);
        contenedorLayout.setHorizontalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1491, Short.MAX_VALUE)
        );
        contenedorLayout.setVerticalGroup(
            contenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 840, Short.MAX_VALUE)
        );

        jPanel1.add(contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 66, 1491, 840));
        contenedor.getAccessibleContext().setAccessibleName("");

        jPanel2.setBackground(new java.awt.Color(29, 30, 81));

        rSLabelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/feliz-sol-negro.png"))); // NOI18N

        rSSwitch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSSwitch1MouseClicked(evt);
            }
        });

        rSLabelCircleImage1.setBackground(new java.awt.Color(29, 30, 81));
        rSLabelCircleImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WhatsApp Image 2025-03-28 at 11.10.17 AM.jpeg"))); // NOI18N
        rSLabelCircleImage1.setColorBorde(new java.awt.Color(29, 30, 81));
        rSLabelCircleImage1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSLabelCircleImage1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                rSLabelCircleImage1MouseEntered(evt);
            }
        });

        rolusuario.setBackground(new java.awt.Color(0, 0, 0));
        rolusuario.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        rolusuario.setForeground(new java.awt.Color(255, 255, 255));
        rolusuario.setText("Escritorio");

        lblUsuarioLogueado.setBackground(new java.awt.Color(0, 0, 0));
        lblUsuarioLogueado.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        lblUsuarioLogueado.setForeground(new java.awt.Color(255, 255, 255));
        lblUsuarioLogueado.setText("Escritorio");

        lblTituloPrincipal.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        lblTituloPrincipal.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloPrincipal.setText("Escritorio");

        btnNotificacion1.setBackground(new java.awt.Color(255, 255, 255));
        btnNotificacion1.setForeground(new java.awt.Color(255, 255, 255));
        btnNotificacion1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.NOTIFICATIONS);
        btnNotificacion1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnNotificacion1MouseClicked(evt);
            }
        });

        rSLabelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luna (6).png"))); // NOI18N

        lblBotonAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/boton-web-de-ayuda (1).png"))); // NOI18N
        lblBotonAyuda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblBotonAyudaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblBotonAyudaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblBotonAyudaMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(310, 310, 310)
                .addComponent(lblTituloPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(310, 310, 310)
                .addComponent(rSLabelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(rSSwitch1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(rSLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(lblBotonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(17, 17, 17)
                .addComponent(btnNotificacion1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(rSLabelCircleImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblUsuarioLogueado)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(rolusuario)))
                .addContainerGap(397, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblTituloPrincipal))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rSLabelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rSSwitch1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(rSLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblBotonAyuda, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnNotificacion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(rSLabelCircleImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(lblUsuarioLogueado)
                .addGap(7, 7, 7)
                .addComponent(rolusuario))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        jPanel4.setBackground(new java.awt.Color(29, 30, 81));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        uno1.setBackground(new java.awt.Color(29, 30, 81));
        uno1.setBorder(null);
        uno1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casa.png"))); // NOI18N
        uno1.setColorHover(new java.awt.Color(200, 200, 200));
        uno1.setColorTextHover(new java.awt.Color(0, 0, 0));
        uno1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        uno1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                uno1MouseEntered(evt);
            }
        });
        uno1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uno1ActionPerformed(evt);
            }
        });
        jPanel4.add(uno1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 40, 50));

        dos1.setBackground(new java.awt.Color(29, 30, 81));
        dos1.setBorder(null);
        dos1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caja.png"))); // NOI18N
        dos1.setColorHover(new java.awt.Color(169, 169, 169));
        dos1.setColorTextHover(new java.awt.Color(0, 0, 0));
        dos1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        dos1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dos1MouseEntered(evt);
            }
        });
        dos1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dos1ActionPerformed(evt);
            }
        });
        jPanel4.add(dos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 240, 40, 50));

        cuatro1.setBackground(new java.awt.Color(29, 30, 81));
        cuatro1.setBorder(null);
        cuatro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/productivity.png"))); // NOI18N
        cuatro1.setColorHover(new java.awt.Color(200, 200, 200));
        cuatro1.setColorTextHover(new java.awt.Color(0, 0, 0));
        cuatro1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cuatro1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                cuatro1MouseEntered(evt);
            }
        });
        cuatro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuatro1ActionPerformed(evt);
            }
        });
        jPanel4.add(cuatro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 40, 51));

        nueve1.setBackground(new java.awt.Color(29, 30, 81));
        nueve1.setBorder(null);
        nueve1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/public-service_1.png"))); // NOI18N
        nueve1.setColorHover(new java.awt.Color(128, 128, 128));
        nueve1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nueve1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nueve1MouseEntered(evt);
            }
        });
        nueve1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nueve1ActionPerformed(evt);
            }
        });
        jPanel4.add(nueve1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 40, 52));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 906));

        jPanel3.setBackground(new java.awt.Color(29, 30, 81));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dos.setBackground(new java.awt.Color(29, 30, 81));
        dos.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        dos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/caja.png"))); // NOI18N
        dos.setText(" Inventario           ▼");
        dos.setColorHover(new java.awt.Color(118, 142, 240));
        dos.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        dos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        dos.setMaximumSize(new java.awt.Dimension(127, 24));
        dos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                dosMouseEntered(evt);
            }
        });
        dos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dosActionPerformed(evt);
            }
        });
        jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 245, 250, 50));

        cuatro.setBackground(new java.awt.Color(29, 30, 81));
        cuatro.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        cuatro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/productivity.png"))); // NOI18N
        cuatro.setText(" Producción");
        cuatro.setColorHover(new java.awt.Color(128, 128, 128));
        cuatro.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        cuatro.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        cuatro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cuatroActionPerformed(evt);
            }
        });
        jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 290, 250, 52));

        nueve.setBackground(new java.awt.Color(29, 30, 81));
        nueve.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        nueve.setIcon(new javax.swing.ImageIcon(getClass().getResource("/public-service_1.png"))); // NOI18N
        nueve.setText("Catalogo");
        nueve.setToolTipText("");
        nueve.setColorHover(new java.awt.Color(128, 128, 128));
        nueve.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        nueve.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        nueve.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nueveActionPerformed(evt);
            }
        });
        jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 340, 250, 53));

        uno.setBackground(new java.awt.Color(29, 30, 81));
        uno.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        uno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/casa.png"))); // NOI18N
        uno.setText("Escritorio");
        uno.setToolTipText("");
        uno.setColorHover(new java.awt.Color(118, 142, 240));
        uno.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        uno.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        uno.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        uno.setIconTextGap(10);
        uno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                unoActionPerformed(evt);
            }
        });
        jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 194, 250, 50));

        rSPanelImage3.setImagen(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logo blancoooo.png"))); // NOI18N
        rSPanelImage3.setPreferredSize(new java.awt.Dimension(158, 109));

        javax.swing.GroupLayout rSPanelImage3Layout = new javax.swing.GroupLayout(rSPanelImage3);
        rSPanelImage3.setLayout(rSPanelImage3Layout);
        rSPanelImage3Layout.setHorizontalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        rSPanelImage3Layout.setVerticalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 130, Short.MAX_VALUE)
        );

        jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 63, -1, 130));

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 906));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cuatroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuatroActionPerformed
        ocultarSubmenus();
        if (!this.cuatro.isSelected()) {
            deseleccionar();
            this.cuatro.setSelected(true);

            Produccion pr = new Produccion(new javax.swing.JFrame(), true);
            pr.setSize(1290, 730);
            pr.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(pr);
            contenedor.revalidate();
            contenedor.repaint();
            lblTituloPrincipal.setText("Producción");
        }
        {

        }
        animacion();

    }//GEN-LAST:event_cuatroActionPerformed

    private void unoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unoActionPerformed
        ocultarSubmenus();
        if (!this.uno.isSelected()) {
            deseleccionar();
            this.uno.setSelected(true);

            Escritorio1 es = new Escritorio1();
            es.setSize(1290, 730);
            es.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(es);
            contenedor.revalidate();
            contenedor.repaint();

        }
        animacion();
    }//GEN-LAST:event_unoActionPerformed

    private void dosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dosActionPerformed
        if (!this.dos.isSelected()) {
            deseleccionar();
            this.dos.setSelected(true);

        }
        // Alternar visibilidad del submenú
        if (!submenuVisible) {
            ocultarSubmenus();// Asegurarse de que el estado anterior esté limpio
            jPanel3.removeAll();
            jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

            // Reubicar los botones con el submenú visible
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 63, 158, 130));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(submenuInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 100)); // Submenú (Materiales + Herramientas)
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));
            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 250, 50));

            /* Mostrar submenú de Ventas
            submenuVentas.setBounds(0, cinco.getY() + cinco.getHeight(),
                    MENU_EXPANDED_WIDTH, submenuVentas.getPreferredSize().height);
            jPanel3.add(submenuVentas);
            jPanel3.setComponentZOrder(submenuVentas, 10);
            submenuVentasVisible = true;*/
            dos.setText(" Inventario           ▲"); // Submenú abierto

            submenuVisible = true;
            jPanel3.revalidate();
            jPanel3.repaint();
        } else {
            ocultarSubmenus(); // Ocultar submenú si ya está visible
            this.dos.setSelected(false);
        }

        lblTituloPrincipal.setText("Inventario");
    }//GEN-LAST:event_dosActionPerformed

    private void uno1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uno1ActionPerformed

    }//GEN-LAST:event_uno1ActionPerformed

    private void dos1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dos1ActionPerformed

    }//GEN-LAST:event_dos1ActionPerformed

    private void cuatro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cuatro1ActionPerformed

    }//GEN-LAST:event_cuatro1ActionPerformed

    private void uno1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uno1MouseEntered
        animation_salir();
    }//GEN-LAST:event_uno1MouseEntered

    private void cuatro1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cuatro1MouseEntered
        animation_salir();
    }//GEN-LAST:event_cuatro1MouseEntered

    private void dos1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dos1MouseEntered
        animation_salir();
    }//GEN-LAST:event_dos1MouseEntered

    private void jPanel5MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseExited

    }//GEN-LAST:event_jPanel5MouseExited

    private void jPanel5MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel5MouseEntered
        animacion();
    }//GEN-LAST:event_jPanel5MouseEntered

    private void nueveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nueveActionPerformed
        ocultarSubmenus();
        if (!this.nueve.isSelected()) {
            deseleccionar();
            this.nueve.setSelected(true);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Crear y mostrar el panel de inventario
            catalogo22 cat = new catalogo22(parentFrame, false, contenedor);
            // Crear y mostrar el panel de inventario

            cat.setSize(1290, 730);
            cat.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(cat);
            contenedor.revalidate();
            contenedor.repaint();
            lblTituloPrincipal.setText("Catálogo");

        }
        animacion();
    }//GEN-LAST:event_nueveActionPerformed

    private void dosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dosMouseEntered

    }//GEN-LAST:event_dosMouseEntered

    private void rSSwitch1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSSwitch1MouseClicked

        boolean nuevoTema = !TemaManager.getInstance().isOscuro();
        TemaManager.getInstance().guardarTema(nuevoTema);
        aplicarTema(); // Update this panel
    }//GEN-LAST:event_rSSwitch1MouseClicked

    private void rSLabelCircleImage1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSLabelCircleImage1MouseClicked
        configurarPopupMenu();
        // Acciones de los ítems del menú
    }//GEN-LAST:event_rSLabelCircleImage1MouseClicked

    private void rSLabelCircleImage1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rSLabelCircleImage1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_rSLabelCircleImage1MouseEntered

    private void btnNotificacion1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNotificacion1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnNotificacion1MouseClicked

    private void lblBotonAyudaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBotonAyudaMouseExited
        setCursor(Cursor.getDefaultCursor()); // Cursor normal al salir
    }//GEN-LAST:event_lblBotonAyudaMouseExited

    private void lblBotonAyudaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBotonAyudaMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Mano al pasar
    }//GEN-LAST:event_lblBotonAyudaMouseEntered

    private void lblBotonAyudaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblBotonAyudaMouseClicked
        //boton descargar pdf ayuda
        try {
            // Ruta local directa (ajusta a la que prefieras)
            File archivoPDF = new File("src/main/java/vista/manual_ayuda.pdf");
            // También puedes probar con:
            // File archivoPDF = new File("src/main/java/archivos/manual_ayuda.pdf");

            if (!archivoPDF.exists()) {
                JOptionPane.showMessageDialog(this, "No se encontró el PDF en:\n" + archivoPDF.getAbsolutePath());
                return;
            }

            // Abre el PDF en el navegador predeterminado
            Desktop.getDesktop().browse(archivoPDF.toURI());

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al abrir el PDF.");
        }
    }//GEN-LAST:event_lblBotonAyudaMouseClicked

    private void nueve1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nueve1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve1MouseEntered

    private void nueve1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nueve1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve1ActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalTrap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalTrap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalTrap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalTrap.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Nota: No ejecutes este main directamente en el flujo real.
                // Inicia desde Login11211.java para pasar el idUsuario real.
                // Usa un idUsuario de prueba solo para depuración local.
                int idUsuarioPrueba = Integer.parseInt(args.length > 0 ? args[0] : "0");
                new PrincipalTrap(idUsuarioPrueba).setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojerusan.RSLabelIcon btnNotificacion1;
    private javax.swing.JPanel contenedor;
    private rojeru_san.RSButton cuatro;
    private rojeru_san.RSButton cuatro1;
    private rojeru_san.RSButton dos;
    private rojeru_san.RSButton dos1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private rojerusan.RSLabelImage lblBotonAyuda;
    private javax.swing.JLabel lblTituloPrincipal;
    private javax.swing.JLabel lblUsuarioLogueado;
    private rojeru_san.RSButton nueve;
    private rojeru_san.RSButton nueve1;
    private rojerusan.RSLabelCircleImage rSLabelCircleImage1;
    private rojerusan.RSLabelImage rSLabelImage1;
    private rojerusan.RSLabelImage rSLabelImage3;
    private rojerusan.RSPanelImage rSPanelImage3;
    private rojerusan.RSSwitch rSSwitch1;
    private javax.swing.JLabel rolusuario;
    private rojeru_san.RSButton uno;
    private rojeru_san.RSButton uno1;
    // End of variables declaration//GEN-END:variables

    private void deseleccionar() {
        this.uno.setSelected(false);
        this.dos.setSelected(false);
        this.cuatro.setSelected(false);
        this.nueve.setSelected(false);

    }

    private void cargarUsuarioLogueado() {
        if (idUsuario == 0) {
            lblUsuarioLogueado.setText("Usuario logueado: No identificado");
            return;
        }
        UsuarioModelo usuario = controlador.obtenerUsuario(idUsuario);
        if (usuario.getId_usuario() != 0) {
            lblUsuarioLogueado.setText(usuario.getNombre() + " " + usuario.getApellido());

        } else {
            lblUsuarioLogueado.setText("Usuario logueado: No encontrado (ID: " + idUsuario + ")");
        }
    }

    private void cargarrol() {
        if (idUsuario == 0) {
            rolusuario.setText("Rol: No identificado");
            return;
        }
        UsuarioModelo usuario = controlador.obtenerUsuario(idUsuario);

        if (usuario.getId_usuario() != 0) {
            rolusuario.setText(usuario.getRol());

        } else {
            rolusuario.setText("Usuario logueado: No encontrado (ID: " + idUsuario + ")");
        }
    }

}
