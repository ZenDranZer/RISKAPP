package models;

import java.util.Observable;

/**
 * Class that represents a Risk Card
 * @author hp
 *
 */
public class RiskCard extends Observable {

	private String countryName;
	private String armyType;

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getArmyType() {
		return armyType;
	}

	public void setArmyType(String armyType) {
		this.armyType = armyType;
	}

}
