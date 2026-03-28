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
        Skeleton.initSettingUpObjectStart(this);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Az “l” sávról letakarítja a havat, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l){
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
        
        double snowAmount = l.cleanSnow();

        double length = l.getRoad().getLength();

        //int money = (int) (snowAmount*length);
        int money = Skeleton.questionValue("Mennyi pénzt adjunk a hókotrónak: ");
        
        Skeleton.logFunctionEnd();
        return money;
    }
}