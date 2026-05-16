package main;

import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import graphics.NewMain;
import graphics.Panels.MapPanel;

public class App {

	public static void main(String[] args) throws Exception {
		JFrame f = new JFrame();
		MapPanel p = new MapPanel(null);

		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		f.add(p);
		f.pack();
		f.setVisible(true);

		// Új main hívás, persze nem így lesz, csak könnyebben tudok prototipizálni
		// Meg valszeg majd itt csak létre lesz hozva a Graphics, vagy tudja a rosseb.
		NewMain.main(args);

		// Proto proto = new Proto(World.players);

		// // Handler beállítása
		// Logger.getGlobal().setUseParentHandlers(false);
		// OwnHandler ownHandler = new OwnHandler(proto.objectMap);
		// Logger.getGlobal().addHandler(ownHandler);

		// // Parancsok beolvasása
		// proto.readCommandsFromCommandLine();
	}
}