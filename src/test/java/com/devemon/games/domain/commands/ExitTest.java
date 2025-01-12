package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.devemon.games.domain.elements.GameState.FINISHED;
import static com.devemon.games.domain.elements.GameState.PLAYING;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.description;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ExitTest {

    @Test
    public void it_should_notify_exit_game() {
        exit.apply(LEVEL);
        verify(messagePublisher, description("It should publish exit message")).accept(EXIT_MESSAGE);
    }

    @Test
    public void it_should_return_finished_game_state() {
        assertThat(exit.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(FINISHED);
    }

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private Exit exit;

    private static final String EXIT_MESSAGE = "Exit game...";
    private static final Map<String, Object> LEVEL = Map.of("state", PLAYING);
}