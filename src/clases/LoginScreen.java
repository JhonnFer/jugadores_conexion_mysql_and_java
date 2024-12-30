package clases;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginScreen {
    private JFrame Panel; // Ventana principal de la aplicación
    private JTextField usernameField; // Campo para el nombre de usuario
    private JPasswordField passwordField; // Campo para la contraseña

    // Constructor que configura la interfaz
    public LoginScreen() {
        Panel = new JFrame("Login"); // Título de la ventana
        Panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Panel.setSize(300, 200); // Tamaño de la ventana

        // Crear el panel donde se agregan los componentes
        JPanel panel = new JPanel();
        Panel.add(panel);
        placeComponents(panel); // Llamada a la función para colocar los componentes

        Panel.setVisible(true); // Hacer visible la ventana
    }

    // Función para colocar los componentes dentro del panel
    private void placeComponents(JPanel panel) {
        panel.setLayout(null); // Deshabilita el LayoutManager, para colocar los componentes manualmente

        JLabel userLabel = new JLabel("Usuario:");
        userLabel.setBounds(10, 20, 80, 25); // Posición y tamaño del label de usuario
        panel.add(userLabel);

        usernameField = new JTextField(20);
        usernameField.setBounds(100, 20, 165, 25); // Posición y tamaño del campo de texto para el usuario
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Contraseña:");
        passwordLabel.setBounds(10, 50, 80, 25); // Posición y tamaño del label de contraseña
        panel.add(passwordLabel);

        passwordField = new JPasswordField(20);
        passwordField.setBounds(100, 50, 165, 25); // Posición y tamaño del campo de texto para la contraseña
        panel.add(passwordField);

        JButton loginButton = new JButton("Login"); // Botón de login
        loginButton.setBounds(10, 80, 80, 25); // Posición y tamaño del botón
        panel.add(loginButton);

        JButton registerButton = new JButton("Registrar"); // Botón de registro
        registerButton.setBounds(100, 80, 165, 25); // Posición y tamaño del botón
        panel.add(registerButton);

        // Acción a ejecutar cuando el usuario hace clic en el botón de login
        loginButton.addActionListener(new LoginActionListener());

        // Acción a ejecutar cuando el usuario hace clic en el botón de registro
        registerButton.addActionListener(new RegisterActionListener());
    }

    // Clase interna que maneja el evento de login
    private class LoginActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText(); // Obtener el texto del campo de usuario
            String password = new String(passwordField.getPassword()); // Obtener la contraseña

            // Intentar conectar a la base de datos y verificar el login
            try (Connection connection = DatabaseConfig.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM Usuarios WHERE usuario = ? AND contrasena = ?")) {

                statement.setString(1, username); // Establecer el usuario en la consulta
                statement.setString(2, password); // Establecer la contraseña en la consulta
                ResultSet resultSet = statement.executeQuery(); // Ejecutar la consulta

                // Si se encuentra el usuario y la contraseña
                if (resultSet.next()) {
                    JOptionPane.showMessageDialog(Panel, "Login exitoso!"); // Mensaje de éxito
                    Panel.dispose(); // Cerrar la ventana de login
                    new DashboardScreen(); // Crear una nueva ventana para el Dashboard
                } else {
                    JOptionPane.showMessageDialog(Panel, "Usuario o contraseña incorrectos.", "Error", JOptionPane.ERROR_MESSAGE); // Error de login
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Panel, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE); // Error de conexión
            }
        }
    }

    // Clase interna que maneja el evento de registro
    private class RegisterActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText(); // Obtener el texto del campo de usuario
            String password = new String(passwordField.getPassword()); // Obtener la contraseña

            // Intentar conectar a la base de datos e insertar el nuevo usuario
            try (Connection connection = DatabaseConfig.getConnection();
                 PreparedStatement statement = connection.prepareStatement("INSERT INTO Usuarios (usuario, contrasena) VALUES (?, ?)")) {

                statement.setString(1, username); // Establecer el usuario en la consulta
                statement.setString(2, password); // Establecer la contraseña en la consulta

                int rowsAffected = statement.executeUpdate(); // Ejecutar la consulta de inserción

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(Panel, "Usuario registrado exitosamente!");
                } else {
                    JOptionPane.showMessageDialog(Panel, "Error al registrar el usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Panel, "Error de conexión a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método main para ejecutar la pantalla de login
    public static void main(String[] args) {
        new LoginScreen(); // Crear una nueva instancia de la pantalla de login
    }
}
