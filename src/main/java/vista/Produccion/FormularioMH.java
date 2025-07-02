/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista.Produccion;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import modelo.Conexion;
import rojeru_san.RSButtonRiple;

/**
 *
 * @author SENA
 */
public class FormularioMH extends javax.swing.JDialog {

    private List<String> materialesSeleccionados;
    private List<String> herramientasSeleccionadas;
    private JPanel panelMateriales;
    private JPanel panelHerramientas;
    private boolean confirmado = false;
    private Map<String, Double> inventarioMateriales = new HashMap<>();
    private Map<String, Double> inventarioHerramientas = new HashMap<>();
    private List<String> materialesAEliminar = new ArrayList<>();
    private List<String> herramientasAEliminar = new ArrayList<>();
    private Map<String, String> cantidadesHerramientasIniciales;
    private Map<String, String> cantidadesMaterialesIniciales;

    public FormularioMH(Frame parent, boolean modal, List<String> materiales, List<String> herramientasLista) {
        super(parent, modal);
        this.materialesSeleccionados = materiales;
        this.herramientasSeleccionadas = herramientasLista;
        //System.out.println("Materiales seleccionados: " + materiales);
        //System.out.println("Herramientas seleccionadas: " + herramientasLista);
        initComponents();
        cargarInventario();
        generarCamposDinamicos();
        // Configurar JScrollPane para ContenedorH
        ContenedorH.setBackground(new java.awt.Color(255, 255, 255));
        JScrollPane scrollPaneH = new JScrollPane(ContenedorH);
        scrollPaneH.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jPanel1.add(scrollPaneH, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 360, 370));

