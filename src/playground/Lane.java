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
		private static final double CLEANSNOWAMOUNT = 0.1; // Ezek mennyiek legyenek?
		private static final double CLEANICEAMOUNT = 0.1;
		static final double PAY_ICE = 0.2;
		static final double PAY_SNOW = 0.3;

		Lane lane;
		/**
		 * A sót birtokló cleaner.
		 */
		Cleaner owner;
		/**
		 * A só élettartama, meddig marad a sávon
		 */
		double lifetime; // Ezt hogy kellene csökkenteni?

		private static final double STARTING_LIFETIME = 100.0;

		/**
		 * Konstruktor
		 * 
		 * @param c a cleaner, akihez a só tartozik
		 */
		Salt(Cleaner c, Lane l) {
			owner = c;
			lifetime = STARTING_LIFETIME;
			lane = l;
			Logger.getGlobal().log(Level.INFO, "[Obj] created with owner [Obj] and lifetime [Obj]",
					new Object[] { this, c, lifetime });

		}

		/**
		 * Óraütésre letakarít egy adag havat és jeget a sávról, csökkenti a
		 * lifetime-ot,
		 * és fizet a játékosnak annak függvényében hogy mennyi havat és jeget
		 * olvasztott el.
		 */
		public void onTick() {
			int payment = 0;
			if (snowLevel > CLEANSNOWAMOUNT) {
				snowLevel -= CLEANSNOWAMOUNT;
				payment += CLEANSNOWAMOUNT * road.getLength();
				Logger.getGlobal().log(Level.INFO,
						"[Obj] melted " + CLEANSNOWAMOUNT + " snow from lane, paid [Obj] " + payment + "$",
						new Object[] { this, owner });
				owner.addMoney(payment);
			}
			if (iceLevel > CLEANICEAMOUNT) {
				iceLevel -= CLEANICEAMOUNT;
				payment += CLEANICEAMOUNT * road.getLength() * 2;
				Logger.getGlobal().log(Level.INFO,
						"[Obj] melted " + CLEANICEAMOUNT + " ice from lane, paid [Obj] " + payment + "$",
						new Object[] { this, owner });
				owner.addMoney(payment);
			}
			lifetime--;
			Logger.getGlobal().log(Level.INFO, "[Obj] lifetime decreased to " + lifetime, this);

			if (lifetime <= 0) {
				salt = null;
				Logger.getGlobal().log(Level.INFO, "[Obj] expired", this);
			}
		}
	}

	/**
	 * Konstruktor
	 * 
	 * @param r a sávot tartalmazó út.
	 */
	public Lane(Road r) {
		road = r;
		Logger.getGlobal().log(Level.INFO, "Created [Obj]", this);
	}

	/**
	 * kiveszi a kapott járművet a sávból
	 * 
	 * @param v A jármű, amit le fog venni a sávról.
	 */
	public void removeVehicle(Vehicle v) {
		Logger.getGlobal().log(Level.INFO, "[Obj] removed [Obj]", new Object[] { this, v });

		vehicles.remove(v);
	}

	/**
	 * hozzáadja a kapott járművet a sávhoz
	 * 
	 * @param v A jármű, amit hozzá fog adni a sávhoz.
	 */
	public void addVehicle(Vehicle v) {
		Logger.getGlobal().log(Level.INFO, "[Obj] added [Obj]", new Object[] { this, v });
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
				Logger.getGlobal().log(Level.INFO, "[Obj] has stuck vehicle", new Object[] { this });
				return true;
			}
		}
		Logger.getGlobal().log(Level.INFO, "[Obj] has no stuck vehicle", new Object[] { this });
		return false;
	}

	/**
	 * Visszaadja, van-e rajta só
	 * 
	 * @return van-e só a sávon
	 */
	public boolean hasSalt() {

		if (salt != null) {
			Logger.getGlobal().log(Level.INFO, "[Obj] has salt", new Object[] { this });
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] has no salt", new Object[] { this });
		}

		return salt != null;
	}

	/**
	 * A kapott cleanerhez tartozó sót rak a sávra
	 * 
	 * @param c a cleaner, akinek a sója rákerül a sávra
	 */
	public void setSalt(Cleaner c) {
		salt = new Salt(c, this);
		Logger.getGlobal().log(Level.INFO, "[Obj] set [Obj] with [Obj]", new Object[] { this, salt, c });
	}

	/**
	 * Visszaadja, van-e rajta zúzott kő
	 * 
	 * @return van-e zúzott kő a sávon
	 */
	public boolean hasGravel() {
		if (hasGravel) {
			Logger.getGlobal().log(Level.INFO, "[Obj] has gravel", new Object[] { this });
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] has no gravel", new Object[] { this });
		}

		return hasGravel;
	}

	/**
	 * A zúzott követ rak a sávra
	 * 
	 * @param b logikai érték, ami jelzi, hogy a zúzott kő rákerül-e a sávra
	 */
	public void setGravel(boolean b) {
		if (b) {
			Logger.getGlobal().log(Level.INFO, "[Obj] set to have gravel", new Object[] { this });
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] set to not have gravel", new Object[] { this });
		}

		hasGravel = b;
	}

	/**
	 * Visszaadja a jég magasságát a sávban
	 * 
	 * @return a jég magassága
	 */
	public double getIce() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned ice level " + iceLevel, new Object[] { this });

		return iceLevel;
	}

	/**
	 * Visszaadja a hó magasságát a sávban
	 * 
	 * @return a hó magassága
	 */
	public double getSnow() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned snow level " + snowLevel, new Object[] { this });

		return snowLevel;
	}

	/**
	 * amount mennyiségű havat ad a sávhoz
	 * 
	 * @param amount a hozzáadott hó mennyisége
	 */
	public void addSnow(double amount) {
		Logger.getGlobal().log(Level.INFO, "[Obj] added " + amount + " snow", new Object[] { this });

		snowLevel += amount;
	}

	/**
	 * hozzáadja a hó magasságát a jég magasságához és 0-ra állítja a
	 * hó magasságát
	 */
	public void trampleSnow() {
		iceLevel += snowLevel;

		Logger.getGlobal().log(Level.INFO, "[Obj] turned " + snowLevel + " snow into ice", new Object[] { this });

		snowLevel = 0;
	}

	/**
	 * Visszaadja az utat amihez a sáv tartozik
	 * 
	 * @return a tartalmazó út
	 */
	public Road getRoad() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned [Obj]", new Object[] { this, road });

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

		Logger.getGlobal().log(Level.INFO, "[Obj] cleaned " + removedSnow + " snow", new Object[] { this });
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

		Logger.getGlobal().log(Level.INFO, "[Obj] melted " + removedIce + " ice", new Object[] { this });
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

		Logger.getGlobal().log(Level.INFO, "[Obj] turned " + iceLevel + " ice into snow", new Object[] { this });

		iceLevel = 0;
		return brokenIce;
	}

	/**
	 * Visszaadja a sávban lévő járműveket
	 * 
	 * @return a sávban lévő járművek halmaza.
	 */
	public Set<Vehicle> getVehicles() {
		Logger.getGlobal().log(Level.INFO, "[Obj] returned vehicles", new Object[] { this });

		return vehicles;
	}
}