package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.GameCommand;
import com.devemon.games.domain.commands.Shoot;
import com.devemon.games.logging.MessagePublisher;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Pattern;

@Component
public class ShootAssembler implements CommandAssembler {

    @Override
    public Optional<GameCommand> apply(String keyboardInput) {
        var pattern = Pattern.compile(ACCEPTED_PATTERN);
        var matcher = pattern.matcher(keyboardInput);

        if (!matcher.matches() || keyboardInput.isBlank()) {
            return Optional.empty();
        }

        return Optional.of(new Shoot(messagePublisher, Integer.valueOf(matcher.group(3))));
    }

    @Override
    public Optional<String> getAvailableInputExample() {
        return Optional.of(INPUT_EXAMPLE);
    }

    public ShootAssembler(MessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    private final MessagePublisher messagePublisher;
    private static final String INPUT_EXAMPLE = "S<Number>";
    private static final String ACCEPTED_PATTERN = "((^[sS])(\\d{1,2}))?";


}
