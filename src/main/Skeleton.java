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

/**
 * A fejlesztés ezen fázisában a program irányítására használt osztály.<br>
 * Kezeli a logolást, eltárolja a közös objektumokat teszteléshez, és kezeli a
 * felhasználó és modell közötti kapcsolatot.
 */
public class Skeleton {
	static Stack<Object> initStack = new Stack<>();

	static Scanner sc = new Scanner(System.in);
	static boolean init_log = true;
	static int indentation;

	static Stack<Integer> answerStack = new Stack<>();

	/**
	 * Visszaadja az objektum "olvasható nevét"
	 * 
	 * @param o Az objektum
	 * @return Az objektum olvasható neve, mint string
	 */
	public static String createNameOfObject(Object o) {
		String temp = o.getClass().toString();
		temp = temp.substring(temp.lastIndexOf(".") + 1);

		return temp + "#" + Integer.toString(o.hashCode() % 1000);
	}

	/**
	 * Logol egy stringet a megfelelő behúzással.
	 * 
	 * @param s A string
	 */
	public static void logString(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.println(s);
	}

	/**
	 * Logol egy stringet a megfelelő behúzással, sortörés nélkül.
	 * 
	 * @param s A string
	 */
	public static void logStringNoBreak(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.print(s);
	}

	/**
	 * Logolja egy függvény kezdetét.<br>
	 * Ez a verzió egy stringet kér be az osztály helyett, így statikus függvényeket
	 * is lehet logolni.
	 * 
	 * @param s            A hívó osztály neve
	 * @param functionName A függvény neve
	 * @param params       A függvény paraméterei (értékei stringként)
	 */
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

	/**
	 * Logolja egy függvény kezdetét.
	 * 
	 * @param o            A hívó osztály
	 * @param functionName A függvény neve
	 * @param params       A függvény paraméterei (értékei stringként)
	 */
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

	/**
	 * Logolja egy függvény végét.
	 */
	public static void logFunctionEnd() {
		indentation--;
		logString("end");
	}

	/**
	 * Feltesz a felhasználónak egy több választási lehetőségből álló kérdést.
	 * 
	 * @param q       A kérdés
	 * @param options A választási lehetőségek
	 * @return A válasz sorszáma (1-től kezdve)
	 */
	public static int questionMultiple(String q, List<String> options) {
        logString("[" + q + "]");
        for (int i = 0; i < options.size(); i++) {
            logString((i + 1) + "." + options.get(i));
        }
        logStringNoBreak("Válasz: ");
        int answer;

        if (answerStack.isEmpty()) {
            answer = sc.nextInt();
        } else {
            answer = answerStack.pop();
            boolean fromUser = false;
            if (answer == -1) {
                answer = sc.nextInt();
                fromUser = true;
            }
            if (answer > options.size()) {
                answer = options.size();
            }
            if (answer < 1) {
                answer = 1;
            }

            if (!fromUser) {
                System.out.println(options.get(answer - 1));
            }
        }

        if (answer > options.size()) {
            answer = options.size();
        }
        if (answer < 1) {
            answer = 1;
        }

        return answer;
    }

	/**
	 * Feltesz a felhasználónak egy kérdést, amire egy egész szám a válasz.
	 * 
	 * @param q A kérdés
	 * @return A válasz, mint egész szám
	 */
	public static int questionValue(String q) {
        logString("[" + q + "]");
        logStringNoBreak("Válasz: ");

        int answer;
        if (answerStack.isEmpty()) {
            answer = sc.nextInt();
        } else {
            answer = answerStack.pop();
            boolean fromUser = false;
            if (answer == -1) {
                answer = sc.nextInt();
                fromUser = true;
            }
            if (!fromUser) {
                System.out.println(answer);
            }
        }

        return answer;
    }

	/**
	 * Tesztesetek elején kell hívni. Újra felépíti a közös objektumokat.
	 */
	public static void startInit() {
		System.out.println("\t--- Initialization ---");
		init_log = true;
		Market.resetMarket();
		indentation = 0;
	}

	/**
	 * Az inicializálás után, a UseCase-ek kezdetén kell hívni.
	 * 
	 * @param useCaseName A UseCase neve.
	 */
	public static void startUseCase(String useCaseName) {
		System.out.println("\t--- " + useCaseName + " ---");
		init_log = false;
		indentation = 0;
	}

	/**
	 * Objektumok felállításának elején kell hívni (általában konstruktor elején).
	 * 
	 * @param o Az objektum
	 */
	public static void initSettingUpObjectStart(Object o) {

		for (int i = 0; i < initStack.size(); i++) {
			if (init_log)
				System.out.print("\t");
		}
		if (!initStack.isEmpty()) {
			if (init_log)
				System.out.print(createNameOfObject(initStack.peek()) + "->");
		}
		if (init_log)
			System.out.println(createNameOfObject(o));
		initStack.push(o);
	}

