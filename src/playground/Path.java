package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Leírja a beállított útvonalat (egymás után következő sávokat), amit a jármű
 * automatikusan követ.
 */
public class Path {
	List<Lane> pathLanes;
	Crossing lastCrossing;

	/**
	 * Konstruktor
	 */
	public Path() {

	}

	/**
	 * Visszaadja a következő sávot az útvonalon.
	 * 
	 * @return A következő sáv az útvonalon. Ha az útvonalnak vége, akkor null.
	 */
	public Lane pop() {

	}

	/**
	 * Útvonal hosszabbítása. Ha nem lehet az adott sávval folytatni az utat, akkor
	 * hamissal tér vissza.
	 * 
	 * @param l A sáv, amit hozzá szeretnénk adni.
	 * @return Sikerült-e hozzáadni a sávot
	 */
	public boolean extendPath(Lane l) {
		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Útvonal törlése, minden sáv eltávolítása az útvonalból, és a végpont
	 * nullázása.
	 */
	public void clear() {

	}
}