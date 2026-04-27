package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Egy jármű által követett útvonalat leíró osztály.
 * <p>
 * 
 * Felelősség <br>
 * Útvonal helyességének ellenőrzése, nyilvántartása.
 */
public class Path {
	/**
	 * Azok a sávok, amin a jármű végig fog menni.
	 */
	List<Lane> pathLanes;
	/**
	 * Az a kereszteződés amiben végződik az út utolsó sávja.
	 */
	Crossing lastCrossing;

	/**
	 * Konstruktor
	 */
	public Path() {
	
	}

	/**
	 * Útvonal hosszabbítása. Ha nem lehet az adott sávval folytatni az utat, akkor
	 * hamissal tér vissza.
	 * 
	 * @param l A sáv, amit hozzá szeretnénk adni.
	 * @return Sikerült-e hozzáadni a sávot
	 */
	public boolean extendPath(Lane l) {
		
	}

	/**
	 * Útvonal törlése, minden sáv eltávolítása az útvonalból, és a végpont nullázása.
	 */
	public void clear() {
		pathLanes.clear();
		lastCrossing = null;
	}

	/**
	 * Visszaadja a következő sávot az útvonalon.
	 * 
	 * @return A következő sáv az útvonalon. Ha az útvonalnak vége, akkor null.
	 */
	public Lane pop() {
		if (pathLanes.isEmpty()) {
			return null;
		}
		return pathLanes.remove(pathLanes.size() - 1);
	}
}