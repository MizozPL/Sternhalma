package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.connection.NetworkMessages;
import com.sternhalma.common.games.Games;
import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.server.connection.Player;
import com.sternhalma.server.games.Game;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.abs;

public class BasicSternhalma implements Game {

    private final HashMap<Player, Integer> players;
    private final Board board;
    private int turn;
    private boolean gameFinished;
    private Point lastPieceMovedLocation;
    private boolean isLastMoveJump;
    private final Set<Integer> winners;

    public BasicSternhalma() {
        players = new HashMap<>();
        board = new Board();
        turn = 1;
        gameFinished = false;
        isLastMoveJump = false;
        lastPieceMovedLocation = null;
        winners = new HashSet<>();
    }

    @Override
    public void joinPlayer(Player player) {
        if(!checkPlayersConnected()) {
            player.sendMessage(NetworkMessages.GAME_END_PLAYER_DISCONNECTED);
            return;
        }

        if(gameFinished) {
            player.sendMessage(NetworkMessages.GAME_END);
        }

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
        if(!checkPlayersConnected())
            return;

        if (gameFinished) {
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
                actionMove(player, tokens);
            }

            case NetworkMessages.END_TURN -> {
                actionEndTurn(player);
            }
        }

    }

    private void actionEndTurn(Player player) {
        int playerID = players.get(player);
        if (!getWinners().contains(playerID) && board.checkForWin(playerID)) {
            addWinner(playerID);
            broadcastWinner(playerID);
        }
        if (getWinners().size() == board.getNumberOfPlayers()) {
            gameFinished = true;
            broadcastGameEnd();
            return;
        }
        lastPieceMovedLocation = null;
        isLastMoveJump = false;
        do {
            turn++;
        }
        while (getWinners().contains(getPlayerIDFromTurn(turn)));
        broadcastBoardUpdate();
    }

    private void actionMove(Player player, String[] tokens) {
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

        if (lastPieceMovedLocation != null) {
            if (!lastPieceMovedLocation.equals(oldPoint)) {
                return;
            }

            if (!isLastMoveJump) {
                return;
            }

            if (isValidJump(oldPoint, newPoint)) {
                if (!board.isLeavingOpponentBase(oldPoint, newPoint)) {
                    board.movePiece(oldPoint, newPoint);
                    lastPieceMovedLocation = newPoint;
                    isLastMoveJump = true;
                    broadcastBoardUpdate();

                }
            }

        } else {
            if (isValidMove(oldPoint, newPoint) || isValidJump(oldPoint, newPoint)) {
                if (!board.isLeavingOpponentBase(oldPoint, newPoint)) {
                    isLastMoveJump = isValidJump(oldPoint, newPoint);
                    lastPieceMovedLocation = newPoint;
                    board.movePiece(oldPoint, newPoint);
                    broadcastBoardUpdate();
                }
            }
        }
    }

    private boolean canJump(Point oldP, Point newP, int offsetX, int offsetY) {
        int newX = newP.x;
        int newY = newP.y;
        int oldX = oldP.x;
        int oldY = oldP.y;
        return ((newX - oldX) == 2 * offsetX && (newY - oldY) == 2 * offsetY)
                && board.getAllPiecesPositions().contains(new Point(oldX + offsetX, oldY + offsetY));
    }

    public boolean isValidMove(Point oldPoint, Point newPoint) {
        return (abs(oldPoint.x - newPoint.x) == 1 && abs(oldPoint.y - newPoint.y) == 1)
                || (abs(oldPoint.x - newPoint.x) == 2 && abs(oldPoint.y - newPoint.y) == 0);
    }

    public boolean isValidJump(Point oldPoint, Point newPoint) {
        return this.canJump(oldPoint, newPoint, 2, 0)
                || this.canJump(oldPoint, newPoint, -2, 0)
                || this.canJump(oldPoint, newPoint, 1, 1)
                || this.canJump(oldPoint, newPoint, 1, -1)
                || this.canJump(oldPoint, newPoint, -1, 1)
                || this.canJump(oldPoint, newPoint, -1, -1);
    }

    public Set<Integer> getWinners() {
        return winners;
    }

    public void addWinner(int playerID) {
        this.winners.add(playerID);
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

    private boolean checkPlayersConnected(){
        for(Player p : players.keySet()) {
            if(!p.isAlive()) {
                players.keySet().forEach(p2 -> {
                    p2.sendMessage(NetworkMessages.GAME_END_PLAYER_DISCONNECTED);
                });
                gameFinished = true;
                return false;
            }
        }
        return true;
    }

    @Override
    public String getGameName() {
        return Games.BASIC_STERNHALMA;
    }
}