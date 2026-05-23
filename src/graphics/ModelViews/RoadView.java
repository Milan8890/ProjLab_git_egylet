package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;
import java.awt.BasicStroke;

import graphics.NewMain;
import playground.Road;

/**
 * Egy út grafikus megjelenítéséért felelős osztály.
 * <p>
 * Az út kezdő- és végpontja alapján rajzolja ki az utat a térképen.
 */
public class RoadView {
	public Point2D startPos;
	public Point2D endPos;

	private Road modelRoad;

	/**
	 * Létrehozza az út grafikus nézetét a hozzá tartozó modellúttal, kezdő- és végponttal.
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
        
        lineG.drawLine((int)Math.round(startPos.getX()), (int)Math.round(startPos.getY()), 
                       (int)Math.round(endPos.getX()), (int)Math.round(endPos.getY()));
        lineG.dispose();
    }
}
