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
import graphics.NewMain;
import entities.Snowplower;
import equipment.Head;

public class SnowplowerView {
	private Snowplower modelSnowplower;
	private Point2D pos;
	private MainPanel mainPanel;

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

	public SnowplowerView(Snowplower snowplower, MainPanel mainPanel, Color _color) {
		this.modelSnowplower = snowplower;
		this.mainPanel = mainPanel;
		color = _color;
		this.pos = new Point2D.Double(0, 0);
	}

	public void paint(Graphics2D g) {
		Lane currentLane = modelSnowplower.getCurrentLane();
		double plowX, plowY, szog;

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
				return;

			double startX = currentLaneView.startPos.getX();
			double startY = currentLaneView.startPos.getY();
			double endX = currentLaneView.endPos.getX();
			double endY = currentLaneView.endPos.getY();

			double roadLenght = currentLane.getRoad().getLength();
			double progressRatio = (roadLenght > 0) ? (absoluteProgress / roadLenght) : 0.0;

			plowX = startX + (endX - startX) * progressRatio;
			plowY = startY + (endY - startY) * progressRatio;

			double dx = endX - startX;
			double dy = endY - startY;
			szog = Math.atan2(dy, dx);

		} else {
			Crossing currentCrossing = modelSnowplower.getLastCrossing();
			if (currentCrossing == null)
				return;

			CrossingView currentCrossingView = mainPanel.getCrossingView(currentCrossing);
			if (currentCrossingView == null)
				return;

			Point2D.Double center = MainPanel.calculateCenter(currentCrossingView);
			plowX = center.x;
			plowY = center.y;
			szog = 0.0;
		}

		this.pos.setLocation(plowX, plowY);

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

		//double eredetiSzelesseg = kivalasztottKep.getWidth();
		//double eredetiMagassag = kivalasztottKep.getHeight();
		BufferedImage szinezettKep = getTintedImage(kivalasztottKep);

		double eredetiSzelesseg = szinezettKep.getWidth();
		double eredetiMagassag = szinezettKep.getHeight();

		int jarmuMagassag = MainPanel.LANE_WIDTH;
		int jarmuHossz = (int) Math.round(jarmuMagassag * (eredetiSzelesseg / eredetiMagassag));

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(plowX, plowY);
		g2.rotate(szog);

		g2.drawImage(szinezettKep,
				-jarmuHossz / 2, -jarmuMagassag / 2,
				jarmuHossz, jarmuMagassag,
				null);

		g2.dispose();
	}

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

	public boolean isClicked(int x, int y) {
		NewMain.notdone("SnowplowerView isClicked");
		return false;
	}

	
///TESZT#######################################################################################
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			BufferedImage originalImage = imgJegtoro;
			if (originalImage == null) {
				System.err.println("Nem sikerult betolteni az Asset/jegtoro.png kepet.");
				return;
			}

			SnowplowerView redView = new SnowplowerView(null, null, Color.RED);
			SnowplowerView greenView = new SnowplowerView(null, null, Color.GREEN);
			SnowplowerView blueView = new SnowplowerView(null, null, Color.BLUE);

			BufferedImage redImage = redView.getTintedImage(originalImage);
			BufferedImage greenImage = greenView.getTintedImage(originalImage);
			BufferedImage blueImage = blueView.getTintedImage(originalImage);

			JPanel panel = new JPanel() {
				@Override
				protected void paintComponent(Graphics g) {
					super.paintComponent(g);
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

					int imageWidth = 160;
					int imageHeight = 100;
					int y = 55;

					g2.drawString("Eredeti", 35, 25);
					g2.drawImage(originalImage, 20, y, imageWidth, imageHeight, null);

					g2.drawString("Piros", 225, 25);
					g2.drawImage(redImage, 200, y, imageWidth, imageHeight, null);

					g2.drawString("Zold", 405, 25);
					g2.drawImage(greenImage, 380, y, imageWidth, imageHeight, null);

					g2.drawString("Kek", 585, 25);
					g2.drawImage(blueImage, 560, y, imageWidth, imageHeight, null);

					g2.dispose();
				}

				@Override
				public Dimension getPreferredSize() {
					return new Dimension(740, 190);
				}
			};

			JFrame frame = new JFrame("SnowplowerView szinezes teszt");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.add(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
///TESZT VÉGE#######################################################################################

}
