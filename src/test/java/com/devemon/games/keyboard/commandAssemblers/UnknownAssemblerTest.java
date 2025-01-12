package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.Unknown;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UnknownAssemblerTest {


    @Test
    public void it_should_not_return_null() {
        assertThat(unknownAssembler.apply(INPUT_STRING))
                .as("It should not return none")
                .isNotNull();
    }

    @Test
    public void it_should_return_optional_unknown_command() {
        assertThat(unknownAssembler.apply(INPUT_STRING))
                .as("It should return optional of unknown")
                .isPresent()
                .get()
                .isInstanceOf(Unknown.class);
    }


    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private UnknownAssembler unknownAssembler;


    private static final String INPUT_STRING = "any";
}