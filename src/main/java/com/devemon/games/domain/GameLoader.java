package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.GameMap.GameMapBuilder;
import com.devemon.games.domain.elements.User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class GameLoader {

    public Map<String, Object> load() {

        return Map.of("user", new User(), "gameMap", new GameMapBuilder().build());
    }

}
