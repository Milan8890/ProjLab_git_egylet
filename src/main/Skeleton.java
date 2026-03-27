package main;

import java.util.List;
import java.util.Scanner;

public class Skeleton {
	static Scanner sc = new Scanner(System.in);
	static boolean init_log;
	static int indentation;
	// TODO input list
	// TODO szekvenciaszám?

	static String stringHash(Object o) {

		return o.getClass().toString() + "#" + Integer.toString(o.hashCode() % 1000);
	}

	public static void logString(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.println(s);
	}

	public static void logStringNoBreak(String s) {
		for (int i = 0; i < indentation; i++) {
			System.out.print("\t");
		}
		System.out.print(s);
	}

	public static void logFunctionStart(Object o, String functionName, List<String> params){

		String params_to_string = "";
		if (params.size() != 0) {
			for (String param : params) {
				params_to_string += param;
				params_to_string += ", ";
			}
			params_to_string.substring(0, params_to_string.length() - 2);
		}

		logString("start" + stringHash(o) + "." + functionName + "(" + params_to_string + ")");
		indentation++;
	}

	public static void logFunctionEnd(){
		indentation--;
		logString("end");
	}

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

	public static int questionValue(String q) {
		logString("[" + q + "]");
		logString("Válasz: ");

		int answer = sc.nextInt();
		return answer;
	}

	public static void initObj(Object o) {
		System.out.println(stringHash(o));
	}

	public static void createObj(Object o) {
		logStringNoBreak(stringHash(o) + "->");
		indentation++;
	}

	public static void initObjFinish() {
		indentation--;
	}

	public static void startInit() {
		// TODO kéne neki név?
		System.out.println("\t--- Initialization ---");
		indentation = 0;
	}

	public static void startUseCase(String useCaseName) {
		System.out.println("\t--- useCaseName ---");
		indentation = 0;
	}
}
