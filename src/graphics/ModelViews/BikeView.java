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
import entities.Bike;
import entities.Car;

/**
 * Az autó grafikus megjelenítéséért felelős osztály.
 * <p>
 * A modellbeli autó aktuális sávja vagy kereszteződése alapján határozza meg a
 * kirajzolási pozíciót, majd a járművet a haladási irányhoz igazítva jeleníti meg.
 */
public class BikeView {
	private Bike modelBike;
	private Point2D pos;
	private MainPanel mainPanel;
	private static BufferedImage bikeImage;
	private Color color;
	private BufferedImage lastOriginalImage;
	private BufferedImage lastTintedImage;

	static {
		try {
			bikeImage = ImageIO.read(new File("Asset/bike.png"));
		} catch (Exception e) {
			bikeImage = null;
		}
	}

	/**
	 * Létrehozza az autó grafikus nézetét a megadott megjelenítési színnel.
	 *
	 * @param bike       a kirajzolandó autó modellobjektuma
	 * @param mainPanel a főpanel, amelyből a pálya nézetei elérhetők
	 * @param color    az autó megjelenítéséhez használt szín
	 */
	public BikeView(Bike bike, MainPanel mainPanel, Color color) {
		this.modelBike = bike;
		this.mainPanel = mainPanel;
		this.color = color;
		this.pos = new Point2D.Double(0, 0);
	}

	/**
	 * Kirajzolja az autót az aktuális sávján vagy az utolsó kereszteződésénél,
	 * és a haladási iránynak megfelelően elforgatja.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	public void paint(Graphics2D g) {
		if (bikeImage == null)
			return;

		Lane currentLane = modelBike.getCurrentLane();
		double carX, carY, szog;

		if (currentLane != null) {
			double absoluteProgress = modelBike.getLaneProgress();

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

			carX = startX + (endX - startX) * progressRatio;
			carY = startY + (endY - startY) * progressRatio;

			double dx = endX - startX;
			double dy = endY - startY;
			szog = Math.atan2(dy, dx);

			double length = Math.sqrt(dx * dx + dy * dy);
			double normalX = -dy / length;
			double normalY = dx / length;

			carX += normalX * (MainPanel.LANE_WIDTH / 2.0);
			carY += normalY * (MainPanel.LANE_WIDTH / 2.0);

		} else {
			Crossing currentCrossing = modelBike.getLastCrossing();
			if (currentCrossing == null)
				return;

			CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
			if (currentCrossingView == null)
				return;

			Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
			carX = center.x;
			carY = center.y;

			szog = 0.0;
		}

		this.pos.setLocation(carX, carY);

		BufferedImage szinezettKep = getTintedImage(bikeImage);

		double eredetiSzelesseg = szinezettKep.getWidth();
		double eredetiMagassag = szinezettKep.getHeight();

		int autoMagassag = MainPanel.LANE_WIDTH;
		int autoHossz = (int) Math.round(autoMagassag * (eredetiSzelesseg / eredetiMagassag));

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(carX, carY);
		g2.rotate(szog);

		g2.drawImage(szinezettKep,
				-autoHossz / 2, -autoMagassag / 2,
				autoHossz, autoMagassag,
				null);

		g2.dispose();
	}

	/**
	 * Visszaadja az autó képének a megadott színnel színezett változatát, és
	 * eltárolja az utolsó színezett képet az ismételt számítás elkerülésére.
	 *
	 * @param originalImage az eredeti, színezés nélküli autókép
	 * @return a színezett autókép, vagy az eredeti kép, ha nincs megadott szín
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
