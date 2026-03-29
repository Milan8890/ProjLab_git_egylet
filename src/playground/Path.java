package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Skeleton;

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
		Skeleton.initSettingUpObjectStart(this);
		pathLanes = new ArrayList<>();
		lastCrossing = null;
		Skeleton.initSettingUpObjectEnd();
	}


	/**
	 * Visszaadja a következő sávot az útvonalon.
	 * 
	 * @return A következő sáv az útvonalon. Ha az útvonalnak vége, akkor null.
	 */
	public Lane pop() {
		Skeleton.logFunctionStart(this, "pop", null);

		int choice = Skeleton.questionMultiple("Mi a következő sáv az útvonalon?",
				Arrays.asList("Lane 1", "Lane 2", "Vége van"));

		Lane nextLane = null;

		if (choice == 1) {
			nextLane = Skeleton.Market.lane;
		} else if (choice == 2) {
			nextLane = Skeleton.Market.lane2;
		} else {
			nextLane = null;
		}

		Skeleton.logFunctionEnd();
		return nextLane;
	}

	/**
	 * Útvonal hosszabbítása. Ha nem lehet az adott sávval folytatni az utat, akkor
	 * hamissal tér vissza.
	 * 
	 * @param l A sáv, amit hozzá szeretnénk adni.
	 * @return Sikerült-e hozzáadni a sávot
	 */
	public boolean extendPath(Lane l) {
		Skeleton.logFunctionStart(this, "extendPath", Arrays.asList(Skeleton.createNameOfObject(l)));

		// Megkérdezzük a sávot, melyik úthoz tartozik
		Road r = l.getRoad();

		// Megkérdezzük az utat, honnan indul
		Crossing c = r.getFromCrossing();

		boolean isConnected = 1 == Skeleton.questionMultiple("A megadott sáv csatlakozik a mostani út végpontjához?",
				Arrays.asList("Igen", "Nem"));

		if (isConnected) {
			Skeleton.logString("Út sikeresen hozzáadva az útvonalhoz.");
			pathLanes.add(l);
			lastCrossing = r.getToCrossing();
		} else {
			Skeleton.logString("Nincs összekötve az eddigi útvonallal a sáv, sikertelen hozzáadás.");
		}

		Skeleton.logFunctionEnd();
		return isConnected;
	}

	/**
	 * Útvonal törlése, minden sáv eltávolítása az útvonalból, és a végpont nullázása.
	 */
	public void clear() {
		Skeleton.logFunctionStart(this, "clear", null);
		pathLanes.clear();
		lastCrossing = null;
		Skeleton.logFunctionEnd();
	}
}