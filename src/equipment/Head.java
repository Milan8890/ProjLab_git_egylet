package equipment;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import entities.Snowplower;
import playground.Lane;

/**
 * A hókotró fejét reprezentáló absztrakt osztály.
 * <p>
 *
 * Felelősség <br>
 * Tisztítófejek őseként szolgáló absztrakt osztály.
 * Típus szerint a sáv tisztítása, ezért járó pénz kiszámítása, és a sávra
 * ráhajtás engedélyezése/tiltása.
 * A fejek ismerik a hókotrót, amelyhez tartoznak, így tudják fogyasztani annak
 * alapanyagait.
 */
public abstract class Head {

	/**
	 * A hókotró, akihez ez a fej tartozik.
	 */
	protected Snowplower snowplower;

	/**
	 * Konstruktor.
	 * 
	 * @param snowplower A hókotró, amelyhez a fej kapcsolódik.
	 */
	public Head(Snowplower snowplower) {
		this.snowplower = snowplower;
		Logger.getGlobal().log(Level.INFO, "[Obj] created", this);
	}

	/**
	 * Fej típustól függ, a visszaadott pénzérték függ az eltakarított hó és jég
	 * vastagságától,
	 * valamint a sáv útjának hosszától.
	 * Absztrakt metódus, itt nincs implementálva.
	 * 
	 * @param l A tisztítandó sáv (Lane).
	 */
	public abstract int clean(Lane l);

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * Alapvető implementációként igazzal tér vissza.
     * 
     * @param l A sáv, amire a hókotró be szeretne fordulni.
     * @return {@code true}, ha befordulhat a sávra, egyébként {@code false}. 
    */
    public boolean canEnterLane(Lane l) {
		Logger.getGlobal().log(Level.INFO, "[Obj] allows [Obj] to enter [Obj]", new Object[] {this, snowplower, l});
        return true;
    }
}
