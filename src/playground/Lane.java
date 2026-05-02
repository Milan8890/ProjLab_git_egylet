package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Vehicle;
import user.Cleaner;

/**
 * A sávot reprezentáló osztály.
 * <p>
 * 
 * Felelősség <br>
 * Tárolja a rajta lévő sót. Ismeri a rajta lévő autókat, ez alapján jelezni
 * tudja, ha valamelyik elakadna. Képes a rajta lévő hó és jég mennyiségének
 * változtatására a leírt folyamatok mentén.
 */
public class Lane {
	/**
	 * A sávot tartalmazó út.
	 */
	Road road;
	/**
	 * A sávonn lévő hó magassága.
	 */
	double snowLevel = 0;
	/**
	 * A sávon lévő jég magassága.
	 */
	double iceLevel = 0;
	/**
	 * A sávban lévő járművek halmaza.
	 */
	Set<Vehicle> vehicles = new HashSet<>();
	/**
	 * A sávon lévő só.
	 */
	Salt salt = null;
	/**
	 * Van-e a sávon zúzott kő.
	 */
	boolean hasGravel = false;

	/**
	 * Sót reprezentáló belső osztály.
	 * <p>
	 * Olvasztja a havat és jeget, kifizeti a játékost.
	 */
	public class Salt {
		Lane lane;

		/**
		 * A sót birtokló cleaner.
		 */
		Cleaner owner;
		/**
		 * A só élettartama, meddig marad a sávon
		 */
		double lifetime;

		private static final double STARTING_LIFETIME = 100.0;

		/**
		 * Konstruktor
		 * 
		 * @param c a cleaner, akihez a só tartozik
		 */
		Salt(Cleaner c, Lane l) {
			owner = c;
			lane = l;
			lifetime = STARTING_LIFETIME;
		}

		/**
		 * óraütésen letakarít egy adag havat és jeget a sávról, és csökkenti a só
		 * élettartamát, kifizeti a játékost
		 */
		public void onTick() {

		}
	}

	/**
	 * Konstruktor
	 * 
	 * @param r a sávot tartalmazó út.
	 */
	public Lane(Road r) {
		road = r;
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

	/**
	 * kiveszi a kapott járművet a sávból
	 * 
	 * @param v A jármű, amit le fog venni a sávról.
	 */
	public void removeVehicle(Vehicle v) {
		vehicles.remove(v);
	}

	/**
	 * hozzáadja a kapott járművet a sávhoz
	 * 
	 * @param v A jármű, amit hozzá fog adni a sávhoz.
	 */
	public void addVehicle(Vehicle v) {
		vehicles.add(v);
	}

	/**
	 * Visszaadja, van-e rajta beragadt jármű
	 * 
	 * @return van-e beakadt jármű a sávon
	 */
	public boolean hasStuckVehicle() {
		for (Vehicle v : vehicles) {
			if (v.isStuck()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Visszaadja, van-e rajta só
	 * 
	 * @return van-e só a sávon
	 */
	public boolean hasSalt() {
		return salt != null;
	}

	/**
	 * A kapott cleanerhez tartozó sót rak a sávra
	 * 
	 * @param c a cleaner, akinek a sója rákerül a sávra
	 */
	public void setSalt(Cleaner c) {
		salt = new Salt(c, this);
	}

	/**
	 * Visszaadja, van-e rajta zúzott kő
	 * 
	 * @return van-e zúzott kő a sávon
	 */
	public boolean hasGravel() {
		return hasGravel;
	}

	/**
	 * A zúzott követ rak a sávra
	 * 
	 * @param b logikai érték, ami jelzi, hogy a zúzott kő rákerül-e a sávra
	 */
	public void setGravel(boolean b) {
		hasGravel = b;
	}

	/**
	 * Visszaadja a jég magasságát a sávban
	 * 
	 * @return a jég magassága
	 */
	public double getIce() {
		return iceLevel;
	}

	/**
	 * Visszaadja a hó magasságát a sávban
	 * 
	 * @return a hó magassága
	 */
	public double getSnow() {
		return snowLevel;
	}

	/**
	 * amount mennyiségű havat ad a sávhoz
	 * 
	 * @param amount a hozzáadott hó mennyisége
	 */
	public void addSnow(double amount) {
		snowLevel += amount;
	}

	/**
	 * hozzáadja a hó magasságát a jég magasságához és 0-ra állítja a
	 * hó magasságát
	 */
	public void trampleSnow() {
		iceLevel += snowLevel;
		snowLevel = 0;
	}

	/**
	 * Visszaadja az utat amihez a sáv tartozik
	 * 
	 * @return a tartalmazó út
	 */
	public Road getRoad() {
		return road;
	}

	/**
	 * Beállítja a hó magasságát 0-ra, és visszaajda, ezzel mennyi havat tüntetett
	 * el.
	 * 
	 * @return mennyi havat tüntetett el
	 */
	public double cleanSnow() {
		double removedSnow = snowLevel;
		snowLevel = 0;
		return removedSnow;
	}

	/**
	 * Beállítja a jég magasságát 0-ra, és visszaajda, ezzel mennyi jeget tüntetett
	 * el.
	 * 
	 * @return mennyi jeget tüntetett el
	 */
	public double meltIce() {
		double removedIce = iceLevel;
		iceLevel = 0;
		return removedIce;
	}

	/**
	 * 0-ra állítja a jégmagasságot, és az így eltüntetett jeget a hómagassághoz
	 * adja
	 * <p>
	 * visszaadja, mennyi jeget tört fel
	 * 
	 * @return mennyi jeget tört fel
	 */
	public double breakIce() {
		double brokenIce = iceLevel;
		snowLevel += iceLevel;
		iceLevel = 0;
		return brokenIce;
	}

	/**
	 * Visszaadja a sávban lévő járműveket
	 * 
	 * @return a sávban lévő járművek halmaza.
	 */
	public Set<Vehicle> getVehicles() {
		return vehicles;
	}
}