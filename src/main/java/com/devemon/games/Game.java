package com.devemon.games;

import com.devemon.games.domain.GameMap;
import com.devemon.games.domain.User;
import com.devemon.games.keyboard.CommandFactory;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

@Component
public class Game implements Runnable {
    @Override
    public void run() {
        messagePublisher.accept("Welcome to hunt the wumpus!");
        var command = commandListener.read();
        command.apply(gameMap, user);
    }

    public Game(MessagePublisher messagePublisher, CommandListener commandListener, CommandFactory commandFactory) {
        this.messagePublisher = messagePublisher;
        this.commandListener = commandListener;

    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private final MessagePublisher messagePublisher;
    private final CommandListener commandListener;

    private GameMap gameMap = new GameMap();
    private User user = new User();

}
