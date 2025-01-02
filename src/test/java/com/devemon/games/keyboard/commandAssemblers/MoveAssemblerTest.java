package com.devemon.games.keyboard.commandAssemblers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class MoveAssemblerTest {

    @Test
    public void it_should_not_return_null_retrieving_input_example() {

        assertThat(moveAssembler.getAvailableInputExample())
                .as("It should return some avaiable input")
                .isNotNull();
    }


    @Test
    public void it_should_return_the_expected_optional_input_command() {

        assertThat(moveAssembler.getAvailableInputExample())
                .as("It should return some avaiable input")
                .isEqualTo(Optional.of(INPUT_EXAMPLE));
    }


    @InjectMocks
    private MoveAssembler moveAssembler;

    private static final String INPUT_EXAMPLE = "M<Number>";
}