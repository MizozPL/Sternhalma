package com.sternhalma.client.gui;

import javax.swing.*;
import java.awt.*;

/**
 * Główne okno interfejsu użytkownika
 */
public class ClientFrame extends JFrame {
    private JPanel gamePanel = null;

    /**
     * Inicjalizuje okno interfejsu.
     * @param title tytuł okna
     */
    public ClientFrame(String title) {
        setTitle(title);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    /**
     * Ustawia odpowiedni dla danej gry panel interfejsu użytkownika.
     * @param panel panel interfejsu danej gry
     */
    public void setGamePanel(JPanel panel) {
        if (gamePanel != null)
            remove(gamePanel);
        gamePanel = panel;
        add(gamePanel, BorderLayout.CENTER);
        revalidate();
    }
}
