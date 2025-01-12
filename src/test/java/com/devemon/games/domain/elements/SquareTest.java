package com.devemon.games.domain.elements;

import com.devemon.games.domain.elements.Square.SquareBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.devemon.games.domain.elements.SquareState.WUMPUS;
import static org.assertj.core.api.Assertions.assertThat;

class SquareTest {

    @Test
    public void it_should_not_return_null() {
        assertThat(new SquareBuilder(ID).build())
                .as("It should not return null")
                .isInstanceOf(Square.class);
    }

    @Test
    public void it_should_return_the_expected_id() {
        assertThat(new SquareBuilder(ID).build().getId())
                .as("It should return the expected id")
                .isEqualTo(ID);
    }

    @Test
    public void it_should_return_the_expected_connection_list() {
        var square = setupSquare(CONNECTIONS_IDS);

        assertThat(square.getConnections())
                .as("It should return the expected connection list")
                .isEqualTo(CONNECTIONS_IDS);

    }

    @Test
    public void it_should_return_the_expected_connection_list_given_malformed_connection() {
        var square = setupSquare(CONNECTIONS_IDS_MALFORMED);

        assertThat(square.getConnections())
                .as("It should return the expected connection list")
                .isEqualTo(CONNECTIONS_IDS);

    }

    @Test
    public void it_should_return_the_expected_state() {
        var square = new SquareBuilder(ID).withState(EXPECTED_STATE).build();

        assertThat(square.getThreat())
                .as("It should return the expected square state")
                .isEqualTo(EXPECTED_STATE);

    }

    public Square setupSquare(List<Integer> connectionList) {
        var squareBuilder = new SquareBuilder(ID).connectedTo(connectionList);
        return squareBuilder.build();
    }

    private static final List<Integer> CONNECTIONS_IDS_MALFORMED = List.of(2, 2, 5, 5, 8);
    private static final List<Integer> CONNECTIONS_IDS = List.of(2, 5, 8);
    private static final SquareState EXPECTED_STATE = WUMPUS;
    private static final Integer ID = 1;
}