package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entities.Bus;
import graphics.NewMain;

public class BusView {
	private Bus modelBus;
	private Point2D pos;

	static private BufferedImage img;

	static void readImage() {

	}

	public BusView(Bus bus) {
		this.modelBus = bus;

		// nincs meg a kép
		NewMain.notdone("BusView konstruktor");
	}

	public void paint(Graphics2D g) {

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g.translate(300, 300);
		g.rotate(Math.toRadians(60));
		g.drawRect(-100, -100, 100, 100);

		// NewMain.notdone("Bus paint");
	}
}
