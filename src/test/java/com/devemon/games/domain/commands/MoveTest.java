package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.GameState.FINISHED;
import static com.devemon.games.domain.elements.GameState.PLAYING;
import static com.devemon.games.domain.elements.SquareState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MoveTest {

    @Test
    public void it_should_not_return_null() {
        setupMoveCommand(NOT_DANGEROUS_SQUARE.getId());
        assertThat(move.apply(LEVEL))
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_playing_game_state() {
        setupMoveCommand(NOT_DANGEROUS_SQUARE.getId());
        assertThat(move.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    @Test
    public void it_should_move_user_to_not_dangerous_square() {
        setupMoveCommand(NOT_DANGEROUS_SQUARE.getId());
        var user = (User) move.apply(LEVEL).get("user");

        assertThat(user.getPositionId())
                .as("It should move the user")
                .isEqualTo(NOT_DANGEROUS_SQUARE.getId());
    }


    @Test
    public void it_should_not_move_user_to_not_reachable_square() {
        setupMoveCommand(NOT_REACHEABLE_SQUARE.getId());
        var user = (User) move.apply(LEVEL).get("user");

        assertThat(user.getPositionId())
                .as("It should move the user")
                .isEqualTo(INITIAL_SQUARE.getId());
    }

    @Test
    public void it_should_log_when_the_move_is_not_reachable() {
        setupMoveCommand(NOT_REACHEABLE_SQUARE.getId());
        move.apply(LEVEL);
        verify(messagePublisher).accept(NOT_REACHABLE_SQUARE_MESSAGE);
    }

    @ParameterizedTest
    @MethodSource("dangerousSquares")
    public void it_should_stop_level_if_user_reaches_threat_square(Square targetSquare) {
        setupMoveCommand(targetSquare.getId());
        assertThat(move.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(FINISHED);
    }

    @ParameterizedTest
    @MethodSource("dangerousSquares")
    public void it_should_log_when_is_finished(Square targetSquare) {
        setupMoveCommand(targetSquare.getId());
        move.apply(LEVEL);
        verify(messagePublisher).accept(FINISHED_MESSAGE);
    }

    @Test
    public void it_should_teleport_user_when_bat_square_is_reached() {
        setupMoveCommand(BATS_SQUARE.getId());
        var user = (User) move.apply(LEVEL).get("user");

        assertThat(user.getPositionId())
                .as("It should return the user position")
                .isIn(List.of(NOT_DANGEROUS_SQUARE.getId(), NOT_REACHEABLE_SQUARE.getId()));
    }

    @Test
    public void it_should_return_the_expected_level_state() {
        setupMoveCommand(BATS_SQUARE.getId());
        assertThat(move.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    @Test
    public void it_should_log_when_is_finished() {
        setupMoveCommand(BATS_SQUARE.getId());
        move.apply(LEVEL);
        verify(messagePublisher).accept(TELEPORT_MESSAGE);
    }

    public void setupMoveCommand(Integer targetId) {
        this.move = new Move(messagePublisher, targetId);
    }

    private static List<Square> dangerousSquares() {
        return List.of(
                WUMPUS_SQUARE,
                HOLE_SQUARE
        );
    }

    @Mock
    private MessagePublisher messagePublisher;


    private static final Square INITIAL_SQUARE = new Square.SquareBuilder(1).connectedTo(List.of(2, 3, 4, 5)).build();
    private static final Square NOT_DANGEROUS_SQUARE = new Square.SquareBuilder(2).connectedTo(List.of(1, 3, 6)).build();
    private static final Square WUMPUS_SQUARE = new Square.SquareBuilder(3).connectedTo(List.of(1, 2, 3, 6)).withState(WUMPUS).build();
    private static final Square HOLE_SQUARE = new Square.SquareBuilder(4).connectedTo(List.of(1, 3, 5, 6)).withState(HOLE).build();
    private static final Square BATS_SQUARE = new Square.SquareBuilder(5).connectedTo(List.of(1, 4, 6)).withState(BATS).build();
    private static final Square NOT_REACHEABLE_SQUARE = new Square.SquareBuilder(6).connectedTo(List.of(2, 3, 4, 5)).build();


    private final GameMap GAME_MAP = new GameMap.GameMapBuilder()
            .withSquare(INITIAL_SQUARE)
            .withSquare(NOT_DANGEROUS_SQUARE)
            .withSquare(WUMPUS_SQUARE)
            .withSquare(HOLE_SQUARE)
            .withSquare(BATS_SQUARE)
            .withSquare(NOT_REACHEABLE_SQUARE)
            .build();
    private final User USER = new User(INITIAL_SQUARE.getId());

    private static final String NOT_REACHABLE_SQUARE_MESSAGE = "This room is not reachable from your current position";
    private static final String FINISHED_MESSAGE = "You dead";
    private static final String TELEPORT_MESSAGE = "You run away escaping from bats";


    private final Map<String, Object> LEVEL = Map.of("user", USER, "gameMap", GAME_MAP, "state", PLAYING);
    private Move move;
}