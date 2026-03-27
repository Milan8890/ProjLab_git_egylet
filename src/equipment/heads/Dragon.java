package equipment.heads;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;

/**
 * Dragon
 * Felelősség: A jég és hó elolvasztása, és az ezért járó fizetés kiszámítása.
 * Elhasználja a biokerozint a hókotróból.
 * Ősosztályok:Head
 */
public class Dragon extends Head {

/**
 * Konstruktor.
 */ 
    public Dragon(Snowplower snowplower) {
        super(snowplower);
    }

/**
 * az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által kifizetendő pénzt.
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