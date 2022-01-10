package com.sternhalma.server.games;

import com.sternhalma.common.games.Games;
import com.sternhalma.server.games.basicsternhalma.BasicSternhalma;

public class GameFactory {

    private GameFactory() {

    }

    public static Game createGame(String gameName) {
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
