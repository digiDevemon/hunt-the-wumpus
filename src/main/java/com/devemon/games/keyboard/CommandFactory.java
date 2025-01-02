package com.devemon.games.keyboard;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.keyboard.commandAssemblers.*;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public void showAvailableCommands() {
        var commands = commandFactories.stream()
                .map(CommandAssembler::getAvailableInputExample)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.joining(" / "));

        this.messagePublisher.accept("Possible command inputs: ".concat(commands));
    }

    public CommandFactory(MoveAssembler moveFactory, ShootAssembler shootFactory, ExitAssembler exitFactory, UnknownAssembler unknownAssembler, MessagePublisher messagePublisher) {
        commandFactories = List.of(
                moveFactory,
                shootFactory,
                exitFactory,
                unknownAssembler
        );
        this.messagePublisher = messagePublisher;
    }

    private final List<CommandAssembler> commandFactories;
    private final MessagePublisher messagePublisher;
}
