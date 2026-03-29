package playground;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import entities.Vehicle;
import main.Skeleton;
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
			Skeleton.initSettingUpObjectStart(this);
			owner = c;
			Skeleton.initSettingUpObjectEnd();
		}

		/**
		 * óraütésen letakarít egy adag havat és jeget a sávról, és csökkenti a só
		 * élettartamát, kifizeti a játékost
		 */
		public void onTick() {
			Skeleton.logFunctionStart(this, "onTick", null);

			int meltedSnow = Skeleton.questionValue("Mennyi havat olvasztott le a só?");
			int meltedIce = Skeleton.questionValue("Mennyi jeget olvasztott le a só?");
			owner.addMoney(meltedIce + meltedSnow);

			Skeleton.logFunctionEnd();
		}
	}

	/**
	 * Konstruktor
	 * 
	 * @param r a sávot tartalmazó út.
	 */
	public Lane(Road r) {
		Skeleton.initSettingUpObjectStart(this);

		road = r;

		Skeleton.initSettingUpObjectEnd();
	}

	/**
	 * kiveszi a kapott járművet a sávból
	 * 
	 * @param v A jármű, amit le fog venni a sávról.
	 */
	public void removeVehicle(Vehicle v) {
		Skeleton.logFunctionStart(this, "removeVehicle", Arrays.asList(Skeleton.createNameOfObject(v)));
		vehicles.remove(v);
		Skeleton.logFunctionEnd();
	}

	/**
	 * hozzáadja a kapott járművet a sávhoz
	 * 
	 * @param v A jármű, amit hozzá fog adni a sávhoz.
	 */
	public void addVehicle(Vehicle v) {
		Skeleton.logFunctionStart(this, "addVehicle", Arrays.asList(Skeleton.createNameOfObject(v)));
		vehicles.add(v);
		Skeleton.logFunctionEnd();
	}

	/**
	 * Visszaadja, van-e rajta beragadt jármű
	 * 
	 * @return van-e beakadt jármű a sávon
	 */
	public boolean hasStuckVehicle() {
		Skeleton.logFunctionStart(this, "hasStuckVehicle", null);

		int ans = Skeleton.questionMultiple("Van a sávon jármű?", Arrays.asList("Igen", "Nem"));

		Skeleton.logFunctionEnd();
		return ans == 1;
	}

	/**
	 * Visszaadja, van-e rajta só
	 * 
	 * @return van-e só a sávon
	 */
	public boolean hasSalt() {
		Skeleton.logFunctionStart(this, "hasSalt", null);

		int ans = Skeleton.questionMultiple("Van a sávon só?",
				Arrays.asList("Igen", "Nem"));

		Skeleton.logFunctionEnd();
		return ans == 1;
	}

	/**
	 * A kapott cleanerhez tartozó sót rak a sávra
	 * 
	 * @param c a cleaner, akinek a sója rákerül a sávra
	 */
	public void setSalt(Cleaner c) {
		Skeleton.logFunctionStart(this, "setSalt", Arrays.asList(Skeleton.createNameOfObject(c)));

		new Salt(c);

		Skeleton.logFunctionEnd();
	}

	/**
	 * Visszaadja a jég magasságát a sávban
	 * 
	 * @return a jég magassága
	 */
	public double getIce() {
		Skeleton.logFunctionStart(this, "getIce", null);

		int answer = Skeleton.questionValue("Milyen magas a jég a sávban?");

		Skeleton.logFunctionEnd();
		return (double) answer;
	}

	/**
	 * Visszaadja a hó magasságát a sávban
	 * 
	 * @return a hó magassága
	 */
	public double getSnow() {
		Skeleton.logFunctionStart(this, "getSnow", null);

		int answer = Skeleton.questionValue("Milyen magas a hó a sávban?");

		Skeleton.logFunctionEnd();
		return (double) answer;
	}

	/**
	 * amount mennyiségű havat ad a sávhoz
	 * 
	 * @param amount a hozzáadott hó mennyisége
	 */
	public void addSnow(double amount) {
		Skeleton.logFunctionStart(this, "addSnow", Arrays.asList(Double.toString(amount)));
		Skeleton.logFunctionEnd();
	}

	/**
	 * hozzáadja a hó magasságát a jég magasságához és 0-ra állítja a
	 * hó magasságát
	 */
	public void trampleSnow() {
		Skeleton.logFunctionStart(this, "trampleSnow", null);
		Skeleton.logFunctionEnd();
	}

	/**
	 * Visszaadja az utat amihez a sáv tartozik
	 * 
	 * @return a tartalmazó út
	 */
	public Road getRoad() {
		Skeleton.logFunctionStart(this, "getRoad", null);

		Skeleton.logFunctionEnd();
		return road;
	}

	/**
	 * Beállítja a hó magasságát 0-ra, és visszaajda, ezzel mennyi havat tüntetett
	 * el.
	 * 
	 * @return mennyi havat tüntetett el
	 */
	public double cleanSnow() {
		Skeleton.logFunctionStart(this, "cleanSnow", null);
		double answer = Skeleton.questionValue("Milyen magas volt a hó a sávban takarítás előtt?");

		Skeleton.logFunctionEnd();
		return answer;
	}

	/**
	 * Beállítja a jég magasságát 0-ra, és visszaajda, ezzel mennyi jeget tüntetett
	 * el.
	 * 
	 * @return mennyi jeget tüntetett el
	 */
	public double meltIce() {
		Skeleton.logFunctionStart(this, "meltIce", null);
		double answer = Skeleton.questionValue("Milyen magas volt a jég a sávban takarítás előtt?");

		Skeleton.logFunctionEnd();
		return answer;
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
		Skeleton.logFunctionStart(this, "breakIce", null);
		int answer = Skeleton.questionValue("Milyen magas volt a jég a sávban a feltörése előtt?");

		Skeleton.logFunctionEnd();
		return (double) answer;
	}

	/**
	 * Visszaadja a sávban lévő járműveket
	 * 
	 * @return a sávban lévő járművek halmaza.
	 */
	public Set<Vehicle> getVehicles() {
		Skeleton.logFunctionStart(this, "getVehicles", null);

		Skeleton.logFunctionEnd();
		return vehicles;
	}
}