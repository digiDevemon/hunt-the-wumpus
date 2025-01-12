package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static com.devemon.games.domain.elements.GameState.PLAYING;
import static org.assertj.core.api.Assertions.assertThat;

class ShootTest {

    @Test
    public void it_should_not_return_null() {
        setupMoveCommand();
        assertThat(shoot.apply(LEVEL))
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_playing_game_state() {
        setupMoveCommand();
        assertThat(shoot.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }


    public void setupMoveCommand() {
        this.shoot = new Shoot(messagePublisher, SHOOT_TARGET_ID);
    }

    @Mock
    private MessagePublisher messagePublisher;

    private final Integer SHOOT_TARGET_ID = 2;
    private final Map<String, Object> LEVEL = Map.of("state", PLAYING);
    private Shoot shoot;
}