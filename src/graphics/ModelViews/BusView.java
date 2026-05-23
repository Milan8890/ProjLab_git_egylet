package graphics.ModelViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.RenderingHints;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import playground.Crossing;
import playground.Lane;
import graphics.MainPanel;
import entities.Bus;

/**
 * A busz grafikus megjelenítéséért felelős osztály.
 * <p>
 * A modellbeli busz aktuális helyzete alapján kiszámolja a képernyőpozíciót,
 * elforgatja a buszképet a haladási iránynak megfelelően, és a játékos
 * színével színezi azt.
 */
public class BusView {
	private Bus modelBus;
	private Point2D pos;
	private MainPanel mainPanel;
	private static BufferedImage img;

	private Color color;
	private BufferedImage lastOriginalImage;
	private BufferedImage lastTintedImage;

	static {
		try {
			img = ImageIO.read(new File("Asset/busz.png"));
		} catch (Exception e) {
			img = null;
		}
	}


	/**
	 * Létrehozza a busz grafikus nézetét a hozzá tartozó modellbusszal,
	 * főpanellel és megjelenítési színnel.
	 *
	 * @param bus       a kirajzolandó busz modellobjektuma
	 * @param mainPanel a főpanel, amelyből a pálya nézetei elérhetők
	 * @param color    a busz megjelenítéséhez használt szín
	 */
	public BusView(Bus bus, MainPanel mainPanel, Color color) {
		this.modelBus = bus;
		this.mainPanel = mainPanel;
		this.color = color;
		this.pos = new Point2D.Double(0, 0);
	}

	/**
	 * Kirajzolja a buszt az aktuális sávján vagy az utolsó kereszteződésénél,
	 * és a haladási iránynak megfelelően elforgatja.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	public void paint(Graphics2D g) {
		if (img == null)
			return;

		Lane currentLane = modelBus.getCurrentLane();
		double busX, busY, szog;

		if (currentLane != null) {
			double absoluteProgress = modelBus.getLaneProgress();

			LaneView currentLaneView = null;
			for (LaneView lv : mainPanel.getLaneViews()) {
				if (lv.getLane() == currentLane) {
					currentLaneView = lv;
					break;
				}
			}
			if (currentLaneView == null)
				return;

			double startX = currentLaneView.startPos.getX();
			double startY = currentLaneView.startPos.getY();
			double endX = currentLaneView.endPos.getX();
			double endY = currentLaneView.endPos.getY();

			double utHossz = currentLane.getRoad().getLength();
			double progressRatio = (utHossz > 0) ? (absoluteProgress / utHossz) : 0.0;

			busX = startX + (endX - startX) * progressRatio;
			busY = startY + (endY - startY) * progressRatio;

			double dx = endX - startX;
			double dy = endY - startY;
			szog = Math.atan2(dy, dx);

			double length = Math.sqrt(dx * dx + dy * dy);
			double normalX = -dy / length;
			double normalY = dx / length;

			busX += normalX * (MainPanel.LANE_WIDTH / 2.0);
			busY += normalY * (MainPanel.LANE_WIDTH / 2.0);

		} else {
			Crossing currentCrossing = modelBus.getLastCrossing();
			if (currentCrossing == null)
				return;

			CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
			if (currentCrossingView == null)
				return;

			Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
			busX = center.x;
			busY = center.y;
			szog = 0.0;
		}

		this.pos.setLocation(busX, busY);

		BufferedImage szinezettKep = getTintedImage(img);

		double eredetiSzelesseg = szinezettKep.getWidth();
		double eredetiMagassag = szinezettKep.getHeight();

		int busMagassag = MainPanel.LANE_WIDTH;
		int busHossz = (int) Math.round(busMagassag * (eredetiSzelesseg / eredetiMagassag));

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(busX, busY);
		g2.rotate(szog);

		g2.drawImage(szinezettKep,
				-busHossz / 2, -busMagassag / 2,
				busHossz, busMagassag,
				null);

		g2.dispose();
	}

	/**
	 * Visszaadja a busz képének a megadott színezett változatát,
	 * és eltárolja az utolsó színezett képet az ismételt számítás elkerülése érdekében.
	 *
	 * @param originalImage az eredeti, színezés nélküli buszkép
	 * @return a színezett buszkép
	 */
	private BufferedImage getTintedImage(BufferedImage originalImage) {
		if (color == null) {
			return originalImage;
		}
		if (originalImage == lastOriginalImage && lastTintedImage != null) {
			return lastTintedImage;
		}

		BufferedImage argbImage = new BufferedImage(
				originalImage.getWidth(),
				originalImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D imageGraphics = argbImage.createGraphics();
		imageGraphics.drawImage(originalImage, 0, 0, null);
		imageGraphics.dispose();

		float[] scales = {
				color.getRed() / 255f,
				color.getGreen() / 255f,
				color.getBlue() / 255f,
				color.getAlpha() / 255f
		};
		float[] offsets = new float[4];

		RescaleOp tintFilter = new RescaleOp(scales, offsets, null);

		lastOriginalImage = originalImage;
		lastTintedImage = tintFilter.filter(argbImage, null);
		return lastTintedImage;
	}

}
