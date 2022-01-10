package com.sternhalma.common.games.basicsternhalma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Mock
    private Board underTest;

    @BeforeEach
    void setUp() {
        underTest = new Board();
    }

    @Test
    void testConstructor() {
        assertEquals(0, underTest.getNumberOfPlayers());
    }

    @Test
    void testMovePiece() {
        underTest.addPlayer();
        Piece before = underTest.getPieceAt(new Point(9, 3));
        underTest.movePiece(new Point(9, 3), new Point(8, 4));
        Piece after = underTest.getPieceAt(new Point(8, 4));
        assertThat(before).isEqualTo(after);

    }

    @Test
    void testGetPlayerIDAt() {
        underTest.addPlayer();
        assertThat(underTest.getPlayerIDAt(new Point(12, 0))).isEqualTo(1);
    }

    @Test
    void testGetOpponentBaseNumber() {
        underTest.addPlayer();
        underTest.addPlayer();
        assertThat(underTest.getOpponentBaseNumber(1)).isEqualTo(4);
    }

    @Test
    void testGetOpponentBaseCoordinates() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(1).size());
    }

    @Test
    void testGetOpponentBaseCoordinates2() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(1).size());
    }

    @Test
    void testGetOpponentBaseCoordinates3() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(2).size());
    }

    @Test
    void testGetOpponentBaseCoordinates4() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(2).size());
    }

    @Test
    void testGetOpponentBaseCoordinates5() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(4).size());
    }

    @Test
    void testGetOpponentBaseCoordinates6() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        board.addPlayer();
        assertEquals(10, board.getOpponentBaseCoordinates(5).size());
    }

    @Test
    void isLeavingOpponentBase() {
        //given
        Point tempOldPoint = new Point(9, 13);
        Point tempOewPoint = new Point(8, 12);
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
    void testGetPieceAt() {
        assertNull(underTest.getPieceAt(new Point(2, 3)));
    }

    @Test
    void testGetPieceAt2() {
        underTest.addPlayer();
        assertNotNull(underTest.getPieceAt(new Point(12, 0)));
    }


    @Test
    void testAddPlayer() {
        assertThat(underTest.addPlayer()).isEqualTo(1);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(1);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(10);
    }

    @Test
    void testAddPlayer2() {
        underTest.addPlayer();
        assertThat(underTest.addPlayer()).isEqualTo(2);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(2);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(20);
    }

    @Test
    void testAddPlayer3() {
        underTest.addPlayer();
        underTest.addPlayer();
        assertThat(underTest.addPlayer()).isEqualTo(3);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(3);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(30);
    }

    @Test
    void testAddPlayer4() {
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        assertThat(underTest.addPlayer()).isEqualTo(4);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(4);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(40);
    }

    @Test
    void testAddPlayer5() {
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        assertThat(underTest.addPlayer()).isEqualTo(5);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(5);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(50);
    }

    @Test
    void testAddPlayer6() {
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        assertThat(underTest.addPlayer()).isEqualTo(6);
        assertThat(underTest.getNumberOfPlayers()).isEqualTo(6);
        assertThat(underTest.getAllPiecesPositions().size()).isEqualTo(60);
    }

    @Test
    void testAddPlayer7() {
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        underTest.addPlayer();
        assertThatThrownBy(() -> underTest.addPlayer())
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Invalid number of players.");
    }

    @Test
    void testCheckForWin() {
        Board board = new Board();
        board.addPlayer();
        board.addPlayer();
        assertFalse(board.checkForWin(1));
    }
}