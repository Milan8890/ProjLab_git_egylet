package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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
		Logger.getGlobal().log(Level.INFO, "[Obj] extended with [Obj] successfully" , new Object[] {this, l});
		Logger.getGlobal().log(Level.INFO, "[Obj] couldn’t extend with [Obj], because it’s not connected" , new Object[] {this, l});

		throw new UnsupportedOperationException("Még nincs kész");
	}

	/**
	 * Útvonal törlése, minden sáv eltávolítása az útvonalból, és a végpont
	 * nullázása.
	 */
	public void clear() {
		Logger.getGlobal().log(Level.INFO, "[Obj] cleared" , new Object[] {this});

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
			Logger.getGlobal().log(Level.INFO, "[Obj] has no next lane" , new Object[] {this});
			return null;
		}

		Lane l = pathLanes.remove(pathLanes.size() - 1);

		Logger.getGlobal().log(Level.INFO, "[Obj] returned next lane [Obj]" , new Object[] {this, l});
		return l;
	}
}