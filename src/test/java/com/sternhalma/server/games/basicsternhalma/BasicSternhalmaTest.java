package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.games.basicsternhalma.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;
import java.lang.reflect.Field;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BasicSternhalmaTest {

    @Mock
    BasicSternhalma underTest;

    @BeforeEach
    void setUp() {
        underTest = new BasicSternhalma();
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
    void testIsValidJump() throws NoSuchFieldException, IllegalAccessException {
        //given
        Field boardField = BasicSternhalma.class.getDeclaredField("board");
        boardField.setAccessible(true);
        Point oldPoint = new Point(10, 2);
        Point newPoint = new Point(8, 4);
        //when
        ((Board) boardField.get(underTest)).addPlayer();
        //then
        assertThat(underTest.isValidJump(oldPoint, newPoint)).isTrue();
    }

    @Test
    void testIsValidJump2() throws NoSuchFieldException, IllegalAccessException {
        //given
        Field boardField = BasicSternhalma.class.getDeclaredField("board");
        boardField.setAccessible(true);
        Point oldPoint = new Point(9, 3);
        Point newPoint = new Point(7, 5);
        //when
        ((Board) boardField.get(underTest)).addPlayer();
        //then
        assertThat(underTest.isValidJump(oldPoint, newPoint)).isFalse();
    }

}