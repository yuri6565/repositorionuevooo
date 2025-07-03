package modelo;

import java.util.Date;

public class Cotizacion {
    private int id_cotizacion;
    private String detalle;
    private String unidad;
    private int cantidad;
    private Date fecha;
    private double valor_unitario;
    private double sub_total;
    private double total;
    private Integer usuario_id_usuario; // Usamos Integer para permitir null
    private Integer cliente_codigo;    // Usamos Integer para permitir null

    public Cotizacion() {
    }

    public Cotizacion(int id_cotizacion, String detalle, String unidad, int cantidad, Date fecha,
                      double valor_unitario, double sub_total, double total, Integer usuario_id_usuario, Integer cliente_codigo) {
        this.id_cotizacion = id_cotizacion;
        this.detalle = detalle;
        this.unidad = unidad;
        this.cantidad = cantidad;
        this.fecha = fecha;
        this.valor_unitario = valor_unitario;
        this.sub_total = sub_total;
        this.total = total;
        this.usuario_id_usuario = usuario_id_usuario;
        this.cliente_codigo = cliente_codigo;
    }

    // Getters y Setters
    public int getId_cotizacion() {
        return id_cotizacion;
    }

    public void setId_cotizacion(int id_cotizacion) {
        this.id_cotizacion = id_cotizacion;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getValor_unitario() {
        return valor_unitario;
    }

    public void setValor_unitario(double valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public double getSub_total() {
        return sub_total;
    }

    public void setSub_total(double sub_total) {
        this.sub_total = sub_total;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Integer getUsuario_id_usuario() {
        return usuario_id_usuario;
    }

    public void setUsuario_id_usuario(Integer usuario_id_usuario) {
        this.usuario_id_usuario = usuario_id_usuario;
    }

    public Integer getCliente_codigo() {
        return cliente_codigo;
    }

    public void setCliente_codigo(Integer cliente_codigo) {
        this.cliente_codigo = cliente_codigo;
    }
}