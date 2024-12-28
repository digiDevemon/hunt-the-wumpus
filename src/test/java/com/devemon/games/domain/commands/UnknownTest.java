package com.devemon.games.domain.commands;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UnknownTest {

    @Test
    public void it_should_notify_that_is_executing_an_unknown_command() {
        unknown.apply(gameMap, user);
        verify(messagePublisher).accept(UNKNOWN_PRONTO);
    }

    @Mock
    private GameMap gameMap;

    @Mock
    private User user;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private Unknown unknown;

    private static final String UNKNOWN_PRONTO = "Sorry, command not found";
}