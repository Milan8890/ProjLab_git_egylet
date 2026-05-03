package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.*;
import main.World;
import playground.Crossing;

/**
 * City
 * <p>
 * 
 * Felelősség <br>
 * Utak, kereszteződések felvétele, autók felvétele, hókotróbázis kijelölése,
 * azaz város inicializálása.
 * Autók számára legrövidebb járható út számolása.
 * Mivel a játék folyamán csak egy várost fogunk használni, és sok helyről
 * szeretnénk elérni, statikus osztály lesz.
 */
public class City {
	/**
	 * A városban lévő kereszteződések.
	 */
	static Set<Crossing> crossings = new HashSet<>();
	/**
	 * A városban lévő utak.
	 */
	static Set<Road> roads = new HashSet<>();
	/**
	 * A városban lévő autók.
	 */
	static Set<Car> cars = new HashSet<>();
	/**
	 * A hókotróbázis.
	 */
	static Crossing snowplowBase = null;

	/**
	 * A egyszerű várost inicializáló függvény, mivel a tesztek maguktól hozzák
	 * lérte a pályát, nincs használva.
	 */
	public static void initCity() {
		Crossing c1 = new Crossing();
		Crossing c2 = new Crossing();
		Crossing c3 = new Crossing();
		new Road(c1, c2, 1, 100);
		new Road(c2, c3, 1, 100);
		new Road(c3, c1, 1, 100);
		cars.add(new Car(c1, c3));
		Logger.getGlobal().log(Level.INFO, "City initialized", new Object[] {});
	}

	/**
	 * Városban lévő kereszteződések lekérdezése.
	 */
	public static Set<Crossing> getCrossings() {
		Logger.getGlobal().log(Level.INFO, "City returned crossings", new Object[] {});
		return crossings;
	}

	/**
	 * Hókotróközpont lekérdezése.
	 */
	public static Crossing getSnowplowBase() {
		Logger.getGlobal().log(Level.INFO, "City returned [Obj] as snowplower base", new Object[] { snowplowBase });
		return snowplowBase;
	}

	/**
	 * A városban két kereszteződés között a legrövidebb útvonalat adja vissza.
	 * 
	 * @param from a kiindulási kereszteződés
	 * @param to   a célkereszteződés
	 * @return a két kereszteződés közötti legrövidebb Path
	 */
	public static Path shortestPathFrom(Crossing from, Crossing to, Vehicle v) {

		Crossing startCrossing = from;
		HashMap<Crossing, Double> distances = new HashMap<>();
		HashMap<Crossing, Lane> connectingLane = new HashMap<>();
		HashMap<Crossing, Boolean> visited = new HashMap<>();

		for (Crossing c : crossings) {
			if (c != startCrossing) {
				distances.put(c, Double.MAX_VALUE);
			} else {
				distances.put(c, 0.0);
			}
			connectingLane.put(c, null);
			visited.put(c, false);
		}

		while (true) {
			Crossing unvisited = null;
			Double unvisitedDistance = Double.MAX_VALUE;

			for (Entry<Crossing, Double> e : distances.entrySet()) {
				Crossing current = e.getKey();
				if (!visited.get(current) && distances.get(current) < unvisitedDistance) {
					unvisited = current;
					unvisitedDistance = distances.get(current);
				}
			}

			if (unvisited == null)
				break;

			visited.put(unvisited, true);

			for (Road r : unvisited.getOutRoads()) {
				Crossing neighbour = r.getToCrossing();
				if (!visited.get(neighbour)) {
					Double newDist = distances.get(unvisited) + r.getLength();

					Lane safeLane = null;
					for (Lane l : r.getLanes()) {
						// csak járható sávot adhasson
						if (l.hasStuckVehicle() || l.getSnow() > Vehicle.MAX_SNOW_LEVEL) {
							continue;
						}
						safeLane = l;
					}

					if (safeLane == null) {
						continue;
					}

					if (newDist < distances.get(neighbour)) {
						distances.put(neighbour, newDist);
						connectingLane.put(neighbour, safeLane);
					}
				}
			}
		}

		Path p = new Path(v);

		List<Lane> reversedLaneList = new ArrayList<>();

		Crossing currentCrossing = to;

		while (true) {
			Lane l = connectingLane.get(currentCrossing);
			if (l == null) {
				Logger.getGlobal().log(Level.INFO, "City couldn't find path between [Obj] and [Obj] for car [Obj]",
						new Object[] { from, to, v });
				return new Path(v);
			}
			reversedLaneList.add(l);
			currentCrossing = l.getRoad().getFromCrossing();
			if (currentCrossing == from) {
				break;
			}
		}

		for (Lane l : reversedLaneList.reversed()) {
			if (!p.extendPath(l)) {
				Logger.getGlobal().severe("Couldn't add lane while constructing shortest path.");
			}
		}

		Logger.getGlobal().log(Level.INFO, "City calculated shortest path from [Obj] to [Obj] as [Obj]",
				new Object[] { from, to, p });

		return p;
	}
}