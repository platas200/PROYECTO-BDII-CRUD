package Proyecto_BDII;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/Proyecto_BDII";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Siempre retorna una NUEVA conexión
    public static Connection getConexion() {
        try {
            Connection nuevaConexion = DriverManager.getConnection(URL, USER, PASSWORD);
            // System.out.println("🔄 Nueva conexión abierta correctamente.");
            return nuevaConexion;
        } catch (SQLException e) {
            System.out.println("❌ Error abriendo conexión: " + e.getMessage());
            return null;
        }
    }
}
