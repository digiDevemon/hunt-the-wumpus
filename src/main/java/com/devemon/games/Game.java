package com.devemon.games;

import com.devemon.games.domain.GameLoader;
import com.devemon.games.domain.commands.*;
import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Game implements Runnable {
    @Override
    public void run() {
        messagePublisher.accept("Welcome to hunt the wumpus!");
        var pairGame = gameLoader.load();
        var user = pairGame.getRight();
        var gameMap = pairGame.getLeft();

        playLevel(gameMap, user);
    }

    private void playLevel(GameMap gameMap, User user) {
        while (true) {
            var command = commandListener.read();

            if (command.getClass() == Exit.class) {
                command.apply(gameMap, user);
                break;
            }

            if (!ALLOWED_PLAY_COMMANDS.contains(command.getClass())) {
                messagePublisher.accept("Pf.Oak: It is not time to use this!");
                break;
            }

            command.apply(gameMap, user);
        }
    }

    public Game(MessagePublisher messagePublisher, CommandListener commandListener, GameLoader gameLoader) {
        this.messagePublisher = messagePublisher;
        this.commandListener = commandListener;
        this.gameLoader = gameLoader;
    }

    private final MessagePublisher messagePublisher;
    private final CommandListener commandListener;
    private final GameLoader gameLoader;

    private static final List<Class<? extends GameCommand>> ALLOWED_PLAY_COMMANDS = List.of(Unknown.class, Move.class, Shot.class);
}
