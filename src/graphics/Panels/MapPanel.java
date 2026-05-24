package graphics.Panels;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import graphics.MainPanel;
import graphics.NewMain;
import graphics.ModelViews.*;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.logging.Logger;

/**
 * A játék térképének kirajzolásáért és térképre történő kattintások
 * kezeléséért felelős panel.
 * <p>
 * A háttérkép mellett a főpanelben tárolt kereszteződés-, út-, sáv- és
 * járműnézeteket rajzolja ki.
 */
public class MapPanel extends JPanel {

	MainPanel mainPanel = null;

	private static BufferedImage backgroundImg = null;

	private static final int WIDTH = 1500;
	private static final int HEIGHT = 1000;

	/**
	 * Kép beolvasása.
	 */
	static private void readImage() { // EZZEL MIZU, SZTEM MAR MUKSZIK, HA IGEN A PR-NAL TOROLD LÉCCI??
		NewMain.notdone("Nagymagyarország térképe a háttérterünk. Ezt észre kéne venni máshonnan.");
		try {
			backgroundImg = ImageIO.read(new File("Asset/zuzmaravaros.png"));
		} catch (Exception e) {
			Logger.getGlobal().severe("Nem sikerült beolvasni a hátteret");
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
			/**
			 * Kezeli a térképre érkező kattintásokat, és továbbadja őket a
			 * kereszteződés- vagy hókotró nézeteknek.
			 *
			 * @param e az egérkattintás eseménye
			 */
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

	/**
	 * Kirajzolja a térképpanel tartalmát, beleértve a hátteret, a pályaelemeket
	 * és a járművek grafikus nézeteit.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (backgroundImg != null) {
			g2d.drawImage(backgroundImg, 0, 0, 1500, 1000, this);
		}

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

		NewMain.notdone("MapPanel paintComponent"); // EZZEL MIZU??
	}
}
