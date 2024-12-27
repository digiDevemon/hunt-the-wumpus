package com.devemon.games.domain.commands;

import com.devemon.games.domain.GameMap;
import com.devemon.games.domain.User;

public interface GameCommand {

    void apply(GameMap map, User user);
}
