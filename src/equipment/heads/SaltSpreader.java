package equipment.heads;

import java.lang.ref.Cleaner;
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
        Skeleton.initSettingUpObjectStart(this);
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

        double length = l.getRoad().getLenght();

        l.setSalt(owner);
        
        //double saltUsed = length*0.67;
        double saltUsed = (double) Skeleton.questionValue("Mennyi sót használjon: ");
        
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

        int salty = Skeleton.questionValue("Van só a sávon (0:nem, 1:igen): ");

        if(salty == 1){
            Skeleton.logFunctionEnd();
            return false;
        }
        else if (salty != 0){ 
            Skeleton.logString("Érvénytelen bemenet, feltételezi, hogy nincs só a sávon.");
        }

        Road road = l.getRoad();
           
        double lenght = road.getLenght();

        double saltAmount = snowplower.getSalt();

        int enter = Skeleton.questionValue("Van elég só a sáv tisztításához (0:nem, 1:igen): ");
        boolean enterBool;
        //if(saltAmount < lenght*0.67){ enter = false; }

        if(enter == 0){
            enterBool = false;
        }
        else if(enter == 1){
            enterBool = true;
        }
        else{
            Skeleton.logString("Érvénytelen bemenet, feltételezi, hogy van elég só.");
            enterBool = true;
        }

        Skeleton.logFunctionEnd();
        return enterBool;
    }
}