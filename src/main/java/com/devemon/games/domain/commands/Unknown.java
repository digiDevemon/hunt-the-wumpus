package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.PLAYING;

@Component
public class Unknown implements GameCommand {

    @Override
    public GameState apply(Map<String, Object> level) {
        messagePublisher.accept(UNKNOWN_PRONTO);
        return PLAYING;
    }

    public Unknown(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;

    private static final String UNKNOWN_PRONTO = "Sorry, command not found";

}
