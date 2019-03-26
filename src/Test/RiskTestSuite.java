package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestGameEngine.class,TestMapValidator.class,TestPlayerAttack.class,TestRiskCardController.class,TestValidMapFormat.class,TestTurnController.class
})
public class RiskTestSuite {
}
