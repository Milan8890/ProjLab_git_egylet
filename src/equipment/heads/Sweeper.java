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

        List<Lane> lanes = road.getLanes();     //erre kéne valamit logolni?

        if( lanes.getLast() != l){

            int idx = lanes.indexOf(l) + 1;
            lanes.get(idx).addSnow(snowAmount);
        }
        else{
        }

        double length = road.getLength();

        int money = (int) (snowAmount*length);
        
        Skeleton.logFunctionEnd();
        return money;
    }

}