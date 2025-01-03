package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.function.Consumer;

@Component
public class ClueLogging implements Consumer<Map<String, Object>> {
    @Override
    public void accept(Map<String, Object> level) {
        var user = (User) level.get("user");
        var map = (GameMap) level.get("gameMap");
        var clueSquares = map.getConnectedSquares(user.getPositionID());

    }

    public ClueLogging(MessagePublisher messagePublisher){
        this.messagePublisher = messagePublisher;
    }

    private MessagePublisher messagePublisher;
}
