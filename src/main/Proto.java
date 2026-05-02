package main;

import java.beans.VetoableChangeListener;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import javax.print.DocFlavor.INPUT_STREAM;

import entities.Bus;
import entities.Car;
import entities.Snowplower;
import entities.Vehicle;
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import user.BusDriver;
import user.Cleaner;
import user.Player;

public class Proto {
	public HashMap<Object, String> objectMap = new HashMap<>();
	public Set<Player> players = new HashSet<>();

	private Object getObject(String s) {
		for (Entry<Object, String> e : objectMap.entrySet()) {
			if (e.getValue() == s)
				return e.getKey();
		}
		return null;
	}

	private void forceAddField(Object o, String fieldName, double amount) {
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			// TODO ez int-el is megy?
			field.set(o, (Double) field.get(o) + amount);
		} catch (Exception e) {
			Logger.getGlobal().severe("No such field " + fieldName + " in Proto forceAddField");
		}
	}

	private void forceSetField(Object o, String fieldName, double amount) {
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			// TODO ez int-el is megy?
			field.set(o, amount);
		} catch (Exception e) {
			Logger.getGlobal().severe("No such field " + fieldName + " in Proto forceSetField");
		}
	}

	public void startReadingCommands() {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNext()) {
			String line = sc.nextLine();
			String[] args = line.split(" ");
			String command = args[0];
			try {

				// Feldolgozába lehet egy nagy try catch
				// és akkor minden dobálhat hibaüzenetet
				// vagy csak egyszerűen nem működik nullok miatt, és csak a severe-ek átírását
				// kell megoldani
				switch (command) {
					case "crossing" -> commandCrossing(args);
					case "road" -> commandRoad(args);
					case "spbase" -> commandSpbase(args);
					case "cleaner" -> commandCleaner(args);
					case "driver" -> commandDriver(args);
					case "plower" -> commandPlower(args);
					case "bus" -> commandBus(args);
					case "car" -> commandCar(args);
					case "initend" -> commandInitend(args); // TODO ez am akkor mit is csinál? Lehet a loggert
															// bekapcsolja?
					case "addm" -> commandAddm(args);
					case "addpoint" -> commandAddpoint(args);
					case "addbio" -> commandAddbio(args);
					case "addsalt" -> commandAddsalt(args);
					case "addgravel" -> commandAddgravel(args);
					case "addhead" -> commandAddhead(args);
					case "putsnow" -> commandPutsnow(args);
					case "putice" -> commandPutice(args);
					case "putsalt" -> commandPutsalt(args);
					case "putgravel" -> commandPutgravel(args);
					case "putvehicle" -> commandPutvehicle(args);
					case "clroad" -> commandClroad(args);
					case "info" -> commandInfo(args);
					case "slip" -> commandSlip(args);
				}
			} catch (Exception e) {
				Logger.getGlobal()
						.severe("Error while running the following command:\n" + line + "\n Error: " + e.getMessage());
			}
		}

		sc.close();
	}

	private void commandCrossing(String[] args) {
		int num = Integer.parseInt(args[1]);

		for (int i = 0; i < num; i++) {
			City.getCrossings().add(new Crossing());
		}

	}

	private void commandRoad(String[] args) {
		new Road((Crossing) getObject(args[0]), (Crossing) getObject(args[1]), Integer.parseInt(args[2]),
				Double.parseDouble(args[2]));
	}

	private void commandSpbase(String[] args) {
		// TODO ezt akkor bemozgatni a City-be, vagy egy setter?
		City.setSnowplowBase((Crossing) getObject(args[0]));
	}

	private void commandCleaner(String[] args) {
		players.add(new Cleaner(args[0]));
	}

	private void commandDriver(String[] args) {
		players.add(new BusDriver(args[0]));
	}

	private void commandPlower(String[] args) {
		Cleaner cleaner = (Cleaner) getObject("Cleaner" + args[0]);
		switch (args[1]) {
			case "breaker" -> Snowplower.createWithBreaker(cleaner);
			case "ejector" -> Snowplower.createWithEjector(cleaner);
			default -> throw new IllegalArgumentException("Incorrect head type for snowplower");
		}
	}

	private void commandBus(String[] args) {
		BusDriver driver = (BusDriver) getObject("BusDriver" + args[0]);
		new Bus((Crossing) getObject(args[1]), (Crossing) getObject(args[2]), driver);
	}

	private void commandCar(String[] args) {
		City.getCars().add(new Car((Crossing) getObject(args[0]), (Crossing) getObject(args[1])));
	}

	private void commandInitend(String[] args) {
		Logger.getGlobal().info("Inicialization done");
	}

	private void commandAddm(String[] args) {
		Cleaner c = (Cleaner) getObject("Cleaner" + args[0]);
		c.addMoney(Integer.parseInt(args[1]));
	}

	private void commandAddpoint(String[] args) {
		BusDriver bd = (BusDriver) getObject("BusDriver" + args[0]);
		int amount = Integer.parseInt(args[1]);
		for (int i = 0; i < amount; i++) {
			bd.addPoint();
		}
	}

	private void commandAddbio(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		forceAddField(sp, "bioAmount", Double.parseDouble(args[1]));
	}

	private void commandAddsalt(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		forceAddField(sp, "saltAmount", Double.parseDouble(args[1]));
	}

	private void commandAddgravel(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		forceAddField(sp, "gravelAmount", Double.parseDouble(args[1]));
	}

	private void commandAddhead(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		// TODO nem tudom hogy működnek a fejek (még nincs megírva / Máténál van)
		// sp.getHeadInventory().
		throw new UnsupportedOperationException("addhead parancs még nincs kész");
	}

	private void commandPutsnow(String[] args) {
		Lane l = (Lane) getObject(args[0]);
		forceSetField(l, "snowLevel", Double.parseDouble(args[1]));
	}

	private void commandPutice(String[] args) {
		Lane l = (Lane) getObject(args[0]);
		forceSetField(l, "iceLevel", Double.parseDouble(args[1]));
	}

	private void commandPutsalt(String[] args) {
		Lane l = (Lane) getObject(args[0]);
		l.setSalt((Cleaner) getObject(args[1]));
	}

	private void commandPutgravel(String[] args) {
		Lane l = (Lane) getObject(args[0]);
		switch (args[1]) {
			case "true" -> l.setGravel(true);
			case "false" -> l.setGravel(false);
			default -> throw new IllegalArgumentException("Incorrect true/false value");
		}
	}

	private void commandPutvehicle(String[] args) throws Exception {
		Vehicle v = (Vehicle) getObject(args[0]);
		Lane l = (Lane) getObject(args[1]);

		Field vehicleCurrentLane = v.getClass().getDeclaredField("currentLane");
		vehicleCurrentLane.setAccessible(true);

		Field vehicleLastCrossing = v.getClass().getDeclaredField("lastCrossing");
		vehicleLastCrossing.setAccessible(true);

		Field vehiclePath = v.getClass().getDeclaredField("path");
		vehicleLastCrossing.setAccessible(true);

		// TODO csak ennyi a sávváltás?

		Lane perviousLane = (Lane) vehicleCurrentLane.get(v);
		perviousLane.removeVehicle(v);

		vehicleCurrentLane.set(v, l);
		vehicleLastCrossing.set(v, l.getRoad().getFromCrossing());
		forceSetField(v, "laneProgress", 0);
		((Path) vehiclePath.get(v)).clear();

		l.addVehicle(v);
	}

	private void commandClroad(String[] args) throws Exception {
		Lane l = (Lane) getObject(args[0]);
		forceSetField(l, "iceLevel", 0);
		forceSetField(l, "snowLevel", 0);

		Field field = l.getClass().getDeclaredField("salt");
		field.setAccessible(true);
		field.set(l, null);

		l.setGravel(false);
	}

	private void commandInfo(String[] args) {
		// TODO
	}

	private void commandSlip(String[] args) throws Exception {
		Vehicle v = (Vehicle) getObject(args[0]);
		Field field = v.getClass().getDeclaredField("currentLane");
		field.setAccessible(true);
		Lane currentLane = (Lane) field.get(v);

		// TODO szerintem ez így OK, megcsúsztatás a crasheltetés.
		currentLane.getRoad().crashVehicle(v);
	}

}