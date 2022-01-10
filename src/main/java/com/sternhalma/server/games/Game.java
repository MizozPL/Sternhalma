package com.sternhalma.server.games;

import com.sternhalma.server.connection.Player;

public interface Game {
    void joinPlayer(Player player);

    void performAction(Player player, String action);

    String getGameName();
}
