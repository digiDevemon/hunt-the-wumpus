package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.SquareState;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;

import java.util.*;

import static com.devemon.games.domain.elements.GameState.FINISHED;
import static com.devemon.games.domain.elements.SquareState.*;

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
            newLevel.put("state", FINISHED);
            messagePublisher.accept("You dead");
            return newLevel;
        }

        if (TELEPORT_SQUARES.contains(targetSquare.getThreat())) {
            user.setPositionId(getOtherNotDangerousZone((GameMap) level.get("gameMap"), user.getPositionId()));
            messagePublisher.accept("You run away escaping from bats");
            return newLevel;
        }

        user.setPositionId(targetSquareId);
        return level;
    }

    private Integer getOtherNotDangerousZone(GameMap gameMap, Integer userPositionId) {
        var targetIds = gameMap.getSquares().stream()
                .filter(square -> square.getThreat().equals(NONE))
                .map(Square::getId)
                .filter(squareId -> !squareId.equals(userPositionId))
                .toList();

        if (targetIds.isEmpty()) {
            return userPositionId;
        }

        var random = new Random();
        return targetIds.get(random.nextInt(targetIds.size()));
    }

    public Move(MessagePublisher messagePublisher, Integer square) {
        this.messagePublisher = messagePublisher;
        this.targetSquareId = square;
    }

    private final MessagePublisher messagePublisher;

    private final Integer targetSquareId;

    private static final Collection<SquareState> DANGEROUS_SQUARES = List.of(WUMPUS, HOLE);
    private static final Collection<SquareState> TELEPORT_SQUARES = List.of(BATS);

}
