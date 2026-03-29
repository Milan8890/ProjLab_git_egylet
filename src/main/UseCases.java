package main;

import entities.Car;
import playground.Lane;
import playground.Road;

public class UseCases {
    //TODO

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

    Skeleton.logFunctionEnd();
}
}
