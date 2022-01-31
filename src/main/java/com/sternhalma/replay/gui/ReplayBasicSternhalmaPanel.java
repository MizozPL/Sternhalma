package com.sternhalma.replay.gui;

import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.replay.replays.BasicSternhalmaReplay;

import javax.swing.*;
import java.awt.*;

public class ReplayBasicSternhalmaPanel extends JPanel {

    private final ReplayBoardPanel boardPanel;
    private final ReplayInfoPanel infoPanel;

    public ReplayBasicSternhalmaPanel(BasicSternhalmaReplay basicSternhalmaReplay) {
        setLayout(new BorderLayout());
        boardPanel = new ReplayBoardPanel(basicSternhalmaReplay.getBoard());
        infoPanel = new ReplayInfoPanel(basicSternhalmaReplay);
        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
    }

    public void setTurn(int playerID) {
        infoPanel.setTurn(playerID);
    }
}
