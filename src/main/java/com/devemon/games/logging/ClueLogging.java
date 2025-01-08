package com.devemon.games.logging;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.SquareState;
import com.devemon.games.domain.elements.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.devemon.games.domain.elements.SquareState.*;

@Component
public class ClueLogging {

    public void logFeelingsClue(Map<String, Object> level) {
        var connectedSquares = getNearSquares(level);

        var clueSquares = connectedSquares.stream()
                .map(Square::getThreat)
                .map(this::findClue)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
        clueSquares.add(0, INTRODUCTION_CLUE_MESSAGE);
        messagePublisher.accept(String.join("\n", clueSquares));
    }

    private Optional<String> findClue(SquareState squareState) {
        if (CLUE_MAPPING.containsKey(squareState)) {
            return Optional.of(CLUE_MAPPING.get(squareState));
        }
        return Optional.empty();
    }

    public void logNearRooms(Map<String, Object> level) {
        var connectedSquares = getNearSquares(level);

        var clueSquares = connectedSquares.stream()
                .map(Square::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());
        var nearRoomsMessage = String.join(",", clueSquares);
        var localRoomMessage = "Current room: ".concat(String.valueOf(((User) level.get("user")).getPositionID()));
        messagePublisher.accept(String.join(" ", localRoomMessage, INTRODUCTION_ROOMS_MESSAGE, nearRoomsMessage));
    }

    private Collection<Square> getNearSquares(Map<String, Object> level) {
        var user = (User) level.get("user");
        var map = (GameMap) level.get("gameMap");
        return map.getConnectedSquares(user.getPositionID());
    }

    public ClueLogging(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private MessagePublisher messagePublisher;

    private static final String INTRODUCTION_CLUE_MESSAGE = "You have the next feelings:";
    private static final String INTRODUCTION_ROOMS_MESSAGE = "Near rooms:";

    private static final Map<SquareState, String> CLUE_MAPPING = Map.of(
            BATS, "-You hear nearby flapping.",
            HOLE, "-A gust of cold air comes from somewhere.",
            WUMPUS, "-A foul smell comes from somewhere."
    );
}
