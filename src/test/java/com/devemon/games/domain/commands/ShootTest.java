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

import static com.devemon.games.domain.elements.GameState.FINISHED;
import static com.devemon.games.domain.elements.GameState.PLAYING;
import static com.devemon.games.domain.elements.SquareState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ShootTest {

    @Test
    public void it_should_not_return_null() {
        setupShootCommand(NOT_REACHEABLE_SQUARE_ID);
        assertThat(shoot.apply(getLevel(getGameMap())))
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_playing_game_state_for_not_reacheable_square() {
        setupShootCommand(NOT_REACHEABLE_SQUARE_ID);
        assertThat(shoot.apply(getLevel(getGameMap())))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }


    @Test
    public void it_should_subtract_ammunition_from_user(){
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        var user = (User) shoot.apply(getLevel(getGameMap())).get("user");

        assertThat(user.getAmmunition())
                .as("It should subtract the ammunition")
                .isEqualTo(2);
    }

    @Test
    public void it_should_log_not_enough_ammunition() {
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        var user = new User(INITIAL_SQUARE_ID, 0);
        shoot.apply(getLevel(getGameMap(), user));
        verify(messagePublisher).accept(NO_AMMUNITION_MESSAGE);
    }


    @Test
    public void it_should_log_not_reachable_square_shot() {
        setupShootCommand(NOT_REACHEABLE_SQUARE_ID);
        shoot.apply(getLevel(getGameMap()));
        verify(messagePublisher).accept(NOT_REACHABLE_SQUARE_MESSAGE);
    }

    @Test
    public void it_should_return_playing_game_state_for_not_dangerous_square() {
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        assertThat(shoot.apply(getLevel(getGameMap())))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    @Test
    public void it_should_remove_wumpus_from_previous_position() {
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        var map = (GameMap) shoot.apply(getLevel(getGameMap())).get("gameMap");
        assertThat(map.getSquareFromID(WUMPUS_SQUARE_ID).getThreat())
                .as("The Wumpus should have moved from its original position")
                .isNotEqualTo(WUMPUS);
    }

    @Test
    public void it_should_move_wumpus_to_a_new_position() {
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        var map = (GameMap) shoot.apply(getLevel(getGameMap())).get("gameMap");
        var potentialWumpusSquares = List.of(map.getSquareFromID(NOT_DANGEROUS_SQUARE_ID).getThreat(),
                map.getSquareFromID(NOT_REACHEABLE_SQUARE_ID).getThreat());
        assertThat(potentialWumpusSquares)
                .as("The Wumpus should have moved to other position")
                .contains(WUMPUS);
    }

    @Test
    public void it_should_log_movement_of_wumpus_in_case_of_failure() {
        setupShootCommand(NOT_DANGEROUS_SQUARE_ID);
        shoot.apply(getLevel(getGameMap()));
        verify(messagePublisher).accept(WUMPUS_MOVEMENT_CLUE);
    }

    @Test
    public void it_should_remove_wumpus_threat_from_square() {
        setupShootCommand(WUMPUS_SQUARE_ID);
        var map = (GameMap) shoot.apply(getLevel(getGameMap())).get("gameMap");
        var wumpusSquare = map.getSquareFromID(WUMPUS_SQUARE_ID);
        assertThat(wumpusSquare.getThreat())
                .as("It should remove wumpus threat")
                .isEqualTo(NONE);
    }

    @Test
    public void it_should_log_wumpus_shot() {
        setupShootCommand(WUMPUS_SQUARE_ID);
        shoot.apply(getLevel(getGameMap()));
        verify(messagePublisher).accept(WUMPUS_SUCESS_SHOOT_MESSAGE);
    }

    @Test
    public void it_should_finish_the_game_if_there_are_no_more_wumpus() {
        setupShootCommand(WUMPUS_SQUARE_ID);
        assertThat(shoot.apply(getLevel(getGameMap())))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(FINISHED);
    }

    @Test
    public void it_should_log_you_win_when_there_are_no_more_wumpus() {
        setupShootCommand(WUMPUS_SQUARE_ID);
        shoot.apply(getLevel(getGameMap()));
        verify(messagePublisher).accept(WIN_MESSAGE);
    }

    @Test
    public void it_should_continue_the_game_if_there_are_more_wumpus() {
        setupShootCommand(WUMPUS_SQUARE_ID);
        assertThat(shoot.apply(getLevel(getDoubleWumpusGameMap())))
                .as("It should return the expected game state")
                .extracting("state")
                .isEqualTo(PLAYING);
    }

    public void setupShootCommand(Integer targetId) {
        this.shoot = new Shoot(messagePublisher, targetId);
    }

    public GameMap getGameMap() {
        var initialSquare = new Square.SquareBuilder(1).connectedTo(List.of(2, 3, 4, 5)).build();
        var notDangerousSquare = new Square.SquareBuilder(2).connectedTo(List.of(1, 3, 6)).build();
        var wumpusSquare = new Square.SquareBuilder(3).connectedTo(List.of(1, 2, 3, 6)).withState(WUMPUS).build();
        var holeSquare = new Square.SquareBuilder(4).connectedTo(List.of(1, 3, 5, 6)).withState(HOLE).build();
        var batsSquare = new Square.SquareBuilder(5).connectedTo(List.of(1, 4, 6)).withState(BATS).build();
        var notReacheableSquare = new Square.SquareBuilder(6).connectedTo(List.of(2, 3, 4, 5)).build();


        return new GameMap.GameMapBuilder()
                .withSquare(initialSquare)
                .withSquare(notDangerousSquare)
                .withSquare(wumpusSquare)
                .withSquare(holeSquare)
                .withSquare(batsSquare)
                .withSquare(notReacheableSquare)
                .build();
    }

    public GameMap getDoubleWumpusGameMap() {
        var initialSquare = new Square.SquareBuilder(INITIAL_SQUARE_ID).connectedTo(List.of(2, 3, 4, 5)).build();
        var notDangerousSquare = new Square.SquareBuilder(NOT_DANGEROUS_SQUARE_ID).connectedTo(List.of(1, 3, 6)).withState(WUMPUS).build();
        var wumpusSquare = new Square.SquareBuilder(WUMPUS_SQUARE_ID).connectedTo(List.of(1, 2, 3, 6)).withState(WUMPUS).build();
        var holeSquare = new Square.SquareBuilder(HOLE_SQUARE_ID).connectedTo(List.of(1, 3, 5, 6)).withState(HOLE).build();
        var batsSquare = new Square.SquareBuilder(BATS_SQUARE_ID).connectedTo(List.of(1, 4, 6)).withState(BATS).build();
        var notReacheableSquare = new Square.SquareBuilder(NOT_REACHEABLE_SQUARE_ID).connectedTo(List.of(2, 3, 4, 5)).build();


        return new GameMap.GameMapBuilder()
                .withSquare(initialSquare)
                .withSquare(notDangerousSquare)
                .withSquare(wumpusSquare)
                .withSquare(holeSquare)
                .withSquare(batsSquare)
                .withSquare(notReacheableSquare)
                .build();
    }

    public Map<String, Object> getLevel(GameMap gameMap, User user) {
        return Map.of("user", user, "gameMap", gameMap, "state", PLAYING);
    }

    public Map<String, Object> getLevel(GameMap gameMap) {
        return Map.of("user", new User(INITIAL_SQUARE_ID), "gameMap", gameMap, "state", PLAYING);
    }

    @Mock
    private MessagePublisher messagePublisher;

    private static final Integer INITIAL_SQUARE_ID = 1;
    private static final Integer NOT_DANGEROUS_SQUARE_ID = 2;
    private static final Integer WUMPUS_SQUARE_ID = 3;
    private static final Integer HOLE_SQUARE_ID = 4;
    private static final Integer BATS_SQUARE_ID = 5;
    private static final Integer NOT_REACHEABLE_SQUARE_ID = 6;


    private static final String NOT_REACHABLE_SQUARE_MESSAGE = "This room is not reachable from your current position";
    private static final String WUMPUS_MOVEMENT_CLUE = "You failed the shoot. You listen steps somewhere.";
    private static final String WUMPUS_SUCESS_SHOOT_MESSAGE = "You hear a roar. You have slide a wumpus.";
    private static final String WIN_MESSAGE = "You killed all the wumpus. You WIN!";
    private static final String NO_AMMUNITION_MESSAGE = "Not enough ammunition to shoot";

    private Shoot shoot;
}