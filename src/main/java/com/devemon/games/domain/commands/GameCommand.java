package com.devemon.games.domain.commands;

import java.util.Map;

public interface GameCommand {

    Map<String, Object> apply(Map<String, Object> level);
}
