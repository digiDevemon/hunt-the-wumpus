package com.devemon.games.keyboard;

import com.devemon.games.domain.GameCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandListenerTest {

    @Test
    public void it_should_not_return_null() {
        setupCommandListener();

        assertThat(commandListener.read())
                .as("It should not return null")
                .isNotNull();
    }

    @Test
    public void it_should_read_a_command_from_input() {
        setupCommandListener();
        commandListener.read();
        verify(scanner).nextLine();
    }

    @Test
    public void it_should_return_a_game_command() {
        setupCommandListener();
        assertThat(commandListener.read())
                .as("It should return a command")
                .isEqualTo(fooCommand);
    }

    public void setupCommandListener() {
        this.commandListener = new CommandListener(commandFactory);
        this.commandListener.setScanner(scanner);
    }

    @BeforeEach
    public void setupScanner() {
        when(scanner.nextLine()).thenReturn(INPUT_COMMAND);
    }

    @BeforeEach
    public void setupCommandFactory() {
        when(commandFactory.apply(INPUT_COMMAND)).thenReturn(fooCommand);
    }

    @Mock
    private Scanner scanner;

    @Mock
    private CommandFactory commandFactory;

    @Mock
    private GameCommand fooCommand;

    private CommandListener commandListener;

    private static final String INPUT_COMMAND = "Just an input command";
}