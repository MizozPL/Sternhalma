package com.sternhalma.server.games.Sternhalma;

import com.sternhalma.server.Game;
import com.sternhalma.server.Player;

public class BasicSternhalma implements Game {

    public BasicSternhalma(){

    }

    @Override
    public boolean joinPlayer(Player player) {
        return false;
    }

    @Override
    public boolean performAction(Player player, String action) {
        return false;
    }

    @Override
    public String getGameName() {
        return null;
    }
}
