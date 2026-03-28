package user;

import main.Skeleton;
import entities.Bus;
import java.awt.Color;

/**
 * Egyik játékos fajta, buszt irányít 2 megálló között.
 * 
 * Felelősségei: <br>
 * Megtett fordulók számolása, busz nyilvántartása.
 * 
 */
public class BusDriver extends Player {

    Bus bus;

    /**
     * Konstruktor, létrehoz egy új buszvezető játékost.
     * @param name a játékos neve
     * @param color a játékos színe
     */
    public BusDriver(String name, Color color)
    {
        super(name, color);
        Skeleton.initSettingUpObjectStart(this);
        this.bus = new Bus();
        Skeleton.initSettingUpObjectEnd();
    }
}