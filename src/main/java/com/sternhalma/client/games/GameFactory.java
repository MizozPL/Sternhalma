package com.sternhalma.client.games;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;

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
