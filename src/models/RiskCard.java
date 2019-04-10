package models;

import java.io.Serializable;
import java.util.Observable;

/**
 * Class that represents a Risk Card
 */
public class RiskCard extends Observable implements Serializable {


	private String countryName;


	private int armyType;

	/**
	 *Get country name
	 */
	public String getCountryName() {
		return countryName;
	}

	/**Specifies the unique country name per card.*/
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * Get army type
	 * @return army type
	 */
	public int getArmyType() {
		return armyType;
	}

	/**
	 * set army type
	 * @param armyType type of army
	 */
	public void setArmyType(int armyType) {
		this.armyType = armyType;
	}

	/**
	 * To print logs
	 * @return message to be printed
	 */
	public String toString() {
		System.out.println("Card: "+countryName+": "+armyType);
		return "Card: "+countryName+": "+armyType;
	}


}
