package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Board;

import javax.swing.*;
import java.awt.*;

/**
 * Panel agregujący panel z informacjami i planszą gry.
 */
public class BasicSternhalmaPanel extends JPanel {

    /**
     * Panel zawierający planszę gry.
     */
    private final BoardPanel boardPanel;
    /**
     * Panel zawierający informacje o grze.
     */
    private final InfoPanel infoPanel;

    /**
     * Domyślny konstruktor inicjalizujący zawartość panelu.
     * @param basicSternhalma referencja do obiektu obsługującego grę
     */
    public BasicSternhalmaPanel(BasicSternhalma basicSternhalma) {
        setLayout(new BorderLayout());
        boardPanel = new BoardPanel(basicSternhalma);
        infoPanel = new InfoPanel(basicSternhalma);
        add(boardPanel, BorderLayout.CENTER);
        add(infoPanel, BorderLayout.NORTH);
    }

    /**
     * Deleguje aktualizację planszy do klasy BoardPanel.
     * @param board
     */
    public void setBoard(Board board) {
        boardPanel.setBoard(board);
    }

    /**
     * Deleguje aktualizuję panelu z id gracza do klasy InfoPanel.
     * @param playerID id gracza
     */
    public void setPlayerID(int playerID) {
        infoPanel.setPlayerID(playerID);
    }

    /**
     * Deleguje aktualizuję panelu z kolorem tury do klasy InfoPanel.
     * @param playerID id gracza, którego jest tura
     */
    public void setTurn(int playerID) {
        infoPanel.setTurn(playerID);
    }
}
