package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.FINISHED;

public class Exit implements GameCommand {

    @Override
    public GameState apply(Map<String, Object> level) {
        messagePublisher.accept("Exit game...");
        return FINISHED;
    }

    public Exit(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

}
