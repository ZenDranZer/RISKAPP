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
	private String armyType;
	private boolean isWild;

	public boolean isCardWild() { return isWild;}

	public void setWild(boolean wild) { isWild = wild; }

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
