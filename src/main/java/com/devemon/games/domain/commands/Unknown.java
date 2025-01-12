package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Unknown implements GameCommand {

    @Override
    public Map<String, Object> apply(Map<String, Object> level) {
        messagePublisher.accept(UNKNOWN_PRONTO);
        return level;
    }

    public Unknown(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

    private static final String UNKNOWN_PRONTO = "Sorry, command not found";

}
