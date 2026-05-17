package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.Color;

import graphics.NewMain;
import playground.Road;

public class RoadView {
	public Point2D startPos;
	public Point2D endPos;

	private Road modelRoad;

	public RoadView(Road road, Point2D startPos, Point2D endPos) {
		this.modelRoad = road;
		this.startPos = startPos;
		this.endPos = endPos;
	}

	public void paint(Graphics2D g) {
        Graphics2D lineG = (Graphics2D) g.create();
        lineG.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        lineG.setColor(Color.RED);
        lineG.setStroke(new java.awt.BasicStroke(5f));
        
        lineG.drawLine((int)Math.round(startPos.getX()), (int)Math.round(startPos.getY()), 
                       (int)Math.round(endPos.getX()), (int)Math.round(endPos.getY()));
        lineG.dispose();
    }
}
