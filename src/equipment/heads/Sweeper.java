package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;

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
        super(snowplower);
    }

    /**
     * Eggyel kívülebbi sávra rakja át a havat, ha nincsen kívülebbi sáv akkor letakarítja,
     * és visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        int money = 10;

        return money;
    }
}