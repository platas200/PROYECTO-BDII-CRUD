package Proyecto_BDII;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Venta {
    private Connection con;

    public Venta() {
        con = Conexion.getConexion();
    }

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
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
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
            if(res > 0)
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
            if(res > 0)
                JOptionPane.showMessageDialog(null, "Venta eliminada correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró la venta con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar venta: " + e.getMessage());
        }
    }
}
