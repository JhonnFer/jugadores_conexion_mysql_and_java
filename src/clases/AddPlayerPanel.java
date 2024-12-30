package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class AddPlayerPanel extends JPanel {
    private JTextField nameField;
    private JTextField positionField;
    private JTextField teamField;
    private JTextField ageField;

    public AddPlayerPanel() {
        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Nombre:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Posición:"));
        positionField = new JTextField();
        add(positionField);

        add(new JLabel("Equipo:"));
        teamField = new JTextField();
        add(teamField);

        add(new JLabel("Edad:"));
        ageField = new JTextField();
        add(ageField);

        JButton addButton = new JButton("Agregar Jugador");
        addButton.addActionListener(new AddPlayerActionListener());
        add(addButton);
    }

    private class AddPlayerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = nameField.getText();
            String position = positionField.getText();
            String team = teamField.getText();
            String ageText = ageField.getText();

            try {
                int age = Integer.parseInt(ageText);

                try (Connection connection = DatabaseConfig.getConnection();
                     PreparedStatement statement = connection.prepareStatement(
                             "INSERT INTO Jugadores (nombre, posicion, equipo, edad) VALUES (?, ?, ?, ?)")) {
                    statement.setString(1, name);
                    statement.setString(2, position);
                    statement.setString(3, team);
                    statement.setInt(4, age);

                    statement.executeUpdate();
                    JOptionPane.showMessageDialog(AddPlayerPanel.this, "Jugador agregado con éxito.");
                    nameField.setText("");
                    positionField.setText("");
                    teamField.setText("");
                    ageField.setText("");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(AddPlayerPanel.this, "Edad inválida. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(AddPlayerPanel.this, "Error al agregar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
