package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

public class Exit implements GameCommand {

    @Override
    public void apply(Map<String, Object> level) {
        messagePublisher.accept("Exit game...");
    }

    public Exit(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

}
