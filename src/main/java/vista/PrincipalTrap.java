/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.Ctrl_Perfil;
import vista.Caja.Caja;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import modelo.UsuarioModelo;
import rojeru_san.RSButton;

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

    private JPanel submenuInventario;
    private boolean submenuVisible = false;
// Botón "Materiales" del submenú
    // Botón "Herramientas" del submenú
    // Para controlar si el submenú está visible
    private final int idUsuario;
    private final Ctrl_Perfil controlador;

    // Nuevas variables para el submenú de Ventas
    private final JPanel submenuVentas;
    private boolean submenuVentasVisible = false; // Para controlar si el submenú de Ventas está visible

    private rojeru_san.RSButton item1;
    private rojeru_san.RSButton item4;
    private rojeru_san.RSButton item3;
    private boolean menuExpanded = true; // Inicialmente expandido
    private final int MENU_COLLAPSED_WIDTH = 1;
    private final int MENU_EXPANDED_WIDTH = 250;

    public PrincipalTrap(int idUsuario) {
        this.idUsuario = idUsuario;
        this.controlador = new Ctrl_Perfil();
        this.item1 = new rojeru_san.RSButton(); // ✔️ usa la variable de instancia
        this.item4 = new rojeru_san.RSButton();
        this.item3 = new rojeru_san.RSButton(); // ✔️

        boolean oscuro = TemaManager.getInstance().isOscuro();
        initComponents();

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

        jPanel1.setLayout(new BorderLayout());
        jPanel4.setVisible(true);
        jPanel5.setVisible(false);
        jPanel3.setVisible(false);
// Cambia el layout del panel del menú a null (después de initComponents())
        jPanel3.setLayout(null);

        // Establece tamaño inicial
        jPanel3.setPreferredSize(new Dimension(MENU_EXPANDED_WIDTH, jPanel3.getHeight()));

//submenu inventario------------------
        // Inicializar el submenú
        submenuInventario = new JPanel();
        submenuInventario.setBackground(new Color(29, 30, 81)); // Mismo color que el menú
        submenuInventario.setLayout(new GridLayout(2, 1, 0, 0)); // 3 filas, 1 columna, espacio entre ítems
        submenuInventario.setPreferredSize(new Dimension(250, 80)); // Reducir altura total para reflejar menos espacio

        // Añadir ítems al submenú (como botones RSButton)
        //boton para materiales
        rojeru_san.RSButton item1 = new rojeru_san.RSButton();
        item1.setBackground(new Color(29, 30, 81)); // Fondo igual que el menú
        item1.setForeground(Color.WHITE);
        item1.setFont(new Font("Tahoma", Font.BOLD, 15));
        item1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1)); // Margen izquierdo para alinear con el texto del menú
        item1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item1.setIconTextGap(10);
        item1.setText("Materiales");
        item1.setColorHover(new Color(150, 150, 150)); // Mismo color de hover que los botones del menú
        item1.setColorTextHover(Color.WHITE);
        aplicarTema();

        //boton para herramientas
        rojeru_san.RSButton item2 = new rojeru_san.RSButton();
        item2.setBackground(new Color(29, 30, 81)); // Fondo igual que el menú
        item2.setForeground(Color.WHITE);
        item2.setFont(new Font("Tahoma", Font.BOLD, 15));
        item2.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1)); // Margen izquierdo para alinear con el texto del menú
        item2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
        submenuVentas.setBackground(new Color(29, 30, 81)); // Mismo color que el menú
        submenuVentas.setLayout(new java.awt.GridLayout(2, 1, 0, 0)); // 2 filas, 1 columna
        submenuVentas.setPreferredSize(new Dimension(250, 80)); // Alto de 100 (50 por cada botón)

        // Botón "Pedidos"
        item3 = new rojeru_san.RSButton();
        item3.setBackground(new Color(29, 30, 81));
        item3.setForeground(Color.WHITE);
        item3.setFont(new Font("Tahoma", Font.BOLD, 15));
        item3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1));
        item3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item3.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        item3.setIconTextGap(10);
        item3.setText("Pedidos");
        item3.setColorHover(new Color(150, 150, 150));
        item3.setColorTextHover(Color.WHITE);

        // Botón "Cotización"
        item4 = new rojeru_san.RSButton();
        item4.setBackground(new Color(29, 30, 81));
        item4.setForeground(Color.WHITE);
        item4.setFont(new Font("Tahoma", Font.BOLD, 15));
        item4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 60, 1, 1));
        item4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        item4.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
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
//                cinco.setSelected(true); // Mantener "Ventas" resaltado

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
//                cinco.setSelected(true); // Mantener "Ventas" resaltado

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
            lblUsuarioLogueado.setForeground(Color.WHITE);
            rolusuario.setForeground(Color.WHITE);
            lblTituloPrincipal.setForeground(Color.WHITE);
            cambiarEstiloBotonRS(item1, new Color(30, 30, 45), Color.WHITE);

            uno.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa.png")));
            dos.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-negra.png")));
            cuatro.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity_1.png")));

            nueve.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogar.png")));
            Diez.setIcon(new ImageIcon(getClass().getResource("/imagenes/ajustesblanco.png")));

            rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/WhatsApp Image 2025-03-20 at 3.58.18 PM (1).png")));
            rSLabelImage3.setIcon(new ImageIcon(getClass().getResource("/imagenes/luna (6).png")));
            rSLabelImage1.setIcon(new ImageIcon(getClass().getResource("/imagenes/feliz-sol-negro.png")));

            uno1.setIcon(new ImageIcon(getClass().getResource("/imagenes/casa.png")));
            dos1.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-negra.png")));
            cuatro1.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity_1.png")));

            nueve1.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogar.png")));
            rSLabelIcon1.setForeground(new Color(255, 255, 255));
            //rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/logo_blanco.png")));

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
            contenedor.setBackground(new Color(242, 247, 255));
            jPanel5.setBackground(new Color(242, 247, 255));
            lblUsuarioLogueado.setForeground(textoLabel);
            rolusuario.setForeground(textoLabel);
            lblTituloPrincipal.setForeground(textoLabel);
            item4.setForeground(fondoBoton);
            rSLabelIcon1.setForeground(new Color(0, 0, 0));
            uno.setIcon(new ImageIcon(getClass().getResource("/imagenes/home.png")));
            dos.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-blanca.png")));
            cuatro.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity.png")));

            nueve.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogarnegro.png")));
            Diez.setIcon(new ImageIcon(getClass().getResource("/imagenes/ajustess.png")));
            rSLabelImage1.setIcon(new ImageIcon(getClass().getResource("/imagenes/feliz-sol (6).png")));
            rSLabelImage3.setIcon(new ImageIcon(getClass().getResource("/imagenes/luna (7).png")));

            uno1.setIcon(new ImageIcon(getClass().getResource("/imagenes/home.png")));
            dos1.setIcon(new ImageIcon(getClass().getResource("/imagenes/caja-blanca.png")));
            cuatro1.setIcon(new ImageIcon(getClass().getResource("/imagenes/productivity.png")));

            nueve1.setIcon(new ImageIcon(getClass().getResource("/imagenes/catalogarnegro.png")));
            rSPanelImage3.setImagen(new ImageIcon(getClass().getResource("/imagenes/logo_azul.png")));
            rSPanelImage3.setPreferredSize(new Dimension(160, 109));

        }

        // Botones
        cambiarEstiloBotonRS(uno, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(dos, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(cuatro, fondoBoton, textoBoton);

        cambiarEstiloBotonRS(nueve, fondoBoton, textoBoton);
        cambiarEstiloBotonRS(Diez, fondoBoton, textoBoton);

        cambiarEstiloBotonRS(item1, new Color(30, 30, 45), Color.WHITE);

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
        Diez.setText(expanded ? "Config" : "");

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

    private void configurarPopupMenu() {
        JPopupMenu userPopupMenu = new JPopupMenu();
        userPopupMenu.setOpaque(false);
        userPopupMenu.setBackground(new Color(255, 255, 255));
        userPopupMenu.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // Crear los ítems del menú  
        JMenuItem settingsItem = new JMenuItem("Configuraciones");
        JMenuItem profileItem = new JMenuItem("Perfil");
        JMenuItem messagesItem = new JMenuItem("Mensajes");
        JMenuItem logoutItem = new JMenuItem("Salir");

        // Personalizar los ítems sin hover
        Font menuFont = new Font("Arial", Font.PLAIN, 14);
        Dimension itemSize = new Dimension(150, 30);

        settingsItem.setFont(menuFont);
        settingsItem.setForeground(new Color(100, 100, 100));
        settingsItem.setBackground(new Color(255, 255, 255));
        settingsItem.setOpaque(false);
        settingsItem.setPreferredSize(itemSize);
        settingsItem.setBorderPainted(false);  // Eliminar bordes al hacer hover

        profileItem.setFont(menuFont);
        profileItem.setForeground(new Color(100, 100, 100));
        profileItem.setBackground(new Color(255, 255, 255));
        profileItem.setOpaque(false);
        profileItem.setPreferredSize(itemSize);
        profileItem.setBorderPainted(false);

        messagesItem.setFont(menuFont);
        messagesItem.setForeground(new Color(100, 100, 100));
        messagesItem.setBackground(new Color(255, 255, 255));
        messagesItem.setOpaque(false);
        messagesItem.setPreferredSize(itemSize);
        messagesItem.setBorderPainted(false);

        logoutItem.setFont(menuFont);
        logoutItem.setForeground(new Color(100, 100, 100));
        logoutItem.setBackground(new Color(255, 255, 255));
        logoutItem.setOpaque(false);
        logoutItem.setPreferredSize(itemSize);
        logoutItem.setBorderPainted(false);

        // Agregar ítems al menú  
        userPopupMenu.add(settingsItem);
        userPopupMenu.add(profileItem);
        userPopupMenu.add(messagesItem);
        userPopupMenu.add(logoutItem);

        // Mostrar el JPopupMenu al hacer clic en rSLabelCircleImage1  
        rSLabelCircleImage1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userPopupMenu.show(rSLabelCircleImage1, 0, rSLabelCircleImage1.getHeight());
            }
        });

        // Acciones de los ítems del menú  
        settingsItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Abriendo configuración..."));
        profileItem.addActionListener(e -> {
            // Cargar la vista de InformacionBasica en el contenedor  
            perfil1 info = new perfil1(idUsuario);

            info.setSize(1290, 730);
            info.setLocation(0, 0);
            contenedor.removeAll();
            contenedor.add(info);
            contenedor.revalidate();
            contenedor.repaint();
        });
        messagesItem.addActionListener(e -> JOptionPane.showMessageDialog(null, "Abriendo mensajes..."));
        logoutItem.addActionListener(e -> System.exit(0));
    }

    private void animacion() {
        int posicion = jPanel3.getX();
        System.out.println("Posición actual: " + posicion); // Debug
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
        System.out.println("Posición actual: " + posicion); // Debug
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
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 41, 158, 153));
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
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 41, 158, 153));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 50));

            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, 50));

            submenuVentasVisible = false;

        }

        jPanel3.revalidate();
        jPanel3.repaint();
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
        rSLabelImage3 = new rojerusan.RSLabelImage();
        rSLabelIcon1 = new rojerusan.RSLabelIcon();
        rSLabelCircleImage1 = new rojerusan.RSLabelCircleImage();
        rolusuario = new javax.swing.JLabel();
        lblUsuarioLogueado = new javax.swing.JLabel();
        lblTituloPrincipal = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        uno1 = new rojeru_san.RSButton();
        dos1 = new rojeru_san.RSButton();
        cuatro1 = new rojeru_san.RSButton();
        nueve1 = new rojeru_san.RSButton();
        nueve2 = new rojeru_san.RSButton();
        jPanel3 = new javax.swing.JPanel();
        dos = new rojeru_san.RSButton();
        cuatro = new rojeru_san.RSButton();
        nueve = new rojeru_san.RSButton();
        uno = new rojeru_san.RSButton();
        rSPanelImage3 = new rojerusan.RSPanelImage();
        Diez = new rojeru_san.RSButton();

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
            .addGap(0, 810, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 68, 1316, 810));

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

        jPanel1.add(contenedor, new org.netbeans.lib.awtextra.AbsoluteConstraints(81, 68, 1491, 840));
        contenedor.getAccessibleContext().setAccessibleName("");

        jPanel2.setBackground(new java.awt.Color(29, 30, 81));

        rSLabelImage1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/feliz-sol-negro.png"))); // NOI18N

        rSSwitch1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rSSwitch1MouseClicked(evt);
            }
        });

        rSLabelImage3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luna (6).png"))); // NOI18N

        rSLabelIcon1.setBackground(new java.awt.Color(255, 255, 255));
        rSLabelIcon1.setForeground(new java.awt.Color(255, 255, 255));
        rSLabelIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.NOTIFICATIONS);

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(315, 315, 315)
                .addComponent(lblTituloPrincipal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 606, Short.MAX_VALUE)
                .addComponent(rSLabelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(rSSwitch1, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(rSLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(rSLabelIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rSLabelCircleImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblUsuarioLogueado))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rolusuario)))
                .addGap(209, 209, 209))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rSLabelCircleImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblUsuarioLogueado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rolusuario))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rSLabelImage1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rSSwitch1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rSLabelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(rSLabelIcon1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblTituloPrincipal)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 63));

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
        jPanel4.add(dos1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 40, 50));

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
        jPanel4.add(cuatro1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 40, 51));

        nueve1.setBackground(new java.awt.Color(29, 30, 81));
        nueve1.setBorder(null);
        nueve1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ajustes (1).png"))); // NOI18N
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
        jPanel4.add(nueve1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 420, 40, 52));

        nueve2.setBackground(new java.awt.Color(29, 30, 81));
        nueve2.setBorder(null);
        nueve2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/public-service_1.png"))); // NOI18N
        nueve2.setColorHover(new java.awt.Color(128, 128, 128));
        nueve2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        nueve2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nueve2MouseEntered(evt);
            }
        });
        nueve2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nueve2ActionPerformed(evt);
            }
        });
        jPanel4.add(nueve2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 40, 52));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 924));

        jPanel3.setBackground(new java.awt.Color(29, 30, 81));

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

        rSPanelImage3.setImagen(new javax.swing.ImageIcon(getClass().getResource("/logo_blanco.png"))); // NOI18N
        rSPanelImage3.setPreferredSize(new java.awt.Dimension(158, 109));

        javax.swing.GroupLayout rSPanelImage3Layout = new javax.swing.GroupLayout(rSPanelImage3);
        rSPanelImage3.setLayout(rSPanelImage3Layout);
        rSPanelImage3Layout.setHorizontalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 158, Short.MAX_VALUE)
        );
        rSPanelImage3Layout.setVerticalGroup(
            rSPanelImage3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 129, Short.MAX_VALUE)
        );

        Diez.setBackground(new java.awt.Color(29, 30, 81));
        Diez.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 30, 1, 1));
        Diez.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ajustes (1).png"))); // NOI18N
        Diez.setText("Configuracion");
        Diez.setToolTipText("");
        Diez.setColorHover(new java.awt.Color(128, 128, 128));
        Diez.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        Diez.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Diez.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DiezActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(uno, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(dos, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(nueve, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(Diez, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(cuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(rSPanelImage3, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(uno, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(dos, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cuatro, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nueve, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Diez, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(450, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 250, 924));

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

            Produccion pr = new Produccion(new javax.swing.JFrame());
            pr.setSize(1290, 730);
            pr.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(pr);
            contenedor.revalidate();
            contenedor.repaint();
            lblTituloPrincipal.setText("Producción");
        }

        animacion();

    }//GEN-LAST:event_cuatroActionPerformed

    private void unoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_unoActionPerformed
        ocultarSubmenus();
        if (!this.uno.isSelected()) {
            deseleccionar();
            this.uno.setSelected(true);

            Escritorio1 es = new Escritorio1();
            es.setSize(1090, 690);
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
            jPanel3.add(rSPanelImage3, new org.netbeans.lib.awtextra.AbsoluteConstraints(49, 41, 158, 153));
            jPanel3.add(uno, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));
            jPanel3.add(dos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, 250, 50));
            jPanel3.add(submenuInventario, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 250, 100)); // Submenú (Materiales + Herramientas)
            jPanel3.add(cuatro, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 250, 50));

            jPanel3.add(nueve, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 250, 50));

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
        if (!this.nueve.isSelected()) {
            deseleccionar();
            this.nueve.setSelected(true);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);

            // Crear y mostrar el panel de inventario
            catalogo22 cat = new catalogo22(parentFrame, false, contenedor);

            cat.setSize(1290, 730);
            cat.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(cat);
            contenedor.revalidate();
            contenedor.repaint();
            lblTituloPrincipal.setText("Gestión de Usuarios");

        }
        animacion();
    }//GEN-LAST:event_nueveActionPerformed

    private void DiezActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DiezActionPerformed
        // TODO add your handling code here:

        if (!this.Diez.isSelected()) {
            deseleccionar();
            this.Diez.setSelected(true);

            // Crear y mostrar el panel de inventario
            config cong = new config();
            cong.setSize(1290, 730);
            cong.setLocation(0, 0);

            contenedor.removeAll();
            contenedor.add(cong);
            contenedor.revalidate();
            contenedor.repaint();

        }
        animacion();
    }//GEN-LAST:event_DiezActionPerformed

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

    private void nueve1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nueve1MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve1MouseEntered

    private void nueve1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nueve1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve1ActionPerformed

    private void nueve2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_nueve2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve2MouseEntered

    private void nueve2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nueve2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nueve2ActionPerformed

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
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
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
    private rojeru_san.RSButton Diez;
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
    private javax.swing.JLabel lblTituloPrincipal;
    private javax.swing.JLabel lblUsuarioLogueado;
    private rojeru_san.RSButton nueve;
    private rojeru_san.RSButton nueve1;
    private rojeru_san.RSButton nueve2;
    private rojerusan.RSLabelCircleImage rSLabelCircleImage1;
    private rojerusan.RSLabelIcon rSLabelIcon1;
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
