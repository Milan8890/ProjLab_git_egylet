package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entities.Bus;
import entities.Car;
import graphics.NewMain;

public class CarView {
	private Car modelCar;
	private Point2D pos;

	static private BufferedImage img;

	public CarView(Car car) {
		this.modelCar = car;
		// nincs meg a kép
		NewMain.notdone("CarView konstruktor");
	}

	public void paint(Graphics2D g) {
		NewMain.notdone("CarView paint");
	}
}
