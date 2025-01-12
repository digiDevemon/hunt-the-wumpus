package com.devemon.games;

import com.devemon.games.domain.GameLoader;
import com.devemon.games.domain.commands.Exit;
import com.devemon.games.domain.commands.Move;
import com.devemon.games.domain.commands.Shoot;
import com.devemon.games.domain.commands.Unknown;
import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.ClueLogging;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.FINISHED;
import static com.devemon.games.domain.commands.GameState.PLAYING;
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
    public void it_should_execute_the_unknown_command_in_usual_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(unknown, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_execute_the_move_command_in_usual_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(move, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_execute_the_shot_command_in_usual_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(shot, description("It should execute the command"))
                .apply(level);
    }

    @Test
    public void it_should_execute_the_exit_command_in_usual_play() {
        setupCommandListenerUsualPlay();

        game.run();

        verify(exit, description("It should execute the command"))
                .apply(level);
    }

    public void setupCommandListenerDummyPlay() {
        when(commandListener.read())
                .thenReturn(exit);
    }


    public void setupCommandListenerUsualPlay() {
        when(commandListener.read())
                .thenReturn(move).thenReturn(shot).thenReturn(unknown).thenReturn(exit);
    }

    @BeforeEach
    public void setupGameLoader() {
        level = Map.of("user", user, "gameMap", gameMap);
        when(gameLoader.load()).thenReturn(level);
    }

    @BeforeEach
    public void setupGameCommandResults() {
        lenient().when(move.apply(any())).thenReturn(PLAYING);
        lenient().when(shot.apply(any())).thenReturn(PLAYING);
        lenient().when(unknown.apply(any())).thenReturn(PLAYING);
        lenient().when(exit.apply(any())).thenReturn(FINISHED);
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

}