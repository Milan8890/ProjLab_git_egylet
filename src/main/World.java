package main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import user.Player;

/**
 * A világ tulajdonságai, modellhez szorosabban illeszkedő beállítások
 */
public class World {
	/**
	 * A játékban lévő játékosok
	 */
	public static Set<Player> players = new HashSet<>();

	/**
	 * Az idő múlásával meghívandó onTick függvények
	 */
	static List<Runnable> onTickFunctions = new ArrayList<>();

	/**
	 * Havazás állíthatósága
	 */
	static boolean isSnowing = false;
	/**
	 * Idő számlálása
	 */
	private static int elapsedTicks = 0;

	public enum RandomMode {
		FALSE, TRUE, RANDOM
	}

	static RandomMode randomMode = RandomMode.RANDOM;

	/**
	 * Új onTick függvény feltétele
	 * 
	 * @param func az új onTick
	 */
	public static void registerOnTick(Runnable func) {
		onTickFunctions.add(func);
	}

	/**
	 * Idő múlatása (onTick-ek meghívása)
	 */
	public static void tick() {
		elapsedTicks++;
		Logger.getGlobal().info("\nStarting tick " + elapsedTicks + "\n");
		for (int i = 0; i < onTickFunctions.size(); i++) {
			onTickFunctions.get(i).run();
		}
	}

	/**
	 * Havazás lekérdezése
	 * 
	 * @return havazik-e a világban
	 */
	public static boolean isSnowing() {
		return isSnowing;
	}

	/**
	 * Havazás beállítása
	 * 
	 * @param value havazzon-e a világban
	 */
	public static void setIsSnowing(boolean value) {
		isSnowing = value;
	}

	/**
	 * Központilag állítható véletlenszám generátor lekérdezése.
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

	/**
	 * Központilag állítható véletlenszám generátor beállítása.
	 * 
	 * @param newMode Az új beállítás
	 */
	public static void setRandomMode(RandomMode newMode) {
		randomMode = newMode;
	}
}
