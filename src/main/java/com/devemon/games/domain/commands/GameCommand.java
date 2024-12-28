package com.devemon.games.domain.commands;

import java.util.Map;

public interface GameCommand {

    void apply(Map<String, Object> level);
}
