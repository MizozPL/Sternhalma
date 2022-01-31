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

/**
 * Klasa implementująca odpowiednie ruchy i zasady gry.
 * (TERMINOLOGIA) ID GRUPY PIONKÓW I ID GRACZA SĄ STOSOWANE ZAMIENNIE.
 */
public class BasicSternhalma implements Game {

    /**
     * Mapa handlerów graczy do odpowiednich intów reprezentujących id grupy pionków.
     */
    private final HashMap<Player, Integer> players;
    /**
     * Plansza, na której toczy się rozgrywka.
     */
    private final Board board;
    /**
     * Numer tury.
     */
    private int turn;
    /**
     * Określa czy gra została zakończona.
     */
    private boolean gameFinished;
    /**
     * Określa lokację ostatnio ruszanego piona.
     */
    private Point lastPieceMovedLocation;
    /**
     * Określa czy ostatni ruch był skokiem.
     */
    private boolean isLastMoveJump;
    /**
     * Set zawierający id gry pionków, które ukończyły grę (tzn. wszystkie piony danego kkoloru znalazły się w bazie).
     */
    private final Set<Integer> winners;

    /**
     * Domyślny konstruktor, inicjalizuje wartości domyślne.
     */
    public BasicSternhalma() {
        players = new HashMap<>();
        board = new Board();
        turn = 1;
        gameFinished = false;
        isLastMoveJump = false;
        lastPieceMovedLocation = null;
        winners = new HashSet<>();
    }

