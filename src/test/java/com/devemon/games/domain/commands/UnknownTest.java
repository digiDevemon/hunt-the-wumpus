package com.devemon.games.domain.commands;

import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnknownTest {

    @Test
    public void it_should_notify_that_is_executing_an_unknown_command() {
        unknown.apply(LEVEL);
        verify(messagePublisher, description("It should notify about not found command")).accept(UNKNOWN_PRONTO);
    }

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private Unknown unknown;

    private static final Map<String, Object> LEVEL = Map.of();
    private static final String UNKNOWN_PRONTO = "Sorry, command not found";
}