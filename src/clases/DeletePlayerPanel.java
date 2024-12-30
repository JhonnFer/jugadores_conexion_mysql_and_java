package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class DeletePlayerPanel extends JPanel {
    private JTextField idField;

    public DeletePlayerPanel() {
        setLayout(new GridLayout(2, 2, 10, 10));

        add(new JLabel("ID del Jugador a Eliminar:"));
        idField = new JTextField();
        add(idField);

        JButton deleteButton = new JButton("Eliminar Jugador");
        deleteButton.addActionListener(new DeletePlayerActionListener());
        add(deleteButton);
    }

    private class DeletePlayerActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String idText = idField.getText();

            try {
                int id = Integer.parseInt(idText);

                try (Connection connection = DatabaseConfig.getConnection();
                     PreparedStatement statement = connection.prepareStatement("DELETE FROM Jugadores WHERE id = ?")) {
                    statement.setInt(1, id);
                    int rowsDeleted = statement.executeUpdate();

                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(DeletePlayerPanel.this, "Jugador eliminado con éxito.");
                    } else {
                        JOptionPane.showMessageDialog(DeletePlayerPanel.this, "No se encontró un jugador con ese ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    idField.setText("");
                }

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(DeletePlayerPanel.this, "ID inválido. Debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(DeletePlayerPanel.this, "Error al eliminar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

