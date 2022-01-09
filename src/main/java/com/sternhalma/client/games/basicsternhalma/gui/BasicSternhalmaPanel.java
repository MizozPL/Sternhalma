package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Board;

import javax.swing.*;
import java.awt.*;

public class BasicSternhalmaPanel extends JPanel {

    private final BoardPanel boardPanel;
    private final InfoPanel infoPanel;

    public BasicSternhalmaPanel(BasicSternhalma basicSternhalma) {
        setLayout(new BorderLayout());
        boardPanel = new BoardPanel(basicSternhalma);
        infoPanel = new InfoPanel();
        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
    }

    public void setBoard(Board board) {
        boardPanel.setBoard(board);
    }
}
