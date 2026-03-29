package main;

import java.util.Arrays;

import entities.Snowplower;
import equipment.HeadListing;
import equipment.heads.*;

public class UseCases {
    //TODO
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

    public static void cleaningALane_12(){
        Snowplower plower = Skeleton.Market.snowplower;
        plower.onTick();
    }
    public static void switchHead_14(){
        Snowplower plower = Skeleton.Market.snowplower;
        plower.getHeadInventory().cycleActiveHead();
    }


}
