/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista;

import controlador.Ctrl_Perfil;
import java.awt.Frame;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelo.UsuarioModelo;

/**
 *
 * @author ZenBook
 */
public class crear_usuario extends javax.swing.JDialog {

    private String[] datos; // Almacena los datos ingresados
    private boolean guardado = false; // Indica si se presionó "Guardar"

    private String correoIngresado = "";
    private boolean isPasswordVisible = false;
    private boolean isPasswordVisible1 = false; // Para el segundo campo
    private JPanel tooltip;
    private ImageIcon eyeOpenIcon;
    private ImageIcon eyeClosedIcon;

    /**
     * Creates new form nuevoMateriales
     */
    public crear_usuario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        // Cargar el ícono del ojo
        URL iconUrl = getClass().getResource("/imagenes/ojo.png");
        if (iconUrl == null) {
            System.err.println("❌ No se encontró /imagenes/ojo.png");
        } else {
            System.out.println("✅ Imagen encontrada: " + iconUrl);
            ImageIcon icono = new ImageIcon(iconUrl);
            jLabel1.setIcon(icono); // ← Asegúrate que jLabel1 existe y es donde va el ícono
        }
        configurarFiltroTexto();
        setTitle("Nuevo Material");
        agregarValidacion();

        // Ocultar etiquetas de error
        tipoidentificacion.setVisible(false);
        tipoidentificacion1.setVisible(false);
        tipoidentificacion2.setVisible(false);
        tipoidentificacion3.setVisible(false);
        tipoidentificacion4.setVisible(false);
        tipoidentificacion5.setVisible(false);
        tipoidentificacion6.setVisible(false);
        tipoidentificacion7.setVisible(false);
        tipoidentificacion8.setVisible(false);
        tipoidentificacion9.setVisible(false);
    }

    public String[] getDatos() {
        return datos;
    }

    // Método para verificar si se presionó "Guardar"
    public boolean isGuardado() {
        return guardado;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnGuardar1 = new rojeru_san.RSButtonRiple();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        nombretxt = new RSMaterialComponent.RSTextFieldMaterial();
        btnCancelar = new rojeru_san.RSButtonRiple();
        btnGuardar = new rojeru_san.RSButtonRiple();
        usuariotxt = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel10 = new javax.swing.JLabel();
        apellidotxt = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel8 = new javax.swing.JLabel();
        telefonotxt = new RSMaterialComponent.RSTextFieldMaterial();
        jLabel9 = new javax.swing.JLabel();
        roltxt = new RSMaterialComponent.RSComboBoxMaterial();
        jLabel11 = new javax.swing.JLabel();
        correotxt = new RSMaterialComponent.RSTextFieldMaterial();
        tipoidentificacion = new javax.swing.JLabel();
        contrasenatxt = new RSMaterialComponent.RSPasswordIconOne();
        txtcontrasenanueva1 = new RSMaterialComponent.RSPasswordIconOne();
        jLabel12 = new javax.swing.JLabel();
        tipoidentificacion1 = new javax.swing.JLabel();
        tipoidentificacion2 = new javax.swing.JLabel();
        tipoidentificacion3 = new javax.swing.JLabel();
        tipoidentificacion4 = new javax.swing.JLabel();
        tipoidentificacion5 = new javax.swing.JLabel();
        tipoidentificacion6 = new javax.swing.JLabel();
        tipoidentificacion7 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        identificaciontxt = new RSMaterialComponent.RSComboBoxMaterial();
        numerotxt = new RSMaterialComponent.RSTextFieldMaterial();
        tipoidentificacion8 = new javax.swing.JLabel();
        tipoidentificacion9 = new javax.swing.JLabel();
        rSButtonMaterialRippleIcon2 = new RSMaterialComponent.RSButtonMaterialRippleIcon();
        rSButtonMaterialRippleIcon1 = new RSMaterialComponent.RSButtonMaterialRippleIcon();
        jLabel24 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(610, 530));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Crear Usuario");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        btnGuardar1.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x.png"))); // NOI18N
        btnGuardar1.setColorHover(new java.awt.Color(204, 0, 0));
        btnGuardar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnGuardar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 0, 40, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 610, 70));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel4.setText("Nombre:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, 20));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel6.setText("Usuario:");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel7.setText("Correo Electronico:");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));

        nombretxt.setForeground(new java.awt.Color(0, 0, 0));
        nombretxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        nombretxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        nombretxt.setPhColor(new java.awt.Color(0, 0, 0));
        nombretxt.setPlaceholder("Ingrese el nombre...");
        nombretxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        nombretxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nombretxtActionPerformed(evt);
            }
        });
        jPanel1.add(nombretxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 210, 230, 30));

        btnCancelar.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar.setText("Cancelar");
        btnCancelar.setColorHover(new java.awt.Color(204, 0, 0));
        btnCancelar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 590, 140, -1));

        btnGuardar.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar.setText("Guardar");
        btnGuardar.setColorHover(new java.awt.Color(0, 153, 51));
        btnGuardar.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 590, 140, -1));

        usuariotxt.setForeground(new java.awt.Color(0, 0, 0));
        usuariotxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        usuariotxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        usuariotxt.setPhColor(new java.awt.Color(0, 0, 0));
        usuariotxt.setPlaceholder("Ingrese el usuario...");
        usuariotxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        usuariotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                usuariotxtActionPerformed(evt);
            }
        });
        jPanel1.add(usuariotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 230, 30));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel10.setText("Apellido:");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 180, -1, 20));

        apellidotxt.setForeground(new java.awt.Color(0, 0, 0));
        apellidotxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        apellidotxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        apellidotxt.setPhColor(new java.awt.Color(0, 0, 0));
        apellidotxt.setPlaceholder("Ingrese el apellido");
        apellidotxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        apellidotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apellidotxtActionPerformed(evt);
            }
        });
        jPanel1.add(apellidotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 240, 30));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel8.setText("Telefono:");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 450, -1, -1));

        telefonotxt.setForeground(new java.awt.Color(0, 0, 0));
        telefonotxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        telefonotxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        telefonotxt.setPhColor(new java.awt.Color(0, 0, 0));
        telefonotxt.setPlaceholder("ejem 3227408892");
        telefonotxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        telefonotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                telefonotxtActionPerformed(evt);
            }
        });
        jPanel1.add(telefonotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 480, 240, 30));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel9.setText("Rol:");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 450, -1, -1));

        roltxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Escoja el rol:", "Administrador", "Trabajador", "Contador" }));
        roltxt.setColorMaterial(new java.awt.Color(29, 30, 51));
        roltxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jPanel1.add(roltxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 480, 230, 30));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel11.setText("Nueva Contraseña:");
        jPanel1.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 350, -1, -1));

        correotxt.setForeground(new java.awt.Color(0, 0, 0));
        correotxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        correotxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        correotxt.setPhColor(new java.awt.Color(0, 0, 0));
        correotxt.setPlaceholder("Ingrese el correo electronico...");
        correotxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        correotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                correotxtActionPerformed(evt);
            }
        });
        jPanel1.add(correotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 230, 30));

        tipoidentificacion.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion.setText("TIPO");
        jPanel1.add(tipoidentificacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 520, 230, -1));

        contrasenatxt.setForeground(new java.awt.Color(0, 0, 0));
        contrasenatxt.setToolTipText("");
        contrasenatxt.setBorderColor(new java.awt.Color(230, 230, 230));
        contrasenatxt.setColorIcon(new java.awt.Color(204, 204, 204));
        contrasenatxt.setPhColor(new java.awt.Color(51, 51, 51));
        contrasenatxt.setPlaceholder("Ingrese su contraseña");
        contrasenatxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contrasenatxtActionPerformed(evt);
            }
        });
        jPanel1.add(contrasenatxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 290, 230, 40));

        txtcontrasenanueva1.setForeground(new java.awt.Color(0, 0, 0));
        txtcontrasenanueva1.setToolTipText("");
        txtcontrasenanueva1.setBorderColor(new java.awt.Color(230, 230, 230));
        txtcontrasenanueva1.setColorIcon(new java.awt.Color(204, 204, 204));
        txtcontrasenanueva1.setPhColor(new java.awt.Color(51, 51, 51));
        txtcontrasenanueva1.setPlaceholder("Ingrese su contraseña");
        txtcontrasenanueva1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtcontrasenanueva1ActionPerformed(evt);
            }
        });
        jPanel1.add(txtcontrasenanueva1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 380, 230, 40));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel12.setText("Contraseña:");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, -1, -1));

        tipoidentificacion1.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion1.setText("TIPO");
        jPanel1.add(tipoidentificacion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 200, -1));

        tipoidentificacion2.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion2.setText("TIPO");
        jPanel1.add(tipoidentificacion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 330, 200, -1));

        tipoidentificacion3.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion3.setText("TIPO");
        jPanel1.add(tipoidentificacion3, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 240, 240, -1));

        tipoidentificacion4.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion4.setText("TIPO");
        jPanel1.add(tipoidentificacion4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 520, 210, -1));

        tipoidentificacion5.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion5.setText("TIPO");
        jPanel1.add(tipoidentificacion5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 420, 200, -1));

        tipoidentificacion6.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion6.setText("TIPO");
        jPanel1.add(tipoidentificacion6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 420, 240, -1));

        tipoidentificacion7.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion7.setText("TIPO");
        jPanel1.add(tipoidentificacion7, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 330, 240, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 51, 51));
        jLabel14.setText("*");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 260, 20, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 51, 51));
        jLabel15.setText("*");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 180, 60, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 51, 51));
        jLabel16.setText("*");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 180, 60, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 51, 51));
        jLabel17.setText("*");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 260, 60, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 51, 51));
        jLabel18.setText("*");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 360, 60, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 51, 51));
        jLabel19.setText("*");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 450, 20, -1));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 51, 51));
        jLabel20.setText("*");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 350, 20, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel5.setText("Tipo de documento:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, 20));

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 51, 51));
        jLabel22.setText("*");
        jPanel1.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 100, 60, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 15)); // NOI18N
        jLabel13.setText("Número:");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 100, -1, 20));

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 51, 51));
        jLabel23.setText("*");
        jPanel1.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 100, 60, -1));

        identificaciontxt.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "tipo de identificación", "CC", "TI", "CE", "NIT" }));
        identificaciontxt.setColorMaterial(new java.awt.Color(29, 30, 51));
        identificaciontxt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        identificaciontxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                identificaciontxtActionPerformed(evt);
            }
        });
        jPanel1.add(identificaciontxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 200, 30));

        numerotxt.setForeground(new java.awt.Color(0, 0, 0));
        numerotxt.setColorMaterial(new java.awt.Color(0, 0, 0));
        numerotxt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        numerotxt.setPhColor(new java.awt.Color(0, 0, 0));
        numerotxt.setPlaceholder("Ingrese el número");
        numerotxt.setSelectionColor(new java.awt.Color(0, 0, 0));
        numerotxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numerotxtActionPerformed(evt);
            }
        });
        jPanel1.add(numerotxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 130, 240, 30));

        tipoidentificacion8.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion8.setText("TIPO");
        jPanel1.add(tipoidentificacion8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 200, -1));

        tipoidentificacion9.setForeground(new java.awt.Color(255, 51, 51));
        tipoidentificacion9.setText("TIPO");
        jPanel1.add(tipoidentificacion9, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, 200, -1));

        rSButtonMaterialRippleIcon2.setBackground(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon2.setForeground(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon2.setBackgroundHover(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon2.setForegroundHover(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon2.setForegroundIcon(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon2.setForegroundIconHover(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon2.setForegroundText(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon2.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY);
        rSButtonMaterialRippleIcon2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonMaterialRippleIcon2ActionPerformed(evt);
            }
        });
        jPanel1.add(rSButtonMaterialRippleIcon2, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 290, 40, 40));

        rSButtonMaterialRippleIcon1.setBackground(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon1.setForeground(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon1.setBackgroundHover(new java.awt.Color(242, 247, 255));
        rSButtonMaterialRippleIcon1.setForegroundHover(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon1.setForegroundIcon(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon1.setForegroundIconHover(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon1.setForegroundText(new java.awt.Color(0, 0, 0));
        rSButtonMaterialRippleIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY);
        rSButtonMaterialRippleIcon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSButtonMaterialRippleIcon1ActionPerformed(evt);
            }
        });
        jPanel1.add(rSButtonMaterialRippleIcon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, 40, 40));

        jLabel24.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 51, 51));
        jLabel24.setText("*");
        jPanel1.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 450, 20, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nombretxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nombretxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nombretxtActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
    Ctrl_Perfil controladorPerfil = new Ctrl_Perfil();
    StringBuilder errores = new StringBuilder();

    // Obtener valores de los campos
    String nombre = nombretxt.getText().trim();
    String apellido = apellidotxt.getText().trim();
    String usuario = usuariotxt.getText().trim();
    String correo = correotxt.getText().trim();
    String telefono = telefonotxt.getText().trim();
    String contrasena = new String(contrasenatxt.getPassword()).trim();
    String contrasenaConfirm = new String(txtcontrasenanueva1.getPassword()).trim();
    String rol = roltxt.getSelectedItem().toString();
    String tipoIdentificacion = identificaciontxt.getSelectedItem().toString();
    String numeroIdentificacion = numerotxt.getText().trim();

    // Validaciones
    if (nombre.isEmpty()) {
        errores.append("El nombre es obligatorio.\n");
    }
    if (apellido.isEmpty()) {
        errores.append("El apellido es obligatorio.\n");
    }
    if (usuario.isEmpty()) {
        errores.append("El usuario es obligatorio.\n");
    }
    if (correo.isEmpty()) {
        errores.append("El correo electrónico es obligatorio.\n");
    }
    if (contrasena.isEmpty()) {
        errores.append("La contraseña es obligatoria.\n");
    }
    if (rol.equals("Escoja el rol:")) {
        errores.append("Debe seleccionar un rol.\n");
    }
    if (tipoIdentificacion.equals("tipo de identificación")) {
        errores.append("Debe seleccionar un tipo de identificación.\n");
    }
    if (numeroIdentificacion.isEmpty()) {
        errores.append("El número de identificación es obligatorio.\n");
    }

    // Validaciones adicionales
    if (!correo.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
        errores.append("El correo electrónico no es válido.\n");
    }
    if (!telefono.isEmpty() && !telefono.matches("\\d{7,15}")) {
        errores.append("El teléfono debe contener solo números y tener entre 7 y 15 dígitos.\n");
    }
    if (!esContrasenaValida(contrasena)) {
        errores.append("La contraseña debe tener al menos 8 caracteres, incluir mayúsculas, minúsculas, números y caracteres especiales (@$!%*?&._-).\n");
    }
    if (!contrasena.equals(contrasenaConfirm)) {
        errores.append("Las contraseñas no coinciden.\n");
    }
    if (!numeroIdentificacion.matches("\\d+")) {
        errores.append("El número de identificación debe contener solo números.\n");
    }
    if (controladorPerfil.existeUsuario(usuario)) {
        errores.append("El nombre de usuario ya existe. Por favor, elige otro.\n");
    }
    if (controladorPerfil.existeIdUsuario(numeroIdentificacion)) {
        errores.append("El número de identificación ya está registrado.\n");
    }
    
    // Validar correo único
    if (controladorPerfil.existeCorreo(correo, 0)) { // 0 porque es un nuevo usuario
        errores.append("El correo electrónico ya está registrado.\n");
    }

    // Mostrar errores si los hay
    if (errores.length() > 0) {
        JOptionPane.showMessageDialog(this, errores.toString(), "Errores de Validación", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Resto del código para guardar el usuario...
    // Crear y configurar el modelo de usuario
    UsuarioModelo nuevoUsuario = new UsuarioModelo();
    nuevoUsuario.setNombre(nombre);
    nuevoUsuario.setApellido(apellido);
    nuevoUsuario.setUsuario(usuario);
    nuevoUsuario.setCorreo_electronico(correo);
    nuevoUsuario.setContrasena(contrasena);
    nuevoUsuario.setTelefono(telefono);
    nuevoUsuario.setRol(rol);
    nuevoUsuario.setTipodeiden(tipoIdentificacion);
    try {
        nuevoUsuario.setId_usuario(Integer.parseInt(numeroIdentificacion));
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "El número de identificación debe ser un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Guardar el usuario
    if (controladorPerfil.guardar(nuevoUsuario)) {
        JOptionPane.showMessageDialog(this, "Usuario guardado correctamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        guardado = true;
        datos = new String[]{nombre, apellido, usuario, correo, telefono, rol, tipoIdentificacion, numeroIdentificacion};
        dispose();
    } else {
        JOptionPane.showMessageDialog(this, "Error al guardar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
    }



    }//GEN-LAST:event_btnGuardarActionPerformed

    private void usuariotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_usuariotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_usuariotxtActionPerformed

    private void apellidotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_apellidotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_apellidotxtActionPerformed

    private void telefonotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_telefonotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_telefonotxtActionPerformed

    private void correotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_correotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_correotxtActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void contrasenatxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contrasenatxtActionPerformed

    }//GEN-LAST:event_contrasenatxtActionPerformed

    private void txtcontrasenanueva1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtcontrasenanueva1ActionPerformed

    }//GEN-LAST:event_txtcontrasenanueva1ActionPerformed

    private void identificaciontxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_identificaciontxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_identificaciontxtActionPerformed

    private void numerotxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numerotxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numerotxtActionPerformed

    private void rSButtonMaterialRippleIcon2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonMaterialRippleIcon2ActionPerformed
        togglePasswordVisibility();
    }//GEN-LAST:event_rSButtonMaterialRippleIcon2ActionPerformed

    private void rSButtonMaterialRippleIcon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSButtonMaterialRippleIcon1ActionPerformed
        togglePasswordVisibility1();
    }//GEN-LAST:event_rSButtonMaterialRippleIcon1ActionPerformed

    /**
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
            java.util.logging.Logger.getLogger(crear_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(crear_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(crear_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(crear_usuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                crear_usuario dialog = new crear_usuario(new javax.swing.JFrame(), true);
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
    private RSMaterialComponent.RSTextFieldMaterial apellidotxt;
    private rojeru_san.RSButtonRiple btnCancelar;
    private rojeru_san.RSButtonRiple btnGuardar;
    private rojeru_san.RSButtonRiple btnGuardar1;
    private RSMaterialComponent.RSPasswordIconOne contrasenatxt;
    private RSMaterialComponent.RSTextFieldMaterial correotxt;
    private RSMaterialComponent.RSComboBoxMaterial identificaciontxt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private RSMaterialComponent.RSTextFieldMaterial nombretxt;
    private RSMaterialComponent.RSTextFieldMaterial numerotxt;
    private RSMaterialComponent.RSButtonMaterialRippleIcon rSButtonMaterialRippleIcon1;
    private RSMaterialComponent.RSButtonMaterialRippleIcon rSButtonMaterialRippleIcon2;
    private RSMaterialComponent.RSComboBoxMaterial roltxt;
    private RSMaterialComponent.RSTextFieldMaterial telefonotxt;
    private javax.swing.JLabel tipoidentificacion;
    private javax.swing.JLabel tipoidentificacion1;
    private javax.swing.JLabel tipoidentificacion2;
    private javax.swing.JLabel tipoidentificacion3;
    private javax.swing.JLabel tipoidentificacion4;
    private javax.swing.JLabel tipoidentificacion5;
    private javax.swing.JLabel tipoidentificacion6;
    private javax.swing.JLabel tipoidentificacion7;
    private javax.swing.JLabel tipoidentificacion8;
    private javax.swing.JLabel tipoidentificacion9;
    private RSMaterialComponent.RSPasswordIconOne txtcontrasenanueva1;
    private RSMaterialComponent.RSTextFieldMaterial usuariotxt;
    // End of variables declaration//GEN-END:variables
public boolean esContrasenaValida(String contrasena) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&._-])[A-Za-z\\d@$!%*?&._-]{8,}$";
        return contrasena.matches(regex);
    }

    private void agregarValidacion() {
        // Validación para identificaciontxt
        identificaciontxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (identificaciontxt.getSelectedItem().toString().equals("tipo de identificación")) {
                    tipoidentificacion8.setVisible(true);
                    tipoidentificacion8.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion8.setVisible(false);
                    tipoidentificacion8.setText("TIPO");
                }
            }
        });
        identificaciontxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    numerotxt.requestFocusInWindow();
                }
            }
        });

        // Validación para numerotxt
        numerotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String texto = numerotxt.getText().trim();
                if (texto.isEmpty()) {
                    tipoidentificacion9.setVisible(true);
                    tipoidentificacion9.setText("Este campo es obligatorio");
                } else if (!texto.matches("\\d+")) {
                    tipoidentificacion9.setVisible(true);
                    tipoidentificacion9.setText("Solo números");
                } else {
                    tipoidentificacion9.setVisible(false);
                    tipoidentificacion9.setText("TIPO");
                }
            }
        });
        numerotxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != KeyEvent.VK_BACK_SPACE && c != KeyEvent.VK_DELETE) {
                    e.consume();
                }
                if (!numerotxt.getText().trim().isEmpty()) {
                    tipoidentificacion9.setVisible(false);
                    tipoidentificacion9.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    nombretxt.requestFocusInWindow();
                }
            }
        });

        // Validación para nombretxt
        nombretxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (nombretxt.getText().trim().isEmpty()) {
                    tipoidentificacion1.setVisible(true);
                    tipoidentificacion1.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion1.setVisible(false);
                    tipoidentificacion1.setText("TIPO");
                }
            }
        });
        nombretxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!nombretxt.getText().trim().isEmpty()) {
                    tipoidentificacion1.setVisible(false);
                    tipoidentificacion1.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    apellidotxt.requestFocusInWindow();
                }
            }
        });

        // Validación para apellidotxt
        apellidotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (apellidotxt.getText().trim().isEmpty()) {
                    tipoidentificacion3.setVisible(true);
                    tipoidentificacion3.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion3.setVisible(false);
                    tipoidentificacion3.setText("TIPO");
                }
            }
        });
        apellidotxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!apellidotxt.getText().trim().isEmpty()) {
                    tipoidentificacion3.setVisible(false);
                    tipoidentificacion3.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    usuariotxt.requestFocusInWindow();
                }
            }
        });

        // Validación para usuariotxt
        usuariotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (usuariotxt.getText().trim().isEmpty()) {
                    tipoidentificacion2.setVisible(true);
                    tipoidentificacion2.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion2.setVisible(false);
                    tipoidentificacion2.setText("TIPO");
                }
            }
        });
        usuariotxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!usuariotxt.getText().trim().isEmpty()) {
                    tipoidentificacion2.setVisible(false);
                    tipoidentificacion2.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    contrasenatxt.requestFocusInWindow();
                }
            }
        });

        // Validación para contrasenatxt
        contrasenatxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(contrasenatxt.getPassword()).trim().isEmpty()) {
                    tipoidentificacion7.setVisible(true);
                    tipoidentificacion7.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion7.setVisible(false);
                    tipoidentificacion7.setText("TIPO");
                }
            }
        });
        contrasenatxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!new String(contrasenatxt.getPassword()).trim().isEmpty()) {
                    tipoidentificacion7.setVisible(false);
                    tipoidentificacion7.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtcontrasenanueva1.requestFocusInWindow();
                }
            }
        });

        // Validación para txtcontrasenanueva1
        txtcontrasenanueva1.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (new String(txtcontrasenanueva1.getPassword()).trim().isEmpty()) {
                    tipoidentificacion6.setVisible(true);
                    tipoidentificacion6.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion6.setVisible(false);
                    tipoidentificacion6.setText("TIPO");
                }
            }
        });
        txtcontrasenanueva1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!new String(txtcontrasenanueva1.getPassword()).trim().isEmpty()) {
                    tipoidentificacion6.setVisible(false);
                    tipoidentificacion6.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    correotxt.requestFocusInWindow();
                }
            }
        });

        // Validación para correotxt
        correotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (correotxt.getText().trim().isEmpty()) {
                    tipoidentificacion5.setVisible(true);
                    tipoidentificacion5.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion5.setVisible(false);
                    tipoidentificacion5.setText("TIPO");
                }
            }
        });
        correotxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (!correotxt.getText().trim().isEmpty()) {
                    tipoidentificacion5.setVisible(false);
                    tipoidentificacion5.setText("TIPO");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    roltxt.requestFocusInWindow();
                }
            }
        });

        // Validación para roltxt
        roltxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                if (roltxt.getSelectedItem().toString().equals("Escoja el rol:")) {
                    tipoidentificacion4.setVisible(true);
                    tipoidentificacion4.setText("Este campo es obligatorio");
                } else {
                    tipoidentificacion4.setVisible(false);
                    tipoidentificacion4.setText("TIPO");
                }
            }
        });
        roltxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    telefonotxt.requestFocusInWindow();
                }
            }
        });

        // Validación para telefonotxt (opcional)
        telefonotxt.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // No se requiere validación ya que es opcional
            }
        });
        telefonotxt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btnGuardar.doClick();
                }
            }
        });
    }

//private void togglePasswordVisibility() {
//        if (isPasswordVisible) {
//            contrasenatxt.setEchoChar('•');
//            btnVer.setIcon(eyeClosedIcon);
//        } else {
//            contrasenatxt.setEchoChar((char) 0);
//            btnVer.setIcon(eyeOpenIcon);
//        }
//        isPasswordVisible = !isPasswordVisible;
//    }
//
//    private void togglePasswordVisibility1() {
//        if (isPasswordVisible1) {
//            txtcontrasenanueva1.setEchoChar('•');
//            btnVer1.setIcon(eyeClosedIcon);
//        } else {
//            txtcontrasenanueva1.setEchoChar((char) 0);
//            btnVer1.setIcon(eyeOpenIcon);
//        }
//        isPasswordVisible1 = !isPasswordVisible1;
//    }
    private void configurarFiltroTexto() {
        ((AbstractDocument) nombretxt.getDocument()).setDocumentFilter(new LetterFilter());
        ((AbstractDocument) apellidotxt.getDocument()).setDocumentFilter(new LetterFilter());

        // También puedes agregar un tooltip para informar al usuario
        nombretxt.setToolTipText("Este campo solo acepta letras y espacios");
        apellidotxt.setToolTipText("Este campo solo acepta letras y espacios");

    }

    public class LetterFilter extends DocumentFilter {

        @Override
        public void insertString(DocumentFilter.FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            if (string == null) {
                return;
            }

            if (string.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            if (text == null) {
                return;
            }

            if (text.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
    
    private void togglePasswordVisibility() {
    if (isPasswordVisible) {
        contrasenatxt.setEchoChar('*'); // Ocultar con '*'
        rSButtonMaterialRippleIcon2.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY); // Ícono de ojo visible
    } else {
        contrasenatxt.setEchoChar((char) 0); // Mostrar como texto plano
        rSButtonMaterialRippleIcon2.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY_OFF); // Ícono de ojo tachado
    }
    isPasswordVisible = !isPasswordVisible;
}

// Método para alternar visibilidad de la segunda contraseña
private void togglePasswordVisibility1() {
    if (isPasswordVisible1) {
        txtcontrasenanueva1.setEchoChar('*'); // Ocultar con '*'
        rSButtonMaterialRippleIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY); // Ícono de ojo visible
    } else {
        txtcontrasenanueva1.setEchoChar((char) 0); // Mostrar como texto plano
        rSButtonMaterialRippleIcon1.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.VISIBILITY_OFF); // Ícono de ojo tachado
    }
    isPasswordVisible1 = !isPasswordVisible1;
}
}
