package com.devemon.games.domain;

public class Unknown implements GameCommand {
    @Override
    public GameCommandResult apply() {
        return null;
    }
}
