package com.sternhalma.server.games;

import com.sternhalma.server.games.basicsternhalma.BasicSternhalma;

public class GameFactory {

    public Game createGame(String gameName){
        switch(gameName) {
            case "BasicSternhalma" -> {
                return new BasicSternhalma();
            }
            default -> {
                return null;
            }
        }
    }
}
