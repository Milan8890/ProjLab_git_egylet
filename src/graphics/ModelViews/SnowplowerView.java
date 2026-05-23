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
import entities.Snowplower;
import equipment.Head;

/**
 * A hókotró grafikus megjelenítéséért felelős osztály.
 * <p>
 * A modellbeli hókotró aktuális pozíciója és aktív feje alapján választja ki a
 * megfelelő képet, majd azt a haladási irányhoz igazítva és játékosszínnel
 * színezve rajzolja ki.
 */
public class SnowplowerView {
	private Snowplower modelSnowplower;
	private Point2D pos;
	private MainPanel mainPanel;
	private double rotationAngle;

	private static BufferedImage imgHanyo;
	private static BufferedImage imgJegtoro;
	private static BufferedImage imgKoszoro;
	private static BufferedImage imgSarkany;
	private static BufferedImage imgSopro;
	private static BufferedImage imgSoszoro;

	private Color color;
	private BufferedImage lastOriginalImage;
	private BufferedImage lastTintedImage;

	static {
		try {
			imgHanyo = ImageIO.read(new File("Asset/hanyo.png"));
			imgJegtoro = ImageIO.read(new File("Asset/jegtoro.png"));
			imgKoszoro = ImageIO.read(new File("Asset/koszoro.png"));
			imgSarkany = ImageIO.read(new File("Asset/sarkany.png"));
			imgSopro = ImageIO.read(new File("Asset/sopro.png"));
			imgSoszoro = ImageIO.read(new File("Asset/soszoro.png"));
		} catch (Exception e) {
			// Hiba eseten uresen marad, nem szall el a program
		}
	}

	/**
	 * Létrehozza a hókotró grafikus nézetét a hozzá tartozó modellhókotróval,
	 * főpanellel és megjelenítési színnel.
	 *
	 * @param snowplower a kirajzolandó hókotró modellobjektuma
	 * @param mainPanel  a főpanel, amelyből a pálya nézetei elérhetők
	 * @param color     a hókotró megjelenítéséhez használt szín
	 */
	public SnowplowerView(Snowplower snowplower, MainPanel mainPanel, Color color) {
		this.modelSnowplower = snowplower;
		this.mainPanel = mainPanel;
		this.color = color;
		this.pos = new Point2D.Double(0, 0);
	}

	/**
	 * Kirajzolja a hókotrót az aktuális pozícióján, az aktív fej típusának
	 * megfelelő képpel és a haladási iránynak megfelelő elforgatással.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	public void paint(Graphics2D g) {
		if (!updatePos())
			return;

		double plowX = pos.getX();
		double plowY = pos.getY();

		if (modelSnowplower.getHeadInventory() == null)
			return;
		Head activeHead = modelSnowplower.getHeadInventory().getActiveHead();
		if (activeHead == null)
			return;

		String fejTipus = activeHead.getClass().getSimpleName().toLowerCase();

		BufferedImage kivalasztottKep = null;

		if (fejTipus.contains("ejector")) {
			kivalasztottKep = imgHanyo;
		} else if (fejTipus.contains("breaker")) {
			kivalasztottKep = imgJegtoro;
		} else if (fejTipus.contains("gravel")) {
			kivalasztottKep = imgKoszoro;
		} else if (fejTipus.contains("dragon")) {
			kivalasztottKep = imgSarkany;
		} else if (fejTipus.contains("sweeper")) {
			kivalasztottKep = imgSopro;
		} else if (fejTipus.contains("salt")) {
			kivalasztottKep = imgSoszoro;
		}

		if (kivalasztottKep == null)
			return;

		BufferedImage szinezettKep = getTintedImage(kivalasztottKep);

		double eredetiSzelesseg = szinezettKep.getWidth();
		double eredetiMagassag = szinezettKep.getHeight();

		int jarmuMagassag = MainPanel.LANE_WIDTH;
		int jarmuHossz = (int) Math.round(jarmuMagassag * (eredetiSzelesseg / eredetiMagassag));

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(plowX, plowY);
		g2.rotate(rotationAngle);

		g2.drawImage(szinezettKep,
				-jarmuHossz / 2, -jarmuMagassag / 2,
				jarmuHossz, jarmuMagassag,
				null);

		g2.dispose();
	}

	/**
	 * Frissíti a hókotró kirajzolási pozícióját az aktuális sáv vagy az utolsó
	 * kereszteződés alapján, és beállítja a rajzoláshoz használt forgatási
	 * szöget.
	 *
	 * @return {@code true}, ha a pozíció sikeresen frissíthető, egyébként  {@code false}
	 */
	private boolean updatePos() {
		if (modelSnowplower == null || mainPanel == null)
			return false;

		Lane currentLane = modelSnowplower.getCurrentLane();

		if (currentLane != null) {
			double absoluteProgress = modelSnowplower.getLaneProgress();

			LaneView currentLaneView = null;
			for (LaneView lv : mainPanel.getLaneViews()) {
				if (lv.getLane() == currentLane) {
					currentLaneView = lv;
					break;
				}
			}
			if (currentLaneView == null)
				return false;

			double startX = currentLaneView.startPos.getX();
			double startY = currentLaneView.startPos.getY();
			double endX = currentLaneView.endPos.getX();
			double endY = currentLaneView.endPos.getY();

			double roadLenght = currentLane.getRoad().getLength();
			double progressRatio = (roadLenght > 0) ? (absoluteProgress / roadLenght) : 0.0;

			double plowX = startX + (endX - startX) * progressRatio;
			double plowY = startY + (endY - startY) * progressRatio;

			double dx = endX - startX;
			double dy = endY - startY;
			rotationAngle = Math.atan2(dy, dx);

			double length = Math.sqrt(dx * dx + dy * dy);
			double normalX = -dy / length;
			double normalY = dx / length;

			plowX += normalX * (MainPanel.LANE_WIDTH / 2.0);
			plowY += normalY * (MainPanel.LANE_WIDTH / 2.0);

			pos.setLocation(plowX, plowY);
			return true;
		}

		Crossing currentCrossing = modelSnowplower.getLastCrossing();
		if (currentCrossing == null)
			return false;

		CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
		if (currentCrossingView == null)
			return false;

		Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
		pos.setLocation(center.x, center.y);
		rotationAngle = 0.0;
		return true;
	}

	/**
	 * Visszaadja a hókotró képének a megadott színnel színezett
	 * változatát, és eltárolja az utolsó színezett képet az ismételt számítás
	 * elkerülésének érdekében.
	 *
	 * @param originalImage az eredeti, színezés nélküli hókotrókép
	 * @return a színezett hókotrókép, vagy az eredeti kép, ha nincs megadott szín
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

	/**
	 * Kezeli a hókotróra érkező kattintást, és sikeres találat esetén
	 * kiválasztja ezt a hókotrót a főpanelen.
	 *
	 * @param x a kattintás x koordinátája
	 * @param y a kattintás y koordinátája
	 * @return {@code true}, ha a kattintás ezt a hókotrót találta el, egyébként {@code false}
	 */
	public boolean isClicked(int x, int y) {
		if (!updatePos())
			return false;
		if (modelSnowplower.getCleaner() != mainPanel.getSelectedCleaner())
			return false;
		if (pos.distance(x, y) > MainPanel.LANE_WIDTH)
			return false;

		mainPanel.setSelectedSnowplower(modelSnowplower);
		return true;
	}

}
