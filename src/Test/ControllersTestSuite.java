package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for all Packages
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestTurnController.class, TestMapGenerator.class,TestRiskCardController.class,TestGameEngine.class,TestMapValidator.class,TestTournamentController.class
})
public class ControllersTestSuite {
}
