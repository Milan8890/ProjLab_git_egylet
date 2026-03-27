package entities;

import user.Cleaner;

import equipment.Head;
import equipment.HeadInventory;
import playground.Crossing;

/**
 * 
 */
public class Snowplower extends Vehicle {
    Cleaner owner;
    Head activeHead;
public
    void onTick(){
        System.out.println("Ontick hívódott");

        System.out.println("Elhagytad a sávot?");
        boolean elhagyta = false; // TODO
        if(elhagyta){
            //activeHead.clean();
        }

        System.out.println("OnTick visszatér");

    }
    /** 
     * @return A hókotrót birtokló játékos
     */
    Cleaner getCleaner(){
        System.out.println("getCleaner hívódott");
        System.out.println("getCleaner visszatér");
        return new Cleaner(); //TODO
    }
    /** 
     * @return HeadInventory
     */
    HeadInventory getHeadInventory(){
        System.out.println("getHeadInventory hívódott");
        System.out.println("getHeadInventory visszatér");
        return new HeadInventory(); //TODO
    }
    /** 
     * @return double
     */
    double getSalt(){
        System.out.println("getSalt hívódott");
        System.out.println("Mennyi só van?");
        double saltAmount =0; //TODO
        System.out.println("getSalt visszatér");
        return saltAmount;
    }
    /** 
     * @return double
     */
    double getBio(){
        System.out.println("getBio hívódott");
        System.out.println("Mennyi kerozin van?");
        double bioAmount =0; //TODO
        System.out.println("getBio visszatér");
        return bioAmount;
    }
    /** 
     * @return boolean
     */
    boolean buySalt(){
        System.out.println("buySalt hívódott");
        System.err.println("Van elég pénz?");
        boolean enoughMoney = false; //TODO
        if(enoughMoney){
            //owner.removeMoney();
        }

        return enoughMoney;
    }
    boolean buyBio(){
        System.out.println("buyBio hívódott");
        System.err.println("Van elég pénz?");
        boolean enoughMoney = false; //TODO
        if(enoughMoney){
            //owner.removeMoney();
        }
        
        return enoughMoney;
    }

    void useSalt(double saltAmount){

    }
    void useBio(double bioAmount){

    }
    static Snowplower createWithEjector(Cleaner owner, Crossing base){return new Snowplower(); //TODO
        }
    static Snowplower createWithBreaker(Cleaner owner, Crossing base){return new Snowplower(); //TODO
    }
    



}