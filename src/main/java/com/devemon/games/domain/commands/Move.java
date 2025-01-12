package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.PLAYING;

public class Move implements GameCommand {

    @Override
    public GameState apply(Map<String, Object> level) {
        System.out.println("Moved to " + this.squareId);
        return PLAYING;
    }

    public Move(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.squareId = square;
    }

    private final MessagePublisher messagePublisher;

    private Integer squareId;
}
