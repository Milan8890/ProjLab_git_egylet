package main;

import java.io.IOError;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map.Entry;
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
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import playground.Tunnel;
import playground.Lane.Salt;
import user.BusDriver;
import user.Cleaner;
import user.Player;

public class OwnHandler extends Handler {
	// TODO nem az, csak teszt
	public HashMap<Object, String> objectMap;

	public OwnHandler(HashMap<Object, String> objmap) {
		objectMap = objmap;
	}

	@Override
	public void publish(LogRecord record) {

		String message = record.getMessage();

		if (record.getLevel() == Level.SEVERE) {
			message = "[ERROR]" + message;
		}

		Object[] args = record.getParameters();

		if (args == null) {
			System.out.println(message);
			return;
		}

		// Ha nem annyi [Obj] van a stringben, mint amennyi objektumot kapott
		if (args.length != message.split("\\[Obj\\]", -1).length - 1) {
			Logger.getGlobal()
					.severe("The amount of [Obj]s and objects passed in a logging function are not the same.");
			return;
		}

		for (Object object : args) {
			message = message.replaceFirst("\\[Obj\\]", getOrCreateObjectName(object));
		}

		System.out.println(message);
	}

	@Override
	public void flush() {
	}

	@Override
	public void close() throws SecurityException {
	}

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

	private String getTypename(Object o) {
		String classString = o.getClass().toString();
		classString = classString.substring(classString.lastIndexOf(".") + 1);
		return classString;
	}

	private String getOrCreateObjectName(Object o) {
		if (!objectMap.containsKey(o)) {
			addObject(o);
		}
		return objectMap.get(o);
	}

	private void addObject(Object obj) {
		switch (obj) {
			case Cleaner o -> createSingle(o);
			case BusDriver o -> createSingle(o);
			case Car o -> createSingle(o);
			case Crossing o -> createSingle(o);
			case Road o -> createSingle(o); // TODO Tunnel is, próba!

			case HeadInventory o -> createFromOwner(o, "snowplower");
			case Bus o -> createFromOwner(o, "driver");
			// case Salt o -> createFromOwner(o, "owner");
			case Head o -> createFromOwner(o, "snowplower"); // TODO ez OK?
			case Salt o -> createFromOwner(o, "lane"); // TODO fel kellett venni hozzá egy lane-t

			case Snowplower o -> createFromOwnerPlusID(o, "cleaner");
			case Lane o -> createFromOwnerPlusID(o, "road");
			// case HeadListing o -> createFromOwnerPlusID(o, "snowplower");
			case HeadListing o -> createName(o); // TODO
			case Path o -> createName(o);

			default ->
				Logger.getGlobal().severe("No type found for " + getTypename(obj) + " in addObject");
		}
	}

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

	// ID eleje az owner ID-je
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

	public void createName(HeadListing o) {
		String name = getTypename(o);

		Head head = (Head) forceGetField(o, "head");
		Snowplower snowplower = (Snowplower) forceGetField(head, "snowplower");
		String snowplowerName = getOrCreateObjectName(snowplower);
		String ID = snowplowerName.substring(getTypename(snowplower).length());

		objectMap.put(o, name + ID);
	}
	// public void createName(HeadInventory hi) {

	// }

	// lehet több?
	// public void createName(Head h) {

	// }

	// public void createName(Tunnel t) {

	// }

	// public void createName(Lane l) {

	// }



	public void infoCity(){
		String prefix = "INFO City has";
		for (Crossing crossing : City.getCrossings()) {
			Logger.getGlobal().log(Level.INFO, prefix + " [Obj]", new Object[] { crossing });
		}
	}
}
