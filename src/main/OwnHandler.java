package main;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import entities.Bus;
import entities.Car;
import entities.Snowplower;
import equipment.Head;
import equipment.HeadInventory;
import equipment.HeadListing;
import equipment.heads.Breaker;
import equipment.heads.Dragon;
import equipment.heads.Ejector;
import equipment.heads.GravelSpreader;
import equipment.heads.SaltSpreader;
import equipment.heads.Sweeper;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import playground.Lane.Salt;
import user.BusDriver;
import user.Cleaner;

/**
 * A globális loggernek küldött üzeneteket elkapó, majd azokat kiírató Handler.
 * <p>
 * Itt történik az objektumok számozása. Az egyes üzenetekben [Obj]-vel kell
 * jelölni a helyettesítendő részeket.
 * 
 */
public class OwnHandler extends Handler {
	// Objektumok tára a Proto-ból
	public HashMap<Object, String> objectMap;

	public OwnHandler(HashMap<Object, String> objmap) {
		objectMap = objmap;
	}

	// A log üzenetek feldolgozása
	@Override
	public void publish(LogRecord record) {

		String message = record.getMessage();

		// Ha ilyen szintű hibaüzenetet külön jelöljük, és a tesztek hibásak, ha van
		// bennük ilyen.
		if (record.getLevel() == Level.SEVERE) {
			message = "[ERROR]" + message;
		}

		Object[] args = record.getParameters();

		if (args == null) {
			System.out.println(message);
			return;
		}

		// Ha nem annyi [Obj] van a stringben, mint amennyi objektumot kapott, jelezze.
		if (args.length != message.split("\\[Obj\\]", -1).length - 1) {
			Logger.getGlobal()
					.severe("The amount of [Obj]s and objects passed in a logging function are not the same.");
			return;
		}

		// Egyes objektumok nevének kitöltése az üzenetekben
		for (Object object : args) {
			message = message.replaceFirst("\\[Obj\\]", getOrCreateObjectName(object));
		}

		// üzenet kiírása
		System.out.println(message);
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

	// Erővel, privát mező lekérdezése. Rekurzíz, hogy leszármazott típusokkal is
	// működjön.
	private Object forceGetField(Object o, String fieldName) {
		Class<?> currentClass = o.getClass();
		while (currentClass != null) {
			try {
				Field field = currentClass.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field.get(o);
			} catch (Exception e) {
				currentClass = currentClass.getSuperclass();
			}
		}

		Logger.getGlobal().severe("No such field " + fieldName + " in forceGetField");
		return null;
	}

	// Objektum típusneve alapján a név lekérése.
	private String getTypename(Object o) {
		String classString = o.getClass().toString();
		classString = classString.substring(classString.lastIndexOf(".") + 1);

		// Tunnel-ek Road-ként vannak kezelve (számozás és elérésügyileg)
		if (classString.equals("Tunnel"))
			return "Road";

		return classString;
	}

	// Objektum nevének létrehozása, ha még nem lenne felvéve
	private String getOrCreateObjectName(Object o) {
		// CSAK ez a két objektum van, aminél felülírhatják egymást
		if (o.getClass() == Salt.class || o.getClass() == Path.class) {
			addObject(o);
		}
		if (!objectMap.containsKey(o)) {
			addObject(o);
		}
		return objectMap.get(o);
	}

	// Objektum hozzáadása az objektumtárhoz
	private void addObject(Object obj) {
		switch (obj) {
			case Cleaner o -> createSingle(o);
			case BusDriver o -> createSingle(o);
			case Car o -> createSingle(o);
			case Crossing o -> createSingle(o);
			case Road o -> createSingle(o);

			case HeadInventory o -> createFromOwner(o, "snowplower");
			case Bus o -> createFromOwner(o, "driver");
			case Head o -> createFromOwner(o, "snowplower");

			case Snowplower o -> createFromOwnerPlusID(o, "cleaner");
			case Lane o -> createFromOwnerPlusID(o, "road");

			case HeadListing o -> createName(o);
			case Path o -> createName(o);
			case Salt o -> createName(o);

			default ->
				Logger.getGlobal().severe("No type found for " + getTypename(obj) + " in addObject");
		}
	}

	// ID saját számozás
	public void createSingle(Object o) {
		String name = getTypename(o);

		int number = 1;
		String completeName = name + number;
		while (objectMap.containsValue(completeName)) {
			number++;
			completeName = name + number;
		}
		objectMap.put(o, completeName);
	}

	// ID csak az ownertől függ
	public void createFromOwner(Object o, String ownerFieldName) {
		String name = getTypename(o);

		Object owner = forceGetField(o, ownerFieldName);
		String ownerName = getOrCreateObjectName(owner);
		String ID = ownerName.substring(getTypename(owner).length());

		objectMap.put(o, name + ID);
	}

	// ID eleje az owner ID-je, plusz saját számozása
	public void createFromOwnerPlusID(Object o, String ownerFieldName) {
		String name = getTypename(o);
		Object owner = forceGetField(o, ownerFieldName);
		String ownerName = getOrCreateObjectName(owner);
		String ID = ownerName.substring(getTypename(owner).length());

		int number = 1;
		String completeName = name + ID + "_" + number;
		while (objectMap.containsValue(completeName)) {
			number++;
			completeName = name + ID + "_" + number;
		}

		objectMap.put(o, completeName);
	}

	// Path egyedi névlétrehozása
	public void createName(Path o) {
		String name = getTypename(o);

		Object owner = forceGetField(o, "vehicle");
		String ownerName = getOrCreateObjectName(owner);
		String ID = ownerName.substring(getTypename(owner).length());

		String completeName = "Temporary value for Path name";
		switch (owner) {
			case Car c -> {
				completeName = name + "C_0_" + ID;
			}
			case Bus b -> {
				completeName = name + "B_0_" + ID;
			}
			case Snowplower sp -> {
				completeName = name + "S_" + ID;
			}
			default -> Logger.getGlobal().severe("Vehicle type didn't fit in createName for Path.");
		}

		objectMap.put(o, completeName);
	}

	// HeadListing egyedi névlétrehozása
	public void createName(HeadListing o) {
		String name = getTypename(o);

		Head head = (Head) forceGetField(o, "head");
		Snowplower snowplower = (Snowplower) forceGetField(head, "snowplower");
		String snowplowerName = getOrCreateObjectName(snowplower);
		String ID = snowplowerName.substring(getTypename(snowplower).length());

		String headString = switch (head) {
			case Breaker h -> "BRE";
			case Dragon h -> "DRA";
			case Ejector h -> "EJE";
			case GravelSpreader h -> "GRA";
			case SaltSpreader h -> "SAL";
			case Sweeper h -> "SWE";
			default -> "UNHANDLED";
		};
		if (headString == "UNHANDLED") {
			Logger.getGlobal().severe("Uknown head type in logging function createName for HeadListing");
		}

		objectMap.put(o, name + ID + "_" + headString);
	}

	// Só egyedi névlétrehozása
	public void createName(Salt o) {
		String name = o.getClass().toString();
		name = name.substring(name.lastIndexOf("$") + 1);

		Lane lane = (Lane) forceGetField(o, "lane");
		String laneName = getOrCreateObjectName(lane);
		String ID = laneName.substring(getTypename(lane).length());

		objectMap.put(o, name + ID);
	}
}
