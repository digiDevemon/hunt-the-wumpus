package com.devemon.games;

import com.devemon.games.domain.GameLoader;
import com.devemon.games.domain.commands.*;
import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.apache.commons.lang3.tuple.Pair;
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
        setupCommandListenerDummyPlay();

        game.run();

        verify(messagePublisher, description("It should welcome you"))
                .accept(WELCOME_MESSAGE);
    }

    @Test
    public void it_should_load_the_game() {
        setupCommandListenerDummyPlay();

        game.run();

        verify(gameLoader, description("It should load the game"))
                .load();
    }

    @Test
    public void it_should_retrieve_game_action_from_command_listener_in_dummy_play() {
        setupCommandListenerDummyPlay();

        game.run();

        verify(commandListener, atLeast(2))
                .read();
    }

    @Test
    public void it_should_execute_the_unknown_command_in_dummy_play() {
        setupCommandListenerDummyPlay();

        game.run();

        verify(unknown, description("It should execute the command"))
                .apply(gameMap, user);
    }

    @Test
    public void it_should_execute_the_move_command_in_fail_play() {
        setupCommandListenerFailPlay();

        game.run();

        verify(move, description("It should execute the command"))
                .apply(gameMap, user);
    }

    @Test
    public void it_should_execute_the_shot_command_in_fail_play() {
        setupCommandListenerFailPlay();

        game.run();

        verify(move, description("It should execute the command"))
                .apply(gameMap, user);
    }

    @Test
    public void it_should_not_execute_the_undefined_command_in_fail_play() {
        setupCommandListenerFailPlay();

        game.run();

        verify(undefinedCommand, never())
                .apply(gameMap, user);
    }

    @Test
    public void it_should_notify_you_for_not_allowed_commands_in_fail_play() {
        setupCommandListenerFailPlay();

        game.run();

        verify(messagePublisher, description("It should notify you that it is not allowed action now."))
                .accept(NOT_ALLOWED_MESSAGE);
    }

    public void setupCommandListenerDummyPlay() {
        when(commandListener.read())
                .thenReturn(unknown).thenReturn(exit);
    }

    public void setupCommandListenerFailPlay() {
        when(commandListener.read())
                .thenReturn(move).thenReturn(shot).thenReturn(undefinedCommand).thenReturn(exit);
    }

    @BeforeEach
    public void setupGameLoader() {
        when(gameLoader.load()).thenReturn(Pair.of(gameMap, user));
    }


    @Mock
    private MessagePublisher messagePublisher;

    @Mock
    private CommandListener commandListener;

    @Mock
    private Unknown unknown;

    @Mock
    private Move move;

    @Mock
    private Shot shot;

    @Mock
    private GameCommand undefinedCommand;

    @Mock
    private Exit exit;

    @Mock
    private GameLoader gameLoader;

    @Mock
    private GameMap gameMap;

    @Mock
    private User user;

    @InjectMocks
    private Game game;

    private static final String WELCOME_MESSAGE = "Welcome to hunt the wumpus!";
    private static final String NOT_ALLOWED_MESSAGE = "Pf.Oak: It is not time to use this!";

}