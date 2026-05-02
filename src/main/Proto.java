package main;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;

import entities.Bus;
import entities.Car;
import entities.Snowplower;
import entities.Vehicle;
import equipment.Head;
import equipment.HeadInventory;
import equipment.HeadListing;
import equipment.heads.Breaker;
import equipment.heads.Dragon;
import equipment.heads.Ejector;
import equipment.heads.GravelSpreader;
import equipment.heads.SaltSpreader;
import equipment.heads.Sweeper;
import main.World.RandomMode;
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
			if (e.getValue().equals(s))
				return e.getKey();
		}
		return null;
	}

	private void forceAddField(Object o, String fieldName, double amount) {
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(o, (Double) field.get(o) + amount);
		} catch (Exception e) {
			Logger.getGlobal().severe("No such field " + fieldName + " in Proto forceAddField");
		}
	}

	private void forceSetField(Object o, String fieldName, double amount) {
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(o, amount);
		} catch (Exception e) {
			Logger.getGlobal().severe("No such field " + fieldName + " in Proto forceSetField");
		}
	}

	private static final int TICKS_PER_SEC = 20;

	public void readCommandsFromCommandLine() throws Exception {
		Scanner sc = new Scanner(System.in);

		while (sc.hasNext()) {
			String line = sc.nextLine();
			if (line.equals("starttime")) {
				timeLoop(sc);
			} else {
				readCommand(line);
			}
		}
		sc.close();
	}

	private void timeLoop(Scanner sc) throws Exception {
		// TODO Még egy outer wilds referencia
		while (true) {
			if (System.in.available() > 0) {
				String line = sc.nextLine();
				if (line.equals("stoptime"))
					return;
				readCommand(line);
			} else {
				Thread.sleep(1000 / TICKS_PER_SEC);
			}

			World.tick();
		}
	}

	private void readCommand(String line) {
		String[] fullArgs = line.split(" ");
		String command = fullArgs[0];

		String[] args = new String[fullArgs.length - 1];
		for (int i = 1; i < fullArgs.length; i++) {
			args[i - 1] = fullArgs[i];
		}

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
				case "initend" -> commandInitend(args);
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
				case "buyplower" -> commandBuyplower(args);
				case "buybio" -> commandBuybio(args);
				case "buysalt" -> commandBuysalt(args);
				case "buygravel" -> commandBuygravel(args);
				case "buyhead" -> commandBuyhead(args);
				case "changehead" -> commandChangehead(args);
				case "cyclehead" -> commandCyclehead(args);
				case "extpath" -> commandExtpath(args);
				case "clpath" -> commandClpath(args);

				// EZEK KÜLÖN VANNAK KEZELVE, ITT MARAD HOGY LEGYEN ÁTFOGÓ PARANCSLISTA
				// case "starttime" -> commandStarttime(args);
				// case "stoptime" -> commandStoptime(args);

				case "endtime" -> commandEndtime(args);
				case "passtime" -> commandPasstime(args);
				case "load" -> commandLoad(args);
				case "crash" -> commandCrash(args);
				case "setrand" -> commandSetrand(args);
				case "setsnowing" -> commandSetsnowing(args);
			}
		} catch (Exception e) {
			Logger.getGlobal()
					.severe("Error while running the following command:\n" + line + "\n Error: " + e);
			e.printStackTrace();
		}
	}

	private void commandCrossing(String[] args) {
		int num = Integer.parseInt(args[0]);

		for (int i = 0; i < num; i++) {
			City.getCrossings().add(new Crossing());
		}

	}

	private void commandRoad(String[] args) {
		new Road((Crossing) getObject(args[0]), (Crossing) getObject(args[1]), Integer.parseInt(args[2]),
				Double.parseDouble(args[3]));
	}

	private void commandSpbase(String[] args) throws Exception {
		Field fieldSpbase = City.class.getDeclaredField("snowplowBase");
		fieldSpbase.setAccessible(true);

		fieldSpbase.set(null, (Crossing) getObject(args[0]));
	}

	private void commandCleaner(String[] args) {
		players.add(new Cleaner(args[0]));
	}

	private void commandDriver(String[] args) {
		players.add(new BusDriver(args[0]));
	}

	private void commandPlower(String[] args) throws Exception {
		Cleaner cleaner = (Cleaner) getObject(args[0]);

		Snowplower sp = switch (args[1]) {
			case "breaker" -> Snowplower.createWithBreaker(cleaner);
			case "ejector" -> Snowplower.createWithEjector(cleaner);
			default -> throw new IllegalArgumentException("Incorrect head type for snowplower");
		};

		Field fieldSnowplowers = cleaner.getClass().getDeclaredField("snowplowers");
		fieldSnowplowers.setAccessible(true);

		Set<Snowplower> snowplowers = (Set<Snowplower>) fieldSnowplowers.get(cleaner);

		snowplowers.add(sp);
	}

	private void commandBus(String[] args) throws Exception {
		BusDriver driver = (BusDriver) getObject(args[0]);
		Bus b = new Bus((Crossing) getObject(args[1]), (Crossing) getObject(args[2]), driver);

		Field fieldBus = driver.getClass().getDeclaredField("bus");
		fieldBus.setAccessible(true);

		fieldBus.set(driver, b);
	}

	private void commandCar(String[] args) throws Exception {
		Field field = City.class.getDeclaredField("cars");
		Set<Car> cars = (Set<Car>) field.get(null);

		cars.add(new Car((Crossing) getObject(args[0]), (Crossing) getObject(args[1])));
	}

	private void commandInitend(String[] args) {
		Logger.getGlobal().info("\nInicialization done\n");
	}

	private void commandAddm(String[] args) {
		Cleaner c = (Cleaner) getObject(args[0]);
		c.addMoney(Integer.parseInt(args[1]));
	}

	private void commandAddpoint(String[] args) {
		BusDriver bd = (BusDriver) getObject(args[0]);
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

	private void commandAddhead(String[] args) throws Exception {
		Snowplower sp = (Snowplower) getObject(args[0]);
		HeadInventory hi = sp.getHeadInventory();
		Field fieldHeads = hi.getClass().getDeclaredField("heads");
		fieldHeads.setAccessible(true);

		List<Head> heads = (List<Head>) fieldHeads.get(hi);

		Head newHead = switch (args[1]) {
			case "breaker" -> new Breaker(sp);
			case "dragon" -> new Dragon(sp);
			case "ejector" -> new Ejector(sp);
			case "gravelSpreader" -> new GravelSpreader(sp);
			case "saltSpreader" -> new SaltSpreader(sp);
			case "sweeper" -> new Sweeper(sp);
			default -> throw new Exception("Unknown head type in changehead");
		};
		heads.add(newHead);
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
		l.meltIce();
		l.cleanSnow();

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

		currentLane.getRoad().crashVehicle(v);
	}

	private void commandBuyplower(String[] args) {
		Cleaner c = (Cleaner) getObject(args[0]);
		switch (args[1]) {
			case "breaker" -> c.buyBreakerSnowplower();
			case "ejector" -> c.buyEjectorSnowplower();
			default -> throw new IllegalArgumentException("Incorrect head type for snowplower");
		}
	}

	private void commandBuybio(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		sp.buyBio();
	}

	private void commandBuysalt(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		sp.buySalt();
	}

	private void commandBuygravel(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		sp.buyGravel();
	}

	private void commandBuyhead(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		HeadInventory hi = sp.getHeadInventory();
		List<HeadListing> headListings = hi.getShop();

		for (HeadListing hl : headListings) {
			if (objectMap.get(hl) == args[1]) {
				hi.buyListing(hl);
				return;
			}
		}
	}

	private void commandChangehead(String[] args) throws Exception {
		Snowplower sp = (Snowplower) getObject(args[0]);
		HeadInventory hi = sp.getHeadInventory();

		String headName = args[1];

		Field field = hi.getClass().getDeclaredField("heads");
		field.setAccessible(true);
		List<Head> heads = (List<Head>) field.get(hi);

		for (Head h : heads) {
			String currentHeadName = switch (h) {
				case Breaker o -> "breaker";
				case Dragon o -> "dragon";
				case Ejector o -> "ejector";
				case GravelSpreader o -> "gravelSpreader";
				case SaltSpreader o -> "saltSpreader";
				case Sweeper o -> "sweeper";
				default -> throw new Exception("Admin command changehead is unimplemented for a new head type");
			};
			if (headName.equals(currentHeadName)) {
				Field activeHead = hi.getClass().getDeclaredField("activeHead");
				activeHead.setAccessible(true);
				activeHead.set(hi, h);
			}
		}

	}

	private void commandCyclehead(String[] args) {
		Snowplower sp = (Snowplower) getObject(args[0]);
		sp.getHeadInventory().cycleActiveHead();
	}

	private void commandExtpath(String[] args) {
		Vehicle v = (Vehicle) getObject(args[0]);
		v.extendPath((Lane) getObject(args[1]));
	}

	private void commandClpath(String[] args) throws Exception {
		Vehicle v = (Vehicle) getObject(args[0]);
		Field field = v.getClass().getDeclaredField("path");
		field.setAccessible(true);
		((Path) field.get(v)).clear();
	}

	private void commandEndtime(String[] args) {
		Logger.getGlobal().severe("endtime command has been renamed to stoptime");
		// https://www.youtube.com/watch?v=t5vG4Be1Ci8
		System.exit(22);
	}

	private void commandPasstime(String[] args) {
		int num = Integer.parseInt(args[0]);
		for (int i = 0; i < num; i++) {
			World.tick();
		}
	}

	private void commandLoad(String[] args) throws Exception {
		String filename = args[0];
		Scanner sc = new Scanner(new File(filename));
		while (sc.hasNext()) {
			String line = sc.nextLine();
			readCommand(line);
		}
		sc.close();
	}

	private void commandCrash(String[] args) {
		Vehicle v = (Vehicle) getObject(args[0]);
		v.crashedInto(Integer.parseInt(args[1]));
	}

	private void commandSetrand(String[] args) {
		switch (args[0]) {
			case "true" -> World.setRandomMode(RandomMode.TRUE);
			case "false" -> World.setRandomMode(RandomMode.FALSE);
			case "random" -> World.setRandomMode(RandomMode.RANDOM);
			default -> throw new IllegalArgumentException("Illegal random type");
		}
	}

	private void commandSetsnowing(String[] args) {
		switch (args[0]) {
			case "true" -> World.setIsSnowing(true);
			case "false" -> World.setIsSnowing(false);
			default -> throw new IllegalArgumentException("Illegal snow true/false type");
		}
	}

}