package entities;

import java.lang.runtime.SwitchBootstraps;
import java.util.Arrays;
import java.util.List;

import main.Skeleton;
import playground.City;
import playground.Crossing;
import playground.City;
import playground.Path;
import playground.Lane;

/**
 * A gépi irányítású járművek, home és work közt közlekednek
 * <p>
 * 
 * Felelősség <br>
 * A legrövidebb út újraterveztetése, ha blokkolva lenne egy sáv. Elakad, ha túl
 * magas a
 * hó. Kezdeményezi a csúszást. Ütközés kezelése. Nyilvántartja, hogy a
 * jelenlegi úton
 * hol helyezkedik el. Letaposás végzése. <br>
 * 
 * Ősosztályok <br>
 * Vechicle
 */
public class Car extends Vehicle {
	Crossing home;
	Crossing work;

	/**
	 * Konstruktor
	 * 
	 * @param home egy kereszteződés ami az otthona
	 * @param work egy kereszteződés ami a munkahelye
	 */
	public Car(Crossing home, Crossing work) {
		Skeleton.initSettingUpObjectStart(this);
		this.home = home;
		this.lastCrossing = home;
		this.work = work;
		Skeleton.initSettingUpObjectEnd();
	}

	/**
	 * Az autó léptetéskor történő lehetőségeit kezeli:
	 * -Kereszteződésnél kezeli az új sávba hajtás lehetőségét az ottani hó
	 * magasságtól függően.
	 * -Útnál pedig kezeli a túl magas hó miatti elakadást, a takarítás miatti
	 * felszabadítást.
	 * -Kezeli még az elcsúszást jeges úton, az emiatti baleseteket
	 * -Kezeli ha útról kereszteződésbe ér, és azt ha ez éppen a munkahelye, vagy
	 * otthona az autónak.
	 */
	public void onTick() {
		Skeleton.logFunctionStart(this, "onTick", null);

		boolean isInCrossing = isInCrossing();

		if (isInCrossing) {
			currentLane = Skeleton.Market.path.pop();
			if (currentLane == null) {
				boolean isGoingToWork = 1 == Skeleton.questionMultiple("A jármű merre felé megy?", Arrays.asList("Munkahely", "Otthon"));
				if (isGoingToWork) {
					City.shortestPathFrom(lastCrossing, work);
				} else {
					City.shortestPathFrom(lastCrossing, home);
				}
				Skeleton.logFunctionEnd();
				return;
			}
			currentLane.addVehicle(this);

			Skeleton.logString("A jármű elkezd haladni az új sávon.");
		} else {
			currentLane = Skeleton.Market.lane;
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

			boolean finished = 1 == Skeleton.questionMultiple("A jármű a célpontjához ért?",
					Arrays.asList("Igen", "Nem"));
			if (finished) {
				Skeleton.logString("Az autó elkezdett visszamenni.");
			}
		}

		Skeleton.logFunctionEnd();
	}
}