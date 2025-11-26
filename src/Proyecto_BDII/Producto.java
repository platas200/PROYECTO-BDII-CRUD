package Proyecto_BDII;

import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

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

    // --- MÉTODOS AÑADIDOS PARA COMPATIBILIDAD CON TU MAIN ---

    // Alias para mantener compatibilidad (Main llama a producto.Eliminar)
    public void Eliminar(String id) {
        Borrar(id);
    }

    // Llena un JComboBox con "ID - NOMBRE"
    public void CargarCombo(JComboBox<String> combo) {
        combo.removeAllItems();
        String sql = "SELECT IDPRODUCTO, NOMBREPRODUCTO FROM PRODUCTO";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                combo.addItem(rs.getString("IDPRODUCTO") + " - " + rs.getString("NOMBREPRODUCTO"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Obtiene el precio directo desde BD (por ID o por formato "ID - Nombre")
    public double getPrecio(String idProducto) {
        if (idProducto == null) return 0.0;
        if (idProducto.contains(" - ")) idProducto = idProducto.split(" - ")[0];

        String sql = "SELECT PRECIO FROM PRODUCTO WHERE IDPRODUCTO = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("PRECIO");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Obtiene nombre por id
    public String getNombre(String idProducto) {
        if (idProducto == null) return "";
        if (idProducto.contains(" - ")) idProducto = idProducto.split(" - ")[0];

        String sql = "SELECT NOMBREPRODUCTO FROM PRODUCTO WHERE IDPRODUCTO = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("NOMBREPRODUCTO");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    // Obtener stock actual
    public int getStock1(String idProducto) {
        if (idProducto == null) return 0;
        if (idProducto.contains(" - ")) idProducto = idProducto.split(" - ")[0];

        String sql = "SELECT STOCK FROM PRODUCTO WHERE IDPRODUCTO = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("STOCK");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Busca producto por nombre exacto y devuelve fila (ID, Nombre, Desc, Precio, Stock)
    public Object[] obtenerProductoPorNombreExacto(String nombreProd) {
        String sql = "SELECT IDPRODUCTO, NOMBREPRODUCTO, DESCRIPCION, PRECIO, STOCK FROM PRODUCTO WHERE NOMBREPRODUCTO = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombreProd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Object[] {
                    rs.getString("IDPRODUCTO"),
                    rs.getString("NOMBREPRODUCTO"),
                    rs.getString("DESCRIPCION"),
                    rs.getDouble("PRECIO"),
                    rs.getInt("STOCK")
                };
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<String> obtenerListaProductos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT IDPRODUCTO, NOMBREPRODUCTO FROM PRODUCTO";
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(rs.getString("IDPRODUCTO") + " - " + rs.getString("NOMBREPRODUCTO"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener productos: " + e.getMessage());
        }
        return lista;
    }

    // Devuelve precio del producto por ID
    public double getPrecio1(String idProducto) {
        String sql = "SELECT PRECIO FROM PRODUCTO WHERE IDPRODUCTO=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getDouble("PRECIO");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener precio: " + e.getMessage());
        }
        return 0;
    }

    // Devuelve stock del producto por ID
    public int getStock(String idProducto) {
        String sql = "SELECT STOCK FROM PRODUCTO WHERE IDPRODUCTO=?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, idProducto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("STOCK");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al obtener stock: " + e.getMessage());
        }
        return 0;
    }
}
