package com.sternhalma.client.games.basicsternhalma.gui;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.basicsternhalma.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicSternhalmaPanel extends JPanel implements MouseListener {

    private Board board;
    private Point selectedPosition = null;
    private final BasicSternhalma basicSternhalma;

    private static final int SIZE = 20;
    private static final int SCALEX = 20;
    private static final int SCALEY = 30;

    public BasicSternhalmaPanel(BasicSternhalma basicSternhalma) {
        board = new Board();
        this.basicSternhalma = basicSternhalma;
        addMouseListener(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        drawAllValidSpots(g2d, Color.GRAY);
        drawPlayerStartingPieces(g2d, 1, Color.RED);
        drawPlayerStartingPieces(g2d, 2, Color.GREEN);
        drawPlayerStartingPieces(g2d, 3, Color.ORANGE);
        drawPlayerStartingPieces(g2d, 4, Color.YELLOW);
        drawPlayerStartingPieces(g2d, 5, Color.PINK);
        drawPlayerStartingPieces(g2d, 6, Color.CYAN);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = new Point(e.getX() / SCALEX, e.getY() / SCALEY);
        if (board.getPieceAt(point) != null) {
            selectedPosition = point;
        } else {
            if (board.getPieceAt(selectedPosition) != null && board.getValidPositions().contains(point)) {
                basicSternhalma.movePiece(selectedPosition, point);
                selectedPosition = null;
            }
        }
    }

    private void drawAllValidSpots(Graphics2D g2d, Color color) {
        g2d.setColor(color);
        for (Point point : board.getValidPositions()) {
            g2d.fillOval(SCALEX * point.x, SCALEY * point.y, SIZE, SIZE);
        }
    }

    private void drawPlayerStartingPieces(Graphics2D g2d, int playerID, Color color) {
        g2d.setColor(color);
        for (Point point : board.getPlayerPiecesCoordinates(playerID))
            g2d.fillOval(SCALEX * point.x, SCALEY * point.y, SIZE, SIZE);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
