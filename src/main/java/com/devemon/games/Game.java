package com.devemon.games;

import com.devemon.games.domain.ClueLogging;
import com.devemon.games.domain.GameLoader;
import com.devemon.games.domain.commands.*;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class Game implements Runnable {
    @Override
    public void run() {
        messagePublisher.accept("Welcome to hunt the wumpus!");
        var level = gameLoader.load();

        playLevel(level);
    }

    private void playLevel(Map<String, Object> level) {
        while (true) {
            clueLogging.accept(level);

            var command = commandListener.read();

            if (command.getClass() == Exit.class) {
                command.apply(level);
                break;
            }

            if (!ALLOWED_PLAY_COMMANDS.contains(command.getClass())) {
                messagePublisher.accept("Pf.Oak: It is not time to use this!");
                break;
            }

            command.apply(level);
        }
    }

    public Game(MessagePublisher messagePublisher, CommandListener commandListener, GameLoader gameLoader, ClueLogging clueLogging) {
        this.messagePublisher = messagePublisher;
        this.commandListener = commandListener;
        this.gameLoader = gameLoader;
        this.clueLogging = clueLogging;
    }

    private final MessagePublisher messagePublisher;
    private final CommandListener commandListener;
    private final GameLoader gameLoader;
    private final ClueLogging clueLogging;

    private static final List<Class<? extends GameCommand>> ALLOWED_PLAY_COMMANDS = List.of(Unknown.class, Move.class, Shoot.class);
}
