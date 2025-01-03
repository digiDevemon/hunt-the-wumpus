package com.devemon.games.domain.elements;

import java.util.Collection;
import java.util.List;

public class GameMap {
    public Collection<GameSquare> getConnectedSquares(Integer userPositionId) {
        return List.of(new GameSquare());
    }
}
