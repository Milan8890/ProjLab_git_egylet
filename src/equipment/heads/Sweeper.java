package equipment.heads;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import playground.Road;

/**
 * Sweeper
 * <p>
 * 
 * Felelősség <br>
 * A hó arrébbsöprése a sávról, és az ezért járó fizetés kiszámítása.
 * Eltűnteti a jelenlegi sávról, és áthelyezi a mellette lévőre, a külső sávról
 * csak eltünteti.
 *
 * Ősosztályok <br>
 * Head
 */
public class Sweeper extends Head {
	private static double SNOWCLEANPAY = 0.04;
	private static double SNOWPUSHPAY = 0.02;
	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A tulajdonos hókotró.
	 */
	public Sweeper(Snowplower snowplower) {
		super(snowplower);
	}

	/**
	 * Eggyel kívülebbi sávra rakja át a havat, ha nincsen kívülebbi sáv akkor
	 * letakarítja,
	 * és visszaadja a takarítás által kifizetendő pénzt.
	 * 
	 * @param l a tisztítandó sáv.
	 * @return a takarítás által kifizetendő pénz.
	 */
	@Override
	public int clean(Lane l) {
		double snowAmount = l.getSnow();
		int payment;

		if (l.getRoad().getLanes().indexOf(l) == l.getRoad().getLanes().size() - 1) {
			l.cleanSnow();
			l.setGravel(false);

			payment = (int) (l.getRoad().getLength() * snowAmount * SNOWCLEANPAY);
		} else {
			Lane nextLane = l.getRoad().getLanes().get(l.getRoad().getLanes().indexOf(l) + 1);
			nextLane.addSnow(l.getSnow());
			l.cleanSnow();
			l.setGravel(false);
			nextLane.setGravel(true);

			payment = (int) (l.getRoad().getLength() * snowAmount * SNOWPUSHPAY);
		}

		// Ide kéne még valami ice/snowLevel elem a képletbe?
		 

		Logger.getGlobal().log(Level.INFO, "[Obj] with [Obj] cleans [Obj] for " + payment + "$", new Object[] {snowplower , this, l});

		return payment;

	}
}