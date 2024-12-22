package com.devemon.games;

import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Test
    public void it_should_welcome_you() {
        game.run();

        verify(messagePublisher).accept(WELCOME_MESSAGE);
    }

    @Test
    public void it_should_retrieve_game_action_from_command_listener() {
        game.run();

        verify(commandListener).read();
    }

    @Mock
    private MessagePublisher messagePublisher;

    @Mock
    private CommandListener commandListener;

    @InjectMocks
    private Game game;

    private static final String WELCOME_MESSAGE = "Welcome to hunt the wumpus!";
}