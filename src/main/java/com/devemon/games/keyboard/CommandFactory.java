package com.devemon.games.keyboard;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.keyboard.commandAssemblers.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Component
public class CommandFactory implements Function<String, GameCommand> {

    @Override
    public GameCommand apply(String keyboardInput) {
        return commandFactories.stream()
                .map(commandFactory -> commandFactory.apply(keyboardInput))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .get();
    }

    public CommandFactory(MoveAssembler moveFactory, ShotAssembler shotFactory, ExitAssembler exitFactory, UnknownAssembler unknownAssembler) {
        commandFactories = List.of(
                moveFactory,
                shotFactory,
                exitFactory,
                unknownAssembler
        );
    }

    private final List<CommandAssembler> commandFactories;
}
