package models;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Class that represents a Risk Card
 * @author hp
 *
 */
public class RiskCard extends Observable {

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
