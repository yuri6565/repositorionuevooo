package controlador;

import modelo.UsuarioModelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import modelo.Conexion;

public class Contrl_login {

    public UsuarioModelo loginUser(UsuarioModelo usuario) {
    Connection con = Conexion.getConnection();
    PreparedStatement ps = null;
    ResultSet rs = null;
    UsuarioModelo usuarioAutenticado = null;

    try {
        // 1. Hashear la contraseña ingresada antes de usarla en la consulta
        String contraseñaHasheada = Conexion.hashSHA256(usuario.getContrasena());

        // 2. Hacer la consulta con el hash
        String sql = "SELECT * FROM usuario WHERE usuario = ? AND contrasena = ?";
        ps = con.prepareStatement(sql);
        ps.setString(1, usuario.getUsuario());
        ps.setString(2, contraseñaHasheada); // ← aquí ya va el hash

        rs = ps.executeQuery();

        if (rs.next()) {
            usuarioAutenticado = new UsuarioModelo();
            usuarioAutenticado.setId_usuario(rs.getInt("id_usuario"));
            usuarioAutenticado.setUsuario(rs.getString("usuario"));
            usuarioAutenticado.setContrasena(rs.getString("contrasena"));
            usuarioAutenticado.setNombre(rs.getString("nombre"));
            usuarioAutenticado.setApellido(rs.getString("apellido"));
            usuarioAutenticado.setCorreo_electronico(rs.getString("correo_electronico"));
            usuarioAutenticado.setTelefono(rs.getString("telefono"));
            usuarioAutenticado.setRol(rs.getString("rol"));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return usuarioAutenticado; // Devuelve el usuario completo o null si no se encuentra
}

}