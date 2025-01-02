package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.Exit;
import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.domain.commands.Move;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ExitAssembler implements CommandAssembler {
    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        var pattern = Pattern.compile(ACCEPTED_PATTERN);
        var matcher = pattern.matcher(keyboardInput);

        if (!matcher.matches()) {
            return Optional.empty();
        }

        return Optional.of(new Exit(messagePublisher));
    }

    @Override
    public Optional<String> getAvailableInputExample() {
        return Optional.of(INPUT_EXAMPLE);
    }


    public ExitAssembler(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;
    private static final String INPUT_EXAMPLE = "Exit";
    private static final String ACCEPTED_PATTERN = "((?i)^exit)?";

}
