package entities;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.World;
import playground.City;
import playground.Crossing;
import playground.Lane;
import playground.Path;

/**
 * A járművek absztrakt ősosztálya.
 * <p>
 * 
 * Felelősség <br>
 * Járművek absztrakt ősosztálya, egy alapvető viselkedést implementál, amit a
 * leszármazottak felülírhatnak:
 * Automatikusan követi az utat. Ha túl nagy lenne a hóréteg, elakad.
 * Jeges úton egy adott eséllyel megcsúszik, ezt jelzi az útnak.
 * Ütközés kezelése. Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el.
 * Letaposás végzése.
 */
public abstract class Vehicle {
	protected static final double MAX_SNOW_LEVEL = 10.0;
	protected static final double SNOW_COVER_LEVEL = 5.0;
	protected static final double ICE_DANGER_LIMIT = 5.0;
	protected static final double SLIP_CHANCE = 0.8;
	/**
	 * A legutóbbi kereszteződés, amin volt.
	 */
	Crossing lastCrossing;
	/**
	 * Az a sáv amin éppen megy.
	 */
	Lane currentLane;
	/**
	 * Éppen hol tart az úton ahol megy.
	 */
	double laneProgress;
	/**
	 * Hozzá tartozó útvonal.
	 */
	Path path;
	/**
	 * Be van-e ragadva az autó (karambol vagy hóba), lezárja a sávot.
	 */
	boolean isStuck;
	/**
	 * Az autó éppen karambolban van-e, emiatt nem záródik le a sáv.
	 */
	boolean isCrashed;
	/**
	 * Mennyi ideig nem fog még mozogni egy karambol után.
	 */
	int revTimer;

	/**
	 * Konstruktor
	 * 
	 * @param lastCrossing A legutóbbi kereszteződés, amin volt.
	 */
	public Vehicle(Crossing lastCrossing) {
		World.registerOnTick(this::onTick);
		this.lastCrossing = lastCrossing;
		this.currentLane = null;
		this.laneProgress = 0.0;
		// this.path = new Path(this);
		this.isStuck = false;
		this.isCrashed = false;
		this.revTimer = 0;
	}

	/**
	 * Minden órajelkor meghívódó függvény, kezeli a járművek mozgását.
	 * Sorban meghívja az alábbi függvényeket. Az egyes lépések igazzal
	 * térnek vissza, ha futhat tovább a többi lépés, hamissal, ha nem.
	 */
	public void onTick() {
		if (!stepFollowPath()) {
			return;
		}

		if (!stepWaitAfterCrash()) {
			return;
		}

		if (!stepWaitBecauseOfStuck()) {
			return;
		}

		if (!stepStuckInSnow()) {
			return;
		}

		if (!stepSlipOnIce()) {
			return;
		}

		stepMoveOnLane();
	}

	/**
	 * Meghívódik, ha a jármű beleütközik valamibe.
	 * 
	 * @param r Mennyi ideig marad mozgásképtelen (revTimer)
	 */
	public void crashed(int r) {
		this.isCrashed = true;
		this.revTimer = r;
	}

	/**
	 * Meghívódik, ha egy másik jármű megy neki ennek a járműnek.
	 * 
	 * @param r A várakozási idő (revTimer)
	 */
	public void crashedInto(int r) {
		this.isCrashed = true;
		this.isStuck = true;
		this.revTimer = r;
	}

	/**
	 * Megkísérel ráhajtani a következő sávra az útvonalterv alapján.
	 * 
	 * @return Igaz, ha sikerült a haladás, egyébként hamis.
	 */
	protected boolean stepFollowPath() {
		if (!isInCrossing()) {
			return true;
		}

		Lane nextLane = path.pop();

		if (nextLane == null) {
			Logger.getGlobal().log(Level.INFO, "[Obj] reached end of [Obj], stopping", new Object[] { this, path });
			return false;
		}

		if (canEnterLane(nextLane)) {
			Logger.getGlobal().log(Level.INFO, "[Obj] following [Obj], going onto [Obj]",
					new Object[] { this, path, nextLane });
			this.currentLane = nextLane;
			this.currentLane.addVehicle(this);
			this.laneProgress = 0;

			return true;

		} else {
			path.clear();

			return false;
		}
	}

