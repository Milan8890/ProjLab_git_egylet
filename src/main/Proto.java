package main;

import java.beans.VetoableChangeListener;
import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.DocFlavor.INPUT_STREAM;

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
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Lane.Salt;
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

	public void readCommandsFromCommandLine() {
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			readCommand(sc);
		}
	}

	private void readCommand(Scanner sc) {
		String line = sc.nextLine();
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
				case "buyplower" -> commandBuyplower(args);
				case "buybio" -> commandBuybio(args);
				case "buysalt" -> commandBuysalt(args);
				case "buygravel" -> commandBuygravel(args);
				case "buyhead" -> commandBuyhead(args);
				case "changehead" -> commandChangehead(args);
				case "cyclehead" -> commandCyclehead(args);
				case "extpath" -> commandExtpath(args);
				case "clpath" -> commandClpath(args);
				case "starttime" -> commandStarttime(args);
				case "stoptime" -> commandStoptime(args);
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
				Double.parseDouble(args[2]));
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
		field.setAccessible(true);
		Set<Car> cars = (Set<Car>) field.get(null);

		cars.add(new Car((Crossing) getObject(args[0]), (Crossing) getObject(args[1])));
	}

	private void commandInitend(String[] args) {
		Logger.getGlobal().info("Inicialization done");
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
		l.meltIce();
		l.cleanSnow();

		Field field = l.getClass().getDeclaredField("salt");
		field.setAccessible(true);
		field.set(l, null);

		l.setGravel(false);
	}

	private void commandSlip(String[] args) throws Exception {
		Vehicle v = (Vehicle) getObject(args[0]);
		Field field = v.getClass().getDeclaredField("currentLane");
		field.setAccessible(true);
		Lane currentLane = (Lane) field.get(v);

		// TODO szerintem ez így OK, megcsúsztatás a crasheltetés.
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

	private void commandStarttime(String[] args) {
		// TODO nemtom
	}

	private void commandStoptime(String[] args) {
		// TODO nemtom
	}

	private void commandEndtime(String[] args) {
		// https://www.youtube.com/watch?v=t5vG4Be1Ci8
		System.exit(22);
	}

	private void commandPasstime(String[] args) {
		// TODO nemtom
	}

	private void commandLoad(String[] args) throws Exception {
		String filename = args[0];
		// TODO Így megy?
		Scanner sc = new Scanner(new File(filename));
		while (sc.hasNext()) {
			readCommand(sc);
		}
	}

	private void commandCrash(String[] args) {
		Vehicle v = (Vehicle) getObject(args[0]);
		v.crashedInto(Integer.parseInt(args[1]));
	}

	private void commandSetrand(String[] args) {
		// TODO

		switch (args[0]) {
			case "true" -> System.out.println("Nemtom");
			case "false" -> System.out.println("Nemtom");
			case "random" -> System.out.println("Nemtom");
			default -> throw new IllegalArgumentException("Illegal random type");
		}
	}

	private void commandSetsnowing(String[] args) {
		// TODO
	}

	private void commandInfo(String[] args) throws Exception {
		if (args[0].equals("city")) {
			infoCity();
			return;
		}

		Object obj = getObject(args[0]);

		switch (obj) {
			case Crossing o -> infoObject(o);
			case Road o -> infoObject(o);
			case Lane o -> infoObject(o);
			case Salt o -> infoObject(o);
			case Cleaner o -> infoObject(o);
			case BusDriver o -> infoObject(o);
			case Snowplower o -> infoObject(o);
			case Bus o -> infoObject(o);
			case Car o -> infoObject(o);
			case Path o -> infoObject(o);
			case HeadListing o -> infoObject(o);
			case Head o -> infoObject(o);
			case HeadInventory o -> infoObject(o);

			default -> throw new Exception("No such type for info");
		}
	}

	// INNEN LEFELÉ CSAK INFO

	private void infoCity()
			throws Exception {
		String prefix = "INFO City has ";
		for (Crossing crossing : City.getCrossings()) {
			Logger.getGlobal().log(Level.INFO, prefix + "[Obj]", new Object[] { crossing });
		}
		Field roads = City.class.getDeclaredField("roads");
		roads.setAccessible(true);
		List<Road> roadsList = (List<Road>) roads.get(null);
		for (Road road : roadsList) {
			Logger.getGlobal().log(Level.INFO, prefix + "[Obj]", new Object[] { road });
		}

		Field cars = City.class.getDeclaredField("cars");
		cars.setAccessible(true);
		List<Car> carsList = (List<Car>) cars.get(null);
		for (Car car : carsList) {
			Logger.getGlobal().log(Level.INFO, prefix + "[Obj]", new Object[] { car });
		}

		Crossing snowplowBase = City.getSnowplowBase();
		Logger.getGlobal().log(Level.INFO, "City snowplow base is [Obj]", new Object[] { snowplowBase });

	}

	private void infoObject(Crossing c) {
		for (Road road : c.getOutRoads()) {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has outgoing [Obj]", new Object[] { c, road });
		}
	}

	private void infoObject(Road r) {
		for (Lane lane : r.getLanes()) {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has lane [Obj]", new Object[] { r, lane });
		}
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] from crossing is [Obj]",
				new Object[] { r, r.getFromCrossing() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] to crossing is [Obj]", new Object[] { r, r.getToCrossing() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] length is " + r.getLength(), new Object[] { r });
	}

	private void infoObject(Lane l)
			throws Exception {
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] is on [Obj]", new Object[] { l, l.getRoad() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] snow level is" + l.getSnow(), new Object[] { l });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] ice level is" + l.getIce(), new Object[] { l });
		for (Vehicle v : l.getVehicles()) {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] contains [Obj]", new Object[] { l, v });
		}
		Field saltField = l.getClass().getDeclaredField("salt");
		saltField.setAccessible(true);
		Salt salt = (Salt) saltField.get(l);
		if(salt!=null)
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] contains [Obj]", new Object[] {l,salt });
		else 
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] does not contain salt", new Object[] {l});

		if(l.hasGravel())
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has gravel", new Object[] {l});
		else
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] does not have gravel", new Object[] { l });
	}

	private void infoObject(Salt s)
			throws Exception {
		Field lifetimeField = s.getClass().getDeclaredField("lifetime");
		lifetimeField.setAccessible(true);
		double lifetime = (double) lifetimeField.get(s);

		Logger.getGlobal().log(Level.INFO, "INFO [Obj] lifetime is " + lifetime, new Object[] { s });

		Field ownerField = s.getClass().getDeclaredField("owner");
		ownerField.setAccessible(true);
		Cleaner owner = (Cleaner) ownerField.get(s);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] owner is [Obj]", new Object[] {s, owner});
	}

	private void infoObject(Cleaner c)
			throws Exception {
		infoPlayer(c);
		Field monField = c.getClass().getDeclaredField("money");
		monField.setAccessible(true);
		int money = (int) monField.get(c);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has " + money +"$", new Object[] {c});

		Field spField = c.getClass().getDeclaredField("snowplowers");
		spField.setAccessible(true);
		Set<Snowplower> snowplowers = (Set<Snowplower>) spField.get(c);
		for(Snowplower sp : snowplowers){
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has [Obj]", new Object[] {c, sp});
		}
	}

	private void infoObject(BusDriver bd)
			throws Exception {
		infoPlayer(bd);
		Field pointField = bd.getClass().getDeclaredField("points");
		pointField.setAccessible(true);
		int points = (int) pointField.get(bd);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has " + points +" points", new Object[] {bd});
		Field busField = bd.getClass().getDeclaredField("bus");
		busField.setAccessible(true);
		Bus bus = (Bus) busField.get(bd);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has [Obj]", new Object[] {bd, bus});
	}
	
	

	private void infoObject(Snowplower sp)
			throws Exception {
		infoVehicle(sp);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] owner is [Obj]", new Object[] { sp, sp.getCleaner() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has [Obj]", new Object[] { sp, sp.getHeadInventory() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has " + sp.getSalt() + " salt", new Object[] { sp });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has " + sp.getBio() + " bio", new Object[] { sp });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has " + sp.getGravel() + " gravel", new Object[] { sp });
	}

	private void infoObject(Bus b)
			throws Exception {
		infoVehicle(b);

		Field driverField = b.getClass().getDeclaredField("driver");
		driverField.setAccessible(true);
		BusDriver driver = (BusDriver) driverField.get(b);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] driver is [Obj]", new Object[]{b, driver});
		
		Field stationAField = b.getClass().getDeclaredField("stationA");
		stationAField.setAccessible(true);
		Crossing stationA = (Crossing) stationAField.get(b);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] station A is [Obj]", new Object[]{b, stationA});
		
		Field stationBField = b.getClass().getDeclaredField("stationB");
		stationBField.setAccessible(true);
		Crossing stationB = (Crossing) stationBField.get(b);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] station B is [Obj]", new Object[]{b, stationB});

		Field destField = b.getClass().getDeclaredField("isCurrentDestinationA");
		destField.setAccessible(true);
		boolean isCurrentDestinationA = (boolean) destField.get(b);
		if(isCurrentDestinationA)
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] current destination is A", new Object[]{b});
		else
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] current destination is B", new Object[] { b });

	}

	private void infoObject(Car c)
			throws Exception {
		infoVehicle(c);

		Field homeField = c.getClass().getDeclaredField("home");
		homeField.setAccessible(true);
		Crossing homeCrossing = (Crossing) homeField.get(c);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] home is [Obj]", new Object[]{c, homeCrossing});

		Field workField = c.getClass().getDeclaredField("work");
		workField.setAccessible(true);
		Crossing workCrossing = (Crossing) workField.get(c);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] work is [Obj]", new Object[]{c, workCrossing});

		Field isGoingHomeField = c.getClass().getDeclaredField("isGoingHome");
		isGoingHomeField.setAccessible(true);
		boolean isGoingHome = (boolean) isGoingHomeField.get(c);
		if(isGoingHome)
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is going home", new Object[]{c});
		else
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is going to work", new Object[] { c });
	}

	private void infoObject(Path p)
			throws Exception {
		Field lanesField = p.getClass().getDeclaredField("pathLanes");
		lanesField.setAccessible(true);
		List<Lane> pathLanes = (List<Lane>) lanesField.get(p);
		if (pathLanes.isEmpty()) {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] pathLanes is empty", new Object[] { p });
		} else {
			for (Lane l : pathLanes) {
				Logger.getGlobal().log(Level.INFO, "INFO [Obj] has lane [Obj]", new Object[] { p, l });
			}
		}

		Field lastCrossingField = p.getClass().getDeclaredField("lastCrossing");
		lastCrossingField.setAccessible(true);
		Crossing lastCrossing = (Crossing) lastCrossingField.get(p);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] last crossing is [Obj]", new Object[] { p, lastCrossing });
	}

	private void infoObject(HeadListing hl) {
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] has head [Obj]", new Object[] { hl, hl.getHead() });
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] price is " + hl.getPrice(), new Object[] { hl });
	}

	private void infoObject(Head h)
			throws Exception {
		Field snowplowerField = h.getClass().getDeclaredField("snowplower");
		snowplowerField.setAccessible(true);
		Snowplower sp = (Snowplower) snowplowerField.get(h);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] snowplower is [Obj]", new Object[]{h, sp});
	}

	private void infoVehicle(Vehicle v)
			throws Exception {
		Field lastCrossingField = v.getClass().getDeclaredField("lastCrossing");
		lastCrossingField.setAccessible(true);
		Crossing lastCrossing = (Crossing) lastCrossingField.get(v);
		Logger.getGlobal().log(Level.INFO, "[Obj] last crossing is [Obj]", new Object[] { v, lastCrossing });
		Field currentLaneField = v.getClass().getDeclaredField("currentLane");
		currentLaneField.setAccessible(true);
		Lane currentLane = (Lane) currentLaneField.get(v);
		Logger.getGlobal().log(Level.INFO, "[Obj] current lane is [Obj]", new Object[] { v, currentLane });

		Field lanePrField = v.getClass().getDeclaredField("laneProgress");
		lanePrField.setAccessible(true);
		double laneProgress = (double) lanePrField.get(v);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] lane progress is " + laneProgress, new Object[] { v });

		if (v.isStuck()) {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is stuck", new Object[] { v });
		} else {
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is not stuck", new Object[] { v });
		}

		Field crashedField = v.getClass().getDeclaredField("crashed");
		crashedField.setAccessible(true);
		boolean crashed = (boolean) crashedField.get(v);
		if (crashed)
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is crashed", new Object[] { v });
		else
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] is not crashed", new Object[] { v });

		Field reviveField = v.getClass().getDeclaredField("reviveTime");
		reviveField.setAccessible(true);
		int reviveTime = (int) reviveField.get(v);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] revive timer is " + reviveTime, new Object[] { v });

	}
	private void infoObject(HeadInventory hi)throws Exception{
		Field ownerField = hi.getClass().getDeclaredField("snowplower");
		ownerField.setAccessible(true);
		Snowplower owner = (Snowplower) ownerField.get(hi);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] owner is [Obj]", new Object[] {hi, owner});
		
		Field headsField = hi.getClass().getDeclaredField("heads");
		headsField.setAccessible(true);
		List<Head> heads = (List<Head>) headsField.get(hi);
		for(Head h : heads){
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has head [Obj]", new Object[] {hi, h});
		}

		for(HeadListing hl : hi.getShop()){
			Logger.getGlobal().log(Level.INFO, "INFO [Obj] has head listing [Obj]", new Object[] {hi, hl});
		}

		Logger.getGlobal().log(Level.INFO, "INFO [Obj] active head is [Obj]", new Object[] {hi, hi.getActiveHead()});
	}

	private void infoPlayer(Player p)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field nameField = p.getClass().getDeclaredField("name");
		nameField.setAccessible(true);
		String name = (String) nameField.get(p);
		Logger.getGlobal().log(Level.INFO, "INFO [Obj] name is " + name, new Object[]{p});

	}
}