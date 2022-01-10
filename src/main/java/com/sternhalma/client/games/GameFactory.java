package com.sternhalma.client.games;

import com.sternhalma.client.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.common.games.Games;

public class GameFactory {

    private GameFactory() {

    }

    public static Game creteGameInstance(String gameName) {
        switch (gameName) {
            case Games.BASIC_STERNHALMA -> {
                return new BasicSternhalma();
            }
            default -> {
                return null;
            }
        }
    }
}
