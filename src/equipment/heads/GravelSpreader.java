package equipment.heads;

import user.Cleaner;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Snowplower;
import equipment.Head;
import playground.Lane;
import playground.Road;

/**
 * GravelSpreader
 * <p>
 * 
 * Felelősség <br>
 * Zúzalék elhelyezése a sávon, és az azért járó fizetés kiszámítása. 
 * Elhasználja a zúzalékot a hókotróból.
 * 
 * Ősosztályok <br>
 * Head
 */
public class GravelSpreader extends Head {

    static final double GRAVEL_CONSUME = 1;
    /**
     * Konstruktor.
     * 
     * @param snowplower A tulajdonos hókotró.
     */ 
    public GravelSpreader(Snowplower snowplower) {
        super(snowplower);
    }

    /**
     * Rak zúzalékot az “l” sávra, visszaadja a takarítás által kifizetendő pénzt.
     * 
     * @param l a tisztítandó sáv.
     * @return a takarítás által kifizetendő pénz.
     */
    public int clean(Lane l){
        l.setGravel(true);
        double payPerMeter = 1;    //Ezt kell átírni, nem tudom mi egyáltalán a gravelnél a fizetési pénz.

        //Ide kéne még valami ice/snowLevel elem a képletbe?
        int payment = (int) (l.getRoad().getLength() * payPerMeter);

        double amount = (l.getRoad().getLength() * GRAVEL_CONSUME);
        snowplower.useGravel(amount);

        Logger.getGlobal().log(Level.INFO, "[Obj] with [Obj] cleans [Obj] for " + payment + "$" , new Object[] {snowplower , this, l});
		Logger.getGlobal().log(Level.INFO, "[Obj] uses " + amount + "gravel from [Obj]", new Object[] {this, snowplower});

        return payment;
    }

    /**
     * Visszaadja, hogy befordulhat-e az adott sávra a hókotró.
     * Hamissal tér vissza, ha nincs elég zúzalék a sáv tisztításához, vagy ha már van zúzalék a sávon.
     * 
     * @param l a vizsgált sáv.
     * @return Hamissal tér vissza, ha nincs elég biokerozin a sáv tisztításához, egyébként igazzal.
     */
    @Override
    public boolean canEnterLane(Lane l){
        if(l.hasGravel()) return false;

        double neededAmount = l.getRoad().getLength() * GRAVEL_CONSUME;

        if(neededAmount <= snowplower.getGravel()) 
			Logger.getGlobal().log(Level.INFO, "[Obj] allows [Obj] to enter [Obj] ", new Object[] {this, snowplower, l});
		else
			Logger.getGlobal().log(Level.INFO, "[Obj] blocks [Obj] from entering [Obj] ", new Object[] {this, snowplower, l});

        return snowplower.getGravel() >= neededAmount;
    }
}