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
import modelo.Conexion;
import modelo.Unidad;

/**
 *
 * @author ZenBook
 */
public class Ctrl_UnidadMaterial {

    private final String tipo = "material";  // ⚙️ Fijamos el tipo automáticamente

    public boolean insertar(Unidad unidad) {

        // Primero verificar si ya existe
        if (existeCategoria(unidad.getNombre())) {
            JOptionPane.showMessageDialog(null,
                    "Ya existe una categoría con este nombre",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String sql = "INSERT INTO unidad_medida (nombre, tipo) VALUES (?, ?)";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, unidad.getNombre());
            stmt.setString(2, tipo);  // 👈 Siempre 'material'
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de conexión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public List<Unidad> obtenerCategoriasMaterial() {
        List<Unidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidad_medida WHERE tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tipo);  // 👈 Solo 'material'
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Unidad(rs.getInt("idunidad_medida"), rs.getString("nombre")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean actualizar(Unidad unidad) {
        String sql = "UPDATE unidad_medida SET nombre = ? WHERE idunidad_medida = ? AND tipo = ?";
        try (Connection conn = Conexion.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, unidad.getNombre());
            stmt.setInt(2, unidad.getCodigo());
            stmt.setString(3, tipo);  // 👈 Asegura que solo actualice de tipo material
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int codigo) {
        String sql = "DELETE FROM unidad_medida WHERE idunidad_medida = ? AND tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, codigo);
            stmt.setString(2, tipo);  // 👈 Asegura que solo borre si es material
            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Unidad> obtenerUnidadMaterial(String tipo) {
        List<Unidad> lista = new ArrayList<>();
        String sql = "SELECT * FROM unidad_medida WHERE tipo = ?";
        try (Connection con = Conexion.getConnection(); PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(new Unidad(rs.getInt("idunidad_medida"), rs.getString("nombre")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean categoriaEnUso(int codigoCategoria) {
        String sql = "SELECT COUNT(*) FROM inventario WHERE unidad_medida_idunidad_medida = ? AND tipo = ?";
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

        String sql = "DELETE FROM unidad_medida WHERE idunidad_medida = ? AND tipo = ?";
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
        String sql = "SELECT COUNT(*) FROM unidad_medida WHERE nombre = ? AND tipo = ?";
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
