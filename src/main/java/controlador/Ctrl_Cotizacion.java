package controlador;

import modelo.Cotizacion;
import modelo.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import vista.Cotizacion.cotizacion;

public class Ctrl_Cotizacion {

    private cotizacion vista;
    private CotizacionDAO cotizacionDAO;
    public static final int USUARIO_ID = 1; // ID del usuario logueado (ajustar según autenticación)

    public Ctrl_Cotizacion(cotizacion vista) {
        this.vista = vista;
        this.cotizacionDAO = new CotizacionDAO();
    }

   public int guardarCotizacion(Integer clienteCodigo, DefaultTableModel modeloTabla, double totalGeneral) {
    System.out.println("Intentando guardar cotización - Cliente: " + clienteCodigo + ", Total: " + totalGeneral + ", Filas: " + modeloTabla.getRowCount());

    if (modeloTabla.getRowCount() == 0 || clienteCodigo == null) {
        return -1;
    }

    List<Cotizacion> cotizaciones = new ArrayList<>();
    for (int i = 0; i < modeloTabla.getRowCount(); i++) {
        Cotizacion cot = new Cotizacion();
        try {
            // Configurar los datos de la cotización...
            cotizaciones.add(cot);
        } catch (Exception e) {
            return -1;
        }
    }

    try {
        int idCotizacion = cotizacionDAO.guardarCotizaciones(cotizaciones);
        return idCotizacion;
    } catch (Exception e) {
        return -1;
    }
}

   
    private boolean verificarClienteExistente(int clienteCodigo) {
        try (Connection con = Conexion.getConnection(); PreparedStatement pstmt = con.prepareStatement("SELECT 1 FROM cliente WHERE codigo = ?")) {
            pstmt.setInt(1, clienteCodigo);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cotizacion> obtenerCotizaciones() {
        return cotizacionDAO.obtenerCotizaciones();
    }
}
