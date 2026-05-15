package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseListener;
import java.awt.font.GraphicAttribute;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.logging.Logger;
import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.management.ObjectName;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import entities.Bus;

public class App {

	// public static void main(String[] args) throws Exception {
	// Proto proto = new Proto(World.players);

	// // Handler beállítása
	// Logger.getGlobal().setUseParentHandlers(false);
	// OwnHandler ownHandler = new OwnHandler(proto.objectMap);
	// Logger.getGlobal().addHandler(ownHandler);

	// // Parancsok beolvasása
	// proto.readCommandsFromCommandLine();
	// }

	static class OwnPanel extends JPanel {
		BufferedImage img;

		public OwnPanel() {
			try {
				img = ImageIO.read(new File("assets/black.png"));
			} catch (Exception e) {
				System.err.println("Lol");
			}
		}

		float deg = 0;
		float r = 0;
		float g = 0;
		float b = 0;

		@Override
		protected void paintComponent(Graphics graphics) {
			super.paintComponent(graphics);
			Graphics2D anya = (Graphics2D) graphics;
			Graphics2D a = (Graphics2D) anya.create();
			// a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			// RenderingHints.VALUE_ANTIALIAS_ON);
			a.translate(100, 500);
			a.rotate(-Math.toRadians(deg));
			deg += 1;
			a.scale(0.5, 2);
			a.drawRect(-50, -50, 100, 100);

			// a.drawImage(img, 10, 10, null);
			// A tömb meghatározza a szorzókat a Piros, Zöld, Kék csatornákhoz

			float[] scales = { r, g, b }; // Az utolsó az Alpha (átlátszóság)

			r = (float) Math.random();
			g = (float) Math.random();
			b = (float) Math.random();

			float[] offsets = new float[4]; // Eltolás (itt 0)

			RescaleOp tintFilter = new RescaleOp(scales, offsets, null);

			// A szűrt képet rajzoljuk ki
			a.drawImage(tintFilter.filter(img, null), 0, 0, null);
			// a.drawImage(img, 10, 10, null);

			// g.drawRect(100, 100, 20, 20);
		}
	}

	public static void main(String[] args) throws Exception {

		JFrame f = new JFrame("Title");
		f.setSize(1000, 1000);
		JPanel p = new JPanel();
		OwnPanel op = new OwnPanel();
		op.setSize(1000, 1000);
		f.add(op);
		JButton b = new JButton("Gomb");
		op.setLayout(null);
		List<Bus> buszok = new ArrayList<>();
		op.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// Get coordinates of the click
				System.out.println("buszok: " + buszok.size());
				buszok.add(new Bus(null, null, null));
				int x = e.getX();
				int y = e.getY();
				System.out.println("Mouse pressed at: " + x + "," + y);
			}
		});

		b.setBounds(250, 780, 200, 200);
		f.setVisible(true);

		while (true) {
			f.repaint();
			Thread.sleep(100);
		}
	}

}