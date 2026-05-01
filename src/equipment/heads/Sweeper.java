package equipment.heads;

import java.util.List;
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
		double payPerMeter;
		double snowAmount = l.getSnow();

		if (l.getRoad().getLanes().indexOf(l) == l.getRoad().getLanes().size() - 1) {
			l.cleanSnow();
			payPerMeter = 1; // Ezt kell átírni. Más pénz, mert letolta az útról, kell ilyen?
		} else {
			Lane nextLane = l.getRoad().getLanes().get(l.getRoad().getLanes().indexOf(l) + 1);
			nextLane.addSnow(l.getSnow());
			l.cleanSnow();

			payPerMeter = 0.6; // Ezt kell átírni, Más pénz, mert csak másik sávra tolta.
		}

		// Ide kéne még valami ice/snowLevel elem a képletbe?
		int payment = (int) (l.getRoad().getLength() * snowAmount * payPerMeter);
		return payment;

	}
}