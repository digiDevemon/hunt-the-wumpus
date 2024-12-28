package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;

public interface GameCommand {

    void apply(GameMap map, User user);
}