        // Configurar JScrollPane para ContenedorM
        ContenedorM.setBackground(new java.awt.Color(255, 255, 255));
        JScrollPane scrollPaneM = new JScrollPane(ContenedorM);
        scrollPaneM.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        jPanel1.add(scrollPaneM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 360, 370));
    }

    private void cargarInventario() {
        inventarioMateriales.clear();
        inventarioHerramientas.clear();
        //System.out.println("Iniciando carga de inventario...");

        try (Connection con = Conexion.getConnection()) {
            // Cargar materiales
            String sqlMateriales = "SELECT i.nombre, i.cantidad, u.nombre AS unidad "
                    + "FROM inventario i "
                    + "JOIN unidad_medida u ON i.unidad_medida_idunidad_medida = u.idunidad_medida "
                    + "WHERE i.tipo = 'material' ";
            try (PreparedStatement ps = con.prepareStatement(sqlMateriales)) {
                ResultSet rs = ps.executeQuery();
                int materialCount = 0;
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String cantidadStr = rs.getString("cantidad").trim();
                    //System.out.println("Cantidad cruda para material " + nombre + ": '" + cantidadStr + "'");
                    double cantidad = parseCantidad(cantidadStr);
                    //System.out.println("Cantidad parseada para material " + nombre + ": " + cantidad);
                    String unidad = rs.getString("unidad");
                    String clave = nombre + "|" + unidad;
                    inventarioMateriales.put(clave, cantidad);
                    // System.out.println("Material cargado: " + clave + " -> " + cantidad);
                    materialCount++;
                }
                // System.out.println("Total materiales cargados: " + materialCount);
            }

            // Cargar herramientas
            String sqlHerramientas = "SELECT i.nombre, i.cantidad, u.nombre AS unidad "
                    + "FROM inventario i "
                    + "JOIN unidad_medida u ON i.unidad_medida_idunidad_medida = u.idunidad_medida "
                    + "WHERE i.tipo = 'herramienta' ";
            try (PreparedStatement ps = con.prepareStatement(sqlHerramientas)) {
                ResultSet rs = ps.executeQuery();
                int herramientaCount = 0;
                while (rs.next()) {
                    String nombre = rs.getString("nombre");
                    String cantidadStr = rs.getString("cantidad").trim();
                    //System.out.println("Cantidad cruda para herramienta " + nombre + ": '" + cantidadStr + "'");
                    double cantidad = parseCantidad(cantidadStr);
                    //System.out.println("Cantidad parseada para herramienta " + nombre + ": " + cantidad);
                    String unidad = rs.getString("unidad");
                    String clave = nombre + "|" + unidad;
                    inventarioHerramientas.put(clave, cantidad);
                    //System.out.println("Herramienta cargada: " + clave + " -> " + cantidad);
                    herramientaCount++;
                }
                //System.out.println("Total herramientas cargadas: " + herramientaCount);
            }
        } catch (SQLException ex) {
            Logger.getLogger(FormularioMH.class.getName()).log(Level.SEVERE, "Error al cargar inventario", ex);
            JOptionPane.showMessageDialog(this,
                    "Error al cargar el inventario: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double parseCantidad(String cantidadStr) {
        try {
            // Normalizar: eliminar puntos (separadores de miles) y reemplazar coma por punto
            String normalized = cantidadStr.replace(".", "").replace(",", ".");
            double cantidad = Double.parseDouble(normalized);
            if (cantidad < 0) {
                //System.err.println("Cantidad negativa detectada: " + cantidadStr + " -> " + cantidad);
                return 0.0;
            }
            return cantidad;
        } catch (NumberFormatException e) {
            // System.err.println("Error al parsear cantidad: '" + cantidadStr + "' - " + e.getMessage());
            return 0.0; // Valor predeterminado
        }
    }

    private void generarCamposDinamicos() {
        panelMateriales = new JPanel();
        panelMateriales.setLayout(new BoxLayout(panelMateriales, BoxLayout.Y_AXIS));
        panelMateriales.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Materiales",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 18) // Tamaño 14 y negrita
        ));
        panelMateriales.setBackground(Color.WHITE);
        panelHerramientas = new JPanel();
        panelHerramientas.setLayout(new BoxLayout(panelHerramientas, BoxLayout.Y_AXIS));
        panelHerramientas.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY),
                "Herramientas",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("Segoe UI", Font.BOLD, 18) // Tamaño 14 y negrita
        ));
        panelHerramientas.setBackground(Color.WHITE);

        // Agregar campos para materiales
        for (String material : materialesSeleccionados) {
            agregarCampoMaterial(material);
        }

        // Agregar campos para herramientas
        for (String herramienta : herramientasSeleccionadas) {
            agregarCampoHerramienta(herramienta);
        }

        // Añadir panelMateriales a ContenedorM
        ContenedorM.setLayout(new BoxLayout(ContenedorM, BoxLayout.Y_AXIS));
        ContenedorM.removeAll();
        ContenedorM.add(panelMateriales);

        // Añadir panelHerramientas a ContenedorH
        ContenedorH.setLayout(new BoxLayout(ContenedorH, BoxLayout.Y_AXIS));
        ContenedorH.removeAll();
        ContenedorH.add(panelHerramientas);

        // Forzar revalidación y repintado
        ContenedorM.revalidate();
        ContenedorM.repaint();
        ContenedorH.revalidate();
        ContenedorH.repaint();

        // Ajustar tamaño del JDialog (fijo para permitir scroll)
        this.setSize(520, 700); // Tamaño fijo suficiente para título, botones y área de scroll
        this.pack();
    }

    private void agregarCampoMaterial(String nombreMaterial) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        String claveCompleta = inventarioMateriales.keySet().stream()
                .filter(k -> k.startsWith(nombreMaterial + "|"))
                .findFirst()
                .orElse(nombreMaterial + "|unidad");

        String[] partes = claveCompleta.split("\\|");
        String nombre = partes[0];
        String unidad = partes.length > 1 ? partes[1] : "unidad";
        double stockActual = inventarioMateriales.getOrDefault(claveCompleta, 0.0);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fila.setBackground(Color.WHITE);

        JLabel label = new JLabel(String.format("<html><b>%s</b> (Stock: %s %s)</html>",
                nombre, formatter.format(stockActual), unidad));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField txtCantidad = new JTextField(formatter.format(0.0));
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCantidad.setForeground(Color.BLACK);
        txtCantidad.setPreferredSize(new Dimension(100, 30));

        ((javax.swing.text.AbstractDocument) txtCantidad.getDocument())
                .setDocumentFilter(new NumberFilter(stockActual));

        fila.add(label);
        fila.add(txtCantidad);
        panelMateriales.add(fila);
        panelMateriales.add(Box.createVerticalStrut(5));
        panelMateriales.revalidate();
        panelMateriales.repaint();
    }

    private void agregarCampoHerramienta(String nombreHerramienta) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        //System.out.println("Buscando herramienta: " + nombreHerramienta);
        String claveCompleta = inventarioHerramientas.keySet().stream()
                .filter(k -> k.startsWith(nombreHerramienta + "|"))
                .findFirst()
                .orElse(nombreHerramienta + "|unidad");
        //System.out.println("Clave encontrada: " + claveCompleta);

        String[] partes = claveCompleta.split("\\|");
        String nombre = partes[0];
        String unidad = partes.length > 1 ? partes[1] : "unidad";
        double stockActual = inventarioHerramientas.getOrDefault(claveCompleta, 0.0);
        //System.out.println("Stock actual para herramienta: " + nombre + " -> " + stockActual);

        JPanel fila = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fila.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        fila.setBackground(Color.WHITE);

        JLabel label = new JLabel(String.format("<html><b>%s</b> (Stock: %s %s)</html>",
                nombre, formatter.format(stockActual), unidad));
        label.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JTextField txtCantidad = new JTextField(formatter.format(0.0)); // Inicializar con '0,00'
        txtCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        txtCantidad.setForeground(Color.BLACK);
        txtCantidad.setPreferredSize(new Dimension(100, 30));

        ((javax.swing.text.AbstractDocument) txtCantidad.getDocument())
                .setDocumentFilter(new NumberFilter(stockActual));

        fila.add(label);
        fila.add(txtCantidad);

        panelHerramientas.add(fila);
        panelHerramientas.add(Box.createVerticalStrut(5));
        panelHerramientas.revalidate();
        panelHerramientas.repaint();
    }

    public void setCantidadesIniciales(Map<String, String> materiales, Map<String, String> herramientas) {
        this.cantidadesMaterialesIniciales = materiales;
        this.cantidadesHerramientasIniciales = herramientas;
    }

    private void actualizarCantidadesUsadas(Connection con, FormularioMH formMH, int idEtapa) throws SQLException {
        Map<String, String> cantidadesMateriales = formMH.getCantidadesMateriales();
        Map<String, String> cantidadesHerramientas = formMH.getCantidadesHerramientas();

        // 1. Para materiales
        for (Map.Entry<String, String> entry : cantidadesMateriales.entrySet()) {
            String nombre = entry.getKey();
            // Normalizar el valor reemplazando coma por punto
            String cantidadStr = entry.getValue().replace(",", ".");
            double cantidadNueva = Double.parseDouble(cantidadStr);

            // a. Obtener cantidad actual en utilizado
            String sqlSelect = "SELECT cantidad_usada FROM utilizado "
                    + "WHERE etapa_produccion_idetapa_produccion = ? "
                    + "AND inventario_id_inventario = (SELECT id_inventario FROM inventario WHERE nombre = ?)";
            try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, idEtapa);
                psSelect.setString(2, nombre);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    double cantidadActual = rs.getDouble("cantidad_usada");
                    double diferencia = cantidadActual - cantidadNueva;

                    // b. Ajustar inventario si la cantidad cambió
                    if (diferencia != 0) {
                        String sqlUpdateInventario = "UPDATE inventario SET cantidad = cantidad + ? "
                                + "WHERE nombre = ?";
                        try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateInventario)) {
                            psUpdate.setDouble(1, diferencia);
                            psUpdate.setString(2, nombre);
                            psUpdate.executeUpdate();
                        }
                    }
                }

                // c. Actualizar cantidad en utilizado
                String sqlUpdate = "UPDATE utilizado SET cantidad_usada = ? "
                        + "WHERE etapa_produccion_idetapa_produccion = ? "
                        + "AND inventario_id_inventario = (SELECT id_inventario FROM inventario WHERE nombre = ?)";
                try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                    psUpdate.setDouble(1, cantidadNueva);
                    psUpdate.setInt(2, idEtapa);
                    psUpdate.setString(3, nombre);
                    psUpdate.executeUpdate();
                }
            }
        }

        // 2. Para herramientas (código duplicado, puedes optimizarlo en un método separado si lo deseas)
        for (Map.Entry<String, String> entry : cantidadesHerramientas.entrySet()) {
            String nombre = entry.getKey();
            String cantidadStr = entry.getValue().replace(",", ".");
            double cantidadNueva = Double.parseDouble(cantidadStr);

            String sqlSelect = "SELECT cantidad_usada FROM utilizado "
                    + "WHERE etapa_produccion_idetapa_produccion = ? "
                    + "AND inventario_id_inventario = (SELECT id_inventario FROM inventario WHERE nombre = ?)";
            try (PreparedStatement psSelect = con.prepareStatement(sqlSelect)) {
                psSelect.setInt(1, idEtapa);
                psSelect.setString(2, nombre);
                ResultSet rs = psSelect.executeQuery();

                if (rs.next()) {
                    double cantidadActual = rs.getDouble("cantidad_usada");
                    double diferencia = cantidadActual - cantidadNueva;

                    if (diferencia != 0) {
                        String sqlUpdateInventario = "UPDATE inventario SET cantidad = cantidad + ? "
                                + "WHERE nombre = ?";
                        try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdateInventario)) {
                            psUpdate.setDouble(1, diferencia);
                            psUpdate.setString(2, nombre);
                            psUpdate.executeUpdate();
                        }
                    }
                }

                String sqlUpdate = "UPDATE utilizado SET cantidad_usada = ? "
                        + "WHERE etapa_produccion_idetapa_produccion = ? "
                        + "AND inventario_id_inventario = (SELECT id_inventario FROM inventario WHERE nombre = ?)";
                try (PreparedStatement psUpdate = con.prepareStatement(sqlUpdate)) {
                    psUpdate.setDouble(1, cantidadNueva);
                    psUpdate.setInt(2, idEtapa);
                    psUpdate.setString(3, nombre);
                    psUpdate.executeUpdate();
                }
            }
        }
    }

