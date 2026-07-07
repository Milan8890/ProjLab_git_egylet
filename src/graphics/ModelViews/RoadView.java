package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.awt.Color;
import java.awt.BasicStroke;

import graphics.MainPanel;
import graphics.NewMain;
import playground.Crossing;
import playground.Lane;
import playground.Road;
import playground.Tunnel;

/**
 * Egy út grafikus megjelenítéséért felelős osztály.
 * <p>
 * Az út kezdő- és végpontja alapján rajzolja ki az utat a térképen.
 */
public class RoadView {
	public Point2D startPos;
	public Point2D endPos;

	public Road modelRoad;

	/**
	 * Létrehozza az út grafikus nézetét a hozzá tartozó modellúttal, kezdő- és
	 * végponttal.
	 *
	 * @param road     a kirajzolandó út modellobjektuma
	 * @param startPos az út kirajzolási kezdőpontja
	 * @param endPos   az út kirajzolási végpontja
	 */
	public RoadView(Road road, Point2D startPos, Point2D endPos) {
		this.modelRoad = road;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	/**
	 * Kirajzolja az utat a kezdő- és végpontja között.
	 *
	 * @param g a rajzoláshoz használt grafikus kontextus
	 */
	public void paint(Graphics2D g) {
		Graphics2D lineG = (Graphics2D) g.create();
		lineG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
		lineG.setColor(Color.RED);
		lineG.setStroke(new BasicStroke(5f));

		lineG.drawLine((int) Math.round(startPos.getX()), (int) Math.round(startPos.getY()),
				(int) Math.round(endPos.getX()), (int) Math.round(endPos.getY()));
		lineG.dispose();
	}

	public void recalculatePosition() {
		Crossing from = this.modelRoad.getFromCrossing();
		Crossing to = this.modelRoad.getToCrossing();
		CrossingView fromView = MainPanel.getCrossingView(from);
		CrossingView toView = MainPanel.getCrossingView(to);

		if (fromView == null || toView == null)
			return;

		Point2D.Double kp1 = MainPanel.calculateCenter(fromView);
		Point2D.Double kp2 = MainPanel.calculateCenter(toView);
		Point2D.Double[] roadEnds = MainPanel.calculateRoadEndPoints(kp1, kp2);
		if (roadEnds == null)
			return;

		Point2D.Double utEleje = roadEnds[0];
		Point2D.Double utVege = roadEnds[1];
		double valosUtHossz = utEleje.distance(utVege);

		this.startPos = utEleje;
		this.endPos = utVege;
	}

}
