package equipment.heads;
import java.util.List;

import entities.Snowplower;
import equipment.Head;
import main.Skeleton;
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
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
        
        double snowAmount = l.cleanSnow();
        Skeleton.logString("Snow amount: " + snowAmount);

        double length = l.getRoad().getLength();
        Skeleton.logString("Lane lenght: " + length);


        int money = (int) (snowAmount*length);
        Skeleton.logString("Money: " + money);
        
        Skeleton.logFunctionEnd();
        return money;
    }
}