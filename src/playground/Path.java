package playground;

import java.util.Arrays;
import java.util.List;

import main.Skeleton;

public class Path {
	List<Lane> pathLanes;
	Crossing lastCrossing;

	/**
	 * 
	 * @return
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

	public boolean extendPath(Lane l) {
		// LOL
		Skeleton.logFunctionStart(this, "extendPath", Arrays.asList(Skeleton.createNameOfObject(l)));

		// Megkérdezzük a sávot, melyik úthoz tartozik
		Road r = l.getRoad();

		// Megkérdezzük az utat, honnan indul
		Crossing c = r.getFromCrossing();

		boolean isConnected = 1 == Skeleton.questionMultiple("A megadott sáv csatlakozik a mostani út végpontjához?",
				Arrays.asList("Igen", "Nem"));

		if (isConnected) {
			pathLanes.add(l);
			lastCrossing = r.getToCrossing();
		}

		Skeleton.logFunctionEnd();
		return isConnected;
	}

	public void clear() {
		Skeleton.logFunctionStart(this, "clear", null);
		pathLanes.clear();
		lastCrossing = null;
		Skeleton.logFunctionEnd();
	}
}