// DocumentFilter to restrict input to numbers and max quantity
    public List<String> getMaterialesAEliminar() {
        return materialesAEliminar;
    }

    public List<String> getHerramientasAEliminar() {
        return herramientasAEliminar;
    }

    private class NumberFilter extends DocumentFilter {

        private final double maxQuantity;
        private final NumberFormat numberFormat;

        public NumberFilter(double maxQuantity) {
            this.maxQuantity = maxQuantity;
            this.numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
            numberFormat.setMinimumFractionDigits(0);
            numberFormat.setMaximumFractionDigits(2);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength());
            newStr = newStr.substring(0, offset) + text + newStr.substring(offset + length);

            if (isValidInput(newStr)) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {
            String newStr = fb.getDocument().getText(0, fb.getDocument().getLength());
            newStr = newStr.substring(0, offset) + string + newStr.substring(offset);

            if (isValidInput(newStr)) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        }

        private boolean isValidInput(String input) {
            if (input.isEmpty()) {
                return true;
            }
            if (!input.matches("\\d*,?\\d{0,2}")) {
                return false;
            }
            try {
                Number parsed = numberFormat.parse(input);
                double value = parsed.doubleValue();
                return value >= 0 && value <= maxQuantity;
            } catch (ParseException e) {
                return false;
            }
        }
    }

    public boolean isConfirmado() {
        return confirmado;
    }

    public Map<String, String> getCantidadesMateriales() {
        Map<String, String> cantidades = new HashMap<>();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        for (Component comp : panelMateriales.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel fila = (JPanel) comp;
                JLabel label = (JLabel) fila.getComponent(0);
                JTextField txtCantidad = (JTextField) fila.getComponent(1);

                String textoLabel = label.getText();
                String nombreMaterial = textoLabel.split("<b>")[1].split("</b>")[0].trim();

                String cantidadStr = txtCantidad.getText().trim();
                try {
                    double cantidad = cantidadStr.isEmpty() || cantidadStr.equals("0,00") ? 0.0
                            : formatter.parse(cantidadStr).doubleValue();
                    cantidades.put(nombreMaterial, formatter.format(cantidad));
                } catch (ParseException e) {
                    cantidades.put(nombreMaterial, "");
                }
            }
        }
        return cantidades;
    }

    public Map<String, String> getCantidadesHerramientas() {
        Map<String, String> cantidades = new HashMap<>();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
        formatter.setMinimumFractionDigits(2);
        formatter.setMaximumFractionDigits(2);

        for (Component comp : panelHerramientas.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel fila = (JPanel) comp;
                JLabel label = (JLabel) fila.getComponent(0);
                JTextField txtCantidad = (JTextField) fila.getComponent(1);

                String textoLabel = label.getText();
                String nombreHerramienta = textoLabel.split("<b>")[1].split("</b>")[0].trim();

                String cantidadStr = txtCantidad.getText().trim();
                try {
                    double cantidad = cantidadStr.isEmpty() || cantidadStr.equals("0,00") ? 0.0
                            : formatter.parse(cantidadStr).doubleValue();
                    cantidades.put(nombreHerramienta, formatter.format(cantidad));
                } catch (ParseException e) {
                    cantidades.put(nombreHerramienta, "");
                }
            }
        }
        return cantidades;
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
        btnGuardar1 = new rojeru_san.RSButtonRiple();
        btnCancelar1 = new rojeru_san.RSButtonRiple();
        ContenedorM = new javax.swing.JPanel();
        ContenedorH = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(46, 49, 82));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Century751 BT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Etapa Produccion");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 750, 50));

        btnGuardar1.setBackground(new java.awt.Color(46, 49, 82));
        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        btnGuardar1.setText("Guardar");
        btnGuardar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnGuardar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnGuardar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 460, 140, -1));

        btnCancelar1.setBackground(new java.awt.Color(46, 49, 82));
        btnCancelar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/salida (1).png"))); // NOI18N
        btnCancelar1.setText("Volver");
        btnCancelar1.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 18)); // NOI18N
        btnCancelar1.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        btnCancelar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelar1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnCancelar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, 140, -1));

        ContenedorM.setBackground(new java.awt.Color(255, 255, 255));
        ContenedorM.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ContenedorMLayout = new javax.swing.GroupLayout(ContenedorM);
        ContenedorM.setLayout(ContenedorMLayout);
        ContenedorMLayout.setHorizontalGroup(
            ContenedorMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        ContenedorMLayout.setVerticalGroup(
            ContenedorMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        jPanel1.add(ContenedorM, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 360, 370));

        ContenedorH.setBackground(new java.awt.Color(255, 255, 255));
        ContenedorH.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout ContenedorHLayout = new javax.swing.GroupLayout(ContenedorH);
        ContenedorH.setLayout(ContenedorHLayout);
        ContenedorHLayout.setHorizontalGroup(
            ContenedorHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        ContenedorHLayout.setVerticalGroup(
            ContenedorHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );

        jPanel1.add(ContenedorH, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, 360, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        try {
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);

            // Validar cantidades de materiales
            for (Component comp : panelMateriales.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel fila = (JPanel) comp;
                    JLabel label = (JLabel) fila.getComponent(0);
                    JTextField txtCantidad = (JTextField) fila.getComponent(1);

                    String textoLabel = label.getText();
                    String nombreMaterial = textoLabel.split("<b>")[1].split("</b>")[0].trim();
                    double stockActual = numberFormat.parse(
                            textoLabel.split("Stock: ")[1].split("\\s")[0]).doubleValue();
                    String cantidadStr = txtCantidad.getText().trim();
                    double cantidad = cantidadStr.isEmpty() || cantidadStr.equals("0,00") ? 0.0
                            : numberFormat.parse(cantidadStr).doubleValue();

                    if (cantidad > stockActual) {
                        new Error_guardar(
                                (Frame) SwingUtilities.getWindowAncestor(this),
                                true,
                                "Error",
                                "La cantidad de " + nombreMaterial + " excede el stock disponible ("
                                + numberFormat.format(stockActual) + ")"
                        ).setVisible(true);
                        return;
                    }

                    if (cantidad < 0) {
                        new Error_guardar(
                                (Frame) SwingUtilities.getWindowAncestor(this),
                                true,
                                "Error",
                                "La cantidad de " + nombreMaterial + " no puede ser negativa"
                        ).setVisible(true);
                        return;
                    }
                }
            }

            // Validar cantidades de herramientas
            for (Component comp : panelHerramientas.getComponents()) {
                if (comp instanceof JPanel) {
                    JPanel fila = (JPanel) comp;
                    JLabel label = (JLabel) fila.getComponent(0);
                    JTextField txtCantidad = (JTextField) fila.getComponent(1);

                    String textoLabel = label.getText();
                    String nombreHerramienta = textoLabel.split("<b>")[1].split("</b>")[0].trim();
                    double stockActual = numberFormat.parse(
                            textoLabel.split("Stock: ")[1].split("\\s")[0]).doubleValue();
                    String cantidadStr = txtCantidad.getText().trim();
                    double cantidad = cantidadStr.isEmpty() || cantidadStr.equals("0,00") ? 0.0
                            : numberFormat.parse(cantidadStr).doubleValue();

                    if (cantidad > stockActual) {
                        JOptionPane.showMessageDialog(this,
                                "La cantidad de " + nombreHerramienta + " excede el stock disponible ("
                                + numberFormat.format(stockActual) + ")",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (cantidad < 0) {
                        JOptionPane.showMessageDialog(this,
                                "La cantidad de " + nombreHerramienta + " no puede ser negativa",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            confirmado = true;
            this.dispose();
        } catch (ParseException | NumberFormatException e) {
            new Error_guardar(
                    (Frame) SwingUtilities.getWindowAncestor(this),
                    true,
                    "Error",
                    "Ingrese valores numéricos válidos (ej: 10,50)"
            ).setVisible(true);
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnGuardar1ActionPerformed

    public Map<String, Double> getMaterialesActualizados() {
        Map<String, Double> materiales = new HashMap<>();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));

        for (Component comp : panelMateriales.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel fila = (JPanel) comp;
                JLabel label = (JLabel) fila.getComponent(0);
                JTextField txtCantidad = (JTextField) fila.getComponent(1);

                String nombre = label.getText().split("<b>")[1].split("</b>")[0].trim();
                try {
                    double cantidad = formatter.parse(txtCantidad.getText()).doubleValue();
                    materiales.put(nombre, cantidad);
                } catch (ParseException e) {
                    materiales.put(nombre, 0.0);
                }
            }
        }
        return materiales;
    }

    public Map<String, Double> getHerramientasActualizadas() {
        Map<String, Double> herramientas = new HashMap<>();
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.forLanguageTag("es-ES"));

        for (Component comp : panelHerramientas.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel fila = (JPanel) comp;
                JLabel label = (JLabel) fila.getComponent(0);
                JTextField txtCantidad = (JTextField) fila.getComponent(1);

                String nombre = label.getText().split("<b>")[1].split("</b>")[0].trim();
                try {
                    double cantidad = formatter.parse(txtCantidad.getText()).doubleValue();
                    herramientas.put(nombre, cantidad);
                } catch (ParseException e) {
                    herramientas.put(nombre, 0.0);
                }
            }
        }
        return herramientas;
    }
    private boolean volverPresionado = false;

    public boolean isVolverPresionado() {
        return volverPresionado;
    }
    private void btnCancelar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelar1ActionPerformed
        volverPresionado = true;
        this.setVisible(false);
    }//GEN-LAST:event_btnCancelar1ActionPerformed

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
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(FormularioMH.class.getName()).log(Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            List<String> materiales = new ArrayList<>();
            materiales.add("Material1");
            materiales.add("Material2");
            List<String> herramientas = new ArrayList<>();
            herramientas.add("Herramienta1");
            herramientas.add("Herramienta2");

            FormularioMH dialog = new FormularioMH(new javax.swing.JFrame(), true, materiales, herramientas);
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
    private javax.swing.JPanel ContenedorH;
    private javax.swing.JPanel ContenedorM;
    private rojeru_san.RSButtonRiple btnCancelar1;
    private rojeru_san.RSButtonRiple btnGuardar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
