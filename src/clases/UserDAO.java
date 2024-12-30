package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {
    public static boolean registerUser(String username, String password) {
        String sql = "INSERT INTO Usuarios (usuario, contrasena) VALUES (?, ?)";

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // Establecer los parámetros de la consulta
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            // Ejecutar la consulta
            int rowsAffected = preparedStatement.executeUpdate();

            // Retorna verdadero si la inserción fue exitosa
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

