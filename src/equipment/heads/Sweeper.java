package equipment.heads;

import java.util.List;
import entities.Snowplower;
import equipment.Head;
import main.Skeleton;
import playground.Lane;
import playground.Road;

/**
 * Sweeper
 * <p>
 * 
 * Felelősség <br>
 * A hó arrébbsöprése a sávról, és az ezért járó fizetés kiszámítása.
 * Eltűnteti a jelenlegi sávról, és áthelyezi a mellette lévőre.
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
       
    }

    /**
     * Eggyel kívülebbi sávra rakja át a havat, ha nincsen kívülebbi sáv akkor letakarítja,
     * és visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l){
       
    }
}