package com.devemon.games;

import com.devemon.games.domain.GameMap;
import com.devemon.games.domain.User;
import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameTest {

    @Test
    public void it_should_welcome_you() {
        setupGame();
        game.run();

        verify(messagePublisher, description("It should welcome you"))
                .accept(WELCOME_MESSAGE);
    }

    @Test
    public void it_should_retrieve_game_action_from_command_listener() {
        setupGame();
        game.run();

        verify(commandListener, description("It should retrieve game action"))
                .read();
    }

    @Test
    public void it_should_execute_the_command() {
        setupGame();
        game.run();

        verify(gameCommand, description("It should execute the command"))
                .apply(gameMap, user);
    }

    @BeforeEach
    public void setupCommandListener() {
        when(commandListener.read()).thenReturn(gameCommand);
    }

    public void setupGame() {
        this.game.setGameMap(gameMap);
        this.game.setUser(user);
    }

    @Mock
    private MessagePublisher messagePublisher;

    @Mock
    private CommandListener commandListener;

    @Mock
    private GameCommand gameCommand;

    @Mock
    private GameMap gameMap;

    @Mock
    private User user;

    @InjectMocks
    private Game game;

    private static final String WELCOME_MESSAGE = "Welcome to hunt the wumpus!";
}