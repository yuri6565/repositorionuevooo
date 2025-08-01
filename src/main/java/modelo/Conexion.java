package modelo;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    public static Connection getConnection() {
        Connection con = null;
        try {
            String servidor = "192.168.1.6";
            String puerto = "3306";
            String bd = "carpinteriasistema";
            String usuario = "usercarpinteria";
            String clave = "joseabel123";

            String url = "jdbc:mysql://" + servidor + ":" + puerto + "/" + bd
                    + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

            con = DriverManager.getConnection(url, usuario, clave);

        } catch (SQLException e) {
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return con;
    }

    public static String hashSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes("UTF-8"));
            StringBuilder hex = new StringBuilder();
            for (byte b : hash) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}