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

public class CotizacionDAO {

    public static int guardarCotizacion(Cotizacion cotizacion) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false); // Iniciar transacción

            // Insertar la cotización
            String sql = "INSERT INTO cotizacion (detalle, unidad, cantidad, fecha, valor_unitario, sub_total, total, usuario_id_usuario, cliente_codigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, cotizacion.getDetalle());
            pstmt.setString(2, cotizacion.getUnidad());
            pstmt.setInt(3, cotizacion.getCantidad());
            pstmt.setDate(4, new java.sql.Date(cotizacion.getFecha().getTime()));
            pstmt.setDouble(5, cotizacion.getValor_unitario());
            pstmt.setDouble(6, cotizacion.getSub_total());
            pstmt.setDouble(7, cotizacion.getTotal());
            pstmt.setObject(8, cotizacion.getUsuario_id_usuario()); // Permite null
            pstmt.setObject(9, cotizacion.getCliente_codigo());    // Permite null

            int filasAfectadas = pstmt.executeUpdate();
            if (filasAfectadas == 0) {
                throw new SQLException("No se pudo insertar la cotización");
            }

            // Obtener el ID generado
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
                System.out.println("DEBUG: ID generado: " + idGenerado);
            } else {
                throw new SQLException("No se pudo obtener el ID de la cotización");
            }

            con.commit(); // Confirmar transacción
            return idGenerado;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                    System.out.println("DEBUG: Rollback realizado por: " + e.getMessage());
                }
            } catch (SQLException ex) {
                System.err.println("Error al hacer rollback: " + ex.getMessage());
            }
            System.err.println("Error al guardar cotización: " + e.getMessage());
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
    }

    public static int guardarCotizaciones(List<Cotizacion> cotizaciones) {
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int idGenerado = -1;

        try {
            con = Conexion.getConnection();
            con.setAutoCommit(false);

            if (cotizaciones.isEmpty()) {
                throw new SQLException("La lista de cotizaciones está vacía");
            }

            String sql = "INSERT INTO cotizacion (detalle, unidad, cantidad, fecha, valor_unitario, sub_total, total, usuario_id_usuario, cliente_codigo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

            for (Cotizacion cot : cotizaciones) {
                pstmt.setString(1, cot.getDetalle());
                pstmt.setString(2, cot.getUnidad());
                pstmt.setInt(3, cot.getCantidad());
                pstmt.setDate(4, new java.sql.Date(cot.getFecha().getTime()));
                pstmt.setDouble(5, cot.getValor_unitario());
                pstmt.setDouble(6, cot.getSub_total());
                pstmt.setDouble(7, cot.getTotal());
                pstmt.setObject(8, cot.getUsuario_id_usuario()); // Permite null
                pstmt.setObject(9, cot.getCliente_codigo());    // Permite null
                pstmt.addBatch();
            }

            int[] filasAfectadas = pstmt.executeBatch();
            if (filasAfectadas.length != cotizaciones.size()) {
                throw new SQLException("No se insertaron todas las cotizaciones");
            }

            // Obtener el ID del último insertado (puede variar según la base de datos)
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                idGenerado = rs.getInt(1);
            }

            con.commit();
            return idGenerado;
        } catch (SQLException e) {
            try {
                if (con != null) {
                    con.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return -1;
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Cotizacion> obtenerCotizaciones() {
        List<Cotizacion> cotizaciones = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = Conexion.getConnection();
            String sql = "SELECT * FROM cotizacion ORDER BY id_cotizacion DESC";
            pstmt = con.prepareStatement(sql);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cotizacion cot = new Cotizacion();
                cot.setId_cotizacion(rs.getInt("id_cotizacion"));
                cot.setDetalle(rs.getString("detalle"));
                cot.setUnidad(rs.getString("unidad"));
                cot.setCantidad(rs.getInt("cantidad"));
                cot.setFecha(rs.getDate("fecha"));
                cot.setValor_unitario(rs.getDouble("valor_unitario"));
                cot.setSub_total(rs.getDouble("sub_total"));
                cot.setTotal(rs.getDouble("total"));
                cot.setUsuario_id_usuario(rs.getObject("usuario_id_usuario") != null ? rs.getInt("usuario_id_usuario") : null);
                cot.setCliente_codigo(rs.getObject("cliente_codigo") != null ? rs.getInt("cliente_codigo") : null);
                cotizaciones.add(cot);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener cotizaciones: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error al cargar las cotizaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                System.out.println("Error al cerrar conexiones: " + e.getMessage());
            }
        }
        return cotizaciones;
    }
}