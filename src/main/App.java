package main;

import java.util.logging.Logger;

import graphics.NewMain;

public class App {

	public static void main(String[] args) throws Exception {
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