package main;

import java.io.IOError;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import entities.Bus;
import entities.Car;
import entities.Snowplower;
import equipment.Head;
import equipment.HeadInventory;
import equipment.HeadListing;
import playground.Crossing;
import playground.Lane;
import playground.Path;
import playground.Road;
import playground.Tunnel;
import user.BusDriver;
import user.Cleaner;
import user.Player;

public class OwnHandler extends Handler {
	// TODO nem az, csak teszt
	public HashMap<Object, String> objectMap = new HashMap<>();

	@Override
	public void publish(LogRecord record) {

		String message = record.getMessage();

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
			message = message.replaceFirst("\\[Obj\\]", getObjectName(object));
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
		try {
			Field field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(o);
		} catch (Exception e) {
			Logger.getGlobal().severe("No such field " + fieldName + " in forceGetField");
		}
		return null;
	}

	private String getTypename(Object o) {
		String classString = o.getClass().toString();
		classString = classString.substring(classString.lastIndexOf(".") + 1);
		return classString;
	}

	private String getObjectName(Object o) {
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

			case Snowplower o -> createFromOwnerPlusID(o, "cleaner");
			case Lane o -> createFromOwnerPlusID(o, "road");
			// case HeadListing o -> createFromOwnerPlusID(o, "snowplower");
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
		String ownerName = getObjectName(owner);
		String ID = ownerName.substring(getTypename(owner).length());

		objectMap.put(o, name + ID);
	}

	// ID eleje az owner ID-je
	public void createFromOwnerPlusID(Object o, String ownerFieldName) {
		String name = getTypename(o);

		Object owner = forceGetField(o, ownerFieldName);
		String ownerName = getObjectName(owner);
		String ID = ownerName.substring(getTypename(owner).length());

		int number = 1;
		String completeName = name + ID + "_" + number;
		while (objectMap.containsValue(completeName)) {
			number++;
			completeName = name + ID + "_" + number;
		}

		objectMap.put(o, completeName);
	}

	public void createName(Path b) {

	}

	public void createName(HeadInventory hi) {

	}

	public void createName(HeadListing hl) {

	}

	// lehet több?
	public void createName(Head h) {

	}

	public void createName(Tunnel t) {

	}

	public void createName(Lane l) {

	}

	// public void createName(Salt s) {

	// }

}
