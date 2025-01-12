package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.*;
import static org.assertj.core.api.Assertions.assertThat;

class MoveTest {

    @Test
    public void it_should_not_return_null() {
        setupMoveCommand();
        assertThat(move.apply(LEVEL))
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_playing_game_state() {
        setupMoveCommand();
        assertThat(move.apply(LEVEL))
                .as("It should not return null")
                .isEqualTo(PLAYING);
    }


    public void setupMoveCommand() {
        this.move = new Move(messagePublisher, MOVE_TARGET_ID);
    }

    @Mock
    private MessagePublisher messagePublisher;

    private final Integer MOVE_TARGET_ID = 2;
    private final Map<String, Object> LEVEL = Map.of();
    private Move move;
}