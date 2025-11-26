package Proyecto_BDII;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Venta {
    private Connection con;

    public Venta() {
        con = Conexion.getConexion();
    }

    // ------------------- CRUD BÁSICO -------------------
    public void Crear(String id, String fecha, double total) {
        String sql = "INSERT INTO VENTA (IDVENTA, FECHAVENTA, TOTAL) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, fecha);
            ps.setDouble(3, total);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Venta creada correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear venta: " + e.getMessage());
        }
    }

    public void Leer(JTable tabla) {
        String[] columnas = {"ID", "Fecha", "Total"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT * FROM VENTA";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = {
                        rs.getString("IDVENTA"),
                        rs.getDate("FECHAVENTA"),
                        rs.getDouble("TOTAL")
                };
                modelo.addRow(fila);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer ventas: " + e.getMessage());
        }
    }

    public void Actualizar(String id, String fecha, double total) {
        String sql = "UPDATE VENTA SET FECHAVENTA=?, TOTAL=? WHERE IDVENTA=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, fecha);
            ps.setDouble(2, total);
            ps.setString(3, id);
            int res = ps.executeUpdate();
            if (res > 0)
                JOptionPane.showMessageDialog(null, "Venta actualizada correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró la venta con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar venta: " + e.getMessage());
        }
    }

    public void Borrar(String id) {
        String sql = "DELETE FROM VENTA WHERE IDVENTA=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int res = ps.executeUpdate();
            if (res > 0)
                JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró la venta con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e.getMessage());
        }
    }

    // ------------------- REGISTRAR VENTA COMPLETA -------------------
    public boolean registrarVenta(String idVenta, String fecha, List<DetalleVenta> detalles) {

        String sqlVenta = "INSERT INTO VENTA (IDVENTA, FECHAVENTA, TOTAL) VALUES (?, ?, ?)";
        String sqlDetalle = "INSERT INTO DETALLEVENTA (IDDETALLE, IDVENTA, IDPRODUCTO, CANTIDAD, PRECIO_UNITARIO, SUBTOTAL) VALUES (?, ?, ?, ?, ?, ?)";
        String sqlDescontar = "UPDATE PRODUCTO SET STOCK = STOCK - ? WHERE IDPRODUCTO = ?";

        try {
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            con.setAutoCommit(false); // INICIO DE TRANSACCIÓN

            double total = 0;

            // ---------- VALIDAR STOCK ----------
            for (DetalleVenta d : detalles) {
                String sqlBloqueo = "SELECT STOCK FROM PRODUCTO WHERE IDPRODUCTO=? FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(sqlBloqueo)) {
                    ps.setString(1, d.getIdProducto());
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new Exception("El producto " + d.getIdProducto() + " no existe.");
                    }
                    int stockActual = rs.getInt("STOCK");
                    if (stockActual < d.getCantidad()) {
                        throw new Exception("Stock insuficiente para producto " + d.getIdProducto() +
                                ". Disponible: " + stockActual + ", solicitado: " + d.getCantidad());
                    }
                }
                total += d.getSubtotal();
            }

            // ---------- INSERTAR VENTA ----------
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta)) {
                psVenta.setString(1, idVenta);
                psVenta.setString(2, fecha);
                psVenta.setDouble(3, total);
                psVenta.executeUpdate();
            }

            // ---------- INSERTAR DETALLES ----------
            int contadorDetalle = 1;
            for (DetalleVenta d : detalles) {
                String idDetalle = idVenta + "-D" + contadorDetalle;
                try (PreparedStatement psDet = con.prepareStatement(sqlDetalle);
                     PreparedStatement psStock = con.prepareStatement(sqlDescontar)) {

                    psDet.setString(1, idDetalle);
                    psDet.setString(2, idVenta);
                    psDet.setString(3, d.getIdProducto());
                    psDet.setInt(4, d.getCantidad());
                    psDet.setDouble(5, d.getPrecioUnitario());
                    psDet.setDouble(6, d.getSubtotal());
                    psDet.executeUpdate();

                    psStock.setInt(1, d.getCantidad());
                    psStock.setString(2, d.getIdProducto());
                    psStock.executeUpdate();
                }
                contadorDetalle++;
            }

            con.commit();
            JOptionPane.showMessageDialog(null, "Venta registrada exitosamente.");
            return true;

        } catch (Exception e) {
            try { con.rollback(); } catch (Exception ignored) {}
            JOptionPane.showMessageDialog(null, "Error en la venta: " + e.getMessage(),
                    "Transacción cancelada", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try { con.setAutoCommit(true); } catch (SQLException ignored) {}
        }
    }

    // ------------------- REALIZAR VENTA CON CONCURRENCIA -------------------
    public boolean realizarVenta(String idCliente, String nombreCliente, List<String> productos, List<Integer> cantidades) {

        Connection con = null;

        try {
            con = Conexion.getConexion();
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            con.setAutoCommit(false);

            // Generar ID
            String idVenta = generarIdVenta();
            double total = 0;

            // Consolidar repetidos
            Map<String, Integer> productosConsolidados = new HashMap<>();
            for (int i = 0; i < productos.size(); i++) {
                String idProd = productos.get(i).split(" - ")[0];
                int cantidad = cantidades.get(i);
                productosConsolidados.merge(idProd, cantidad, Integer::sum);
            }

            // Ordenar ids para evitar deadlocks
            List<String> idsOrdenados = new ArrayList<>(productosConsolidados.keySet());
            Collections.sort(idsOrdenados);

            Map<String, Double> precios = new HashMap<>();
            Map<String, Integer> stockActualMap = new HashMap<>();

            // VALIDAR Y BLOQUEAR STOCK
            for (String idProd : idsOrdenados) {
                int cantidadTotal = productosConsolidados.get(idProd);

                String sqlStock = "SELECT STOCK, PRECIO FROM PRODUCTO WHERE IDPRODUCTO=? FOR UPDATE";
                try (PreparedStatement ps = con.prepareStatement(sqlStock)) {
                    ps.setString(1, idProd);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next())
                            throw new SQLException("Producto no encontrado: " + idProd);

                        int stockActual = rs.getInt("STOCK");
                        double precioUnitario = rs.getDouble("PRECIO");

                        // Guardar
                        stockActualMap.put(idProd, stockActual);
                        precios.put(idProd, precioUnitario);

                        // ❗❗❗ --- AQUI VA EL BLOQUEO DE CONCURRENCIA ---
                        System.out.println("DEBUG: Bloqueando producto " + idProd +
                                ". Hilo: " + Thread.currentThread().getName() +
                                " -> Esperando 10 segundos...");

                        try { Thread.sleep(10000); } catch (InterruptedException ie) {}

                        // Validar stock
                        if (cantidadTotal > stockActual) {
                            JOptionPane.showMessageDialog(null,
                                    "Stock insuficiente para el cliente " + idCliente +
                                            " - " + nombreCliente + " en producto " + idProd +
                                            ": solo puede agregar " + stockActual + " unidades en total",
                                    "Error de venta", JOptionPane.WARNING_MESSAGE);
                            con.rollback();
                            return false;
                        }

                        total += cantidadTotal * precioUnitario;
                    }
                }
            }

            // INSERTAR VENTA
            String sqlVenta = "INSERT INTO VENTA(IDVENTA, FECHAVENTA, TOTAL) VALUES(?, ?, ?)";
            try (PreparedStatement psVenta = con.prepareStatement(sqlVenta)) {
                psVenta.setString(1, idVenta);
                psVenta.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                psVenta.setDouble(3, total);
                psVenta.executeUpdate();
            }

            // INSERTAR DETALLES
            String sqlDetalle = "INSERT INTO DETALLEVENTA(IDDETALLE, IDVENTA, IDPRODUCTO, CANTIDAD, PRECIO_UNITARIO, SUBTOTAL) VALUES(?, ?, ?, ?, ?, ?)";
            String sqlActualizarStock = "UPDATE PRODUCTO SET STOCK = STOCK - ? WHERE IDPRODUCTO=?";

            int detalleIndex = 1;
            for (int i = 0; i < productos.size(); i++) {
                String idProd = productos.get(i).split(" - ")[0];
                int cantidad = cantidades.get(i);
                double precioUnitario = precios.get(idProd);
                double subtotal = cantidad * precioUnitario;
                String idDetalle = "D" + (detalleIndex++) + "-" + idVenta;

                // Insert detalle
                try (PreparedStatement psDet = con.prepareStatement(sqlDetalle)) {
                    psDet.setString(1, idDetalle);
                    psDet.setString(2, idVenta);
                    psDet.setString(3, idProd);
                    psDet.setInt(4, cantidad);
                    psDet.setDouble(5, precioUnitario);
                    psDet.setDouble(6, subtotal);
                    psDet.executeUpdate();
                }

                // update stock
                try (PreparedStatement psStock = con.prepareStatement(sqlActualizarStock)) {
                    psStock.setInt(1, cantidad);
                    psStock.setString(2, idProd);
                    psStock.executeUpdate();
                }
            }

            con.commit();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            try { if (con != null) con.rollback(); } catch (Exception ex) {}
            return false;
        } finally {
            try { if (con != null) con.setAutoCommit(true); } catch (Exception e) {}
        }
    }

    // ------------------- GENERADOR DE IDS -------------------
    private String generarIdVenta() {
        String id = "V1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement("SELECT IDVENTA FROM VENTA ORDER BY IDVENTA DESC LIMIT 1");
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String ultimoId = rs.getString("IDVENTA");
                int numero = Integer.parseInt(ultimoId.replace("V", ""));
                id = "V" + (numero + 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    public int obtenerUltimoId() {
        String sql = "SELECT COUNT(*) AS c FROM VENTA";
        try (Statement st = con.createStatement(); 
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt("c");
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public String obtenerNuevoIdVenta() {
        int c = obtenerUltimoId();
        return "V" + (c + 1);
    }
}
