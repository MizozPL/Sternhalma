package com.sternhalma.client.games;

import com.sternhalma.client.Client;
import com.sternhalma.client.Game;
import com.sternhalma.client.gui.BasicSternhalmaPanel;
import com.sternhalma.common.games.BasicSternhalma.Board;

public class BasicSternhalma implements Game {
    private BasicSternhalmaPanel panel;

    @Override
    public void init(Client client) {
        panel = new BasicSternhalmaPanel();
        client.getClientFrame().setGamePanel(panel);
        Object object;
        while(true){
            object = client.readObject();
            if(object instanceof Board) {
                //Log
                System.out.println("BoardUpdate");
                panel.setBoard((Board) object);
                continue;
            }
            if(object instanceof String) {
                //TODO: Handle Messages
            }
        }
    }
}
