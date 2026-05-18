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
		"Zöld", "Sárga", "Kék", "Piros", "Lila", "Narancs" 
	 * @return a játékos színe
	 */
	public Color getColor() {
		switch (color) {
			case "Piros":
				return Color.RED;
			case "Kék":
				return Color.BLUE;
			case "Zöld":
				return Color.GREEN;
			case "Sárga":
				return Color.YELLOW;
			case "Lila":
				return Color.MAGENTA;
			case "Narancs":
				return Color.ORANGE;
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
