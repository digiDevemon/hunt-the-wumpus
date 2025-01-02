package com.devemon.games.keyboard.commandAssemblers;

import com.devemon.games.domain.commands.Move;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
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

    @ParameterizedTest
    @MethodSource("acceptedCommands")
    public void it_should_return_move_command_from_accepted_commands(String inputCommand) {
        assertThat(moveAssembler.apply(inputCommand))
                .as("It should return the ")
                .isPresent()
                .get()
                .isInstanceOf(Move.class);
    }

    @Test
    public void it_should_return_optional_empty_from_not_accepted_command() {
        assertThat(moveAssembler.apply(NOT_ACCEPTED_COMMAND))
                .as("It should return empty command")
                .isEmpty();
    }


    private static List<String> acceptedCommands() {
        return List.of(
                ACCEPTED_COMMAND,
                LOWER_ACCEPTED_COMMAND
        );
    }

    @InjectMocks
    private MoveAssembler moveAssembler;

    private static final String INPUT_EXAMPLE = "M<Number>";
    private static final String ACCEPTED_COMMAND = "M7";
    private static final String LOWER_ACCEPTED_COMMAND = "m7";
    private static final String NOT_ACCEPTED_COMMAND = "x9";
}