package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entities.Bus;
import graphics.NewMain;

public class BusView {
	private Bus modelBus;
	private Point2D pos;

	static private BufferedImage img;

	public BusView(Bus bus) {
		this.modelBus = bus;

		// nincs meg a kép
		NewMain.notdone("BusView konstruktor");
	}

	public void paint(Graphics2D g) {
		NewMain.notdone("Bus paint");
	}
}
