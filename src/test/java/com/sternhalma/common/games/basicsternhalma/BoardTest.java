package com.sternhalma.common.games.basicsternhalma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BoardTest {

    @Mock
    private Board underTest;

    @BeforeEach
    void setUp() {
        underTest = new Board();
    }

    @Test
    void testMovePiece() {

    }

    @Test
    void testIsValidMove() {
        //given
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 2);
        //when
        boolean expected = underTest.isValidMove(point1, point2);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void testIsValidMove2() {
        //given
        Point point1 = new Point(1, 1);
        Point point2 = new Point(2, 10);
        //when
        boolean expected = underTest.isValidMove(point1, point2);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void isLeavingOpponentBase() {
    }

    @Test
    void isValidJump() {

    }

    @Test
    void testIsValidPoint() {
        //given
        Point point1 = new Point(13, 1);
        //when
        boolean expected = underTest.isValidPoint(point1);
        //then
        assertThat(expected).isTrue();
    }

    @Test
    void testIsValidPoint2() {
        //given
        Point point1 = new Point(0, 0);
        //when
        boolean expected = underTest.isValidPoint(point1);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void getWinners() {
    }

    @Test
    void addWinner() {
    }

    @Test
    void getNumberOfPlayers() {
    }

    @Test
    void getPieceAt() {
    }

    @Test
    void getPlayerIDAt() {
    }

    @Test
    void getValidPositions() {
    }

    @Test
    void getAllPiecesPositions() {
    }

    @Test
    void getPlayerPiecesCoordinates() {
    }

    @Test
    void getOpponentBaseNumber() {
    }

    @Test
    void getOpponentBaseCoordinates() {
    }

    @Test
    void addPlayer() {
    }

    @Test
    void checkForWin() {
    }
}