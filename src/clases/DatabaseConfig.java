package clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfig {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/football_db";
    private static final String DB_USER = "root";  // Cambia por tu usuario
    private static final String DB_PASSWORD = "12345";  // Cambia por tu contraseña

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
