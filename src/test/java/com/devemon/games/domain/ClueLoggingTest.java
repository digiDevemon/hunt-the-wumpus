package com.devemon.games.domain;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.GameSquare;
import com.devemon.games.domain.elements.User;
import com.devemon.games.logging.MessagePublisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.SquareStates.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClueLoggingTest {


    @Test
    public void it_should_retrieve_user_position() {
        setupLevels();
        clueLogging.accept(completeLevel);

        verify(user).getPositionID();
    }

    @Test
    public void it_should_retrieve_near_squares_from_map() {
        setupLevels();
        clueLogging.accept(completeLevel);

        verify(completeMap).getConnectedSquares(USER_POSITION_ID);
    }

    @Test
    public void it_should_show_the_expected_logging_for_complete_level() {
        setupLevels();
        clueLogging.accept(completeLevel);

        verify(messagePublisher).accept(EXPECTED_COMPLETE_LEVEL_MESSAGE);
    }


    @Test
    public void it_should_show_the_expected_logging_for_bats_level() {
        setupLevels();
        clueLogging.accept(batsLevel);

        verify(messagePublisher).accept(EXPECTED_BATS_LEVEL_MESSAGE);
    }

    @Test
    public void it_should_show_the_expected_logging_for_hole_and_wumpus_level() {
        setupLevels();
        clueLogging.accept(holeAndWumpusLevel);

        verify(messagePublisher).accept(EXPECTED_HOLE_AND_WUMPUS_LEVEL_MESSAGE);
    }

    private void setupLevels() {
        completeLevel = Map.of("user", user, "gameMap", completeMap);
        batsLevel = Map.of("user", user, "gameMap", batsMap);
        holeAndWumpusLevel = Map.of("user", user, "gameMap", holeAndWumpusMap);
    }

    @BeforeEach
    public void setupUser() {
        when(user.getPositionID()).thenReturn(USER_POSITION_ID);
    }

    @BeforeEach
    public void setupMaps() {
        lenient().when(completeMap.getConnectedSquares(USER_POSITION_ID)).thenReturn(List.of(gameSquareBats, gameSquareWumpus, gameSquareHole));
        lenient().when(batsMap.getConnectedSquares(USER_POSITION_ID)).thenReturn(List.of(gameSquareBats));
        lenient().when(holeAndWumpusMap.getConnectedSquares(USER_POSITION_ID)).thenReturn(List.of(gameSquareWumpus, gameSquareHole));
    }

    @BeforeEach
    public void setupGameSquareHole() {
        lenient().when(gameSquareHole.getThreat()).thenReturn(HOLE);
    }

    @BeforeEach
    public void setupGameSquareBats() {
        lenient().when(gameSquareBats.getThreat()).thenReturn(BATS);
    }

    @BeforeEach
    public void setupGameSquareWumpus() {
        lenient().when(gameSquareWumpus.getThreat()).thenReturn(WUMPUS);
    }

    private static final Integer USER_POSITION_ID = 1;

    private Map<String, Object> completeLevel;
    private Map<String, Object> batsLevel;
    private Map<String, Object> holeAndWumpusLevel;

    private static final String EXPECTED_COMPLETE_LEVEL_MESSAGE = "You have the next feelings:\n-You hear nearby flapping.\n-A foul smell comes from somewhere.\n-A gust of cold air comes from somewhere.";

    private static final String EXPECTED_BATS_LEVEL_MESSAGE = "You have the next feelings:\n-You hear nearby flapping.";
    private static final String EXPECTED_HOLE_AND_WUMPUS_LEVEL_MESSAGE = "You have the next feelings:\n-A foul smell comes from somewhere.\n-A gust of cold air comes from somewhere.";

    @Mock
    private GameMap completeMap;

    @Mock
    private GameMap batsMap;

    @Mock
    private GameMap holeAndWumpusMap;

    @Mock
    private User user;

    @Mock
    private GameSquare gameSquareBats;

    @Mock
    private GameSquare gameSquareHole;

    @Mock
    private GameSquare gameSquareWumpus;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private ClueLogging clueLogging;
}