package com.devemon.games.domain.elements.exceptions;

public class SquareDuplication extends RuntimeException {
    public SquareDuplication(Integer squareId) {
        super("The next square ID is duplicated: " + squareId);
    }

}

