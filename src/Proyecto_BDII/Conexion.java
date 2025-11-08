package Proyecto_BDII;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:mysql://localhost:3306/Proyecto_BDII";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static Connection con = null;

    // Método estático que devuelve la conexión
    public static Connection getConexion() {
        if (con == null) {
            try {
                con = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Conexión exitosa a la base de datos.");
            } catch (SQLException e) {
                System.out.println("❌ Error en la conexión: " + e.getMessage());
            }
        }
        return con;
    }
}
