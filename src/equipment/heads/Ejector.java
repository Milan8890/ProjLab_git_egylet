package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;


/**
 * Ejector
 * <p>
 * 
 * Felelősség <br>
 * A hó lesöprése a sávról, és az ezért járó fizetés kiszámítása.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Ejector extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public Ejector(Snowplower snowplower) {
        super(snowplower);
    }

    /**
     * Az “l” sávról letakarítja a havat, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        int money = 10;

        return money;
    }
}