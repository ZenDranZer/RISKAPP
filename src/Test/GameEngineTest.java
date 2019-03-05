package Test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import controllers.*;
import utils.TestUtil;
import models.*;

import static org.junit.Assert.*;

public class GameEngineTest {

	TestUtil testData;
	TurnController objTurnController;
	GameEngine objGameEngine;
	@Before
	public void setUp() throws Exception {
		
		objGameEngine = new GameEngine();
		testData = new TestUtil();
		objGameEngine.setActivePlayers(testData.lstPlayers);
		//objTurnController = new TurnController();
		MapGenerator.continentHashMap = testData.testContinentHashMap;
		MapGenerator.countryHashMap = testData.testCountryHashMap;
	}

	@After
	public void tearDown() throws Exception {
	}
	
	
	/**
	 * Validate initial army allocation wherein each country has exactly one army
	 */
	@Test
	public void testAllocateInitialArmies() {
			
		objGameEngine.allocateInitialArmies();

		boolean actualResult = true;
		for(GameCountry country : MapGenerator.countryHashMap.values())
		{
			if(country.getArmiesStationed() != 1)
			{
				actualResult = false;
			}
		}
		assertTrue(actualResult);
	}
}