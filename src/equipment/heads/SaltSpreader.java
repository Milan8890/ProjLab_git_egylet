package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;

/**
 * SaltSpreader
 * Felelősség: Só elhelyezése a sávon, és az azért járó fizetés kiszámítása.
 * Elhasználja a sót a hókotróból.
 * Ősosztályok:Head
 */
public class SaltSpreader extends Head {


/**
 * Konstruktor.
 */ 
    public SaltSpreader(Snowplower snowplower) {
        super(snowplower);
    }

/**
 * az “l” sávra leszórja a sót, és visszaadja a takarítás által kifizetendő pénzt.
 * @param l a tisztítandó sáv.
 * @return a takarítás által kifizetendő pénz.
 */
    public int clean(Lane l){
        int money = 10;

        return money;
    }

/**
 * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
 * @param l a vizsgált sáv
 * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
 */
    @Override
    public boolean canEnterLane(Lane l){

        return true;
    }
}