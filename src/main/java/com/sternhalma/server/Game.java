package com.sternhalma.server;

public interface Game {
    boolean joinPlayer(Player player);
    boolean performAction(Player player, String action);
    String getGameName();
}
