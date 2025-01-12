package com.devemon.games;

import com.devemon.games.domain.GameLoader;
import com.devemon.games.keyboard.CommandListener;
import com.devemon.games.logging.ClueLogging;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.devemon.games.domain.commands.GameState.PLAYING;

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
            clueLogging.logFeelingsClue(level);
            clueLogging.logNearRooms(level);

            var command = commandListener.read();

            if (command.apply(level) != PLAYING) {
                break;
            }
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
}
