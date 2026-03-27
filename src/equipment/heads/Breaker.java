package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;

/**
 * Breaker
 * Felelősség: A sávon lévő jég feltörése, és az ezért járó fizetés kiszámítása.
 * Ősosztályok: Head
 */
public class Breaker extends Head {

/**
 * Konstruktor.
 */ 
    public Breaker(Snowplower snowplower) {
        super(snowplower);
    }

/**
 * az “l” sávról letakarítja a jeget, visszaadja a takarítás által kifizetendő pénzt.
 * @param l a tisztítandó sáv.
 * @return a takarítás által kifizetendő pénz.
 */
    @Override
    public int clean(Lane l) {
        int money = 10;

        return money;
    }
}