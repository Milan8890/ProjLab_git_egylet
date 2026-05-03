package main;

import java.util.ArrayList;
import java.util.List;

public class World {
	static List<Runnable> onTickFunctions = new ArrayList<>();
	static boolean isSnowing = false;

	public enum RandomMode {
		FALSE, TRUE, RANDOM
	}

	static RandomMode randomMode = RandomMode.RANDOM;

	public static void registerOnTick(Runnable func) {
		onTickFunctions.add(func);
	}

	public static void tick() {
		for (int i = 0; i < onTickFunctions.size(); i++) {
			onTickFunctions.get(i).run();
		}
	}

	public static boolean isSnowing() {
		return isSnowing;
	}

	public static void setIsSnowing(boolean value) {
		isSnowing = value;
	}

	/**
	 * Központilag állítható véletlenszám generátor.
	 * 
	 * @param chance Annak az esélye, hogy a visszatérési érték true.
	 * @return Az esemény kimenetele
	 */
	public static boolean getRandom(double chance) {
		return switch (randomMode) {
			case TRUE -> true;
			case FALSE -> false;
			case RANDOM -> Math.random() <= chance;
		};
	}

	public static void setRandomMode(RandomMode newMode) {
		randomMode = newMode;
	}
}
