package equipment.heads;

import user.Cleaner;
import java.util.List;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import playground.Road;

/**
 * SaltSpreader
 * <p>
 * 
 * Felelősség <br>
 * Só elhelyezése a sávon, és az azért járó fizetés kiszámítása.
 * Elhasználja a sót a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class SaltSpreader extends Head {

	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A tulajdonos hókotró.
	 */
	public SaltSpreader(Snowplower snowplower) {
		super(snowplower);
	}

	/**
	 * Az “l” sávra leszórja a sót, és visszaadja a takarítás által kifizetendő
	 * pénzt.
	 * 
	 * @param l a tisztítandó sáv.
	 * @return a takarítás által kifizetendő pénz.
	 */
	public int clean(Lane l) {
		l.setSalt(snowplower.getCleaner());
		double payPerMeter = 1; // Ezt kell átírni, hogy 1 méter sózásért mennyi pénz jár.

		// Ide kéne még valami ice/snowLevel elem a képletbe?
		int payment = (int) (l.getRoad().getLength() * payPerMeter);
		return payment;
	}

	/**
	 * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
	 * 
	 * @param l a vizsgált sáv.
	 * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához,
	 *         egyébként igazzal.
	 */
	@Override
	public boolean canEnterLane(Lane l) {
		if (l.hasSalt())
			return false;
		double saltConsume = 1; // Ezt kell átírni, hogy 1 méterre mennyi só kerül.
		double neededSalt = l.getRoad().getLength() * saltConsume;

		return snowplower.getSalt() >= neededSalt;
	}
}