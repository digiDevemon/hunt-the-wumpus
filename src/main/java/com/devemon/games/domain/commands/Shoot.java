package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static com.devemon.games.domain.elements.SquareState.NONE;
import static com.devemon.games.domain.elements.SquareState.WUMPUS;

public class Shoot implements GameCommand {

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

        return shoot(level, reachableSquares, map, user.getPositionId());
    }

    private Map<String, Object> shoot(Map<String, Object> level, List<Square> reachableSquares, GameMap map, Integer userPositionId) {
        var targetSquare = reachableSquares.stream()
                .filter(square -> square.getId().equals(targetSquareId))
                .findFirst()
                .get();

        if (targetSquare.getThreat().equals(WUMPUS)) {
            return level;
        }

        moveWumpus(map, userPositionId);
        messagePublisher.accept("You failed the shoot. You listen steps somewhere.");
        return level;
    }

    private void moveWumpus(GameMap map, Integer userPositionId) {
        var noneSquares = new ArrayList<>(map.getSquares().stream()
                .filter(square -> square.getThreat().equals(NONE) && !square.getId().equals(userPositionId))
                .toList());
        var random = new Random();

        map.getSquares().stream()
                .filter(square -> square.getThreat().equals(WUMPUS))
                .forEach(wumpusSaquare -> {
                    var randomSquare = noneSquares.get(random.nextInt(noneSquares.size()));
                    randomSquare.setThreat(WUMPUS);
                    wumpusSaquare.setThreat(NONE);
                    noneSquares.remove(randomSquare);
                });
    }

    public Shoot(MessagePublisher messagePublisher, Integer targetSquareId) {
        this.messagePublisher = messagePublisher;
        this.targetSquareId = targetSquareId;
    }

    private final MessagePublisher messagePublisher;

    private final Integer targetSquareId;
}
