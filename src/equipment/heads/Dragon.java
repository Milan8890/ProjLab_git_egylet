package equipment.heads;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import playground.Road;

/**
 * Dragon
 * <p>
 * 
 * Felelősség: <br>
 * A jég és hó elolvasztása, és az ezért járó fizetés kiszámítása.
 * Elhasználja a biokerozint a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Dragon extends Head {

	static final double BIO_CONSUME = 1;

	static final double PAY_ICE = 0.5;
	static final double PAY_SNOW = 1;

	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A tulajdonos hókotró.
	 */
	public Dragon(Snowplower snowplower) {
		super(snowplower);
	}

	/**
	 * Az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által
	 * kifizetendő pénzt.
	 * 
	 * @param l a tisztítandó sáv.
	 * @return a takarítás által kifizetendő pénz.
	 */
	@Override
	public int clean(Lane l) {
		double iceAmount = l.meltIce();
		double snowAmount = l.cleanSnow();

		// 1 jég = 0.5 pénz, 1 hó = 1 pénz, ez van tesztben
		double payment = (iceAmount * PAY_ICE + snowAmount * PAY_SNOW) * l.getRoad().getLength(); 
		
		double amount = BIO_CONSUME * l.getRoad().getLength();
		snowplower.useBio(amount);

		Logger.getGlobal().log(Level.INFO, "[Obj] with [Obj] cleans [Obj] for " + payment + "$" , new Object[] {snowplower , this, l});
		Logger.getGlobal().log(Level.INFO, "[Obj] uses " + amount + "bio from [Obj]", new Object[] {this, snowplower});
		return (int) payment;
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

		double neededAmount = BIO_CONSUME * l.getRoad().getLength();

		if(neededAmount <= snowplower.getBio()) 
			Logger.getGlobal().log(Level.INFO, "[Obj] allows [Obj] to enter [Obj] ", new Object[] {this, snowplower, l});
		else
			Logger.getGlobal().log(Level.INFO, "[Obj] blocks [Obj] from entering [Obj] ", new Object[] {this, snowplower, l});

		return neededAmount <= snowplower.getBio();
	}

}