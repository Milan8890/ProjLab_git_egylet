package equipment.heads;

import user.Cleaner;
import java.util.List;
import entities.Snowplower;
import equipment.Head;
import main.Skeleton;
import playground.Lane;
import playground.Road;

/**
 * SaltSpreader
 * <p>
 * 
 * Felelősség <br>
 * Só elhelyezése a sávon, és az azért járó fizetés kiszámítása.
 * Elhasználja a sót a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class SaltSpreader extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public SaltSpreader(Snowplower snowplower) {
        super(snowplower);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Az “l” sávra leszórja a sót, és visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));

        Cleaner owner = snowplower.getCleaner();

        double length = l.getRoad().getLength();

        l.setSalt(owner);
        
        //double saltUsed = length*0.67;
        double saltUsed = (double) Skeleton.questionValue("Mennyi sót használjon?");
        
        snowplower.useSalt(saltUsed);

        int money = 0;

        Skeleton.logFunctionEnd();
        return money;       
    }

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * 
     * @param l a vizsgált sáv.
     * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
     */
    @Override
    public boolean canEnterLane(Lane l){
        Skeleton.logFunctionStart(this, "canEnterLane", List.of(Skeleton.createNameOfObject(l)));

        int salty = l.hasSalt() ? 1 : 2;

        if(salty == 1){
            Skeleton.logFunctionEnd();
            return false;
        }

        Road road = l.getRoad();
           
        double lenght = road.getLength();

        double saltAmount = snowplower.getSalt();

        int enter = Skeleton.questionMultiple("Van elég só a sáv tisztításához?", List.of("igen", "nem"));
        boolean enterBool =  (enter == 1);
        //if(saltAmount < lenght*0.67){ enter = false; }

        Skeleton.logFunctionEnd();
        return enterBool;
    }
}