package com.sternhalma.client.games;

import com.sternhalma.common.games.Games;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GameFactoryTest {

    @Test
    void testCreateGame() {
        assertThat(com.sternhalma.client.games.GameFactory.createGameInstance("Game Name"))
                .isNull();

        assertThat(com.sternhalma.client.games.GameFactory.createGameInstance(Games.BASIC_STERNHALMA))
                .isInstanceOf(com.sternhalma.client.games.basicsternhalma.BasicSternhalma.class);
    }
}