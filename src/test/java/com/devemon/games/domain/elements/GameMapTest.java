package com.devemon.games.domain.elements;

import com.devemon.games.domain.elements.GameMap.GameMapBuilder;
import com.devemon.games.domain.elements.exceptions.NotValidMap;
import com.devemon.games.domain.elements.exceptions.SquareDuplication;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devemon.games.domain.elements.SquareState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameMapTest {

    @Test
    public void it_should_not_return_null() {
        assertThat(new GameMapBuilder().build())
                .as("It should not return null")
                .isInstanceOf(GameMap.class);

    }


    @Test
    public void it_should_return_the_expected_connected_square_list_from_given_square_id() {
        var gameMap = new GameMapBuilder().withSquare(HOLE_SQUARE).withSquare(WUMPUS_SQUARE).build();
        assertThat(gameMap.getConnectedSquares(HOLE_SQUARE_ID))
                .as("It should contains the expected connection")
                .contains(WUMPUS_SQUARE);
    }

    @Test
    public void it_should_throw_an_exception_when_the_map_is_not_completely_connected() {
        var gameMapBuilder = new GameMapBuilder().withSquare(HOLE_SQUARE).withSquare(WUMPUS_SQUARE).withSquare(NOT_CONNECTED_SQUARE);

        assertThrows(NotValidMap.class,
                gameMapBuilder::build, "It should throw a not valid map exception");

    }

    @Test
    public void it_should_throw_an_exception_when_the_map_has_duplicated_ids() {
        var gameMapBuilder = new GameMapBuilder().withSquare(HOLE_SQUARE).withSquare(WUMPUS_SQUARE).withSquare(DUPLICATED_SQUARE);

        assertThrows(SquareDuplication.class,
                gameMapBuilder::build, "It should throw a not square duplication exception");

    }


    private static final Integer HOLE_SQUARE_ID = 1;
    private static final Integer WUMPUS_SQUARE_ID = 2;
    private static final Square HOLE_SQUARE = new Square.SquareBuilder(HOLE_SQUARE_ID).connectedTo(List.of(WUMPUS_SQUARE_ID)).withState(HOLE).build();
    private static final Square WUMPUS_SQUARE = new Square.SquareBuilder(WUMPUS_SQUARE_ID).connectedTo(List.of(HOLE_SQUARE_ID)).withState(WUMPUS).build();
    private static final Square NOT_CONNECTED_SQUARE = new Square.SquareBuilder(77).withState(NONE).build();
    private static final Square DUPLICATED_SQUARE =  new Square.SquareBuilder(HOLE_SQUARE_ID).connectedTo(List.of(WUMPUS_SQUARE_ID)).withState(BATS).build();
}