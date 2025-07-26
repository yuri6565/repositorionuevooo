/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Categoria;
import modelo.Conexion;
import modelo.Marca;

/**
 *
 * @author ZenBook
 */
public class Ctrl_MarcaHerramienta {

    private final String tipo = "herramienta";  // ⚙️ Fijamos el tipo automáticamente

    public boolean insertar(Marca marca) {
        // Primero verificar si ya existe
        if (existeCategoria(marca.getNombre())) {
            JOptionPane.showMessageDialog(null,
                    "Ya existe una categoría con este nombre",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO marca (nombre, tipo) VALUES (?, ?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, marca.getNombre());
            stmt.setString(2, tipo);  // 👈 Siempre 'material'
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<Marca> obtenerMarcasHerramienta() {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT * FROM marca WHERE tipo = 'herramienta'"; // Filtrar por tipo en la base de datos

        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Marca marca = new Marca(
                        rs.getInt("idmarca"),
                        rs.getString("nombre")
                );
                lista.add(marca);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener categorías: " + e.getMessage());
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Marca marca) {
        String sql = "UPDATE marca SET nombre = ? WHERE idmarca = ? AND tipo = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, marca.getNombre());
            stmt.setInt(2, marca.getCodigo());
            stmt.setString(3, tipo);  // 👈 Asegura que solo actualice de tipo material
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int idmarca) {
        String sql = "DELETE FROM marca WHERE idmarca = ? AND tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, idmarca);
            stmt.setString(2, tipo);  // 👈 Asegura que solo borre si es material
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Marca> obtenerMarcaHerramienta(String tipo) {
        List<Marca> lista = new ArrayList<>();
        String sql = "SELECT * FROM marca WHERE tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Marca(rs.getInt("idmarca"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean categoriaEnUso(int codigoCategoria) {
        String sql = "SELECT COUNT(*) FROM inventario WHERE marca_idmarca = ? AND tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setInt(1, codigoCategoria);
            stmt.setString(2, this.tipo); // Usamos el tipo definido en el controlador (material)
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0; // Si count > 0, la categoría está en uso
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar uso de categoría: " + e.getMessage());
            e.printStackTrace();
            return true; // Por seguridad, asumir que está en uso si hay error
        }
    }

    public int eliminarConVerificacion(int codigo) {
        if (categoriaEnUso(codigo)) {
            return -1; // Código para "categoría en uso"
        }

        String sql = "DELETE FROM marca WHERE idmarca = ? AND tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.setString(2, this.tipo);
            return stmt.executeUpdate() > 0 ? 1 : 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -2; // Código para error de conexión
        }
    }

    public boolean existeCategoria(String nombre) {
        String sql = "SELECT COUNT(*) FROM marca WHERE nombre = ? AND tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, this.tipo);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al verificar categoría existente: " + e.getMessage());
            e.printStackTrace();
            return true; // Por seguridad, asumir que existe si hay error
        }
    }

}
