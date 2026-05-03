package equipment.heads;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.Head;
import playground.City;
import playground.Lane;

/**
 * Breaker
 * <p>
 * 
 * Felelősség: <br>
 * A sávon lévő jég feltörése, és az ezért járó fizetés kiszámítása.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Breaker extends Head {
	private static double ICEBREAKPAY = 0.05;
	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A tulajdonos hókotró.
	 */
	public Breaker(Snowplower snowplower) {
		super(snowplower);
	}

	/**
	 * Az “l” sávról letakarítja a jeget, visszaadja a takarítás által kifizetendő
	 * pénzt.
	 * 
	 * @param l a tisztítandó sáv.
	 * @return a takarítás által kifizetendő pénz.
	 */
	@Override
	public int clean(Lane l) {
		double iceAmount = l.breakIce();

		double payment = iceAmount * l.getRoad().getLength() * ICEBREAKPAY;

		Logger.getGlobal().log(Level.INFO, "[Obj] with [Obj] cleans [Obj] for " + payment + "$" , new Object[] {snowplower , this, l});
		return (int) payment; // 0.5 szorzó van a tesztben is.
	}
}