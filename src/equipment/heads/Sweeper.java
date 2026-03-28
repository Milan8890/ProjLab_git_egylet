package equipment.heads;
import java.util.List;

import entities.Snowplower;
import equipment.Head;
import main.Skeleton;
import playground.Lane;

/**
 * Sweeper
 * <p>
 * 
 * Felelősség <br>
 * A hó arrébbsöprése a sávról, és az ezért járó fizetés kiszámítása.
 * Eltűnteti a jelenlegi sávról, és áthelyezi a mellette lévőre.
 * 
 * Ősosztályok <br>
 * Head
 */
public class Sweeper extends Head {

    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public Sweeper(Snowplower snowplower) {
        super(snowplower);
        Skeleton.initSettingUpObjectStart(this);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Eggyel kívülebbi sávra rakja át a havat, ha nincsen kívülebbi sáv akkor letakarítja,
     * és visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
        
        double snowAmount = l.cleanSnow();

        Road road = l.getRoad();

        List<Lane> lanes = road.getLanes(); 

        int sideLane = Skeleton.questionValue("Szélső sávban takarít a hókotró (0:nem, 1:igen): ");

        /*lanes.getLast() != l*/
        if( sideLane == 0 ){
            //int idx = lanes.indexOf(l) + 1;
            //lanes.get(idx).addSnow(snowAmount);

            l.addSnow(snowAmount); //Önmagára hívja meg, mivel ez egy biztosan létező sáv.            
        }

        double length = road.getLength();

        //int money = (int) (snowAmount*length);
        int money = Skeleton.questionValue("Mennyi pénzt adjunk a hókotrónak: ");
        
        Skeleton.logFunctionEnd();
        return money;
    }

}