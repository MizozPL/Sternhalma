package com.sternhalma.client;

import com.sternhalma.client.games.BasicSternhalma;

public class GameFactory {
    public static Game creteGameInstance(String gameName) {
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
