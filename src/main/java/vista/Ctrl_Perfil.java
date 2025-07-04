package vista;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Conexion;
import modelo.UsuarioModelo;

/**
 *
 * @author Personal
 */
public class Ctrl_Perfil {

    // Check if an email already exists in the database
    public boolean existeCorreo(String correo, int excludeIdUsuario) {
        boolean existe = false;
        String sql = "SELECT id_usuario FROM usuario WHERE correo_electronico = ? AND id_usuario != ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, correo);
            ps.setInt(2, excludeIdUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar existencia del correo: " + e.getMessage());
        }
        return existe;
    }

    public UsuarioModelo obtenerUsuario(int id_usuario) {
        UsuarioModelo usuario = new UsuarioModelo();
        Connection cn = Conexion.getConnection();
        String sql = "SELECT id_usuario, tipodeiden, nombre, apellido, usuario, contrasena, correo_electronico, telefono, rol, imagen FROM usuario WHERE id_usuario = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, id_usuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setTipodeiden(rs.getString("tipodeiden"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setCorreo_electronico(rs.getString("correo_electronico"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setImagen(rs.getBytes("imagen"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuario: " + e.getMessage());
        }
        return usuario;
    }

    public boolean actualizarUsuario(UsuarioModelo usuario, boolean esTrabajador) {
        if (usuario.getId_usuario() <= 0) {
            JOptionPane.showMessageDialog(null, "ID de usuario inválido.");
            return false;
        }
        // Check if the email is already used by another user
        if (existeCorreo(usuario.getCorreo_electronico(), usuario.getId_usuario())) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está en uso por otro usuario.");
            return false;
        }
        boolean exito = false;
        Connection cn = Conexion.getConnection();
        String rolActual = obtenerUsuario(usuario.getId_usuario()).getRol();
        String rolFinal = esTrabajador ? rolActual : usuario.getRol();

        String sql = "UPDATE usuario SET id_usuario = ?, tipodeiden = ?, nombre = ?, apellido = ?, correo_electronico = ?, usuario = ?, contrasena = ?, telefono = ?, rol = ? WHERE id_usuario = ?";
        try (PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setInt(1, usuario.getId_usuario());
            ps.setString(2, usuario.getTipodeiden());
            ps.setString(3, usuario.getNombre());
            ps.setString(4, usuario.getApellido());
            ps.setString(5, usuario.getCorreo_electronico());
            ps.setString(6, usuario.getUsuario());
            String hashedPassword = Conexion.hashSHA256(usuario.getContrasena());
            if (hashedPassword == null) {
                JOptionPane.showMessageDialog(null, "Error al encriptar la contraseña.");
                return false;
            }
            ps.setString(7, hashedPassword);
            ps.setString(8, usuario.getTelefono());
            ps.setString(9, rolFinal);
            ps.setInt(10, usuario.getId_usuario());

            int filasAfectadas = ps.executeUpdate();
            exito = filasAfectadas > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar usuario: " + e.getMessage());
        }
        return exito;
    }

    public boolean guardar(UsuarioModelo objeto) {
        if (objeto.getId_usuario() <= 0) {
            JOptionPane.showMessageDialog(null, "ID de usuario inválido.");
            return false;
        }
        // Check if the email is already used
        if (existeCorreo(objeto.getCorreo_electronico(), 0)) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está en uso.");
            return false;
        }
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection();
             PreparedStatement consulta = con.prepareStatement(
                 "INSERT INTO usuario (id_usuario, tipodeiden, imagen, nombre, apellido, usuario, contrasena, correo_electronico, telefono, rol) " +
                 "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            consulta.setInt(1, objeto.getId_usuario());
            consulta.setString(2, objeto.getTipodeiden());
            consulta.setBytes(3, objeto.getImagen());
            consulta.setString(4, objeto.getNombre());
            consulta.setString(5, objeto.getApellido());
            consulta.setString(6, objeto.getUsuario());
            String hashedPassword = Conexion.hashSHA256(objeto.getContrasena());
            if (hashedPassword == null) {
                JOptionPane.showMessageDialog(null, "Error al encriptar la contraseña.");
                return false;
            }
            consulta.setString(7, hashedPassword);
            consulta.setString(8, objeto.getCorreo_electronico());
            consulta.setString(9, objeto.getTelefono());
            consulta.setString(10, objeto.getRol());

            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuario: " + e.getMessage());
        }
        return respuesta;
    }

    public List<UsuarioModelo> obtenerUsuarios() {
        List<UsuarioModelo> lista = new ArrayList<>();
        String sql = "SELECT id_usuario, tipodeiden, nombre, apellido, usuario, contrasena, correo_electronico, telefono, rol, imagen FROM usuario";
        try (Connection con = Conexion.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsuarioModelo usuario = new UsuarioModelo();
                usuario.setId_usuario(rs.getInt("id_usuario"));
                usuario.setTipodeiden(rs.getString("tipodeiden"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                usuario.setCorreo_electronico(rs.getString("correo_electronico"));
                usuario.setTelefono(rs.getString("telefono"));
                usuario.setRol(rs.getString("rol"));
                usuario.setImagen(rs.getBytes("imagen"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al obtener usuarios: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeUsuario(String usuario) {
        boolean existe = false;
        String sql = "SELECT id_usuario FROM usuario WHERE usuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar existencia del usuario: " + e.getMessage());
        }
        return existe;
    }

    public boolean editar(UsuarioModelo objeto, int id_usuario) {
        if (objeto.getId_usuario() <= 0 || id_usuario <= 0) {
            JOptionPane.showMessageDialog(null, "ID de usuario inválido.");
            return false;
        }
        // Check if the email is already used by another user
        if (existeCorreo(objeto.getCorreo_electronico(), id_usuario)) {
            JOptionPane.showMessageDialog(null, "El correo electrónico ya está en uso por otro usuario.");
            return false;
        }
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection();
             PreparedStatement consulta = con.prepareStatement(
                 "UPDATE usuario SET id_usuario = ?, tipodeiden = ?, imagen = ?, nombre = ?, apellido = ?, usuario = ?, correo_electronico = ?, telefono = ?, rol = ? WHERE id_usuario = ?")) {
            consulta.setInt(1, objeto.getId_usuario());
            consulta.setString(2, objeto.getTipodeiden());
            consulta.setBytes(3, objeto.getImagen());
            consulta.setString(4, objeto.getNombre());
            consulta.setString(5, objeto.getApellido());
            consulta.setString(6, objeto.getUsuario());
            consulta.setString(7, objeto.getCorreo_electronico());
            consulta.setString(8, objeto.getTelefono());
            consulta.setString(9, objeto.getRol());
            consulta.setInt(10, id_usuario);

            respuesta = consulta.executeUpdate() > 0;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al editar usuario: " + e.getMessage());
        }
        return respuesta;
    }

    public boolean eliminar(int id_usuario) {
        if (id_usuario <= 0) {
            JOptionPane.showMessageDialog(null, "ID de usuario inválido.");
            return false;
        }
        boolean respuesta = false;
        try (Connection con = Conexion.getConnection();
             PreparedStatement consulta = con.prepareStatement("DELETE FROM usuario WHERE id_usuario = ?")) {
            consulta.setInt(1, id_usuario);
            if (consulta.executeUpdate() > 0) {
                respuesta = true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar usuario: " + e.getMessage());
        }
        return respuesta;
    }

    public boolean existeIdUsuario(String idUsuario) {
        boolean existe = false;
        String sql = "SELECT id_usuario FROM usuario WHERE id_usuario = ?";
        try (Connection conn = Conexion.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            int id;
            try {
                id = Integer.parseInt(idUsuario);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "El número de identificación debe ser un valor numérico válido.");
                return false;
            }
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    existe = true;
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al verificar existencia del número de identificación: " + e.getMessage());
        }
        return existe;
    }
}