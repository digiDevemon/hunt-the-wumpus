package com.devemon.games.domain.elements;

import com.devemon.games.domain.elements.exceptions.NotValidMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class GameMap {
    public GameMap(GameMapBuilder gameMapBuilder) {
        this.squares = gameMapBuilder.squares;
    }

    private Collection<Square> squares;

    public Collection<Square> getConnectedSquares(Integer positionId) {
        return getSquareFromID(positionId).getConnections().stream()
                .map(this::getSquareFromID)
                .collect(Collectors.toList());
    }

    private Square getSquareFromID(Integer id) {
        return squares.stream()
                .filter(square -> id.equals(square.getId()))
                .findFirst()
                .orElseThrow();
    }


    public static class GameMapBuilder {


        public GameMap build() throws NotValidMap {
            //TODO: Implement check not duplicated ids
            checkMapConnections();
            return new GameMap(this);
        }

        public void checkMapConnections() {
            var connections = squares.stream().map(Square::getConnections).flatMap(List::stream).toList();

            squares.stream().map(Square::getId)
                    .forEach(squareId -> {
                        if (!connections.contains(squareId)) {
                            throw new NotValidMap();
                        }
                    });

        }

        public GameMapBuilder withSquare(Square square) {
            squares.add(square);
            return this;
        }

        private final Collection<Square> squares = new ArrayList<>();

    }
}
