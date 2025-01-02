package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.Square;
import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

public class Move implements GameCommand {

    @Override
    public void apply(Map<String, Object> level) {

    }

    public Move(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

    private Square square;
}
