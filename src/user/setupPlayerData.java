package user;

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
	 * @return a játékos színe
	 */
	public String getColor() {
		return color;
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
