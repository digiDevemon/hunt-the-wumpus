package com.devemon.games.keyboard;

import com.devemon.games.domain.GameCommand;
import com.devemon.games.domain.Unknown;
import com.devemon.games.keyboard.commandAssemblers.CommandAssembler;
import com.devemon.games.keyboard.commandAssemblers.ExitAssembler;
import com.devemon.games.keyboard.commandAssemblers.MoveAssembler;
import com.devemon.games.keyboard.commandAssemblers.ShotAssembler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CommandsFactory implements Function<String, GameCommand> {

    @Override
    public GameCommand apply(String keyboardInput) {
        return commandFactories.stream()
                .map(commandFactory -> commandFactory.apply(keyboardInput))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElse(new Unknown());
    }

    public CommandsFactory(MoveAssembler moveFactory, ShotAssembler shotFactory, ExitAssembler exitFactory) {
        commandFactories = List.of(
                moveFactory,
                shotFactory,
                exitFactory
        );
    }

    private final List<CommandAssembler> commandFactories;
}
