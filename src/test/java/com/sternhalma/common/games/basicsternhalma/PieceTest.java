package com.sternhalma.common.games.basicsternhalma;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.awt.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PieceTest {

    @Mock
    private Piece underTest;

    @BeforeEach
    void setUp() {
        underTest = new Piece(3);
    }

    @Test
    void getPlayerNumber() {
        //when
        int expected = underTest.getPlayerNumber();

        //then
        assertThat(expected).isEqualTo(3);
    }

    @Test
    void getColorForPlayerWithID() {
        //given
        Color[] colors = new Color[]{Color.WHITE, Color.RED, Color.GREEN, Color.ORANGE, Color.YELLOW, Color.PINK, Color.CYAN};
        //then
        for (int i = 0; i <= 6; i++) {
            assertThat(Piece.getColorForPlayerWithID(i)).isEqualTo(colors[i]);
        }
    }

}