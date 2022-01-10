package com.sternhalma.server.games;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.server.connection.Player;

import java.util.HashMap;

public class GameManager {

    private static final GameManager instance = new GameManager();
    private final HashMap<String, Game> games;

    private GameManager() {
        games = new HashMap<String, Game>();
    }

    public static GameManager getInstance() {
        return instance;
    }

    public synchronized void createGame(Player player, String gameID, String gameType) {
        if (games.containsKey(gameID)) {
            player.sendMessage(NetworkMessages.GAME_WITH_ID_EXISTS);
            return;
        }
        Game game = GameFactory.createGame(gameType);
        if (game == null) {
            player.sendMessage(NetworkMessages.BAD_GAME_TYPE);
            return;
        }
        games.put(gameID, game);
        player.sendMessage(NetworkMessages.GAME_CREATED);
    }

    public synchronized void performAction(Player player, String gameID, String action) {
        if (games.containsKey(gameID)) {
            games.get(gameID).performAction(player, action);
            return;
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }

    public synchronized void joinGame(Player player, String gameID) {
        if (games.containsKey(gameID)) {
            games.get(gameID).joinPlayer(player);
        }
        player.sendMessage(NetworkMessages.GAME_WITH_ID_DOES_NOT_EXIST);
    }
}
