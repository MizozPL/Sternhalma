package com.sternhalma.server.games.basicsternhalma;

import com.sternhalma.common.games.Games;
import com.sternhalma.common.games.basicsternhalma.Board;
import com.sternhalma.server.connection.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BasicSternhalmaTest {

    @Mock
    BasicSternhalma underTest;

    @BeforeEach
    void setUp() {
        underTest = new BasicSternhalma();
    }

    @Test
    void testConstructor() {
        assertThat((new BasicSternhalma()).getGameName()).isEqualTo(Games.BASIC_STERNHALMA);
        assertThat((new BasicSternhalma()).getGameName()).isEqualTo(Games.BASIC_STERNHALMA);
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
    void testJoinPlayerAndSendMessage() {
        BasicSternhalma basicSternhalma = new BasicSternhalma();
        Player player = mock(Player.class);
        doNothing().when(player).sendObject((Object) any());
        doNothing().when(player).sendMessage((String) any());
        basicSternhalma.joinPlayer(player);
        verify(player, atLeast(1)).sendMessage((String) any());
        verify(player).sendObject((Object) any());
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

    @Test
    void testGetPlayerIDFromTurn() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, NoSuchFieldException {
        Field boardField = BasicSternhalma.class.getDeclaredField("board");
        boardField.setAccessible(true);
        ((Board) boardField.get(underTest)).addPlayer();
        ((Board) boardField.get(underTest)).addPlayer();
        Method getPlayer = BasicSternhalma.class.getDeclaredMethod("getPlayerIDFromTurn", int.class);
        getPlayer.setAccessible(true);
        int result = (int) getPlayer.invoke(underTest,3);
        assertThat(result).isEqualTo(1);
    }
}