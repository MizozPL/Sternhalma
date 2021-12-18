package com.sternhalma.server;

import com.sternhalma.server.games.Sternhalma.BasicSternhalma;

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
