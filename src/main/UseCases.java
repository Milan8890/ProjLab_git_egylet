package main;

import java.util.Arrays;

import entities.Snowplower;
import equipment.HeadListing;

public class UseCases {
    //TODO

    /**
     * Use case 1: Fej vásárlása
     * A játékos kiválaszt egy fejet a lehetőségek közül, és megvásárolja azt a hókotrójához. A fej ára levonódik a játékos pénzéből, és a fej hozzáadódik a hókotrónak a fejtárolójához.
     */
    public static void purchaseHead_1(){
        Snowplower plower = Skeleton.Market.snowplower;

        int listing = Skeleton.questionMultiple("Milyen fejet szeretne vásárolni?", Arrays.asList("Jégtörő", "Hányó", "Seprő", "Sószóró", "Sárkány"));
        HeadListing headListing;
        switch (listing) {
            case 1:
                headListing = Skeleton.Market.breakerHeadListing; 
                break;
            case 2:
                headListing =Skeleton.Market.sweeperHeadListing; 
                break;
            case 3:
                headListing = Skeleton.Market.ejectorHeadListing; 
                break;
            case 4:
                headListing = Skeleton.Market.saltSpreaderHeadListing; 
                break;
            default:
                headListing = Skeleton.Market.dragonHeadListing; 
                break;
        }
        plower.getHeadInventory().buyListing(headListing);
    }

    /**
     * Use case 2: Sáv letakarítása
     */
    public static void cleaningALane_12(){
        Snowplower plower = Skeleton.Market.snowplower;
        plower.onTick();
    }

    /**
     * Use case 3: Fej váltása
     */
    public static void switchHead_14(){
        Snowplower plower = Skeleton.Market.snowplower;
        plower.getHeadInventory().cycleActiveHead();
    }


}
