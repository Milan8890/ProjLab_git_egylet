package equipment.heads;

import user.Cleaner;
import java.util.List;
import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import playground.Road;

/**
 * GravelSpreader
 * <p>
 * 
 * Felelősség <br>
 * Zúzalék elhelyezése a sávon, és az azért járó fizetés kiszámítása. 
 * Elhasználja a zúzalékot a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class GravelSpreader extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public GravelSpreader(Snowplower snowplower) {
        super(snowplower);
    }

    /**
     * Rak zúzalékot az “l” sávra, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        l.setGravel(true);
        double payPerMeter = 1;    //Ezt kell átírni, nem tudom mi egyáltalán a gravelnél a fizetési pénz.

        //Ide kéne még valami ice/snowLevel elem a képletbe?
        int payment = (int) (l.getRoad().getLength() * payPerMeter);
        return payment;
    }

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * Hamissal tér vissza, ha nincs elég zúzalék a sáv tisztításához, vagy ha már van zúzalék a sávon.
     * 
     * @param l a vizsgált sáv.
     * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
     */
    @Override
    public boolean canEnterLane(Lane l){
        if(l.hasGravel()) return false;

        double gravelConsume = 1;   //Ezt kell átírni, hogy 1 méterre mennyi kő kerül.
        double neededGravel = l.getRoad().getLength() * gravelConsume;

        return snowplower.getGravel() >= neededGravel;
    }
}