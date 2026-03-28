package equipment.heads;
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
    }

    /**
     * Az “l” sávra leszórja a sót, és visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){   //DIAGRAMMM???
        
        throw new UnsupportedOperationException("Unimplemented method 'clean'");
        



        
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

        boolean salty = l.hasSalt();
        Skeleton.logString("Is there any salt on the next lane: " + salty);

        if(salty){
            Skeleton.logString("The lane is already salty.");
            Skeleton.logFunctionEnd();
            return false;
        }

        Road road = l.getRoad();
        Skeleton.logString("Road: " + Skeleton.createNameOfObject(road));
           
        double lenght = road.getLenght();
        Skeleton.logString("Length: " + lenght);

        double saltAmount = snowplower.getSalt();
        Skeleton.logString("Salt amount: " + saltAmount);

        boolean enter = true;

        if(saltAmount < lenght*0.67){ //itt mi legyen a konstans???
            enter = false;
            Skeleton.logString("There is not enough salt.");
        }null
        else{
            Skeleton.logString("There is enough salt.");
        }

        Skeleton.logFunctionEnd();
        return enter;
    }
}