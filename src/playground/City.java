package playground;

import java.util.Arrays;

import entities.*;
import main.Skeleton;
import playground.Crossing;

/**
 * City
 * <p>
 * Tartalmazza a város összes kereszteződését, útját, és a járműveket.
 */
public class City {

	/**
	 * A várost inicializáló függvény
	 */
	public static void initCity() {
		Skeleton.logFunctionStart("City", "initCity", null);

		Crossing c1 = new Crossing();
		Crossing c2 = new Crossing();
		Road r = new Road(c1, c2, 1, 10.0);

		Car car = new Car(c1, c2);

		Skeleton.logFunctionEnd();
	}

	/**
	 * A városban két kereszteződés között a legrövidebb útvonalat adja vissza.
	 * @param from a kiindulási kereszteződés
	 * @param to a célkereszteződés
	 * @return a két kereszteződés közötti legrövidebb Path
	 */
	public static Path shortestPathFrom(Crossing from, Crossing to) {
		Skeleton.logFunctionStart("City", "shortestPathFrom",
				Arrays.asList(Skeleton.createNameOfObject(from), Skeleton.createNameOfObject(to)));

		Path path = Skeleton.Market.path;

		Skeleton.logFunctionEnd();
		return path;
	}
}