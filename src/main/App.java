package main;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.*;
import user.*;
import playground.*;
import equipment.*;

public class App {
	static HashMap<Object, String> objectMap = new HashMap<Object, String>();

	public static void main(String[] args) throws Exception {
		Logger.getGlobal().setUseParentHandlers(false);

		Handler ownHandler = new OwnHandler();
		Logger.getGlobal().addHandler(ownHandler);

		Crossing c = new Crossing();
		Crossing c2 = new Crossing();
		Road r = new Road(c, c2, 0, 0);
	}

	public static void CreateObject(Object o) {
		String classString = o.getClass().toString();
		classString = classString.substring(classString.lastIndexOf(".") + 1);
		objectMap.put(o, classString);
	}

	public static String getObjectName(Object o) {
		return objectMap.get(o);
	}
}