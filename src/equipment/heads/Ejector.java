package equipment.heads;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.Head;
import playground.Lane;

/**
 * Ejector
 * <p>
 * 
 * Felelősség <br>
 * A hó lesöprése a sávról, és az ezért járó fizetés kiszámítása.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Ejector extends Head {

	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A tulajdonos hókotró.
	 */
	public Ejector(Snowplower snowplower) {
		super(snowplower);
	}

	/**
	 * Az “l” sávról letakarítja a havat, visszaadja a takarítás által kifizetendő
	 * pénzt.
	 * 
	 * @param l a tisztítandó sáv.
	 * @return a takarítás által kifizetendő pénz.
	 */
	@Override
	public int clean(Lane l) {
		double payPerMeter = 1; // Ezt kell átírni.

		double payment = l.cleanSnow() * l.getRoad().getLength() * payPerMeter;
		l.setGravel(false);
		Logger.getGlobal().log(Level.INFO, "[Obj] with [Obj] cleans [Obj] for " + payment + "$" , new Object[] {snowplower , this, l});
		return (int) payment;
	}
}