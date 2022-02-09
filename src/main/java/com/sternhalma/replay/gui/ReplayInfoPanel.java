package com.sternhalma.replay.gui;

import com.sternhalma.common.games.basicsternhalma.Piece;
import com.sternhalma.replay.replays.BasicSternhalmaReplay;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class ReplayInfoPanel extends JPanel {

    private final JPanel turnPanel;
    private final JPanel playerTag;

    public ReplayInfoPanel(BasicSternhalmaReplay basicSternhalmaReplay) {
        setPreferredSize(new Dimension(0, 100));
        setLayout(new BorderLayout());

        JButton endTurnButton = new JButton("NEXT ACTION");
        endTurnButton.addActionListener(e -> {
            basicSternhalmaReplay.nextAction();
        });

        turnPanel = new JPanel();
        turnPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));

        playerTag = new JPanel();
        playerTag.setPreferredSize(endTurnButton.getPreferredSize());
        playerTag.setBorder(new BevelBorder(BevelBorder.LOWERED));
        playerTag.setBackground(Color.BLACK);

        JPanel spacing = new JPanel();
        spacing.setPreferredSize(new Dimension(0, 15));

        add(playerTag, BorderLayout.WEST);
        add(turnPanel, BorderLayout.CENTER);
        add(endTurnButton, BorderLayout.EAST);
        add(spacing, BorderLayout.SOUTH);
    }

    public void setTurn(int playerID) {
        turnPanel.setBackground(Piece.getColorForPlayerWithID(playerID));
    }
}
