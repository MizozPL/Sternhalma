package com.sternhalma.server;

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

    public synchronized String createGame(String gameID, String gameType) {
        if (games.containsKey(gameID))
            return "Game with id: " + gameID + "already exists!";
        Game game = factory.createGame(gameType);
        if (game == null)
            return "No such game!";
        games.put(gameID, game);
        return "Game created!";
    }

    public synchronized String performAction(Player player, String gameID, String action) {
        if (games.containsKey(gameID)) {
            return games.get(gameID).performAction(player, action) ? "Action performed" : "Action not performed";
        }
        return "Action not performed";
    }

    public synchronized String joinGame(Player player, String gameID) {
        if (games.containsKey(gameID)) {
            return games.get(gameID).joinPlayer(player) ? games.get(gameID).getGameName() : "Not joined";
        }
        return "Not joined";
    }
}
