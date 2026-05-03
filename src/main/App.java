package main;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.GapContent;

import entities.*;
import user.*;
import playground.*;
import playground.Lane.Salt;
import equipment.*;
import equipment.heads.GravelSpreader;

public class App {

	public static void main(String[] args) throws Exception {
		Proto proto = new Proto();

		Logger.getGlobal().setUseParentHandlers(false);
		OwnHandler ownHandler = new OwnHandler(proto.objectMap);
		Logger.getGlobal().addHandler(ownHandler);

		// Crossing c1 = new Crossing();
		// Crossing c2 = new Crossing();
		// Road r1 = new Road(c1, c2, 3, 10.0);

		// World.tick();

		proto.readCommandsFromCommandLine();

		// Cleaner cleaner1 = new Cleaner("Asd", null);
		// System.out.println(cleaner1.hashCode());
		// Cleaner cleaner2 = new Cleaner("Asd", null);
		// System.out.println(cleaner2.hashCode());

		// BusDriver busdriver1 = new BusDriver("ASd");
		// System.out.println(busdriver1.hashCode());
		// BusDriver busdriver2 = new BusDriver("ASd");
		// System.out.println(busdriver2.hashCode());

		// Crossing crossing1 = new Crossing();
		// System.out.println(crossing1.hashCode());
		// Crossing crossing2 = new Crossing();
		// System.out.println(crossing2.hashCode());

		// Car car1 = new Car(crossing1, crossing2);
		// System.out.println(car1.hashCode());
		// Car car2 = new Car(crossing1, crossing2);
		// System.out.println(car2.hashCode());

		// Road road1 = new Road(crossing1, crossing2, 0, 0);
		// System.out.println(road1.hashCode());
		// Road road2 = new Road(crossing1, crossing2, 0, 0);
		// System.out.println(road2.hashCode());

		// // HeadInventory headinventory1 = HeadInventory.createWithBreaker(null);

		// Bus bus1 = new Bus(crossing1, crossing2, busdriver1);
		// System.out.println(bus1.hashCode());
		// Bus bus2 = new Bus(crossing2, crossing1, busdriver2);
		// System.out.println(bus2.hashCode());

		// Crossing c1 = new Crossing();
		// Crossing c2 = new Crossing();

		// Road r1 = new Road(c1, c2, 3, 0);
		// Road r2 = new Road(c2, c1, 3, 0);

		// System.out.println(r1.getLanes().get(0).hashCode());
		// System.out.println(r1.getLanes().get(1).hashCode());
		// System.out.println(r1.getLanes().get(2).hashCode());

		// System.out.println(r2.getLanes().get(0).hashCode());
		// System.out.println(r2.getLanes().get(1).hashCode());
		// System.out.println(r2.getLanes().get(2).hashCode());

		// // Cleaner cleaner1 = new Cleaner("Asd", null);
		// // System.out.println(cleaner1.hashCode());
		// // Cleaner cleaner2 = new Cleaner("Asd", null);
		// // System.out.println(cleaner2.hashCode());

		// // Snowplower snowplower1 = new Snowplower(cleaner1, c1);
		// // System.out.println(snowplower1.hashCode());
		// // Snowplower snowplower2 = new Snowplower(cleaner1, c1);
		// // System.out.println(snowplower2.hashCode());

		// for (Entry<Object, String> e : ownHandler.objectMap.entrySet()) {
		// System.out.println(e.getValue() + "\t" + e.getKey().hashCode());
		// }

		// Crossing c = new Crossing();
		// Crossing c2 = new Crossing();
		// Road r = new Road(c, c2, 0, 0);
	}
}