	/**
	 * Az egyes járművekben definiálandó virtuális függvény. Visszaadja, hogy az
	 * adott jármű behajthat-e a sávra.
	 */
	protected boolean canEnterLane(Lane l) {
		if (l.hasStuckVehicle()) {
			Logger.getGlobal().log(Level.INFO, "[Obj] [Obj] is blocked by crash, stopping and clearing [Obj]",
					new Object[] { this, l, path });
			return false;
		}
		if (l.getSnow() > MAX_SNOW_LEVEL) {
			Logger.getGlobal().log(Level.INFO,
					"[Obj] [Obj] has too thick snow (" + l.getSnow() + "), stopping and clearing [Obj]",
					new Object[] { this, l, path });
			return false;
		}
		return true;
	}

	/**
	 * Halad a jármű az úton egy megadott mennyiséget. Ha ezzel az út végére ér,
	 * akkor meghívja a reachedCrossing() metódust.
	 */
	protected void stepMoveOnLane() {
		double speed = 1.0;
		this.laneProgress += speed;

		Logger.getGlobal().log(Level.INFO, "[Obj] advancing on road, current progress is " + laneProgress,
				new Object[] { this });

		if (this.laneProgress >= this.currentLane.getRoad().getLength()) {
			reachedCrossing();
		}
	}

	/**
	 * Kezeli a kereszteződés elérését.
	 */
	protected void reachedCrossing() {
		this.lastCrossing = this.currentLane.getRoad().getToCrossing();

		Logger.getGlobal().log(Level.INFO, "[Obj] reached end of road, currently in [Obj]",
				new Object[] { this, lastCrossing });

		this.currentLane.removeVehicle(this);

		this.currentLane = null;
		this.laneProgress = 0.0;
	}

