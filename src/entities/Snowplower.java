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
    HeadInventory inventory;

public
    /**
     * Konstruktor
     * @param owner a játékos aki irányítja a hókotrót
     * @param spawn a kereszteződés ahol a hókotró megjelenik
     * @param inventory a fejtároló amivel a hókotró rendelkezik
     */
    Snowplower(Cleaner owner,Crossing spawn, HeadInventory inventory){
        this.owner=owner;
        this.inventory = inventory;
        currentLane= null;
        lastCrossing= spawn;

    }
    /**
     * Sáv elhagyásakor tisztítja a sávot az aktív fejjel.
     */
    void onTick(){
        Skeleton.logFunctionStart(this, "OnTick", null);

        int answer = Skeleton.questionMultiple("Út végére értél?", Arrays.asList("Igen", "Nem"));
        if(answer==1){
            inventory.getActiveHead().clean(); 
        }
        
        Skeleton.logFunctionEnd();
    }
    /** 
     * visszaadja a birtokló játékost
     * 
     * 
     * 
     * @return A hókotrót birtokló játékos
     */
    Cleaner getCleaner(){
        Skeleton.logFunctionStart(this, "getCleaner", null);
        Skeleton.logFunctionEnd();
        return owner;
    }
    /** 
     * visszaadja a headInventory-t
     * 
     * 
     * 
     * @return HeadInventory
     */
    HeadInventory getHeadInventory(){
        Skeleton.logFunctionStart(this, "getHeadInventory", null);  
        Skeleton.logFunctionEnd();
        return inventory;
    }
    /** 
     * Visszaadja, hogy mennyi só áll rendelkezésre.
     * 
     * 
     * 
     * @return mennyi Só áll rendelkezésre
     */
    double getSalt(){
        Skeleton.logFunctionStart(this, "getSalt", null);
        
        double saltAmount =Skeleton.questionValue("Mennyi só van?");

        Skeleton.logFunctionEnd();
        return saltAmount;
    }
    /** 
     * Visszaadja, hogy mennyi biokerozin áll rendelkezésre.
     * 
     * 
     * 
     * @return mennyi Biokerozin áll rendelkezésre
     */
    double getBio(){
        Skeleton.logFunctionStart(this, "getBio", null);
        
        double bioAmount =Skeleton.questionValue("Mennyi kerozin van?");

        Skeleton.logFunctionEnd();
        return bioAmount;
    }
    /** 
     * Vesz egy adag sót. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem kereszteződésben van.
     * 
     * 
     * 
     * @return Sikeres volt-e a vásárlás
     */
    boolean buySalt(){
        Skeleton.logFunctionStart(this, "buySalt", null);
        int answer1 = Skeleton.questionMultiple("Kereszteződésben van?", Arrays.asList("Igen", "Nem"));
        if(answer1!=1)
        {
            Skeleton.logFunctionEnd();
            return false;
        }
        int answer2 = Skeleton.questionMultiple("Van elég pénz?", Arrays.asList("Igen", "Nem"));
        if(answer2==1){
            owner.removeMoney(); // TODO
        }
        Skeleton.logFunctionEnd();
        return answer2==1;
    }
    /**
     * Vesz egy adag kerozint. Hamissal tér vissza, ha nincs rá elég pénz, vagy nem kereszteződésben van.
     * 
     * 
     * 
     * @return Sikeres volt-e a vásárlás
     */
    boolean buyBio(){
        Skeleton.logFunctionStart(this, "buyBio", null);
        int answer1 = Skeleton.questionMultiple("Kereszteződésben van?", Arrays.asList("Igen", "Nem"));
        if(answer1!=1)
        {
            Skeleton.logFunctionEnd();
            return false;
        }
        int answer2 = Skeleton.questionMultiple("Van elég pénz?", Arrays.asList("Igen", "Nem"));
        if(answer2==1){
            owner.removeMoney(); // TODO
        }
        Skeleton.logFunctionEnd();
        return answer2==1;
    }

    /**
     * levonja az elhasznált sót
     * 
     * 
     * 
     * @param saltAmount Az elhasznált só mennyisége
     */
    void useSalt(double saltAmount){
        Skeleton.logFunctionStart(this, "useSalt", Arrays.asList( Double.toString(saltAmount)));
        Skeleton.logFunctionEnd();
    }
    void useBio(double bioAmount){
        Skeleton.logFunctionStart(this, "useBio", Arrays.asList( Double.toString(bioAmount)));
        Skeleton.logFunctionEnd();
    }
    /**
     * Konstruktor segítségével létrehoz egy Hókotrót egy hányó fejjel.
     * @param owner a játékos aki irányítja a hókotrót
     * @param base  a kereszteződés ahol a hókotró megjelenik
     * @return a létrehozott Hókotró
     */
    static Snowplower createWithEjector(Cleaner owner, Crossing base){
        Skeleton.logFunctionStart( "Snowplower", "creatWithEjector", Arrays.asList(owner.toString(), base.toString()));
        Snowplower pl = new Snowplower(owner, base, null);
        HeadInventory inv =HeadInventory.createWithEjector(pl);
        pl.inventory=inv;
        Skeleton.logFunctionEnd();
        return pl;
    }
    /**
     * Konstruktor segítségével létrehoz egy Hókotrót egy jégtörő fejjel.
     * @param owner a játékos aki irányítja a hókotrót
     * @param base  a kereszteződés ahol a hókotró megjelenik
     * @return a létrehozott Hókotró
     */
    static Snowplower createWithBreaker(Cleaner owner, Crossing base){
        Skeleton.logFunctionStart( "Snowplower", "creatWithBreaker", Arrays.asList(owner.toString(), base.toString()));
        Snowplower pl = new Snowplower(owner, base, null);
        HeadInventory inv =HeadInventory.createWithBreaker(pl);
        pl.inventory=inv;
        Skeleton.logFunctionEnd();
        return pl;
    }
}