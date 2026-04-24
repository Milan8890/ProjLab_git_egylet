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

    }

    /**
     * Az “l” sávról letakarítja a havat és jeget, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    @Override
    public int clean(Lane l){
        
    }

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * 
     * @param l a vizsgált sáv.
     * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
     */
    @Override
    public boolean canEnterLane(Lane l){
    
    }

}