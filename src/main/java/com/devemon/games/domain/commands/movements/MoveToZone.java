package com.devemon.games.domain.commands.movements;

import java.util.Map;

public interface MoveToZone {

    Boolean fullfill(Map<String, Object> level, Integer targetId);

    Map<String, Object> move(Map<String, Object> level, Integer targetId);
}
