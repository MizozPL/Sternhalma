package com.sternhalma.client.gui;

import com.sternhalma.common.games.BasicSternhalma.Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BasicSternhalmaPanel extends JPanel implements MouseListener {

    private Board board;

    public BasicSternhalmaPanel(){
        board = new Board();
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
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        }
        g2d.setColor(Color.RED);
        for(Point point : board.getPlayerPiecesCoordinates(1))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        g2d.setColor(Color.GREEN);
        for(Point point : board.getPlayerPiecesCoordinates(2))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        g2d.setColor(Color.GREEN);
        for(Point point : board.getPlayerPiecesCoordinates(3))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        g2d.setColor(Color.YELLOW);
        for(Point point : board.getPlayerPiecesCoordinates(4))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        g2d.setColor(Color.PINK);
        for(Point point : board.getPlayerPiecesCoordinates(5))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
        g2d.setColor(Color.CYAN);
        for(Point point : board.getPlayerPiecesCoordinates(6))
            g2d.fillOval(30* point.x, 20*point.y, 15, 15);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        //TODO: Implement moving pawns
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
