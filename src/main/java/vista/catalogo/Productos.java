package vista.catalogo;

import controlador.Ctrl_productocatalogo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import modelo.catalogoproducto;
import newscomponents.RSPanelEffect;
import rojeru_san.efectos.ValoresEnum;
import rojerusan.RSLabelIcon;
import vista.TemaManager;

import vista.alertas.alertaEliminar1;
import vista.alertas.borradooconexito;

/**
 * c
 *
 * @author buitr
 */
public class Productos extends javax.swing.JPanel {

    private JScrollPane scrollPane;
    private int idCategoria; // Guardar el ID de la categoría
    private String nombreCategoria; // Guardar el nombre de la categoría
    private Ctrl_productocatalogo controladorProducto; // Controlador para productos
    private JPanel parentPanel; // Reference to the parent panel for navigation
    private int currentPage = 0;
    private static final int PRODUCTOS_POR_PAGINA = 15; // Mostrar 15 productos por página
    private List<catalogoproducto> todosLosProductos = new ArrayList<>();
    private JPopupMenu filterPopup;
    private boolean filterByName = false;
    private String filterNameValue = null;
    private boolean filterByColor = false;
    private String filterColorValue = null;
    private boolean filterByMaterial = false;
    private String filterMaterialValue = null;
    private boolean filterByMedida = false;
    private String filterMedidaValue = null;
    private boolean sortByName = false;
    private boolean sortByColor = false;
    private boolean sortByMaterial = false;
    private boolean sortByMedida = false;
    private JPanel filterPanel;
    private List<String> filterColorValues = new ArrayList<>(); // Lista para múltiples colores

