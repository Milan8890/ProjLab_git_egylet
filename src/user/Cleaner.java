package user;

import java.awt.Color;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import entities.Snowplower;
import main.Skeleton;
import playground.Crossing;

/**
 * Egyik játékos fajta, hókotrókat irányít.
 * 
 * Felelősségei: <br>
 * Játékos pénzének kezelése. Új hókotró vásárlása.
 * 
 */
public class Cleaner extends Player {

    /**
     * Konstruktor, létrehoz egy új takarító játékost.
     * @param name a játékos neve
     * @param color a játékos színe
     */
    public Cleaner(String name, Color color)
    {
        super(name, color);
        Skeleton.initSettingUpObjectEnd();
    }

    /**
     * Hozzáad pénzt a játékoshoz
     * @param m az hozzáadandó pénz összege
     */
    public void addMoney(int m)
    {
        Skeleton.logFunctionStart(this, "addMoney", Arrays.asList(Integer.toString(m)));
        Skeleton.logFunctionEnd();
    }

    /**
     * Eltávolít pénzt a játékos pénzéből.
     * Ha nincs elég pénz akkor visszatér false-al, <br>
     * különben levonja a pézt és visszatér true-al
     * @param m az eltávolítandó pénz összege
     * @return true, ha sikerült az eltávolítás, false egyébként
     */
    public boolean removeMoney(int m)
    {
        Skeleton.logFunctionStart(this, "removeMoney", Arrays.asList(Integer.toString(m)));
        int ans = Skeleton.questionMultiple("Van elég pénz?", Arrays.asList("Igen", "Nem"));
        Skeleton.logFunctionEnd();
        return ans == 1;
    }

    /**
     * Vásárol egy breaker fejjel kezdő hókotrót.
     * @return true, ha sikerült a vásárlás, false egyébként
     */
    public boolean buyBreakerSnowplower()
    {
        Skeleton.logFunctionStart(this, "buyBreakerSnowplower",  Arrays.asList());

        int val = Skeleton.questionValue("Mennyibe kerül?");
        boolean temp = removeMoney(val);

        //Kéne bázis lekérés?
        // TODO: LE KELL CSERÉLNI A base:
        if(temp)
            Snowplower.createWithBreaker(this, new Crossing());

        Skeleton.logFunctionEnd();
        return temp;
    }

    /**
     * Vásárol egy ejector fejjel kezdő hókotrót.
     * @return true, ha sikerült a vásárlás, false egyébként
     */
    public boolean buyEjectorSnowplower()
    {
        Skeleton.logFunctionStart(this, "buyEjectorSnowplower", Arrays.asList());
        
        int val = Skeleton.questionValue("Mennyibe kerül?");
        boolean temp = removeMoney(val);

        //Kéne bázis lekérés?
        // TODO: LE KELL CSERÉLNI A base:
        if(temp)
            Snowplower.createWithEjector(this, new Crossing());

        Skeleton.logFunctionEnd();
        return temp;
    }

}