	/**
	 * Visszaadja, hogy el van-e akadva.
	 */
	public boolean isStuck() {
		if (isStuck) {
			Logger.getGlobal().log(Level.INFO, "[Obj] is stuck", this);
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] is not stuck", this);
		}
		return isStuck;
	}

	/**
	 * Visszaadja, hogy a jármű jelenleg kereszteződésben várakozik-e.
	 * <p>
	 * Ha a járműnek nincs kijelölt sávja (currentLane értéke null),
	 * akkor egy kereszteződésben tartózkodik.
	 * * @return true, ha kereszteződésben van, egyébként false.
	 */
	public boolean isInCrossing() {
		boolean val = currentLane == null;
		if (val) {
			Logger.getGlobal().log(Level.INFO, "[Obj] is in a crossing", this);
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] is not in a crossing", this);
		}
		return val;
	}

	/**
	 * Hozzáadja a path-hez az “l” sávot.
	 * <p>
	 * A jármű a feladatot továbbítja a saját Path objektumának,
	 * amely elvégzi a sáv tényleges hozzáadását.
	 * 
	 * @param l Az új sáv (Lane), amivel bővíteni szeretnénk az útvonalat.
	 */
	public boolean extendPath(Lane l) {
		boolean success = path.extendPath(l);
		if (success) {
			Logger.getGlobal().log(Level.INFO, "[Obj] extended its path with [Obj] successfully",
					new Object[] { this, l });
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] couldn't extend [Obj] with [Obj], clearing it",
					new Object[] { this, l });

		}
		return path.extendPath(l);
	}

	// TODO javadoc
	public Crossing getLastCrossing() {
		return lastCrossing;
	}

	/**
	 * Kezeli az ütközés utáni kényszerpihenőt.
	 * 
	 * @return Igaz, ha a jármű újra mozgásképes, egyébként hamis.
	 */
	protected boolean stepWaitAfterCrash() {
		if (isCrashed) {
			revTimer--;
			if (revTimer <= 0) {
				Logger.getGlobal().log(Level.INFO, "[Obj] revTime expired, unstucking vehicle", new Object[] { this });
				revive();
				isCrashed = false;
				return true;
			}
			Logger.getGlobal().log(Level.INFO, "[Obj] is currently stuck, revTime decreased to " + revTimer,
					new Object[] { this });
			return false;
		}
		return true;
	}

	/**
	 * A baleset utáni felépülés pillanatában végrehajtandó feladatok.
	 * Alapértelmezetten üres, a leszármazottak (pl. Car) definiálják felül.
	 */
	protected void revive() {
	}

	/**
	 * Ha a sávon van más jármű ami eltorlaszolja az utat, akkor false-al, különben
	 * true-val tér vissza.
	 */
	protected boolean stepWaitBecauseOfStuck() {
		if (isInCrossing()) {
			return true;
		}
		if (currentLane.hasStuckVehicle()) {
			Logger.getGlobal().log(Level.INFO, "[Obj] [Obj] is blocked by crash, stopping and clearing [Obj]",
					new Object[] { this, currentLane, path });
			return false;
		}
		return true;
	}

	/**
	 * Ha túl magas a hó abban a sávban amin van, akkor megnézi hogy van-e az úton
	 * másik sáv ahol nem túl mély a hó.
	 * Ha van, akkor átmegy rá. Ha nincs, akkor a Stuck állapotot beállítja true
	 * értékre, és visszatér false-val.
	 * Ha sikeresen tud tovább haladni, visszatér true-val.
	 */
	protected boolean stepStuckInSnow() {
		if (currentLane.getSnow() <= MAX_SNOW_LEVEL) {
			this.isStuck = false;
			return true;
		}

		Logger.getGlobal().log(Level.INFO, "[Obj] snow is too deep in [Obj], checking other lanes",
				new Object[] { this, currentLane });

		List<Lane> allLanes = currentLane.getRoad().getLanes();

		for (int i = 0; i < allLanes.size(); i++) {
			Lane l = allLanes.get(i);

			if (l != currentLane && l.getSnow() <= MAX_SNOW_LEVEL) {
				Logger.getGlobal().log(Level.INFO, "[Obj] switching to [Obj]", new Object[] { this, l });
				currentLane.removeVehicle(this);
				this.currentLane = l;
				this.currentLane.addVehicle(this);

				this.isStuck = false;
				return true;
			}
		}

		Logger.getGlobal().log(Level.INFO, "[Obj] no other suitable lane found, stuck in snow", new Object[] { this });
		this.isStuck = true;
		return false;
	}

	/**
	 * Ha a sávon amin halad a jármű egy határérték felett van a jég mennyisége,
	 * és azt nem védi zuzalék, akkor egy megadott eséllyel meghívja az út
	 * crashVehicle() függvényét paraméterül megadva önmagát.
	 * Ez után ellenőrzi azt, hogy ütköztek-e belé. Ha igen, false-al tér vissza, ha
	 * nem, true-val.
	 */
	protected boolean stepSlipOnIce() {
		if (currentLane.getIce() > ICE_DANGER_LIMIT) {
			Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: ice is thick enough",
					new Object[] { this, currentLane });

			if (currentLane.hasGravel() && currentLane.getSnow() <= SNOW_COVER_LEVEL) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: has gravel",
						new Object[] { this, currentLane });
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: snow is thin enough, not slipping",
						new Object[] { this, currentLane });
				return true;
			} else if (currentLane.hasGravel() && currentLane.getSnow() > SNOW_COVER_LEVEL) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: has gravel",
						new Object[] { this, currentLane });
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: snow is too thick for gravel, slipping",
						new Object[] { this, currentLane });
			} else {
				Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: doesn’t have gravel, slipping",
						new Object[] { this, currentLane });
			}

			// TODO: Random hívás központosítása a tesztelhetőség miatt
			if (Math.random() < SLIP_CHANCE) {
				Logger.getGlobal().log(Level.INFO, "[Obj] slipping on [Obj]",
						new Object[] { this, currentLane.getRoad() });
				currentLane.getRoad().crashVehicle(this);

				if (this.isCrashed) {
					return false;
				}
			}
		} else {
			Logger.getGlobal().log(Level.INFO, "[Obj] slip check on [Obj]: ice is too thin, not slipping",
					new Object[] { this, currentLane });
		}

		return true;
	}
}