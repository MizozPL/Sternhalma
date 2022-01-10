package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JPanel turnPanel;
    private final JPanel playerTag;

    public InfoPanel(BasicSternhalma basicSternhalma) {
        setPreferredSize(new Dimension(0, 100));
        setBackground(Color.BLACK);
        setLayout(new BorderLayout());
        JButton endTurnButton = new JButton("END TURN");
        endTurnButton.addActionListener(e -> {
            basicSternhalma.endTurn();
        });
        add(endTurnButton, BorderLayout.EAST);
        turnPanel = new JPanel();
        turnPanel.setBackground(Color.GRAY);
        add(turnPanel, BorderLayout.CENTER);
        playerTag = new JPanel();
        playerTag.setPreferredSize(endTurnButton.getPreferredSize());
        turnPanel.setBackground(Color.BLACK);
        add(playerTag, BorderLayout.WEST);
        playerTag.setBorder(new BevelBorder(BevelBorder.LOWERED));
        turnPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));
    }

    public void setPlayerID(int playerID) {
        switch (playerID) {
            case 1 -> {
                playerTag.setBackground(Color.RED);
            }
            case 2 -> {
                playerTag.setBackground(Color.GREEN);
            }
            case 3 -> {
                playerTag.setBackground(Color.ORANGE);
            }
            case 4 -> {
                playerTag.setBackground(Color.YELLOW);
            }
            case 5 -> {
                playerTag.setBackground(Color.PINK);
            }
            case 6 -> {
                playerTag.setBackground(Color.CYAN);
            }
        }
    }

    public void setTurn(int turn) {
        switch (turn) {
            case 1 -> {
                turnPanel.setBackground(Color.RED);
            }
            case 2 -> {
                turnPanel.setBackground(Color.GREEN);
            }
            case 3 -> {
                turnPanel.setBackground(Color.ORANGE);
            }
            case 4 -> {
                turnPanel.setBackground(Color.YELLOW);
            }
            case 5 -> {
                turnPanel.setBackground(Color.PINK);
            }
            case 6 -> {
                turnPanel.setBackground(Color.CYAN);
            }
        }
    }
}
