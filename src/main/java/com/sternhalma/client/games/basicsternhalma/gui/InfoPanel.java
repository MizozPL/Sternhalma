package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Piece;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * Reprezentuje panel zawierający informację o kolorze gracza, turze i posiadający przycisk końca tury.
 */
public class InfoPanel extends JPanel {

    /**
     * Panel wyświetlający kolor gracza, którego jest tura.
     */
    private final JPanel turnPanel;
    /**
     * Panel wyświetlający odpowiedni kolor dla danego gracza.
     */
    private final JPanel playerTag;

    /**
     * Domyślny konstruktor inicjalizujący zawartość panelu.
     * @param basicSternhalma referencja do obiektu obsługującego grę
     */
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

    /**
     * Aktualizuje panel z id gracza.
     * @param playerID id gracza, którego kolor ma być wyświetlony.
     */
    public void setPlayerID(int playerID) {
        playerTag.setBackground(Piece.getColorForPlayerWithID(playerID));
    }

    /**
     * Aktualizuje panel z kolorem tury
     * @param playerID id gracza, którego jest tura
     */
    public void setTurn(int playerID) {
        turnPanel.setBackground(Piece.getColorForPlayerWithID(playerID));
    }
}
