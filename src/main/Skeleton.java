package main;

import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Skeleton {
	static Stack<Object> initStack = new Stack<>();

	static Scanner sc = new Scanner(System.in);
	static boolean init_log;
	static int indentation;
	// TODO input list
	// TODO szekvenciaszám?

	// visszaadja egy objektum "nevét", az objektumot kell beleadni
	public static String createNameOfObject(Object o) {
		// TODO class lecsippantás
		return o.getClass().toString() + "#" + Integer.toString(o.hashCode() % 1000);
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
		// TODO befaktor
		String params_to_string = "";
		if (params.size() != 0) {
			for (String param : params) {
				params_to_string += param;
				params_to_string += ", ";
			}
			params_to_string.substring(0, params_to_string.length() - 2);
		}

		logString("start" + s + "." + functionName + "(" + params_to_string + ")");
		indentation++;
	}

	// függvény elindulásakor kell hívni
	// első Object paraméter `this`, hogy ki tudjuk írni melyik osztály hívta a
	// függvényt
	public static void logFunctionStart(Object o, String functionName, List<String> params) {

		String params_to_string = "";
		if (params.size() != 0) {
			for (String param : params) {
				params_to_string += param;
				params_to_string += ", ";
			}
			params_to_string.substring(0, params_to_string.length() - 2);
		}

		logString("start" + createNameOfObject(o) + "." + functionName + "(" + params_to_string + ")");
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
		logString("Válasz: ");
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
		logString("Válasz: ");

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
			System.out.println(createNameOfObject(initStack.peek()) + "->");
		}
		System.out.println(createNameOfObject(o));
		initStack.push(o);
	}

	// új függvény a régi init logging helyett
	// konstruktor végén/objektum teljes beállítása végén kell meghívni
	public static void initSettingUpObjectEnd() {
		initStack.pop();
	}
}
