package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

public class Shoot implements GameCommand {

    @Override
    public Map<String, Object> apply(Map<String, Object> level) {
        System.out.println("Shot to " + this.squareId);
        return level;
    }

    public Shoot(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.squareId = square;
    }

    private final MessagePublisher messagePublisher;

    private Integer squareId;
}
