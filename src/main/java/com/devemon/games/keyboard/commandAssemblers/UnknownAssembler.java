package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.domain.commands.Unknown;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UnknownAssembler implements CommandAssembler {

    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        return Optional.of(new Unknown(
                messagePublisher
        ));
    }

    @Override
    public Optional<String> getAvailableInputExample() {
        return Optional.empty();
    }


    public UnknownAssembler(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;
}
