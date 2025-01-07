package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap.GameMapBuilder;
import com.devemon.games.domain.elements.SquareState;
import com.devemon.games.domain.elements.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.Square.SquareBuilder;

@Component
public class GameLoader {

    public Map<String, Object> load() {

        return Map.of("user", new User(), "gameMap", new GameMapBuilder()
                .withSquare(
                        new SquareBuilder(1).connectedTo(List.of(2, 3)).withState(SquareState.BATS).build()
                )
                .withSquare(
                        new SquareBuilder(2).connectedTo(List.of(1)).withState(SquareState.HOLE).build()
                )
                .withSquare(
                        new SquareBuilder(3).connectedTo(List.of(1)).withState(SquareState.WUMPUS).build()
                )
                .build());
    }

}
