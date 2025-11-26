package Proyecto_BDII;

public class DetalleVenta {

    private String idDetalle;     // Ej: D1, D2, ...
    private String idVenta;       // FK a VENTAS
    private String idProducto;    // FK a PRODUCTOS
    private int cantidad;
    private double precioUnitario;
    private double subtotal;

    // --------------------------
    // CONSTRUCTORES
    // --------------------------

    public DetalleVenta() {}

    public DetalleVenta(String idDetalle, String idVenta, String idProducto,
                        int cantidad, double precioUnitario) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    // --------------------------
    // MÉTODOS
    // --------------------------

    // Calcula el subtotal cada vez que se actualiza cantidad o precio
    private void calcularSubtotal() {
        this.subtotal = cantidad * precioUnitario;
    }

    // --------------------------
    // GETTERS Y SETTERS
    // --------------------------

    public String getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(String idDetalle) {
        this.idDetalle = idDetalle;
    }

    public String getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(String idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    @Override
    public String toString() {
        return "DetalleVenta{" +
                "ID Detalle='" + idDetalle + '\'' +
                ", ID Venta='" + idVenta + '\'' +
                ", ID Producto='" + idProducto + '\'' +
                ", Cantidad=" + cantidad +
                ", Precio Unitario=" + precioUnitario +
                ", Subtotal=" + subtotal +
                '}';
    }
}