	/**
	 * Objektum felállításának a végén kell hívni. Gyakran nem a konstruktor végén.
	 */
	public static void initSettingUpObjectEnd() {
		initStack.pop();
	}

	/**
	 * Beállít egy listát, amivel automatikusan válaszol a feltett kérdésekre.<br>
	 * A "-1"-es érték azt jelenti, hogy nem használ automatikus válaszolást.
	 * 
	 * @param inputAnswerList A válaszok listája.
	 */
	public static void setAnswerStack(List<Integer> inputAnswerList) {
		answerStack.clear();
		answerStack.addAll(inputAnswerList.reversed());
	}

	/**
	 * Törli a beállított válaszokat.
	 */
	public static void clearAnswerStack() {
		answerStack.clear();
	}

	// --------------- HA KELL VALAMI INNEN KÉRD LE -------------------
	/**
	 * Közös objektumok tára.
	 */
	public static class Market {
		static public Crossing crossing = new Crossing();
		static public Crossing crossing2 = new Crossing();
		static public Road road = new Road(crossing, crossing2, 1, 1);
		static public Road road2 = new Road(crossing2, crossing, 2, 1);
		static public Tunnel tunnel = new Tunnel(crossing, crossing, 1, 1);

		static public Lane lane = new Lane(road);
		static public Lane lane2 = new Lane(road);

		static public Cleaner cleaner = new Cleaner("a", Color.RED);
		static public BusDriver busDriver = new BusDriver("b", Color.BLUE);

		static public Bus bus = new Bus(crossing, crossing2, busDriver);
		static public Bus bus2 = new Bus(crossing2, crossing, busDriver);
		static public Snowplower snowplower = Snowplower.createWithBreaker(cleaner, crossing);
		static public Snowplower snowplower2 = Snowplower.createWithBreaker(cleaner, crossing2);
		static public Car car = new Car(crossing, crossing2);
		static public Car car2 = new Car(crossing2, crossing);

		static public Ejector ejector = new Ejector(snowplower);
		static public Breaker breaker = new Breaker(snowplower);
		static public Sweeper sweeper = new Sweeper(snowplower);
		static public SaltSpreader saltSpreader = new SaltSpreader(snowplower);
		static public Dragon dragon = new Dragon(snowplower);

		static public HeadInventory headInventory = HeadInventory.createWithBreaker(snowplower);
		static public HeadListing breakerHeadListing = new HeadListing(breaker, 1);
		static public HeadListing sweeperHeadListing = new HeadListing(sweeper, 1);
		static public HeadListing ejectorHeadListing = new HeadListing(ejector, 1);
		static public HeadListing saltSpreaderHeadListing = new HeadListing(saltSpreader, 1);
		static public HeadListing dragonHeadListing = new HeadListing(dragon, 1);

		static public Path path = new Path();

		/**
		 * Közös objektumok újraépítése.
		 */
		public static void resetMarket() {
			crossing = new Crossing();
			crossing2 = new Crossing();
			road = new Road(crossing, crossing2, 1, 1);
			road2 = new Road(crossing2, crossing, 2, 1);
			tunnel = new Tunnel(crossing, crossing, 1, 1);
			lane = new Lane(road);
			lane2 = new Lane(road);
			cleaner = new Cleaner("a", Color.RED);
			busDriver = new BusDriver("b", Color.BLUE);
			bus = new Bus(crossing, crossing2, busDriver);
			bus2 = new Bus(crossing2, crossing, busDriver);
			snowplower = Snowplower.createWithBreaker(cleaner, crossing);
			snowplower2 = Snowplower.createWithBreaker(cleaner, crossing2);
			car = new Car(crossing, crossing2);
			car2 = new Car(crossing2, crossing);
			ejector = new Ejector(snowplower);
			breaker = new Breaker(snowplower);
			sweeper = new Sweeper(snowplower);
			saltSpreader = new SaltSpreader(snowplower);
			dragon = new Dragon(snowplower);
			headInventory = HeadInventory.createWithBreaker(snowplower);
			breakerHeadListing = new HeadListing(breaker, 1);
			sweeperHeadListing = new HeadListing(sweeper, 1);
			ejectorHeadListing = new HeadListing(ejector, 1);
			saltSpreaderHeadListing = new HeadListing(saltSpreader, 1);
			dragonHeadListing = new HeadListing(dragon, 1);
			path = new Path();
		}
	}
}