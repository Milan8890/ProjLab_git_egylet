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
        Skeleton.logString("Snow amount: " + snowAmount);

        Road road = l.getRoad();
        Skeleton.logString("Road: " + Skeleton.createNameOfObject(road));

        List<Lane> lanes = road.getLanes();     //erre kéne valamit logolni?

        if( lanes.getLast() != l){
            Skeleton.logString("The lane is a non-siselane.");

            int idx = lanes.indexOf(l) + 1;
            lanes.get(idx).addSnow(snowAmount);
        }
        else{
            Skeleton.logString("The lane is a sidelane.");
        }

        double length = road.getLength();
        Skeleton.logString("Lane lenght: " + length);

        int money = (int) (snowAmount*length);
        Skeleton.logString("Money: " + money);
        
        Skeleton.logFunctionEnd();
        return money;
    }
}