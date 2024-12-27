package com.devemon.games.keyboard;

import com.devemon.games.domain.commands.*;
import com.devemon.games.keyboard.commandAssemblers.ExitAssembler;
import com.devemon.games.keyboard.commandAssemblers.MoveAssembler;
import com.devemon.games.keyboard.commandAssemblers.ShotAssembler;
import com.devemon.games.keyboard.commandAssemblers.UnknownAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class CommandFactoryTest {

    @Test
    public void it_should_not_return_null() {
        assertThat(commandAssembler.apply(MOVE_INPUT_ACTION))
                .as("It should not return null")
                .isInstanceOf(GameCommand.class);
    }


    @Test
    public void it_should_return_a_move_action() {
        assertThat(commandAssembler.apply(MOVE_INPUT_ACTION))
                .as("It should return move action")
                .isInstanceOf(Move.class);
    }

    @Test
    public void it_should_return_a_shot_action() {
        assertThat(commandAssembler.apply(SHOT_INPUT_ACTION))
                .as("It should return shot action")
                .isInstanceOf(Shot.class);
    }

    @Test
    public void it_should_return_a_exit_action() {
        assertThat(commandAssembler.apply(EXIT_INPUT_ACTION))
                .as("It should return exit action")
                .isInstanceOf(Exit.class);
    }

    @Test
    public void it_should_return_unknown_action_as_default_case() {
        assertThat(commandAssembler.apply(UNKNOWN_ACTION))
                .as("It should return unknown action")
                .isInstanceOf(Unknown.class);
    }

    @BeforeEach
    public void setupMoveFactory() {
        lenient().when(moveFactory.apply(MOVE_INPUT_ACTION)).thenReturn(Optional.of(moveAction));
    }

    @BeforeEach
    public void setupShotFactory() {
        lenient().when(shotAssembler.apply(SHOT_INPUT_ACTION)).thenReturn(Optional.of(shotAction));
    }

    @BeforeEach
    public void setupExitFactory() {
        lenient().when(exitAssembler.apply(EXIT_INPUT_ACTION)).thenReturn(Optional.of(exitAction));
    }

    @BeforeEach
    public void setupUnknownFactory() {
        lenient().when(unknownAssembler.apply(any())).thenReturn(Optional.of(unknown));
    }

    @Mock
    private Move moveAction;
    @Mock
    private MoveAssembler moveFactory;

    @Mock
    private Shot shotAction;
    @Mock
    private ShotAssembler shotAssembler;

    @Mock
    private Exit exitAction;
    @Mock
    private ExitAssembler exitAssembler;

    @Mock
    private Unknown unknown;
    @Mock
    private UnknownAssembler unknownAssembler;

    @InjectMocks
    private CommandFactory commandAssembler;

    private static final String MOVE_INPUT_ACTION = "move keyboard action";
    private static final String SHOT_INPUT_ACTION = "shot keyboard action";
    private static final String EXIT_INPUT_ACTION = "exit action";
    private static final String UNKNOWN_ACTION = "unknown action";
}