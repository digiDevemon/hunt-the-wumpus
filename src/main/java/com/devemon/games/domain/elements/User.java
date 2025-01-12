package com.devemon.games.domain.elements;

public class User {
    public Integer getPositionId() {
        return squareId;
    }

    public void setPositionId(Integer newPositionId) {
        this.squareId = newPositionId;
    }

    public User(Integer squareId) {

        this.squareId = squareId;
    }

    private Integer squareId;
}
