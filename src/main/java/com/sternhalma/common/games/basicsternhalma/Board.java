package com.sternhalma.common.games.basicsternhalma;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentująca planszę gry, służąca do przechowywania i wymiany danych.
 */
public class Board implements Serializable {

    /**
     * Wymiary planszy do działań matematycznych. (magic numbers)
     */
    public static final int BOARD_X = 25;
    public static final int BOARD_Y = 17;

    /**
     * Liczba graczy w obecnej grze.
     */
    private int numberOfPlayers;
    /**
     * Mapa pionków wszystkich graczy wraz z pozycjami na planszy.
     */
    private final HashMap<Point, Piece> piecesWithPosition;
    /**
     * Set wszystkich pozycji na planszy.
     */
    private static final HashSet<Point> validPoints;

    /**
     * Sety pozycji startowych zawierających się w poszczególnych rogach mapy.
     */
    private static final HashSet<Point> topBasePoints;
    private static final HashSet<Point> topRightBasePoints;
    private static final HashSet<Point> bottomRightBasePoints;
    private static final HashSet<Point> bottomBasePoints;
    private static final HashSet<Point> bottomLeftBasePoints;
    private static final HashSet<Point> topLeftBasePoints;

    /**
     * Przechowuje ID graczy (przeciwników), do których bazy musimy wejść, aby wygrać.
     * Odpowiednio dla pierwszego gracza jest to pierwsza pozycja w tabeli, drugiego druga ...
     */
    private int[] opponents;

    /**
     * Domyślny konstruktor, inicjalizuje wartości domyślne.
     */
    public Board() {
        numberOfPlayers = 0;
        opponents = null;
        piecesWithPosition = new HashMap<>();
    }

    /**
     * Inicjalizuje stałe informacje o planszy (Sety: wszystkich pozycji, poszczególnych rogów)
     */
    static {
        validPoints = generateAllValidPoints();
        topBasePoints = generateTopBasePoints();
        topRightBasePoints = generateTopRightBasePoints();
        bottomRightBasePoints = generateBottomRightBasePoints();
        bottomBasePoints = generateBottomBasePoints();
        bottomLeftBasePoints = generateBottomLeftBasePoints();
        topLeftBasePoints = generateTopLeftBasePoints();
    }

    /**
     * Zmienia pozycję pionka z oldPosition na newPosition.
     *
     * @param oldPosition stara pozycja
     * @param newPosition nowa pozycja
     */
    public void movePiece(Point oldPosition, Point newPosition) {
        piecesWithPosition.put(newPosition, piecesWithPosition.get(oldPosition));
        piecesWithPosition.remove(oldPosition);
    }

    /**
     * Sprawdza czy oldPoint znajduje się w rogu docelowym i czy newPoint znajduje się poza nim.
     *
     * @param oldPoint stara pozycja
     * @param newPoint nowa pozycja
     * @return
     */
    public boolean isLeavingOpponentBase(Point oldPoint, Point newPoint) {
        int playerID = this.getPlayerIDAt(oldPoint);
        return this.getOpponentBaseCoordinates(playerID).contains(oldPoint) &&
                !(this.getOpponentBaseCoordinates(playerID).contains(newPoint));

    }

    /**
     * Sprawdza czy point jest na liście wszystkich pozycji planszy.
     *
     * @param point pozycja
     * @return
     */
    public boolean isValidPoint(Point point) {
        return validPoints.contains(point);
    }

    /**
     * Zwraca liczbę graczy.
     *
     * @return
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Zwraca pionek na pozycji point
     *
     * @param point pozycja
     * @return
     */
    public Piece getPieceAt(Point point) {
        return piecesWithPosition.get(point);
    }

    /**
     * Zwraca id gracza, którego pionek jest na pozycji point
     *
     * @param point pozycja
     * @return
     */
    public int getPlayerIDAt(Point point) {
        return getPieceAt(point).getPlayerNumber();
    }

    /**
     * zwraca wszystkie pozycje na planszy.
     *
     * @return
     */
    public Set<Point> getValidPositions() {
        return validPoints;
    }

    /**
     * Zwraca wszystkie pozycje, na których są pionki.
     *
     * @return
     */
    public Set<Point> getAllPiecesPositions() {
        return piecesWithPosition.keySet();
    }

    /**
     * Zwraca pozycje, na których stoją pionki gracza z playerID
     *
     * @param playerID id gracza
     * @return
     */
    public Set<Point> getPlayerPiecesCoordinates(int playerID) {
        HashSet<Point> playerPieces = new HashSet<>();
        piecesWithPosition.forEach((key, value) -> {
            if (value.getPlayerNumber() == playerID) {
                playerPieces.add(key);
            }
        });
        return playerPieces;
    }

    /**
     * Zwraca id przeciwnika, do kttórego bazy musimy dojść.
     *
     * @param playerID id gracza
     * @return
     */
    public int getOpponentBaseNumber(int playerID) {
        return opponents[playerID - 1];
    }