    /**
     * Dołącza gracza do rozgrywki, oraz przesyła odpowiedni komunikat powodzenia/niepowodzenia. Aktualizuje planszę dla wszystkich graczy.
     *
     * @param player handler gracza
     */
    @Override
    public void joinPlayer(Player player) {
        if (!checkPlayersConnected()) {
            player.sendMessage(NetworkMessages.GAME_END_PLAYER_DISCONNECTED);
            return;
        }

        if (gameFinished) {
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

    /**
     * Sprawdza czy gracz o danym handlerze player ma teraz swoją turę.
     *
     * @param player handler gracza
     * @return true jeśli prawda, false w przeciwnym przypadku
     */
    private boolean isPlayersTurn(Player player) {
        int playerID = players.get(player);
        return playerID == getPlayerIDFromTurn(turn);
    }

    /**
     * Zamienia id gracza, którego teraz jest tura.
     *
     * @param turn numer tury.
     * @return id gracza, którego jest tura.
     */
    private int getPlayerIDFromTurn(int turn) {
        int controlNumber = turn % board.getNumberOfPlayers();
        return (controlNumber == 0 ? board.getNumberOfPlayers() : controlNumber);
    }

    /**
     * Wykonuje akcję na planszy. Odpowiednio ruch/skok lub zakończenie tury.
     *
     * @param player handler gracza
     * @param action String określający akcję
     */
    @Override
    public void performAction(Player player, String action) {
        if (!checkPlayersConnected())
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

    @Override
    public boolean isEnded() {
        return gameFinished;
    }

    /**
     * Akcja zakończenia tury przez gracza. Sprawdzane tu jest m.in. czy ustawił wszystkie piony w bazie. Przesyła zaktualizowaną planszę do wszystkich graczy.
     *
     * @param player handler gracza kończącego turę.
     */
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

    /**
     * Akcja ruchu/skoku przez gracza. Przesyła zaktualizowaną planszę do wszystkich graczy.
     *
     * @param player handler gracza wykonującego akcję.
     * @param tokens parametry akcji
     */
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

    /**
     * Sprawdza czy ruch z punktu oldP do punktu newP (z możliwym przesunięciem) spełnia reguły.
     * Różne przesunięcia definują róze kierunki skoków. Np. offsetX = 1 i offsetY = 1 oznacza kierunek skoku prawy dół.
     *
     * @param oldP    stara pozycja
     * @param newP    nowa pozycja
     * @param offsetX przesunięcie na osi X
     * @param offsetY przesunięcie na osi Y
     */
    private boolean canJump(Point oldP, Point newP, int offsetX, int offsetY) {
        int newX = newP.x;
        int newY = newP.y;
        int oldX = oldP.x;
        int oldY = oldP.y;
        return (((newX - oldX) == 2 * offsetX && (newY - oldY) == 2 * offsetY)
                && board.getAllPiecesPositions().contains(new Point(oldX + offsetX, oldY + offsetY)))
                && board.getPieceAt(newP) == null;
    }

    /**
     * Sprawdza czy ruch z punktu oldPoint na newPoint spełnia reguły.
     *
     * @param oldPoint stara pozycja
     * @param newPoint nowa pozycja
     * @return true jeśli spełnia reguły
     */
    public boolean isValidMove(Point oldPoint, Point newPoint) {
        return ((abs(oldPoint.x - newPoint.x) == 1 && abs(oldPoint.y - newPoint.y) == 1)
                || (abs(oldPoint.x - newPoint.x) == 2 && abs(oldPoint.y - newPoint.y) == 0))
                && board.getPieceAt(newPoint) == null;
    }


    /**
     * Sprawdza czy skok z punktu oldPoint na newPoint spełnia reguły.
     *
     * @param oldPoint stara pozycja
     * @param newPoint nowa pozycja
     * @return jeśli spełnia reguły
     */
    public boolean isValidJump(Point oldPoint, Point newPoint) {
        return this.canJump(oldPoint, newPoint, 2, 0)
                || this.canJump(oldPoint, newPoint, -2, 0)
                || this.canJump(oldPoint, newPoint, 1, 1)
                || this.canJump(oldPoint, newPoint, 1, -1)
                || this.canJump(oldPoint, newPoint, -1, 1)
                || this.canJump(oldPoint, newPoint, -1, -1);
    }

    /**
     * Zwraca Set winners.
     *
     * @return
     */
    public Set<Integer> getWinners() {
        return winners;
    }

    /**
     * Dodaje id gracza (grupy pionków) do zwycięzców.
     *
     * @param playerID id gracza (grupy pionków)
     */
    public void addWinner(int playerID) {
        this.winners.add(playerID);
    }

    /**
     * Wysyła zaktualizowaną planszę do klientów.
     */
    private void broadcastBoardUpdate() {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.BOARD_UPDATE + ":" + players.get(p) + ":" + turn + ":" + board.getNumberOfPlayers());
            p.sendObject(board);
        });
    }

    /**
     * Wysyła informację o wygranej gracza playerID do wszystkich graczy.
     *
     * @param playerID id gracza (grupy pionków)
     */
    private void broadcastWinner(int playerID) {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.WINNER + ":" + playerID);
            p.sendObject(board);
        });
    }

    /**
     * Wysyła informację o końcu gry do wszystkich graczy.
     */
    private void broadcastGameEnd() {
        players.keySet().forEach(p -> {
            p.sendMessage(NetworkMessages.GAME_END);
        });
    }

    /**
     * Sprawdza czy wszyscy gracze są połączeni.
     *
     * @return true jeśli wszyscy są połączeni, false jeśli ktoś stracił połączenie.
     */
    private boolean checkPlayersConnected() {
        for (Player p : players.keySet()) {
            if (!p.isAlive()) {
                players.keySet().forEach(p2 -> {
                    p2.sendMessage(NetworkMessages.GAME_END_PLAYER_DISCONNECTED);
                });
                gameFinished = true;
                return false;
            }
        }
        return true;
    }

    public Board getBoard(){
        return board;
    }

    /**
     * Zwraca unikalną nazwę gry (określoną w Games.java).
     *
     * @return unikalna nazwa gry.
     */
    @Override
    public String getGameName() {
        return Games.BASIC_STERNHALMA;
    }

    public int getTurn() {
        return turn;
    }
}