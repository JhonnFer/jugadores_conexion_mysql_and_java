package clases;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ViewPlayersPanel extends JPanel {
    public ViewPlayersPanel() {
        setLayout(new BorderLayout());

        String[] columnNames = {"ID", "Nombre", "Posición", "Equipo", "Edad"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        try (Connection connection = DatabaseConfig.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM Jugadores")) {

            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("posicion"),
                        resultSet.getString("equipo"),
                        resultSet.getInt("edad")
                };
                tableModel.addRow(row);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar jugadores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

