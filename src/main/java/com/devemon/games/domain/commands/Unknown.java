package com.devemon.games.domain.commands;

import com.devemon.games.domain.GameMap;
import com.devemon.games.domain.User;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

@Component
public class Unknown implements GameCommand {

    @Override
    public void apply(GameMap map, User user) {
        messagePublisher.accept(UNKNOWN_PRONTO);
    }

    public Unknown(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

    private static final String UNKNOWN_PRONTO = "Sorry, command not found";

}
