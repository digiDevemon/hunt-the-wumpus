package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;

import java.util.*;

import static com.devemon.games.domain.elements.GameState.FINISHED;
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

        if (user.getAmmunition().equals(0)){
            messagePublisher.accept("Not enough ammunition to shoot");
            return level;
        }

        return shoot(level, reachableSquares, map, user);
    }

    private Map<String, Object> shoot(Map<String, Object> level, List<Square> reachableSquares, GameMap map, User user) {
        var targetSquare = reachableSquares.stream()
                .filter(square -> square.getId().equals(targetSquareId))
                .findFirst()
                .get();

        user.popAmmunition();
        if (targetSquare.getThreat().equals(WUMPUS)) {
            messagePublisher.accept("You hear a roar. You have slide a wumpus.");
            return shootWumpus(level, targetSquare, map);
        }

        moveWumpus(map, user.getPositionId());
        messagePublisher.accept("You failed the shoot. You listen steps somewhere.");
        return level;
    }

    private Map<String, Object> shootWumpus(Map<String, Object> level, Square targetSquare, GameMap map) {
        var newLevel = new HashMap<>(level);

        targetSquare.setThreat(NONE);
        var wumpusSquares = map.getSquares().stream().filter(square -> square.getThreat().equals(WUMPUS)).toList();
        if (wumpusSquares.isEmpty()) {
            messagePublisher.accept("You killed all the wumpus. You WIN!");
            newLevel.put("state", FINISHED);
        }
        return newLevel;
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
