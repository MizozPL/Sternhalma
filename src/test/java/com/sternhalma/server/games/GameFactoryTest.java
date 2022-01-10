package com.sternhalma.server.games;

import com.sternhalma.common.games.Games;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameFactoryTest {
    @Test
    void testCreateGame() {
        assertThat((new GameFactory()).createGame("Game Name")).isNull();

        assertThat((new GameFactory())
                .createGame(Games.BASIC_STERNHALMA))
                .isInstanceOf(com.sternhalma.server.games.basicsternhalma.BasicSternhalma.class);

    }
}