  public Productos(int idCategoria, String nombreCategoria, JPanel parentPanel) {
    this.idCategoria = idCategoria;
    this.nombreCategoria = nombreCategoria != null ? nombreCategoria : "Categoría desconocida";
    this.controladorProducto = new Ctrl_productocatalogo();
    this.parentPanel = parentPanel; // Correctly assign the passed parent panel
    initComponents(); // Let the GUI builder handle the layout
    TemaManager.getInstance().addThemeChangeListener(this::aplicarTema);
    aplicarTema(); // Explicitly call aplicarTema at startup
    panelCards.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
    panelCards.setBackground(new Color(242, 247, 255));
    jPanel2.setBackground(new java.awt.Color(255, 255, 255)); // Blanco
    scrollPane = new JScrollPane(panelCards);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.setBorder(null);
    scrollPane.setPreferredSize(new Dimension(1055, 700)); // Set a reasonable initial size

    // Replace the existing panelCards in jPanel2 with scrollPane
    jPanel2.remove(panelCards); // Remove the old panelCards
    jPanel2.add(scrollPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 1078, 540));
    cargarProductosDesdeBD();
    setupFilterPopup();
}
    
    
    // ... (keep initComponents, action listeners, and cargartablacliente as they are)

    public void aplicarTema() {
        boolean oscuro = TemaManager.getInstance().isOscuro();
        Color fondo = oscuro ? new Color(21, 21, 33) : new Color(247, 247, 255);
        Color primario = oscuro ? new Color(40, 60, 150) : new Color(72, 92, 188);
        Color texto = oscuro ? Color.WHITE : Color.BLACK;

        setBackground(fondo);
        jPanel1.setBackground(fondo);
        jPanel2.setBackground(fondo);
        panelCards.setBackground(oscuro ? fondo : new Color(203,203,203));
        txtBuscar.setOpaque(true);
        txtBuscar.setBackground(fondo);
        txtBuscar.setForeground(texto);
        txtBuscar.setColorIcon(texto);
        txtBuscar.setPhColor(oscuro ? Color.LIGHT_GRAY : Color.DARK_GRAY);
        txtBuscar.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        Eliminar.setBackground (new Color(21, 21, 21));
        rSCheckBox1.setBackground(fondo);
        rSCheckBox1.setColorUnCheck(texto);
        rSCheckBox1.setColorCheck(texto);
        rSCheckBox1.setForeground(texto);
        paginacion.setForeground(texto);
        actualizarColoresLabels(texto);

        revalidate();
        repaint();
    }

    private void actualizarColoresLabels(Color color) {
        for (Component comp : panelCards.getComponents()) {
            if (comp instanceof JPanel) {
                JPanel cardWrapper = (JPanel) comp;
                for (Component subComp : cardWrapper.getComponents()) {
                    if (subComp instanceof JPanel) {
                        JPanel subPanel = (JPanel) subComp;
                        for (Component innerComp : subPanel.getComponents()) {
                            if (innerComp instanceof JLabel) {
                                ((JLabel) innerComp).setForeground(color);
                            }
                        }
                    }
                }
            }
        }
        panelCards.revalidate();
        panelCards.repaint();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        panelCards = new javax.swing.JPanel();
        txtBuscar = new RSMaterialComponent.RSTextFieldMaterialIcon();
        Añadir3 = new rojeru_san.RSButtonRiple();
        Añadir2 = new RSMaterialComponent.RSButtonShape();
        Anterior = new rojeru_san.RSButtonRiple();
        Siguiente = new rojeru_san.RSButtonRiple();
        rSCheckBox1 = new rojerusan.RSCheckBox();
        paginacion = new javax.swing.JLabel();
        Eliminar = new rojerusan.RSLabelIcon();
        btnVolver = new RSMaterialComponent.RSButtonShape();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(242, 247, 255));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelCards.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout panelCardsLayout = new javax.swing.GroupLayout(panelCards);
        panelCards.setLayout(panelCardsLayout);
        panelCardsLayout.setHorizontalGroup(
            panelCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1160, Short.MAX_VALUE)
        );
        panelCardsLayout.setVerticalGroup(
            panelCardsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
        );

        jPanel2.add(panelCards, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 160, 1160, 600));

        txtBuscar.setForeground(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorIcon(new java.awt.Color(0, 0, 0));
        txtBuscar.setColorMaterial(new java.awt.Color(153, 153, 153));
        txtBuscar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.SEARCH);
        txtBuscar.setPhColor(new java.awt.Color(102, 102, 102));
        txtBuscar.setPlaceholder("Buscar...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        jPanel2.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 430, 40));

        Añadir3.setBackground(new java.awt.Color(46, 49, 82));
        Añadir3.setText("Filtros");
        Añadir3.setColorHover(new java.awt.Color(0, 153, 51));
        Añadir3.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        Añadir3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Añadir3ActionPerformed(evt);
            }
        });
        jPanel2.add(Añadir3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 90, 90, 40));

        Añadir2.setBackground(new java.awt.Color(46, 49, 82));
        Añadir2.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        Añadir2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/plus (2).png"))); // NOI18N
        Añadir2.setText(" Nuevo");
        Añadir2.setBackgroundHover(new java.awt.Color(67, 150, 209));
        Añadir2.setFont(new java.awt.Font("Roboto Bold", 1, 16)); // NOI18N
        Añadir2.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        Añadir2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Añadir2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Añadir2ActionPerformed(evt);
            }
        });
        jPanel2.add(Añadir2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 90, 110, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -50, 1400, 700));
        jPanel2.getAccessibleContext().setAccessibleName("");

        Anterior.setBackground(new java.awt.Color(46, 49, 82));
        Anterior.setText("Anterior");
        Anterior.setColorHover(new java.awt.Color(0, 153, 51));
        Anterior.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        Anterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AnteriorActionPerformed(evt);
            }
        });

        Siguiente.setBackground(new java.awt.Color(46, 49, 82));
        Siguiente.setText("Siguiente");
        Siguiente.setColorHover(new java.awt.Color(0, 153, 51));
        Siguiente.setFont(new java.awt.Font("Humnst777 BlkCn BT", 1, 14)); // NOI18N
        Siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SiguienteActionPerformed(evt);
            }
        });

        rSCheckBox1.setForeground(new java.awt.Color(0, 0, 0));
        rSCheckBox1.setText("Seleccionar todo");
        rSCheckBox1.setColorCheck(new java.awt.Color(0, 0, 0));
        rSCheckBox1.setColorUnCheck(new java.awt.Color(204, 204, 204));
        rSCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rSCheckBox1ActionPerformed(evt);
            }
        });

        paginacion.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        paginacion.setText("jLabel1");

        Eliminar.setForeground(new java.awt.Color(255, 0, 0));
        Eliminar.setIcons(rojeru_san.efectos.ValoresEnum.ICONS.DELETE);
        Eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                EliminarMouseClicked(evt);
            }
        });

        btnVolver.setBackground(new java.awt.Color(46, 49, 82));
        btnVolver.setBorder(javax.swing.BorderFactory.createCompoundBorder());
        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/volver (1).png"))); // NOI18N
        btnVolver.setText(" Volver");
        btnVolver.setBackgroundHover(new java.awt.Color(67, 150, 209));
        btnVolver.setFont(new java.awt.Font("Roboto Bold", 1, 17)); // NOI18N
        btnVolver.setForma(RSMaterialComponent.RSButtonShape.FORMA.ROUND);
        btnVolver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(156, 156, 156)
                .addComponent(rSCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(248, 248, 248)
                .addComponent(paginacion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 738, Short.MAX_VALUE)
                .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(83, 83, 83))
            .addGroup(layout.createSequentialGroup()
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(312, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1278, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(126, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 837, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(Anterior, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(Siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rSCheckBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(paginacion))
                    .addComponent(Eliminar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(136, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(182, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AnteriorActionPerformed
        if (currentPage > 0) {
            currentPage--;
            mostrarPagina(currentPage);
            System.out.println("Moved to page: " + (currentPage + 1));
        }
    }//GEN-LAST:event_AnteriorActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void SiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SiguienteActionPerformed
        int totalPaginas = (int) Math.ceil((double) todosLosProductos.size() / PRODUCTOS_POR_PAGINA);
        if (currentPage < totalPaginas - 1) {
            currentPage++;
            mostrarPagina(currentPage);
            System.out.println("Moved to page: " + (currentPage + 1) + " of " + totalPaginas); // Debug output
        }
    }//GEN-LAST:event_SiguienteActionPerformed

    private void rSCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rSCheckBox1ActionPerformed
        boolean seleccionarTodo = rSCheckBox1.isSelected();

        for (Component comp : panelCards.getComponents()) {
            if (comp instanceof RSPanelEffect) {
                RSPanelEffect cardWrapper = (RSPanelEffect) comp;
                for (Component c : cardWrapper.getComponents()) {
                    if (c instanceof JPanel && "imageWrapper".equals(((JPanel) c).getName())) {
                        JPanel imageWrapper = (JPanel) c;
                        for (Component check : imageWrapper.getComponents()) {
                            if (check instanceof JCheckBox) {
                                ((JCheckBox) check).setSelected(seleccionarTodo);
                            }
                        }
                    }
                }
            }
        }
    }//GEN-LAST:event_rSCheckBox1ActionPerformed

    private void EliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_EliminarMouseClicked
        List<Integer> idsToDelete = new ArrayList<>();
        for (Component comp : panelCards.getComponents()) {
            if (comp instanceof RSPanelEffect) {
                RSPanelEffect cardWrapper = (RSPanelEffect) comp;
                for (Component c : cardWrapper.getComponents()) {
                    if (c instanceof JPanel && "imageWrapper".equals(((JPanel) c).getName())) {
                        JPanel imageWrapper = (JPanel) c;
                        for (Component check : imageWrapper.getComponents()) {
                            if (check instanceof JCheckBox && ((JCheckBox) check).isSelected()) {
                                JCheckBox checkBox = (JCheckBox) check; // Casteo explícito
                                Integer productId = (Integer) checkBox.getClientProperty("productoId");
                                if (productId != null) {
                                    idsToDelete.add(productId);
                                }
                            }
                        }
                    }
                }
            }
        }

        // Check if there are products to delete
        if (idsToDelete.isEmpty()) {
            System.out.println("¡Oye, no has seleccionado nada para eliminar!");
            return;
        }

        // Llamar a tu alerta personalizada with dynamic message
        String message = "¿Está seguro que desea eliminar " + (idsToDelete.size() > 1 ? "estos " + idsToDelete.size() + " productos?" : "este producto?");
        alertaEliminar1 dialog = new alertaEliminar1(new javax.swing.JFrame(), true, "¿Estás seguro?", message, "/warning-triangle-sign-free-vector-removebg-preview.png");
        boolean confirmado = dialog.confirmarEliminar();

        if (confirmado) {
            // Notificación de eliminación
            System.out.println("¡Borrando " + idsToDelete.size() + " producto(s) seleccionados!");

            boolean allDeleted = true;
            List<catalogoproducto> productsToRemove = new ArrayList<>();

            // Delete each selected product
            for (Integer id : idsToDelete) {
                try {
                    catalogoproducto productToDelete = todosLosProductos.stream()
                            .filter(p -> p.getIdproducto() == id)
                            .findFirst()
                            .orElse(null);
                    if (productToDelete != null && controladorProducto.eliminar(id)) {
                        productsToRemove.add(productToDelete);
                    } else {
                        allDeleted = false;
                    }
                } catch (Exception e) {
                    allDeleted = false;
                    System.out.println("Error al eliminar un producto: " + e.getMessage());
                }
            }

            // Remove deleted products from the list
            todosLosProductos.removeAll(productsToRemove);

            // Refresh the current page
            mostrarPagina(currentPage);

            // Show result message
            if (allDeleted) {
                System.out.println("¡Todos los productos seleccionados fueron borrados con éxito!");
                borradooconexito exito = new borradooconexito();
                exito.setVisible(true); // Mostrar el diálogo
            } else {
                System.out.println("Algo falló, no se pudieron borrar todos los productos.");
            }
        } else {
            System.out.println("¡Ok, no borramos nada entonces!");
        }//
    }//GEN-LAST:event_EliminarMouseClicked

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
      if (parentPanel != null) {
        // Limpiar el panel padre
        parentPanel.removeAll();
        parentPanel.setLayout(new BorderLayout());
        
        // Crear una nueva instancia de catalogo22 pasando el parentPanel
        catalogo22 catalogoPanel = new catalogo22((JFrame) getTopLevelAncestor(), false, parentPanel);
        
        // Agregar el panel de categorías al panel padre
        parentPanel.add(catalogoPanel, BorderLayout.CENTER);
        
        // Actualizar la UI
        parentPanel.revalidate();
        parentPanel.repaint();
    } else {
        System.err.println("Error: parentPanel is null. Cannot navigate back to categories.");
        JOptionPane.showMessageDialog(this, 
            "No se puede volver a categorías. El panel principal no está definido.", 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnVolverActionPerformed

    private void Añadir2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir2ActionPerformed
          nuevoProducto dialog = new nuevoProducto((JFrame) getTopLevelAncestor(), true, idCategoria, nombreCategoria);
        dialog.setVisible(true);
        cargarProductosDesdeBD(); 
    }//GEN-LAST:event_Añadir2ActionPerformed

    private void Añadir3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Añadir3ActionPerformed
        if (filterPopup != null && !filterPopup.isVisible()) {
            filterPopup.show(Añadir3, 0, Añadir3.getHeight());
        }
    }//GEN-LAST:event_Añadir3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private rojeru_san.RSButtonRiple Anterior;
    private RSMaterialComponent.RSButtonShape Añadir2;
    private rojeru_san.RSButtonRiple Añadir3;
    private rojerusan.RSLabelIcon Eliminar;
    private rojeru_san.RSButtonRiple Siguiente;
    private RSMaterialComponent.RSButtonShape btnVolver;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel paginacion;
    private javax.swing.JPanel panelCards;
    private rojerusan.RSCheckBox rSCheckBox1;
    private RSMaterialComponent.RSTextFieldMaterialIcon txtBuscar;
    // End of variables declaration//GEN-END:variables

    public void cargarProductosDesdeBD() {
        todosLosProductos = controladorProducto.obtenerProductosPorCategoria(idCategoria);
        if (todosLosProductos == null || todosLosProductos.isEmpty()) {
            System.out.println("No se encontraron productos para idCategoria: " + idCategoria);
            panelCards.removeAll();
            panelCards.revalidate();
            panelCards.repaint();
            paginacion.setText("Página 1 de 1");
            Anterior.setEnabled(false);
            Siguiente.setEnabled(false);
            return;
        }
        currentPage = 0;
        mostrarPagina(currentPage);
    }

    private void mostrarPagina(int pagina) {
        panelCards.removeAll();

        int inicio = pagina * PRODUCTOS_POR_PAGINA;
        int fin = Math.min(inicio + PRODUCTOS_POR_PAGINA, todosLosProductos.size());

        for (int i = inicio; i < fin; i++) {
            agregarCard(todosLosProductos.get(i));
        }

        int totalCards = fin - inicio;
        int cardsPerRow = 5;
        int row = (totalCards - 1) / cardsPerRow + 1;
        int nuevoAlto = Math.max(600, row * 300 + 10);
        panelCards.setPreferredSize(new Dimension(1055, nuevoAlto));
        scrollPane.setPreferredSize(new Dimension(1055, nuevoAlto));

        int totalPaginas = (int) Math.ceil((double) todosLosProductos.size() / PRODUCTOS_POR_PAGINA);
        Anterior.setEnabled(currentPage > 0);
        Siguiente.setEnabled(currentPage < totalPaginas - 1);
        paginacion.setText("Página " + (currentPage + 1) + " de " + (totalPaginas == 0 ? 1 : totalPaginas));

        panelCards.revalidate();
        panelCards.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
        revalidate();
        repaint();
    }

    private void agregarCard(catalogoproducto producto) {
        RSPanelEffect cardPanel = new RSPanelEffect();
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setPreferredSize(new Dimension(200, 290));
        cardPanel.setLayout(new BorderLayout());

        JPanel imageWrapper = new JPanel(null);
        imageWrapper.setPreferredSize(new Dimension(200, 180));
        imageWrapper.setOpaque(false);
        imageWrapper.setName("imageWrapper");

        JLabel imageLabel = new JLabel();
        imageLabel.setBounds(0, 0, 200, 180);
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);

        List<ImageIcon> images = new ArrayList<>();
        int imgWidth = 200;
        int imgHeight = 180;

        if (producto.getImagen() != null) {
            images.add(scaleImageIcon(new ImageIcon(producto.getImagen()), imgWidth, imgHeight));
        }
        if (producto.getImagen2() != null) {
            images.add(scaleImageIcon(new ImageIcon(producto.getImagen2()), imgWidth, imgHeight));
        }
        if (producto.getImagen3() != null) {
            images.add(scaleImageIcon(new ImageIcon(producto.getImagen3()), imgWidth, imgHeight));
        }
        if (producto.getImagen4() != null) {
            images.add(scaleImageIcon(new ImageIcon(producto.getImagen4()), imgWidth, imgHeight));
        }

        if (images.isEmpty()) {
            images.add(scaleImageIcon(new ImageIcon("ruta/a/imagen_por_defecto.png"), imgWidth, imgHeight));
        }

        final int[] currentIndex = {0};
        imageLabel.setIcon(images.get(currentIndex[0]));

        RSLabelIcon btnPrev = new RSLabelIcon();
        btnPrev.setIcons(ValoresEnum.ICONS.KEYBOARD_ARROW_LEFT);
        btnPrev.setBounds(5, 70, 30, 40);
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.setOpaque(false);
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                currentIndex[0] = (currentIndex[0] - 1 + images.size()) % images.size();
                imageLabel.setIcon(images.get(currentIndex[0]));
            }
        });

        RSLabelIcon btnNext = new RSLabelIcon();
        btnNext.setIcons(ValoresEnum.ICONS.KEYBOARD_ARROW_RIGHT);
        btnNext.setBounds(165, 70, 30, 40);
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.setOpaque(false);
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                currentIndex[0] = (currentIndex[0] + 1) % images.size();
                imageLabel.setIcon(images.get(currentIndex[0]));
            }
        });

        RSLabelIcon btnUp = new RSLabelIcon();
        btnUp.setIcons(ValoresEnum.ICONS.KEYBOARD_ARROW_UP);
        btnUp.setBounds(85, 5, 30, 40);
        btnUp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUp.setOpaque(false);
        btnUp.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                currentIndex[0] = 0;
                imageLabel.setIcon(images.get(currentIndex[0]));
            }
        });

        RSLabelIcon btnCurve = new RSLabelIcon();
        btnCurve.setIcons(ValoresEnum.ICONS.KEYBOARD_ARROW_LEFT);
        btnCurve.setBounds(40, 70, 30, 40);
        btnCurve.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCurve.setOpaque(false);
        btnCurve.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                currentIndex[0] = (currentIndex[0] - 2 + images.size()) % images.size();
                imageLabel.setIcon(images.get(currentIndex[0]));
            }
        });

        JCheckBox cardCheckBox = new JCheckBox();
        cardCheckBox.setBounds(5, 5, 20, 20);
        cardCheckBox.setOpaque(false);
        cardCheckBox.setBorderPainted(false);
        cardCheckBox.putClientProperty("productoId", producto.getIdproducto());

        imageWrapper.add(imageLabel);
        imageWrapper.add(btnPrev);
        imageWrapper.add(btnNext);
        imageWrapper.add(btnUp);
        imageWrapper.add(btnCurve);
        imageWrapper.add(cardCheckBox);
        imageWrapper.setComponentZOrder(cardCheckBox, 0);
        imageWrapper.revalidate();
        imageWrapper.repaint();

        System.out.println("Checkbox added at (5, 5) with size 20x20");

        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);

        // Changed from getDescripcion() to getNombre()
        JLabel descLabel = new JLabel(producto.getNombre() != null ? producto.getNombre() : "Sin nombre");
        descLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
        descLabel.setHorizontalAlignment(JLabel.CENTER);

        String dimensiones = (producto.getAlto() != null ? producto.getAlto() : "") + " x "
                + (producto.getAncho() != null ? producto.getAncho() : "") + " x "
                + (producto.getProfundidad() != null ? producto.getProfundidad() : "");
        if (dimensiones.trim().isEmpty()) {
            dimensiones = "Sin dimensiones";
        }
        JLabel sizeLabel = new JLabel(dimensiones);
        sizeLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        sizeLabel.setHorizontalAlignment(JLabel.CENTER);

        textPanel.add(descLabel);
        textPanel.add(sizeLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        buttonPanel.setOpaque(false);

        RSLabelIcon iconEdit = new RSLabelIcon();
        iconEdit.setIcons(ValoresEnum.ICONS.EDIT);
        iconEdit.setPreferredSize(new Dimension(25, 25));
        iconEdit.setForeground(Color.BLACK);
        iconEdit.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                editarProducto(producto);
            }
        });

        RSLabelIcon iconViewDetails = new RSLabelIcon();
        iconViewDetails.setIcons(ValoresEnum.ICONS.VISIBILITY);
        iconViewDetails.setPreferredSize(new Dimension(25, 25));
        iconViewDetails.setForeground(Color.BLUE);
        iconViewDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                mostrarDetalleProducto(producto);
            }
        });

        RSLabelIcon iconDelete = new RSLabelIcon();
        iconDelete.setIcons(ValoresEnum.ICONS.DELETE);
        iconDelete.setPreferredSize(new Dimension(25, 25));
        iconDelete.setForeground(Color.RED);
        iconDelete.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                eliminarProducto(producto, cardPanel);
            }
        });

        buttonPanel.add(iconEdit);
        buttonPanel.add(iconViewDetails);
        buttonPanel.add(iconDelete);

        cardPanel.add(imageWrapper, BorderLayout.NORTH);
        cardPanel.add(textPanel, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        panelCards.add(cardPanel);
    }

    private ImageIcon scaleImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image scaled = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaled);
    }

    private void eliminarProducto(catalogoproducto producto, JPanel cardPanel) {
        int confirm = javax.swing.JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas eliminar el producto '" + producto.getNombre() + "'?",
                "Confirmar Eliminación",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE
        );

        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            try {
                boolean eliminado = controladorProducto.eliminar(producto.getIdproducto());
                if (eliminado) {
                    todosLosProductos.remove(producto);
                    mostrarPagina(currentPage);
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "Producto eliminado correctamente.",
                            "Éxito",
                            javax.swing.JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    javax.swing.JOptionPane.showMessageDialog(
                            this,
                            "No se pudo eliminar el producto.",
                            "Error",
                            javax.swing.JOptionPane.ERROR_MESSAGE
                    );
                }
            } catch (Exception e) {
                javax.swing.JOptionPane.showMessageDialog(
                        this,
                        "Error al eliminar el producto: " + e.getMessage(),
                        "Error",
                        javax.swing.JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void mostrarDetalleProducto(catalogoproducto producto) {
        removeAll();
        setLayout(new BorderLayout());
        DetallesProducto detallePanel = new DetallesProducto(this, producto, idCategoria, nombreCategoria);
        add(detallePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
//

    public void volverAProductos() {
        removeAll();
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelCards.setPreferredSize(new Dimension(1055, 700));
        cargarProductosDesdeBD();
        revalidate();
        repaint();
    }

    private void editarProducto(catalogoproducto producto) {
        editarProducto1 dialog = new editarProducto1((JFrame) getTopLevelAncestor(), true, idCategoria, nombreCategoria, producto);
        dialog.setVisible(true);
        cargarProductosDesdeBD();
    }

    private Container getContentPane() {
        return this;
    }

    private void setupFilterPopup() {
        JDialog filterDialog = new JDialog((JFrame) getTopLevelAncestor(), "Filtros", false);
        filterDialog.setLayout(new BorderLayout());
        filterDialog.setSize(300, 400);
        filterDialog.setLocationRelativeTo(this);

        // Panel superior para búsqueda
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 30));
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                filterItems();
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                filterItems();
            }

            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                filterItems();
            }

            private void filterItems() {
                String text = searchField.getText().toLowerCase();
                for (Component comp : filterPanel.getComponents()) {
                    if (comp instanceof JCheckBox) {
                        JCheckBox check = (JCheckBox) comp;
                        check.setVisible(check.getText().toLowerCase().contains(text));
                    }
                }
                filterPanel.revalidate();
                filterPanel.repaint();
            }
        });
        searchPanel.add(new JLabel("Buscar: "));
        searchPanel.add(searchField);
        filterDialog.add(searchPanel, BorderLayout.NORTH);

        // Panel central para checkboxes
        filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(0, 1, 0, 5));
        filterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JCheckBox selectAll = new JCheckBox("Seleccionar todo");
        selectAll.addActionListener(e -> {
            boolean selected = selectAll.isSelected();
            for (Component comp : filterPanel.getComponents()) {
                if (comp instanceof JCheckBox && comp != selectAll) {
                    ((JCheckBox) comp).setSelected(selected);
                }
            }
            updateFilterColorValues(); // Actualizar lista de colores
            applyFilters();
        });
        filterPanel.add(selectAll);

        // Depuración: Mostrar colores disponibles
        System.out.println("Colores en todosLosProductos:");
        todosLosProductos.forEach(p -> System.out.println("Producto ID: " + p.getIdproducto() + ", Color: " + p.getColor()));

        // Contar productos por color
        java.util.Map<String, Long> colorCounts = todosLosProductos.stream()
                .filter(p -> p.getColor() != null)
                .map(p -> p.getColor().trim().toLowerCase())
                .collect(Collectors.groupingBy(
                        color -> color,
                        Collectors.counting()
                ));

        // Añadir etiqueta para colores
        filterPanel.add(new JLabel("Color:"));
        colorCounts.forEach((color, count) -> {
            String displayColor = color.substring(0, 1).toUpperCase() + color.substring(1);
            JCheckBox colorCheckBox = new JCheckBox(displayColor + " (" + count + ")");
            colorCheckBox.addActionListener(e -> {
                updateFilterColorValues(); // Actualizar lista de colores
                applyFilters();
            });
            filterPanel.add(colorCheckBox);
        });

        // Añadir etiqueta para materiales
        filterPanel.add(new JLabel("Material:"));
        // Crear checkboxes dinámicos para materiales (sin cambios aquí por simplicidad)
        java.util.Map<String, Long> materialCounts = todosLosProductos.stream()
                .filter(p -> p.getMaterial() != null)
                .map(p -> p.getMaterial().trim().toLowerCase())
                .collect(Collectors.groupingBy(
                        material -> material,
                        Collectors.counting()
                ));
        materialCounts.forEach((material, count) -> {
            String displayMaterial = material.substring(0, 1).toUpperCase() + material.substring(1);
            JCheckBox materialCheckBox = new JCheckBox(displayMaterial + " (" + count + ")");
            materialCheckBox.addActionListener(e -> {
                filterMaterialValue = materialCheckBox.isSelected() ? displayMaterial : null;
                System.out.println("FilterMaterialValue actualizado a: " + filterMaterialValue);
                applyFilters();
            });
            filterPanel.add(materialCheckBox);
        });

        filterDialog.add(filterPanel, BorderLayout.CENTER);

        // Botón para cerrar
        JButton closeButton = new JButton("Cerrar");
        closeButton.addActionListener(e -> filterDialog.dispose());
        filterDialog.add(closeButton, BorderLayout.SOUTH);

        // Mostrar el diálogo al hacer clic en "Filtros"
        Añadir3.addActionListener(e -> {
            filterPanel.removeAll();
            filterPanel.add(selectAll);

            java.util.Map<String, Long> updatedColorCounts = todosLosProductos.stream()
                    .filter(p -> p.getColor() != null)
                    .map(p -> p.getColor().trim().toLowerCase())
                    .collect(Collectors.groupingBy(
                            color -> color,
                            Collectors.counting()
                    ));
            java.util.Map<String, Long> updatedMaterialCounts = todosLosProductos.stream()
                    .filter(p -> p.getMaterial() != null)
                    .map(p -> p.getMaterial().trim().toLowerCase())
                    .collect(Collectors.groupingBy(
                            material -> material,
                            Collectors.counting()
                    ));

            filterPanel.add(new JLabel("Color:"));
            updatedColorCounts.forEach((color, count) -> {
                String displayColor = color.substring(0, 1).toUpperCase() + color.substring(1);
                JCheckBox colorCheckBox = new JCheckBox(displayColor + " (" + count + ")");
                colorCheckBox.setSelected(filterColorValues.contains(displayColor));
                colorCheckBox.addActionListener(e2 -> {
                    updateFilterColorValues();
                    applyFilters();
                });
                filterPanel.add(colorCheckBox);
            });

            filterPanel.add(new JLabel("Material:"));
            updatedMaterialCounts.forEach((material, count) -> {
                String displayMaterial = material.substring(0, 1).toUpperCase() + material.substring(1);
                JCheckBox materialCheckBox = new JCheckBox(displayMaterial + " (" + count + ")");
                materialCheckBox.addActionListener(e2 -> {
                    filterMaterialValue = materialCheckBox.isSelected() ? displayMaterial : null;
                    System.out.println("FilterMaterialValue actualizado a: " + filterMaterialValue);
                    applyFilters();
                });
                filterPanel.add(materialCheckBox);
            });

            filterPanel.revalidate();
            filterPanel.repaint();
            filterDialog.setVisible(true);
        });
    }

    private void updateFilterColorValues() {
        filterColorValues.clear();
        for (Component comp : filterPanel.getComponents()) {
            if (comp instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) comp;
                if (checkBox.isSelected() && !checkBox.getText().startsWith("Seleccionar todo") && !checkBox.getText().contains("Material:")) {
                    String color = checkBox.getText().split(" \\(")[0]; // Extraer el nombre del color
                    filterColorValues.add(color);
                }
            }
        }
        System.out.println("Colores seleccionados: " + filterColorValues);
    }

    private void applyFilters() {
        // Recargar la lista original desde la base de datos
        todosLosProductos = new ArrayList<>(controladorProducto.obtenerProductosPorCategoria(idCategoria));
        List<catalogoproducto> filteredProducts = new ArrayList<>(todosLosProductos);

        System.out.println("Aplicando filtros - filterColorValues: " + filterColorValues + ", filterMaterialValue: " + filterMaterialValue);
        System.out.println("Tamaño de la lista original: " + todosLosProductos.size());

        if (!filterColorValues.isEmpty() || filterMaterialValue != null) {
            filteredProducts = filteredProducts.stream()
                    .filter(p -> {
                        boolean matches = true;
                        if (!filterColorValues.isEmpty()) {
                            String productColor = p.getColor() != null ? p.getColor().trim() : "";
                            boolean colorMatches = filterColorValues.stream().anyMatch(c -> productColor.equalsIgnoreCase(c));
                            System.out.println("Comparando color: productColor=" + productColor + ", matches=" + colorMatches);
                            if (!colorMatches) {
                                matches = false;
                            }
                        }
                        if (filterMaterialValue != null) {
                            String productMaterial = p.getMaterial() != null ? p.getMaterial().trim() : "";
                            System.out.println("Comparando material: filterMaterialValue=" + filterMaterialValue + ", productMaterial=" + productMaterial);
                            if (!productMaterial.equalsIgnoreCase(filterMaterialValue)) {
                                matches = false;
                            }
                        }
                        return matches;
                    })
                    .collect(Collectors.toList());
        }

        todosLosProductos.clear();
        todosLosProductos.addAll(filteredProducts);

        if (todosLosProductos.isEmpty() && (!filterColorValues.isEmpty() || filterMaterialValue != null)) {
            System.out.println("No se encontraron productos con los filtros aplicados, restaurando lista original.");
            JOptionPane.showMessageDialog(this, "No se encontraron productos con los filtros aplicados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            todosLosProductos = new ArrayList<>(controladorProducto.obtenerProductosPorCategoria(idCategoria));
        }

        currentPage = 0;
        mostrarPagina(currentPage);
    }

    private void applyFiltersAndShowPage() {
        List<catalogoproducto> filteredProducts = new ArrayList<>(todosLosProductos);

        // Apply filters only if values are provided
        if (filterByName || filterByColor || filterByMaterial || filterByMedida) {
            filteredProducts = filteredProducts.stream()
                    .filter(p -> {
                        boolean matches = true;
                        if (filterByName && filterNameValue != null && !p.getNombre().toLowerCase().contains(filterNameValue.toLowerCase())) {
                            matches = false;
                        }
                        if (filterByColor && filterColorValue != null && !p.getColor().equalsIgnoreCase(filterColorValue)) {
                            matches = false;
                        }
                        if (filterByMaterial && filterMaterialValue != null && !p.getMaterial().equalsIgnoreCase(filterMaterialValue)) {
                            matches = false;
                        }
                        if (filterByMedida && filterMedidaValue != null && !(p.getAlto() != null && p.getAlto().contains(filterMedidaValue)
                                || p.getAncho() != null && p.getAncho().contains(filterMedidaValue)
                                || p.getProfundidad() != null && p.getProfundidad().contains(filterMedidaValue))) {
                            matches = false;
                        }
                        return matches;
                    })
                    .collect(Collectors.toList());
        }

        // Apply sorting
        if (sortByName) {
            filteredProducts.sort(Comparator.comparing(catalogoproducto::getNombre, Comparator.nullsLast(String::compareTo)));
        } else if (sortByColor) {
            filteredProducts.sort(Comparator.comparing(catalogoproducto::getColor, Comparator.nullsLast(String::compareTo)));
        } else if (sortByMaterial) {
            filteredProducts.sort(Comparator.comparing(catalogoproducto::getMaterial, Comparator.nullsLast(String::compareTo)));
        } else if (sortByMedida) {
            filteredProducts.sort(Comparator.comparing(catalogoproducto::getAlto, Comparator.nullsLast(String::compareTo))
                    .thenComparing(catalogoproducto::getAncho, Comparator.nullsLast(String::compareTo))
                    .thenComparing(catalogoproducto::getProfundidad, Comparator.nullsLast(String::compareTo)));
        }

        todosLosProductos.clear();
        todosLosProductos.addAll(filteredProducts);
        if (todosLosProductos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron productos con los filtros aplicados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
        currentPage = 0;
        mostrarPagina(currentPage);
    }
}
