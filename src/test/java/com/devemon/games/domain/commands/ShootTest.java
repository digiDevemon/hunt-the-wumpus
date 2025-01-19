package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.GameState.PLAYING;
import static com.devemon.games.domain.elements.SquareState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShootTest {

    @Test
    public void it_should_not_return_null() {
        setupShootCommand(NOT_REACHEABLE_SQUARE.getId());
        assertThat(shoot.apply(LEVEL))
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_playing_game_state_for_not_reacheable_square() {
        setupShootCommand(NOT_REACHEABLE_SQUARE.getId());
        assertThat(shoot.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    @Test
    public void it_should_log_not_reachable_square_shot() {
        setupShootCommand(NOT_REACHEABLE_SQUARE.getId());
        shoot.apply(LEVEL);
        verify(messagePublisher).accept(NOT_REACHABLE_SQUARE_MESSAGE);
    }

    @Test
    public void it_should_return_playing_game_state_for_not_dangerous_square() {
        setupShootCommand(NOT_DANGEROUS_SQUARE.getId());
        assertThat(shoot.apply(LEVEL))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    @Test
    public void it_should_remove_wumpus_from_previous_position() {
        setupShootCommand(NOT_DANGEROUS_SQUARE.getId());
        var map = (GameMap) shoot.apply(LEVEL).get("gameMap");
        assertThat(map.getSquareFromID(WUMPUS_SQUARE.getId()).getThreat())
                .as("The Wumpus should have moved from its original position")
                .isNotEqualTo(WUMPUS);
    }

    @Test
    public void it_should_move_wumpus_to_a_new_position() {
        setupShootCommand(NOT_DANGEROUS_SQUARE.getId());
        var map = (GameMap) shoot.apply(LEVEL).get("gameMap");
        var potentialWumpusSquares = List.of(map.getSquareFromID(NOT_DANGEROUS_SQUARE.getId()).getThreat(),
                map.getSquareFromID(NOT_REACHEABLE_SQUARE.getId()).getThreat());
        assertThat(potentialWumpusSquares)
                .as("The Wumpus should have moved to other position")
                .contains(WUMPUS);
    }

//    @Test
//    YOU ARE MODIFIYING THE CLASS MAP DURING THE TEST YOU HAVE TO CREATE IT
//    public void it_should_log_movement_of_wumpus_in_case_of_failure() {
//        setupShootCommand(NOT_DANGEROUS_SQUARE.getId());
//        shoot.apply(LEVEL);
//        verify(messagePublisher).accept(WUMPUS_MOVEMENT_CLUE);
//    }

    public void setupShootCommand(Integer targetId) {
        this.shoot = new Shoot(messagePublisher, targetId);
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
    private static final String WUMPUS_MOVEMENT_CLUE = "You failed the shoot. You listen steps somewhere.";


    private final Map<String, Object> LEVEL = Map.of("user", USER, "gameMap", GAME_MAP, "state", PLAYING);
    private Shoot shoot;
}