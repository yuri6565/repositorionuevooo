/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Caja;
import modelo.Conexion;

/**
 *
 * @author ADSO
 */
public class Ctrl_CajaEgresos {

    private final String movimiento = "egreso";

    public boolean insertar(Caja caja) {
        String sql = "INSERT INTO caja (fecha, descripcion, monto, cantidad, movimiento, categoria) VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, caja.getFecha());
            stmt.setString(2, caja.getDescripcion());
            stmt.setDouble(3, caja.getMonto());
            stmt.setInt(6, caja.getCantidad());
            stmt.setString(4, movimiento);  // 👈 'egreso'
            stmt.setString(5, caja.getCategoria());
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

   public List<Caja> obtenerEgresos() {
    List<Caja> listaEgresos = new ArrayList<>();
    String sql = "SELECT c.id_codigo, c.fecha, c.monto, c.cantidad, c.descripcion, c.categoria, " +
                 "p.nombre as proveedor, GROUP_CONCAT(i.nombre SEPARATOR ', ') as productos " +
                 "FROM caja c " +
                 "LEFT JOIN suministra s ON c.suministra_idSuministra = s.id_suministra " +
                 "LEFT JOIN proveedor p ON s.proveedor_id_proveedor = p.id_proveedor " +
                 "LEFT JOIN inventario i ON s.inventario_id_inventario = i.id_inventario " +
                 "WHERE c.movimiento = 'egreso' " +
                 "GROUP BY c.id_codigo";

    try (Connection con = Conexion.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Caja caja = new Caja();
            caja.setId_codigo(rs.getInt("id_codigo"));
            caja.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toString() : "");
            caja.setMonto(rs.getDouble("monto"));
            caja.setDescripcion(rs.getString("descripcion"));
            caja.setCategoria(rs.getString("categoria"));
            caja.setProveedor(rs.getString("proveedor"));
                        caja.setProveedor(rs.getString("cantidad"));

            
            // Manejo de productos
            String productosStr = rs.getString("productos");
            List<String> productos = new ArrayList<>();
            if (productosStr != null) {
                productos = Arrays.asList(productosStr.split(", "));
            }
            caja.setProductos(productos);
            
            listaEgresos.add(caja);
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener egresos: " + e.getMessage());
        e.printStackTrace();
    }
    return listaEgresos;
}

    private List<String> obtenerProductosPorEgreso(int idEgreso) {
        List<String> productos = new ArrayList<>();
        String sql = "SELECT i.nombre FROM egreso_productos ep "
                + "JOIN inventario i ON ep.id_producto = i.id_inventario "
                + "WHERE ep.id_egreso = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEgreso);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    productos.add(rs.getString("nombre"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return productos;
    }
// Mejora el método actualizar con mensajes más descriptivos

    public boolean actualizar(Caja caja) {
        String sql = "UPDATE caja SET fecha = ?, descripcion = ?, monto = ?,cantidad = ? categoria = ? WHERE id_codigo = ? AND movimiento = ?";

        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, caja.getFecha());
            stmt.setString(2, caja.getDescripcion());
            stmt.setDouble(3, caja.getMonto());
            stmt.setString(4, caja.getCategoria());
            stmt.setInt(5, caja.getId_codigo());
            stmt.setString(6, movimiento);
            stmt.setInt(7, caja.getCantidad());

            int result = stmt.executeUpdate();
            if (result == 0) {
                JOptionPane.showMessageDialog(null, "No se encontró el egreso o no es de tipo 'egreso'",
                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
            return result > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar egreso: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public boolean eliminar(int id_codigo) {
        String sql = "DELETE FROM caja WHERE id_codigo = ? AND movimiento = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id_codigo);
            stmt.setString(2, movimiento);  // 👈 Solo si es 'egreso'
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Caja obtenerEgresoPorId(int idEgreso) {
        Caja caja = null;
        String sql = "SELECT c.*, p.nombre as proveedor_nombre, GROUP_CONCAT(i.nombre SEPARATOR ', ') as productos_nombres " +
                     "FROM caja c " +
                     "LEFT JOIN suministra s ON c.suministra_idSuministra = s.id_suministra " +
                     "LEFT JOIN proveedor p ON s.proveedor_id_proveedor = p.id_proveedor " +
                     "LEFT JOIN inventario i ON s.inventario_id_inventario = i.id_inventario " +
                     "WHERE c.id_codigo = ? AND c.movimiento = 'egreso' " +
                     "GROUP BY c.id_codigo";
        
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idEgreso);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    caja = new Caja();
                    caja.setId_codigo(rs.getInt("id_codigo"));
                    caja.setFecha(rs.getString("fecha"));
                    caja.setMonto(rs.getDouble("monto"));
                    caja.setCantidad(rs.getInt("cantidad"));
                    caja.setDescripcion(rs.getString("descripcion"));
                    caja.setCategoria(rs.getString("categoria"));
                    
                    // Manejo de proveedor (puede ser NULL)
                    String proveedor = rs.getString("proveedor_nombre");
                    if (!rs.wasNull()) {
                        caja.setProveedor(proveedor);
                    }
                    
                    // Manejo de productos (puede ser NULL)
                    String productos = rs.getString("productos_nombres");
                    if (!rs.wasNull()) {
                        caja.setProductos(Arrays.asList(productos.split(", ")));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return caja;

    }
}
