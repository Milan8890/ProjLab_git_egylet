package user;

import java.awt.Color;

/**
 * Egy játékos kezdőképernyőn megadott adatait tárolja.
 */
public class setupPlayerData {
	/**
	 * A játékos neve.
	 */
	private String name;

	/**
	 * A játékos választott színe.
	 */
	private String color;

	/**
	 * A játékos választott járműve.
	 */
	private String vehicle;

	/**
	 * Létrehoz egy üres játékosadat objektumot.
	 */
	public setupPlayerData() {
	}

	/**
	 * Visszaadja a játékos nevét.
	 *
	 * @return a játékos neve
	 */
	public String getName() {
		return name;
	}

	/**
	 * Beállítja a játékos nevét.
	 *
	 * @param name a beállítandó játékosnév
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Visszaadja a játékos választott színét.
	 *
		"Bordó", "Barna", "Ibolya", "Lime", "Rózsaszín", "Narancs" 
	 * @return a játékos színe
	 * 
	 */
	public Color getColor() {
		switch (color) {
			case "Bordó":
				return new Color(153, 0, 0);
			case "Barna":
				return new Color(153, 76, 0);
			case "Ibolya":
				return new Color(153, 0, 153);
			case "Lime":
				return new Color(194, 204, 106);
			case "Rózsaszín":
				return new Color(255, 51, 153);
			case "Narancs":
				return new Color(255, 128, 0);
			default:
			{
				System.err.println("Invalid color: " + color);
				return Color.BLACK;
			}
		}
	}

	/**
	 * Beállítja a játékos választott színét.
	 *
	 * @param color a beállítandó szín
	 */
	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Visszaadja a játékos választott járművét.
	 *
	 * @return a játékos járműve
	 */
	public String getVehicle() {
		return vehicle;
	}

	/**
	 * Beállítja a játékos választott járművét.
	 *
	 * @param vehicle a beállítandó jármű
	 */
	public void setVehicle(String vehicle) {
		this.vehicle = vehicle;
	}
}
