package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.GameCommand;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MoveAssembler implements CommandAssembler {
    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        return Optional.empty();
    }
}
