package com.devemon.games.domain.commands.movements;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.SquareState;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.GameState.FINISHED;
import static com.devemon.games.domain.elements.SquareState.HOLE;
import static com.devemon.games.domain.elements.SquareState.WUMPUS;

public class Move implements GameCommand {

    @Override
    public Map<String, Object> apply(Map<String, Object> level) {
        var user = (User) level.get("user");
        var map = (GameMap) level.get("gameMap");

        var reachableSquares = map.getConnectedSquares(user.getPositionId()).stream().toList();
        var reachableIds = reachableSquares.stream().map(Square::getId).toList();

        if (!reachableIds.contains(targetSquareId)) {
            messagePublisher.accept("This room is not reachable from your current position");
            return level;
        }

        return move(level, reachableSquares, user);
    }

    private Map<String, Object> move(Map<String, Object> level, List<Square> reachableSquares, User user) {
        var newLevel = new HashMap<>(level);
        var targetSquare = reachableSquares.stream().filter(square -> square.getId().equals(targetSquareId)).findFirst().get();

        if (DANGEROUS_SQUARES.contains(targetSquare.getThreat())) {
            System.out.println("te han comido");
            newLevel.put("state", FINISHED);
            return newLevel;
        }

        user.setPositionId(targetSquareId);
        return level;
    }

    public Move(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.targetSquareId = square;
    }

    private final MessagePublisher messagePublisher;

    private final Integer targetSquareId;

    private static final Collection<SquareState> DANGEROUS_SQUARES = List.of(WUMPUS, HOLE);
}
