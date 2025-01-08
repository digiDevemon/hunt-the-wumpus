package com.devemon.games.domain.elements;

public class User {
    public Integer getPositionID() {
        return squareID;
    }

    public User(Integer squareID) {

        this.squareID = squareID;
    }

    private final Integer squareID;
}
