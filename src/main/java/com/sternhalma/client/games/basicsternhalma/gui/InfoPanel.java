package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Piece;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class InfoPanel extends JPanel {

    private final JPanel turnPanel;
    private final JPanel playerTag;

    public InfoPanel(BasicSternhalma basicSternhalma) {
        setPreferredSize(new Dimension(0, 100));
        setLayout(new BorderLayout());

        JButton endTurnButton = new JButton("END TURN");
        endTurnButton.addActionListener(e -> {
            basicSternhalma.endTurn();
        });

        turnPanel = new JPanel();
        turnPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        playerTag = new JPanel();
        playerTag.setPreferredSize(endTurnButton.getPreferredSize());
        playerTag.setBorder(new BevelBorder(BevelBorder.LOWERED));

        JPanel spacing = new JPanel();
        spacing.setPreferredSize(new Dimension(0, 15));

        add(playerTag, BorderLayout.WEST);
        add(turnPanel, BorderLayout.CENTER);
        add(endTurnButton, BorderLayout.EAST);
        add(spacing, BorderLayout.SOUTH);
    }

    public void setPlayerID(int playerID) {
        playerTag.setBackground(Piece.getColorForPlayerWithID(playerID));
    }

    public void setTurn(int playerID) {
        turnPanel.setBackground(Piece.getColorForPlayerWithID(playerID));
    }
}