    /**
     * Zwraca set pozycji z bazy docelowej (przeciwnika).
     *
     * @param playerID
     * @return
     */
    public Set<Point> getOpponentBaseCoordinates(int playerID) {
        int baseNumber = getOpponentBaseNumber(playerID);
        switch (baseNumber) {
            case 1 -> {
                return topBasePoints;
            }
            case 2 -> {
                return topRightBasePoints;
            }
            case 3 -> {
                return bottomRightBasePoints;
            }
            case 4 -> {
                return bottomBasePoints;
            }
            case 5 -> {
                return bottomLeftBasePoints;
            }
            case 6 -> {
                return topLeftBasePoints;
            }
            default -> throw new IllegalStateException("Unexpected value: " + baseNumber);
        }
    }

    /**
     * Ustawia id przeciwników odpowiednio dla ilości graczy.
     */
    private void setOpponents() {
        this.opponents = switch (numberOfPlayers) {
            case 2 -> new int[]{4, 1};
            case 3 -> new int[]{3, 5, 1};
            case 4 -> new int[]{4, 1, 5, 2};
            case 6 -> new int[]{4, 1, 5, 2, 6, 3};
            default -> throw new IllegalStateException("Unexpected value: " + numberOfPlayers);
        };
    }

    /**
     * Usuwa wszystkie pionki graczy z planszy.
     */
    private void clearBoard() {
        piecesWithPosition.clear();
    }

    /**
     * Tworzy Set wszystkich pól na planszy.
     *
     * @return
     */
    private static HashSet<Point> generateAllValidPoints() {
        HashSet<Point> points = new HashSet<>();
        int mxMidPoint = 12;
        for (int i = 0; i < 13; i++) {
            for (int j = 0; j <= i; j++) {
                points.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint -= 1;
        }
        mxMidPoint = 12;
        for (int i = 16; i >= 4; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                points.add(new Point(mxMidPoint + j * 2, i));
            }
            mxMidPoint--;
        }
        return points;
    }

    /**
     * Tworzy Set startowych pozycji z górnego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateTopBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 12;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j <= i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    /**
     * Tworzy Set startowych pozycji z górnego prawego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateTopRightBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 21;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }

        return points;
    }

    /**
     * Tworzy Set startowych pozycji z dolnego prawego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateBottomRightBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 21;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    /**
     * Tworzy Set startowych pozycji z dolnego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateBottomBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 12;
        for (int i = 16; i > 12; i--) {
            for (int j = 0; j <= 16 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    /**
     * Tworzy Set startowych pozycji z dolnego lewego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateBottomLeftBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 3;
        for (int i = 9; i < 13; i++) {
            for (int j = 0; j <= i - 9; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    /**
     * Tworzy Set startowych pozycji z górnego lewego rogu planszy.
     *
     * @return
     */
    private static HashSet<Point> generateTopLeftBasePoints() {
        HashSet<Point> points = new HashSet<>();
        int xMidPoint = 3;
        for (int i = 7; i >= 4; i--) {
            for (int j = 0; j <= 7 - i; j++) {
                Point point = new Point(xMidPoint + j * 2, i);
                points.add(point);
            }
            xMidPoint -= 1;
        }
        return points;
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji pierwszej (górny róg)
     *
     * @param playerID
     */
    private void setPiecesAtPositionOne(int playerID) {
        topBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji drugiej (górny prawy róg)
     *
     * @param playerID
     */
    private void setPiecesAtPositionTwo(int playerID) {
        topRightBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji trzeciej (dolny prawy róg)
     *
     * @param playerID
     */
    private void setPiecesAtPositionThree(int playerID) {
        bottomRightBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji czwartej (dolny róg)
     *
     * @param playerID
     */
    private void setPiecesAtPositionFour(int playerID) {
        bottomBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji piątej (dolny lewy róg)
     *
     * @param playerID
     */
    private void setPiecesAtPositionFive(int playerID) {
        bottomLeftBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Ustawia pionki gracza z playerID na pozycji szóstej (górny lewy róg).
     *
     * @param playerID
     */
    private void setPiecesAtPositionSix(int playerID) {
        topLeftBasePoints.forEach(point -> {
            Piece piece = new Piece(playerID);
            piecesWithPosition.put(point, piece);
        });
    }

    /**
     * Dodaje gracza: Zwraca id dla tego gracza, zwiększa liczbę graczy,ustawia pionki na odpowiednich dla graczy pozycjach.
     * @return
     */
    public int addPlayer() {
        numberOfPlayers++;
        switch (numberOfPlayers) {
            case 1 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
            }
            case 2 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setOpponents();
            }
            case 3 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionThree(2);
                setPiecesAtPositionFive(3);
                setOpponents();
            }
            case 4 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setOpponents();
            }
            case 5 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setPiecesAtPositionThree(5);
            }
            case 6 -> {
                clearBoard();
                setPiecesAtPositionOne(1);
                setPiecesAtPositionFour(2);
                setPiecesAtPositionTwo(3);
                setPiecesAtPositionFive(4);
                setPiecesAtPositionThree(5);
                setPiecesAtPositionSix(6);
                setOpponents();
            }
            default -> {
                throw new IllegalStateException("Invalid number of players.");
            }
        }
        return numberOfPlayers;
    }

    /**
     * Sprawdza czy gracz z playerID wygrał grę.
     * @param playerID ID gracza
     * @return
     */
    public boolean checkForWin(int playerID) {
        return getPlayerPiecesCoordinates(playerID)
                .equals(getOpponentBaseCoordinates(playerID));
    }

}
