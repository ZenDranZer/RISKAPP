package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestTurnController.class,TestValidMapFormat.class,TestRiskCardController.class,TestGameEngine.class,TestMapValidator.class
})
public class ControllersTestSuite {
}
