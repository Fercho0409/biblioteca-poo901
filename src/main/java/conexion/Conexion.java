package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL      = "jdbc:mysql://localhost:3306/biblioteca_db";
    private static final String USUARIO  = "root";
    private static final String PASSWORD = "1234";

    public static Connection getConexion() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: No se encontró el driver de MySQL.");
            System.out.println("Agrega el conector JDBC al proyecto.");
        } catch (SQLException e) {
            System.out.println("ERROR: No se pudo conectar a la base de datos.");
            System.out.println(e.getMessage());
        }
        return con;
    }
}