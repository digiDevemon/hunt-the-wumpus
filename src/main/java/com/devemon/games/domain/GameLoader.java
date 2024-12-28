package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;

@Component
public class GameLoader {

    public Pair<GameMap, User> load() {

        return Pair.of(new GameMap(), new User());
    }

}
