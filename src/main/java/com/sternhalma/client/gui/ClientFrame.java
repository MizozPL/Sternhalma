package com.sternhalma.client.gui;

import javax.swing.*;
import java.awt.*;

public class ClientFrame extends JFrame {
    private JPanel gamePanel = null;
    public ClientFrame(String title) {
        setTitle(title);
        setSize(800,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    public void setGamePanel(JPanel panel){
        if(gamePanel != null)
            remove(gamePanel);
        gamePanel = panel;
        add(gamePanel, BorderLayout.CENTER);
        revalidate();
    }
}
