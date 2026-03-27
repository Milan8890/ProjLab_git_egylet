package equipment;

import entities.Snowplower;
import playground.Lane;

/**
 * A hókotró fejét reprezentáló absztrakt osztály.
 * <p> 
 *
 * Felelősség <br>
 * Típus szerint a sáv tisztítása, az ezért járó pénz kiszámítása, és a sávra befordulás jogának ellenőrzése.
 * A fejek ismerik a hókotrót, amelyhez tartoznak, így tudják fogyasztani annak alapanyagait.
 */
public abstract class Head {

    /**
     * A hókotró, akihez ez a fej tartozik.
     */
    private Snowplower snowplower;

    /**
     * Konstruktor.
     * 
     * @param snowplower A hókotró, amelyhez a fej kapcsolódik.
     */
    public Head(Snowplower snowplower) {
        this.snowplower = snowplower;
    }

    /**
     * Típus szerint a sáv tisztítása, ezért járó pénz kiszámítása.
     * Absztrakt metódus, itt  nincs implementálva.
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

        System.out.println("Can enter: " + true);
        return true;
    }
}