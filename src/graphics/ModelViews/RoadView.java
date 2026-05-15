package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

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
		NewMain.notdone("RoadView paint");
	}
}
