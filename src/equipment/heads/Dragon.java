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
 * Felelősség <br>
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
    }

    /**
     * Az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        Skeleton.logFunctionStart(this, "clean", List.of(Skeleton.createNameOfObject(l)));
         
        double iceAmount = l.meltIce();
        Skeleton.logString("Ice amount: " + iceAmount);

        double snowAmount = l.cleanSnow();
        Skeleton.logString("Snow amount: " + snowAmount);

        double length = l.getRoad().getLength();
        Skeleton.logString("Lane lenght: " + length);

        //A canEnter miatt csak a lenght-hez lehet kötve a számítása,
        // mivel ott ennek az értéke alapján mondja meg, hogy van-e elég,
        // és nem nézi mellé a hó- és vastagságot.
        // További kérdés mi legyen itt a konstans szorzó???
        double usedBio = length*2.67;  
        snowplower.useBio(usedBio);
        Skeleton.logString("Used amount of biokerozin: " + usedBio);

        int money = (int) ( (snowAmount*length) + (iceAmount*length)*2 );
        Skeleton.logString("Money: " + money);
        
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
        Skeleton.logString("Road: " + Skeleton.createNameOfObject(road));

        double lenght = road.getLenght();
        Skeleton.logString("Length: " + lenght);

        double bioAmount = snowplower.getBio();
        Skeleton.logString("Biokerozim amount: " + bioAmount);

        boolean enter = true;
        if( bioAmount < lenght*2.67){
            enter = false;
            Skeleton.logString("There is not enough biokerozin.");
        }
        else{
            Skeleton.logString("There is enough biokerozin.");
        }

        Skeleton.logFunctionEnd();
        return enter;
    }
}