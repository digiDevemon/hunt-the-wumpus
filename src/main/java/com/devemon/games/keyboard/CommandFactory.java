package com.devemon.games.keyboard;

import com.devemon.games.domain.GameCommand;
import com.devemon.games.domain.Move;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CommandFactory implements Function<String, GameCommand> {

    @Override
    public GameCommand apply(String s) {
        return new Move();
    }
}
