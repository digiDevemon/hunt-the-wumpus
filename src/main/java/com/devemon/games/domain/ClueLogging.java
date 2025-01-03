package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.GameSquare;
import com.devemon.games.domain.elements.SquareStates;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static com.devemon.games.domain.elements.SquareStates.*;

@Component
public class ClueLogging implements Consumer<Map<String, Object>> {
    @Override
    public void accept(Map<String, Object> level) {
        var user = (User) level.get("user");
        var map = (GameMap) level.get("gameMap");
        var clueSquares = map.getConnectedSquares(user.getPositionID()).stream()
                .map(GameSquare::getThreat)
                .map(CLUE_MAPPING::get)
                .collect(Collectors.toList());
        clueSquares.add(0, INTRODUCTION_MESSAGE);
        messagePublisher.accept(String.join("\n", clueSquares));
    }

    public ClueLogging(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private MessagePublisher messagePublisher;

    private static final String INTRODUCTION_MESSAGE = "You have the next feelings:";
    private static final Map<SquareStates, String> CLUE_MAPPING = Map.of(
            BATS, "-You hear nearby flapping.",
            HOLE, "-A gust of cold air comes from somewhere.",
            WUMPUS, "-A foul smell comes from somewhere."
    );
}
