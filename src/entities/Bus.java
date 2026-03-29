package entities;

import java.util.Arrays;
import java.util.List;

import javax.swing.plaf.basic.BasicSplitPaneUI.BasicHorizontalLayoutManager;

import main.Skeleton;
import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import user.BusDriver;
import playground.Lane;

/**
 * A BusDriver játékosok által irányított járművek, a stationA, és a stationB
 * között közlekednek
 * <p>
 * 
 * Felelősség <br>
 * Két megálló közti fordulók számolása. Elakadás, ha túl magas a hó. Csúszás
 * kezdeményezése. Ütközés kezelése, ütközés utáni újraindulás. Útvonal
 * követése.
 * Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el. Letaposás végzése.
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Bus extends Vehicle {
	Crossing stationA;
	Crossing stationB;

	BusDriver owner;

	/**
	 * Konstruktor
	 * 
	 * @param stationA  az egyik végállomás
	 * @param stationB: az egyik végállomás
	 * @param owner:    a busz vezetője
	 */
	public Bus(Crossing stationA, Crossing stationB, BusDriver owner) {
		Skeleton.initSettingUpObjectStart(this);
		this.stationA = stationA;
		this.stationB = stationB;
		this.owner = owner;
		Skeleton.initSettingUpObjectEnd();
	}

	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", null);

		boolean isInCrossing = isInCrossing();

		if (isInCrossing) {

			Lane newLane = Skeleton.Market.path.pop();
			if (currentLane == null) {
				Skeleton.logString("Nincs több út beállítva a járműhöz.");
				Skeleton.logFunctionEnd();
				return;
			}
			newLane.addVehicle(this);
			currentLane = newLane;

			Skeleton.logString("A jármű elkezd haladni az új sávon.");
		}

		boolean isWaitingDueToCrash = 1 == Skeleton.questionMultiple("Ütközés után várakozik-e?",
				Arrays.asList("Igen", "Nem"));

		if (isWaitingDueToCrash) {
			Skeleton.logString("RevTimer csökkentve.");

			boolean isCrashOver = 1 == Skeleton.questionMultiple("Lejárt az ütközési idő?",
					Arrays.asList("Igen", "Nem"));

			if (isCrashOver) {
				Skeleton.logString("A jármű felépült.");
			}

			Skeleton.logFunctionEnd();
			return;
		}

		double ice = currentLane.getIce();

		boolean isSlipping = 1 == Skeleton.questionMultiple("Megcsúszik a jármű " + ice + " vastag jégen?",
				Arrays.asList("Igen", "Nem"));

		if (isSlipping) {
			currentLane.getRoad().crashVehicle(this);
			Skeleton.logFunctionEnd();
			return;
		}

		double snow = currentLane.getSnow();

		boolean isStuckInSnow = 1 == Skeleton.questionMultiple("Megakadt " + snow + " vastag hóban a jármű?",
				Arrays.asList("Igen", "Nem"));

		if (isStuckInSnow) {
			List<Lane> neighbourLanes = currentLane.getRoad().getLanes();
			for (Lane l : neighbourLanes) {
				if (currentLane != l) {
					double tempSnow = l.getSnow();
					boolean switchLane = 1 == Skeleton.questionMultiple(
							"Át tud hajtani az autó " + tempSnow + " vastag hórétegre?",
							Arrays.asList("Igen", "Nem"));

					if (switchLane) {
						currentLane.removeVehicle(this);
						l.addVehicle(this);
						currentLane = l;
						Skeleton.logFunctionEnd();
						return;
					}
				}
			}

			Skeleton.logFunctionEnd();
			return;
		}

		Skeleton.logString("Lane progress nő");

		boolean isEndOfRoad = 1 == Skeleton.questionMultiple("A jármű az út végére ért?", Arrays.asList("Igen", "Nem"));
		if (isEndOfRoad) {
			currentLane.trampleSnow();
			currentLane.removeVehicle(this);
			currentLane = null;

			boolean finished = 1 == Skeleton.questionMultiple("A busz a célpontjához ért, és befejezett egy kört?",
					Arrays.asList("Igen", "Nem"));
			if (finished) {
				owner.addPoint();
			}
		}

		Skeleton.logFunctionEnd();
	}
}