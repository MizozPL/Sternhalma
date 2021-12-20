package com.sternhalma.server;

import com.sternhalma.server.games.BasicSternhalma.BasicSternhalma;

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
