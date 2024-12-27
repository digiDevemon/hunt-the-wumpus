package com.devemon.games.logging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import static org.mockito.Mockito.description;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class MessagePublisherTest {


    @Test
    public void it_should_log_the_event() {
        messagePublisher.accept(MESSAGE);

        verify(logger, description("It should log the event"))
                .info(MESSAGE);
    }


    @Mock
    private Logger logger;

    @InjectMocks
    private MessagePublisher messagePublisher;

    private static final String MESSAGE = "just a message";
}