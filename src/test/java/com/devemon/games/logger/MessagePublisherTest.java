package com.devemon.games.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MessagePublisherTest {


    @Test
    public void it_should_log_the_event() {
        messagePublisher.accept(MESSAGE);

        verify(logger).info(MESSAGE);
    }

    @BeforeEach
    public void setLogger() {
        messagePublisher.setLogger(logger);
    }

    @Mock
    private Logger logger;

    private final MessagePublisher messagePublisher = new MessagePublisher();

    private static final String MESSAGE = "just a message";
}