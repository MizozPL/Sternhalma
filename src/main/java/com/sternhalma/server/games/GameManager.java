package com.sternhalma.server.games;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.server.connection.Player;

import java.util.HashMap;

/**
 * Klasa zarządzająca wszystkimi istniejącymi grami na serwerze. Singleton.
 */
public class GameManager {

    /**
     * Instancja klasy.
     */
    private static final GameManager instance = new GameManager();
    /**
     * Mapa kodów gier i ich instancji.
     */
    private final HashMap<String, Game> games;

    /**
     * Prywatny konstruktor (singleton).
     */
    private GameManager() {
        games = new HashMap<String, Game>();
    }

    /**
     * Zwraca instancję klasy.
     * @return instancja klasy
     */
    public static GameManager getInstance() {
        return instance;
    }

    /**
     * Tworzy nową grę o danym kodzie i typie.
     * @param player handler twórcy gry
     * @param gameID id gry, dowolny unikalny string
     * @param gameType typ gry (z Games.java)
     */
    public synchronized void createGame(Player player, String gameID, String gameType) {
        if (games.containsKey(gameID)) {
            player.sendMessage(NetworkMessages.GAME_WITH_ID_EXISTS);
            return;
        }
        Game game = GameFactory.createGameInstance(gameType);
        if (game == null) {
            player.sendMessage(NetworkMessages.BAD_GAME_TYPE);
            return;
        }
        games.put(gameID, game);
        player.sendMessage(NetworkMessages.GAME_CREATED);
    }

    /**
     * Wykonuje akcję action przez gracza o handlerze player na grze gameID.
     * @param player handler gracza wykonującego akcję
     * @param gameID id gry, na której akcja jest wykonana
     * @param action odpowiednio spreparowany String akcji
     */
    public synchronized void performAction(Player player, String gameID, String action) {
        if (games.containsKey(gameID)) {
            games.get(gameID).performAction(player, action);
            return;
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }

    /**
     * Dołącza gracza o handlerze player do gry gameID.
     * @param player handler gracza
     * @param gameID id gry
     */
    public synchronized void joinGame(Player player, String gameID) {
        if (games.containsKey(gameID)) {
            games.get(gameID).joinPlayer(player);
            return;
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }
}
