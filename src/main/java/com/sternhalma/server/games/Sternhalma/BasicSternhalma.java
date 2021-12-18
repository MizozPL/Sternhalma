package com.sternhalma.server.games.Sternhalma;

import com.sternhalma.server.Game;
import com.sternhalma.server.Player;

import java.util.HashSet;
import java.util.Set;

public class BasicSternhalma implements Game {

    private final Set<Player> players;

    public BasicSternhalma(){
        players = new HashSet<>();
    }

    @Override
    public boolean joinPlayer(Player player) {
        if(players.add(player)) {
            //TODO: Dodać nowe pionki na planszy i wysłać zaktualizowaną planszę do reszty graczy
            return true;
        }
        return false;
    }

    @Override
    public boolean performAction(Player player, String action) {
        //TODO: Na razie po prostu wykonajmy pojedyńczą akcję przesunięcia pionka z punktu A do punktu B
        return false;
    }

    @Override
    public String getGameName() {
        return "BasicSternhalma";
    }
}
