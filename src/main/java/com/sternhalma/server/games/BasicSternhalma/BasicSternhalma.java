package com.sternhalma.server.games.BasicSternhalma;

import com.sternhalma.common.games.BasicSternhalma.Board;
import com.sternhalma.server.Game;
import com.sternhalma.server.Player;

import java.util.HashMap;

public class BasicSternhalma implements Game {

    private final HashMap<Player, Integer> players;
    private final Board board;
    private int turn;

    public BasicSternhalma() {
        players = new HashMap<>();
        board = new Board();
        turn = 1;
    }

    @Override
    public boolean joinPlayer(Player player) {
        if (!players.containsKey(player)) {
            int id = board.addPlayer();
            players.put(player, id);
            for (Player p : players.keySet()) {
                p.sendMessage("BOARD_UPDATE:" + players.get(p) + ":" + turn);
                p.sendObject(board);
            }
            return true;
        }
        return false;
    }



    @Override
    public boolean performAction(Player player, String action) {
        //TODO: Na razie po prostu wykonajmy pojedyńczą akcję przesunięcia pionka z punktu A do punktu B
        //MOVE:4,0:4,2
        String[] tokens = action.split(":", 3);
        String command = tokens[0];
        switch (command) {
            case "MOVE" -> {
                //board.movePiece(oldPosition, newPosition);
            }
        }
        return false;
    }

    @Override
    public String getGameName() {
        return "BasicSternhalma";
    }
}
