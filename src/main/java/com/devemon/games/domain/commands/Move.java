package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

public class Move implements GameCommand {

    @Override
    public void apply(Map<String, Object> level) {
        System.out.println("Moved to " + this.squareID);
    }

    public Move(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.squareID = square;
    }

    private final MessagePublisher messagePublisher;

    private Integer squareID;
}
