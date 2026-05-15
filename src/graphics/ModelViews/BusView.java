package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

import entities.Bus;
import graphics.NewMain;

public class BusView {
	private Bus modelBus;
	private Point2D pos;

	static private BufferedImage img = null;

	static void readImage() {
		try {
			img = ImageIO.read(new File("assets/testing/the thing.png"));
		} catch (Exception e) {
			System.err.println("Lol");
		}
	}

	public BusView(Bus bus) {
		if (img == null) {
			readImage();
		}

		this.modelBus = bus;

		// nincs meg a kép
		NewMain.notdone("BusView konstruktor");
	}

	public void paint(Graphics2D g) {

		g.translate(300, 300);
		g.rotate(Math.toRadians(60));
		g.scale(1.5, 2);

		g.drawRect(-100, -100, 100, 100);

		// Normál rajzolás
		g.drawImage(img, 200, 200, null);

		// A tömb meghatározza a szorzókat a Piros, Zöld, Kék csatornákhoz
		float r = (float) Math.random();
		float green = (float) Math.random();
		float b = (float) Math.random();

		float[] scales = { r, green, b }; // Az utolsó az Alpha (átlátszóság)
		float[] offsets = new float[4]; // Eltolás (itt 0)

		// BESZÍNEZÉS CSAK AZ RGB KOORDINÁTÁKAT SZOROZZA SZÁMOKKAL
		RescaleOp tintFilter = new RescaleOp(scales, offsets, null);

		// A szűrt képet rajzoljuk ki
		g.drawImage(tintFilter.filter(img, null), 0, 0, null);

		NewMain.notdone("Bus paint");
	}
}
