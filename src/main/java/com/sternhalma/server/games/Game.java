package com.sternhalma.server.games;

import com.sternhalma.server.connection.Player;

public interface Game {
    boolean joinPlayer(Player player);
    boolean performAction(Player player, String action);
    String getGameName();
}
