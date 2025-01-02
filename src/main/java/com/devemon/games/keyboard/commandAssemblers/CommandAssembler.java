package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.GameCommand;

import java.util.Optional;
import java.util.function.Function;

public interface CommandAssembler extends Function<String, Optional<GameCommand>> {

    @Override
    Optional<GameCommand> apply(String keyboardInput);

    Optional<String> getAvailableInputExample();
}
