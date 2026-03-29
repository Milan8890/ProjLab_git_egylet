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
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l){
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
         
        double iceAmount = l.meltIce();

        double snowAmount = l.cleanSnow();

        double length = l.getRoad().getLength();

        //A canEnter miatt csak a lenght-hez lehet kötve a számítása,
        // mivel ott ennek az értéke alapján mondja meg, hogy van-e elég,
        // és nem nézi mellé a hó- és vastagságot.
        // További kérdés mi legyen itt a konstans szorzó?
        //double usedBio = length*2.67;
        double usedBio = (double) Skeleton.questionValue("Mennyi biokerozint használjon?");

        snowplower.useBio(usedBio);

        //int money = (int) (snowAmount*length);
        int money = Skeleton.questionValue("Mennyi pénzt adjunk a hókotrónak?");

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

        Road road = l.getRoad();

        double lenght = road.getLength();

        double bioAmount = snowplower.getBio();

        int enter = Skeleton.questionMultiple("Van elég biokerozinunk?", List.of("igen", "nem"));
        boolean enterBool =  (enter == 1);

        Skeleton.logFunctionEnd();
        return enterBool;
    }

}