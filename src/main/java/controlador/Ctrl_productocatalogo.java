package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.catalogoproducto;

public class Ctrl_productocatalogo {

    public boolean guardar(catalogoproducto producto) {
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection()) {
            String sql = "INSERT INTO catalogo_producto (nombre, alto, ancho, profundidad, material, color, descripcion, imagen, imagen2, imagen3, imagen4, Categoria_idCategoria) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getAlto() != null ? producto.getAlto() : "");
            stmt.setString(3, producto.getAncho() != null ? producto.getAncho() : "");
            stmt.setString(4, producto.getProfundidad() != null ? producto.getProfundidad() : "");
            stmt.setString(5, producto.getMaterial() != null ? producto.getMaterial() : "");
            stmt.setString(6, producto.getColor() != null ? producto.getColor() : "");
            stmt.setString(7, producto.getDescripcion() != null ? producto.getDescripcion() : "");
            stmt.setBytes(8, producto.getImagen());
            stmt.setBytes(9, producto.getImagen2());
            stmt.setBytes(10, producto.getImagen3());
            stmt.setBytes(11, producto.getImagen4());
            stmt.setInt(12, producto.getIdCategoria());

            respuesta = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al guardar producto: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar producto: " + e.getMessage());
        }
        return respuesta;
    }

    public List<catalogoproducto> listarProductos() {
        List<catalogoproducto> lista = new ArrayList<>();
        String sql = "SELECT * FROM catalogo_producto";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                catalogoproducto p = new catalogoproducto();
                p.setIdproducto(rs.getInt("idproducto"));
                p.setNombre(rs.getString("nombre"));
                p.setAlto(rs.getString("alto"));
                p.setAncho(rs.getString("ancho"));
                p.setProfundidad(rs.getString("profundidad"));
                p.setMaterial(rs.getString("material"));
                p.setColor(rs.getString("color"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setImagen(rs.getBytes("imagen"));
                p.setImagen2(rs.getBytes("imagen2"));
                p.setImagen3(rs.getBytes("imagen3"));
                p.setImagen4(rs.getBytes("imagen4"));
                p.setIdCategoria(rs.getInt("Categoria_idCategoria"));

                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos: " + e.getMessage());
        }
        return lista;
    }

    public boolean editar(catalogoproducto producto) {
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection()) {
            String sql = "UPDATE catalogo_producto SET nombre=?, alto=?, ancho=?, profundidad=?, material=?, color=?, descripcion=?, imagen=?, imagen2=?, imagen3=?, imagen4=?, Categoria_idCategoria=? WHERE idproducto=?";
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getAlto());
            stmt.setString(3, producto.getAncho());
            stmt.setString(4, producto.getProfundidad());
            stmt.setString(5, producto.getMaterial());
            stmt.setString(6, producto.getColor());
            stmt.setString(7, producto.getDescripcion());
            stmt.setBytes(8, producto.getImagen());
            stmt.setBytes(9, producto.getImagen2());
            stmt.setBytes(10, producto.getImagen3());
            stmt.setBytes(11, producto.getImagen4());
            stmt.setInt(12, producto.getIdCategoria());
            stmt.setInt(13, producto.getIdproducto());

            respuesta = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar producto: " + e.getMessage());
        }
        return respuesta;
    }

    public boolean eliminar(int idProducto) {
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection()) {
            String sql = "DELETE FROM catalogo_producto WHERE idproducto = ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, idProducto);

            respuesta = stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
        }
        return respuesta;
    }

    public List<catalogoproducto> obtenerProductosPorCategoria(int idCategoria) {
        List<catalogoproducto> lista = new ArrayList<>();
        String sql = "SELECT * FROM catalogo_producto WHERE Categoria_idCategoria = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    catalogoproducto p = new catalogoproducto();
                    p.setIdproducto(rs.getInt("idproducto"));
                    p.setNombre(rs.getString("nombre"));
                    p.setAlto(rs.getString("alto"));
                    p.setAncho(rs.getString("ancho"));
                    p.setProfundidad(rs.getString("profundidad"));
                    p.setMaterial(rs.getString("material"));
                    p.setColor(rs.getString("color"));
                    p.setDescripcion(rs.getString("descripcion"));
                    p.setImagen(rs.getBytes("imagen"));
                    p.setImagen2(rs.getBytes("imagen2"));
                    p.setImagen3(rs.getBytes("imagen3"));
                    p.setImagen4(rs.getBytes("imagen4"));
                    p.setIdCategoria(rs.getInt("Categoria_idCategoria"));
                    lista.add(p);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos por categoría: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al listar productos por categoría: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeProducto(String nombreProducto, int idCategoria) {
        String sql = "SELECT COUNT(*) FROM catalogo_producto WHERE nombre = ? AND Categoria_idCategoria = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreProducto);
            ps.setInt(2, idCategoria);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
