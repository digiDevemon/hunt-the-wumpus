package com.devemon.games.keyboard;

import com.devemon.games.domain.*;
import com.devemon.games.keyboard.commandAssemblers.ExitAssembler;
import com.devemon.games.keyboard.commandAssemblers.MoveAssembler;
import com.devemon.games.keyboard.commandAssemblers.ShotAssembler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class CommandFactoryTest {

    @Test
    public void it_should_not_return_null() {
        assertThat(commandFactory.apply(MOVE_INPUT_ACTION))
                .as("It should not return null")
                .isInstanceOf(GameCommand.class);
    }


    @Test
    public void it_should_return_a_move_action() {
        assertThat(commandFactory.apply(MOVE_INPUT_ACTION))
                .as("It should not return null")
                .isInstanceOf(Move.class);
    }

    @Test
    public void it_should_return_a_shot_action() {
        assertThat(commandFactory.apply(SHOT_INPUT_ACTION))
                .as("It should not return null")
                .isInstanceOf(Shot.class);
    }

    @Test
    public void it_should_return_a_exit_action() {
        assertThat(commandFactory.apply(EXIT_INPUT_ACTION))
                .as("It should not return null")
                .isInstanceOf(Exit.class);
    }

    @Test
    public void it_should_return_unknown_action_as_default_case() {
        assertThat(commandFactory.apply(UNKNOWN_ACTION))
                .as("It should not return null")
                .isInstanceOf(Unknown.class);
    }

    @BeforeEach
    public void setupMoveFactory() {
        lenient().when(moveFactory.apply(MOVE_INPUT_ACTION)).thenReturn(Optional.of(moveAction));
    }

    @BeforeEach
    public void setupShotFactory() {
        lenient().when(shotFactory.apply(SHOT_INPUT_ACTION)).thenReturn(Optional.of(shotAction));
    }

    @BeforeEach
    public void setupExitFactory() {
        lenient().when(exitFactory.apply(EXIT_INPUT_ACTION)).thenReturn(Optional.of(exitAction));
    }

    @Mock
    private Move moveAction;
    @Mock
    private MoveAssembler moveFactory;

    @Mock
    private Shot shotAction;
    @Mock
    private ShotAssembler shotFactory;

    @Mock
    private Exit exitAction;
    @Mock
    private ExitAssembler exitFactory;

    @InjectMocks
    private CommandsFactory commandFactory;

    private static final String MOVE_INPUT_ACTION = "move keyboard action";
    private static final String SHOT_INPUT_ACTION = "shot keyboard action";
    private static final String EXIT_INPUT_ACTION = "exit action";
    private static final String UNKNOWN_ACTION = "unknown action";
}