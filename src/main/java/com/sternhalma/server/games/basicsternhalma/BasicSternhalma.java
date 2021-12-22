package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.server.connection.Player;
import com.sternhalma.server.games.Game;

import java.awt.*;
import java.util.HashMap;

import static java.lang.Math.abs;

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
            players.keySet().forEach(p -> {
                p.sendMessage("BOARD_UPDATE:" + players.get(p) + ":" + turn);
                p.sendObject(board);
            });
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
                String fromStr = tokens[1];
                String toStr = tokens[2];
                int oldX = Integer.parseInt(fromStr.split(",")[0]);
                int oldY = Integer.parseInt(fromStr.split(",")[1]);
                int newX = Integer.parseInt(toStr.split(",")[0]);
                int newY = Integer.parseInt(toStr.split(",")[1]);
                Point oldPoint = new Point(oldX, oldY);
                Point newPoint = new Point(newX, newY);
                if (
                        (abs(oldX - newX) == 1 && abs(oldY - newY) == 1)
                        || (abs(oldX - newX) == 2  && abs(oldY - newY) == 0)
                        || board.canJump(oldPoint, newPoint, 2, 0)
                        || board.canJump(oldPoint, newPoint, -2, 0)
                        || board.canJump(oldPoint, newPoint, 1, 1)
                        || board.canJump(oldPoint, newPoint, 1, -1)
                        || board.canJump(oldPoint, newPoint, -1, 1)
                        || board.canJump(oldPoint, newPoint, -1, -1)
                ) {
                    board.movePiece(oldPoint, newPoint);
                    players.keySet().forEach(p -> {
                        p.sendMessage("BOARD_UPDATE:" + players.get(p) + ":" + turn);
                        p.sendObject(board);
                    });
                }
            }
        }
        return false;
    }

    @Override
    public String getGameName() {
        return "BasicSternhalma";
    }
}
