package com.devemon.games.domain.elements;

public class User {
    public Integer getPositionId() {
        return squareId;
    }

    public User(Integer squareId) {

        this.squareId = squareId;
    }

    private final Integer squareId;
}
