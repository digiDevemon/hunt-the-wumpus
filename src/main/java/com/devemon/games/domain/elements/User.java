package com.devemon.games.domain.elements;

public class User {
    public Integer getPositionId() {
        return squareId;
    }

    public void setPositionId(Integer newPositionId) {
        this.squareId = newPositionId;
    }

    public void popAmmunition(){
        ammunition = ammunition-1;
    }

    public Integer getAmmunition() {
        return ammunition;
    }

    public User(Integer squareId) {
        this.squareId = squareId;
    }

    public User(Integer squareId, Integer ammunition) {
        this.squareId = squareId;
        this.ammunition = ammunition;
    }

    private Integer squareId;

    private Integer ammunition = 3;

}
