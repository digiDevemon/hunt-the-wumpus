package com.devemon.games.domain.elements;

import java.util.ArrayList;
import java.util.List;

import static com.devemon.games.domain.elements.SquareState.NONE;

public class Square {

    public Square(SquareBuilder squareBuilder) {
        this.squareState = squareBuilder.squareState;
        this.connections = squareBuilder.connections;
        this.id = squareBuilder.id;
    }

    public SquareState getThreat() {
        return this.squareState;
    }

    public List<Integer> getConnections() {
        return this.connections;
    }

    public Integer getId() {
        return this.id;
    }


    private final List<Integer> connections;
    private final SquareState squareState;
    private final Integer id;


    public static class SquareBuilder {


        public Square build() {
            return new Square(this);
        }

        public SquareBuilder connectedTo(Integer squareId) {
            if (connections.contains(squareId)) {
                return this;
            }
            connections.add(squareId);
            return this;
        }

        public SquareBuilder withState(SquareState squareState) {
            this.squareState = squareState;
            return this;
        }

        public SquareBuilder(Integer id) {
            this.id = id;
        }

        private ArrayList<Integer> connections = new ArrayList<>();
        private SquareState squareState = NONE;
        private final Integer id;
    }
}
