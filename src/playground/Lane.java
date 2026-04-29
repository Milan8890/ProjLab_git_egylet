package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import entities.Vehicle;
import user.Cleaner;

/**
 * Tárolja a rajta lévő sót. Ismeri a rajta lévő autókat, ez alapján jelezni
 * tudja, ha valamelyik elakadna. Képes a rajta lévő hó és jég mennyiségének
 * változtatására a leírt folyamatok mentén.
 */
public class Lane {
	Road road;
	Set<Vehicle> vehicles = new HashSet<>();

	/**
	 * Sót reprezentáló belső osztály.
	 * <p>
	 * Olvasztja a havat és jeget, kifizeti a játékost.
	 */
	class Salt {
		Cleaner owner;

		/**
		 * Konstruktor
		 * 
		 * @param c a cleaner, akihez a só tartozik
		 */
		Salt(Cleaner c) {

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

	}

	/**
	 * kiveszi a kapott járművet a sávból
	 * 
	 * @param v A jármű, amit le fog venni a sávról.
	 */
	public void removeVehicle(Vehicle v) {

	}

	/**
	 * hozzáadja a kapott járművet a sávhoz
	 * 
	 * @param v A jármű, amit hozzá fog adni a sávhoz.
	 */
	public void addVehicle(Vehicle v) {
	}

	/**
	 * Visszaadja, van-e rajta beragadt jármű
	 * 
	 * @return van-e beakadt jármű a sávon
	 */
	public boolean hasStuckVehicle() {

	}

	/**
	 * Visszaadja, van-e rajta só
	 * 
	 * @return van-e só a sávon
	 */
	public boolean hasSalt() {

	}

	/**
	 * A kapott cleanerhez tartozó sót rak a sávra
	 * 
	 * @param c a cleaner, akinek a sója rákerül a sávra
	 */
	public void setSalt(Cleaner c) {

	}

	/**
	 * Visszaadja a jég magasságát a sávban
	 * 
	 * @return a jég magassága
	 */
	public double getIce() {

	}

	/**
	 * Visszaadja a hó magasságát a sávban
	 * 
	 * @return a hó magassága
	 */
	public double getSnow() {

	}

	/**
	 * amount mennyiségű havat ad a sávhoz
	 * 
	 * @param amount a hozzáadott hó mennyisége
	 */
	public void addSnow(double amount) {

	}

	/**
	 * hozzáadja a hó magasságát a jég magasságához és 0-ra állítja a
	 * hó magasságát
	 */
	public void trampleSnow() {

	}

	/**
	 * Visszaadja az utat amihez a sáv tartozik
	 * 
	 * @return a tartalmazó út
	 */
	public Road getRoad() {

	}

	/**
	 * Beállítja a hó magasságát 0-ra, és visszaajda, ezzel mennyi havat tüntetett
	 * el.
	 * 
	 * @return mennyi havat tüntetett el
	 */
	public double cleanSnow() {

	}

	/**
	 * Beállítja a jég magasságát 0-ra, és visszaajda, ezzel mennyi jeget tüntetett
	 * el.
	 * 
	 * @return mennyi jeget tüntetett el
	 */
	public double meltIce() {

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

	}

	/**
	 * Visszaadja a sávban lévő járműveket
	 * 
	 * @return a sávban lévő járművek halmaza.
	 */
	public Set<Vehicle> getVehicles() {

	}
}