package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;


/**
 * Ejector
 * Felelősség: A hó lesöprése a sávról, és az ezért járó fizetés kiszámítása.
 * Ősosztályok:Head
 */
public class Ejector extends Head {

/**
 * Konstruktor.
 */ 
    public Ejector(Snowplower snowplower) {
        super(snowplower);
    }

/**
 * az “l” sávról letakarítja a havat, visszaadja a takarítás által kifizetendő pénzt.
 * @param l a tisztítandó sáv.
 * @return a takarítás által kifizetendő pénz.
 */
    public int clean(Lane l){
        int money = 10;

        return money;
    }
}