package clases;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SearchPlayerPanel extends JPanel {
    private JTextField searchField;
    private JTextArea resultArea;

    public SearchPlayerPanel() {
        setLayout(new BorderLayout(10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout());
        searchPanel.add(new JLabel("Buscar por Nombre:"));
        searchField = new JTextField(15);
        searchPanel.add(searchField);
        JButton searchButton = new JButton("Buscar");
        searchButton.addActionListener(new SearchActionListener());
        searchPanel.add(searchButton);

        add(searchPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private class SearchActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String name = searchField.getText();
            resultArea.setText("");

            try (Connection connection = DatabaseConfig.getConnection();
                 PreparedStatement statement = connection.prepareStatement("SELECT * FROM Jugadores WHERE nombre LIKE ?")) {
                statement.setString(1, "%" + name + "%");
                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    String result = String.format("ID: %d | Nombre: %s | Posición: %s | Equipo: %s | Edad: %d\n",
                            resultSet.getInt("id"),
                            resultSet.getString("nombre"),
                            resultSet.getString("posicion"),
                            resultSet.getString("equipo"),
                            resultSet.getInt("edad"));
                    resultArea.append(result);
                }

                if (resultArea.getText().isEmpty()) {
                    resultArea.setText("No se encontraron resultados.");
                }

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(SearchPlayerPanel.this, "Error al buscar el jugador.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
