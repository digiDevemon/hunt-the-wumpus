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

    public void setThreat(SquareState squareState) {
        this.squareState = squareState;
    }


    private final List<Integer> connections;
    private SquareState squareState;
    private final Integer id;


    public static class SquareBuilder {


        public Square build() {
            return new Square(this);
        }

        public SquareBuilder connectedTo(List<Integer> squareIdList) {
            if (connections.contains(squareIdList)) {
                return this;
            }
            squareIdList.stream()
                    .filter(connectionID -> !connections.contains(connectionID))
                    .forEach(connections::add);
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
