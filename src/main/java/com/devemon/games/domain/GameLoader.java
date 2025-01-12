package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.GameMap.GameMapBuilder;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.SquareState;
import com.devemon.games.domain.elements.User;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.devemon.games.domain.elements.Square.SquareBuilder;
import static com.devemon.games.domain.elements.SquareState.*;

@Component
public class GameLoader {

    public Map<String, Object> load() {
        var gameMap = buildMap();
        var user = buildUser(gameMap);
        return Map.of("user", user, "gameMap", gameMap);
    }

    private GameMap buildMap() {
        var mapBuilder = new GameMapBuilder();
        var holeSquares = getFreeSquaresId(NUMBER_OF_HOLES, List.of(), List.of());
        var batsSquares = getFreeSquaresId(NUMBER_OF_BATS, holeSquares, List.of());
        var wumpusSquares = getFreeSquaresId(NUMBER_OF_WUMPUS, holeSquares, batsSquares);

        MAP_STRUCTURE.keySet().forEach(key -> mapBuilder.withSquare(
                        new SquareBuilder(key)
                                .connectedTo(MAP_STRUCTURE.get(key))
                                .withState(getStateFromIdList(key, holeSquares, batsSquares, wumpusSquares))
                                .build()
                )
        );

        return mapBuilder.build();
    }

    private User buildUser(GameMap gameMap) {
        var availableSquares = gameMap.getSquares().stream().filter(square -> square.getThreat().equals(NONE)).map(Square::getId).toList();
        var random = new Random();
        var randomPosition = availableSquares.get(random.nextInt(availableSquares.size()));

        return new User(randomPosition);
    }

    private SquareState getStateFromIdList(Integer id, Collection<Integer> holeIds, Collection<Integer> batIds, Collection<Integer> wumpusIds) {
        if (holeIds.contains(id)) {
            return HOLE;
        }

        if (batIds.contains(id)) {
            return BATS;
        }

        if (wumpusIds.contains(id)) {
            return WUMPUS;
        }

        return NONE;
    }

    private List<Integer> getFreeSquaresId(Integer size, Collection<Integer> firstList, Collection<Integer> secondList) {
        var squareIds = new ArrayList<Integer>(List.of());
        var random = new Random();
        var availableSquares = MAP_STRUCTURE.keySet().stream().toList();

        while (squareIds.size() < size) {
            var randomElement = availableSquares.get(random.nextInt(availableSquares.size()));
            if (firstList.contains(randomElement) || secondList.contains(randomElement) || squareIds.contains(randomElement)) {
                continue;
            }
            squareIds.add(randomElement);
        }
        return squareIds;
    }

    private static final Map<Integer, List<Integer>> MAP_STRUCTURE = Map.ofEntries(
            Map.entry(1, List.of(2, 5, 8)),
            Map.entry(2, List.of(3, 1, 10)),
            Map.entry(3, List.of(2, 4, 12)),
            Map.entry(4, List.of(3, 5, 14)),
            Map.entry(5, List.of(4, 1, 6)),
            Map.entry(6, List.of(7, 15, 5)),
            Map.entry(7, List.of(8, 6, 17)),
            Map.entry(8, List.of(9, 1, 7)),
            Map.entry(9, List.of(10, 8, 18)),
            Map.entry(10, List.of(11, 2, 9)),
            Map.entry(11, List.of(19, 12, 10)),
            Map.entry(12, List.of(11, 3, 13)),
            Map.entry(13, List.of(12, 14, 20)),
            Map.entry(14, List.of(13, 15, 4)),
            Map.entry(15, List.of(14, 6, 16)),
            Map.entry(16, List.of(20, 17, 15)),
            Map.entry(17, List.of(7, 18, 16)),
            Map.entry(18, List.of(9, 17, 19)),
            Map.entry(19, List.of(20, 11, 18)),
            Map.entry(20, List.of(19, 16, 13))
    );


    private static final Integer NUMBER_OF_BATS = 2;
    private static final Integer NUMBER_OF_WUMPUS = 1;
    private static final Integer NUMBER_OF_HOLES = 2;
}
