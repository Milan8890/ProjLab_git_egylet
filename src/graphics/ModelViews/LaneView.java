package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Image;
import javax.swing.ImageIcon;

import java.awt.Color;

import graphics.NewMain;
import playground.Lane;
import graphics.MainPanel;

public class LaneView {
	private static final float SZEGELY_VASTAGSAG = 1f;

	private static final Image[] ASZFALT_TEXTURAK = new Image[] {
		new ImageIcon("Asset/beton100.png").getImage(),
		new ImageIcon("Asset/beton200.png").getImage(),
		new ImageIcon("Asset/beton300.png").getImage(),
		new ImageIcon("Asset/beton400.png").getImage(),
		new ImageIcon("Asset/beton500.png").getImage()
	};

	private static final int[] TEXTURA_HOSSZAK = new int[] { 100, 200, 300, 400, 500 };

	public Point2D startPos;
	public Point2D endPos;

	private Lane modelLane;

	public LaneView(Lane lane, Point2D startPos, Point2D endPos) {
		this.modelLane = lane;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	public Lane getLane() {
		return modelLane;
	}

	public void paint(Graphics2D g) {
		double startX = startPos.getX();
		double startY = startPos.getY();
		double endX = endPos.getX();
		double endY = endPos.getY();

		double dx = endX - startX;
		double dy = endY - startY;
		double hossza = Math.sqrt(dx * dx + dy * dy);
		if (hossza == 0) return;

		double szog = Math.atan2(dy, dx);

		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(startX, startY);
		g2.rotate(szog);

		int h = (int) Math.round(hossza);

		Image kivalasztottAszfalt = valasztLegkozelebbiTexturat(h);

		if (kivalasztottAszfalt != null) {
			g2.drawImage(kivalasztottAszfalt, 
				1, 1,
				h - 1, MainPanel.LANE_WIDTH - 1,
				null
			);
		} else {
			g2.setColor(Color.BLACK);
			g2.fillRect(1, 1, h - 1, MainPanel.LANE_WIDTH - 1);
		}

		g2.setColor(Color.YELLOW);
		g2.setStroke(new java.awt.BasicStroke(SZEGELY_VASTAGSAG));
		g2.drawRect(0, 0, h, MainPanel.LANE_WIDTH);

		g2.dispose();
	}

	/**
	 * Segédfüggvény, ami megkeresi, hogy a sáv 'h' pixelhosszúságához 
	 * melyik fix beton textúrahossz (100, 200, 300, 400, 500) áll a legközelebb.
	 */
	private Image valasztLegkozelebbiTexturat(int aktualisHossz) {
		int legjobbIndex = 0;
		int legkisebbKulonbseg = Math.abs(aktualisHossz - TEXTURA_HOSSZAK[0]);

		for (int i = 1; i < TEXTURA_HOSSZAK.length; i++) {
			int aktualisKulonbseg = Math.abs(aktualisHossz - TEXTURA_HOSSZAK[i]);
			if (aktualisKulonbseg < legkisebbKulonbseg) {
				legkisebbKulonbseg = aktualisKulonbseg;
				legjobbIndex = i;
			}
		}
		
		return ASZFALT_TEXTURAK[legjobbIndex];
	}
}
