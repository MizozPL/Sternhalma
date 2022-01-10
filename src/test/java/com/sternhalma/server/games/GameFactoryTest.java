package com.sternhalma.server.games;

import com.sternhalma.common.games.Games;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameFactoryTest {
    @Test
    void testCreateGame() {
        assertThat(com.sternhalma.server.games.GameFactory.createGameInstance("Game Name"))
                .isNull();

        assertThat(com.sternhalma.server.games.GameFactory.createGameInstance(Games.BASIC_STERNHALMA))
                .isInstanceOf(com.sternhalma.server.games.basicsternhalma.BasicSternhalma.class);
    }

    @Test
    void createGame() {
    }
}
