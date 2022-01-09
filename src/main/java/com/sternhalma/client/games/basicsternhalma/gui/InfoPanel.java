package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel {
    public InfoPanel(BasicSternhalma basicSternhalma) {
        setPreferredSize(new Dimension(0, 100));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JButton endTurnButton = new JButton("END TURN");
        endTurnButton.addActionListener(e -> {
            basicSternhalma.endTurn();
        });
        add(endTurnButton, BorderLayout.EAST);
        JPanel turnPanel = new JPanel();
        turnPanel.setBackground(Color.GRAY);
        add(turnPanel, BorderLayout.CENTER);
    }
}
