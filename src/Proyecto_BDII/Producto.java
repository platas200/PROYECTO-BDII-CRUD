package Proyecto_BDII;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Producto {
    private Connection con;

    public Producto() {
        con = Conexion.getConexion();
    }

    public void Crear(String id, String nombre, String descripcion, double precio, int stock) {
        String sql = "INSERT INTO PRODUCTO (IDPRODUCTO, NOMBREPRODUCTO, DESCRIPCION, PRECIO, STOCK) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, descripcion);
            ps.setDouble(4, precio);
            ps.setInt(5, stock);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Producto creado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear producto: " + e.getMessage());
        }
    }

    public void Leer(JTable tabla) {
        String[] columnas = {"ID", "Nombre", "Descripción", "Precio", "Stock"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT * FROM PRODUCTO";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("IDPRODUCTO"),
                    rs.getString("NOMBREPRODUCTO"),
                    rs.getString("DESCRIPCION"),
                    rs.getDouble("PRECIO"),
                    rs.getInt("STOCK")
                };
                modelo.addRow(fila);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer productos: " + e.getMessage());
        }
    }

    public void Actualizar(String id, String nombre, String descripcion, double precio, int stock) {
        String sql = "UPDATE PRODUCTO SET NOMBREPRODUCTO=?, DESCRIPCION=?, PRECIO=?, STOCK=? WHERE IDPRODUCTO=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, descripcion);
            ps.setDouble(3, precio);
            ps.setInt(4, stock);
            ps.setString(5, id);
            int res = ps.executeUpdate();
            if(res > 0)
                JOptionPane.showMessageDialog(null, "Producto actualizado correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró el producto con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar producto: " + e.getMessage());
        }
    }

    public void Borrar(String id) {
        String sql = "DELETE FROM PRODUCTO WHERE IDPRODUCTO=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int res = ps.executeUpdate();
            if(res > 0)
                JOptionPane.showMessageDialog(null, "Producto eliminado correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró el producto con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
        }
    }

}
