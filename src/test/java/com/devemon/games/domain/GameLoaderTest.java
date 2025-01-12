package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import org.junit.jupiter.api.Test;

import static com.devemon.games.domain.elements.SquareState.*;
import static org.assertj.core.api.Assertions.assertThat;

class GameLoaderTest {

    @Test
    public void it_should_not_return_null() {
        assertThat(gameLoader.load())
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_return_game_map_in_key_gameMap() {
        assertThat(gameLoader.load())
                .as("It should contains game map in gameMap key")
                .extracting("gameMap")
                .isInstanceOf(GameMap.class);
    }

    @Test
    public void it_should_contains_twenty_squares() {
        var map = (GameMap) gameLoader.load().get("gameMap");
        assertThat(map.getSquares())
                .as("It should has twelve squares")
                .hasSize(20);
    }

    @Test
    public void it_should_contains_the_expected_number_of_holes() {
        var map = (GameMap) gameLoader.load().get("gameMap");
        var holeSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(HOLE)).toList();
        assertThat(holeSquares.size())
                .as("It should contains the expected number of holes")
                .isEqualTo(EXPECTED_NUMBER_OF_HOLES);
    }

    @Test
    public void it_should_contains_the_expected_number_of_bats() {
        var map = (GameMap) gameLoader.load().get("gameMap");
        var batsSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(BATS)).toList();
        assertThat(batsSquares.size())
                .as("It should contains the expected number of bats")
                .isEqualTo(EXPECTED_NUMBER_OF_BATS);
    }

    @Test
    public void it_should_contains_the_expected_number_of_wumpus() {
        var map = (GameMap) gameLoader.load().get("gameMap");
        var wumpusSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(WUMPUS)).toList();
        assertThat(wumpusSquares.size())
                .as("It should contains the expected number of wumpus")
                .isEqualTo(EXPECTED_NUMBER_OF_WUMPUS);
    }

    @Test
    public void it_should_return_user_in_key_user() {
        assertThat(gameLoader.load())
                .as("It should contains user in user key")
                .extracting("user")
                .isInstanceOf(User.class);
    }

    @Test
    public void it_should_return_an_user_outside_dangerous_square() {
        var gameLevel = gameLoader.load();
        var map = (GameMap) gameLevel.get("gameMap");
        var user = (User) gameLevel.get("user");
        var holeSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(HOLE)).map(Square::getId).toList();
        var batsSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(BATS)).map(Square::getId).toList();
        var wumpusSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(WUMPUS)).map(Square::getId).toList();


        assertThat(!holeSquares.contains(user.getPositionId())
                && !batsSquares.contains(user.getPositionId())
                && !wumpusSquares.contains(user.getPositionId()))
                .as("It should be outside threat square")
                .isTrue();
    }

    private static final GameLoader gameLoader = new GameLoader();
    private static final Integer EXPECTED_NUMBER_OF_HOLES = 2;
    private static final Integer EXPECTED_NUMBER_OF_BATS = 2;
    private static final Integer EXPECTED_NUMBER_OF_WUMPUS = 1;
}