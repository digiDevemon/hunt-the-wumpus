package com.devemon.games.domain.elements.exceptions;

public class SquareNotFound extends RuntimeException {
    public SquareNotFound(Integer squareId) {
        super("The square with the next: " + squareId);
    }

}

