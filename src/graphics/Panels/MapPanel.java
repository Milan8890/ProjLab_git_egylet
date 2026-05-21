package graphics.Panels;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.MainPanel;
import graphics.NewMain;
import graphics.ModelViews.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

public class MapPanel extends JPanel {

	MainPanel mainPanel = null;

	private static BufferedImage backgroundImg = null;

	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;

	/**
	 * Kép beolvasása
	 */
	static private void readImage() {
		NewMain.notdone("Nagymagyarország térképe a háttérterünk. Ezt észre kéne venni máshonnan.");
		try {
			backgroundImg = ImageIO.read(new File("assets/testing/trianon2.jpeg"));
		} catch (Exception e) {
			System.err.println("Nem sikerült beolvasni a hátteret");
		}
	}

	/**
	 * Játékteret megjelenítő panel konstruktora. Beállítja a háttérképet, felveszi
	 * a MouseListener-jét.
	 * 
	 * @param mainPanel
	 */
	public MapPanel(MainPanel mainPanel) {
		this.mainPanel = mainPanel;
		if (backgroundImg == null) {
			readImage();
		}
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();

				for (CrossingView view : mainPanel.getCrossingViews()) {
					if (view.isClicked(x, y))
						return;
				}

				for (SnowplowerView view : mainPanel.getSnowplowerViews()) {
					if (view.isClicked(x, y))
						return;
				}
			}
		});

		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}

	private void drawBackground(Graphics2D g) {
		int width = backgroundImg.getWidth();
		int height = backgroundImg.getHeight();

		g.scale((float) WIDTH / (float) width, (float) HEIGHT / (float) height);

		g.drawImage(backgroundImg, 0, 0, null);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		this.drawBackground((Graphics2D) g2d.create());

		if (mainPanel == null) {
			Logger.getGlobal().severe("No main panel in MapPanel.java");
			return;
		}

		// Külön típusonként, mert fontos a sorrend
		for (CrossingView view : mainPanel.getCrossingViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		for (RoadView view : mainPanel.getRoadViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		for (LaneView view : mainPanel.getLaneViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		for (SnowplowerView view : mainPanel.getSnowplowerViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		for (BusView view : mainPanel.getBusViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		for (CarView view : mainPanel.getCarViews()) {
			view.paint((Graphics2D) g2d.create());
		}

		NewMain.notdone("MapPanel paintComponent");
	}
}
