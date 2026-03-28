package user;

import main.Skeleton;
import entities.Bus;
import java.awt.Color;

public class BusDriver extends Player {

    Bus bus;

    BusDriver(String name, Color color)
    {
        super(name, color);
        Skeleton.initSettingUpObjectStart(this);
        this.bus = new Bus();
        Skeleton.initSettingUpObjectEnd();
    }
}