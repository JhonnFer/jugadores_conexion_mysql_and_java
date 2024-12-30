package clases;

import javax.swing.*;

public class DashboardScreen {
    private JFrame frame;

    public DashboardScreen() {
        frame = new JFrame("Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JTabbedPane tabbedPane = new JTabbedPane();

        tabbedPane.addTab("Ver Jugadores", new ViewPlayersPanel());
        tabbedPane.addTab("Agregar Jugador", new AddPlayerPanel());
        tabbedPane.addTab("Buscar Jugador", new SearchPlayerPanel());
        tabbedPane.addTab("Eliminar Jugador", new DeletePlayerPanel());

        frame.add(tabbedPane);
        frame.setVisible(true);
    }
}
