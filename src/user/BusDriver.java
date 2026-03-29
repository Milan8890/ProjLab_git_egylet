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
	/**
	 * Konstruktor, létrehoz egy új buszvezető játékost.
	 * 
	 * @param name  a játékos neve
	 * @param color a játékos színe
	 */
	public BusDriver(String name, Color color) {
		super(name, color);
		new Bus(Skeleton.Market.crossing, Skeleton.Market.crossing, this);
		Skeleton.initSettingUpObjectEnd();
	}
}