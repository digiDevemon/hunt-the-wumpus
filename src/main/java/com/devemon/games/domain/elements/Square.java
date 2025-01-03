package com.devemon.games.domain.elements;

import static com.devemon.games.domain.elements.SquareState.HOLE;

public class Square {

    public SquareState getThreat() {
        return HOLE;
    }
}
