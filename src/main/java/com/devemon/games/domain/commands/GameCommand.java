package com.devemon.games.domain.commands;

import java.util.Map;

public interface GameCommand {

    GameState apply(Map<String, Object> level);
}
