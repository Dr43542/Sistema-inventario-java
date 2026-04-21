package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {

    public static Connection conectar() {
        try {
            String url = "jdbc:mysql://localhost:3306/tienda";
            String user = "root";
            String pass = "Root";

            return DriverManager.getConnection(url, user, pass);

        } catch (Exception e) {
            System.out.println("Error en conexión: " + e);
            return null;
        }
    }
}
