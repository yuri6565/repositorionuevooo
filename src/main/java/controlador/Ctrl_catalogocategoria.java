package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Catalogocategoria;
import modelo.Conexion;
import modelo.catalogoproducto;

public class Ctrl_catalogocategoria {

    // En Ctrl_catalogocategoria
    public boolean guardar(Catalogocategoria categoria) {
        String sql = "INSERT INTO categoriacatalogo (nombre, imagen) VALUES (?, ?)";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, categoria.getNombre());
            ps.setBytes(2, categoria.getImagen());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Catalogocategoria> obtenerCategorias() {
        List<Catalogocategoria> lista = new ArrayList<>();
        String sql = "SELECT * FROM categoriacatalogo";

        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Catalogocategoria categoria = new Catalogocategoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setImagen(rs.getBytes("imagen"));

                lista.add(categoria);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener categorías: " + e.getMessage());
        }
        return lista;
    }

    public Catalogocategoria obtenerCategoriaPorId(int idCategoria) {
        Catalogocategoria categoria = null;
        Connection con = Conexion.getConnection();

        try {
            String sql = "SELECT * FROM categoriacatalogo WHERE idCategoria = ?";
            PreparedStatement consulta = con.prepareStatement(sql);
            consulta.setInt(1, idCategoria);

            ResultSet rs = consulta.executeQuery();

            if (rs.next()) {
                categoria = new Catalogocategoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNombre(rs.getString("nombre"));
                categoria.setImagen(rs.getBytes("imagen"));
            }

            rs.close();
            consulta.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener categoría: " + e.getMessage());
        }

        return categoria;
    }

    public boolean editar(Catalogocategoria objeto, int idCategoria) {
        boolean respuesta = false;

        try (Connection con = Conexion.getConnection()) {
            String sql = "UPDATE categoriacatalogo SET nombre = ?, imagen = ? WHERE idCategoria = ?";
            PreparedStatement consulta = con.prepareStatement(sql);

            consulta.setString(1, objeto.getNombre());
            consulta.setBytes(2, objeto.getImagen());
            consulta.setInt(3, idCategoria);

            System.out.println("Actualizando categoría ID " + idCategoria);
            int filas = consulta.executeUpdate();
            System.out.println("Filas afectadas: " + filas);

            respuesta = filas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar categoría: " + e.getMessage());
            e.printStackTrace();
        }

        return respuesta;
    }

    public boolean eliminar(int idCategoria) {
        boolean respuesta = false;
        Connection con = Conexion.getConnection();

        try {
            String sql = "DELETE FROM categoriacatalogo WHERE idCategoria = ?";
            PreparedStatement consulta = con.prepareStatement(sql);
            consulta.setInt(1, idCategoria);

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }

            consulta.close();
            con.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar categoría: " + e.getMessage());
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
                    catalogoproducto producto = new catalogoproducto();
                    producto.setIdproducto(rs.getInt("idproducto"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setAlto(rs.getString("alto"));
                    producto.setAncho(rs.getString("ancho"));
                    producto.setProfundidad(rs.getString("profundidad"));
                    producto.setMaterial(rs.getString("material"));
                    producto.setColor(rs.getString("color"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setImagen(rs.getBytes("imagen"));
                    producto.setImagen2(rs.getBytes("imagen2"));
                    producto.setImagen3(rs.getBytes("imagen3"));
                    producto.setImagen4(rs.getBytes("imagen4"));
                    producto.setIdCategoria(rs.getInt("Categoria_idCategoria"));
                    lista.add(producto);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al listar productos por categoría: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Verifica si una categoría tiene productos asociados.
     *
     * @param idCategoria ID de la categoría a verificar
     * @return true si la categoría tiene productos, false en caso contrario
     */
    public boolean tieneProductos(int idCategoria) {
        List<catalogoproducto> productos = obtenerProductosPorCategoria(idCategoria);
        return !productos.isEmpty();
    }
    // En tu clase Ctrl_catalogocategoria (o similar)

    public boolean existeCategoria(String nombreCategoria) {
        String sql = "SELECT COUNT(*) FROM categoriacatalogo WHERE nombre = ?";

        try (Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreCategoria);

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
