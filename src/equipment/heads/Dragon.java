package equipment.heads;

import java.util.List;
import entities.Snowplower;
import equipment.Head;
import main.Skeleton;
import playground.Lane;
import playground.Road;

/**
 * Dragon
 * <p>
 * 
 * Felelősség: <br>
 * A jég és hó elolvasztása, és az ezért járó fizetés kiszámítása.
 * Elhasználja a biokerozint a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Dragon extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public Dragon(Snowplower snowplower) {
        super(snowplower);
    }

    /**
     * Az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l){
        double iceAmount = l.meltIce();
        double snowAmount = l.cleanSnow();
        double payPerMeterIce = 0.5;    //Ezt kell átírni.
        double payPerMeterSnow = 1;      //Ezt kell átírni.

        double payment = (iceAmount * payPerMeterIce + snowAmount * payPerMeterSnow)* l.getRoad().getLength(); // 1 jég = 0.5 pénz, 1 hó = 1 pénz, ez van tesztben
        return (int) payment;
    }

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * 
     * @param l a vizsgált sáv.
     * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
     */
    @Override
    public boolean canEnterLane(Lane l){
        double snowConsume = 1;         //Ezt a 2 értéket kell átírni a megfelelő fogyasztási konstansokra.
        double iceConsume = 0.5;

        double neededAmount = (l.getSnow() * snowConsume + l.getIce() * iceConsume) * l.getRoad().getLength();

        return neededAmount <= snowplower.getBio();
    }

}