package graphics.Panels;

import javax.swing.JPanel;

import graphics.NewMain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MapPanel extends JPanel {
	// TODO asszem ez direkt nem static?
	// Lehessen más fajta pálya, de járműveknél biztos ugyan az
	private BufferedImage backgroundImg;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		NewMain.notdone("MapPanel paintComponent");
	}
}
