package equipment.heads;

import java.util.List;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import main.Skeleton;

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

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public Breaker(Snowplower snowplower) {

    }

    /**
     * Az “l” sávról letakarítja a jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l) {
        
    }
}