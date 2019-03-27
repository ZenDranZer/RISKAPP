package models;

import java.util.Observable;

/**
 * Class that represents a Risk Card
 */
public class RiskCard extends Observable {

	/**Specifies the unique country name per card.*/
	private String countryName;

	private int armyType;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public int getArmyType() {
		return armyType;
	}

	public void setArmyType(int armyType) {
		this.armyType = armyType;
	}

}
