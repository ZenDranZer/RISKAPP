package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for models
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestPlayer.class,TestGameState.class,TestCheaterPlayer.class,TestAggressivePlayer.class,TestRandomPlayer.class,TestBenevolentPlayer.class
})
public class ModelsTestSuit {
}
