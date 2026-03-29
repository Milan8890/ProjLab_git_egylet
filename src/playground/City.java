package playground;

import java.util.Arrays;

import entities.*;
import main.Skeleton;
import playground.Crossing;

public class City {

	public static void initCity() {
		Skeleton.logFunctionStart("City", "initCity", null);

		Crossing c1 = new Crossing();
		Crossing c2 = new Crossing();
		Road r = new Road(c1, c2, 1, 10.0);

		Car car = new Car(c1, c2);

		Skeleton.logFunctionEnd();
	}

	public static Path shortestPathFrom(Crossing from, Crossing to) {
		Skeleton.logFunctionStart("City", "shortestPathFrom",
				Arrays.asList(Skeleton.createNameOfObject(from), Skeleton.createNameOfObject(to)));

		Path path = Skeleton.Market.path;

		Skeleton.logFunctionEnd();
		return path;
	}
}