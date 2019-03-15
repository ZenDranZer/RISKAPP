package Test;

import utils.*;
import controllers.MapGenerator;
import controllers.TurnController;
import models.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class TurnControllerTest {
	private ArrayList<Player> lstPlayers;

	TestUtil testData;
	TurnController objTurnController;
	MapGenerator mapGenerator ;

	@Before
	public void setUp() throws Exception {
		testData = new TestUtil();
		mapGenerator = new MapGenerator();
		objTurnController = new TurnController();
		MapGenerator.continentHashMap = testData.testContinentHashMap;
		MapGenerator.countryHashMap = testData.testCountryHashMap;
	}

	@After
	public void tearDown() throws Exception {
	}


	/**
	 * Test the calculation on new armies to allocated at the beginning of each
	 * turn
	 */
	@Test
	public void testCalculateNewArmies() {
		int expectedArmies = 8;

		int actualArmies = objTurnController.calculateNewArmies(testData.lstPlayers.get(0),mapGenerator);
		assertEquals(expectedArmies, actualArmies);
	}

	/**
	 * Tests allocation of initial armies to each player
	 */
	@Test
	public void testGetEachPlayerArmy() {
		int expectedArmies = 40;
		int actualArmies = objTurnController.getEachPlayerArmy(testData.lstPlayers);
		assertEquals(expectedArmies, actualArmies);
	}

	/**
	 * Checks if all the countries have been allocated
	 */
	@Test
	public void testAllocateCountriesAll() {
		ArrayList<GameCountry> lstCountries = new ArrayList( testData.testCountryHashMap.values());

		ArrayList<Player> tempPlayers = new ArrayList<Player>();

		for (Player pl : testData.lstPlayers) {
			Player obj = new Player(pl.getName(), pl.getId());
			tempPlayers.add(obj);
		}

		objTurnController.allocateCountries(tempPlayers, lstCountries);

		int countryCount = 0;
		for (Player pl : tempPlayers) {
			countryCount += pl.getCountries().size();
		}
		// Checks if all countries have been allocated
		assertEquals(testData.testCountryHashMap.values().size(), countryCount);

	}

	/**
	 * checks whether all the allocated countries are uniquely allocated
	 */
	@Test
	public void testAllocateCountriesUnique() {
		ArrayList<GameCountry> lstCountries = new ArrayList<GameCountry>( testData.testCountryHashMap.values());

		ArrayList<Player> tempPlayers = new ArrayList<Player>();

		for (Player pl : testData.lstPlayers) {
			Player obj = new Player(pl.getName(), pl.getId());
			tempPlayers.add(obj);
		}

		objTurnController.allocateCountries(tempPlayers, lstCountries);

		ArrayList<String> tempCountryNames = tempPlayers.get(0).getCountryNames();
		
		//tempCountry will only contain names common in both lists
		tempCountryNames.retainAll(tempPlayers.get(1).getCountryNames());
		// Checks if all the allocated countries are unique
		assertEquals(0, tempCountryNames.size());
	}
	
	/**
	 * checks the army placement functionality
	 */
	@Test
	public void testPlaceArmy()
	{
		objTurnController.placeArmy(testData.lstPlayers.get(0), "India", 3);
		assertEquals(3, testData.testCountryHashMap.get("India").getArmiesStationed());
	}
	
}