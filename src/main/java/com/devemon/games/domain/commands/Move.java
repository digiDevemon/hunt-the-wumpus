package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

public class Move implements GameCommand {

    @Override
    public Map<String, Object> apply(Map<String, Object> level) {
        var user = (User) level.get("user");
        user.setPositionId(targetSquareId);
        return level;
    }

    public Move(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.targetSquareId = square;
    }

    private final MessagePublisher messagePublisher;

    private final Integer targetSquareId;
}
