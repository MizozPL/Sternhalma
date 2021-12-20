package com.sternhalma.client.games.basicsternhalma;

import com.sternhalma.client.connection.Client;
import com.sternhalma.client.games.Game;
import com.sternhalma.client.games.basicsternhalma.gui.BasicSternhalmaPanel;
import com.sternhalma.common.games.basicsternhalma.Board;

import java.awt.*;

public class BasicSternhalma implements Game {
    private BasicSternhalmaPanel panel;
    private Client client;

    @Override
    public void init(Client client) {
        panel = new BasicSternhalmaPanel(this);
        this.client = client;
        this.client.getClientFrame().setGamePanel(panel);
        Object object;
        while(true){
            object = client.readObject();
            if(object instanceof Board) {
                Board board = (Board) object;
                panel.setBoard(board);
                continue;
            }
            if(object instanceof String) {
                //TODO: Handle Messages
            }
        }
    }

    public void movePiece(Point from, Point to){
        client.sendMessage("performAction:"+ client.getGameID() +":MOVE:"+from.x+","+from.y+":"+to.x+","+to.y);
    }
}
