package graphics.ModelViews;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;

import entities.Snowplower;
import graphics.NewMain;

public class SnowplowerView {
	private Snowplower modelSnowplower;
	private Point2D pos;

	static private BufferedImage img;

	public SnowplowerView(Snowplower snowplower) {
		this.modelSnowplower = snowplower;
		// nincs meg a kép
		NewMain.notdone("SnowplowerView konstruktor");
	}

	public void paint(Graphics2D g) {
		NewMain.notdone("SnowplowerView paint");
	}

	public boolean isClicked(int x, int y) {
		NewMain.notdone("SnowplowerView isClicked");
		return false;
	}

}
