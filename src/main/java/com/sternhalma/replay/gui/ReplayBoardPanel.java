package com.sternhalma.replay.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.common.games.basicsternhalma.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Klasa odpowiedzialna za wyświetlanie planszy do gry.
 */
public class ReplayBoardPanel extends JPanel {

    private Board board;


    private static final double DOT_SIZE = 0.9;


    public ReplayBoardPanel(Board board) {
        this.board = board;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawAllValidSpots(g2d, Color.GRAY);
        drawPlayerPieces(g2d, 1, Piece.getColorForPlayerWithID(1));
        drawPlayerPieces(g2d, 2, Piece.getColorForPlayerWithID(2));
        drawPlayerPieces(g2d, 3, Piece.getColorForPlayerWithID(3));
        drawPlayerPieces(g2d, 4, Piece.getColorForPlayerWithID(4));
        drawPlayerPieces(g2d, 5, Piece.getColorForPlayerWithID(5));
        drawPlayerPieces(g2d, 6, Piece.getColorForPlayerWithID(6));
    }

    private void drawAllValidSpots(Graphics2D g2d, Color color) {
        g2d.setColor(color);
        for (Point point : board.getValidPositions()) {
            Point p = scaleCoords(point);
            g2d.fillOval(p.x, p.y, scaleSize(), scaleSize());
        }
    }

     private void drawPlayerPieces(Graphics2D g2d, int playerID, Color color) {
        g2d.setColor(color);
        for (Point point : board.getPlayerPiecesCoordinates(playerID)) {
            Point p = scaleCoords(point);
            g2d.fillOval(p.x, p.y, scaleSize(), scaleSize());
        }
    }

    private Point scaleCoords(Point p) {
        int min = getScale();
        Point ret = new Point((int) (p.x * min / Board.BOARD_X) + getHorizontalOffset(), (int) (p.y * min / Board.BOARD_Y) + getVerticalOffset());
        return ret;
    }
    private Point unscaleCords(Point p) {
        int min = getScale();
        Point ret = new Point((int) ((p.x - getHorizontalOffset()) * Board.BOARD_X / min), (int) ((p.y - getVerticalOffset()) * Board.BOARD_Y / min));
        return ret;
    }
    private int getScale() {
        return (int) Math.min(getWidth(), getHeight());
    }
    private int scaleSize() {
        return (int) (getScale() * DOT_SIZE / Math.max(Board.BOARD_X, Board.BOARD_Y));
    }
    private int getVerticalOffset() {
        return (getHeight() - getScale()) / 2;
    }
    private int getHorizontalOffset() {
        return (getWidth() - getScale()) / 2;
    }
}
