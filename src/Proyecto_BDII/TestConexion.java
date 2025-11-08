package Proyecto_BDII;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection con = Conexion.conectar();
        if (con != null) {
            System.out.println("✅ Conexión a la base de datos verificada correctamente.");
        } else {
            System.out.println("❌ No se pudo establecer la conexión.");
        }
    }
}
