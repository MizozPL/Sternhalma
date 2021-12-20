package com.sternhalma.client.gui;

import com.sternhalma.client.games.BasicSternhalma;
import com.sternhalma.common.games.BasicSternhalma.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicSternhalmaPanel extends JPanel implements MouseListener {

    private Board board;
    private Point selectedPosition = null;
    private final BasicSternhalma basicSternhalma;

    public BasicSternhalmaPanel(BasicSternhalma basicSternhalma){
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
        g2d.setColor(Color.gray);
        for(Point point : board.getValidPositions()){
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        }
        g2d.setColor(Color.RED);
        for(Point point : board.getPlayerPiecesCoordinates(1))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        g2d.setColor(Color.GREEN);
        for(Point point : board.getPlayerPiecesCoordinates(2))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        g2d.setColor(Color.ORANGE);
        for(Point point : board.getPlayerPiecesCoordinates(3))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        g2d.setColor(Color.YELLOW);
        for(Point point : board.getPlayerPiecesCoordinates(4))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        g2d.setColor(Color.PINK);
        for(Point point : board.getPlayerPiecesCoordinates(5))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
        g2d.setColor(Color.CYAN);
        for(Point point : board.getPlayerPiecesCoordinates(6))
            g2d.fillOval(20* point.x, 30*point.y, 15, 15);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point point = new Point(e.getX()/20, e.getY()/30);
        if(board.getPieceAt(point) != null){
            selectedPosition = point;
        } else {
            if(board.getPieceAt(selectedPosition) != null) {
                basicSternhalma.movePiece(selectedPosition, point);
                selectedPosition = null;
            }
        }
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
