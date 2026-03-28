package equipment.heads;
import java.util.List;

import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import main.Skeleton;

/**
 * Breaker
 * <p>
 * 
 * Felelősség <br>
 * A sávon lévő jég feltörése, és az ezért járó fizetés kiszámítása.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Breaker extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public Breaker(Snowplower snowplower) {
        super(snowplower);
        Skeleton.initSettingUpObjectStart(this);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Az “l” sávról letakarítja a jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l) {
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
        
        double iceAmount = l.breakIce();

        double length = l.getRoad().getLength();

        //int money =(int) (iceAmount*length)*2;
        int money = Skeleton.questionValue("Mennyi pénzt adjunk a hókotrónak: "); 
        
        Skeleton.logFunctionEnd();
        return money;
    }
}