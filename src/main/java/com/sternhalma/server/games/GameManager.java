package com.sternhalma.server.games;

import com.sternhalma.server.connection.Player;

import java.util.HashMap;

public class GameManager {

    private static final GameManager instance = new GameManager();
    private final GameFactory factory;
    private final HashMap<String, Game> games;

    private GameManager() {
        games = new HashMap<String, Game>();
        factory = new GameFactory();
    }

    public static GameManager getInstance() {
        return instance;
    }

    public synchronized void createGame(Player player, String gameID, String gameType) {
        if (games.containsKey(gameID)) {
            player.sendMessage("Game with id: " + gameID + "already exists!");
            return;
        }
        Game game = factory.createGame(gameType);
        if (game == null) {
            player.sendMessage("No such game!");
            return;
        }
        games.put(gameID, game);
        player.sendMessage("Game created!");
    }

    public synchronized void performAction(Player player, String gameID, String action) {
        if (games.containsKey(gameID)) {
            games.get(gameID).performAction(player, action);
            return;
        }
        player.sendMessage("INVALID");
    }

    public synchronized void joinGame(Player player, String gameID) {
        if (games.containsKey(gameID)) {
            if(!games.get(gameID).joinPlayer(player)) {
                player.sendMessage("Not joined");
            }
        }
    }
}
