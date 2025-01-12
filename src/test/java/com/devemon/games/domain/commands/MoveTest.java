package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.GameState.PLAYING;
import static org.assertj.core.api.Assertions.assertThat;

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


    public void setupMoveCommand(Integer targetId) {
        this.move = new Move(messagePublisher, targetId);
    }

    @Mock
    private MessagePublisher messagePublisher;


    private final Square INITIAL_SQUARE = new Square.SquareBuilder(1).connectedTo(List.of(2, 3, 4, 5)).build();
    private final Square NOT_DANGEROUS_SQUARE = new Square.SquareBuilder(2).connectedTo(List.of(1, 3)).build();
    private final Square WUMPUS_SQUARE = new Square.SquareBuilder(3).connectedTo(List.of(1, 2, 3)).build();
    private final Square HOLE_SQUARE = new Square.SquareBuilder(4).connectedTo(List.of(1, 3, 5)).build();
    private final Square BATS_SQUARE = new Square.SquareBuilder(5).connectedTo(List.of(1, 4)).build();


    private final GameMap GAME_MAP = new GameMap.GameMapBuilder()
            .withSquare(INITIAL_SQUARE)
            .withSquare(NOT_DANGEROUS_SQUARE)
            .withSquare(WUMPUS_SQUARE)
            .withSquare(HOLE_SQUARE)
            .withSquare(BATS_SQUARE)
            .build();
    private final User USER = new User(INITIAL_SQUARE.getId());

    private final Map<String, Object> LEVEL = Map.of("user", USER, "gameMap", GAME_MAP, "state", PLAYING);
    private Move move;
}