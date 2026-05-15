package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;

import graphics.NewMain;
import playground.Crossing;

public class CrossingView {
	public Point2D pos;

	private Crossing modelCrossing;
	private boolean isSnowplowerBase;

	public CrossingView(Crossing modelCrossing, Point2D pos, boolean isSnowplowerBase) {
		this.modelCrossing = modelCrossing;
		this.pos = pos;
		this.isSnowplowerBase = isSnowplowerBase;
	}

	public void paint(Graphics2D g) {
		NewMain.notdone("CrossingView paint");
	}

	public boolean isClicked(int x, int y) {
		NewMain.notdone("CrossingView isClicked");
		return false;
	}
}
