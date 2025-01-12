package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameState;
import com.devemon.games.logging.MessagePublisher;

import java.util.HashMap;
import java.util.Map;

public class Exit implements GameCommand {

    @Override
    public Map<String, Object> apply(Map<String, Object> level) {
        messagePublisher.accept("Exit game...");
        var newLevel = new HashMap<>(level);
        newLevel.put("state", GameState.FINISHED);
        return newLevel;
    }

    public Exit(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

}
