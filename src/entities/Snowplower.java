package entities;

import user.Cleaner;
import java.util.Arrays;

import equipment.HeadInventory;
import playground.Crossing;
import main.Skeleton;

/**
 * 
 * A játékos által irányított jármű.<br>
 * Tudja melyik hókotrófejet használja éppen a hókotró.<br>
 * Tudja mennyi só, illetve biokerozin van még az egyes fejekhez.<br>
 * 
 * <p></p>
 * 
 * Felelősségei: <br>
 *      kotrófejek cserélése, vásárlása. Alapanyag vásárlása, Eldönti, hogy fel tud-e hajtani egy útra.<br>
 *      Útvona követése. Ütközés utáni újraindulás. Nyilvántartja, hogy a jelenlegi úton hol helyezkedik el.<br>
 *      Letaposás végzése. A fej által visszaadott mennyiséggel kifizeti a játékost.<br>
 */
public class Snowplower extends Vehicle {
    Cleaner owner;

    /**
     * Konstruktor
     * 
     * @param owner a játékos aki irányítja a hókotrót
     * @param spawn a kereszteződés ahol a hókotró megjelenik
     * @param inventory a fejtároló amivel a hókotró rendelkezik
     */
    private Snowplower(Cleaner owner,Crossing spawn){
        Skeleton.initSettingUpObjectStart(this);
        this.owner=owner;
        currentLane= null;
        lastCrossing= spawn;
        Skeleton.initSettingUpObjectEnd();

    }

    /**
     * Sáv elhagyásakor tisztítja a sávot az aktív fejjel.
     */
    public void onTick(){
        Skeleton.logFunctionStart(this, "OnTick", null);
        int answer = Skeleton.questionMultiple("Út végére értél?", Arrays.asList("Igen", "Nem"));
        if(answer ==1){
            owner.addMoney(HeadInventory.createWithBreaker(this).getActiveHead().clean(currentLane)); 
        }
        super.onTick();
        Skeleton.logFunctionEnd();
    }
    /** 
     * visszaadja a birtokló játékost
     * 
     * 
     * 
     * @return A hókotrót birtokló játékos
     */
    public Cleaner getCleaner(){
        Skeleton.logFunctionStart(this, "getCleaner", null);
        Skeleton.logFunctionEnd();
        return owner;
    }

    /** 
     * visszaadja a headInventory-t
     * 
     * @return HeadInventory
     */
    public HeadInventory getHeadInventory(){
        Skeleton.logFunctionStart(this, "getHeadInventory", null);  
        Skeleton.logFunctionEnd();
        return HeadInventory.createWithBreaker(this);
    }

    /** 
     * Visszaadja, hogy mennyi só áll rendelkezésre.
     * 
     * @return mennyi Só áll rendelkezésre
     */
    public double getSalt(){
        Skeleton.logFunctionStart(this, "getSalt", null);
        
        double saltAmount = Skeleton.questionValue("Mennyi só van?");

        Skeleton.logFunctionEnd();
        return saltAmount;
    }

    /** 
     * Visszaadja, hogy mennyi biokerozin áll rendelkezésre.
     * 
     * @return mennyi Biokerozin áll rendelkezésre
     */
    public double getBio(){
        Skeleton.logFunctionStart(this, "getBio", null);
        
        double bioAmount =Skeleton.questionValue("Mennyi kerozin van?");

        Skeleton.logFunctionEnd();
        return bioAmount;
    }

    /** 
     * Vesz egy adag sót. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem kereszteződésben van.
     * 
     * @return Sikeres volt-e a vásárlás
     */
    public boolean buySalt(){
        Skeleton.logFunctionStart(this, "buySalt", null);
        boolean answer1 = isinCrossing();
        if(!answer1)
        {
            Skeleton.logFunctionEnd();
            return false;
        }
        int saltPrice = Skeleton.questionValue("Mennyibe kerül a só?");
        boolean ret =owner.removeMoney(saltPrice);
        Skeleton.logFunctionEnd();
        return ret;
    }

    /**
     * Vesz egy adag kerozint. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem kereszteződésben van.
     * 
     * @return Sikeres volt-e a vásárlás
     */
    public boolean buyBio(){
        Skeleton.logFunctionStart(this, "buyBio", null);
        boolean answer1 = isinCrossing();
        if(!answer1)
        {
            Skeleton.logFunctionEnd();
            return false;
        }
        int bioPrice = Skeleton.questionValue("Mennyibe kerül a kerozin?");
        boolean ret =owner.removeMoney(bioPrice);
        Skeleton.logFunctionEnd();
        return ret;
    }

    /**
     * levonja az elhasznált sót
     * 
     * @param saltAmount Az elhasznált só mennyisége
     */
    public void useSalt(double saltAmount){
        Skeleton.logFunctionStart(this, "useSalt", Arrays.asList( Double.toString(saltAmount)));
        Skeleton.logFunctionEnd();
    }

    /**
     * levonja az elhasznált kerozint
     * 
     * @param bioAmount Az elhasznált kerozin mennyisége
     */
    public void useBio(double bioAmount){
        Skeleton.logFunctionStart(this, "useBio", Arrays.asList( Double.toString(bioAmount)));
        Skeleton.logFunctionEnd();
    }

    /**
     * Konstruktor segítségével létrehoz egy Hókotrót egy hányó fejjel.
     * 
     * @param owner a játékos aki irányítja a hókotrót
     * @param base  a kereszteződés ahol a hókotró megjelenik
     * @return a létrehozott Hókotró
     */
    public static Snowplower createWithEjector(Cleaner owner, Crossing base){
        Skeleton.logFunctionStart( "Snowplower", "creatWithEjector", Arrays.asList(owner.toString(), base.toString()));
        Snowplower pl = new Snowplower(owner, base);
        HeadInventory.createWithEjector(pl);
        Skeleton.logFunctionEnd();
        return pl;
    }
    /**
     * Konstruktor segítségével létrehoz egy Hókotrót egy jégtörő fejjel.
     * @param owner a játékos aki irányítja a hókotrót
     * @param base  a kereszteződés ahol a hókotró megjelenik
     * @return a létrehozott Hókotró
     */
    public static Snowplower createWithBreaker(Cleaner owner, Crossing base){
        Skeleton.logFunctionStart( "Snowplower", "creatWithBreaker", Arrays.asList(owner.toString(), base.toString()));
        Snowplower pl = new Snowplower(owner, base);
        HeadInventory.createWithBreaker(pl);
        Skeleton.logFunctionEnd();
        return pl;
    }
}