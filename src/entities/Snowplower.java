package entities;

import user.Cleaner;

import equipment.Head;
import equipment.HeadInventory;

public class Snowplower extends Vehicle {
    Cleaner owner;
    Head activeHead;

    void onTick(){
        System.out.println("Ontick hívódott");

        System.out.println("Elhagytad a sávot?");
        boolean elhagyta = false; // TODO
        if(elhagyta){
            activeHead.clean();
        }

        System.out.println("OnTick visszatért");

    }
    Cleaner getCleaner(){

    }
}