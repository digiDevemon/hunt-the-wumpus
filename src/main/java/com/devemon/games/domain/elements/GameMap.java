package com.devemon.games.domain.elements;

import com.devemon.games.domain.elements.Square.SquareBuilder;

import java.util.Collection;
import java.util.List;

public class GameMap {
    public GameMap(GameMapBuilder gameMapBuilder) {
        this.squares = gameMapBuilder.squares;
    }

    private Collection<Square> squares;

    public Collection<Square> getConnectedSquares(Integer userPositionId) {
        return List.of(new SquareBuilder(1).build());
    }

    public static class GameMapBuilder {


        public GameMap build() {
            return new GameMap(this);
        }

        private Collection<Square> squares;
    }
}
