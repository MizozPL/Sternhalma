package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.common.games.Games;
import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.server.connection.Player;
import com.sternhalma.server.games.Game;

import java.awt.*;
import java.util.HashMap;

public class BasicSternhalma implements Game {

    private final HashMap<Player, Integer> players;
    private final Board board;
    private int turn;
    private boolean gameFinished;

    public BasicSternhalma() {
        players = new HashMap<>();
        board = new Board();
        turn = 1;
        gameFinished = false;
    }

    @Override
    public void joinPlayer(Player player) {
        if (players.size() >= 6) {
            player.sendMessage(NetworkMessages.GAME_FULL);
            return;
        }
        if (players.containsKey(player)) {
            player.sendMessage(NetworkMessages.PLAYER_ALREADY_IN_GAME);
            return;
        }
        player.sendMessage(getGameName());

        int id = board.addPlayer();
        players.put(player, id);
        broadcastBoardUpdate();
    }

    private boolean isPlayersTurn(Player player) {
        int playerID = players.get(player);
        return playerID == getPlayerIDFromTurn(turn);
    }

    private int getPlayerIDFromTurn(int turn) {
        int controlNumber = turn % board.getNumberOfPlayers();
        return (controlNumber == 0 ? board.getNumberOfPlayers() : controlNumber);
    }

    @Override
    public void performAction(Player player, String action) {
        if(gameFinished) {
            player.sendMessage(NetworkMessages.GAME_END);
        }

        String[] tokens = action.split(":", 3);
        String command = tokens[0];
        int numberOfPlayers = board.getNumberOfPlayers();

        if (!isPlayersTurn(player)) {
            player.sendMessage(NetworkMessages.WRONG_TURN);
            return;
        }

        if (numberOfPlayers == 1 || numberOfPlayers == 5) {
            player.sendMessage(NetworkMessages.WAITING_FOR_MORE_PLAYERS);
            return;
        }

        switch (command) {
            case NetworkMessages.MOVE -> {
                String fromStr = tokens[1];
                String toStr = tokens[2];

                int oldX = Integer.parseInt(fromStr.split(",")[0]);
                int oldY = Integer.parseInt(fromStr.split(",")[1]);
                int newX = Integer.parseInt(toStr.split(",")[0]);
                int newY = Integer.parseInt(toStr.split(",")[1]);

                Point oldPoint = new Point(oldX, oldY);
                Point newPoint = new Point(newX, newY);

                if (players.get(player) != board.getPlayerIDAt(oldPoint)) {
                    player.sendMessage(NetworkMessages.BAD_PIECE);
                    return;
                }

                if (board.isValidMove(oldPoint, newPoint) || board.isValidJump(oldPoint, newPoint)) {
                    if (!board.isLeavingOpponentBase(oldPoint, newPoint)) {
                        board.movePiece(oldPoint, newPoint);
                        broadcastBoardUpdate();
                    }
                }
            }

            case NetworkMessages.END_TURN -> {
                int playerID = players.get(player);
                if (!board.getWinners().contains(playerID) && board.checkForWin(playerID)) {
                    board.addWinner(playerID);
                    broadcastWinner(playerID);
                }
                if (board.getWinners().size() == board.getNumberOfPlayers()) {
                    gameFinished = true;
                    broadcastGameEnd();
                    return;
                }
                do {
                    turn++;
                }
                while (board.getWinners().contains(getPlayerIDFromTurn(turn)));
                broadcastBoardUpdate();
            }
        }

    }

    private void broadcastBoardUpdate() {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.BOARD_UPDATE + ":" + players.get(p) + ":" + turn + ":" + board.getNumberOfPlayers());
            p.sendObject(board);
        });
    }

    private void broadcastWinner(int playerID) {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.WINNER + ":" + playerID);
            p.sendObject(board);
        });
    }

    private void broadcastGameEnd() {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.GAME_END);
        });
    }

    @Override
    public String getGameName() {
        return Games.BASIC_STERNHALMA;
    }
}