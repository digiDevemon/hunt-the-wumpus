package com.devemon.games;

import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
    @Override
    public void run() {
        messagePublisher.accept("Welcome to hunt the wumpus!");
        commandListener.read();
    }

    public Game(MessagePublisher messagePublisher, CommandListener commandListener) {
        this.messagePublisher = messagePublisher;
        this.commandListener = commandListener;
    }

    private final MessagePublisher messagePublisher;
    private final CommandListener commandListener;
}
