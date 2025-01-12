package com.devemon.games.logging;

import com.devemon.games.domain.elements.GameMap;
import com.devemon.games.domain.elements.Square;
import com.devemon.games.domain.elements.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static com.devemon.games.domain.elements.SquareState.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClueLoggingTest {


    @Test
    public void it_should_retrieve_user_position() {
        setupLevels();
        clueLogging.logFeelingsClue(completeLevel);

        verify(user).getPositionId();
    }

    @Test
    public void it_should_retrieve_near_squares_from_map() {
        setupLevels();
        clueLogging.logFeelingsClue(completeLevel);

        verify(completeMap).getConnectedSquares(USER_POSITION_ID);
    }

    @Test
    public void it_should_show_the_expected_logging_for_complete_level() {
        setupLevels();
        clueLogging.logFeelingsClue(completeLevel);

        verify(messagePublisher).accept(EXPECTED_COMPLETE_LEVEL_MESSAGE);
    }


    @Test
    public void it_should_show_the_expected_logging_for_bats_level() {
        setupLevels();
        clueLogging.logFeelingsClue(batsLevel);

        verify(messagePublisher).accept(EXPECTED_BATS_LEVEL_MESSAGE);
    }

    @Test
    public void it_should_show_the_expected_logging_for_hole_and_wumpus_level() {
        setupLevels();
        clueLogging.logFeelingsClue(holeAndWumpusLevel);

        verify(messagePublisher).accept(EXPECTED_HOLE_AND_WUMPUS_LEVEL_MESSAGE);
    }

    @Test
    public void it_should_show_the_expected_logging_target_squares() {
        setupLevels();
        clueLogging.logNearRooms(completeLevel);

        verify(messagePublisher).accept(EXPECTED_TARGET_SQUARES_MESSAGE);
    }

    private void setupLevels() {
        completeLevel = Map.of("user", user, "gameMap", completeMap);
        batsLevel = Map.of("user", user, "gameMap", batsMap);
        holeAndWumpusLevel = Map.of("user", user, "gameMap", holeAndWumpusMap);
    }

    @BeforeEach
    public void setupUser() {
        when(user.getPositionId()).thenReturn(USER_POSITION_ID);
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
        lenient().when(gameSquareHole.getId()).thenReturn(HOLE_SQUARE_POSITION);
    }

    @BeforeEach
    public void setupGameSquareBats() {
        lenient().when(gameSquareBats.getThreat()).thenReturn(BATS);
        lenient().when(gameSquareBats.getId()).thenReturn(BATS_SQUARE_POSITION);
    }

    @BeforeEach
    public void setupGameSquareWumpus() {
        lenient().when(gameSquareWumpus.getThreat()).thenReturn(WUMPUS);
        lenient().when(gameSquareWumpus.getId()).thenReturn(WUMPUS_SQUARE_POSITION);

    }

    private static final Integer USER_POSITION_ID = 1;

    private static final Integer BATS_SQUARE_POSITION = 2;
    private static final Integer WUMPUS_SQUARE_POSITION = 3;
    private static final Integer HOLE_SQUARE_POSITION = 4;

    private Map<String, Object> completeLevel;
    private Map<String, Object> batsLevel;
    private Map<String, Object> holeAndWumpusLevel;

    private static final String EXPECTED_COMPLETE_LEVEL_MESSAGE = "You have the next feelings:\n-You hear nearby flapping.\n-A foul smell comes from somewhere.\n-A gust of cold air comes from somewhere.";
    private static final String EXPECTED_BATS_LEVEL_MESSAGE = "You have the next feelings:\n-You hear nearby flapping.";
    private static final String EXPECTED_HOLE_AND_WUMPUS_LEVEL_MESSAGE = "You have the next feelings:\n-A foul smell comes from somewhere.\n-A gust of cold air comes from somewhere.";
    private static final String EXPECTED_TARGET_SQUARES_MESSAGE = "Current room: 1 Near rooms: 2,3,4";


    @Mock
    private GameMap completeMap;

    @Mock
    private GameMap batsMap;

    @Mock
    private GameMap holeAndWumpusMap;

    @Mock
    private User user;

    @Mock
    private Square gameSquareBats;

    @Mock
    private Square gameSquareHole;

    @Mock
    private Square gameSquareWumpus;

    @Mock
    private MessagePublisher messagePublisher;

    @InjectMocks
    private ClueLogging clueLogging;
}