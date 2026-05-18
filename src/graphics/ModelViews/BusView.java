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


	public BusView(Bus bus, MainPanel mainPanel, Color _color) {
		this.modelBus = bus;
		this.mainPanel = mainPanel;
		this.color = _color;
		this.pos = new Point2D.Double(0, 0);
	}
/*
	void setColor(Color c){
		color = c;
	}
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

///TESZT#######################################################################################
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			BufferedImage originalImage = img;
			if (originalImage == null) {
				System.err.println("Nem sikerult betolteni az Asset/busz.png kepet.");
				return;
			}

			BusView redView = new BusView(null, null, Color.RED);
			BusView greenView = new BusView(null, null, Color.GREEN);
			BusView blueView = new BusView(null, null, Color.BLUE);

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

			JFrame frame = new JFrame("BusView szinezes teszt");
			frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
			frame.add(panel);
			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
///TESZT VEGE#######################################################################################
}
