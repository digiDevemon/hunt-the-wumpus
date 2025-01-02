package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MoveAssembler implements CommandAssembler {

    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        return Optional.empty();
    }

    @Override
    public Optional<String> getAvailableInputExample() {
        return Optional.of(INPUT_EXAMPLE);
    }

    public MoveAssembler(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;
    private static final String INPUT_EXAMPLE = "M<Number>";
}
