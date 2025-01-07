package com.devemon.games;

import com.devemon.games.logging.ClueLogging;
import com.devemon.games.domain.GameLoader;
import com.devemon.games.domain.commands.*;
import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

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
    public void it_should_show_level_clues() {
        setupCommandListenerDummyPlay();

        game.run();

        verify(clueLogging, description("It should show level clues"))
                .logFeelingsClue(level);
    }

    @Test
    public void it_should_show_near_rooms() {
        setupCommandListenerDummyPlay();

        game.run();

        verify(clueLogging, description("It should show level clues"))
                .logNearRooms(level);
    }

    @Test
    public void it_should_retrieve_game_action_from_command_listener_in_dummy_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(commandListener, atLeast(4))
                .read();
    }

    @Test
    public void it_should_execute_the_unknown_command_in_dummy_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(unknown, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_execute_the_move_command_in_fail_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(move, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_execute_the_shot_command_in_fail_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(move, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_not_execute_the_undefined_command_in_fail_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(undefinedCommand, never())
                .apply(level);
    }

    @Test
    public void it_should_notify_you_for_not_allowed_commands_in_fail_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(messagePublisher, description("It should notify you that it is not allowed action now."))
                .accept(NOT_ALLOWED_MESSAGE);
    }

    public void setupCommandListenerDummyPlay() {
        when(commandListener.read())
                .thenReturn(exit);
    }


    public void setupCommandListenerUsualPlay() {
        when(commandListener.read())
                .thenReturn(move).thenReturn(shot).thenReturn(unknown).thenReturn(undefinedCommand).thenReturn(exit);
    }

    @BeforeEach
    public void setupGameLoader() {
        level = Map.of("user", user, "gameMap", gameMap);
        when(gameLoader.load()).thenReturn(level);
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
    private Shoot shot;

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

    @Mock
    private ClueLogging clueLogging;

    @InjectMocks
    private Game game;

    private static Map<String, Object> level;

    private static final String WELCOME_MESSAGE = "Welcome to hunt the wumpus!";
    private static final String NOT_ALLOWED_MESSAGE = "Pf.Oak: It is not time to use this!";

}