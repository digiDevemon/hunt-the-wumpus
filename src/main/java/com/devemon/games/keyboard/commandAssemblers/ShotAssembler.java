package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.GameCommand;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ShotAssembler implements CommandAssembler {
    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        return Optional.empty();
    }
}
