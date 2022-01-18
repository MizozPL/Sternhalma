package com.sternhalma.server.games;

import com.sternhalma.common.games.Games;
import com.sternhalma.server.games.basicsternhalma.BasicSternhalma;
import com.sternhalma.server.games.basicsternhalma.SternhalmaNoJumps;

/**
 * Klasa statyczna tworząca odpowiednie instancje gier.
 */
public class GameFactory {

    /**
     * Prywatny konstruktor.
     */
    private GameFactory() {

    }

    /**
     * Tworzy instancję gry o danej nazwie (z Games.java)
     * @param gameName nazwa gry
     * @return instancję gry lub null, jeśli taka nie istnieje
     */
    public static Game createGameInstance(String gameName) {
        switch (gameName) {
            case Games.BASIC_STERNHALMA -> {
                return new BasicSternhalma();
            }
            case Games.STERNHALMA_NO_JUMPS -> {
                return new SternhalmaNoJumps();
            }
            default -> {
                return null;
            }
        }
    }
}
