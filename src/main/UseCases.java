package main;

import entities.Car;
import playground.Lane;
import playground.Road;
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

        public static void snowing_15() {
        Skeleton.startUseCase("Snowing (15)");

        Road road = Skeleton.Market.road;
        road.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleUnblocking_16() {
        Skeleton.startUseCase("Vehicle unblocking itself during onTick (16)");
        Car car = Skeleton.Market.car;
        car.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleEntersNextLane_17() {
        Skeleton.startUseCase("Vehicle enters next lane from crossing (17)");
        Car car = Skeleton.Market.car;
        car.onTick();

        Skeleton.logFunctionEnd();
    }

    public static void vehicleSwitchesLane_18() {
        Skeleton.startUseCase("Vehicle switches lanes due to deep snow (18)");

        Car car = Skeleton.Market.car;
        car.onTick();
    }
}
