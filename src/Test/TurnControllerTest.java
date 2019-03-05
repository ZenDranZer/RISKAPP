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

	@Before
	public void setUp() throws Exception {
		testData = new TestUtil();
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
		int expectedArmies = 3;

		int actualArmies = objTurnController.calculateNewArmies(testData.lstPlayers.get(0));
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
	public void testAllocateCountriesAll() {
		ArrayList<GameCountry> lstCountries = (ArrayList<GameCountry>) testData.testCountryHashMap.values();

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
		
		System.out.println("Test : " + countryCount);
		// Checks if all countries have been allocated
		assertEquals(testData.testCountryHashMap.values().size(), countryCount);

	}

	/**
	 * checks whether all the allocated countries are uniquely allocated
	 */
	public void testAllocateCountriesUnique() {
		ArrayList<GameCountry> lstCountries = (ArrayList<GameCountry>) testData.testCountryHashMap.values();

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
		
		
	}
	
}