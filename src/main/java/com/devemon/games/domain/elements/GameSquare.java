package com.devemon.games.domain.elements;

import static com.devemon.games.domain.elements.SquareStates.HOLE;

public class GameSquare {

    public SquareStates getThreat() {
        return HOLE;
    }
}
