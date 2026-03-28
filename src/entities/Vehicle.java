package entities;

import playground.Crossing;
import playground.Lane;

public abstract class Vehicle {
    Crossing lastCrossing;
    Lane currentLane;
    public void onTick() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onTick'");
    }
}