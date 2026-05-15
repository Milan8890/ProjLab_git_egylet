package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import graphics.NewMain;
import playground.Lane;

public class LaneView {
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
		NewMain.notdone("LaneView paint");
	}
}
