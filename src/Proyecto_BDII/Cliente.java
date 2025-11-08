package Proyecto_BDII;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Cliente {
    private Connection con;

    public Cliente() {
        con = Conexion.getConexion();
    }

    public void Crear(String id, String nombre, String apellido, String telefono, String direccion) {
        String sql = "INSERT INTO CLIENTE (IDCLIENTE, NOMBRECLIENTE, APELLIDOCLIENTE, TELEFONO, DIRECCION) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, direccion);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cliente creado correctamente.");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al crear cliente: " + e.getMessage());
        }
    }

    public void Leer(JTable tabla) {
        String[] columnas = {"ID", "Nombre", "Apellido", "Teléfono", "Dirección"};
        DefaultTableModel modelo = new DefaultTableModel(null, columnas);
        String sql = "SELECT * FROM CLIENTE";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Object[] fila = {
                    rs.getString("IDCLIENTE"),
                    rs.getString("NOMBRECLIENTE"),
                    rs.getString("APELLIDOCLIENTE"),
                    rs.getString("TELEFONO"),
                    rs.getString("DIRECCION")
                };
                modelo.addRow(fila);
            }
            tabla.setModel(modelo);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al leer clientes: " + e.getMessage());
        }
    }

    public void Actualizar(String id, String nombre, String apellido, String telefono, String direccion) {
        String sql = "UPDATE CLIENTE SET NOMBRECLIENTE=?, APELLIDOCLIENTE=?, TELEFONO=?, DIRECCION=? WHERE IDCLIENTE=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, direccion);
            ps.setString(5, id);
            int res = ps.executeUpdate();
            if(res > 0)
                JOptionPane.showMessageDialog(null, "Cliente actualizado correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void Borrar(String id) {
        String sql = "DELETE FROM CLIENTE WHERE IDCLIENTE=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, id);
            int res = ps.executeUpdate();
            if(res > 0)
                JOptionPane.showMessageDialog(null, "Cliente eliminado correctamente.");
            else
                JOptionPane.showMessageDialog(null, "No se encontró el cliente con ID: " + id);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar cliente: " + e.getMessage());
        }
    }
}
