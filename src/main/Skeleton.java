package main;

import java.awt.Color;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import playground.*;
import entities.*;
import user.*;
import equipment.*;
import equipment.heads.*;

public class Skeleton {
	static Stack<Object> initStack = new Stack<>();

	static Scanner sc = new Scanner(System.in);
	static boolean init_log;
	static int indentation;
	// TODO input list
	// TODO szekvenciaszám?
	// TODO INIT KIKAPCS

			
	// visszaadja egy objektum "nevét", az objektumot kell beleadni
	public static String createNameOfObject(Object o) {
		// TODO class lecsippantás
		String temp = o.getClass().toString();
		temp = temp.substring(temp.lastIndexOf(".") + 1);

		return temp + "#" + Integer.toString(o.hashCode() % 1000);
	}

	// egy string kiírása megfelelő behúzással
	public static void logString(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.println(s);
	}

	// egy string kiírása behúzás nélkül
	public static void logStringNoBreak(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.print(s);
	}

	// függvény elindulásakor kell hívni
	// első paraméter egy string, ha nem lehetne megadni az objektumot, ami a
	// függvényt hívta
	public static void logFunctionStart(String s, String functionName, List<String> params) {
		String params_to_string = "";
		if (params != null && params.size() != 0) {
			for (String param : params) {
				params_to_string += param;
				params_to_string += ", ";
			}
			params_to_string = params_to_string.substring(0, params_to_string.length() - 2);
		}

		logString("start " + s + "." + functionName + "(" + params_to_string + ")");
		indentation++;
	}

	// függvény elindulásakor kell hívni
	// első Object paraméter `this`, hogy ki tudjuk írni melyik osztály hívta a
	// függvényt
	public static void logFunctionStart(Object o, String functionName, List<String> params) {

		String params_to_string = "";
		if (params != null && params.size() != 0) {
			for (String param : params) {
				params_to_string += param;
				params_to_string += ", ";
			}
			params_to_string = params_to_string.substring(0, params_to_string.length() - 2);
		}

		logString("start " + createNameOfObject(o) + "." + functionName + "(" + params_to_string + ")");
		indentation++;
	}

	// függvény hívásának végekor kell hívni
	public static void logFunctionEnd() {
		indentation--;
		logString("end");
	}

	// multiple choice kérdés feltevése
	// első paraméter a kérdés, második egy lista a választási lehetőségekről
	public static int questionMultiple(String q, List<String> options) {
		logString("[" + q + "]");
		for (int i = 0; i < options.size(); i++) {
			logString((i + 1) + "." + options.get(i));
		}
		logStringNoBreak("Válasz: ");
		int answer = sc.nextInt();

		if (answer > options.size()) {
			answer = options.size();
		}
		if (answer < 1) {
			answer = 1;
		}

		return answer;
	}

	// érték kérdés feltevése
	public static int questionValue(String q) {
		logString("[" + q + "]");
		logStringNoBreak("Válasz: ");

		int answer = sc.nextInt();
		return answer;
	}

	// konstuktor elején kell meghívni
	public static void initObj(Object o) {
		System.out.println(createNameOfObject(o));
	}

	// új objektum létrehozásakor kell meghívni
	// pl amikor a város létrehozza az utakat, a város meghívja ezt a függvény,
	// magát beadva
	@Deprecated
	public static void createObj(Object o) {
		logStringNoBreak(createNameOfObject(o) + "->");
		indentation++;
	}

	// Objektum konstruktorjának végén kell meghívni
	@Deprecated
	public static void initObjFinish() {
		indentation--;
	}

	// teszteset függvényének elején, az inicializálás kezdetekor kell meghívni
	public static void startInit() {
		// TODO kéne neki név?
		System.out.println("\t--- Initialization ---");
		indentation = 0;
	}

	// tesztesetben inicializálás után, a valós teszt kezdése előtt kell meghívni
	public static void startUseCase(String useCaseName) {
		System.out.println("\t--- " + useCaseName + " ---");
		indentation = 0;
	}

	// új függvény a régi init logging helyett
	// minden konstruktor elején kell meghívni (csak ott)
	public static void initSettingUpObjectStart(Object o) {
		for (int i = 0; i < initStack.size(); i++) {
			System.out.print("\t");
		}
		if (!initStack.isEmpty()) {
			System.out.print(createNameOfObject(initStack.peek()) + "->");
		}
		System.out.println(createNameOfObject(o));
		initStack.push(o);
	}

	// új függvény a régi init logging helyett
	// konstruktor végén/objektum teljes beállítása végén kell meghívni
	public static void initSettingUpObjectEnd() {
		initStack.pop();
	}





//--------------- HA KELL VALAMI INNEN KÉRD LE -------------------
	public static class Market{
		static public City city = new City();
		static public Crossing crossing = new Crossing();
		static public Road road = new Road(crossing, crossing, 1, 1);
		static public Tunnel tunnel = new Tunnel(crossing, crossing, 1, 1);
		static public Lane lane = new Lane(road);

		static public Cleaner cleaner = new Cleaner("a", Color.RED);
		static public BusDriver busDriver = new BusDriver("b", Color.BLUE);


		static public Bus bus = new Bus(crossing, crossing, busDriver);
		static public Snowplower snowplower = Snowplower.createWithBreaker(cleaner, crossing);
		static public Car car = new Car(crossing, crossing);

		static public Ejector ejector = new Ejector(snowplower);
		static public Breaker breaker = new Breaker(snowplower);
		static public Sweeper sweeper = new Sweeper(snowplower);
		static public SaltSpreader	saltSpreader = new SaltSpreader(snowplower);
		static public Dragon dragon = new Dragon(snowplower);


		static public HeadInventory headInventory = HeadInventory.createWithBreaker(snowplower);
		static public HeadListing breakerHeadListing = new HeadListing(breaker, 1);
		static public HeadListing sweeperHeadListing = new HeadListing(sweeper, 1);
		static public HeadListing ejectorHeadListing = new HeadListing(ejector, 1);
		static public HeadListing saltSpreaderHeadListing = new HeadListing(saltSpreader, 1);
		static public HeadListing dragonHeadListing = new HeadListing(dragon, 1);

		//IDE TALÁN MAJD CTOR CUCC
		static public Path path = new Path();
	}
}
